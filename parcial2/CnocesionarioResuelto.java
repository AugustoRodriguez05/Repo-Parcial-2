package Parciales.Parcial2025.Segundo.Concesionario;

import java.util.ArrayList;
import java.util.Scanner;

// =================================================================
// EJERCICIO 1: LA INTERFACE (Definición del contrato)
// =================================================================
interface Ventas {
    double calcularPrecioVenta(double precioBase, int anioActual);
}

// =================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Clase Checked para validar)
// =================================================================
class PuertasInsuficientesException extends Exception {
    public PuertasInsuficientesException(String mensaje) { super(mensaje); }
    public PuertasInsuficientesException() { super("Error: Un auto debe tener al menos 3 puertas."); }
}

// --- Clase Abstracta Vehiculo ---
abstract class Vehiculo {
    protected String marca;
    protected int modelo; // Año del modelo
    protected String patente;
    protected int kilometraje;

    public Vehiculo(String marca, int modelo, String patente, int kilometraje) {
        this.marca = marca.toUpperCase();
        this.modelo = modelo;
        this.patente = patente.toUpperCase();
        this.kilometraje = kilometraje;
    }

    public String getPatente() { return patente; }
    public abstract String verTipoDeVehiculo();

    @Override
    public String toString() {
        return marca + "\t" + modelo + "\t" + patente + "\t" + kilometraje + "Km";
    }

    // =================================================================
    // EJERCICIO 3: IGUALDAD (Sobrescribir equals y hashCode por Patente)
    // =================================================================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Validación estricta (getClass)
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo otro = (Vehiculo) obj;
        return patente.equals(otro.patente);
    }

    @Override
    public int hashCode() {
        return patente.hashCode();
    }
}

// --- Clase Auto ---
class Auto extends Vehiculo implements Ventas {
    private int cantPuertas;

    // Uso de la EXCEPCIÓN (Ejercicio 2) en el constructor
    public Auto(String marca, int modelo, String patente, int kilometraje, int cantPuertas)
            throws PuertasInsuficientesException {
        super(marca, modelo, patente, kilometraje);

        // Validación requerida
        if (cantPuertas < 3) {
            throw new PuertasInsuficientesException("Error: Auto requiere al menos 3 puertas. Recibido: " + cantPuertas);
        }
        this.cantPuertas = cantPuertas;
    }

    // Implementación de INTERFACE (Ejercicio 1) para Auto
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int aniosUso = anioActual - this.modelo;
        // 1. Depreciación: 5% por año
        double precioDepreciado = precioBase - (precioBase * (aniosUso * 0.05));
        
        // 2. Lógica de puertas (Porcentaje adicional)
        double porcentajePuertas;
        if (cantPuertas == 3) porcentajePuertas = 0.30;       // 30%
        else if (cantPuertas == 4) porcentajePuertas = 0.40;  // 40%
        else porcentajePuertas = 0.35;                        // 35% (defecto)
        
        return precioDepreciado * (1 + porcentajePuertas);
    }

    @Override
    public String verTipoDeVehiculo() { return "🚗 Auto"; }

    @Override
    public String toString() { return super.toString() + "\t" + cantPuertas + " puertas"; }
}

// --- Clase Moto ---
class Moto extends Vehiculo implements Ventas {

    public Moto(String marca, int modelo, String patente, int kilometraje) {
        super(marca, modelo, patente, kilometraje);
    }

    // Implementación de INTERFACE (Ejercicio 1) para Moto
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int aniosUso = anioActual - this.modelo;
        // 1. Depreciación: 8% por año (rápida)
        double depreciacion = aniosUso * 0.08;
        // 2. Descuento fijo: 10%
        double descuentoMoto = 0.10;

        double precioFinal = (precioBase * (1 - depreciacion)) * (1 - descuentoMoto);
        return Math.max(precioFinal, 0);
    }

    @Override
    public String verTipoDeVehiculo() { return "🏍️ Moto"; }
}

// --- Clase Inventario ---
class Inventario {
    private ArrayList<Auto> autos = new ArrayList<>();
    private ArrayList<Moto> motos = new ArrayList<>();

    public boolean agregarAuto(Auto a) {
        if (buscarVehiculo(a.getPatente()) == null) {
            autos.add(a);
            return true;
        }
        return false;
    }

    public boolean agregarMoto(Moto m) {
        if (buscarVehiculo(m.getPatente()) == null) {
            motos.add(m);
            return true;
        }
        return false;
    }

    // =================================================================
    // EJERCICIO 4: BÚSQUEDA (Buscar en dos listas secuencialmente)
    // =================================================================
    public Vehiculo buscarVehiculo(String patente) {
        // 1. Primero busca en autos
        for (Auto a : autos) {
            if (a.getPatente().equals(patente)) return a;
        }
        // 2. Luego busca en motos
        for (Moto m : motos) {
            if (m.getPatente().equals(patente)) return m;
        }
        return null; // Si no encuentra en ninguna
    }

    public boolean eliminarVehiculo(String patente) {
        boolean borradoA = autos.removeIf(a -> a.getPatente().equals(patente));
        boolean borradoM = motos.removeIf(m -> m.getPatente().equals(patente));
        return borradoA || borradoM;
    }

    public void listarTodo() {
        System.out.println("--- AUTOS ---");
        autos.forEach(System.out::println);
        System.out.println("\n--- MOTOS ---");
        motos.forEach(System.out::println);
    }
    
    // Métodos update para completar la funcionalidad
    public boolean actualizarAuto(String patente, Auto nuevo) {
        for(int i=0; i<autos.size(); i++) {
            if(autos.get(i).getPatente().equals(patente)) { autos.set(i, nuevo); return true; }
        } return false;
    }
    public boolean actualizarMoto(String patente, Moto nueva) {
        for(int i=0; i<motos.size(); i++) {
            if(motos.get(i).getPatente().equals(patente)) { motos.set(i, nueva); return true; }
        } return false;
    }
}

// --- Clase Principal (Main) ---
public class Main {
    public static void main(String[] args) {
        Inventario inv = new Inventario();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Agregar Auto | 2. Agregar Moto | 3. Listar | 4. Buscar | 5. Salir");
            String op = sc.nextLine();
            
            if (op.equals("5")) break;
            
            try {
                if (op.equals("1")) {
                    System.out.print("Patente, Marca, Año, Km, Puertas: ");
                    // Ejemplo entrada: ABC-123 Ford 2020 50000 4
                    String[] datos = sc.nextLine().split(" ");
                    Auto a = new Auto(datos[1], Integer.parseInt(datos[2]), datos[0], Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    if (inv.agregarAuto(a)) System.out.println("Agregado. Precio venta: " + a.calcularPrecioVenta(10000, 2025));
                    else System.out.println("Error: Patente repetida.");
                } 
                else if (op.equals("2")) {
                    System.out.print("Patente, Marca, Año, Km: ");
                    String[] datos = sc.nextLine().split(" ");
                    Moto m = new Moto(datos[1], Integer.parseInt(datos[2]), datos[0], Integer.parseInt(datos[3]));
                    if (inv.agregarMoto(m)) System.out.println("Agregada. Precio venta: " + m.calcularPrecioVenta(5000, 2025));
                    else System.out.println("Error: Patente repetida.");
                }
                else if (op.equals("3")) inv.listarTodo();
                else if (op.equals("4")) {
                    System.out.print("Patente: ");
                    Vehiculo v = inv.buscarVehiculo(sc.nextLine().toUpperCase());
                    System.out.println(v != null ? "Encontrado: " + v : "No existe.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
