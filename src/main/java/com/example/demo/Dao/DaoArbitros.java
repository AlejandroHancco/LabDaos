package com.example.demo.Dao;
import com.example.demo.Beans.Arbitro;

import java.sql.*;
import java.util.ArrayList;

public class DaoArbitros extends DaoBase {


    public ArrayList<Arbitro> listarArbitros(){
        ArrayList<Arbitro> lista = new ArrayList<>();
        String sql = "SELECT * FROM arbitro";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery())
             {
                 while (rs.next()) {
                 Arbitro arbitro = new Arbitro();
                 arbitro.setIdArbitro(rs.getInt("idArbitro"));
                 arbitro.setNombre(rs.getString("nombre"));
                 arbitro.setPais(rs.getString("pais"));
                 lista.add(arbitro);
                 }

        }catch (SQLException e){throw new RuntimeException(e);}


        return lista;

    }

    public ArrayList<Arbitro> busquedaPais(String busqueda){
        ArrayList<Arbitro> lista = new ArrayList<>();
        String sql = "select * from arbitro where pais like ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,  busqueda + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));
                    lista.add(arbitro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public ArrayList<Arbitro> busquedaNombre(String busqueda){
        ArrayList<Arbitro> lista = new ArrayList<>();
        String sql = "select * from arbitro where nombre like ?";
        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + busqueda + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Arbitro arbitro = new Arbitro();
                    arbitro.setIdArbitro(rs.getInt("idArbitro"));
                    arbitro.setNombre(rs.getString("nombre"));
                    arbitro.setPais(rs.getString("pais"));
                    lista.add(arbitro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public void crearArbitro(Arbitro arbitro){
        String sql = "insert into arbitro (idArbitro,nombre,pais) values (?,?,?)";
        try (Connection conn =this.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, arbitro.getIdArbitro());
            pstmt.setString(2, arbitro.getNombre());
            pstmt.setString(3, arbitro.getPais());
            pstmt.executeUpdate();

        }catch(SQLException e){throw new RuntimeException(e);}

    }
    public Arbitro buscarporId(int id){
        Arbitro arbitro = null;
        String sql = "select * from arbitro where idArbitro = ?";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
        pstmt.setInt(1, id);

        try(ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {
                arbitro = new Arbitro();
                arbitro.setIdArbitro(rs.getInt("idArbitro"));
                arbitro.setNombre(rs.getString("nombre"));
                arbitro.setPais(rs.getString("pais"));

            }
        }
        }catch(SQLException e){throw new RuntimeException(e);
        }
        return arbitro;
    }

    public void borrarArbitro(int id){
        String sql = "delete from arbitro where idArbitro = ?";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1,id);
            pstmt.executeUpdate();

        }catch(SQLException e){throw new RuntimeException(e);}

    }

    public int giveIdArbitrobyName(String nombre){
        int id = 0;
        String sql = "select * from arbitro where nombre = ?";
        try (Connection conn =this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, nombre);

            try(ResultSet rs = pstmt.executeQuery()){

                while (rs.next()) {
                    id =rs.getInt("idArbitro");

                }
            }
        }catch(SQLException e){throw new RuntimeException(e);
        }
        return id;
    }



}
