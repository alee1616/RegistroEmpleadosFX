package com.example.registroempleadosfx.controller;

import com.example.registroempleadosfx.database.DatabaseConnection;
import com.example.registroempleadosfx.model.Empleado;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;

public class EmpleadoController {

    @FXML
    private TextField txtNombres;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCedula;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCargo;
    @FXML
    private ComboBox<String> cmbDepartamento;
    @FXML
    private TextField txtSalario;
    @FXML
    private DatePicker dpFechaContratacion;
    @FXML
    private ComboBox<String> cmbEstado;

    // ---------- Botones ----------
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnActualizar;

    // ---------- Tabla y columnas ----------
    @FXML
    private TableView<Empleado> tvEmpleados;
    @FXML
    private TableColumn<Empleado, Integer> colId;
    @FXML
    private TableColumn<Empleado, String> colNombres;
    @FXML
    private TableColumn<Empleado, String> colApellidos;
    @FXML
    private TableColumn<Empleado, String> colCedula;
    @FXML
    private TableColumn<Empleado, String> colCorreo;
    @FXML
    private TableColumn<Empleado, String> colTelefono;
    @FXML
    private TableColumn<Empleado, String> colCargo;
    @FXML
    private TableColumn<Empleado, String> colDepartamento;
    @FXML
    private TableColumn<Empleado, Double> colSalario;
    @FXML
    private TableColumn<Empleado, LocalDate> colFechaContratacion;
    @FXML
    private TableColumn<Empleado, String> colEstado;

    private final ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Columnas: el texto debe ser igual al nombre del atributo en Empleado
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<>("fechaContratacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Valores iniciales de los ComboBox
        cmbDepartamento.getItems().addAll("Finanzas", "Tecnología", "Recursos Humanos", "Ventas");
        cmbEstado.getItems().addAll("Activo", "Inactivo");

        // Cargar los empleados al abrir la ventana
        cargarEmpleados();
    }

    private void cargarEmpleados() {
        listaEmpleados.clear();
        String sql = "SELECT * FROM empleado";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("cedula"),
                        rs.getString("correo"),
                        rs.getString("telefono"),
                        rs.getString("cargo"),
                        rs.getString("departamento"),
                        rs.getDouble("salario"),
                        rs.getDate("fecha_contratacion").toLocalDate(),
                        rs.getString("estado")
                );
                listaEmpleados.add(emp);
            }
            tvEmpleados.setItems(listaEmpleados);

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los empleados:\n" + e.getMessage());
        }
    }

    @FXML
    private void guardarEmpleado() {
        if (!validarCampos()) {
            return;
        }

        String sql = "INSERT INTO empleado (nombres, apellidos, cedula, correo, telefono, cargo, "
                + "departamento, salario, fecha_contratacion, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, txtNombres.getText().trim());
            ps.setString(2, txtApellidos.getText().trim());
            ps.setString(3, txtCedula.getText().trim());
            ps.setString(4, textoONull(txtCorreo));
            ps.setString(5, textoONull(txtTelefono));
            ps.setString(6, txtCargo.getText().trim());
            ps.setString(7, cmbDepartamento.getValue());
            ps.setDouble(8, Double.parseDouble(txtSalario.getText().trim()));
            ps.setDate(9, Date.valueOf(dpFechaContratacion.getValue()));
            ps.setString(10, cmbEstado.getValue());

            ps.executeUpdate();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                    "Empleado guardado correctamente.");
            limpiarFormulario();
            cargarEmpleados();

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo guardar el empleado:\n" + e.getMessage());
        }
    }

    private boolean validarCampos() {
        if (txtNombres.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Ingrese los nombres.");
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Ingrese los apellidos.");
            return false;
        }
        if (txtCedula.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Ingrese la cédula.");
            return false;
        }
        if (txtCargo.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Ingrese el cargo.");
            return false;
        }
        if (cmbDepartamento.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Seleccione un departamento.");
            return false;
        }
        try {
            Double.parseDouble(txtSalario.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "El salario debe ser un número válido.");
            return false;
        }
        if (dpFechaContratacion.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Seleccione la fecha de contratación.");
            return false;
        }
        if (cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación",
                    "Seleccione un estado.");
            return false;
        }
        return true;
    }

    @FXML
    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        txtCedula.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtCargo.clear();
        cmbDepartamento.setValue(null);
        txtSalario.clear();
        dpFechaContratacion.setValue(null);
        cmbEstado.setValue(null);
    }

    @FXML
    private void actualizarTabla() {
        cargarEmpleados();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private String textoONull(TextField campo) {
        String texto = campo.getText().trim();
        return texto.isEmpty() ? null : texto;
    }
}

