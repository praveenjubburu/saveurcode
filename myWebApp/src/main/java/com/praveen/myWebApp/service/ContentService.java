package com.praveen.myWebApp.service;

import com.praveen.myWebApp.entity.Content;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ContentService {

    public List<Content> findAll();

    public void save(Content content);

    public Optional<Content> findById(int i);

    public void delete(Content content);
}
