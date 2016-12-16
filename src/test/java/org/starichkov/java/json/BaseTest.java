package org.starichkov.java.json;

import org.junit.Before;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Vadim Starichkov
 * @since 16.12.2016 17:54
 */
abstract class BaseTest {
    BaseEntity entity;

    @Before
    public void init() {
        Random random = new Random();
        entity = new BaseEntity(random.nextInt(100500), "Test Entity");
        for (int i = 0; i < 5; i++) {
            entity.addChild(new BaseEntity(random.nextInt(100500) + i, "Test Child Entity"));
        }
    }

    void checkAsserts(BaseEntity expected, BaseEntity actual) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getChildren().size(), expected.getChildren().size());
        for (int i = 0; i < actual.getChildren().size(); i++) {
            BaseEntity childFromJson = actual.getChildren().get(i);
            BaseEntity child = expected.getChildren().get(i);
            assertEquals(childFromJson.getId(), child.getId());
            assertEquals(childFromJson.getName(), child.getName());
            assertEquals(childFromJson.getChildren().size(), child.getChildren().size());
        }
    }
}
