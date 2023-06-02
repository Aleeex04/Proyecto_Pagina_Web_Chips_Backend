package com.example.proyecto_farmachip;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import Clases.Doctor;
import Clases.Paciente;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PacienteServlet", value = "/PacienteServlet")
public class PacienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String session = request.getParameter("session");
        Doctor doctor = new Doctor();
        try {
            if (doctor.isLogged(email,session)){
                List<Paciente> listaPacientes = doctor.listaPacientes();
                Gson gson = new Gson();
                String pacientes = gson.toJson(listaPacientes);
                response.setHeader("Content-Type", "application/json");
                response.getWriter().write(pacientes);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Credenciales inválidos");
            }
        } catch (SQLException e) {
            System.out.println("Error en PacienteServlet3.doGet" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
        }

    }


}