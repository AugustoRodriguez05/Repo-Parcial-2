-Para el Ejercicio de la Excepción (Ejercicio 2)
A. Crear la clase de la excepción: Copia esto si te piden crear la clase PuertosInsuficientesException.

public class PuertosInsuficientesException extends Exception {
    public PuertosInsuficientesException(String mensaje) {
        super(mensaje);
    }

    public PuertosInsuficientesException() {
        super("Error: Un escritorio debe tener al menos 5 puertos");
    }
}

B. Usar la excepción en el Constructor: Copia esto dentro del constructor de la clase Escritorio.
// Validar puertos (Ejercicio 2)
        if (cantidadPuertos < 5) {
            throw new PuertosInsuficientesException("Un escritorio requiere mínimo 5 puertos. Recibido: " + cantidadPuertos);
        }



-Para el Ejercicio de la Interface / Precios (Ejercicio 1)
A. En la clase Escritorio: Copia este método dentro de la clase Escritorio.
@Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int anios = anioActual - this.modelo;
        double depreciacion = anios * 0.06; // 6% depreciación
        
        double porcentajePuertos;
        if (cantidadPuertos <= 4) porcentajePuertos = 0.1;
        else if (cantidadPuertos <= 8) porcentajePuertos = 0.25;
        else porcentajePuertos = 0.4;

        double precioDepreciado = precioBase * (1 - depreciacion);
        return precioDepreciado * (1 + porcentajePuertos);
    }
B. En la clase Laptop: Copia este método dentro de la clase Laptop.
@Override
    public double calcularPrecioVenta(double precioBase, int anioActual) {
        int anios = anioActual - this.modelo;
        double depreciacion = anios * 0.12; // 12% depreciación
        double descuentoPortabilidad = 0.15; // 15% descuento extra

        double precioFinal = (precioBase * (1 - depreciacion)) * (1 - descuentoPortabilidad);
        return (precioFinal < 0) ? 0 : precioFinal;
    }




-Para el Ejercicio de Igualdad / Equals (Ejercicio 3)
Copia estos dos métodos dentro de la clase padre Computadora.
@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Computadora otra = (Computadora) obj;
        return numeroSerie.equals(otra.getNumeroSerie());
    }

    @Override
    public int hashCode() {
        return numeroSerie.hashCode();
    }




-Para el Ejercicio de Búsqueda (Ejercicio 4)
Copia este método dentro de la clase InventarioComputadoras.
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
