package Parciales.Parcial2025.Segundo.Veterinaria;

import java.util.ArrayList;
import java.util.List;

// ==================================================================================
// EJERCICIO 1: LA INTERFAZ (Contrato de servicios o cobros)
// ==================================================================================
public interface Cuidados {
    /**
     * Calcula el costo de cuidado del animal basado en el costo base y la edad
     * actual
     *
     */
    double calcularCostoCuidado(double costoBase, int edadAnimal);
}

// ==================================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Checked)
// ==================================================================================
class PesoInsuficienteException extends Exception {

    public PesoInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public PesoInsuficienteException() {
        super("El peso del animal es insuficiente para la especie o menor al mínimo requerido.");
    }
}

// ==================================================================================
// CLASE PADRE: ANIMALITO
// Aquí resolvemos el EJERCICIO 3 (Equals y HashCode por Nombre)
// ==================================================================================
abstract class Animalito {
    protected String especie;
    protected int edad;
    protected String nombre;
    protected double peso;

    // El constructor lanza la excepción requerida por las clases hijas
    public Animalito(String especie, int edad, String nombre, double peso)
            throws PesoInsuficienteException { 
        // Validación básica (solo para demostrar el uso de la excepción)
        if (peso <= 0) {
            throw new PesoInsuficienteException("El peso debe ser un valor positivo.");
        }
        
        this.especie = especie.toUpperCase();
        this.edad = edad;
        this.nombre = nombre.toUpperCase();
        this.peso = peso;
    }

    // Getters
    public String getEspecie() { return especie; }
    public int getEdad() { return edad; }
    public String getNombre() { return nombre; }
    public double getPeso() { return peso; }
    public abstract String verTipoDeAnimal();

    // --- EJERCICIO 3: IGUALDAD BASADA EN NOMBRE ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Animalito animalito = (Animalito) obj;
        return nombre.equals(animalito.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }
    
    // Método toString
    @Override
    public String toString() {
        return especie + "\t" + edad + " años\t" + nombre + "\t" + peso + "Kg";
    }
}

// ==================================================================================
// CLASE AVICOLAS (Implementa Ejercicio 1 - Cuidados)
// ==================================================================================
class Avicolas extends Animalito implements Cuidados {
    private String tipoPlumaje;

    public Avicolas(String especie, int edad, String nombre, double peso, String tipoPlumaje)
            throws PesoInsuficienteException {
        super(especie, edad, nombre, peso); 

        // Validación específica
        if (peso < 1.0) {
            throw new PesoInsuficienteException(
                "Error: Un animal avícola debe tener al menos 1kg de peso. Peso recibido: " + peso + "kg");
        }

        this.tipoPlumaje = tipoPlumaje;
    }

    // Getter
    public String getTipoPlumaje() {
        return tipoPlumaje;
    }

    // --- EJERCICIO 1: IMPLEMENTACIÓN DE INTERFAZ CUIDADOS ---
    @Override
    public double calcularCostoCuidado(double costoBase, int edadAnimal) {
        double incrementoEdad = this.edad * 0.05; 
        double porcentajePlumaje;
        
        if (tipoPlumaje.equalsIgnoreCase("EXOTICO")) {
            porcentajePlumaje = 0.4; 
        } else if (tipoPlumaje.equalsIgnoreCase("COLORIDO")) {
            porcentajePlumaje = 0.3; 
        } else {
            porcentajePlumaje = 0.2; 
        }

        double costoConEdad = costoBase * (1 + incrementoEdad);
        return costoConEdad * (1 + porcentajePlumaje);
    }

    // Setter
    public void setTipoPlumaje(String tipoPlumaje) {
        this.tipoPlumaje = tipoPlumaje;
    }

    @Override
    public String verTipoDeAnimal() {
        return "🐦";
    }

    // Método toString
    @Override
    public String toString() {
        return super.toString() + "\t" + tipoPlumaje + " plumaje";
    }
}

