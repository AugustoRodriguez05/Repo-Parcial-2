package Parciales.Parcial2025.Segundo.Tecnologia;

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
class PuertosInsuficientesException extends Exception {
    public PuertosInsuficientesException(String mensaje) { super(mensaje); }
    public PuertosInsuficientesException() { super("Error: Mínimo 5 puertos requeridos."); }
}

// --- Clase Abstracta Computadora ---
abstract class Computadora {
    protected String marca;
    protected int modelo;
    protected String numeroSerie;
    protected int horasUso;

    public Computadora(String marca, int modelo, String numeroSerie, int horasUso) {
        this.marca = marca.toUpperCase();
        this.modelo = modelo;
        this.numeroSerie = numeroSerie.toUpperCase();
        this.horasUso = horasUso;
    }

    public String getNumeroSerie() { return numeroSerie; }
    public abstract String verTipoDeComputadora();

    @Override
    public String toString() {
        return marca + "\t" + modelo + "\t" + numeroSerie + "\t" + horasUso + "hrs";
    }

    // =================================================================
    // EJERCICIO 3: IGUALDAD (Sobrescribir equals y hashCode)
    // =================================================================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Computadora otra = (Computadora) obj;
        return numeroSerie.equals(otra.numeroSerie);
    }

    @Override
    public int hashCode() {
        return numeroSerie.hashCode();
    }
}

// --- Clase Escritorio ---
class Escritorio extends Computadora implements Ventas {
    private int cantidadPuertos;

    // Uso de la EXCEPCIÓN (Ejercicio 2) en el constructor
    public Escritorio(String marca, int modelo, String numeroSerie, int horasUso, int cantidadPuertos)
            throws PuertosInsuficientesException {
        super(marca, modelo, numeroSerie, horasUso);
        
        // Validación requerida
        if (cantidadPuertos < 5) {
            throw new PuertosInsuficientesException("Un escritorio requiere mínimo 5 puertos. Recibido: " + cantidadPuertos);
        }
        this.cantidadPuertos = cantidadPuertos;
    }

    // Implementación de INTERFACE (Ejercicio 1) para Escritorio
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int anios = anioActual - this.modelo;
        double depreciacion = anios * 0.06; // 6% por año
        
        double porcentajePuertos;
        if (cantidadPuertos <= 4) porcentajePuertos = 0.1;
        else if (cantidadPuertos <= 8) porcentajePuertos = 0.25;
        else porcentajePuertos = 0.4;

        double precioDepreciado = precioBase * (1 - depreciacion);
        return precioDepreciado * (1 + porcentajePuertos);
    }

    @Override
    public String verTipoDeComputadora() { return "🖥️"; }

    @Override
    public String toString() { return super.toString() + "\t" + cantidadPuertos + " puertos"; }
}

// --- Clase Laptop ---
class Laptop extends Computadora implements Ventas {

    public Laptop(String marca, int modelo, String numeroSerie, int horasUso) {
        super(marca, modelo, numeroSerie, horasUso);
    }

    // Implementación de INTERFACE (Ejercicio 1) para Laptop
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int anios = anioActual - this.modelo;
        double depreciacion = anios * 0.12; // 12% por año
        double descuentoPortabilidad = 0.15; // 15% extra

        double precioFinal = (precioBase * (1 - depreciacion)) * (1 - descuentoPortabilidad);
        return (precioFinal < 0) ? 0 : precioFinal;
    }

    @Override
    public String verTipoDeComputadora() { return "💻"; }
}

// --- Clase Inventario ---
class InventarioComputadoras {
    private ArrayList<Escritorio> escritorios = new ArrayList<>();
    private ArrayList<Laptop> laptops = new ArrayList<>();

    public boolean agregarEscritorio(Escritorio e) {
        if (buscarComputadora(e.getNumeroSerie()) == null) {
            escritorios.add(e);
            return true;
        }
        return false;
    }

    public boolean agregarLaptop(Laptop l) {
        if (buscarComputadora(l.getNumeroSerie()) == null) {
            laptops.add(l);
            return true;
        }
        return false;
    }
    
    // =================================================================
    // EJERCICIO 4: BÚSQUEDA (Buscar en dos listas secuencialmente)
    // =================================================================
    public Computadora buscarComputadora(String serie) {
        // 1. Primero busca en escritorios
        for (Escritorio e : escritorios) {
            if (e.getNumeroSerie().equals(serie)) return e;
        }
        // 2. Luego busca en laptops
        for (Laptop l : laptops) {
            if (l.getNumeroSerie().equals(serie)) return l;
        }
        return null; // Si no encuentra en ninguna
    }
    
    public boolean eliminarComputadora(String serie) {
        boolean borradoE = escritorios.removeIf(e -> e.getNumeroSerie().equals(serie));
        boolean borradoL = laptops.removeIf(l -> l.getNumeroSerie().equals(serie));
        return borradoE || borradoL;
    }

    public void listarTodo() {
        System.out.println("--- ESCRITORIOS ---");
        escritorios.forEach(System.out::println);
        System.out.println("\n--- LAPTOPS ---");
        laptops.forEach(System.out::println);
    }

    public int cantidadTotal() { return escritorios.size() + laptops.size(); }
    
    // Métodos update simplificados para completar el código
    public boolean actualizarEscritorio(String serie, Escritorio nuevo) {
        for(int i=0; i<escritorios.size(); i++) {
            if(escritorios.get(i).getNumeroSerie().equals(serie)) { escritorios.set(i, nuevo); return true; }
        } return false;
    }
    public boolean actualizarLaptop(String serie, Laptop nuevo) {
        for(int i=0; i<laptops.size(); i++) {
            if(laptops.get(i).getNumeroSerie().equals(serie)) { laptops.set(i, nuevo); return true; }
        } return false;
    }
}

// --- Clase Principal (Main) ---
public class Main {
    public static void main(String[] args) {
        InventarioComputadoras inv = new InventarioComputadoras();
        Scanner sc = new Scanner(System.in);
        
        // Simulación básica de menú para probar todo
        while (true) {
            System.out.println("\n1. Agregar Escritorio | 2. Agregar Laptop | 3. Listar | 4. Buscar | 5. Salir");
            String op = sc.nextLine();
            
            if (op.equals("5")) break;
            
            try {
                if (op.equals("1")) {
                    System.out.print("Serie, Marca, Año, Horas, Puertos: ");
                    // Ejemplo entrada rápida: SERIE1 HP 2020 100 6
                    String[] datos = sc.nextLine().split(" ");
                    Escritorio e = new Escritorio(datos[1], Integer.parseInt(datos[2]), datos[0], Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    if (inv.agregarEscritorio(e)) System.out.println("Agregado. Precio venta: " + e.calcularPrecioVenta(1000, 2025));
                    else System.out.println("Error: Serie repetida.");
                } 
                else if (op.equals("2")) {
                    System.out.print("Serie, Marca, Año, Horas: ");
                    String[] datos = sc.nextLine().split(" ");
                    Laptop l = new Laptop(datos[1], Integer.parseInt(datos[2]), datos[0], Integer.parseInt(datos[3]));
                    if (inv.agregarLaptop(l)) System.out.println("Agregado. Precio venta: " + l.calcularPrecioVenta(1000, 2025));
                    else System.out.println("Error: Serie repetida.");
                }
                else if (op.equals("3")) inv.listarTodo();
                else if (op.equals("4")) {
                    System.out.print("Serie: ");
                    Computadora c = inv.buscarComputadora(sc.nextLine().toUpperCase());
                    System.out.println(c != null ? "Encontrado: " + c : "No existe.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
