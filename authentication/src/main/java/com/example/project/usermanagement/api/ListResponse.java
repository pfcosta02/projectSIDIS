package com.example.project.usermanagement.api;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse<T> {
    List<T> items;
}
