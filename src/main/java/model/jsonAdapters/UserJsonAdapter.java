package model.jsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.UserEntity;

import java.io.IOException;

public class UserJsonAdapter extends TypeAdapter<UserEntity> {
    @Override
    public void write(JsonWriter jsonWriter, UserEntity user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(user.getId());
        jsonWriter.name("name");
        jsonWriter.value(user.getName());
        jsonWriter.endObject();
    }

    @Override
    public UserEntity read(JsonReader jsonReader) throws IOException {
        return null;
    }
}