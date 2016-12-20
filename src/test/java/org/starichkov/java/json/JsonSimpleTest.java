package org.starichkov.java.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

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

        // TODO - implement parsing

        BaseEntity entityFromJson = new BaseEntity();
        try {
            JSONParser parser = new JSONParser();

            Object object = parser.parse(json);
            if (isJsonObject(object)) {
                JSONObject jsonObject1 = (JSONObject) object;
                for (Object keyObject : jsonObject1.keySet()) {
                    /*if (isString(keyObject)) {
                        String key = (String) keyObject;
                        switch (key) {
                            case "id":
                                entityFromJson.setId(object.getInt(key));
                                break;
                            case "name":
                                entityFromJson.setName(object.getString(key));
                                break;
                            case "children":
                                JsonValue jsonValue = object.get(key);
                                if (JsonValue.ValueType.ARRAY == jsonValue.getValueType()) {
                                    fillEntityFromJson(jsonValue, root);
                                }
                                break;
                        }
                    }*/
                    Object valueObject = jsonObject1.get(keyObject);
                    System.out.println("[" + keyObject.getClass().getSimpleName() + "] " + keyObject + " > " + "[" + valueObject.getClass().getSimpleName() + "] " + valueObject);
                }
            }

            /*
            JSONArray array = (JSONArray) object;

            JSONObject obj2 = (JSONObject) array.get(1);
            */
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

//        checkAsserts(entity, entityFromJson);
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

    private static boolean isNumber(Object object) {
        return object.getClass().isAssignableFrom(Number.class);
    }

    private static boolean isString(Object object) {
        return object.getClass().isAssignableFrom(String.class);
    }

    private static boolean isJsonArray(Object object) {
        return object.getClass().isAssignableFrom(JSONArray.class);
    }

    private static boolean isJsonObject(Object object) {
        return object.getClass().isAssignableFrom(JSONObject.class);
    }
}
