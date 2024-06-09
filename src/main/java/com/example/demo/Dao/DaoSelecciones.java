package com.example.demo.Dao;

import com.example.demo.Beans.Arbitro;
import com.example.demo.Beans.Estadio;
import com.example.demo.Beans.Partido;
import com.example.demo.Beans.Seleccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoSelecciones extends DaoBase{
    public ArrayList<Seleccion> listarSelecciones (){
        ArrayList<Seleccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM seleccion s "+"left join estadio e on s.estadio_idEstadio = e.idEstadio";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next()) {
                Seleccion seleccion = new Seleccion();
                seleccion.setIdSeleccion(rs.getInt("idSeleccion"));
                seleccion.setNombre(rs.getString("nombre"));
                seleccion.setTecnico(rs.getString("tecnico"));

                Estadio estadio = new Estadio();
                estadio.setIdEstadio(rs.getInt("e.idEstadio"));
                estadio.setNombre(rs.getString("e.nombre"));

                seleccion.setEstadio(estadio);

                lista.add(seleccion);
            }

        }catch (SQLException e){throw new RuntimeException(e);}

        return lista;

    }
    public int giveIdSeleccionbyName(String nombre){
        int id = 0;
        String sql = "select * from seleccion where nombre = ?";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombre);

            try(ResultSet rs = pstmt.executeQuery()){

                while (rs.next()) {
                    id =rs.getInt("idSeleccion");

                }
            }
        }catch(SQLException e){throw new RuntimeException(e);
        }
        return id;
    }

}
