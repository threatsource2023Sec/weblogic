package com.bea.connector.monitoring1Dot0;

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

public interface LoggingDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoggingDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("logging6a01doctype");

   LoggingType getLogging();

   void setLogging(LoggingType var1);

   LoggingType addNewLogging();

   public static final class Factory {
      public static LoggingDocument newInstance() {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().newInstance(LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument newInstance(XmlOptions options) {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().newInstance(LoggingDocument.type, options);
      }

      public static LoggingDocument parse(String xmlAsString) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(File file) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(file, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(file, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(URL u) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(u, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(u, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(InputStream is) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(is, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(is, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(Reader r) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(r, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(r, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(XMLStreamReader sr) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(sr, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(sr, LoggingDocument.type, options);
      }

      public static LoggingDocument parse(Node node) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(node, LoggingDocument.type, (XmlOptions)null);
      }

      public static LoggingDocument parse(Node node, XmlOptions options) throws XmlException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(node, LoggingDocument.type, options);
      }

      /** @deprecated */
      public static LoggingDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(xis, LoggingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoggingDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoggingDocument)XmlBeans.getContextTypeLoader().parse(xis, LoggingDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoggingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoggingDocument.type, options);
      }

      private Factory() {
      }
   }
}
