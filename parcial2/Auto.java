package Parciales.Parcial2025.Segundo.Concesionario;

import java.util.ArrayList;
import java.util.List;

// ==================================================================================
// EJERCICIO 1: LA INTERFAZ
// Definimos el contrato "Ventas". Cualquier vehículo vendible debe saber calcular su precio.
// ==================================================================================
interface Ventas {
    double calcularPrecioVenta(double precioBase, int anioActual);
}

// ==================================================================================
// EJERCICIO 2: LA EXCEPCIÓN (Checked)
// Excepción personalizada para validar las puertas del Auto.
// ==================================================================================
class PuertasInsuficientesException extends Exception {
    
    // Constructor con mensaje personalizado (Usado en el código que me pasaste)
    public PuertasInsuficientesException(String mensaje) {
        super(mensaje);
    }
    
    // Constructor por defecto (Buena práctica tenerlo)
    public PuertasInsuficientesException() {
        super("El vehículo no tiene la cantidad de puertas necesaria.");
    }
}

// ==================================================================================
// CLASE PADRE: VEHICULO
// Aquí resolvemos el EJERCICIO 3 (Equals y HashCode)
// ==================================================================================
abstract class Vehiculo {
    protected String marca;
    protected int modelo; // Año del modelo
    protected String patente;
    protected int kilometraje;

    public Vehiculo(String marca, int modelo, String patente, int kilometraje) {
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.kilometraje = kilometraje;
    }

    public String getPatente() { return patente; }
    public abstract String verTipoDeVehiculo();

    // --- EJERCICIO 3: ANALIZAR LA IGUALDAD ---
    // Dos vehículos son iguales únicamente si tienen la misma patente.
    @Override
    public boolean equals(Object obj) {
        // 1. Si es el mismo objeto en memoria, son iguales.
        if (this == obj) return true;
        
        // 2. Si el objeto comparado no es un Vehículo (o hijo), no son iguales.
        if (!(obj instanceof Vehiculo)) return false;
        
        // 3. Convertimos (Casting) para poder leer la patente.
        Vehiculo otro = (Vehiculo) obj;
        
        // 4. Comparamos las patentes (Strings).
        return this.patente != null && this.patente.equals(otro.patente);
    }

    // Siempre que se sobrescribe equals, se debe sobrescribir hashCode.
    @Override
    public int hashCode() {
        return this.patente != null ? this.patente.hashCode() : 0;
    }
}

// ==================================================================================
// CLASE AUTO (Código que me pasaste, integrado con la estructura)
// Implementa Ventas y usa la Excepción.
// ==================================================================================
class Auto extends Vehiculo implements Ventas {
    private int cantPuertas;

    public Auto(String marca, int modelo, String patente, int kilometraje, int cantPuertas)
            throws PuertasInsuficientesException {
        super(marca, modelo, patente, kilometraje);

        // EJERCICIO 2: Uso de la excepción en la validación
        if (cantPuertas < 3) {
            throw new PuertasInsuficientesException(
                "Error: Un auto debe tener al menos 3 puertas. Puertas recibidas: " + cantPuertas);
        }
        this.cantPuertas = cantPuertas;
    }

    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int aniosDeUso = anioActual - this.modelo;
        double depreciacion = aniosDeUso * 0.05; // 5% por año

        double porcentajePuertas;
        if (cantPuertas == 3) porcentajePuertas = 0.3;
        else if (cantPuertas == 4) porcentajePuertas = 0.4;
        else porcentajePuertas = 0.35;

        double precioConDepreciacion = precioBase * (1 - depreciacion);
        return precioConDepreciacion * (1 + porcentajePuertas);
    }

    @Override
    public String verTipoDeVehiculo() { return "🚗 Auto"; }
}

// ==================================================================================
// CLASE MOTO (Adaptación del ejercicio de la Laptop)
// Implementa la lógica de descuentos acumulados (12% + 15%).
// ==================================================================================
class Moto extends Vehiculo implements Ventas {
    private int cilindrada;

    public Moto(String marca, int modelo, String patente, int kilometraje, int cilindrada) {
        super(marca, modelo, patente, kilometraje);
        this.cilindrada = cilindrada;
    }

    // --- LÓGICA DEL EJERCICIO 1 (ADAPTADA A MOTO) ---
    // "Implemente el método requerido por la interface..."
    @Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        // 1. Calcular años de uso
        int aniosUso = anioActual - this.modelo;

        // 2. Depreciación: 12% por cada año de uso (Igual que el ejercicio de Laptop)
        double porcentajeDepreciacion = 0.12 * aniosUso;

        // 3. Descuento adicional: 15% por "desgaste típico" (o cilindrada en este caso)
        double porcentajeDesgaste = 0.15;

        // 4. Sumamos todos los porcentajes a descontar
        double descuentoTotal = porcentajeDepreciacion + porcentajeDesgaste;

        // 5. Aplicamos el descuento al precio base
        // Formula: PrecioBase - (PrecioBase * DescuentoTotal)
        double precioFinal = precioBase - (precioBase * descuentoTotal);

        // Validamos que no sea negativo
        return precioFinal > 0 ? precioFinal : 0;
    }

    @Override
    public String verTipoDeVehiculo() { return "🏍️ Moto"; }
}

// ==================================================================================
// EJERCICIO 4: BUSCAR ELEMENTO (CONCESIONARIO)
// Buscar en lista Autos -> Si no está, buscar en lista Motos -> Si no, null.
// ==================================================================================
class Concesionario {
    private List<Auto> autos = new ArrayList<>();
    private List<Moto> motos = new ArrayList<>();

    // Métodos auxiliares para llenar las listas
    public void agregarAuto(Auto a) { autos.add(a); }
    public void agregarMoto(Moto m) { motos.add(m); }

    // --- LÓGICA DE BÚSQUEDA ---
    public Vehiculo buscarVehiculo(String patenteBuscada) {
        // 1. Primero buscamos en la lista de AUTOS
        for (Auto a : autos) {
            // Usamos equals (o comparamos Strings directamente)
            if (a.getPatente().equals(patenteBuscada)) {
                return a; // ¡Encontrado en autos!
            }
        }

        // 2. Si no apareció, buscamos en la lista de MOTOS
        for (Moto m : motos) {
            if (m.getPatente().equals(patenteBuscada)) {
                return m; // ¡Encontrado en motos!
            }
        }

        // 3. Si no está en ninguna, retornamos null
        return null;
    }
}
