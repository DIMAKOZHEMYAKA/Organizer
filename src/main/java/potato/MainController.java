package potato;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import potato.dao.TeaJSON;
import potato.dao.TeaSQL;
import potato.dao.TeaService;
import potato.controllers.ClassificationController;
import potato.controllers.InventoryController;
import potato.controllers.RecommendationController;
import potato.models.Tea;


import java.sql.*;
import java.util.*;

public class MainController {
    @FXML private TextField descriptionField;
    @FXML private Label typeResult;
    @FXML private TextField brewTimeField;
    @FXML private Label strengthResult;
    @FXML private Label inventoryResult;
    @FXML private ComboBox<Tea> teaSelector;
    @FXML private CheckBox preferenceStronger;
    @FXML private Label recommendationResult;
    @FXML private TableView<Tea> teaTable;
    @FXML private TableColumn<Tea, Integer> idColumn;
    @FXML private TableColumn<Tea, String> nameColumn;
    @FXML private TextField nameField;
    @FXML private TextField typeIdField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField countryField;
    @FXML private TextField flavorField;
    @FXML private TextField imageUrlField;
    @FXML private ToggleGroup dataSourceGroup;
    @FXML private RadioButton jsonRadio;
    @FXML private RadioButton postgresRadio;
    @FXML private RadioButton restRadio;

    private TeaService teaService;
    private Stage primaryStage;
    private ObservableList<Tea> teaData = FXCollections.observableArrayList();

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void onClassify(ActionEvent event) {
        String desc = descriptionField.getText();
        String teaType = ClassificationController.classifyTeaType(desc);
        typeResult.setText("Определённый тип: " + teaType);
    }

    public void onCalculateStrength(ActionEvent event) {
        try {
            int brewTime = Integer.parseInt(brewTimeField.getText());
            String strength = ClassificationController.calculateStrength(brewTime);
            strengthResult.setText("Крепость: " + strength);
        } catch (NumberFormatException e) {
            strengthResult.setText("Введите число");
        }
    }

    public void onCheckInventory(ActionEvent event) throws SQLException {
        InventoryController ic = new InventoryController();
        ic.checkInventoryAndNotify();
        inventoryResult.setText("Проверка выполнена (смотрите консоль)");
    }

    public void onGetRecommendation(ActionEvent event) {
        Tea selectedTea = teaSelector.getValue();
        if (selectedTea == null) {
            recommendationResult.setText("Выберите чай");
            return;
        }

        Map<String, Object> prefs = Map.of("stronger", preferenceStronger.isSelected());
        Map<String, Object> rec = RecommendationController.recommendBrewing(selectedTea, prefs);
        recommendationResult.setText(
                "Рекомендуемая температура: " + rec.get("temperature") + "°C, " +
                        "Время заваривания: " + rec.get("time_seconds") + " сек."
        );
    }

    //Работа с базой
    @FXML
    private void initialize() {
        // Инициализация таблицы
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getId());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());

        // Выбор источника данных по умолчанию
        jsonRadio.setSelected(true);
        switchToJson();

        // Слушатели для переключения источников данных
        jsonRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) switchToJson();
        });

        postgresRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) switchToPostgres();
        });
        // Загрузка данных
        refreshTeaTable();
    }

    private void switchToJson() {
        teaService = new TeaService(new TeaJSON());
        refreshTeaTable();
    }

    private void switchToPostgres() {
        teaService = new TeaService(new TeaSQL());
        refreshTeaTable();
    }


    @FXML
    private void handleRefresh() {
        refreshTeaTable();
    }

    @FXML
    private void handleAddTea() {
        try {
            Tea tea = new Tea();
            tea.setName(nameField.getText());
            tea.setTypeId(Integer.parseInt(typeIdField.getText()));
            tea.setDescription(descriptionArea.getText());
            tea.setCountryOfOrigin(countryField.getText());
            tea.setFlavorProfileFromString(flavorField.getText());
            tea.setImageUrl(imageUrlField.getText());

            teaService.addTea(tea);
            refreshTeaTable();
            clearFields();
        } catch (Exception e) {
            showError("Error adding tea", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateTea() {
        Tea selectedTea = teaTable.getSelectionModel().getSelectedItem();
        if (selectedTea != null) {
            try {
                selectedTea.setName(nameField.getText());
                selectedTea.setTypeId(Integer.parseInt(typeIdField.getText()));
                selectedTea.setDescription(descriptionArea.getText());
                selectedTea.setCountryOfOrigin(countryField.getText());
                selectedTea.setFlavorProfileFromString(flavorField.getText());
                selectedTea.setImageUrl(imageUrlField.getText());

                teaService.updateTea(selectedTea);
                refreshTeaTable();
            } catch (Exception e) {
                showError("Error updating tea", e.getMessage());
            }
        }
    }

    @FXML
    private void handleDeleteTea() {
        Tea selectedTea = teaTable.getSelectionModel().getSelectedItem();
        if (selectedTea != null) {
            try {
                teaService.deleteTea(selectedTea.getId());
                refreshTeaTable();
                clearFields();
            } catch (Exception e) {
                showError("Error deleting tea", e.getMessage());
            }
        }
    }

    private void refreshTeaTable() {
        try {
            List<Tea> teas = teaService.getAllTeas();
            teaData.clear();
            teaData.addAll(teas);
            teaTable.setItems(teaData);
        } catch (Exception e) {
            showError("Error loading teas", e.getMessage());
        }
    }

    private void clearFields() {
        nameField.clear();
        typeIdField.clear();
        descriptionArea.clear();
        countryField.clear();
        flavorField.clear();
        imageUrlField.clear();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
