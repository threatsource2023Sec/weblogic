package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface AggregateListenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AggregateListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("aggregatelistenertypec938type");

   public static final class Factory {
      public static AggregateListenerType newInstance() {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().newInstance(AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType newInstance(XmlOptions options) {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().newInstance(AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(String xmlAsString) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(File file) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(file, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(file, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(URL u) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(u, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(u, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(InputStream is) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(is, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(is, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(Reader r) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(r, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(r, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(XMLStreamReader sr) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(sr, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(sr, AggregateListenerType.type, options);
      }

      public static AggregateListenerType parse(Node node) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(node, AggregateListenerType.type, (XmlOptions)null);
      }

      public static AggregateListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(node, AggregateListenerType.type, options);
      }

      /** @deprecated */
      public static AggregateListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(xis, AggregateListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AggregateListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AggregateListenerType)XmlBeans.getContextTypeLoader().parse(xis, AggregateListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AggregateListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AggregateListenerType.type, options);
      }

      private Factory() {
      }
   }
}
