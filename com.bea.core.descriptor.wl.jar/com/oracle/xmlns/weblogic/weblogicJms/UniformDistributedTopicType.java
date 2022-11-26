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

public interface UniformDistributedTopicType extends TopicType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UniformDistributedTopicType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("uniformdistributedtopictype36fctype");

   public static final class Factory {
      public static UniformDistributedTopicType newInstance() {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType newInstance(XmlOptions options) {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(String xmlAsString) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(File file) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(URL u) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(InputStream is) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(Reader r) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(XMLStreamReader sr) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedTopicType.type, options);
      }

      public static UniformDistributedTopicType parse(Node node) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      public static UniformDistributedTopicType parse(Node node, XmlOptions options) throws XmlException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedTopicType.type, options);
      }

      /** @deprecated */
      public static UniformDistributedTopicType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UniformDistributedTopicType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UniformDistributedTopicType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedTopicType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedTopicType.type, options);
      }

      private Factory() {
      }
   }
}
