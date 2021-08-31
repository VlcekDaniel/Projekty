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
import prostredky.Dodavka;
import prostredky.DodavkaTyp;
import prostredky.OsobniAutomobil;
import util.Barva;

/**
 *
 * @author Dan
 */
public class DialogDodavka extends Stage {

    private final Consumer<Dodavka> vystupniOperace;
    private TextField SPZTextField;
    private ComboBox<DodavkaTyp> typComboBox;
    private TextField hmotnostTextField;
    private TextField vyskaTextField;
    private boolean edit;
    private Dodavka d;

    public static DialogDodavka factoryDialogDodavka(
            Supplier<Dodavka> vstup, Consumer<Dodavka> vystup) {
        return new DialogDodavka(vstup, vystup);
    }

    private DialogDodavka(
            Supplier<Dodavka> vstup,
            Consumer<Dodavka> vystup) {

        this.vystupniOperace = vystup;
        edit = vstup != null;
        d = (edit) ? vstup.get() : new Dodavka();
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
        hmotnostTextField = addRow(grid, "Hmotnost:", radek++);
        typComboBox = addRowComboBox(grid, "Typ:", radek++, DodavkaTyp.values());
        vyskaTextField =addRow(grid, "Vyska: ", radek++);
        Button buttonUloz = new Button("Uloz");
        buttonUloz.setOnAction(e -> {
            update();
            vystupniOperace.accept(d);
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

    private static <T> ComboBox<T> addRowComboBox(GridPane grid,
            String nazev, int radek, Enum[] enumList) {
        grid.add(new Label(nazev), 0, radek);
        ComboBox<T> comboBox = new ComboBox(
                FXCollections.observableList(Arrays.asList(enumList)));
        grid.add(comboBox, 1, radek);
        return comboBox;
    }

    private void set() {
        SPZTextField.setText(d.getSpz());
        hmotnostTextField.setText(String.valueOf(d.getHmotnost()));
        typComboBox.setValue(d.getTyp());
        vyskaTextField.setText(String.valueOf(d.getVyska()));
    }

    private void update() {
        try {
            d.setSpz(SPZTextField.getText());
            d.setHmotnost(Float.valueOf(hmotnostTextField.getText()));
            d.setTyp(typComboBox.getValue());
            d.setVyska(Integer.valueOf(vyskaTextField.getText()));
        } catch (NumberFormatException ex) {
        }
    }
}
