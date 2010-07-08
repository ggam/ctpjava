package com.ctp.test.db;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.ctp.test.configuration.DatabaseConfiguration;

/**
 * 
 * @author Bartosz Majsak
 *
 */
public class XmlDatasetSeeder implements DataSeeder {    
    
    private DatabaseConnection databaseConnection;

    private final DatabaseConfiguration dbConfig;
    
    private final String xmlFile;
    
    public XmlDatasetSeeder(String xmlFile, DatabaseConfiguration dbConfig) {
        this.dbConfig = dbConfig;
        this.xmlFile = xmlFile;
    }

    @Override
    public void prepare() {
        try {
            setupDatabase();
            applyInitStatement();
            fillDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void cleanup() {
        try {
            setupDatabase();
            cleanDatabase();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void applyInitStatement() {
        try {
            Statement initStatement = databaseConnection.getConnection().createStatement();
            initStatement.execute(dbConfig.getInitStatement());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    private void setupDatabase()
            throws IOException, SQLException, DatabaseUnitException {
       if (null != databaseConnection) {
           return;
       }
       DatabaseConnection con = new DatabaseConnection(DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword()));
       // used to avoid problems with boolean
       con.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new DefaultDataTypeFactory());
       databaseConnection = con;
    }

    private void fillDatabase() throws IOException, SQLException, DatabaseUnitException {
       FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
       URL xmlUrl = getClass().getClassLoader().getResource(xmlFile);
       FlatXmlDataSet fx = flatXmlDataSetBuilder.build(xmlUrl);
       DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, fx);
    }

    private void cleanDatabase() throws DatabaseUnitException, SQLException {
        IDataSet dataSet = databaseConnection.createDataSet();
        DatabaseOperation.DELETE_ALL.execute(databaseConnection, dataSet);
    }
    
}
