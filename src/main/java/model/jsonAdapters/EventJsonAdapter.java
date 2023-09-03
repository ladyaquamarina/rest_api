package model.jsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.EventEntity;

import java.io.IOException;

public class EventJsonAdapter extends TypeAdapter<EventEntity> {
    @Override
    public void write(JsonWriter jsonWriter, EventEntity event) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("eventId");
        jsonWriter.value(event.getId());
        jsonWriter.name("userId");
        jsonWriter.value(event.getUser().getId());
        jsonWriter.name("fileId");
        jsonWriter.value(event.getFile().getId());
        jsonWriter.endObject();
    }

    @Override
    public EventEntity read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
