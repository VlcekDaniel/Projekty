package sem_imosi_a;

import java.util.ArrayList;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TriangularDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

public class Sem_IMOSI_A {

    public static void main(String[] args) {

        NormalDistribution r1 = new NormalDistribution(7.0, 0.4);
        UniformRealDistribution r2 = new UniformRealDistribution(9.1, 9.9);
        NormalDistribution r3 = new NormalDistribution(6.5, 0.45);
        TriangularDistribution d1 = new TriangularDistribution(7.6, 8, 8.4);
        TriangularDistribution d2 = new TriangularDistribution(7.6, 8, 8.2);

        ArrayList<Double> vysledky = new ArrayList<>();
        double rozptyl = 0;
        double aktualniPrumer = 0;
        double sumaVsechVysledku = 0;
        double smerodatnaOdchylka = 0;
        double predchoziS = 0;
        int pocetPrubehu = 1;
        double pocetUspesnychPokusu = 0;
        double pocetNeuspesnychPokusu = 0;
        
        //celková velikost pouzdra hodinek 30mm, volný prostor na obou stranách 2mm, p1 ap2 3.5mm

        //maximální možná velikost součástek 28mm       

        while (true) {

            double nm1 = r1.sample();
            double urd2 = r2.sample();
            double nm3 = r3.sample();
            double td1 = d1.sample();
            double td2 = d2.sample();

            double soucetPokusu = nm1 + urd2 + nm3 + td1 - 3.5;
            double soucetPokusu2 = nm1 + urd2 + nm3 + td2 - 3.5;
            
            if(soucetPokusu <28 ){
                pocetUspesnychPokusu++;
            }else{
                pocetNeuspesnychPokusu++;
            }
            
            sumaVsechVysledku+=soucetPokusu;
            aktualniPrumer = sumaVsechVysledku / pocetPrubehu;  
            
            vysledky.add(soucetPokusu);     
            if (pocetPrubehu >= 2) {
                for (int j = 0; j < pocetPrubehu; j++) {
                    rozptyl += Math.pow((vysledky.get(j) - aktualniPrumer), 2);                    
                }      
                smerodatnaOdchylka = Math.sqrt(rozptyl / (pocetPrubehu - 1));                 
                if (Math.abs(smerodatnaOdchylka - predchoziS) < 0.000001 || pocetPrubehu == 5000) {
                    break;
                } else {
                    predchoziS = smerodatnaOdchylka;
                    rozptyl = 0;
                }
            }
            pocetPrubehu++;
        }
        
        //výpočet pravděpodobnosti že součástky nebude možné umístit do pouzdra
        double pravdepodobnost = pocetNeuspesnychPokusu/pocetPrubehu;
        double cenaVadnychSoucastek = 5* pocetNeuspesnychPokusu;
        
        System.out.println("************************************");
        System.out.println("počet: " + pocetPrubehu);
        System.out.println("Pravděpodobnost že součástky nebude možné umístit do pouzdra: " + pravdepodobnost);
        System.out.println("Náklady na ověření součástek: " + cenaVadnychSoucastek + " USD");
    }
}
