package com.mikaelfrancoeur.testingspringboot.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Relationship {
    @Getter
    private final String sourceId;
    @Getter
    private final String destinationId;
    private final String typeSuffix;
    @JsonIgnore
    private final Type type;

    @JsonCreator
    static Relationship fromJson(
            @Pattern(regexp = "\\w+-\\w+")
            String tuple,
            @Pattern(regexp = "(int|ext-.*)")
            @JsonProperty("relationshipType")
            String typeString
    ) {
        String sourceId = tuple.substring(0, tuple.indexOf("-"));
        String destinationId = tuple.substring(tuple.indexOf("-") + 1);
        Type type = typeString.startsWith("int-") ? Type.INTERNAL : Type.EXTERNAL;
        String typeSuffix = typeString.substring("int-".length());

        return new Relationship(sourceId, destinationId, typeSuffix, type);
    }

    @JsonValue
    private Object jsonValue() {
        return switch (type) {
            case INTERNAL -> serializeInternal();
            case EXTERNAL -> serializeExternal();
        };
    }

    private Object serializeInternal() {
        class RelationshipDTO extends Relationship {
            public RelationshipDTO(String sourceId, String destinationId, String typeSuffix, Type type) {
                super(sourceId, destinationId, typeSuffix, type);
            }

            @JsonValue(false)
            private Void jsonValue() {
                return null;
            }
        }
        return new RelationshipDTO(sourceId, destinationId, typeSuffix, type);
    }

    private Object serializeExternal() {
        record RelationshipForExternal(String sourceId, String destinationId) {
        }
        return new RelationshipForExternal(obfuscate(sourceId), obfuscate(destinationId));
    }

    @SuppressWarnings("SuspiciousRegexArgument")
    private String obfuscate(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return str.charAt(0) + str.substring(1).replaceAll(".", "x");
    }

    private enum Type {
        INTERNAL, EXTERNAL;
    }
}
