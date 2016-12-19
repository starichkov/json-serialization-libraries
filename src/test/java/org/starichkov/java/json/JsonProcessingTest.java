package org.starichkov.java.json;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Using Oracle's <a href='https://jsonp.java.net/'>JSON Processing</a> library - the most awkward JSON library to use.
 * <p>
 * All serialization logic for containers and non-primitives your should write by yourself as you can see below.
 * </p>
 *
 * @author Vadim Starichkov
 * @since 19.12.2016 18:08
 */
public class JsonProcessingTest extends BaseTest {
    @Test
    public void testJsonProcessing() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", entity.getId());
        builder.add("name", entity.getName());
        writeChildren(builder, entity);
        JsonObject object = builder.build();

        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(object);
        }

        String jsonData = stringWriter.toString();
        System.out.println(jsonData);

        JsonReader reader = Json.createReader(new StringReader(jsonData));
        JsonStructure jsonStructure = reader.read();
        System.out.println(jsonStructure);
    }

    private void writeChildren(JsonObjectBuilder builder, BaseEntity parent) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (BaseEntity child : parent.getChildren()) {
            JsonObjectBuilder childBuilder = Json.createObjectBuilder();
            childBuilder
                    .add("id", child.getId())
                    .add("name", child.getName());

            if (!child.getChildren().isEmpty()) {
                writeChildren(childBuilder, child);
            }

            arrayBuilder.add(childBuilder.build());
        }

        builder.add("children", arrayBuilder);
    }

    private void readChildren() {
        // TODO
    }
}
