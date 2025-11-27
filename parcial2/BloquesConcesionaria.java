1️.Para el Ejercicio de la Excepción (Ejercicio 2)

A. Crear la clase de la excepción: Copia esto si te piden crear PuertasInsuficientesException.
Java

public class PuertasInsuficientesException extends Exception {
    public PuertasInsuficientesException(String mensaje) {
        super(mensaje);
    }

    public PuertasInsuficientesException() {
        super("Error: Un auto debe tener al menos 3 puertas.");
    }
}

B. Usar la excepción en el Constructor: Copia esto dentro del constructor de la clase Auto.
Java

        // Validación requerida
        if (cantPuertas < 3) {
            throw new PuertasInsuficientesException("Error: Auto requiere al menos 3 puertas. Recibido: " + cantPuertas);
        }

2️.Para el Ejercicio de la Interface / Precios (Ejercicio 1)

A. En la clase Auto: Copia este método dentro de la clase Auto.
Java

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

B. En la clase Moto: Copia este método dentro de la clase Moto.
Java

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

3.Para el Ejercicio de Igualdad / Equals (Ejercicio 3)

Copia estos dos métodos dentro de la clase padre Vehiculo.
Java

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Validación estricta (getClass)
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehiculo otro = (Vehiculo) obj;
        return patente.equals(otro.getPatente());
    }

    @Override
    public int hashCode() {
        return patente.hashCode();
    }

4.Para el Ejercicio de Búsqueda (Ejercicio 4)

Copia este método dentro de la clase Inventario.
Java

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
