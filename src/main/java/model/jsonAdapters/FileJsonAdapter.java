package model.jsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.FileEntity;

import java.io.IOException;

public class FileJsonAdapter extends TypeAdapter<FileEntity> {
    @Override
    public void write(JsonWriter jsonWriter, FileEntity file) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(file.getId());
        jsonWriter.name("name");
        jsonWriter.value(file.getName());
        jsonWriter.name("filePath");
        jsonWriter.value(file.getFilePath());
        jsonWriter.endObject();
    }

    @Override
    public FileEntity read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
