package com.todo.web.resource;

import com.todo.web.client.TodoRestClient;
import com.todo.web.dto.TodoUserDto;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("/")
public class TodoWebResource {
    
    @Inject
    Template index;
    
    @Inject
    Template todos;
    
    @Inject
    @RestClient
    TodoRestClient todoRestClient;
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String home() {
        return index.instance().render();
    }
    
    @GET
    @Path("/todos")
    @Produces(MediaType.TEXT_HTML)
    public String allTodos() {
        try {
            List<TodoUserDto> todoList = todoRestClient.getAllTodos();
            return todos.data("todos", todoList).data("userId", null).data("error", null).render();
        } catch (Exception e) {
            return todos.data("todos", List.of())
                       .data("userId", null)
                       .data("error", "Error al cargar las tareas: " + e.getMessage())
                       .render();
        }
    }
    
    @GET
    @Path("/todos/user/{userId}")
    @Produces(MediaType.TEXT_HTML)
    public String todosByUser(@PathParam("userId") Long userId) {
        try {
            List<TodoUserDto> todoList = todoRestClient.getTodosByUserId(userId);
            return todos.data("todos", todoList).data("userId", userId).data("error", null).render();
        } catch (Exception e) {
            return todos.data("todos", List.of())
                       .data("userId", userId)
                       .data("error", "Error al cargar las tareas del usuario: " + e.getMessage())
                       .render();
        }
    }
}
