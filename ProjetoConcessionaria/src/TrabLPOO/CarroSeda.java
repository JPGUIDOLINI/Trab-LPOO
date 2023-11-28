package TrabLPOO;
public class CarroSeda extends Carro{
    
    private static double diaria = 250.00;

    public CarroSeda(String placa, String modelo, String tipo, String status) {
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
        return "Confortavél e Tecnológico";
    }
    
}
