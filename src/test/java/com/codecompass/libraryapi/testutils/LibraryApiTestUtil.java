package com.codecompass.libraryapi.testutils;

import com.codecompass.libraryapi.Publisher.Publisher;
import com.codecompass.libraryapi.Publisher.PublisherEntity;

import java.util.Optional;

public class LibraryApiTestUtil {

    public static Publisher createPublisher(){

        return new Publisher(null,TestConstants.TEST_PUBLISHER_NAME,
                TestConstants.TEST_PUBLISHER_EMALE,
                TestConstants.TEST_PUBLISHER_PHONE);
    }

    public static PublisherEntity createPublisherEntity() {

        return new PublisherEntity(TestConstants.TEST_PUBLISHER_NAME,
                TestConstants.TEST_PUBLISHER_EMALE,
                TestConstants.TEST_PUBLISHER_PHONE);
    }

    public static Optional<PublisherEntity> createPublisherEntityOptional() {
        return Optional.of(createPublisherEntity());
    }
}
