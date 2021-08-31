/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import command.CommandLineMain;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import kolekce.KolekceException;
import kolekce.LinSeznam;
import prostredky.Dodavka;
import prostredky.DodavkaTyp;
import prostredky.DopravniProstredek;
import prostredky.DopravniProstredekKlic;
import prostredky.Editor;
import prostredky.KomparatorTyp;
import prostredky.NakladniAutomobil;
import prostredky.OsobniAutomobil;
import prostredky.ProstredekTyp;
import prostredky.Traktor;
import prostredky.ZnackaTraktoru;
import sprava.SpravaProstredku;
import util.Barva;

/**
 *
 * @author Dan
 */
public class GUIMain extends Application {

    private static final String ZALOHA_FILE_NAME = "zaloha.bin";
    private static final String ZALOHA_TEXTFILE_NAME = "zaloha.txt";
    private static final String ZALOHA_TEXTFILETEST_NAME = "zaloha.txt";

    //TODO EDITOR
    private static final Function<DopravniProstredek, DopravniProstredek> editor = null;
    private static CommandLineMain instance;
    private SpravaProstredku<DopravniProstredek> spravce;

    private final Comparator<DopravniProstredek> komparatorSPZ = (o1, o2) -> {
        return o1.getSpz().compareTo(o2.getSpz());
    };
    private final Comparator<DopravniProstredek> komparatorID = (o1, o2) -> {
        return o1.getId() - o2.getId();
    };

    ObservableList<DopravniProstredek> observavleListDopravniProstredky = FXCollections.observableArrayList();
    ListView<DopravniProstredek> listViewDopravniProstredky = new ListView(observavleListDopravniProstredky);

    public GUIMain() {
        spravce = new SpravaProstredku<>();
        spravce.vytvorSeznam(LinSeznam::new);
        spravce.nastavKomparator(komparatorSPZ);
    }

    //SCENE
    @Override
    public void start(Stage primaryStage) {

        BorderPane border = new BorderPane();
        HBox listViewBox = addHBox(border.getMaxWidth(), border.getMaxHeight());
        HBox bottomMenu = addHBoxBottomMenu();
        VBox rightMenu = addVBox(border.getMaxWidth(), border.getMaxHeight());

        border.setLeft(listViewBox);
        border.setRight(rightMenu);
        border.setBottom(bottomMenu);

        Scene scene = new Scene(border, 1200, 800);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Správa dopravních prostředků");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //LAYOUT
    public HBox addHBox(double width, double height) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.setSpacing(10);

        hbox.setPrefWidth(1080);
        listViewDopravniProstredky.setPrefWidth(1080);

        hbox.getChildren().addAll(listViewDopravniProstredky);

        return hbox;
    }

