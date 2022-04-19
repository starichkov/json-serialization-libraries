package org.starichkov.java.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Using <a href='https://code.google.com/archive/p/json-simple/'>Yidong Fang</a>'s
 * <a href='https://github.com/fangyidong/json-simple'>json-simple</a> library.
 * <p>
 * Library is almost as awkward as Oracle's one.
 * You'll have to write own methods for encoding\decoding to\from JSON all possible object's children.
 * </p>
 *
 * @author Vadim Starichkov
 * @since 20.12.2016 14:12
 */
public class JsonSimpleTest extends BaseTest {
    @Test
    public void testJsonSimple() {
        JSONObject jsonObject = createJsonEntity(entity);

        String json = jsonObject.toJSONString();

        BaseEntity entityFromJson = new BaseEntity();
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(json);
            read(object, entityFromJson);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        checkAsserts(entity, entityFromJson);
    }

    private JSONObject createJsonEntity(BaseEntity entity) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", entity.getId());
        jsonObject.put("name", entity.getName());
        if (!entity.getChildren().isEmpty()) {
            jsonObject.put("children", getChildren(entity.getChildren()));
        }
        return jsonObject;
    }

    private JSONArray getChildren(List<BaseEntity> children) {
        JSONArray jsonArray = new JSONArray();
        for (BaseEntity child : children) {
            jsonArray.add(createJsonEntity(child));
        }
        return jsonArray;
    }

    private void read(Object object, BaseEntity root) {
        if (!isJsonObject(object)) {
            return;
        }

        JSONObject jsonObject = (JSONObject) object;
        for (Object keyObject : jsonObject.keySet()) {
            if (isString(keyObject)) {
                String key = (String) keyObject;
                Object value = jsonObject.get(key);
                switch (key) {
                    case "id":
                        if (isNumber(value)) {
                            root.setId(((Number) value).intValue());
                        }
                        break;
                    case "name":
                        if (isString(value)) {
                            root.setName((String) value);
                        }
                        break;
                    case "children":
                        if (isJsonArray(value)) {
                            JSONArray array = (JSONArray) value;
                            List<BaseEntity> children = new ArrayList<>(array.size());
                            array.forEach(o -> {
                                BaseEntity child = new BaseEntity();
                                read(o, child);
                                children.add(child);
                            });
                            root.setChildren(children);
                        }
                        break;
                }
            }
        }
    }

    private static boolean isNumber(Object object) {
        return Number.class.isAssignableFrom(object.getClass());
    }

    private static boolean isString(Object object) {
        return String.class.isAssignableFrom(object.getClass());
    }

    private static boolean isJsonArray(Object object) {
        return JSONArray.class.isAssignableFrom(object.getClass());
    }

    private static boolean isJsonObject(Object object) {
        return JSONObject.class.isAssignableFrom(object.getClass());
    }
}
