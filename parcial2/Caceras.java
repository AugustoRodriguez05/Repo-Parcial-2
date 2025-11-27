package Parciales.Parcial2025.Segundo.Veterinaria;

import java.util.ArrayList;
import java.util.List;

// ==================================================================================
// EJERCICIO 1: LA INTERFAZ (Contrato de servicios o cobros)
// Usamos la interfaz "Cuidados" requerida por la nueva clase Avicolas
// ==================================================================================
interface Cuidados {
    /**
     * Calcula el costo total del servicio, basado en un costo base y la edad del animal.
     */
    double calcularCostoCuidado(double costoBase, int edadAnimal);
}

// ==================================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Checked)
// Adaptamos la excepción PesoInvalido a PesoInsuficiente, según la nueva clase Avicolas
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
// NUEVA CLASE: AVICOLAS (Proporcionada por el usuario)
// Implementación del EJERCICIO 1 (Interface Cuidados)
// ==================================================================================
class Avicolas extends Animalito implements Cuidados {
    private String tipoPlumaje;

    public Avicolas(String especie, int edad, String nombre, double peso, String tipoPlumaje)
            throws PesoInsuficienteException {
        // Llama al constructor de Animalito y su validación general
        super(especie, edad, nombre, peso); 

        // Validación específica del enunciado: Avícolas deben pesar al menos 1kg
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
        // Calcular incremento por años (animales mayores necesitan más cuidado)
        double incrementoEdad = this.edad * 0.05; // 5% por año

        // Calcular porcentaje adicional según tipo de plumaje
        double porcentajePlumaje;
        if (tipoPlumaje.equalsIgnoreCase("EXOTICO")) {
            porcentajePlumaje = 0.4; // 40%
        } else if (tipoPlumaje.equalsIgnoreCase("COLORIDO")) {
            porcentajePlumaje = 0.3; // 30%
        } else {
            porcentajePlumaje = 0.2; // 20%
        }

        // Calcular costo final
        // Costo con Edad = Costo Base * (1 + Incremento por Edad)
        double costoConEdad = costoBase * (1 + incrementoEdad);
        
        // Costo Final = Costo con Edad * (1 + Porcentaje por Plumaje)
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
// CLASES COMPLEMENTARIAS (Perro y Gato, adaptadas para usar la nueva excepción)
// ==================================================================================
// NOTA: La clase Perro e interfaz Cobros han sido reemplazadas por Avicolas e Cuidados
// Si el parcial pide mantenerlas, deberías renombrar las interfaces/excepciones originales.
// Por simplicidad, adaptaremos Perro/Gato para usen la nueva excepción.

class Perro extends Animalito {
    public Perro(String nombre, int edad, double peso) 
            throws PesoInsuficienteException {
        super("PERRO", edad, nombre, peso);
    }
    
    // Si la interfaz "Cobros" se mantiene, el método debe ser reubicado o eliminado
    // Aquí solo mantenemos la estructura básica.

    @Override
    public String verTipoDeAnimal() {
        return "🐶";
    }
}

class Gato extends Animalito {
    public Gato(String nombre, int edad, double peso) 
            throws PesoInsuficienteException {
        super("GATO", edad, nombre, peso);
    }

    @Override
    public String verTipoDeAnimal() {
        return "🐱";
    }
}

// ==================================================================================
// EJERCICIO 4: BÚSQUEDA (Ficha Médica)
// Consigna: Buscar por nombre, primero en Perros, luego en Gatos, luego en Avicolas
// ==================================================================================
class FichaMedica {
    private List<Perro> perros = new ArrayList<>();
    private List<Gato> gatos = new ArrayList<>();
    private List<Avicolas> avicolas = new ArrayList<>(); // Nueva lista

    public void registrarPerro(Perro p) { perros.add(p); }
    public void registrarGato(Gato g) { gatos.add(g); }
    public void registrarAvicola(Avicolas a) { avicolas.add(a); } // Nuevo método

    // --- LÓGICA DE BÚSQUEDA DEL EJERCICIO 4 (Actualizada para 3 listas) ---
    public Animalito buscarAnimal(String nombre) {
        String nombreBuscado = nombre.toUpperCase();
        
        // 1. Buscar en la lista de PERROS
        for (Perro p : perros) {
            if (p.getNombre().equals(nombreBuscado)) { return p; }
        }
        
        // 2. Buscar en la lista de GATOS
        for (Gato g : gatos) {
            if (g.getNombre().equals(nombreBuscado)) { return g; }
        }
        
        // 3. Buscar en la lista de AVICOLAS
        for (Avicolas a : avicolas) {
            if (a.getNombre().equals(nombreBuscado)) { return a; }
        }
        
        // 4. Si no se encontró en ninguna lista
        return null;
    }
}
