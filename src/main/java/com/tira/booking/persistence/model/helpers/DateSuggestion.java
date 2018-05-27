package com.tira.booking.persistence.model.helpers;

import java.util.Date;

public class DateSuggestion {

    private Date startDate;
    private Date endDate;

    public DateSuggestion() {
    }

    public DateSuggestion(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
