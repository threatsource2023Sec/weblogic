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

public interface LogType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LogType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("logtypec628type");

   public static final class Factory {
      public static LogType newInstance() {
         return (LogType)XmlBeans.getContextTypeLoader().newInstance(LogType.type, (XmlOptions)null);
      }

      public static LogType newInstance(XmlOptions options) {
         return (LogType)XmlBeans.getContextTypeLoader().newInstance(LogType.type, options);
      }

      public static LogType parse(String xmlAsString) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LogType.type, options);
      }

      public static LogType parse(File file) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(file, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(file, LogType.type, options);
      }

      public static LogType parse(URL u) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(u, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(u, LogType.type, options);
      }

      public static LogType parse(InputStream is) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(is, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(is, LogType.type, options);
      }

      public static LogType parse(Reader r) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(r, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(r, LogType.type, options);
      }

      public static LogType parse(XMLStreamReader sr) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(sr, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(sr, LogType.type, options);
      }

      public static LogType parse(Node node) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(node, LogType.type, (XmlOptions)null);
      }

      public static LogType parse(Node node, XmlOptions options) throws XmlException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(node, LogType.type, options);
      }

      /** @deprecated */
      public static LogType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(xis, LogType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LogType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LogType)XmlBeans.getContextTypeLoader().parse(xis, LogType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LogType.type, options);
      }

      private Factory() {
      }
   }
}
