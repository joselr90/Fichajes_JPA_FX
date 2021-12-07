module dam.jlr.fichajes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.naming;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires javafaker;
    //requires swing



    opens dam.jlr.fichajes0 to javafx.fxml,org.hibernate.orm.core,javafx.base;
    opens dam.jlr.fichajes0.model to org.hibernate.orm.core,javafx.base;
    exports dam.jlr.fichajes0;
}