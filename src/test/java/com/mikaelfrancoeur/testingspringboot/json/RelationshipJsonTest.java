package com.mikaelfrancoeur.testingspringboot.json;

import java.io.IOException;
import java.io.StringReader;

import org.assertj.core.api.WithAssertions;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;

@JsonTest
class RelationshipJsonTest implements WithAssertions {

    @Autowired
    private JacksonTester<Relationship> jacksonTester;

    @Test
    void internalRoundTrip() throws IOException {
        // language=JSON
        String json = """
                {
                  "tuple": "123-456",
                  "relationshipType": "int-friendship"
                }
                """;
        ObjectContent<Relationship> objectContent = jacksonTester.read(new StringReader(json));
        JsonContent<Relationship> jsonContent = jacksonTester.write(objectContent.getObject());

        // language=JSON
        String expected = """
                {
                  "sourceId": "123",
                  "destinationId": "456",
                  "typeSuffix": "friendship"
                }
                """;
        assertThat(jsonContent).isEqualToJson(expected);
    }

    @Test
    void externalRoundTrip() throws JSONException, IOException {
        // language=JSON
        String json = """
                {
                  "tuple": "123-456",
                  "relationshipType": "ext-friendship"
                }
                """;
        ObjectContent<Relationship> objectContent = jacksonTester.read(new StringReader(json));

        JsonContent<Relationship> jsonContent = jacksonTester.write(objectContent.getObject());
        // language=JSON
        String expected = """
                {
                  "sourceId": "1xx",
                  "destinationId": "4xx"
                }
                """;
        JSONAssert.assertEquals(expected, jsonContent.getJson(), true);
    }
}
