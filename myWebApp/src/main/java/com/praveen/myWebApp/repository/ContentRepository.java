package com.praveen.myWebApp.repository;

import com.praveen.myWebApp.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content,Integer> {

}
