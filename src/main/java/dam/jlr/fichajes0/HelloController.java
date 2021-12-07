package dam.jlr.fichajes0;

import com.github.javafaker.Faker;
import dam.jlr.fichajes0.model.Empleado;
import dam.jlr.fichajes0.model.Fichaje;
import dam.jlr.fichajes0.util.HibernateUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.hibernate.Session;
import org.w3c.dom.events.Event;
import java.sql.Date;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import javax.persistence.*;



public class HelloController implements Initializable {
    private Session session;
    private EntityManager entityMgr;
    private boolean activados=true;
    private boolean desactivados=false;

    @FXML
    private TableColumn colFichajes;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private Button btnAlta;
    @FXML
    private TreeTableView treetable;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colId;
    @FXML
    private Button btnModificar;
    @FXML
    private Button generarButton;
    private Faker faker;
    @FXML
    private CheckBox activadosCheck;
    @FXML
    private CheckBox desactivadosCheck;
    @FXML
    private ContextMenu contextMenu;


    @FXML
    public void borrar(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
            Empleado miEmpleado=(Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
            System.out.println("Id ="+miEmpleado.getId());
            System.out.println("Nombre ="+miEmpleado.getNombre());
            System.out.println("Apellido ="+miEmpleado.getApellido());
           for (Fichaje fichaje:miEmpleado.getFichajes()){
               System.out.println(fichaje.toString());
           }

            session.beginTransaction();
            session.delete(miEmpleado);

            session.getTransaction().commit();
        }



        session.close();
        entityMgr.close();
       cargarTabla();
    }
public void alta(ActionEvent actionEvent) {
    this.session = HibernateUtil.getSessionFactory().openSession();
    this.entityMgr = session.getEntityManagerFactory().createEntityManager();
    Empleado empleado = new Empleado();
    empleado.setActivo(true);
    empleado.setNombre(txtNombre.getText());
    empleado.setApellido(txtApellidos.getText());

    entityMgr.getTransaction().begin();
    entityMgr.persist(empleado);
    entityMgr.getTransaction().commit();

    cargarTabla();
    session.close();
    entityMgr.close();
}
    @Deprecated
    public void clicTabla(javafx.scene.input.MouseEvent mouseEvent) {
        Empleado miEmpleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        if (miEmpleado != null) {
            txtId.setText(Integer.toString(miEmpleado.getId()));
            txtNombre.setText(miEmpleado.getNombre());
            txtApellidos.setText(miEmpleado.getApellido());


        }


    }

    @FXML
    public void modificar(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        //modify element in the table
        Empleado empleado = new Empleado();
        empleado.setId(Integer.parseInt(txtId.getText()));
        empleado.setNombre(txtNombre.getText());
        empleado.setApellido(txtApellidos.getText());


        entityMgr.getTransaction().begin();
        entityMgr.merge(empleado);
        entityMgr.getTransaction().commit();

        session.close();
        entityMgr.close();
        cargarTabla();
    }

