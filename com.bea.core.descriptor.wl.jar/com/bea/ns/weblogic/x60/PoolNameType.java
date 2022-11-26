package com.bea.ns.weblogic.x60;

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

public interface PoolNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PoolNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("poolnametype3449type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PoolNameType newInstance() {
         return (PoolNameType)XmlBeans.getContextTypeLoader().newInstance(PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType newInstance(XmlOptions options) {
         return (PoolNameType)XmlBeans.getContextTypeLoader().newInstance(PoolNameType.type, options);
      }

      public static PoolNameType parse(String xmlAsString) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolNameType.type, options);
      }

      public static PoolNameType parse(File file) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(file, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(file, PoolNameType.type, options);
      }

      public static PoolNameType parse(URL u) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(u, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(u, PoolNameType.type, options);
      }

      public static PoolNameType parse(InputStream is) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(is, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(is, PoolNameType.type, options);
      }

      public static PoolNameType parse(Reader r) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(r, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(r, PoolNameType.type, options);
      }

      public static PoolNameType parse(XMLStreamReader sr) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(sr, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(sr, PoolNameType.type, options);
      }

      public static PoolNameType parse(Node node) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(node, PoolNameType.type, (XmlOptions)null);
      }

      public static PoolNameType parse(Node node, XmlOptions options) throws XmlException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(node, PoolNameType.type, options);
      }

      /** @deprecated */
      public static PoolNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(xis, PoolNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PoolNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PoolNameType)XmlBeans.getContextTypeLoader().parse(xis, PoolNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolNameType.type, options);
      }

      private Factory() {
      }
   }
}
