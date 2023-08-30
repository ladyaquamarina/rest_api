package servlets;

import controller.FileController;
import model.File;
import repository.hibernate.HibernateFileRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FileServlet extends HttpServlet {
    private FileController fileController;

    public void init() {
        fileController = new FileController(new HibernateFileRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            File file = fileController.getById(Integer.parseInt(idStr));
            if (!Objects.isNull(file)) {
                writer.println("<h1>File " + file.toString() + "</h1>");
            } else {
                writer.println("<h1>File is not exist</h1>");
            }
        } else {
            writer.println("<h1>List of all files</h1>");
            AtomicInteger i = new AtomicInteger(1);
            fileController.getAll().forEach(file -> writer.println("<br/><b>File " + i.getAndIncrement() + "</b> " + file.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");
        if (!Objects.isNull(name) && !Objects.isNull(filePath)) {
            File file = fileController.create(name, filePath);
            writer.println("<h1>File has been successfully created!</h1><br/>Your new file: " + file.toString());
        } else {
            writer.println("<h1>File has not been created</h1>");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (Objects.isNull(idStr)) {
            writer.println("<h1>File has not been updated</h1>");
            return;
        }
        File file = fileController.getById(Integer.parseInt(idStr));
        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");
        if (!Objects.isNull(name)) {
            file = fileController.update(file, name, "name");
            writer.println("<h1>File has been successfully updated!</h1><br/>Updated file: " + file.toString());
        } else if (!Objects.isNull(filePath)) {
            file = fileController.update(file, filePath, "filePath");
            writer.println("<h1>File has been successfully updated!</h1><br/>Updated file: " + file.toString());
        } else {
            writer.println("<h1>File has not been updated</h1>");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            fileController.deleteById(Integer.parseInt(idStr));
            writer.println("<h1>File has been deleted!</h1>");
        } else {
            writer.println("<h1>File has not been deleted</h1>");
        }
    }

    @Override
    public void destroy() {}
}
