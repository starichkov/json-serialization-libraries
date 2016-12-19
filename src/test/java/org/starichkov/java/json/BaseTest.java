package org.starichkov.java.json;

import org.junit.Before;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Vadim Starichkov
 * @since 16.12.2016 17:54
 */
abstract class BaseTest {
    private Random random;
    BaseEntity entity;

    @Before
    public void init() {
        random = new Random();
        entity = new BaseEntity(random.nextInt(100500), "Test Entity");
        addChildren(entity, 3);
        entity.getChildren().forEach(child -> addChildren(child, 3));
    }

    private void addChildren(BaseEntity parent, int count) {
        for (int i = 0; i < count; i++) {
            BaseEntity child = new BaseEntity(random.nextInt(100500) + i, "Test Child Entity");
            parent.addChild(child);
        }
    }

    void checkAsserts(BaseEntity expected, BaseEntity actual) {
        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getChildren().size(), expected.getChildren().size());
        checkChildren(expected.getChildren(), actual.getChildren());
    }

    private void checkChildren(List<BaseEntity> expectedChildren, List<BaseEntity> actualChildren) {
        for (int i = 0; i < actualChildren.size(); i++) {
            BaseEntity childFromJson = actualChildren.get(i);
            BaseEntity child = expectedChildren.get(i);
            assertEquals(childFromJson.getId(), child.getId());
            assertEquals(childFromJson.getName(), child.getName());
            assertEquals(childFromJson.getChildren().size(), child.getChildren().size());
            if (!child.getChildren().isEmpty()) {
                checkChildren(child.getChildren(), childFromJson.getChildren());
            }
        }
    }
}
