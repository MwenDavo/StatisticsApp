public class ResultadoBinomial {
    public double rc;
    public int n;
    public double diferenciaAlfa;
    public double diferenciaBeta;

    public ResultadoBinomial() {
        this.rc = 0;
        this.n = 0;
        this.diferenciaAlfa = Double.MAX_VALUE;
        this.diferenciaBeta = Double.MAX_VALUE;
    }

    public double getDiferencia(){
        return Math.abs(diferenciaAlfa - diferenciaBeta);
    }


    @Override
    public String toString() {
        return "ResultadoBinomial{" +
                "rc=" + rc +
                ", n=" + n +
                ", diferenciaAlfa=" + diferenciaAlfa +
                ", diferenciaBeta=" + diferenciaBeta +
                '}';
    }
}
