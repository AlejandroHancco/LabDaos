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

public class DaoPartidos extends DaoBase{
    public ArrayList<Partido> listarPartidos(){
        ArrayList<Partido> lista = new ArrayList<>();
        String sql = "SELECT * FROM partido p "+"left join seleccion s1 on p.seleccionLocal = s1.idSeleccion "+
                "left join seleccion s2 on p.seleccionVisitante = s2.idSeleccion "+
                "LEFT JOIN estadio e1 ON s1.estadio_idEstadio = e1.idEstadio "+
                "left join arbitro a on p.arbitro = a.idArbitro";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery())
        {
            while (rs.next()) {
                Partido partido = new Partido();
                partido.setIdPartido(rs.getInt("idPartido"));
                partido.setFecha(rs.getString("fecha"));
                partido.setNumeroJornada(rs.getInt("numeroJornada"));

                Seleccion seleccionLocal = new Seleccion();
                seleccionLocal.setIdSeleccion(rs.getInt("s1.idSeleccion"));
                seleccionLocal.setNombre(rs.getString("s1.nombre"));

                Estadio estadioLocal = new Estadio();
                estadioLocal.setNombre(rs.getString("e1.nombre"));
                seleccionLocal.setEstadio(estadioLocal);

                partido.setSeleccionLocal(seleccionLocal);

                Seleccion seleccionVisitante = new Seleccion();
                seleccionVisitante.setIdSeleccion(rs.getInt("s2.idSeleccion"));
                seleccionVisitante.setNombre(rs.getString("s2.nombre"));
                partido.setSeleccionVisitante(seleccionVisitante);

                Arbitro arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt("a.idArbitro"));
                arbitro.setNombre(rs.getString("a.nombre"));
                partido.setArbitro(arbitro);

                lista.add(partido);
            }

        }catch (SQLException e){throw new RuntimeException(e);}


        return lista;

    }


    public void crearPartido(Partido partido){
        String sql = "insert into partido (idPartido,seleccionLocal,seleccionVisitante, arbitro, fecha, numeroJornada) " +
                "values (?,?,?,?,?,?)";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, partido.getIdPartido());
            pstmt.setInt(2, partido.getSeleccionLocal().getIdSeleccion());
            pstmt.setInt(3, partido.getSeleccionVisitante().getIdSeleccion());
            pstmt.setInt(4, partido.getArbitro().getIdArbitro());
            pstmt.setString(5, partido.getFecha());
            pstmt.setInt(6, partido.getNumeroJornada());
            pstmt.executeUpdate();

        }catch(SQLException e){throw new RuntimeException(e);}

    }

}
