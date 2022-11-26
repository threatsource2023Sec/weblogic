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

public interface MessageadapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageadapterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("messageadaptertype357btype");

   MessagelistenerType[] getMessagelistenerArray();

   MessagelistenerType getMessagelistenerArray(int var1);

   int sizeOfMessagelistenerArray();

   void setMessagelistenerArray(MessagelistenerType[] var1);

   void setMessagelistenerArray(int var1, MessagelistenerType var2);

   MessagelistenerType insertNewMessagelistener(int var1);

   MessagelistenerType addNewMessagelistener();

   void removeMessagelistener(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessageadapterType newInstance() {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().newInstance(MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType newInstance(XmlOptions options) {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().newInstance(MessageadapterType.type, options);
      }

      public static MessageadapterType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(File file) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(file, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(file, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(URL u) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(u, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(u, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(InputStream is) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(is, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(is, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(Reader r) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(r, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(r, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(XMLStreamReader sr) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(sr, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(sr, MessageadapterType.type, options);
      }

      public static MessageadapterType parse(Node node) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(node, MessageadapterType.type, (XmlOptions)null);
      }

      public static MessageadapterType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(node, MessageadapterType.type, options);
      }

      /** @deprecated */
      public static MessageadapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(xis, MessageadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageadapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageadapterType)XmlBeans.getContextTypeLoader().parse(xis, MessageadapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageadapterType.type, options);
      }

      private Factory() {
      }
   }
}
