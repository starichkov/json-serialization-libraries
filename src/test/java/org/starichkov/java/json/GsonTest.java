package org.starichkov.java.json;

import com.google.gson.Gson;
import org.junit.Test;

/**
 * @author Vadim Starichkov
 * @since 16.12.2016 17:53
 */
public class GsonTest extends BaseTest {
    @Test
    public void testGson() {
        Gson gson = new Gson();

        String json = gson.toJson(entity);

        BaseEntity entityFromJson = gson.fromJson(json, BaseEntity.class);

        checkAsserts(entity, entityFromJson);
    }
}
