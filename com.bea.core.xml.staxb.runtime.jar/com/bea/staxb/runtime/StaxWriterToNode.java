package com.bea.staxb.runtime;

import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Node;

public interface StaxWriterToNode {
   Node getCurrentNode(XMLStreamWriter var1);
}
