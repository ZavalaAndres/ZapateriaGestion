/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones_Diseño;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Nanes
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;
    private String NAMEDB = "zapateria";
    private String USER = "root";
    private String CLAVE = "sasa";
    private String URL = "jdbc:mysql://localhost:3306/" + NAMEDB;
    
    /**
     * 
     */
    private DatabaseConnection(){
        try {   
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL,USER,CLAVE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No hay conexion a la base de datos de " + NAMEDB + ".");
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    
    public static DatabaseConnection getInstance() throws SQLException{
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public static JComboBox llenaCombo(String query, JComboBox selector) {
        selector.removeAllItems();
        selector.addItem("selecciona");
        if (connection != null) {
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    selector.addItem(rs.getString(1));
                }
                return selector;
            } catch (Exception e) {
                System.out.println("error en llenar combo" + e);
            }
        } else {
            System.out.println("no hay conexion a la base");
        }
        return null;
    }

    public static JTable llenaTabla(String query) {
        String titulos[] = new String[]{"producto", "cantidad", "precio"};
        String datos[][] = new String[5][3];

        JTable tabla = new JTable(datos, titulos);
        if (connection != null) {
            try {
                Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    int columnas = rs.getMetaData().getColumnCount();
                    rs.last();
                    int fila = rs.getRow();
                    titulos = new String[columnas];
                    datos = new String[fila][columnas];
                    for (int i = 0; i < columnas; i++) {
                        titulos[i] = rs.getMetaData().getColumnName(i + 1);
                    }
                    rs.first();
                    for (int i = 0; i < fila; i++) {
                        for (int j = 0; j < columnas; j++) {
                            datos[i][j] = rs.getString(j + 1);
                        }
                        rs.next();
                    }
                    tabla = new JTable(datos, titulos);
                    return tabla;
                }
            } catch (Exception e) {
                System.out.println("Error al llenar tabla: " + e);
            }
        }
        return tabla;
    }

    public static String llenaArchivo(String query,String reporte) {
        LocalDate fecha= LocalDate.now();
        File f = new File(reporte +fecha.toString()+ ".xls");
        try {
            FileWriter fw = new FileWriter(f, false);
            if (connection != null) {
                try {
                    Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rs = st.executeQuery(query);
                    if (rs.next()) {
                        int columnas = rs.getMetaData().getColumnCount();
                        rs.last();
                        int fila = rs.getRow();
                        for (int i = 0; i < columnas; i++) {
                            fw.write(rs.getMetaData().getColumnName(i + 1) + "\t");
                        }
                        rs.first();
                        fw.write("\t");
                        for (int i = 0; i < fila; i++) {
                            for (int j = 0; j < columnas; j++) {
                                System.out.print(rs.getString(j + 1) + "\t");
                                fw.write(rs.getString(j + 1) + "\t");
                            }
                            rs.next();
                            System.out.println("");
                        }
                        fw.write("\t");
                        fw.close();
                        System.out.print("\t");
                        return "archivo creado";
                    }
                } catch (Exception e) {
                    System.out.println("Error al llenar tabla: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("error al llenar archivo" + e);
        }
        return null;
    }
    public static String codigo(char t,int num){
       if(num<10)
           return t+"00"+num;
       if(num<100)
           return t+"0"+num;
       return t+""+num;
    }
    public static String exportarExcel(JTable x,String nombreReporte){
        int filas = x.getRowCount();
        int columnas = x.getColumnCount();
        System.out.println(filas + "    " + columnas);
        try {
            LocalDate fecha= LocalDate.now();
            JFileChooser destino = new JFileChooser();
            destino.setSelectedFile(new File(nombreReporte+fecha.toString()+".xls"));
            destino.showSaveDialog(null);
            destino.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
            File f = destino.getSelectedFile();
            
            FileWriter fw = new FileWriter(f,true);
            for (int i = 0; i < columnas; i++) {
                fw.write(x.getColumnName(i)+ "\t");
                System.out.print(x.getColumnName(i)+ "\t");
            }
            System.out.println("");
            fw.write("\n");
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    fw.write(x.getValueAt(i,j).toString()+ "\t");
                    System.out.print(x.getValueAt(i,j).toString()+ "\t");
                }
                fw.write("\n");
                System.out.println("");
            }
            fw.close();
            return "Archivo almacenado";
        } catch (Exception e) {
            //System.out.println("Error alguardar el archivo de excel");
            return "Error al almacenar" + e;
        }
    }
}


