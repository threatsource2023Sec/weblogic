package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface MessagelistenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessagelistenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("messagelistenertype011ctype");

   FullyQualifiedClassType getMessagelistenerType();

   void setMessagelistenerType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewMessagelistenerType();

   ActivationspecType getActivationspec();

   void setActivationspec(ActivationspecType var1);

   ActivationspecType addNewActivationspec();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessagelistenerType newInstance() {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType newInstance(XmlOptions options) {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().newInstance(MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(File file) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(file, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(URL u) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(u, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(InputStream is) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(is, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(Reader r) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(r, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(XMLStreamReader sr) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(sr, MessagelistenerType.type, options);
      }

      public static MessagelistenerType parse(Node node) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerType.type, (XmlOptions)null);
      }

      public static MessagelistenerType parse(Node node, XmlOptions options) throws XmlException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(node, MessagelistenerType.type, options);
      }

      /** @deprecated */
      public static MessagelistenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessagelistenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessagelistenerType)XmlBeans.getContextTypeLoader().parse(xis, MessagelistenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessagelistenerType.type, options);
      }

      private Factory() {
      }
   }
}
