package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import model.jsonAdapters.EventJsonAdapter;
import model.jsonAdapters.FileJsonAdapter;
import model.jsonAdapters.UserJsonAdapter;

public class GsonUtils {
    Gson gson;

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(EventEntity.class, new EventJsonAdapter());
        builder.registerTypeAdapter(UserEntity.class, new UserJsonAdapter());
        builder.registerTypeAdapter(FileEntity.class, new FileJsonAdapter());
        return builder.create();
    }
}
