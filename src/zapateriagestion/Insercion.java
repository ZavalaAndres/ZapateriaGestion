/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zapateriagestion;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import patrones_Diseño.DatabaseConnection;

/**
 *
 * @author Nanes
 */
public class Insercion {

    private String tabla;
    private ArrayList<String> tupla;

    public Insercion(String tabla, ArrayList<String> tupla) {
        this.tabla = tabla;
        this.tupla = tupla;
    }

    public void inserta() {
        try
        {
            String query = "insert into " + tabla + " values(";
            DatabaseConnection data = DatabaseConnection.getInstance();
            //"{insert into Proveedor values(?,?,?,?,?)}"
            for (int i = 0; i < tupla.size(); i++)
            {
                if (i == tupla.size() - 1)
                {
                    query = query + "?)";
                } else
                {
                    query = query + "?,";
                }
            }
            // insert into tablaax values ?, ?, ?, ??
            PreparedStatement ps = data.getConnection().prepareStatement(query);
            for (int i = 0; i < tupla.size(); i++)
            {
                if (validaNumeros(tupla.get(i)))
                {
                    JOptionPane.showMessageDialog(null, "Numero");
                    ps.setInt(i + 1, Integer.parseInt(tupla.get(i)));
                } else if (validaFloat(tupla.get(i)))
                {
                    JOptionPane.showMessageDialog(null, "Float");
                    ps.setFloat(i + 1, Float.parseFloat(tupla.get(i)));
                } else
                {
                    JOptionPane.showMessageDialog(null, tupla.get(i));
                    ps.setString(i + 1, tupla.get(i));
                }
            }
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Se ha registrado correctamente");
        } catch (SQLException ex)
        {
            Logger.getLogger(NewApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    

    private boolean validaNombres(String a) {
        a = a.toLowerCase();
        if (a.matches("[a-z]*"))
        {
            return true;
        }
        return false;
    }

    private boolean validaNumeros(String a) {
        if (a.matches("[0-9]*"))
        {
            return true;
        }
        return false;
    }

    private boolean validaFecha(String a) {

        if (a.split("/").length == 3)
        {
            return true;
        }
        return false;
    }

    private boolean validaFloat(String a) {
        try
        {
            Float.parseFloat(a);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }

}
