package com.example.demo.Dao;
import com.example.demo.Beans.Arbitro;
import com.example.demo.Beans.Estadio;
import com.example.demo.Beans.Jugador;

import java.sql.*;
import java.util.ArrayList;

public class DaoEstadio extends DaoBase {


    public ArrayList<Estadio> listarEstadio() {
        ArrayList<Estadio> lista = new ArrayList<>();
        String sql = "SELECT * FROM estadio";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Estadio estadio = new Estadio();
                estadio.setIdEstadio(rs.getInt("idEstadio"));
                estadio.setNombre(rs.getString("nombre"));
                estadio.setProvincia(rs.getString("provincia"));
                estadio.setClub(rs.getString("club"));
                lista.add(estadio);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return lista;

    }

    public void crearEstadio(Estadio estadio){
        String sql = "insert into estadio (idEstadio,nombre,provincia, club) values (?,?,?,?)";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, estadio.getIdEstadio());
            pstmt.setString(2, estadio.getNombre());
            pstmt.setString(3, estadio.getProvincia());
            pstmt.setString(4, estadio.getClub());
            pstmt.executeUpdate();

        }catch(SQLException e){throw new RuntimeException(e);}

    }
    public void borrarEstadio(int id){
        String sql = "delete from estadio where idEstadio = ?";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();

        }catch(SQLException e){throw new RuntimeException(e);}

    }

}