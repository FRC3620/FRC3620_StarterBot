// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.usfirst.frc3620.avaje;

import java.time.Instant;

import io.avaje.jsonb.CustomAdapter;
import io.avaje.json.*;

@CustomAdapter
public class CustomDateAdapterFactory implements JsonAdapter.Factory {

  @Override
  public JsonAdapter<?> create(Type type, Jsonb jsonb) {
    if (type.equals(Instant.class)) {
      return new JsonAdapter<Instant>() {
        
        @Override
        public void toJson(Instant value, JsonWriter writer) {
          writer.value(value != null ? value.toString() : null);
        }

        @Override
        public Instant fromJson(JsonReader reader) {
          return Instant.parse(reader.readString());
        }
      };
    }
    return null;
  }
}

