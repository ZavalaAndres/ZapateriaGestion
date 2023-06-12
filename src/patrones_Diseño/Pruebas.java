/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patrones_Diseño;

import java.sql.*;
import javax.swing.JTable;

/**
 *
 * @author Nanes
 */
public class Pruebas {
    public static void main(String args[]) throws SQLException 
    { 
        DatabaseConnection  a = DatabaseConnection.getInstance();
        DatabaseConnection  b = DatabaseConnection.getInstance();
        DatabaseConnection  c = DatabaseConnection.getInstance();
        
        
        JTable sa = a.llenaTabla("select * from Inventario");
        sa.setVisible(true);
        System.out.println("Hashcode de a es " + a.hashCode()); 
        System.out.println("Hashcode de b es " + b.hashCode()); 
        System.out.println("Hashcode de c es " + c.hashCode()); 
  
        // Condition check 
        if (a == b && b == c) { 
            // Print statement 
            System.out.println( 
                "Solo existe una conexion a la base de datos."); 
        }else { 
            // Print statement 
            System.out.println( 
                "Existen varias conexciones a la base de datos."); 
        } 
    }
}
