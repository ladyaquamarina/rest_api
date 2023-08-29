package servlets;

import model.Event;
import model.User;
import repository.UserRepository;
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

public class UserServlet extends HttpServlet {
    private final UserRepository userRepository = new HibernateUserRepositoryImpl();

    public void init() {}

    public User create(String name) {
        if (name.isEmpty())
            return null;
        User user = new User(name);
        return userRepository.create(user);
    }

    public User getById(int id) {
        if (id < 1)
            return null;
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User update(User user, String newName) {
        if ((newName.isEmpty()) || (getById(user.getId()) == null))
            return null;
        user.setName(newName);
        return userRepository.update(user);
    }

    public User addEvent(User user, Event event) {
        if (getById(user.getId()) == null)
            return null;
        user.addEvent(event);
        return userRepository.update(user);
    }

    public User deleteEvent(User user, int id) {
        if ((id < 1) || (getById(user.getId()) == null))
            return null;
        user.deleteEvent(id);
        return userRepository.update(user);
    }

    public void deleteById(int id) {
        if ((id < 1) || (getById(id) == null))
            return;
        userRepository.deleteById(id);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            User user = getById(Integer.parseInt(idStr));
            if (!Objects.isNull(user)) {
                writer.println("<h1>User " + user.toString() + "</h1>");
            } else {
                writer.println("<h1>User is not exist</h1>");
            }
        } else {
            writer.println("<h1>List of all users</h1>");
            AtomicInteger i = new AtomicInteger(1);
            getAll().forEach(user -> writer.println("<br/><b>User " + i.getAndIncrement() + "</b> " + user.toString()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String name = req.getParameter("name");
        if (!Objects.isNull(name)) {
            User user = create(name);
            writer.println("<h1>User has been successfully created!</h1><br/>Your new user: " + user.toString());
        } else {
            writer.println("<h1>User has not been created</h1>");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (Objects.isNull(idStr)) {
            writer.println("<h1>User has not been updated</h1>");
            return;
        }
        User user = getById(Integer.parseInt(idStr));
        if (user == null) {
            writer.println("<h1>User has not been updated</h1>");
            return;
        }

        String name = req.getParameter("name");
        String eventId = req.getParameter("eventId");
        if (!Objects.isNull(name)) {
            user = update(user, name);
            writer.println("<h1>User has been successfully updated!</h1><br/>Updated user: " + user.toString());
        } else if (!Objects.isNull(eventId)) {
            int id = Integer.parseInt(eventId);
            List<Event> events = user.getEvents().stream().filter(e -> e.getId() == id).toList();
            Event event = events.size() > 0 ? events.get(0) : null;
            if (event == null) {
                event = new Event();
                event.setId(id);
                user = addEvent(user, event);
                writer.println("<h1>User has been successfully updated!</h1><br/>Updated user: " + user.toString());
            } else {
                user = deleteEvent(user, id);
                writer.println("<h1>User has been successfully updated!</h1><br/>Updated user: " + user.toString());
            }
        } else {
            writer.println("<h1>User has not been updated</h1>");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            deleteById(Integer.parseInt(idStr));
            writer.println("<h1>User has been deleted!</h1>");
        } else {
            writer.println("<h1>User has not been deleted</h1>");
        }
    }

    @Override
    public void destroy() {}
}
