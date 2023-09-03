package rest;

import com.google.gson.Gson;
import service.EventService;
import service.FileService;
import service.UserService;
import model.EventEntity;
import model.FileEntity;
import model.UserEntity;
import repository.hibernate.HibernateEventRepositoryImpl;
import repository.hibernate.HibernateFileRepositoryImpl;
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
        name = "EventServlet",
        urlPatterns = { "/api/v1/events"}
)
public class EventServlet extends HttpServlet {
    private EventService eventService;
    private FileService fileService;
    private UserService userService;
    private final Gson GSON = GsonUtils.getGson();

    public void init() {
        eventService = new EventService(new HibernateEventRepositoryImpl());
        fileService = new FileService(new HibernateFileRepositoryImpl());
        userService = new UserService(new HibernateUserRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        String result;
        if (!Objects.isNull(idStr)) {
            EventEntity event = eventService.getById(Integer.parseInt(idStr));
            result = GSON.toJson(event);
        } else {
            List<EventEntity> events = eventService.getAll();
            result = GSON.toJson(events);
        }
        writer.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        String result = null;
        if (!Objects.isNull(userId) && !Objects.isNull(fileId)) {
            if (fileService.getById(Integer.parseInt(fileId)) != null
                    && userService.getById(Integer.parseInt(userId)) != null) {
                UserEntity user = new UserEntity();
                user.setId(Integer.parseInt(userId));
                FileEntity file = new FileEntity();
                file.setId(Integer.parseInt(fileId));
                EventEntity event = eventService.create(user, file);
                result = GSON.toJson(event);
            }
        }
        writer.println(result);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        String result = null;
        if (!Objects.isNull(idStr)) {
            EventEntity event = eventService.getById(Integer.parseInt(idStr));
            String userId = req.getParameter("userId");
            String fileId = req.getParameter("fileId");
            if (!Objects.isNull(userId)) {
                UserEntity user = new UserEntity();
                user.setId(Integer.parseInt(userId));
                event = eventService.update(event, user);
                result = GSON.toJson(event);
            } else if (!Objects.isNull(fileId)) {
                FileEntity file = new FileEntity();
                file.setId(Integer.parseInt(fileId));
                event = eventService.update(event, file);
                result = GSON.toJson(event);
            }
        }
        writer.println(result);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        String result = null;
        if (!Objects.isNull(idStr)) {
            eventService.deleteById(Integer.parseInt(idStr));
            result = "success";
        }
        writer.println(result);
    }

    @Override
    public void destroy() {}
}
