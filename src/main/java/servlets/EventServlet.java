package servlets;

import model.Event;
import model.File;
import model.User;
import repository.EventRepository;
import repository.FileRepository;
import repository.UserRepository;
import repository.hibernate.HibernateEventRepositoryImpl;
import repository.hibernate.HibernateFileRepositoryImpl;
import repository.hibernate.HibernateUserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class EventServlet extends HttpServlet {
    private final EventRepository eventRepository = new HibernateEventRepositoryImpl();

    public void init(EventRepository eventRepository) {}

    public Event create(User user, File file) {
        Event event = new Event(user, file);
        return eventRepository.create(event);
    }

    public Event getById(int id) {
        if (id < 1)
            return null;
        return eventRepository.getById(id);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    public Event update(Event event, User user) {
        if (user == null)
            return null;
        event.setUser(user);
        return eventRepository.update(event);
    }

    public Event update(Event event, File file) {
        if (file == null)
            return null;
        event.setFile(file);
        return eventRepository.update(event);
    }

    public void deleteById(int id) {
        if (id < 1)
            return;
        eventRepository.deleteById(id);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            Event event = getById(Integer.parseInt(idStr));
            if (!Objects.isNull(event)) {
                writer.println("<h1>Event " + event.toString() + "</h1>");
            } else {
                writer.println("<h1>Event is not exist</h1>");
            }
        } else {
            writer.println("<h1>List of all events</h1>");
            AtomicInteger i = new AtomicInteger(1);
            getAll().forEach(event -> writer.println("<br/><b>Event " + i.getAndIncrement() + "</b> " + event.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        FileRepository fileRepository = new HibernateFileRepositoryImpl();
        UserRepository userRepository = new HibernateUserRepositoryImpl();
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (Objects.isNull(userId) || Objects.isNull(fileId)) {
            writer.println("<h1>Event has not been created</h1>");
        } else if ((fileRepository.getById(Integer.parseInt(fileId)) == null)
                || (userRepository.getById(Integer.parseInt(userId)) == null)) {
            writer.println("<h1>Event has not been created</h1>");
        } else {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            File file = new File();
            file.setId(Integer.parseInt(fileId));
            Event event = create(user, file);
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
        Event event = getById(Integer.parseInt(idStr));
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (!Objects.isNull(userId)) {
            User user = new User();
            user.setId(Integer.parseInt(userId));
            event = update(event, user);
            writer.println("<h1>Event has been successfully updated!</h1><br/>Updated event: " + event.toString());
        } else if (!Objects.isNull(fileId)) {
            File file = new File();
            file.setId(Integer.parseInt(fileId));
            event = update(event, file);
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
            deleteById(Integer.parseInt(idStr));
            writer.println("<h1>Event has been deleted!</h1>");
        } else {
            writer.println("<h1>Event has not been deleted</h1>");
        }
    }

    @Override
    public void destroy() {}
}
