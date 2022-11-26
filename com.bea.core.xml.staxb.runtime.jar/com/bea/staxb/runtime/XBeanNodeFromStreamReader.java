package com.bea.staxb.runtime;

import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;

public final class XBeanNodeFromStreamReader implements NodeFromStreamReader {
   private static final XBeanNodeFromStreamReader INSTANCE = new XBeanNodeFromStreamReader();

   private XBeanNodeFromStreamReader() {
   }

   public static NodeFromStreamReader getInstance() {
      return INSTANCE;
   }

   public Node getCurrentNode(XMLStreamReader reader) throws XmlException {
      return XmlBeans.streamToNode(reader);
   }
}
