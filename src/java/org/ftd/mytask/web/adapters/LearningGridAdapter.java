package org.ftd.mytask.web.adapters;

import java.io.Serializable;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 1.0.0 - 2018-10-25
 * 
 */
public class LearningGridAdapter implements Serializable {
    private static final long serialVersionUID = 295704246530633088L;

    private Long id;
    private String group;
    private String termObject;
    private String term1;
    private String term2;
    private String initLike;
    private String finishLike;
    private String type;
    private String subType;    

    public LearningGridAdapter(Long id, String group, String termObject, String term1, String term2, String initLike, String finishLike, String type, String subType) {
        this.id = id;
        this.group = group;
        this.termObject = termObject;
        this.term1 = term1;
        this.term2 = term2;
        this.initLike = initLike;
        this.finishLike = finishLike;
        this.type = type;
        this.subType = subType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTermObject() {
        return termObject;
    }

    public void setTermObject(String termObject) {
        this.termObject = termObject;
    }

    public String getTerm1() {
        return term1;
    }

    public void setTerm1(String term1) {
        this.term1 = term1;
    }

    public String getTerm2() {
        return term2;
    }

    public void setTerm2(String term2) {
        this.term2 = term2;
    }

    public String getInitLike() {
        return initLike;
    }

    public void setInitLike(String initLike) {
        this.initLike = initLike;
    }

    public String getFinishLike() {
        return finishLike;
    }

    public void setFinishLike(String finishLike) {
        this.finishLike = finishLike;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
    

    
}
