package com.example.demo.Servlets;

import com.example.demo.Beans.Arbitro;
import com.example.demo.Beans.Jugador;
import com.example.demo.Dao.DaoArbitros;
import com.example.demo.Dao.DaoJugador;
import com.example.demo.Dao.DaoSelecciones;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name="JugadorServlet", urlPatterns = {"/JugadorServlet"})
public class JugadorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");

        DaoJugador jugadorDao = new DaoJugador();
        RequestDispatcher view;
        DaoArbitros arbitroDaos = new DaoArbitros();
        ArrayList<String> opciones = new ArrayList<>();
        ArrayList<Arbitro> lista = new ArrayList<>();

        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");

        opciones.add("Nombre");
        opciones.add("Pais");
        req.setAttribute("opciones", opciones);
        req.setAttribute("paises", paises);

        switch (action) {

            case "guardar":
                Jugador jugador = new Jugador();
                jugador.setNombre(req.getParameter("nombre"));
                jugador.setEdad(Integer.parseInt(req.getParameter("edad")));
                jugador.setClub(req.getParameter("club"));
                jugador.setPosicion(req.getParameter("posicion"));
                jugador.setSn_idSeleccion(Integer.parseInt(req.getParameter("pais")));

                jugadorDao.crearJugador(jugador);
                resp.sendRedirect(req.getContextPath()+"/JugadorServlet");
                break;

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DaoArbitros arbitroDao = new DaoArbitros();
        DaoJugador jugadorDao = new DaoJugador();
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
        RequestDispatcher view;
        ArrayList<String> paises = new ArrayList<>();
        paises.add("Peru");
        paises.add("Chile");
        paises.add("Argentina");
        paises.add("Paraguay");
        paises.add("Uruguay");
        paises.add("Colombia");
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Nombre");
        opciones.add("Pais");
        req.setAttribute("paises", paises);
        req.setAttribute("opciones", opciones);
        switch (action){
            case "lista":
                req.setAttribute("listarJugadores", jugadorDao.listarJugador());
                view = req.getRequestDispatcher("jugador/list.jsp");
                view.forward(req, resp);
                break;
            case "crear":
                view =req.getRequestDispatcher("jugador/form.jsp");
                view.forward(req, resp);
                break;
            case "borrar":
                String idd= req.getParameter("id");
                if(jugadorDao.buscarporId(Integer.parseInt(idd)) != null){
                    jugadorDao.borrarJugador(Integer.parseInt(idd));
                }
                resp.sendRedirect(req.getContextPath()+"/JugadorServlet");
                break;
        }
    }

}