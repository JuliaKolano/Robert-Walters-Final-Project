package com.finalproject.code.classes;

import java.time.LocalDate;

public class ReadingGoal {

    // Variables
    private final int pageCount;
    private final boolean isReached;
    private final LocalDate dateSet;

    // Constructor
    public ReadingGoal(int pageCount, boolean isReached, LocalDate dateSet) {
        this.pageCount = pageCount;
        this.isReached = isReached;
        this.dateSet = dateSet;
    }


    // Getters

    public int getPageCount() {
        return pageCount;
    }

    public boolean isReached() {
        return isReached;
    }

    public LocalDate getDateSet() {
        return dateSet;
    }
}
