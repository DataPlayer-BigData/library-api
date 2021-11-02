package com.codecompass.libraryapi.Publisher;

import com.codecompass.libraryapi.Exception.LibraryResourceNotFoundException;
import com.codecompass.libraryapi.util.LibraryApiUtils;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
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

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public ResponseEntity<?> getPublisher(@PathVariable Integer publisherId,
                                          @RequestHeader(value="Trace-Id",defaultValue="") String traceId){
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        Publisher publisher = null;
        try{
            publisher = publisherService.getPublisher(publisherId,traceId);
        }catch(LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> addPublisher(@Valid @RequestBody  Publisher publisher,
                                          @RequestHeader(value="Trace-Id",defaultValue="") String traceId)  {

        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        try {
            Publisher publihser = publisherService.addPublisher(publisher,traceId);
        } catch (LibraryResourceAlreadyExistException e) {
            //e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }


    @PutMapping(path = "/{publisherId}")
    public ResponseEntity<?> updatePublisher(@PathVariable Integer publisherId, @Valid @RequestBody Publisher publisher,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId){
        //return new Publisher(publisherId,"Prentice Hall","prentice@email.com","123-456-789");
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        try{
            publisher.setPublisherId(publisherId);
            publisherService.updatePublisher(publisher,traceId);
        }catch(LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(publisher, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{publisherId}")
    public ResponseEntity<?> deletePublisher(@PathVariable Integer publisherId,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId){
        //return new Publisher(publisherId,"Prentice Hall","prentice@email.com","123-456-789");
        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        try{
            publisherService.deletePublisher(publisherId,traceId);
        }catch(LibraryResourceNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Publisher Id : " + publisherId + "delete successfully.", HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<?> searchPublisher(@RequestParam String name,
                                             @RequestHeader(value="Trace-Id",defaultValue="") String traceId){

        if(!LibraryApiUtils.doesStringValueExist(traceId)){
            traceId = UUID.randomUUID().toString();
        }

        if(!LibraryApiUtils.doesStringValueExist(name)){
            return new ResponseEntity<>("Please enter name to search Publisher",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(publisherService.searchPublisher(name,traceId), HttpStatus.OK);
    }


}
