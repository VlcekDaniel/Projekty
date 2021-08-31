package command;


import java.util.InputMismatchException;
import java.util.Scanner;
import util.Barva;


/**
 *
 * @author karel@simerda.cz
 */
public final class Keyboard {

    private static Scanner keyboard = new Scanner(System.in);

    private Keyboard() {
    }

    
    public static String getStringItem(String vyzva) {
        System.out.print(vyzva);
        return keyboard.nextLine().trim();
    }

    public static int getIntItem(String vyzva, int value) {
        boolean opakuj;
        do {
            opakuj = false;
            try {
                System.out.print(vyzva + '(' + value + ')');
                if (!keyboard.hasNext()) {
                    System.out.println("neni");
                    keyboard = new Scanner(System.in);
                    return value;
                }
                value = keyboard.nextInt();

            } catch (java.util.InputMismatchException ex) {
                opakuj = true;
                System.out.println("Chyba, opakuj.");
                keyboard.next();
            }
        } while (opakuj);
        keyboard.nextLine();
        return value;
    }

    public static Float getFloatItem(String vyzva, float valueOld) {
        boolean opakuj;
        float value = valueOld;
        do {
            opakuj = false;
            try {
                System.out.print(vyzva + '(' + value + ')');
                if (keyboard.hasNext()) {
                    value = keyboard.nextFloat();
                }
            } catch (IllegalStateException | InputMismatchException ex) {
                opakuj = true;
                System.out.println("Chyba, opakuj.");
                keyboard.next();
            }
        } while (opakuj);
        keyboard.nextLine();
        return value;
    }

    public static Double getDoubleItem(String vyzva) {
        boolean opakuj;
        double value = 0;
        do {
            opakuj = false;
            try {
                System.out.print(vyzva);
                value = keyboard.nextDouble();
            } catch (IllegalStateException | InputMismatchException ex) {
                opakuj = true;
                System.out.println("Chyba, opakuj.");
                keyboard.next();
            }
        } while (opakuj);
        keyboard.nextLine();
        return value;
    }

    public static Barva getBarvaItem(String vyzva, Barva barvaOld) {
        Barva barva = null;
        boolean opakuj = false;
        do {
            if (barvaOld != null) {
                vyzva = vyzva + '(' + barvaOld.getNazev() + "):";
            }
            String barvaText = getStringItem(vyzva);
            if (barvaText.length() == 0) {
                return barvaOld;
            }
            barva = Barva.decode(barvaText);
            if (barva == null) {
                opakuj = true;
                System.out.println("Neni barva, zadej znovu");
            }
        } while (opakuj);
        return barva;
    }

   
}
