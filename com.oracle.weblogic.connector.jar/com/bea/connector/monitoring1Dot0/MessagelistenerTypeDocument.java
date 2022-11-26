package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MessagelistenerTypeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessagelistenerTypeDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("messagelistenertype8b22doctype");

   String getMessagelistenerType();

   XmlString xgetMessagelistenerType();

   void setMessagelistenerType(String var1);

   void xsetMessagelistenerType(XmlString var1);

   public static final class Factory {
      public static MessagelistenerTypeDocument newInstance() {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument newInstance(XmlOptions options) {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(String xmlAsString) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(File file) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(URL u) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(InputStream is) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(Reader r) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(XMLStreamReader sr) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerTypeDocument.type, options);
      }

      public static MessagelistenerTypeDocument parse(Node node) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerTypeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerTypeDocument.type, options);
      }

      /** @deprecated */
      public static MessagelistenerTypeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessagelistenerTypeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessagelistenerTypeDocument)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerTypeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerTypeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerTypeDocument.type, options);
      }

      private Factory() {
      }
   }
}
