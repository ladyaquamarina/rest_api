package servlets;

import controller.EventController;
import controller.FileController;
import controller.UserController;
import model.Event;
import model.File;
import model.User;
import repository.hibernate.HibernateEventRepositoryImpl;
import repository.hibernate.HibernateFileRepositoryImpl;
import repository.hibernate.HibernateUserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class EventServlet extends HttpServlet {
    private EventController eventController;
    private FileController fileController;
    private UserController userController;

    public void init() {
        eventController = new EventController(new HibernateEventRepositoryImpl());
        fileController = new FileController(new HibernateFileRepositoryImpl());
        userController = new UserController(new HibernateUserRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            Event event = eventController.getById(Integer.parseInt(idStr));
            if (!Objects.isNull(event)) {
                writer.println("<h1>Event " + event.toString() + "</h1>");
            } else {
                writer.println("<h1>Event is not exist</h1>");
            }
        } else {
            writer.println("<h1>List of all events</h1>");
            AtomicInteger i = new AtomicInteger(1);
            eventController.getAll().forEach(event -> writer.println("<br/><b>Event " + i.getAndIncrement() + "</b> " + event.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (Objects.isNull(userId) || Objects.isNull(fileId)) {
            writer.println("<h1>Event has not been created</h1>");
        } else if ((fileController.getById(Integer.parseInt(fileId)) == null)
                || (userController.getById(Integer.parseInt(userId)) == null)) {
            writer.println("<h1>Event has not been created</h1>");
        } else {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            File file = new File();
            file.setId(Integer.parseInt(fileId));
            Event event = eventController.create(user, file);
            writer.println("<h1>Event has been successfully created!</h1><br/>Your new event: " + event.toString());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (Objects.isNull(idStr)) {
            writer.println("<h1>Event has not been updated</h1>");
            return;
        }
        Event event = eventController.getById(Integer.parseInt(idStr));
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (!Objects.isNull(userId)) {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            event = eventController.update(event, user);
            writer.println("<h1>Event has been successfully updated!</h1><br/>Updated event: " + event.toString());
        } else if (!Objects.isNull(fileId)) {
            File file = new File();
            file.setId(Integer.parseInt(fileId));
            event = eventController.update(event, file);
            writer.println("<h1>Event has been successfully updated!</h1><br/>Updated event: " + event.toString());
        } else {
            writer.println("<h1>Event has not been updated</h1>");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            eventController.deleteById(Integer.parseInt(idStr));
            writer.println("<h1>Event has been deleted!</h1>");
        } else {
            writer.println("<h1>Event has not been deleted</h1>");
        }
    }

    @Override
    public void destroy() {}
}
