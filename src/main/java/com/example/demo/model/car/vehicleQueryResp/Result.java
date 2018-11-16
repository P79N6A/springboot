package com.example.demo.model.car.vehicleQueryResp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class Result {
    private boolean empty;
    private int total;
    private List<Data> data;

}
