package TrabLPOO;
public class CarroSuv extends Carro{
    
    private static double diaria = 300.00;

    public CarroSuv(String placa, String modelo, String tipo, String status) {
        super(placa, modelo, tipo, status);
    }

    public double devolver() {
        if (isLocado() == false) {
            System.out.println("o valor da locação é"+diaria+"a diaria");
            return 0;
        }
        double valorLocacao = diaria*super.getNumDias();
        return valorLocacao;

    }

    public String teste(){
        return "Espaçoso e Robusto";
    }
    
}

