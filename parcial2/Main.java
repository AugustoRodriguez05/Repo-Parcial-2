// ------------------------------------------------------------
// Sistema de Administración de Vehículos - Empresa "FlashDelivery"
// Consultan autos, motos y colectivos usados en repartos
// ------------------------------------------------------------

// Importo la clase ArrayList para almacenar objetos
import java.util.ArrayList;

// Importo Scanner para leer datos del usuario por consola
import java.util.Scanner;

// Importo excepción para manejar errores en entrada numérica
import java.util.InputMismatchException;

// Importo Objects para generar equals/hashCode de forma segura
import java.util.Objects;

// Importo Predicate para usar lambdas como removeIf
import java.util.function.Predicate;

// ------------------------------------------------------------
// EXCEPCIÓN PERSONALIZADA
// ------------------------------------------------------------

// Creo mi propia excepción para validar patentes erróneas
class PatenteInvalidaException extends Exception {

    // Constructor que recibe un mensaje explicativo
    public PatenteInvalidaException(String msg) {
        // Llamo al constructor de Exception
        super(msg);
    }
}

// ------------------------------------------------------------
// INTERFAZ IDENTIFICABLE
// ------------------------------------------------------------

// Declaro una interfaz con un método obligatorio
interface Identificable {

    // Método que devuelve un identificador (en este caso, la patente)
    String getIdentificador();
}

// ------------------------------------------------------------
// INTERFAZ COSTO MANTENIMIENTO
// ------------------------------------------------------------

// Interfaz obligatoria para que cada vehículo calcule su costo
interface CostoMantenimiento {

    // Método abstracto a implementar
    double calcularCostoMantenimiento();
}

// ------------------------------------------------------------
// SUPERCLASE VEHÍCULO
// ------------------------------------------------------------

// Clase abstracta porque no quiero instanciar Vehículos directamente
abstract class Vehiculo implements Identificable, CostoMantenimiento {

    // Atributos privados (encapsulados)
    private String marca;       // Marca del vehículo
    private int modelo;         // Año del modelo
    private String patente;     // Patente única
    private int tipo;           // Tipo numérico (auto/moto/colectivo)
    private int kilometraje;    // Kilómetros recorridos

    // Constructor parametrizado que recibe todos los datos
    public Vehiculo(String marca, int modelo, String patente, int tipo, int kilometraje) {

        // Guardo los valores recibidos en los atributos
        this.marca = marca;
        this.modelo = modelo;

        // Normalizo patente a mayúscula para evitar duplicados
        this.patente = patente.toUpperCase();

        // Guardo tipo
        this.tipo = tipo;

        // Guardo km recorridos
        this.kilometraje = kilometraje;
    }

    // --------------------- GETTERS Y SETTERS ---------------------

    public String getMarca() { return marca; }     // Devuelve marca
    public void setMarca(String marca) { this.marca = marca; }   // Modifica marca

    public int getModelo() { return modelo; }      // Devuelve modelo
    public void setModelo(int modelo) { this.modelo = modelo; }  // Modifica modelo

    public String getPatente() { return patente; } // Devuelve patente
    public void setPatente(String patente) { this.patente = patente.toUpperCase(); } // Modifica patente

    public int getTipo() { return tipo; }          // Devuelve tipo
    public void setTipo(int tipo) { this.tipo = tipo; }          // Modifica tipo

    public int getKilometraje() { return kilometraje; }  // Devuelve km
    public void setKilometraje(int kilometraje) { this.kilometraje = kilometraje; } // Modifica km

    // Implementación del método de Identificable
    @Override
    public String getIdentificador() {
        return patente;   // Devuelvo la patente como identificador
    }

    // Método mostrar sin parámetros (sobrecarga)
    public void mostrar() {
        System.out.println(toString());
    }

    // Sobrecarga: mismo nombre, diferente parámetro
    public void mostrar(boolean detallado) {
        // Si detallado es true, agrego prefijo, si no muestro normal
        System.out.println(detallado ? ("DETALLE → " + toString()) : toString());
    }

    // Redefino equals para comparar vehículos por patente
    @Override
    public boolean equals(Object o) {

        // Si es el mismo objeto, son iguales
        if (this == o) return true;

        // Si no es instancia de Vehiculo, no sirven
        if (!(o instanceof Vehiculo)) return false;

        // Casteo el objeto recibido a Vehiculo
        Vehiculo v = (Vehiculo) o;

        // Comparo patentes
        return this.patente.equals(v.patente);
    }

    // Genero hashCode basado en patente
    @Override
    public int hashCode() {
        return Objects.hash(patente);
    }

