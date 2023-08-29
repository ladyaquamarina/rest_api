package servlets;

import model.File;
import repository.FileRepository;
import repository.hibernate.HibernateFileRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FileServlet extends HttpServlet {
    private final FileRepository fileRepository = new HibernateFileRepositoryImpl();

    public void init() {}

    public File create(String name, String filePath) {
        if (name.isEmpty() || filePath.isEmpty())
            return null;
        File file = new File(name, filePath);
        return fileRepository.create(file);
    }

    public File getById(int id) {
        if (id < 1)
            return null;
        return fileRepository.getById(id);
    }

    public List<File> getAll() {
        return fileRepository.getAll();
    }

    public File update(File file, String newValue, String field) {
        if ((newValue.isEmpty()) || (getById(file.getId()) == null))
            return null;
        if (Objects.equals(field, "name")) {
            file.setName(newValue);
        } else {
            file.setFilePath(newValue);
        }
        return fileRepository.update(file);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        fileRepository.deleteById(id);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            File file = getById(Integer.parseInt(idStr));
            if (!Objects.isNull(file)) {
                writer.println("<h1>File " + file.toString() + "</h1>");
            } else {
                writer.println("<h1>File is not exist</h1>");
            }
        } else {
            writer.println("<h1>List of all files</h1>");
            AtomicInteger i = new AtomicInteger(1);
            getAll().forEach(file -> writer.println("<br/><b>File " + i.getAndIncrement() + "</b> " + file.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");
        if (!Objects.isNull(name) && !Objects.isNull(filePath)) {
            File file = create(name, filePath);
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
        File file = getById(Integer.parseInt(idStr));
        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");
        if (!Objects.isNull(name)) {
            file = update(file, name, "name");
            writer.println("<h1>File has been successfully updated!</h1><br/>Updated file: " + file.toString());
        } else if (!Objects.isNull(filePath)) {
            file = update(file, filePath, "filePath");
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
            deleteById(Integer.parseInt(idStr));
            writer.println("<h1>File has been deleted!</h1>");
        } else {
            writer.println("<h1>File has not been deleted</h1>");
        }
    }

    @Override
    public void destroy() {}
}
