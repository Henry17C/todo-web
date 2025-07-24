package com.todo.web.client;

import com.todo.web.dto.TodoUserDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "todo-rest-api")
@Path("/api/todos")
public interface TodoRestClient {
    
    @GET
    List<TodoUserDto> getAllTodos();
    
    @GET
    @Path("/user/{userId}")
    List<TodoUserDto> getTodosByUserId(@PathParam("userId") Long userId);
}
