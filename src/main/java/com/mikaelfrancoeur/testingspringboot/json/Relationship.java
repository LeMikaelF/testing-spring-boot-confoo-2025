package com.mikaelfrancoeur.testingspringboot.json;

import java.time.LocalDate;

record Relationship(String sourceId, String destinationId, LocalDate createdDate) {

}
