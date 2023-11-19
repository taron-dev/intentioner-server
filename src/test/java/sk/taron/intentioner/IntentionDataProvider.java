package sk.taron.intentioner;

import sk.taron.intentioner.persistence.entity.Intention;

import java.util.UUID;

import static sk.taron.intentioner.CategoryDataProvider.CATEGORY;

/**
 * Provides {@link Intention} related data for tests.
 */
public class IntentionDataProvider {

    private static final Long ID = 1L;
    private static final String TEXT = "text";
    public static final String INTENTION_ID = "2c6eb650-28f1-41b6-8171-8b42d02ab9ab";
    public static final UUID INTENTION_UUID = UUID.fromString(INTENTION_ID);

    public static final Intention INTENTION = new Intention(
        ID,
        TEXT,
        CATEGORY,
        INTENTION_UUID
    );
}
