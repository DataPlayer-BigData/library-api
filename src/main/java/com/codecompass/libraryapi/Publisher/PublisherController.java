package com.codecompass.libraryapi.Publisher;

import com.codecompass.libraryapi.Exception.LibraryResourceBadRequestException;
import com.codecompass.libraryapi.Exception.LibraryResourceNotFoundException;
import com.codecompass.libraryapi.util.LibraryApiUtils;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecompass.libraryapi.Exception.LibraryResourceAlreadyExistException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.UUID;


@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private PublisherService publisherService;
    private static Logger logger = LoggerFactory.getLogger(PublisherController.class);

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
                                          @RequestHeader(value="Trace-Id",defaultValue="") String traceId)
            throws LibraryResourceNotFoundException {
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        return new ResponseEntity<>(publisherService.getPublisher(publisherId,traceId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addPublisher(@Valid @RequestBody  Publisher publisher,
                                          @RequestHeader(value="Trace-Id",defaultValue="") String traceId)
            throws LibraryResourceAlreadyExistException {

        logger.debug("Request to add Publisher: {}",publisher);
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }
        logger.debug("Added TraceId: {}",traceId);

        Publisher publihser = publisherService.addPublisher(publisher,traceId);

        logger.debug("Returning response for TraceId: {}",traceId);
        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }


    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @Valid @RequestBody Publisher publisher,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId)
            throws LibraryResourceNotFoundException {
        //return new Publisher(publisherId,"Prentice Hall","prentice@email.com","123-456-789");
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }


            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher,traceId);

        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId)
            throws LibraryResourceNotFoundException {
        //return new Publisher(publisherId,"Prentice Hall","prentice@email.com","123-456-789");
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

            publisherService.deletePublisher(publisherId,traceId);

        return new ResponseEntity<>("Publisher Id : " + publisherId + "delete successfully.", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String name,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId)
            throws LibraryResourceBadRequestException {

        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        if(!LibraryApiUtils.doesStringValueExist(name)){
            throw new LibraryResourceBadRequestException(traceId,"Please enter name to search Publisher");

        }

        return new ResponseEntity<>(publisherService.searchPublisher(name,traceId), HttpStatus.OK);
    }


}
