package com.example.cilo.codevated;

/**
 * Created by cilo on 5/2/17.
 */

public class Concept {
    int conceptId;
    String conceptTitle,conceptContent;

    public Concept(int conceptId, String conceptTitle, String conceptContent) {
        this.conceptId = conceptId;
        this.conceptTitle = conceptTitle;
        this.conceptContent = conceptContent;
    }

    public int getConceptId() {
        return conceptId;
    }

    public void setConceptId(int conceptId) {
        this.conceptId = conceptId;
    }

    public String getConceptTitle() {
        return conceptTitle;
    }

    public void setConceptTitle(String conceptTitle) {
        this.conceptTitle = conceptTitle;
    }

    public String getConceptContent() {
        return conceptContent;
    }

    public void setConceptContent(String conceptContent) {
        this.conceptContent = conceptContent;
    }
}
