package com.bitshift.saams.model;

import java.io.Serializable;
import java.util.List;

public class ReportCard implements Serializable {
    String subjectName;
    List<ExamScore> results;

    public ReportCard(String subjectName, List<ExamScore> results) {
        this.subjectName = subjectName;
        this.results = results;
    }

    public List<ExamScore> getResults() {
        return results;
    }

    public void setResults(List<ExamScore> results) {
        this.results = results;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }


}
