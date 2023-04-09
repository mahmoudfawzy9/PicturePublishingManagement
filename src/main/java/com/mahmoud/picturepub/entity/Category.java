package com.mahmoud.picturepub.entity;

import java.util.HashMap;
import java.util.Map;

public enum Category {
    LIVING_THING("Living Thing","Anything that's alive e.g animals, trees and insects."),
    MACHINE("Machine","Man-made inventions and tools."),
    NATURE("Nature","Mother nature.");

    private final String label;
    private final String description;

    Category(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public static final Map<String, Category> LABELS = new HashMap<>();

    static {
        for (Category e: values()) {
            LABELS.put(e.label, e);
        }
    }

    public static Category valueOfLabel(String label) {
        return LABELS.get(label);
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