// ==================================================================================
// NUEVA CLASE REQUERIDA: CACERAS
// Implementación hipotética del Ejercicio 1 (Interface Cuidados)
// Lógica Hipotética: Costo base + Descuento 15% por peso (son más baratos de alimentar)
// ==================================================================================
class Caceras extends Animalito implements Cuidados {
    private double longitudCola; // Atributo para diferenciar Caceras

    public Caceras(String especie, int edad, String nombre, double peso, double longitudCola)
            throws PesoInsuficienteException {
        super(especie, edad, nombre, peso);
        this.longitudCola = longitudCola;
    }

    // --- IMPLEMENTACIÓN HIPOTÉTICA DE CUIDADOS ---
    @Override
    public double calcularCostoCuidado(double costoBase, int edadAnimal) {
        // Lógica: Descuento base del 15% por ser cazador (son más autosuficientes)
        double descuentoBase = 0.15;
        
        // Recargo adicional si la cola es larga (ej. requiere más espacio o cuidado)
        double recargoCola = (this.longitudCola > 0.5) ? 0.20 : 0.0;
        
        // Costo Final = Costo Base * (1 - Descuento Base) * (1 + Recargo Cola)
        double costoIntermedio = costoBase * (1 - descuentoBase);
        return costoIntermedio * (1 + recargoCola);
    }

    @Override
    public String verTipoDeAnimal() {
        return "🐺"; // Usamos un lobo/depredador como icono
    }
    
    // Método toString
    @Override
    public String toString() {
        return super.toString() + "\t" + longitudCola + "m cola";
    }
}


// ==================================================================================
// EJERCICIO 4: BÚSQUEDA (INVENTARIO) - VERSIÓN FINAL
// Reemplaza FichaMedica.
// ==================================================================================
public class Inventario {

    private ArrayList<Avicolas> avicolas;
    private ArrayList<Caceras> caceras;

    public Inventario() {
        this.avicolas = new ArrayList<>();
        this.caceras = new ArrayList<>();
    }

    // ================ MÉTODOS CRUD (Omitidos para brevedad) ================

    public boolean agregarAvicola(Avicolas avicola) {
      if (!avicolas.stream().anyMatch(a -> a.getNombre().equals(avicola.getNombre()))) {
        avicolas.add(avicola);
        return true;
      }
      return false; 
    }

    public boolean agregarCacera(Caceras cacera) {
      if (!caceras.stream().anyMatch(c -> c.getNombre().equals(cacera.getNombre()))) {
        caceras.add(cacera);
        return true;
      }
      return false; 
    }
    
    // --- LÓGICA DE BÚSQUEDA DEL EJERCICIO 4 ---
    public Animalito buscarAnimal(String nombre) {
        String nombreBuscado = nombre.toUpperCase(); // Asegurar mayúsculas

        // 1. Buscar en la lista de AVÍCOLAS
        for (Avicolas avicola : avicolas) {
            if (avicola.getNombre().equals(nombreBuscado)) {
                return avicola;
            }
        }

        // 2. Buscar en la lista de CACERAS
        for (Caceras cacera : caceras) {
            if (cacera.getNombre().equals(nombreBuscado)) {
                return cacera;
            }
        }

        // 3. Si no se encontró en ninguna lista
        return null;
    }

    // Métodos restantes de Inventario (Omitidos para brevedad, pero presentes en el código)
    
    public void listarTodosLosAnimales() {
        System.out.println("=== INVENTARIO DE ANIMALES ===\n");

        System.out.println("\nAVÍCOLAS (" + avicolas.size() + "):");
        avicolas.forEach(avicola -> System.out.println(avicola.toString()));

        System.out.println("\nCAZADORES (" + caceras.size() + "):");
        caceras.forEach(cacera -> System.out.println(cacera.toString()));

        System.out.println("\nTotal de animales: " + getCantidadDeAnimales());
    }
    
    public String getCantidadDeAnimales() {
        return "Total de animales: " + (avicolas.size() + caceras.size());
    }

    public boolean existeAnimal(String nombre) {
        return buscarAnimal(nombre) != null;
    }
}
