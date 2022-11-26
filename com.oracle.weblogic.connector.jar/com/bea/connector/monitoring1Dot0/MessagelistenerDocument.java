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

public interface MessagelistenerDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessagelistenerDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("messagelistener8425doctype");

   Messagelistener getMessagelistener();

   void setMessagelistener(Messagelistener var1);

   Messagelistener addNewMessagelistener();

   public static final class Factory {
      public static MessagelistenerDocument newInstance() {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument newInstance(XmlOptions options) {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(String xmlAsString) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(File file) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(URL u) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(InputStream is) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(Reader r) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(XMLStreamReader sr) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerDocument.type, options);
      }

      public static MessagelistenerDocument parse(Node node) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerDocument.type, (XmlOptions)null);
      }

      public static MessagelistenerDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerDocument.type, options);
      }

      /** @deprecated */
      public static MessagelistenerDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessagelistenerDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessagelistenerDocument)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Messagelistener extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Messagelistener.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("messagelistener591eelemtype");

      String getMessagelistenerType();

      XmlString xgetMessagelistenerType();

      void setMessagelistenerType(String var1);

      void xsetMessagelistenerType(XmlString var1);

      ActivationspecDocument.Activationspec getActivationspec();

      void setActivationspec(ActivationspecDocument.Activationspec var1);

      ActivationspecDocument.Activationspec addNewActivationspec();

      public static final class Factory {
         public static Messagelistener newInstance() {
            return (Messagelistener)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerDocument.Messagelistener.type, (XmlOptions)null);
         }

         public static Messagelistener newInstance(XmlOptions options) {
            return (Messagelistener)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerDocument.Messagelistener.type, options);
         }

         private Factory() {
         }
      }
   }
}
