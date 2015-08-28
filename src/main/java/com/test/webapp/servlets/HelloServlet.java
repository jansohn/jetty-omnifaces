package com.test.webapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.webapp.Hello;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet
{
	private static Logger log = LoggerFactory.getLogger(HelloServlet.class);
	private static final long serialVersionUID = 6222837567462922081L;
    @Inject
	Hello hello;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	log.info("Received call to HelloServlet!");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<HTML>");
        out.println("<HEAD><TITLE>Bootstrap CDI</TITLE></HEAD>");
        out.println("<BODY>");
        out.println(hello.sayHelloWorld());
        out.println("</BODY>");
        out.println("</HTML>");
        out.close();
    }
}