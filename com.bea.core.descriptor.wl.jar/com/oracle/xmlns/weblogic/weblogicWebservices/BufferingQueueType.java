package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface BufferingQueueType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BufferingQueueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("bufferingqueuetype31f1type");

   String getName();

   boolean isNilName();

   boolean isSetName();

   void setName(String var1);

   String addNewName();

   void setNilName();

   void unsetName();

   boolean getEnabled();

   XmlBoolean xgetEnabled();

   boolean isSetEnabled();

   void setEnabled(boolean var1);

   void xsetEnabled(XmlBoolean var1);

   void unsetEnabled();

   java.lang.String getConnectionFactoryJndiName();

   XmlString xgetConnectionFactoryJndiName();

   boolean isSetConnectionFactoryJndiName();

   void setConnectionFactoryJndiName(java.lang.String var1);

   void xsetConnectionFactoryJndiName(XmlString var1);

   void unsetConnectionFactoryJndiName();

   boolean getTransactionEnabled();

   XmlBoolean xgetTransactionEnabled();

   boolean isSetTransactionEnabled();

   void setTransactionEnabled(boolean var1);

   void xsetTransactionEnabled(XmlBoolean var1);

   void unsetTransactionEnabled();

   public static final class Factory {
      public static BufferingQueueType newInstance() {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().newInstance(BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType newInstance(XmlOptions options) {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().newInstance(BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(java.lang.String xmlAsString) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(File file) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(file, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(file, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(URL u) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(u, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(u, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(InputStream is) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(is, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(is, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(Reader r) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(r, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(r, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(XMLStreamReader sr) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(sr, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(sr, BufferingQueueType.type, options);
      }

      public static BufferingQueueType parse(Node node) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(node, BufferingQueueType.type, (XmlOptions)null);
      }

      public static BufferingQueueType parse(Node node, XmlOptions options) throws XmlException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(node, BufferingQueueType.type, options);
      }

      /** @deprecated */
      public static BufferingQueueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(xis, BufferingQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BufferingQueueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BufferingQueueType)XmlBeans.getContextTypeLoader().parse(xis, BufferingQueueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BufferingQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BufferingQueueType.type, options);
      }

      private Factory() {
      }
   }
}
