package dao;

import java.sql.*;

public class Database {
    private Connection connection;
    private Statement statement;

    public Database() {
    }

    public Database(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;
    }


    //Metoso que me ejecuta los updates
    public void loadUpdate(String query){
        ResultSet rs;
        Statement st;
        rs = null;
        try {
            st = connection.createStatement();
            int rowsAffected = st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error a Database.loadUpdate" + e.getMessage());
        }
    }



    public void initDatabaseConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("Error en BBDD.conectat.Class " + e.getMessage());
        }
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/farmacia","alexr","alex2004");
        }catch (SQLException e){
            System.out.println("Error BBDD.conectar.Connection " + e.getMessage());
        }
        this.statement = null;
        try {
            this.statement = connection.createStatement();
        }catch (SQLException e){
            System.out.println("BBDD.conectar.statement " + e.getMessage());
        }
    }
    public   void closeDatabaseConnection() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar el objeto Statement: " + e.getMessage());
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}

