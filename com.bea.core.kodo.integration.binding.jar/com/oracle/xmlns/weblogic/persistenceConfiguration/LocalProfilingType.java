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

public interface LocalProfilingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalProfilingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("localprofilingtype3ba8type");

   public static final class Factory {
      public static LocalProfilingType newInstance() {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().newInstance(LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType newInstance(XmlOptions options) {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().newInstance(LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(String xmlAsString) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(File file) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(file, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(file, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(URL u) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(u, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(u, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(InputStream is) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(is, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(is, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(Reader r) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(r, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(r, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(XMLStreamReader sr) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(sr, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(sr, LocalProfilingType.type, options);
      }

      public static LocalProfilingType parse(Node node) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(node, LocalProfilingType.type, (XmlOptions)null);
      }

      public static LocalProfilingType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(node, LocalProfilingType.type, options);
      }

      /** @deprecated */
      public static LocalProfilingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(xis, LocalProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalProfilingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalProfilingType)XmlBeans.getContextTypeLoader().parse(xis, LocalProfilingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalProfilingType.type, options);
      }

      private Factory() {
      }
   }
}
