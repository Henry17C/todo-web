package com.todo.web.dto;

public class TodoUserDto {
    
    public Long id;
    public Long userId;
    public String userName;
    public String title;
    public Boolean completed;
    
    public TodoUserDto() {}
    
    public TodoUserDto(Long id, Long userId, String userName, String title, Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.title = title;
        this.completed = completed;
    }
}
