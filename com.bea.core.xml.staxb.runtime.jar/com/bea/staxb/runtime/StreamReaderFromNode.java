package com.bea.staxb.runtime;

import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Element;

public interface StreamReaderFromNode {
   XMLStreamReader getStreamReader(Element var1) throws XmlException;
}
