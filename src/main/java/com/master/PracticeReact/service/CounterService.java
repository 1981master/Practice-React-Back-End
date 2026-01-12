package com.master.PracticeReact.service;

import com.master.PracticeReact.Entity.Counter;
import com.master.PracticeReact.Repository.CounterReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterService {

    @Autowired
    private CounterReposotory counterReposotory;

    public List<Counter> getAllCounters(){
        return counterReposotory.findAll();
    }
}
