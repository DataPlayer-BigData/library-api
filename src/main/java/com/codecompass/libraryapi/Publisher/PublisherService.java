package com.codecompass.libraryapi.Publisher;

import com.codecompass.libraryapi.Exception.LibraryResourceAlreadyExistException;
import com.codecompass.libraryapi.Exception.LibraryResourceNotFoundException;
import com.codecompass.libraryapi.util.LibraryApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {

    private static Logger logger = LoggerFactory.getLogger(PublisherService.class);
    private PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher addPublisher(Publisher publisherToBeAdded, String traceId)
            throws LibraryResourceAlreadyExistException {

        logger.debug("TraceId: {}, Request to add Publisher: {}",traceId,publisherToBeAdded);
        PublisherEntity publisherEntity = new PublisherEntity(
                publisherToBeAdded.getName(),
                publisherToBeAdded.getEmailId(),
                publisherToBeAdded.getPhoneNumber()
        );

        PublisherEntity addedPublisher = null;
        try {
            addedPublisher = publisherRepository.save(publisherEntity);
        } catch (DataIntegrityViolationException e) {
            logger.error("Trace Id : {} , Publisher already exists!!! {}",traceId,e);
            throw new LibraryResourceAlreadyExistException("Trace Id : " + traceId +", Publisher already exists!!!");
        }

        publisherToBeAdded.setPublisherId(addedPublisher.getPublisherId());
        logger.info("TraceId : {}, Publisher addess successfully: {}", traceId, publisherToBeAdded);
        return publisherToBeAdded;
    }

    public Publisher getPublisher(Integer publisherId, String traceId)
    throws LibraryResourceNotFoundException {

        Publisher publisher = null;
        Optional<PublisherEntity> publisherOptional = publisherRepository.findById(publisherId);

        if (publisherOptional.isPresent()) {
            publisher = new Publisher(publisherOptional.get().getPublisherId(), publisherOptional.get().getName(), publisherOptional.get().getEmailId(), publisherOptional.get().getPhoneNumber());
        } else {
            throw new LibraryResourceNotFoundException("Trace Id : " + traceId +", Publisher Id: " + publisherId + " Not Found");
        }
        return publisher;
     }

    public void updatePublisher(Publisher publisher, String traceId) throws LibraryResourceNotFoundException{
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
            throw new LibraryResourceNotFoundException("Trace Id : " + traceId +", Publisher Id: " + publisher.getPublisherId() + " Not Found");
        }
        //return publisher;
    }

    private Publisher createPublisherFromEntity(PublisherEntity pe) {
        return new Publisher(pe.getPublisherId(),pe.getName(),pe.getEmailId(),pe.getPhoneNumber());
    }

    public void deletePublisher(Integer publisherId, String traceId) throws LibraryResourceNotFoundException {
        try{
            publisherRepository.deleteById(publisherId);
        }catch(EmptyResultDataAccessException e){
            throw new LibraryResourceNotFoundException("Trace Id : " + traceId +", Publisher Id : " + publisherId + " Not Found");
        }
    }


    public List<Publisher> searchPublisher(String name,String traceId) {

        List<PublisherEntity> publisherEntities = null;
        if(LibraryApiUtils.doesStringValueExist(name)){
            publisherEntities = publisherRepository.findByNameContaining(name);
        }
        if(publisherEntities != null && publisherEntities.size()>0 ){
            return createPublishersForSearchResponse(publisherEntities);
        }else{
            return Collections.emptyList();
        }
    }

    private List<Publisher> createPublishersForSearchResponse(List<PublisherEntity> publisherEntities) {
        return publisherEntities.stream()
                .map(pe->createPublisherFromEntity(pe))
                .collect(Collectors.toList());
    }
}









