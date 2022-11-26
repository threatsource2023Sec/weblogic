package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface InstanceLockOrderType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InstanceLockOrderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("instancelockordertypeaba1type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InstanceLockOrderType newInstance() {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().newInstance(InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType newInstance(XmlOptions options) {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().newInstance(InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(String xmlAsString) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(File file) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(file, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(file, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(URL u) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(u, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(u, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(InputStream is) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(is, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(is, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(Reader r) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(r, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(r, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(XMLStreamReader sr) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(sr, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(sr, InstanceLockOrderType.type, options);
      }

      public static InstanceLockOrderType parse(Node node) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(node, InstanceLockOrderType.type, (XmlOptions)null);
      }

      public static InstanceLockOrderType parse(Node node, XmlOptions options) throws XmlException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(node, InstanceLockOrderType.type, options);
      }

      /** @deprecated */
      public static InstanceLockOrderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(xis, InstanceLockOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InstanceLockOrderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InstanceLockOrderType)XmlBeans.getContextTypeLoader().parse(xis, InstanceLockOrderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InstanceLockOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InstanceLockOrderType.type, options);
      }

      private Factory() {
      }
   }
}
