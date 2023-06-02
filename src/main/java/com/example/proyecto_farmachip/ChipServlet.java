package com.example.proyecto_farmachip;


import Clases.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ChipServlet", value = "/ChipServlet")
public class ChipServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String session = request.getParameter("session");
        Doctor doctor = new Doctor();
        boolean checkLog;

        try {
            checkLog = doctor.isLogged(email,session);
            if (checkLog){
                //Cargo los datos al objeto doctor
                doctor.load(email);
                doctor.loadRealeaseList();
                if (doctor.getRelaseList().isEmpty()){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,"La lista de Chips esta vacía.");
                } else {
                    response.getWriter().write(doctor.getTable());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en doctor.isLogged" + e.getMessage());
        }
    }

}



