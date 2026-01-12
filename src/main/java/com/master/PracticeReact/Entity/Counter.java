package com.master.PracticeReact.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Counter {
    @Id
    private Long id;
    private int counter;

    public Long getId() { return id; }
    public int getCounter() { return counter; }

    public void setId(Long id) { this.id = id; }
    public void setCounter(int counter) { this.counter = counter; }
}

