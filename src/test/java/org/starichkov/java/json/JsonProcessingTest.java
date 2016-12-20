package org.starichkov.java.json;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
        JsonReader reader = Json.createReader(new StringReader(jsonData));
        BaseEntity entityFromJson = new BaseEntity();
        fillEntityFromJson(reader.read(), entityFromJson);
        checkAsserts(entity, entityFromJson);
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

    private void fillEntityFromJson(JsonValue treeNode, BaseEntity root) {
        switch (treeNode.getValueType()) {
            case OBJECT:
                JsonObject object = (JsonObject) treeNode;
                for (String key : object.keySet()) {
                    switch (key) {
                        case "id":
                            root.setId(object.getInt(key));
                            break;
                        case "name":
                            root.setName(object.getString(key));
                            break;
                        case "children":
                            JsonValue jsonValue = object.get(key);
                            if (JsonValue.ValueType.ARRAY == jsonValue.getValueType()) {
                                fillEntityFromJson(jsonValue, root);
                            }
                            break;
                    }
                }
                break;
            case ARRAY:
                JsonArray children = (JsonArray) treeNode;
                List<BaseEntity> childEntities = new ArrayList<>(children.size());

                children.forEach(child -> {
                    BaseEntity childEntity = new BaseEntity();
                    fillEntityFromJson(child, childEntity);
                    childEntities.add(childEntity);
                });

                root.setChildren(childEntities);
                break;
        }
    }
}