    public VBox addVBox(double width, double height) {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15, 15, 15, 15));
        vbox.setSpacing(5);

        Text title = new Text("Procházení");

        Button buttonPrvni = newButton("Prvni", 100, 20, handlerPrvni());

        Button buttonDalsi = newButton("dalsi", 100, 20, handlerDalsi());

        Button buttonPosledni = newButton("posledni", 100, 20, handlerPosledni());

        Text title2 = new Text("Příkazy");

        Button buttonEdituj = newButton("Edituj", 100, 20, handlerEdituj());

        Button buttonVyjmi = newButton("Vyjmi", 100, 20, handlerVyjmi());

        Button buttonZobraz = newButton("Zobraz", 100, 20, handlerZobraz());

        Button buttonClear = newButton("Clear", 100, 20, handlerClear());

        vbox.getChildren().addAll(title, buttonPrvni, buttonDalsi, buttonPosledni);
        vbox.getChildren().addAll(title2, buttonEdituj, buttonVyjmi, buttonZobraz, buttonClear);

        return vbox;
    }

    public HBox addHBoxBottomMenu() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 15, 15, 15));
        hbox.setSpacing(10);

        Button buttonGeneruj = newButton("Generuj", 100, 20, handlerGeneruj());

        Button buttonUloz = newButton("Uloz", 100, 20, handlerUloz());

        Button buttonNacti = newButton("Nacti", 100, 20, handlerNacti());

        Text novyText = new Text("Novy:");

        ComboBox<ProstredekTyp> comboBoxNovy = newComboBox(100, 20, "Novy", ProstredekTyp.getProstredky(), handlerComboBoxNovy());

        Text filtrText = new Text("Filtr:");

        ComboBox<ProstredekTyp> comboBoxFiltr = newComboBox(100, 20, "Filtr", ProstredekTyp.getProstredkyFilter(), handlerComboBoxFiltr());

        ComboBox<ProstredekTyp> comboBoxNajdi = newComboBox(100, 20, "Najdi", KomparatorTyp.getKomparatory(), handlerComboBoxNajdi());

        Button buttonZalohuj = newButton("Zalohuj", 100, 20, handlerZalohuj());

        Button buttonObnov = newButton("Obnov", 100, 20, handlerObnov());

        Button buttonZrus = newButton("Zrus", 100, 20, handlerZrus());

        hbox.getChildren().addAll(buttonGeneruj, buttonUloz, buttonNacti, novyText, comboBoxNovy, filtrText, comboBoxFiltr, comboBoxNajdi, buttonZalohuj, buttonObnov, buttonZrus);

        return hbox;
    }

    //MAIN
    public static void main(String[] args) {
        launch(args);
    }

    //CREATE BUTTON
    private static Button newButton(
            String text,
            int x, int y,
            EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setPrefWidth(x);
        button.setPrefHeight(y);
        return button;
    }

    //CREATE COMBOBOX
    private ComboBox newComboBox(int x, int y, String promptText, Enum[] seznamVyctu,
            EventHandler<ActionEvent> handler) {
        ComboBox comboBox = new ComboBox(
                FXCollections.observableList(Arrays.asList(seznamVyctu)));
        comboBox.setPromptText(promptText);
        comboBox.setOnAction(handler);
        comboBox.setPrefWidth(x);
        comboBox.setPrefHeight(y);
        return comboBox;
    }

    //REFRESH
    private void obnovZobrazeniSeznamu() {
        observavleListDopravniProstredky.clear();
        spravce.stream().forEach((t) -> {
            observavleListDopravniProstredky.add(t);
        });
    }

    private void obnovZobrazeniSeznamu(ProstredekTyp typ) {
        observavleListDopravniProstredky.clear();

        if (typ == ProstredekTyp.NON_FILTER) {
            obnovZobrazeniSeznamu();
        } else {
            spravce.stream().filter(t -> t.getType() == typ).forEach((t) -> {
                observavleListDopravniProstredky.add(t);
            });
        }
    }

    //HANDLERS
    private EventHandler<ActionEvent> handlerGeneruj() {
        return event -> {
            spravce.generujData(50);
            obnovZobrazeniSeznamu();
        };
    }

    private EventHandler<ActionEvent> handlerZrus() {
        return event -> {
            spravce.zrus();
            obnovZobrazeniSeznamu();
        };
    }

    private EventHandler<ActionEvent> handlerPrvni() {
        return event -> {
            spravce.prejdiNaPrvniPolozku();
            listViewDopravniProstredky.getSelectionModel().selectFirst();
            listViewDopravniProstredky.getFocusModel().focus(listViewDopravniProstredky.getSelectionModel().getSelectedIndex());
        };
    }

    private EventHandler<ActionEvent> handlerDalsi() {
        return event -> {
            spravce.prejdiNaDalsiPolozku();
            listViewDopravniProstredky.getSelectionModel().selectNext();
            listViewDopravniProstredky.getFocusModel().focus(listViewDopravniProstredky.getSelectionModel().getSelectedIndex());
        };
    }

    private EventHandler<ActionEvent> handlerPosledni() {
        return event -> {
            spravce.prejdiNaPosledniPolozku();
            listViewDopravniProstredky.getSelectionModel().selectLast();
            listViewDopravniProstredky.getFocusModel().focus(listViewDopravniProstredky.getSelectionModel().getSelectedIndex());
        };
    }

    private EventHandler<ActionEvent> handlerEdituj() {
        return event -> {
            DopravniProstredek dpEdit = listViewDopravniProstredky.getSelectionModel().getSelectedItem();
            dialogEditujProstredek(dpEdit.getType());
        };
    }

    private EventHandler<ActionEvent> handlerVyjmi() {
        return event -> {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Opravdu chcete odebrat zvolený prvek?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DopravniProstredek akt = listViewDopravniProstredky.getSelectionModel().getSelectedItem();
                spravce.nastavAktualniPolozku(akt);
                spravce.vyjmiAktualniPolozku();
                obnovZobrazeniSeznamu();
            } else {
            }
        };
    }

    private EventHandler<ActionEvent> handlerZobraz() {
        return event -> {
            obnovZobrazeniSeznamu();
        };
    }

    private EventHandler<ActionEvent> handlerUloz() {
        return event -> {
            spravce.ulozTextSoubor(ZALOHA_TEXTFILE_NAME, mapperOutput);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Seznam byl uložen.");
            alert.showAndWait();
        };
    }

    private EventHandler<ActionEvent> handlerNacti() {
        return event -> {
            spravce.nactiTextSoubor(ZALOHA_TEXTFILE_NAME, mapperInput);
            obnovZobrazeniSeznamu();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Seznam byl načtený.");
            alert.showAndWait();
        };
    }

    private EventHandler<ActionEvent> handlerClear() {
        return event -> {
            observavleListDopravniProstredky.removeAll(observavleListDopravniProstredky);
        };
    }

    private EventHandler<ActionEvent> handlerZalohuj() {
        return event -> {
            spravce.ulozDoSouboru(ZALOHA_FILE_NAME);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Seznam byl zálohován.");
            alert.showAndWait();
        };
    }

    private EventHandler<ActionEvent> handlerObnov() {
        return event -> {
            spravce.nactiZeSouboru(ZALOHA_FILE_NAME);
            obnovZobrazeniSeznamu();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Seznam byl obnoven.");
            alert.showAndWait();
        };
    }

    private EventHandler<ActionEvent> handlerComboBoxNovy() {
        return event -> {
            ProstredekTyp typ = ((ComboBox<ProstredekTyp>) event.getSource()).getValue();
            dialogNovyProstredek(typ);
        };
    }

    private EventHandler<ActionEvent> handlerComboBoxFiltr() {
        return event -> {
            ProstredekTyp typ = ((ComboBox<ProstredekTyp>) event.getSource()).getValue();
            obnovZobrazeniSeznamu(typ);
        };
    }

    private EventHandler<ActionEvent> handlerComboBoxNajdi() {
        return event -> {
            KomparatorTyp typ = ((ComboBox<KomparatorTyp>) event.getSource()).getValue();
            dialogNajdiProstredek(typ);
        };
    }

    //DIALOGY
    private void dialogNovyProstredek(ProstredekTyp typ) {
        Stage dialog = null;
        switch (typ) {
            case OSOBNI_AUTOMOBIL:
                dialog = DialogOsobniAutomobil.factoryDialogOsobniAutomobil(null, t -> {
                    spravce.vlozPolozku(t);
                    obnovZobrazeniSeznamu();
                });
                break;
            case NAKLADNI_AUTMOBIL:
                dialog = DialogNakladniAutomobil.factoryDialogNakladniAutomobil(null, t -> {
                    spravce.vlozPolozku(t);
                    obnovZobrazeniSeznamu();
                });
                break;
            case DODAVKA:
                dialog = DialogDodavka.factoryDialogDodavka(null, t -> {                  
                    spravce.vlozPolozku(t);
                    obnovZobrazeniSeznamu();
                });
                break;
            case TRAKTOR:
                dialog = DialogTraktor.factoryDialogTraktor(null, t -> {                  
                    spravce.vlozPolozku(t);
                    obnovZobrazeniSeznamu();
                });
                break;
        }
        dialog.showAndWait();
    }

    private void dialogEditujProstredek(ProstredekTyp typ) {
        Stage dialog = null;
        DopravniProstredek dpEdit = listViewDopravniProstredky.getSelectionModel().getSelectedItem();
        switch (typ) {
            case OSOBNI_AUTOMOBIL:
                dialog = DialogOsobniAutomobil.factoryDialogOsobniAutomobil(
                        () -> (OsobniAutomobil) dpEdit,
                        t -> obnovZobrazeniSeznamu());
                break;
            case NAKLADNI_AUTMOBIL:
                dialog = DialogNakladniAutomobil.factoryDialogNakladniAutomobil(
                        () -> (NakladniAutomobil) dpEdit,
                        t -> obnovZobrazeniSeznamu());

                break;
            case DODAVKA:
                dialog = DialogDodavka.factoryDialogDodavka(
                        () -> (Dodavka) dpEdit,
                        t -> obnovZobrazeniSeznamu());

                break;
            case TRAKTOR:
                dialog = DialogTraktor.factoryDialogTraktor(
                        () -> (Traktor) dpEdit,
                        t -> obnovZobrazeniSeznamu());
                break;
        }
        dialog.showAndWait();
    }

    private void dialogNajdiProstredek(KomparatorTyp typ) {
        TextInputDialog dialog = new TextInputDialog("");
        switch (typ) {
            case KOMPARATOR_ID:
                spravce.nastavKomparator(komparatorID);
                dialog.setTitle("Porovnávání podle ID");
                dialog.setHeaderText("Zadejte ID: ");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    DopravniProstredekKlic dopravniProstredekKlicID = new DopravniProstredekKlic(Integer.parseInt(result.get()));
                    listViewDopravniProstredky.getSelectionModel().select(spravce.najdiPolozku(dopravniProstredekKlicID));
                }
                break;
            case KOMPARATOR_SPZ:
                spravce.nastavKomparator(komparatorSPZ);
                dialog.setTitle("Porovnávání podle SPZ");
                dialog.setHeaderText("Zadejte SPZ: ");
                Optional<String> text = dialog.showAndWait();
                if (text.isPresent()) {
                    DopravniProstredekKlic dopravniProstredekKlicSPZ = new DopravniProstredekKlic(text.get());
                    listViewDopravniProstredky.getSelectionModel().select(spravce.najdiPolozku(dopravniProstredekKlicSPZ));
                    break;
                }
        }
    }

    //MAPERS
    private static final Function<String, DopravniProstredek> mapperInput = (line) -> {
        DopravniProstredek prostredek = null;
        if (line.length() == 0) {
            return prostredek;
        }
        String[] items = line.split(",");
        String spz = items[1].trim();
        float hmotnost = Float.valueOf(items[2]);
        switch (items[0].trim().toLowerCase(Locale.US)) {
            case "oa":
                int pocetSedadel = Integer.valueOf(items[3].trim());
                Barva barva = Barva.decode(items[4].trim());
                int vykon = Integer.valueOf(items[5].trim());
                prostredek = new OsobniAutomobil(spz, barva, pocetSedadel, hmotnost, vykon);
                break;
            case "do":
                DodavkaTyp typ = DodavkaTyp.decode(items[3].trim());
                int vyska = Integer.valueOf(items[4].trim());
                prostredek = new Dodavka(spz, hmotnost, typ,vyska);
                break;
            case "na":
                float nosnost = Float.valueOf(items[4].trim());
                int pocetNaprav = Integer.valueOf(items[2].trim());
                hmotnost = Float.valueOf(items[3]);
                int rokVyroby = Integer.valueOf(items[5].trim());
                prostredek = new NakladniAutomobil(spz, pocetNaprav, hmotnost, nosnost,rokVyroby);
                break;
            case "tr":
                int tah = Integer.valueOf(items[3].trim());
                ZnackaTraktoru znacka = ZnackaTraktoru.decode(items[4].trim());
                prostredek = new Traktor(spz, hmotnost, tah,znacka);
                break;
        }
        return prostredek;
    };

    private static final Function<DopravniProstredek, String> mapperOutput = (dp) -> {
        String text = "";
        switch (dp.getType().toString()) {
            case "osobní automobil":
                OsobniAutomobil oa = (OsobniAutomobil) dp;
                text = "oa" + ", " + oa.getSpz() + ", " + oa.getHmotnost() + ", " + oa.getPocetSedadel() + ", " + oa.getBarva()+ ", " + oa.getVykon();
                break;
            case "dodávka":
                Dodavka d = (Dodavka) dp;
                text = "do" + ", " + d.getSpz() + ", " + d.getHmotnost() + ", " + d.getTyp()+ ", " + d.getVyska();
                break;
            case "nákladní automobil":
                NakladniAutomobil na = (NakladniAutomobil) dp;
                text = "na" + ", " + dp.getSpz() + ", " + na.getPocetNaprav() + ", " + dp.getHmotnost() + ", " + na.getNosnost()+ ", " + na.getRokVyroby();
                break;
            case "traktor":
                Traktor tr = (Traktor) dp;
                text = "tr" + ", " + dp.getSpz() + ", " + dp.getHmotnost() + ", " + tr.getTah() + ", " + tr.getZnacka();
                break;
        }
        return text;
    };
}
