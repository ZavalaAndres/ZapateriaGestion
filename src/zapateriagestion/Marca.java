/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zapateriagestion;

import java.sql.CallableStatement;
import javax.swing.JOptionPane;
import patrones_Diseño.DatabaseConnection;

/**
 *
 * @author Andre
 */
public class Marca {
    String marca;
    public Marca(String marca){
        this.marca=marca;
        inicializa();
    }
    public void inicializa(){
        try {
            DatabaseConnection c=DatabaseConnection.getInstance();
            CallableStatement pc=c.getConnection().prepareCall("{call insertarMarca(?)}");
            System.out.println("ola");
            pc.setString(1, marca);
            pc.execute();
            pc.close();
            JOptionPane.showMessageDialog(null,"Registro exitoso");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: "+e);
        }
    }
}
