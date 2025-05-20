package tricount.modelo;

public class Gasto implements Comparable<Gasto> {
    private String asunto;
    private double importe;

    public Gasto(String asunto, double importe) {
        if (asunto.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El gasto debe tener un asunto.");
        }
        this.importe = this.importeValido(importe);
        this.asunto = asunto;
    }

    @Override
    public String toString() {
        return "Asunto: " + asunto + "\n" + "importe: " + String.format("%.2f", importe);
    }

    private double importeValido(double importe) {
        if (importe < 0) {
            throw new IllegalArgumentException("ERROR: El importe no puede ser 0 o negativo.");
        }
        return importe;
    }

    public void setImporte(double gasto) {
        this.importe = this.importeValido(gasto);
    }

    public void setAsunto(String asunto) {
        if (asunto.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El gasto debe tener un asunto.");
        }
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public double getImporte() {
        return importe;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((asunto == null) ? 0 : asunto.hashCode());
        long temp;
        temp = Double.doubleToLongBits(importe);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Gasto other = (Gasto) obj;
        if (asunto == null) {
            if (other.asunto != null)
                return false;
        } else if (!asunto.equals(other.asunto))
            return false;
        if (Double.doubleToLongBits(importe) != Double.doubleToLongBits(other.importe))
            return false;
        return true;
    }

    @Override
    public int compareTo(Gasto o) {
        return (int) (-this.importe - o.importe);
    }
}
