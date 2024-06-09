package com.example.demo.Servlets;

import com.example.demo.Beans.Arbitro;
import com.example.demo.Dao.DaoArbitros;
import com.example.demo.Dao.DaoSelecciones;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name="ArbitroServlet", urlPatterns = {"/ArbitroServlet"})
public class ArbitroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "lista" : req.getParameter("action");
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
            case "buscar":
                String textSearch = req.getParameter("buscar");
                String tipo = req.getParameter("tipo");
                if ("Nombre".equals(tipo)) {
                    lista = arbitroDaos.busquedaNombre(textSearch);
                } else if ("Pais".equals(tipo)) {
                    lista = arbitroDaos.busquedaPais(textSearch);
                }
                req.setAttribute("listarArbitros", lista);
                req.setAttribute("buscar", textSearch);
                req.getRequestDispatcher("arbitros/list.jsp").forward(req, resp);

                break;
            case "guardar":
                Arbitro arbitro = new Arbitro();
                arbitro.setNombre(req.getParameter("nombre"));
                arbitro.setPais(req.getParameter("pais"));
                arbitroDaos.crearArbitro(arbitro);
                resp.sendRedirect(req.getContextPath()+"/ArbitroServlet");
                break;

        }

    }

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    DaoArbitros arbitroDao = new DaoArbitros();
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
            req.setAttribute("listarArbitros", arbitroDao.listarArbitros());
            view = req.getRequestDispatcher("arbitros/list.jsp");
            view.forward(req, resp);
            break;
        case "crear":
            view =req.getRequestDispatcher("arbitros/form.jsp");
            view.forward(req, resp);
            break;
        case "borrar":
            String idd= req.getParameter("id");
            if(arbitroDao.buscarporId(Integer.parseInt(idd)) != null){
                arbitroDao.borrarArbitro(Integer.parseInt(idd));
        }
            resp.sendRedirect(req.getContextPath()+"/ArbitroServlet");
        break;
    }
}

}