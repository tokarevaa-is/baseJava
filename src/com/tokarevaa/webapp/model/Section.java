package com.tokarevaa.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {

    public abstract void parseJson(String json);
    public abstract String toGson();
}
