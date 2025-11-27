1.Para el Ejercicio de la Excepción (Ejercicio 2)

A. Crear la clase de la excepción: Copia esto si te piden crear PesoInsuficienteException.
Java

public class PesoInsuficienteException extends Exception {
    public PesoInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public PesoInsuficienteException() {
        super("Error: El peso es insuficiente para la especie.");
    }
}

B. Usar la excepción en el Constructor: Copia esto dentro del constructor de la clase Avicolas.
Java

        // Validación requerida: Mínimo 1kg
        if (peso < 1.0) {
            throw new PesoInsuficienteException("Error: Un animal avícola debe tener al menos 1kg. Recibido: " + peso);
        }

2.Para el Ejercicio de la Interface / Costos (Ejercicio 1)

A. En la clase Avicolas: Copia este método dentro de la clase Avicolas.
Java

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

B. En la clase Caceras: Copia este método dentro de la clase Caceras.
Java

    @Override
    public double calcularCostoCuidado(double costoBase, int edadAnimal) {
        // Lógica hipotética: Descuento base 15% + Recargo por cola larga
        double descuentoBase = 0.15;
        double recargoCola = (longitudCola > 0.5) ? 0.20 : 0.0;

        double costoIntermedio = costoBase * (1 - descuentoBase);
        return costoIntermedio * (1 + recargoCola);
    }

3.Para el Ejercicio de Igualdad / Equals (Ejercicio 3)

Copia estos dos métodos dentro de la clase padre Animalito.
Java

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // Validación estricta (getClass)
        if (obj == null || getClass() != obj.getClass()) return false;
        Animalito otro = (Animalito) obj;
        return nombre.equals(otro.getNombre());
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

4.Para el Ejercicio de Búsqueda (Ejercicio 4)

Copia este método dentro de la clase Inventario.
Java

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
