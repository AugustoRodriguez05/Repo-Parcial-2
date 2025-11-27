package Parciales.Parcial2025.Segundo.Concesionario;

import java.util.ArrayList;
import java.util.List;

// ==================================================================================
// EJERCICIO 1: LA INTERFAZ (Contrato de ventas)
// ==================================================================================
interface Ventas {
    double calcularPrecioVenta(double precioBase, int anioActual);
}

// ==================================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Checked)
// Requerida por la clase Auto para validar puertas.
// ==================================================================================
class PuertasInsuficientesException extends Exception {
    public PuertasInsuficientesException(String mensaje) {
        super(mensaje);
    }
}

// ==================================================================================
// CLASE PADRE: VEHICULO
// Aquí resolvemos el EJERCICIO 3 (Equals y HashCode por Patente)
// ==================================================================================
abstract class Vehiculo {
    protected String marca;
    protected int modelo; // Año
    protected String patente;
    protected int kilometraje;

    public Vehiculo(String marca, int modelo, String patente, int kilometraje) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.kilometraje = kilometraje;
    }

    public String getPatente() { return patente; }
    public int getModelo() { return modelo; } // Necesario para calcular años de uso

    public abstract String verTipoDeVehiculo();

    // --- EJERCICIO 3: IGUALDAD BASADA EN PATENTE ---
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehiculo)) return false;
        Vehiculo otro = (Vehiculo) obj;
        return this.patente != null && this.patente.equals(otro.patente);
    }

    @Override
    public int hashCode() {
        return this.patente != null ? this.patente.hashCode() : 0;
    }
    
    @Override
    public String toString() {
        // Nota: Este toString se usa si las clases hijas no lo sobrescriben
        return verTipoDeVehiculo() + " " + marca + " " + patente;
    }
}

// ==================================================================================
// CLASE AUTO
// Usa la Excepción (Ejercicio 2) e implementa Ventas
// ==================================================================================
class Auto extends Vehiculo implements Ventas {
    private int cantPuertas;

    public Auto(String marca, int modelo, String patente, int kilometraje, int cantPuertas)
            throws PuertasInsuficientesException {
        super(marca, modelo, patente, kilometraje);
        
        // Validación del Ejercicio 2
        if (cantPuertas < 3) {
            throw new PuertasInsuficientesException("Un auto debe tener al menos 3 puertas.");
        }
        this.cantPuertas = cantPuertas;
    }

    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int aniosUso = anioActual - this.modelo;
        double precio = precioBase - (precioBase * (aniosUso * 0.05)); // Depreciación 5%
        
        // Lógica de puertas
        if (cantPuertas == 3) precio *= 1.30;
        else if (cantPuertas == 4) precio *= 1.40;
        else precio *= 1.35;
        
        return precio;
    }

    @Override
    public String verTipoDeVehiculo() { return "🚗 Auto"; }
}

// ==================================================================================
// CLASE MOTO (Nueva Versión Solicitada)
// Implementación del Ejercicio 1 (Interface Ventas) con lógica 8% + 10%.
// ==================================================================================
class Moto extends Vehiculo implements Ventas {
    // Nota: Esta nueva versión ya no usa el campo 'cilindrada'.

    // Constructor con parámetros
    public Moto(String marca, int modelo, String patente, int kilometraje) {
        super(marca, modelo, patente, kilometraje);
    }

    // --- EJERCICIO 1: IMPLEMENTACIÓN DE INTERFAZ ---
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        // 1. Calcular años de uso
        int aniosDeUso = anioActual - this.modelo;
        
        // 2. Depreciación: 8% por año para motos (depreciación rápida)
        double depreciacion = aniosDeUso * 0.08; 

        // 3. Descuento fijo (Descuento extra del 10%)
        double descuentoMoto = 0.1; 

        // 4. Aplicar la depreciación
        double precioConDepreciacion = precioBase * (1 - depreciacion);
        
        // 5. Aplicar el descuento fijo al precio ya depreciado
        double precioFinal = precioConDepreciacion * (1 - descuentoMoto);
        
        return Math.max(precioFinal, 0); // Asegurar que no sea negativo
    }

    // Implementación del método abstracto
    @Override
    public String verTipoDeVehiculo() {
        return "🏍️";
    }

    // Método toString
    @Override
    public String toString() {
        // Usamos el formato solicitado: marca + "'\t" + modelo + "\t" + patente + "\t" + kilometraje + "Km"
        return marca + "'\t" + modelo + "\t" + patente + "\t" + kilometraje + "Km";
    }
}

// ==================================================================================
// CLASE INVENTARIO (Tu código, integrando el Ejercicio 4)
// ==================================================================================
class Inventario {

    private ArrayList<Auto> autos;
    private ArrayList<Moto> motos;

    public Inventario() {
        this.autos = new ArrayList<>();
        this.motos = new ArrayList<>();
    }

    // ================ MÉTODOS CRUD PARA AUTOS ================
    public boolean agregarAuto(Auto auto) {
        // Usa el equals implícitamente o compara patentes como haces aquí
        if (!autos.stream().anyMatch(a -> a.getPatente().equals(auto.getPatente()))) {
            autos.add(auto);
            return true;
        }
        return false;
    }

    public boolean actualizarAuto(String patente, Auto autoActualizado) {
        for (int i = 0; i < autos.size(); i++) {
            if (autos.get(i).getPatente().equals(patente)) {
                autos.set(i, autoActualizado);
                return true;
            }
        }
        return false;
    }

    // ================ MÉTODOS CRUD PARA MOTOS ================
    public boolean agregarMoto(Moto moto) {
        if (!motos.stream().anyMatch(m -> m.getPatente().equals(moto.getPatente()))) {
            motos.add(moto);
            return true;
        }
        return false;
    }

    public boolean actualizarMoto(String patente, Moto motoActualizada) {
        for (int i = 0; i < motos.size(); i++) {
            if (motos.get(i).getPatente().equals(patente)) {
                motos.set(i, motoActualizada);
                return true;
            }
        }
        return false;
    }

    // ================ MÉTODOS DE CONSULTA (Ejercicio 4) ================
    
    // Este es el método CLAVE del Ejercicio 4: Buscar en dos listas
    public Vehiculo buscarVehiculo(String patente) {
        // 1. Busca en autos
        for (Auto auto : autos) {
            if (auto.getPatente().equals(patente)) {
                return auto;
            }
        }
        // 2. Busca en motos
        for (Moto moto : motos) {
            if (moto.getPatente().equals(patente)) {
                return moto;
            }
        }
        // 3. Retorna null
        return null;
    }

    public boolean eliminarVehiculo(String patente) {
        boolean eliminadoDeAutos = autos.removeIf(auto -> auto.getPatente().equals(patente));
        boolean eliminadoDeMotos = motos.removeIf(moto -> moto.getPatente().equals(patente));
        return eliminadoDeAutos || eliminadoDeMotos;
    }

    public String getCantidadDeVehiculos() {
        return "Total de vehículos: " + (autos.size() + motos.size());
    }

    public void listarTodosLosVehiculos() {
        System.out.println("=== INVENTARIO DE VEHÍCULOS ===");
        System.out.println("\nAUTOS (" + autos.size() + "):");
        autos.forEach(auto -> System.out.println(auto.toString()));
        System.out.println("\nMOTOS (" + motos.size() + "):");
        motos.forEach(moto -> System.out.println(moto.toString()));
        System.out.println("\n" + getCantidadDeVehiculos());
    }

    public boolean existeVehiculo(String patente) {
        return buscarVehiculo(patente) != null;
    }
}
