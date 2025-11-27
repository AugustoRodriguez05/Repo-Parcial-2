package Parciales.Parcial2025.Segundo.Veterinaria;

import java.util.ArrayList;
import java.util.List;

// ==================================================================================
// EJERCICIO 1: LA INTERFAZ (Contrato de servicios o cobros)
// ==================================================================================
interface Cobros {
    /**
     * Calcula el costo total del servicio (ej. consulta + vacunas).
     */
    double calcularCostoServicio();
}

// ==================================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Checked)
// Consigna hipotética: Excepción al registrar un animal con peso inválido.
// ==================================================================================
class PesoInvalidoException extends Exception {

    public PesoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public PesoInvalidoException() {
        super("El peso del animal no puede ser negativo o exceder el límite de la especie.");
    }
}

// ==================================================================================
// CLASE PADRE: ANIMALITO (El código que me pasaste)
// Aquí resolvemos el EJERCICIO 3 (Equals y HashCode por Nombre)
// ==================================================================================
abstract class Animalito {
    protected String especie;
    protected int edad;
    protected String nombre;
    protected double peso;

    public Animalito(String especie, int edad, String nombre, double peso)
            throws PesoInvalidoException {
        // Validación hipotética usando la nueva excepción
        if (peso <= 0) {
            throw new PesoInvalidoException("El peso debe ser un valor positivo.");
        }
        
        this.especie = especie.toUpperCase();
        this.edad = edad;
        this.nombre = nombre.toUpperCase();
        this.peso = peso;
    }

    // Getters y Setters (simplificados para el Canvas)
    public String getEspecie() { return especie; }
    public int getEdad() { return edad; }
    public String getNombre() { return nombre; }
    public double getPeso() { return peso; }
    public abstract String verTipoDeAnimal();

    // --- EJERCICIO 3: IGUALDAD BASADA EN NOMBRE ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Condición estricta (getClass()) como la que usaste en Vehiculo.
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Animalito animalito = (Animalito) obj;
        // La igualdad se basa UNICAMENTE en el nombre
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
// CLASE PERRO
// Implementación del EJERCICIO 1 (Interface Cobros)
// Consigna: Costo base + recargo del 5% por cada año de vida.
// ==================================================================================
class Perro extends Animalito implements Cobros {
    private double costoBaseConsulta = 50.0;
    
    public Perro(String nombre, int edad, double peso) 
            throws PesoInvalidoException {
        // Especie fija: PERRO
        super("PERRO", edad, nombre, peso);
    }
    
    // --- EJERCICIO 1: IMPLEMENTACIÓN DE INTERFAZ ---
    @Override
    public double calcularCostoServicio() {
        // Recargo: 5% por cada año de vida
        double recargoPorEdad = this.edad * 0.05;
        
        // Costo Total = Costo Base * (1 + Recargo Total)
        return this.costoBaseConsulta * (1 + recargoPorEdad);
    }

    @Override
    public String verTipoDeAnimal() {
        return "🐶";
    }
}

// CLASE GATO (Necesaria para la búsqueda)
class Gato extends Animalito {
    public Gato(String nombre, int edad, double peso) 
            throws PesoInvalidoException {
        super("GATO", edad, nombre, peso);
    }

    @Override
    public String verTipoDeAnimal() {
        return "🐱";
    }
}

// ==================================================================================
// EJERCICIO 4: BÚSQUEDA (Inventario/Ficha Médica)
// Consigna: Buscar por nombre, primero en lista de Perros, luego en Gatos.
// ==================================================================================
class FichaMedica {
    private List<Perro> perros = new ArrayList<>();
    private List<Gato> gatos = new ArrayList<>();
    
    public void registrarPerro(Perro p) { perros.add(p); }
    public void registrarGato(Gato g) { gatos.add(g); }

    // --- LÓGICA DE BÚSQUEDA DEL EJERCICIO 4 ---
    public Animalito buscarAnimal(String nombre) {
        String nombreBuscado = nombre.toUpperCase();
        
        // 1. Buscar en la lista de PERROS
        for (Perro p : perros) {
            if (p.getNombre().equals(nombreBuscado)) {
                return p; // Encontrado!
            }
        }
        
        // 2. Si no se encontró, buscar en la lista de GATOS
        for (Gato g : gatos) {
            if (g.getNombre().equals(nombreBuscado)) {
                return g; // Encontrado!
            }
        }
        
        // 3. Si no se encontró en ninguna lista
        return null;
    }
}