    @FXML
    public void buscarEmpleadoNombre(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        //search by nombre and apellidos
        Query query = entityMgr.createQuery("from Empleado where nombre like :nombre and apellido like :apellido");
        query.setParameter("nombre", "%" + txtNombre.getText() + "%");
        query.setParameter("apellido", "%" + txtApellidos.getText() + "%");
        List<Empleado> empleados = query.getResultList();
        tblEmpleados.getItems().clear();
        tblEmpleados.getItems().addAll(empleados);
        session.close();
        entityMgr.close();
    }
    public void buscarEmpleadoNombre2() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        //search by nombre and apellidos
        Query query = entityMgr.createQuery("from Empleado where nombre like :nombre and apellido like :apellido");
        query.setParameter("nombre", "%" + txtNombre.getText() + "%");
        query.setParameter("apellido", "%" + txtApellidos.getText() + "%");
        List<Empleado> empleados = query.getResultList();
        tblEmpleados.getItems().clear();
        tblEmpleados.getItems().addAll(empleados);
        session.close();
        entityMgr.close();
        //add listener to tblEmpleados


    }

    @Deprecated
    public void autocompletar(Event event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale locale = new Locale("es", "ES");
        faker = new Faker(locale);
        activadosCheck.setSelected(true);
        desactivadosCheck.setSelected(false);
        cargarTabla2();
        txtId.setEditable(false);
        System.out.println("HelloController.initialize()");
       this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();



        System.out.println("Record Successfully Inserted In The Database");

        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        colFichajes.setCellValueFactory(new PropertyValueFactory<Empleado, Set>("fichajes"));
        changeListenerTxtflds();
entityMgr.close();
session.close();
//if double click on table
        tblEmpleados.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    Empleado miEmpleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
                    if (miEmpleado != null) {
                        txtId.setText(Integer.toString(miEmpleado.getId()));
                        txtNombre.setText(miEmpleado.getNombre());
                        txtApellidos.setText(miEmpleado.getApellido());
            }
        }



    }
});

        //table listener selected item
      //selected item listener
        tblEmpleados.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                Empleado empleado = (Empleado) newValue;
                if (empleado != null) {
                    if (empleado.isActivo()==true){
                        contextMenu.getItems().get(1).setVisible(false);
                        contextMenu.getItems().get(2).setDisable(false);
                        contextMenu.getItems().get(1).setDisable(true);
                        contextMenu.getItems().get(2).setVisible(true);
                        System.out.println("1= "+contextMenu.getItems().get(1).getText());
                        System.out.println("0 = "+contextMenu.getItems().get(0).getText());
                        System.out.println("2 = "+contextMenu.getItems().get(2).getText());
                        contextMenu.getItems().get(1).setDisable(true);
                    }else if (empleado.isActivo()==false){
                        contextMenu.getItems().get(1).setVisible(true);
                        contextMenu.getItems().get(2).setVisible(false);
                        contextMenu.getItems().get(2).setDisable(true);
                        contextMenu.getItems().get(1).setDisable(false);
                        System.out.println("1= "+contextMenu.getItems().get(1).getText());
                        System.out.println("0 = "+contextMenu.getItems().get(0).getText());
                        System.out.println("2 = "+contextMenu.getItems().get(2).getText());
                    }
                }

            }

        }
        );
    }
    public void cargarTabla(){
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        entityMgr.getTransaction().begin();
        //query to get all the records from the table fichajes
        Query query = entityMgr.createQuery("from Fichaje ");




       //query to get all the records from the table

        List<Empleado> empleados = entityMgr.createQuery("SELECT e FROM Empleado e").getResultList();
        entityMgr.getTransaction().commit();
        tblEmpleados.getItems().setAll(empleados);
        session.close();
        entityMgr.close();
        System.out.println("Tabla cargada");

    }
    public void clicTabla(ActionEvent actionEvent) {
        Empleado miEmpleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        if (miEmpleado != null) {
            txtId.setText(Integer.toString(miEmpleado.getId()));
            txtNombre.setText(miEmpleado.getNombre());
            txtApellidos.setText(miEmpleado.getApellido());
        }else{
            txtId.setText("");
            txtNombre.setText("");
            txtApellidos.setText("");
        }
    }
