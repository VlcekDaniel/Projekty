/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prostredky;

import command.Keyboard;
import java.util.function.Function;
import util.Barva;

/**
 *
 * @author Dan
 */
public class Editor implements Function<DopravniProstredek, DopravniProstredek> {

    @Override
    public DopravniProstredek apply(DopravniProstredek dp) {
        dp.setSpz(Keyboard.getStringItem("Edituj SPZ:" +'('+ dp.getSpz()+')'));
        dp.setHmotnost(Keyboard.getFloatItem("Edituj hmotnost:", dp.getHmotnost()));
        switch (dp.getType()) {
            case OSOBNI_AUTOMOBIL:
                OsobniAutomobil oa =(OsobniAutomobil) dp;
                oa.setBarva(Keyboard.getBarvaItem("Edituj barvu:", oa.getBarva()));
                oa.setPocetSedadel(Keyboard.getIntItem("Edituj pocet sedadel:",oa.getPocetSedadel()));
                break;
            case DODAVKA:
                Dodavka d = (Dodavka) dp;                               
                String dodavkaString = Keyboard.getStringItem("Edituj typ Dodavky(dvojkabina,nástavba,valník): "+'(' + d.getTyp()+')');
                DodavkaTyp dodavkaTyp;
                switch (dodavkaString) {
                    case "dvojkabina":                       
                        d.setTyp(DodavkaTyp.DVOJ_KABINA);
                        break;
                    case "nástavba":
                        d.setTyp(DodavkaTyp.NASTAVBA);
                        break;
                    case "valník":
                        d.setTyp(DodavkaTyp.VALNIK);
                        break;
                    default:
                        d.setTyp(d.getTyp());
                        break;
                }              
                break;
            case NAKLADNI_AUTMOBIL:
                NakladniAutomobil na = (NakladniAutomobil) dp;
                na.setNosnost(Keyboard.getFloatItem("Edituj nosnost: ", na.getNosnost()));
                na.setPocetNaprav(Keyboard.getIntItem("Edituj pocet naprav: ", na.getPocetNaprav()));
                break;
            case TRAKTOR:
                Traktor tr = (Traktor) dp;
                tr.setTah(Keyboard.getIntItem("Edituj tah: ", (int) tr.getTah()));
                break;
        }
        return null;
    }

}
