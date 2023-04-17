package com.praveen.myWebApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="t_content")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String code;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
