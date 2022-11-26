package com.bea.staxb.runtime;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface StaxSerializer {
   void writeTo(XMLStreamWriter var1) throws XMLStreamException;
}
