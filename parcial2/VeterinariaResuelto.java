package Parciales.Parcial2025.Segundo.Veterinaria;

import java.util.ArrayList;
import java.util.Scanner;

// =================================================================
// EJERCICIO 1: LA INTERFACE (Contrato de Cuidados)
// =================================================================
interface Cuidados {
    double calcularCostoCuidado(double costoBase, int edadAnimal);
}

// =================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Clase Checked para validar peso)
// =================================================================
class PesoInsuficienteException extends Exception {
    public PesoInsuficienteException(String mensaje) { super(mensaje); }
    public PesoInsuficienteException() { super("Error: El peso es insuficiente para la especie."); }
}

// --- Clase Abstracta Animalito ---
abstract class Animalito {
    protected String especie;
    protected int edad;
    protected String nombre;
    protected double peso;

    public Animalito(String especie, int edad, String nombre, double peso) {
        this.especie = especie.toUpperCase();
        this.edad = edad;
        this.nombre = nombre.toUpperCase();
        this.peso = peso;
    }

    public String getNombre() { return nombre; }
    public abstract String verTipoDeAnimal();

    @Override
    public String toString() {
        return especie + "\t" + edad + " años\t" + nombre + "\t" + peso + "Kg";
    }

    // =================================================================
    // EJERCICIO 3: IGUALDAD (Sobrescribir equals y hashCode por Nombre)
    // =================================================================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Validación estricta (getClass)
        if (obj == null || getClass() != obj.getClass()) return false;
        Animalito otro = (Animalito) obj;
        return nombre.equals(otro.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
}

// --- Clase Avicolas ---
class Avicolas extends Animalito implements Cuidados {
    private String tipoPlumaje;

    // Uso de la EXCEPCIÓN (Ejercicio 2) en el constructor
    public Avicolas(String especie, int edad, String nombre, double peso, String tipoPlumaje)
            throws PesoInsuficienteException {
        super(especie, edad, nombre, peso);

        // Validación requerida: Mínimo 1kg
        if (peso < 1.0) {
            throw new PesoInsuficienteException("Error: Un animal avícola debe tener al menos 1kg. Recibido: " + peso);
        }
        this.tipoPlumaje = tipoPlumaje;
    }

    // Implementación de INTERFACE (Ejercicio 1) para Avícolas
    @Override
    public double calcularCostoCuidado(double costoBase, int edadAnimal) {
        // 1. Incremento por edad: 5% por año
        double incrementoEdad = this.edad * 0.05;

        // 2. Porcentaje adicional por plumaje
        double porcentajePlumaje;
        if (tipoPlumaje.equalsIgnoreCase("EXOTICO")) porcentajePlumaje = 0.40;     // 40%
        else if (tipoPlumaje.equalsIgnoreCase("COLORIDO")) porcentajePlumaje = 0.30; // 30%
        else porcentajePlumaje = 0.20;                                             // 20% (defecto)

        double costoConEdad = costoBase * (1 + incrementoEdad);
        return costoConEdad * (1 + porcentajePlumaje);
    }

    @Override
    public String verTipoDeAnimal() { return "🐦"; }

    @Override
    public String toString() { return super.toString() + "\t" + tipoPlumaje + " plumaje"; }
}

// --- Clase Caceras ---
class Caceras extends Animalito implements Cuidados {
    private double longitudCola;

    public Caceras(String especie, int edad, String nombre, double peso, double longitudCola) {
        super(especie, edad, nombre, peso);
        this.longitudCola = longitudCola;
    }

    // Implementación de INTERFACE (Ejercicio 1) para Caceras
    @Override
    public double calcularCostoCuidado(double costoBase, int edadAnimal) {
        // Lógica hipotética: Descuento base 15% + Recargo por cola larga
        double descuentoBase = 0.15;
        double recargoCola = (longitudCola > 0.5) ? 0.20 : 0.0;

        double costoIntermedio = costoBase * (1 - descuentoBase);
        return costoIntermedio * (1 + recargoCola);
    }

    @Override
    public String verTipoDeAnimal() { return "🐺"; }

