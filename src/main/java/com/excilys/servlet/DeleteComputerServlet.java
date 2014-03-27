package main.java.com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.domain.Computer;
import main.java.com.excilys.service.impl.ServiceFactory;

@WebServlet("/delete")
public class DeleteComputerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Computer comp = ServiceFactory.INSTANCE.getComputerService().getOneComputer(Integer.valueOf(req.getParameter("id")));
		req.setAttribute("computer", comp);
		
		getServletContext().getRequestDispatcher("/WEB-INF/deleteComputer.jsp").forward(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServiceFactory.INSTANCE.getComputerService().deleteComputer(Integer.valueOf(req.getParameter("id")));
		resp.sendRedirect("dashboard");
	}
}
