package com.example.model;

public class User {


    private Long id;
    private String name;
    private String surname;
    private Long age;


    public Long getId() {
        return id;
    }
     public void setId(Long id) {
        this.id = id;
    }   

    public String getName() {
        return name;
    }
    
     public void setName(String name) {
        this.name = name;
    }
   

     public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

     public Long getAge() {
        return age;
    }
     public void setAge(Long age) {
        this.age = age;
    } 
}
