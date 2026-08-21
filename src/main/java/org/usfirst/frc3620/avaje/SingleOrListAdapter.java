package org.usfirst.frc3620.avaje;
import io.avaje.json.JsonReader;
import io.avaje.json.JsonWriter;
import io.avaje.json.JsonAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleOrListAdapter extends JsonAdapter<List<String>> {

  @Override
  public void toJson(JsonWriter writer, List<String> value) {
    // Standard serialization as a JSON array
    writer.beginArray();
    if (value != null) {
      for (String s : value) {
        writer.value(s);
      }
    }
    writer.endArray();
  }

  @Override
  public List<String> fromJson(JsonReader reader) {
    List<String> list = new ArrayList<>();
    
    // Check if the next token is an actual array
    if (reader.peek() == JsonReader.Token.BEGIN_ARRAY) {
      reader.beginArray();
      while (reader.hasNext()) {
        list.add(reader.nextString());
      }
      reader.endArray();
    } else {
      // It is a single value, coerce it into the array list
      list.add(reader.nextString());
    }
    return list;
  }
}
