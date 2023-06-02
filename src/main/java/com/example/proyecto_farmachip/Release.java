package com.example.proyecto_farmachip;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Clases.Doctor;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import java.io.IOException;

@WebServlet(name = "Release", value = "/Release")
public class Release extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String session = request.getParameter("session");
        String idXip = request.getParameter("idXip");
        String mailP = request.getParameter("paciente");
        String medicamento = request.getParameter("medicamento");
        String date = request.getParameter("fecha");

        Doctor doctor = new Doctor();
        //Para comprobar la fecha es correcta

        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaIntro = LocalDate.parse(date);

        try {
            if (doctor.isLogged(email,session)){
                if (fechaIntro.isAfter(fechaActual)){
                    boolean respuesta = doctor.darAlta(Integer.parseInt(idXip),email,Integer.valueOf(medicamento),mailP,fechaIntro);
                    if (respuesta){
                        response.getWriter().write("ok");
                    }
                }else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Fecha no válida");
                }

            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Credenciales inválidos");
            }
        } catch (SQLException e) {
            System.out.println("Error en PacienteServlet2.doGet" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
        }
    }
}