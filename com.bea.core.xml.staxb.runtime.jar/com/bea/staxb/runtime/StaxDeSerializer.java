package com.bea.staxb.runtime;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface StaxDeSerializer {
   void readFrom(XMLStreamReader var1) throws XMLStreamException;
}
