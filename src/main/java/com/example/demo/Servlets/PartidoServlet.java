package com.example.demo.Servlets;

import com.example.demo.Beans.Arbitro;
import com.example.demo.Beans.Partido;
import com.example.demo.Beans.Seleccion;
import com.example.demo.Dao.DaoArbitros;
import com.example.demo.Dao.DaoPartidos;
import com.example.demo.Dao.DaoSelecciones;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet (name ="PartidoServlet", urlPatterns={"/PartidoServlet"})
public class PartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion") ==null ? "guardar": request.getParameter("action");
        RequestDispatcher view;
        DaoArbitros arbitroDaos = new DaoArbitros();
        DaoSelecciones seleccionDaos = new DaoSelecciones();
        DaoPartidos partidoDaos = new DaoPartidos();
        switch (accion) {
            case "guardar":
                Partido partido = new Partido();
                partido.setNumeroJornada(Integer.parseInt(request.getParameter("jornada")));
                partido.setFecha(request.getParameter("fecha"));

                Seleccion seleccionLocal = new Seleccion();
                seleccionLocal.setNombre(request.getParameter("local"));
                seleccionLocal.setIdSeleccion(seleccionDaos.giveIdSeleccionbyName(request.getParameter("local")));
                partido.setSeleccionLocal(seleccionLocal);

                Seleccion seleccionVisitante = new Seleccion();
                seleccionVisitante.setNombre(request.getParameter("visitante"));
                seleccionVisitante.setIdSeleccion(seleccionDaos.giveIdSeleccionbyName(request.getParameter("visitante")));
                partido.setSeleccionVisitante(seleccionVisitante);

                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(request.getParameter("arbitro"));
                arbitro.setIdArbitro(arbitroDaos.giveIdArbitrobyName(request.getParameter("arbitro")));
                partido.setArbitro(arbitro);
                partidoDaos.crearPartido(partido);
                response.sendRedirect(request.getContextPath()+"/PartidoServlet");
                break;

        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("action") ==null ? "lista": req.getParameter("action");
        DaoArbitros arbitroDao = new DaoArbitros();
        DaoSelecciones seleccionDao = new DaoSelecciones();

        RequestDispatcher view;
        DaoPartidos partidoDao = new DaoPartidos();
        switch (accion) {
            case "lista":
                req.setAttribute("listarPartidos",partidoDao.listarPartidos() );
                view = req.getRequestDispatcher("/partidos/list.jsp");
                view.forward(req, resp);
                break;
            case "crear":
                req.setAttribute("listarArbitros",arbitroDao.listarArbitros());
                req.setAttribute("listarSeleciones",seleccionDao.listarSelecciones());
                view = req.getRequestDispatcher("/partidos/form.jsp");
                view.forward(req, resp);
                break;
        }

    }

}
