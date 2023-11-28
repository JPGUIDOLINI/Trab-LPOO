package TrabLPOO;
public abstract class Carro {
    private String placa;
    private String modelo;
    private String tipo;
    private String status;
    private boolean locado;
    private int numDias;
    private static int numLocados = 0;
    private static double diaria = 200.00; 

    @Override
    public String toString() {
        return "Carro [codigo=" + placa + ", modelo=" + modelo + ", locado=" + locado + ", numDias=" + numDias + "]";
    }

    public Carro(String placa, String modelo, String tipo, String status) {
        this.placa = placa;
        this.modelo = modelo;
        this.tipo = tipo;
        this.status = status;
        this.numDias = 0;
        this.locado = false;
    }

    
    
    public String getPlaca() {
        return placa;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public int getNumDias() {
        return numDias;
    }

    public String getStatus() {
        return status;
    }
    
    public boolean isLocado() {
        return locado;
    }
    
    public static int getNumLocados() {
        return numLocados;
    }

    public static double getDiaria() {
        return diaria;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setNumDias(int numDias) {
        this.numDias = numDias;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean locar(int diasLocados) {
        if (isLocado()) {
            return false;
        }
        locado = true;
        numDias = diasLocados;
        numLocados++;
        return true;
    }
    
    public double devolver() {
        if (isLocado() == false){
            System.out.println("o valor da locação é"+diaria+"a diaria");
            return 0;
        }
        locado = false;
        numLocados--;
        double valorLocacao = diaria * numDias;
        return valorLocacao;
    }

    public abstract String teste();
}