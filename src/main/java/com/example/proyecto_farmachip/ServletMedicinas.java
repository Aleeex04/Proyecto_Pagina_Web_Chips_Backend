package com.example.proyecto_farmachip;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Clases.Doctor;
import Clases.Medicina;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServletMedicinas", value = "/ServletMedicinas")
public class ServletMedicinas extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String session = request.getParameter("session");
        Doctor doctor = new Doctor();

        try {
            if (doctor.isLogged(email,session)){
                List<Medicina> listaMedicinas =doctor.listaMedicinas();
                Gson gson = new Gson();
                String medicinas = gson.toJson(listaMedicinas);
                response.setHeader("Content-Type","application/json");
                response.getWriter().write(medicinas);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Credenciales inválidos");
            }
        } catch (SQLException e) {
            System.out.println("Error en PacienteServlet1.doGet" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
        }
    }

}