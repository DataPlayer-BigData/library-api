package com.codecompass.libraryapi.Publisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codecompass.libraryapi.Exception.LibraryResourceAlreadyExistException;

import javax.validation.Valid;
import javax.validation.constraints.Email;


@RestController
@RequestMapping(path = "/v1/publishers")
public class PublisherController {

    private PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping(path = "/{publisherId}")
    public Publisher getPublisher(@PathVariable Integer publisherId){
        return new Publisher(publisherId,"Prentice Hall","prentice@email.com","123-456-789");
    }

    @PostMapping()
    public ResponseEntity<?> addPublisher(@RequestBody @Valid Publisher publisher)  {

        try {
            Publisher publihser = publisherService.addPublisher(publisher);
        } catch (LibraryResourceAlreadyExistException e) {
            //e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(publisher,HttpStatus.CREATED);
    }
}
