package com.bea.staxb.runtime;

import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;

public interface NodeFromStreamReader {
   Node getCurrentNode(XMLStreamReader var1) throws XmlException;
}
