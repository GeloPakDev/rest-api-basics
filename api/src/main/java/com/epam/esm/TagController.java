package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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
    public Tag findTagById(@RequestParam int id) {
        Optional<Tag> optTag = tagService.findById(id);
        Tag tag = new Tag();
        if (optTag.isPresent()) {
            tag = optTag.get();
        } else {
            throw new NoSuchElementException();
        }
        return tag;
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