    // Método abstracto que obligo a implementar en subclases
    @Override
    public abstract double calcularCostoMantenimiento();

    // toString devuelve los datos del vehículo
    @Override
    public String toString() {
        return "Marca: " + marca +
               ", Modelo: " + modelo +
               ", Patente: " + patente +
               ", Tipo: " + tipo +
               ", KM: " + kilometraje;
    }
}

// ------------------------------------------------------------
// SUBCLASE AUTO
// ------------------------------------------------------------
class Auto extends Vehiculo {

    // Atributo propio del Auto
    private int cantPuertas;

    // Constructor recibe todos los datos + cantidad de puertas
    public Auto(String marca, int modelo, String patente, int tipo, int kilometraje, int cantPuertas) {

        // Llamo al constructor de Vehiculo
        super(marca, modelo, patente, tipo, kilometraje);

        // Guardo cantidad de puertas
        this.cantPuertas = cantPuertas;
    }

    // Implemento el costo de mantenimiento para el auto
    @Override
    public double calcularCostoMantenimiento() {
        return 5000 + (getKilometraje() * 0.1); // Fórmula del enunciado
    }

    // Devuelvo representación en texto
    @Override
    public String toString() {
        return "[AUTO] " + super.toString() + ", Puertas: " + cantPuertas;
    }
}

// ------------------------------------------------------------
// SUBCLASE MOTO
// ------------------------------------------------------------
class Moto extends Vehiculo {

    public Moto(String marca, int modelo, String patente, int tipo, int kilometraje) {
        super(marca, modelo, patente, tipo, kilometraje);
    }

    // Costo de mantenimiento propio de mot
    @Override
    public double calcularCostoMantenimiento() {
        return 3000 + (getKilometraje() * 0.05);
    }

    @Override
    public String toString() {
        return "[MOTO] " + super.toString();
    }
}

// ------------------------------------------------------------
// SUBCLASE COLECTIVO
// ------------------------------------------------------------
class Colectivo extends Vehiculo {

    // Atributo propio del colectivo
    private int cantAsientos;

    // Constructor recibe todo
    public Colectivo(String marca, int modelo, String patente, int tipo, int kilometraje, int cantAsientos) {
        super(marca, modelo, patente, tipo, kilometraje);
        this.cantAsientos = cantAsientos;
    }

    // Costo de mantenimiento específico
    @Override
    public double calcularCostoMantenimiento() {
        return 12000 + (getKilometraje() * 0.2);
    }

    @Override
    public String toString() {
        return "[COLECTIVO] " + super.toString() + ", Asientos: " + cantAsientos;
    }
}

// ------------------------------------------------------------
// CLASE PRINCIPAL (MAIN)
// ------------------------------------------------------------
public class Main {

    // ArrayList global que almacena todos los vehículos
    static ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    // Scanner global para leer desde consola
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Variable para guardar la opción elegida por el usuario
        int opcion;

