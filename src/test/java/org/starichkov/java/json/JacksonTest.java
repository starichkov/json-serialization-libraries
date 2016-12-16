package org.starichkov.java.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Vadim Starichkov
 * @since 16.12.2016 17:52
 */
public class JacksonTest extends BaseTest {
    @Test
    public void testJackson() {
        StringWriter stringWriter = new StringWriter();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(stringWriter, entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = stringWriter.toString();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BaseEntity entityFromJson = objectMapper.readValue(json, BaseEntity.class);
            checkAsserts(entity, entityFromJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
