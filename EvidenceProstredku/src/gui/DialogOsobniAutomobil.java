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
import prostredky.OsobniAutomobil;
import util.Barva;

/**
 *
 * @author Dan
 */
public class DialogOsobniAutomobil extends Stage {

    private final Consumer<OsobniAutomobil> vystupniOperace;
    private TextField SPZTextField;
    private ComboBox<Barva> barvaComboBox;
    private TextField pocetSedadelTextField;
    private TextField hmotnostTextField;
    private TextField vykonTextField;
    private boolean edit;
    private OsobniAutomobil os;

    public static DialogOsobniAutomobil factoryDialogOsobniAutomobil(
            Supplier<OsobniAutomobil> vstup, Consumer<OsobniAutomobil> vystup) {
        return new DialogOsobniAutomobil(vstup, vystup);
    }

    private DialogOsobniAutomobil(
            Supplier<OsobniAutomobil> vstup,
            Consumer<OsobniAutomobil> vystup) {

        this.vystupniOperace = vystup;
        edit = vstup != null;
        os = (edit) ? vstup.get() : new OsobniAutomobil();
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
        barvaComboBox = addRowComboBox(grid, "Barva:", radek++, Barva.values());
        pocetSedadelTextField = addRow(grid, "Pocet sedadel:", radek++);
        hmotnostTextField = addRow(grid, "Hmotnost:", radek++);
        vykonTextField = addRow(grid, "Vykon: ", radek++);
        Button buttonUloz = new Button("Uloz");
        buttonUloz.setOnAction(e -> {
            update();
            vystupniOperace.accept(os);
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
        SPZTextField.setText(os.getSpz());
        barvaComboBox.setValue(os.getBarva());
        pocetSedadelTextField.setText(String.valueOf(os.getPocetSedadel()));
        hmotnostTextField.setText(String.valueOf(os.getHmotnost()));
        vykonTextField.setText(String.valueOf(os.getVykon()));
    }

    private void update() {
        try {
            os.setSpz(SPZTextField.getText());
            os.setBarva(barvaComboBox.getValue());
            os.setPocetSedadel(Integer.valueOf(pocetSedadelTextField.getText()));
            os.setHmotnost(Float.valueOf(hmotnostTextField.getText()));
            os.setVykon(Integer.valueOf(vykonTextField.getText()));
        } catch (NumberFormatException ex) {
        }
    }
}
