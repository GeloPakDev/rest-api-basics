package com.epam.esm;

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

    @GetMapping(ID)
    public ResponseEntity<Tag> findTagById(@PathVariable(TAG_ID) int id) {
        Optional<Tag> optTag = tagService.find(id);
        return optTag.map(
                        tag -> new ResponseEntity<>(tag, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Tag> findAllTags() {
        return tagService.findAll();
    }

    @PostMapping(consumes = JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean createTag(@RequestBody Tag tag) {
        return tagService.create(tag);
    }

    @DeleteMapping(ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(TAG_ID) int tagID) {
        tagService.delete(tagID);
    }
}