package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface UniformDistributedQueueType extends QueueType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UniformDistributedQueueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("uniformdistributedqueuetype53datype");

   public static final class Factory {
      public static UniformDistributedQueueType newInstance() {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType newInstance(XmlOptions options) {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(String xmlAsString) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(File file) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(URL u) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(InputStream is) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(Reader r) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(XMLStreamReader sr) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedQueueType.type, options);
      }

      public static UniformDistributedQueueType parse(Node node) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      public static UniformDistributedQueueType parse(Node node, XmlOptions options) throws XmlException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedQueueType.type, options);
      }

      /** @deprecated */
      public static UniformDistributedQueueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UniformDistributedQueueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UniformDistributedQueueType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedQueueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedQueueType.type, options);
      }

      private Factory() {
      }
   }
}
