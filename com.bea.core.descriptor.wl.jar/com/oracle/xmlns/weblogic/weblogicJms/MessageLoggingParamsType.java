package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface MessageLoggingParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageLoggingParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("messageloggingparamstype767ctype");

   boolean getMessageLoggingEnabled();

   XmlBoolean xgetMessageLoggingEnabled();

   boolean isSetMessageLoggingEnabled();

   void setMessageLoggingEnabled(boolean var1);

   void xsetMessageLoggingEnabled(XmlBoolean var1);

   void unsetMessageLoggingEnabled();

   String getMessageLoggingFormat();

   XmlString xgetMessageLoggingFormat();

   boolean isNilMessageLoggingFormat();

   boolean isSetMessageLoggingFormat();

   void setMessageLoggingFormat(String var1);

   void xsetMessageLoggingFormat(XmlString var1);

   void setNilMessageLoggingFormat();

   void unsetMessageLoggingFormat();

   public static final class Factory {
      public static MessageLoggingParamsType newInstance() {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().newInstance(MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType newInstance(XmlOptions options) {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().newInstance(MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(String xmlAsString) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(File file) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(file, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(file, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(URL u) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(u, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(u, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(InputStream is) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(is, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(is, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(Reader r) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(r, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(r, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(XMLStreamReader sr) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(sr, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(sr, MessageLoggingParamsType.type, options);
      }

      public static MessageLoggingParamsType parse(Node node) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(node, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      public static MessageLoggingParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(node, MessageLoggingParamsType.type, options);
      }

      /** @deprecated */
      public static MessageLoggingParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xis, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageLoggingParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xis, MessageLoggingParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageLoggingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageLoggingParamsType.type, options);
      }

      private Factory() {
      }
   }
}
