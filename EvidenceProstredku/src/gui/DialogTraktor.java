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
import prostredky.Traktor;
import prostredky.ZnackaTraktoru;
import util.Barva;

/**
 *
 * @author Dan
 */
public class DialogTraktor extends Stage {

    private final Consumer<Traktor> vystupniOperace;
    private TextField SPZTextField;
    private TextField hmotnostTextField;
    private TextField tahTextField;
    private ComboBox<ZnackaTraktoru> znackaComboBox;
    private boolean edit;
    private Traktor tr;

    public static DialogTraktor factoryDialogTraktor(
            Supplier<Traktor> vstup, Consumer<Traktor> vystup) {
        return new DialogTraktor(vstup, vystup);
    }

    private DialogTraktor(
            Supplier<Traktor> vstup,
            Consumer<Traktor> vystup) {

        this.vystupniOperace = vystup;
        edit = vstup != null;
        tr = (edit) ? vstup.get() : new Traktor();
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
        tahTextField = addRow(grid, "Tah:", radek++);
        znackaComboBox = addRowComboBox(grid,"Znacka: ", radek++, ZnackaTraktoru.getZnackyTraktoru());
        Button buttonUloz = new Button("Uloz");
        buttonUloz.setOnAction(e -> {
            update();
            vystupniOperace.accept(tr);
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
        SPZTextField.setText(tr.getSpz());
        hmotnostTextField.setText(String.valueOf(tr.getHmotnost()));
        tahTextField.setText(String.valueOf(tr.getTah()));
        znackaComboBox.setValue(tr.getZnacka());
    }

    private void update() {
        try {
            tr.setSpz(SPZTextField.getText());
            tr.setHmotnost(Float.valueOf(hmotnostTextField.getText()));
            tr.setTah(Long.valueOf(tahTextField.getText()));
            tr.setZnacka(znackaComboBox.getValue());
        } catch (NumberFormatException ex) {
        }
    }
}
