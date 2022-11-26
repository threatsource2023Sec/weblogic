package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.StreamReaderFromNode;
import com.bea.xml.XmlBeans;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Element;

public final class XBeanStreamReaderFromNode implements StreamReaderFromNode {
   private static final StreamReaderFromNode INSTANCE = new XBeanStreamReaderFromNode();

   public static StreamReaderFromNode getInstance() {
      return INSTANCE;
   }

   private XBeanStreamReaderFromNode() {
   }

   public XMLStreamReader getStreamReader(Element elem) {
      return XmlBeans.nodeToXmlStreamReader(elem);
   }
}
