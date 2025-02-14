package com.mikaelfrancoeur.testingspringboot.cache;

public interface MessageFactory {
    String getMessage(BeanWithCache.Descriptor descriptor);
}
