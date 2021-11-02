package com.codecompass.libraryapi.Publisher;

import com.codecompass.libraryapi.Exception.LibraryResourceAlreadyExistException;
import com.codecompass.libraryapi.Exception.LibraryResourceNotFoundException;
import com.codecompass.libraryapi.util.LibraryApiUtils;
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

    public void updatePublisher(Publisher publisher) throws LibraryResourceNotFoundException{
        Optional<PublisherEntity> publisherOptional = publisherRepository.findById(publisher.getPublisherId());

        if (publisherOptional.isPresent()) {
            PublisherEntity pe = publisherOptional.get();
            if(LibraryApiUtils.doesStringValueExist(publisher.getEmailId())){
                pe.setEmailId(publisher.getEmailId());
            }
            if(LibraryApiUtils.doesStringValueExist(publisher.getName())){
                pe.setName(publisher.getName());
            }
            if(LibraryApiUtils.doesStringValueExist(publisher.getPhoneNumber())){
                pe.setPhoneNumber(publisher.getPhoneNumber());
            }

            publisherRepository.save(pe);
            //publisher = createPublisherFromEntity(pe);

        } else {
            throw new LibraryResourceNotFoundException("Publisher Id: " + publisher.getPublisherId() + " Not Found");
        }
        //return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(),pe.getName(),pe.getEmailId(),pe.getPhoneNumber());
    }
}









