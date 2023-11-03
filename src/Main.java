import jdistlib.Binomial;
import jdistlib.Normal;

public class Main {
    public static void main(String[] args){
        System.out.println(calculoPlanMuestreo_casoI(.01,.12,.1,.14).toString());
    }

    public static ResultadoBinomial calculoPlanMuestreo_casoI(double alfa, double p0, double beta, double p1){
        ResultadoBinomial res = new ResultadoBinomial();
        int nAprox = calcularTamañoAproxNormal(alfa,p0,beta,p1);
        int rAprox = (int) calcularVerdaderoRCriticoCasoI((int) calcularRCriticoCasoI(nAprox,alfa,p0),nAprox,alfa,p0);
        res.n = nAprox;
        res.rc = rAprox;
        double alfaCalculadoInicial = Binomial.cumulative(rAprox,nAprox,p0,true,false);
        double betaCalculadoInicial = Binomial.cumulative(rAprox+1,nAprox,p1,false,false);
        res.diferenciaAlfa = Math.abs(alfaCalculadoInicial - alfa);
        res.diferenciaBeta = Math.abs(betaCalculadoInicial - beta);
        System.out.println("n aproximado: " + nAprox);
        System.out.println("r aproximado: " + rAprox);
        System.out.println();
        int nLimite = (int) (nAprox * 0.1f);
        int rLimite = (int) (rAprox * 0.1f);
        for(int i = nLimite * -1; i <= nLimite; i++){
            int nTest = nAprox + i;
            for(int j = rLimite * -1; j < rLimite ;j++){
                int rTest = rAprox + j;
                double alfaCalculado = Binomial.cumulative(rTest-1,nTest,p0,false,false);
                double betaCalculado = Binomial.cumulative(Math.max(rTest-1,0),nTest,p1,true,false);
                if(alfaCalculado < alfa && betaCalculado < beta){
                    if((alfaCalculado < alfa + 0.005 && alfaCalculado > alfa - 0.005) && (betaCalculado < beta + 0.005 && betaCalculado > beta - 0.005)) {
                        double diferenciaAlfa = Math.abs(alfaCalculado - alfa);
                        double diferenciaBeta = Math.abs(betaCalculado - beta);
                        double diferencia = Math.abs(diferenciaAlfa - diferenciaBeta);
                        if(diferencia < res.getDiferencia()){
                            System.out.println("diferencia actual (rc: " + res.rc + " - n: " + res.n + "): " + res.getDiferencia() + "      --      diferencia nueva (rc: " + rTest + " - n: " + nTest + "): " + diferencia);
                            System.out.println("     dif_alfa (actual - nueva): " + res.diferenciaAlfa + " - " + diferenciaAlfa);
                            System.out.println("     dif_beta (actual - nueva): " + res.diferenciaBeta + " - " + diferenciaBeta);
                            System.out.println();
                            res.n = nTest;
                            res.rc = rTest;
                            res.diferenciaAlfa = diferenciaAlfa;
                            res.diferenciaBeta = diferenciaBeta;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static ResultadoBinomial calculoPlanMuestreo_casoII(double alfa, double p0, double beta, double p1){
        ResultadoBinomial res = new ResultadoBinomial();
        int nAprox = calcularTamañoAproxNormal(alfa,p0,beta,p1);
        int rAprox = (int) calcularVerdaderoRCriticoCasoII((int) calcularRCriticoCasoII(nAprox,alfa,p0),nAprox,alfa,p0);
        res.n = nAprox;
        res.rc = rAprox;
        double alfaCalculadoInicial = Binomial.cumulative(rAprox,nAprox,p0,true,false);
        double betaCalculadoInicial = Binomial.cumulative(rAprox+1,nAprox,p1,false,false);
        res.diferenciaAlfa = Math.abs(alfaCalculadoInicial - alfa);
        res.diferenciaBeta = Math.abs(betaCalculadoInicial - beta);
        System.out.println("n aproximado: " + nAprox);
        System.out.println("r aproximado: " + rAprox);
        int nLimite = (int) (nAprox * 0.1f);
        int rLimite = (int) (rAprox * 0.1f);
        for(int i = nLimite * -1; i <= nLimite; i++){
            int nTest = nAprox + i;
            for(int j = rLimite * -1; j < rLimite ;j++){
                int rTest = rAprox + j;
                double alfaCalculado = Binomial.cumulative(rTest,nTest,p0,true,false);
                double betaCalculado = Binomial.cumulative(rTest+1,nTest,p1,false,false);
                if(alfaCalculado < alfa && betaCalculado < beta){
                    if((alfaCalculado < alfa + 0.005 && alfaCalculado > alfa - 0.005) && (betaCalculado < beta + 0.005 && betaCalculado > beta - 0.005)) {
                        double diferenciaAlfa = Math.abs(alfaCalculado - alfa);
                        double diferenciaBeta = Math.abs(betaCalculado - beta);
                        double diferencia = Math.abs(diferenciaAlfa - diferenciaBeta);
                        if(diferencia < res.getDiferencia()){
                            System.out.println("diferencia actual (rc: " + res.rc + " - n: " + res.n + "): " + res.getDiferencia() + "      --      diferencia nueva (rc: " + rTest + " - n: " + nTest + "): " + diferencia);
                            System.out.println("     dif_alfa (actual - nueva): " + res.diferenciaAlfa + " - " + diferenciaAlfa);
                            System.out.println("     dif_beta (actual - nueva): " + res.diferenciaBeta + " - " + diferenciaBeta);
                            System.out.println();
                            res.n = nTest;
                            res.rc = rTest;
                            res.diferenciaAlfa = diferenciaAlfa;
                            res.diferenciaBeta = diferenciaBeta;
                        }
                    }
                }
            }
        }
        return res;
    }

    private static double calcularRCriticoCasoI(int n, double alfa, double p0) {
        double res = Binomial.quantile(alfa,n,p0,false,false);
        return res;
    }

    private static double calcularRCriticoCasoII(int n, double alfa, double p0) {
        double res = Binomial.quantile(alfa,n,p0,true,false);
        return res;
    }

    private static double calcularVerdaderoRCriticoCasoI(int rc, int n, double alfa, double p0){
        double valor = Binomial.cumulative(rc-1,n,p0,false,false);
        while(valor > alfa){
            rc++;
            valor = Binomial.cumulative(rc-1,n,p0,false,false);
        }
        return rc;
    }

    private static double calcularVerdaderoRCriticoCasoII(int rc, int n, double alfa, double p0){
        double valor = Binomial.cumulative(rc,n,p0,true,false);
        while(valor > alfa){
            rc++;
        }
        return rc;
    }

    public static int calcularTamañoAproxNormal(double alfa, double p0, double beta, double p1){
        double alfaNormal = Normal.quantile(1-alfa,0,1,true,false);
        double betaNormal = Normal.quantile(1-beta,0,1,true,false);
        double result = Math.pow((alfaNormal*Math.sqrt(p0*(1-p0)) + betaNormal*Math.sqrt(p1*(1-p1)))/(p1-p0),2);
        return (int) result + 1;
    }


}