    @Override
    public String toString() { return super.toString() + "\t" + longitudCola + "m cola"; }
}

// --- Clase Inventario ---
class Inventario {
    private ArrayList<Avicolas> avicolas = new ArrayList<>();
    private ArrayList<Caceras> caceras = new ArrayList<>();

    public boolean agregarAvicola(Avicolas a) {
        if (buscarAnimal(a.getNombre()) == null) {
            avicolas.add(a);
            return true;
        }
        return false;
    }

    public boolean agregarCacera(Caceras c) {
        if (buscarAnimal(c.getNombre()) == null) {
            caceras.add(c);
            return true;
        }
        return false;
    }

    // =================================================================
    // EJERCICIO 4: BÚSQUEDA (Buscar en dos listas secuencialmente)
    // =================================================================
    public Animalito buscarAnimal(String nombre) {
        // 1. Primero busca en avícolas
        for (Avicolas a : avicolas) {
            if (a.getNombre().equals(nombre)) return a;
        }
        // 2. Luego busca en caceras
        for (Caceras c : caceras) {
            if (c.getNombre().equals(nombre)) return c;
        }
        return null; // Si no encuentra en ninguna
    }

    public boolean eliminarAnimal(String nombre) {
        boolean borradoA = avicolas.removeIf(a -> a.getNombre().equals(nombre));
        boolean borradoC = caceras.removeIf(c -> c.getNombre().equals(nombre));
        return borradoA || borradoC;
    }

    public void listarTodo() {
        System.out.println("--- AVÍCOLAS ---");
        avicolas.forEach(System.out::println);
        System.out.println("\n--- CACERAS ---");
        caceras.forEach(System.out::println);
    }
    
    // Métodos update para completar la funcionalidad
    public boolean actualizarAvicola(String nombre, Avicolas nueva) {
        for(int i=0; i<avicolas.size(); i++) {
            if(avicolas.get(i).getNombre().equals(nombre)) { avicolas.set(i, nueva); return true; }
        } return false;
    }
    public boolean actualizarCacera(String nombre, Caceras nueva) {
        for(int i=0; i<caceras.size(); i++) {
            if(caceras.get(i).getNombre().equals(nombre)) { caceras.set(i, nueva); return true; }
        } return false;
    }
}

// --- Clase Principal (Main) ---
public class Main {
    public static void main(String[] args) {
        Inventario inv = new Inventario();
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Agregar Avicola | 2. Agregar Cacera | 3. Listar | 4. Buscar | 5. Salir");
            String op = sc.nextLine();
            
            if (op.equals("5")) break;
            
            try {
                if (op.equals("1")) {
                    System.out.print("Nombre, Especie, Edad, Peso, Plumaje (EXOTICO/COLORIDO/COMUN): ");
                    // Ejemplo: PIOLIN Canario 2 0.5 COLORIDO  (Fallará por peso < 1.0)
                    // Ejemplo OK: LORO Guacamayo 5 1.2 EXOTICO
                    String[] datos = sc.nextLine().split(" ");
                    Avicolas a = new Avicolas(datos[1], Integer.parseInt(datos[2]), datos[0], Double.parseDouble(datos[3]), datos[4]);
                    if (inv.agregarAvicola(a)) System.out.println("Agregado. Costo Cuidado: " + a.calcularCostoCuidado(100, 2));
                    else System.out.println("Error: Nombre repetido.");
                } 
                else if (op.equals("2")) {
                    System.out.print("Nombre, Especie, Edad, Peso, Longitud Cola: ");
                    String[] datos = sc.nextLine().split(" ");
                    Caceras c = new Caceras(datos[1], Integer.parseInt(datos[2]), datos[0], Double.parseDouble(datos[3]), Double.parseDouble(datos[4]));
                    if (inv.agregarCacera(c)) System.out.println("Agregado. Costo Cuidado: " + c.calcularCostoCuidado(200, 5));
                    else System.out.println("Error: Nombre repetido.");
                }
                else if (op.equals("3")) inv.listarTodo();
                else if (op.equals("4")) {
                    System.out.print("Nombre: ");
                    Animalito ani = inv.buscarAnimal(sc.nextLine().toUpperCase());
                    System.out.println(ani != null ? "Encontrado: " + ani : "No existe.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
