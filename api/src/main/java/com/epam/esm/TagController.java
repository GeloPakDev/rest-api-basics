package com.epam.esm;

import com.epam.esm.response.ResponseHandler;
import com.epam.esm.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.EndpointName.*;
import static com.epam.esm.util.QueryParam.*;

@RestController
@RequestMapping(path = BASE_URL + TAGS, produces = JSON)
@CrossOrigin(origins = LOCALHOST)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(params = TAG_ID)
    public ResponseEntity<Object> findTagById(@RequestParam int id) {
        Optional<Tag> optTag = tagService.findById(id);
        Tag tag;
        if (optTag.isPresent()) {
            tag = optTag.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tag);
        } else {
            return ResponseHandler.generateResponse("Tag with id (" + id + ") was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }

    @RequestMapping(params = TAG_NAME)
    public ResponseEntity<Object> findByTagName(@RequestParam String tagName) {
        Optional<Tag> optTag = tagService.findByName(tagName);
        Tag tag;
        if (optTag.isPresent()) {
            tag = optTag.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, tag);
        } else {
            return ResponseHandler.generateResponse("Tag with name (" + tagName + ") was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }

    @GetMapping
    public List<Tag> findAllTags() {
        return tagService.findAll();
    }

    @PostMapping(consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createTag(@RequestBody Tag tag) {
        tagService.create(tag);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED , HttpStatus.OK);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTag(@PathVariable(TAG_ID) int tagID) {
        boolean check = tagService.delete(tagID);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED_TAG + tagID, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.DELETE_ERROR + tagID, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}