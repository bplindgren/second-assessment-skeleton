package com.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Tag;
import com.service.TagService;

@RestController
@RequestMapping("tags")
public class TagsController {
	
	private TagService tagService;

	public TagsController(TagService tagService) {
		this.tagService = tagService;
	}

//	public TagService getTagService() {
//		return tagService;
//	}
//
//	public void setTagService(TagService tagService) {
//		this.tagService = tagService;
//	}

	@GetMapping
	public List<Tag> findAll() {
		return tagService.findAll();
	}
	
	@GetMapping("/{label}")
	public Tag findByLabel(@PathVariable String label) {
		return tagService.findByLabel(label);
	}
	
}
