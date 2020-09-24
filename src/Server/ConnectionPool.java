/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Manan
 */
public class ConnectionPool {

    private static ConnectionPool connectionPool = null;

    private Vector freeConnections; // La coda di connessioni libere
    private String dbUrl;           // Il nome del database
    private String dbDriver;        // Il driver del database
    private String dbLogin;         // Il login per il database
    private String dbPassword;      // La password di accesso al database

    private ConnectionPool() throws ClassNotFoundException, SQLException {
        // Costruisce la coda delle connessioni libere
        freeConnections = new Vector();

        // Carica I parametric per l'accesso alla base di dati
        loadParameters();

        // Carica il driver del database
        loadDriver();
    }

    // Funzione privata che carica i parametri per l'accesso al database
    private void loadParameters() {
        // Url per un database locale
        dbUrl = "jdbc:mysql://localhost:3306/Social_Project";
        // Driver per database mysql
        dbDriver = "com.mysql.jdbc.Driver";
        // Login della base di dati
        dbLogin = "root";
        // Password per l'accesso al database
        dbPassword = "";
    }

    // Funzione privata che carica il driver per l'accesso al database.
// In caso di errore durante il caricamento del driver solleva un'eccezione.
    private void loadDriver() {
        try {
            java.lang.Class.forName(dbDriver);
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Errore connessione al driver");
            ex.printStackTrace();
        }
    }

    public static synchronized ConnectionPool getConnectionPool() throws ClassNotFoundException, SQLException {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public synchronized Connection getConnection() {

        Connection con;

        if (freeConnections.size() > 0) {

            // Se la coda delle connessioni libere non è vuota
            // preleva il primo elemento e lo rimuove dalla coda
            con = (Connection) freeConnections.firstElement();
            freeConnections.removeElementAt(0);

            try {
                // Verifica se la connessione non è più valida
                if (con.isClosed()) {
                    // Richiama getConnection ricorsivamente
                    con = getConnection();
                }
            } catch (SQLException e) {
                // Se c'è un errore richiama GetConnection
                // ricorsivamente
                con = getConnection();
            }
        } else {
            // se la coda delle connessioni libere è vuota
            // crea una nuova connessione
            con = newConnection();
        }

        // restituisce la connessione
        return con;
    }

    private Connection newConnection() {

        Connection con = null;

        try {
            // crea la connessione
            con = DriverManager.getConnection(dbUrl,dbLogin,dbPassword);
        } catch (SQLException e) {
        }
        // restituisce la nuova connessione
        return con;
    }

    // Il metodo releaseConnection rilascia una connessione inserendola
// nella coda delle connessioni libere
    public synchronized void releaseConnection(Connection con) {
        // Inserisce la connessione nella coda
        freeConnections.add(con);
    }
}
