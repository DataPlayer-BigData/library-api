package com.codecompass.libraryapi.Publisher;

import com.codecompass.libraryapi.Exception.LibraryResourceAlreadyExistException;
import com.codecompass.libraryapi.Exception.LibraryResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher addPublisher(Publisher publisherToBeAdded)
            throws LibraryResourceAlreadyExistException {

        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber()
        );

        PublisherEntity addedPublisher = null;
        try {
            addedPublisher = publisherRepository.save(publisherEntity);
        } catch (DataIntegrityViolationException e) {
            throw new LibraryResourceAlreadyExistException("Publisher already exists.");
        }

        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
        return publisherToBeAdded;
    }

    public Publisher getPublisher(Integer publisherId)
    throws LibraryResourceNotFoundException {

        Publisher publisher = null;
        Optional<PublisherEntity> publisherOptional = publisherRepository.findById(publisherId);

        if (publisherOptional.isPresent()) {
            publisher = new Publisher(publisherOptional.get().getPublisherId(), publisherOptional.get().getName(), publisherOptional.get().getEmailId(), publisherOptional.get().getPhoneNumber());
        } else {
            throw new LibraryResourceNotFoundException("Publisher Id: " + publisherId + " Not Found");
        }
        return publisher;
     }
}









