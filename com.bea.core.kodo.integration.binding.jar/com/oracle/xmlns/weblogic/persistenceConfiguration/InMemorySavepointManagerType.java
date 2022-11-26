package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface InMemorySavepointManagerType extends SavepointManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InMemorySavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("inmemorysavepointmanagertype0d1dtype");

   boolean getPreFlush();

   XmlBoolean xgetPreFlush();

   boolean isSetPreFlush();

   void setPreFlush(boolean var1);

   void xsetPreFlush(XmlBoolean var1);

   void unsetPreFlush();

   public static final class Factory {
      public static InMemorySavepointManagerType newInstance() {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType newInstance(XmlOptions options) {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(String xmlAsString) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(File file) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(URL u) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, InMemorySavepointManagerType.type, options);
      }

      public static InMemorySavepointManagerType parse(Node node) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      public static InMemorySavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, InMemorySavepointManagerType.type, options);
      }

      /** @deprecated */
      public static InMemorySavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InMemorySavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InMemorySavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, InMemorySavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InMemorySavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InMemorySavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}
