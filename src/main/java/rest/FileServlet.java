package rest;

import com.google.gson.Gson;
import model.EventEntity;
import model.UserEntity;
import repository.hibernate.HibernateEventRepositoryImpl;
import repository.hibernate.HibernateUserRepositoryImpl;
import service.EventService;
import service.FileService;
import model.FileEntity;
import repository.hibernate.HibernateFileRepositoryImpl;
import service.UserService;
import utils.GsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@MultipartConfig
@WebServlet(
        name = "FileServlet",
        urlPatterns = { "/api/v1/files"}
)
public class FileServlet extends HttpServlet {
    private final Gson GSON = GsonUtils.getGson();
    private FileService fileService;
    private EventService eventService;

    public void init() {
        fileService = new FileService(new HibernateFileRepositoryImpl());
        eventService = new EventService(new HibernateEventRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String idStr = req.getParameter("id");
        String result;
        if (!Objects.isNull(idStr)) {
            FileEntity file = fileService.getById(Integer.parseInt(idStr));
            result = GSON.toJson(file);
        } else {
            List<FileEntity> files = fileService.getAll();
            result = GSON.toJson(files);
        }
        writer.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = resp.getWriter();
        String userId = req.getHeader("UserID");
        String result = null;

        Part filePart = req.getPart("file");
        if (filePart != null) {
            String fileName = getSubmittedFileName(filePart);
            String FILE_PATH = "D:/stazh_java/stazhirovka/files_for_postman_test";
            File file = new File(FILE_PATH, fileName);
            if (!Objects.isNull(userId)) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(Integer.parseInt(userId));
                FileEntity fileEntity = fileService.create(fileName, file.getAbsolutePath());
                EventEntity eventEntity = eventService.create(userEntity, fileEntity);
                result = GSON.toJson(fileEntity);
            }
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
        String filePath = req.getParameter("filePath");
        if (!Objects.isNull(idStr)) {
            FileEntity file = fileService.getById(Integer.parseInt(idStr));
            if (!Objects.isNull(name)) {
                file = fileService.update(file, name, "name");
            } else if (!Objects.isNull(filePath)) {
                file = fileService.update(file, filePath, "filePath");
            }
            result = GSON.toJson(file);
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
            fileService.deleteById(Integer.parseInt(idStr));
            result = "success";
        }
        writer.println(result);
    }

    @Override
    public void destroy() {}

    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
