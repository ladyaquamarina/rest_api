package rest;

import com.google.gson.Gson;
import service.UserService;
import model.EventEntity;
import model.UserEntity;
import repository.hibernate.HibernateUserRepositoryImpl;
import utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(
        name = "UserServlet",
        urlPatterns = { "/api/v1/users"}
)
public class UserServlet extends HttpServlet {
    private UserService userService;
    private final Gson GSON = GsonUtils.getGson();

    public void init() {
        userService = new UserService(new HibernateUserRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String result;
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            UserEntity user = userService.getById(Integer.parseInt(idStr));
            result = GSON.toJson(user);
        } else {
            List<UserEntity> users = userService.getAll();
            result = GSON.toJson(users);
        }
        writer.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String result = null;
        String name = req.getParameter("name");
        if (!Objects.isNull(name)) {
            UserEntity user = userService.create(name);
            result = GSON.toJson(user);
        }
        writer.println(result);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String result = null;
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String eventId = req.getParameter("eventId");
        if (!Objects.isNull(idStr)) {
            UserEntity user = userService.getById(Integer.parseInt(idStr));
            if (!Objects.isNull(name)) {
                user = userService.update(user, name);
                result = GSON.toJson(user);
            } else if (!Objects.isNull(eventId)) {
                int id = Integer.parseInt(eventId);
                List<EventEntity> events = user.getEvents().stream().filter(e -> e.getId() == id).toList();
                EventEntity event = events.size() > 0 ? events.get(0) : null;
                if (event == null) {
                    event = new EventEntity();
                    event.setId(id);
                    user = userService.addEvent(user, event);
                } else {
                    user = userService.deleteEvent(user, id);
                }
                result = GSON.toJson(user);
            }
        }
        writer.println(result);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String result = null;
        String idStr = req.getParameter("id");
        if (!Objects.isNull(idStr)) {
            userService.deleteById(Integer.parseInt(idStr));
            result = "success";
        }
        writer.println(result);
    }

    @Override
    public void destroy() {}
}
