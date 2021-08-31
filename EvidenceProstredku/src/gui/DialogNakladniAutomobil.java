package gui;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import util.Barva;

/**
 *
 * @author Dan
 */
public class DialogNakladniAutomobil extends Stage {

    private final Consumer<NakladniAutomobil> vystupniOperace;
    private TextField SPZTextField;    
    private TextField pocetNapravTextField;
    private TextField hmotnostTextField;
    private TextField nosnostTextField;
    private TextField rokVyrobyTextField;
    private boolean edit;
    private NakladniAutomobil na;

    public static DialogNakladniAutomobil factoryDialogNakladniAutomobil(
            Supplier<NakladniAutomobil> vstup, Consumer<NakladniAutomobil> vystup) {
        return new DialogNakladniAutomobil(vstup, vystup);
    }

    private DialogNakladniAutomobil(
            Supplier<NakladniAutomobil> vstup,
            Consumer<NakladniAutomobil> vystup) {

        this.vystupniOperace = vystup;
        edit = vstup != null;
        na = (edit) ? vstup.get() : new NakladniAutomobil();
        setWidth(350);
        setHeight(350);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.WINDOW_MODAL);
        setScene(getScena());
        if (edit) {
            set();
        }
    }

    private Scene getScena() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(20);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        box.getChildren().addAll(grid);
        int radek = 0;
        SPZTextField = addRow(grid, "SPZ:", radek++);
        pocetNapravTextField = addRow(grid, "Pocet sedadel:", radek++);
        hmotnostTextField = addRow(grid, "Hmotnost:", radek++);
        nosnostTextField = addRow(grid, "Nosnost:", radek++);
        rokVyrobyTextField = addRow(grid, "Rok vyroby: ", radek++);
        Button buttonUloz = new Button("Uloz");
        buttonUloz.setOnAction(e -> {
            update();
            vystupniOperace.accept(na);
            hide();
        });
        grid.add(buttonUloz, 0, ++radek);
        Scene s = new Scene(box);
        return s;
    }

    private static TextField addRow(GridPane grid, String nazev, int radek) {
        grid.add(new Label(nazev), 0, radek);
        TextField textField = new TextField();
        grid.add(textField, 1, radek);
        return textField;
    }

    private void set() {
        SPZTextField.setText(na.getSpz());       
        pocetNapravTextField.setText(String.valueOf(na.getPocetNaprav()));
        hmotnostTextField.setText(String.valueOf(na.getHmotnost()));
        nosnostTextField.setText(String.valueOf(na.getNosnost()));
        rokVyrobyTextField.setText(String.valueOf(na.getRokVyroby()));
    }

    private void update() {
        try {
            na.setSpz(SPZTextField.getText());
            na.setPocetNaprav(Integer.valueOf(pocetNapravTextField.getText()));
            na.setHmotnost(Float.valueOf(hmotnostTextField.getText()));
            na.setNosnost(Float.valueOf(nosnostTextField.getText()));
            na.setRokVyroby(Integer.valueOf(rokVyrobyTextField.getText()));
        } catch (NumberFormatException ex) {
        }
    }
}