private void changeListenerTxtflds(){

    txtNombre.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
           buscarEmpleadoNombre2();
        }
    });
    txtApellidos.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            buscarEmpleadoNombre2();


        }
    });
}


    public void clicTabla2(javafx.scene.input.MouseEvent mouseEvent) {
        //if left click on the table
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED && mouseEvent.getButton() == MouseButton.PRIMARY) {
            Empleado miEmpleado = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
            if (miEmpleado != null) {
                txtId.setText(Integer.toString(miEmpleado.getId()));
                txtNombre.setText(miEmpleado.getNombre());
                txtApellidos.setText(miEmpleado.getApellido());
                System.out.println(miEmpleado.getNombre()+" "+miEmpleado.getApellido());
            } else {
                txtId.setText("");
                txtNombre.setText("");
                txtApellidos.setText("");
            }
        }
    }

    @FXML
    public void generar(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        entityMgr.getTransaction().begin();

        Empleado farmObj = new Empleado();
        Fichaje fichaje = new Fichaje();
        Random rnd = new Random();
        if(rnd.nextBoolean()){
        fichaje.setTipo("Entrada");
        }else {
            fichaje.setTipo("Salida");
        }
        fichaje.setFecha(new Date(System.currentTimeMillis()));
        fichaje.setHora(new Time(System.currentTimeMillis()));
        fichaje.setEmpleado(farmObj);
        farmObj.setActivo(false);
        Set<Fichaje> set = new HashSet<>();
        set.add(fichaje);
        farmObj.setFichajes(set);


        farmObj.setNombre(faker.name().firstName());
        farmObj.setApellido(faker.name().lastName()+" "+faker.name().lastName());
        farmObj.getFichajes().forEach(role -> entityMgr.persist(role));
        entityMgr.persist(farmObj);

        entityMgr.getTransaction().commit();


        cargarTabla2();
        entityMgr.close();
        session.close();

    }



@FXML
public void activar(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        entityMgr.getTransaction().begin();
        Empleado farmObj = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
    System.out.println("Probandooooo");
    System.out.println(farmObj.toString());
    farmObj.setActivo(true);

        entityMgr.merge(farmObj);
        session.update(farmObj);
        entityMgr.getTransaction().commit();
        entityMgr.close();
        session.close();
        cargarTabla2();
    }
    @FXML
    public void desactivar(ActionEvent actionEvent) {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();
        entityMgr.getTransaction().begin();
        Empleado farmObj = (Empleado) tblEmpleados.getSelectionModel().getSelectedItem();
        farmObj.setActivo(false);

        entityMgr.merge(farmObj);
        entityMgr.getTransaction().commit();
        entityMgr.close();
        session.close();
        cargarTabla2();
    }
    @FXML
    public void switchDesactivados(ActionEvent actionEvent) {
//        desactivadosCheck.setSelected(true);
        desactivados=desactivadosCheck.isSelected();
        System.out.println(desactivados);
        cargarTabla2();
    }

    @FXML
    public void switchActivados(ActionEvent actionEvent) {
       activados=activadosCheck.isSelected();
       System.out.println(activados);
        cargarTabla2();


    }
    public void cargarTabla2() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.entityMgr = session.getEntityManagerFactory().createEntityManager();

        //query to get all the records from the table fichajes
        Query query = entityMgr.createQuery("from Empleado");


        //query to get all records with the status of true
        if (!desactivados && !activados) {
//             entityMgr.getTransaction().begin();
//            query = entityMgr.createQuery("from Empleado");
//            List<Empleado> todos = query.getResultList();
//            entityMgr.getTransaction().commit();
//            tblEmpleados.setItems(FXCollections.observableArrayList(todos));
            tblEmpleados.setItems(null);

        }else if (desactivados && activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado");
            List<Empleado> todos = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(todos));

        }else if (!desactivados && activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado where activo=true");
            List<Empleado> activados = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(activados));

        }else if (desactivados && !activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado where activo=false");
            List<Empleado> desactivados = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(desactivados));

        }else if (desactivados && activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado");
            List<Empleado> todos = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(todos));

        }else if (!desactivados && activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado where activo=true");
            List<Empleado> activados = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(activados));

        }else if (desactivados && !activados) {
            entityMgr.getTransaction().begin();
            query = entityMgr.createQuery("from Empleado where activo=false");
            List<Empleado> desactivados = query.getResultList();
            entityMgr.getTransaction().commit();
            tblEmpleados.setItems(FXCollections.observableArrayList(desactivados));

        }


    session.close();
    entityMgr.close();

    }



}
