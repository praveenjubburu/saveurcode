package com.praveen.myWebApp.service;

import com.praveen.myWebApp.entity.Content;
import com.praveen.myWebApp.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImplementation implements ContentService{

    @Autowired
    private ContentRepository contentRepository;
    @Override
    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public void save(Content content) {
        contentRepository.save(content);
    }

    @Override
    public Optional<Content> findById(int id) {
        return contentRepository.findById(id);
    }

    @Override
    public void delete(Content content) {

        contentRepository.delete(content);

    }
}