        // Bucle principal del menú
        do {

            // Muestro menú en consola
            System.out.println("\n--- SISTEMA CRUD DE VEHÍCULOS ---");
            System.out.println("1. Crear vehículo");
            System.out.println("2. Buscar vehículo (recursivo)");
            System.out.println("3. Modificar vehículo");
            System.out.println("4. Eliminar vehículo");
            System.out.println("5. Mostrar todos");
            System.out.println("6. Contar vehículos con KM > X (recursivo)");
            System.out.println("7. Salir");

            System.out.print("Opción: ");

            // Leo la opción
            opcion = sc.nextInt();

            // Switch con todas las funciones
            switch (opcion) {

                case 1 -> crearVehiculo();         // Alta

                case 2 -> buscarRecursivoWrapper(); // Búsqueda recursiva

                case 3 -> modificarVehiculo();     // Editar

                case 4 -> eliminarVehiculo();      // Baja

                case 5 -> mostrarVehiculos();      // Listar

                case 6 -> contarWrapper();         // Contar recursivo

                case 7 -> System.out.println("Nos fuimooo..."); // Salir

                default -> System.out.println("Opción inválida"); // Error
            }

        // Repito hasta que la opción sea 7
        } while (opcion != 7);
    }

    // ------------------------------------------------------------
    // CREAR VEHÍCULO (CORREGIDO + COMENTADO)
    // ------------------------------------------------------------
    public static void crearVehiculo() {
        try {
            sc.nextLine(); // Limpio el buffer

            System.out.print("Marca: ");
            String marca = sc.nextLine(); // nextLine para permitir espacios

            System.out.print("Modelo: ");
            int modelo = sc.nextInt();
            sc.nextLine(); // Limpio buffer

            System.out.print("Patente: ");
            String patente = sc.nextLine().toUpperCase(); // Normalizo

            if (patente.length() < 6)
                throw new PatenteInvalidaException("Patente muy corta!");

            System.out.print("Tipo (1=Auto,2=Moto,3=Colectivo): ");
            int tipo = sc.nextInt();

            System.out.print("Kilometraje: ");
            int km = sc.nextInt();
            sc.nextLine();

            Vehiculo v = null; // Variable donde guardo el vehículo creado

            // Según el tipo, creo la subclase correcta
            if (tipo == 1) {

                System.out.print("Puertas: ");
                int p = sc.nextInt();
                sc.nextLine();
                v = new Auto(marca, modelo, patente, tipo, km, p);

            } else if (tipo == 2) {

                v = new Moto(marca, modelo, patente, tipo, km);

            } else {

                System.out.print("Asientos: ");
                int a = sc.nextInt();
                sc.nextLine();
                v = new Colectivo(marca, modelo, patente, tipo, km, a);
            }

            // Agrego el vehículo al ArrayList global
            vehiculos.add(v);

            System.out.println("Vehículo agregado correctamente!");

        } catch (PatenteInvalidaException e) {
            System.out.println("ERROR: " + e.getMessage());

        } catch (InputMismatchException e) {
            System.out.println("Error de entrada.");
            sc.nextLine(); // Limpio buffer
        }
    }

    // ------------------------------------------------------------
    // BUSCAR VEHÍCULO (WRAPPER + RECURSIÓN)
    // ------------------------------------------------------------
    public static void buscarRecursivoWrapper() {

        System.out.print("Patente: ");
        String pat = sc.next();

        // Llamo al método recursivo empezando desde índice 0
        Vehiculo res = buscarRecursivo(pat.toUpperCase(), 0);

        // Si no es null, lo encontré
        if (res != null)
            System.out.println("Encontrado: " + res);
        else
            System.out.println("No existe esa patente.");
    }

    public static Vehiculo buscarRecursivo(String pat, int i) {

        // Caso base: llego al final de la lista
        if (i == vehiculos.size()) return null;

        // Si la patente coincide, lo retorno
        if (vehiculos.get(i).getPatente().equals(pat))
            return vehiculos.get(i);

        // Llamado recursivo al siguiente índice
        return buscarRecursivo(pat, i + 1);
    }

    // ------------------------------------------------------------
    // MODIFICAR VEHÍCULO (PASO POR REFERENCIA)
    // ------------------------------------------------------------
    public static void modificarVehiculo() {

        System.out.print("Patente: ");
        String p = sc.next();

        // Busco el vehículo
        Vehiculo v = buscarRecursivo(p.toUpperCase(), 0);

        if (v == null) {
            System.out.println("No existe!");
            return;
        }

        System.out.print("Nuevo KM: ");
        int km = sc.nextInt();

        // Aquí se demuestra paso por referencia:
        v.setKilometraje(km);

        System.out.println("Modificado!");
    }

    // ------------------------------------------------------------
    // ELIMINAR VEHÍCULO (removeIf + lambda)
    // ------------------------------------------------------------
    public static void eliminarVehiculo() {

        System.out.print("Patente: ");
        String p = sc.next();

        // Lambda que compara la patente recibida
        Predicate<Vehiculo> pred = v -> v.getPatente().equals(p.toUpperCase());

        // removeIf devuelve true si eliminó algo
        boolean removed = vehiculos.removeIf(pred);

        System.out.println(removed ? "Eliminado!" : "No existe!");
    }

    // ------------------------------------------------------------
    // MOSTRAR VEHÍCULOS (lambda forEach)
    // ------------------------------------------------------------
    public static void mostrarVehiculos() {

        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos cargados.");
            return;
        }

        // Recorro toda la lista con una lambda
        vehiculos.forEach(v -> v.mostrar());
    }

    // ------------------------------------------------------------
    // CONTAR VEHÍCULOS CON KM > X (RECURSIVO)
    // ------------------------------------------------------------
    public static void contarWrapper() {

        System.out.print("KM mínimo: ");
        int km = sc.nextInt();

        // Llamo al método recursivo
        int c = contarRecursivo(km, 0);

        System.out.println("Cantidad: " + c);
    }

    public static int contarRecursivo(int km, int i) {

        // Caso base
        if (i == vehiculos.size()) return 0;

        // Sumo 1 si cumple la condición
        int suma = vehiculos.get(i).getKilometraje() > km ? 1 : 0;

        // Llamo al siguiente
        return suma + contarRecursivo(km, i + 1);
    }
}
