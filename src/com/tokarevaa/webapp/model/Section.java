package com.tokarevaa.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {

    public abstract void writeData(DataOutputStream dos) throws IOException;
    public abstract void readData(DataInputStream dis) throws IOException;
}