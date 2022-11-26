package com.sun.java.xml.ns.javaee;

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

public interface AccessTimeoutType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AccessTimeoutType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("accesstimeouttype43e5type");

   XsdIntegerType getTimeout();

   void setTimeout(XsdIntegerType var1);

   XsdIntegerType addNewTimeout();

   TimeUnitTypeType getUnit();

   void setUnit(TimeUnitTypeType var1);

   TimeUnitTypeType addNewUnit();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AccessTimeoutType newInstance() {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().newInstance(AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType newInstance(XmlOptions options) {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().newInstance(AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(java.lang.String xmlAsString) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(File file) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(file, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(file, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(URL u) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(u, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(u, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(InputStream is) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(is, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(is, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(Reader r) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(r, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(r, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(XMLStreamReader sr) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, AccessTimeoutType.type, options);
      }

      public static AccessTimeoutType parse(Node node) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(node, AccessTimeoutType.type, (XmlOptions)null);
      }

      public static AccessTimeoutType parse(Node node, XmlOptions options) throws XmlException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(node, AccessTimeoutType.type, options);
      }

      /** @deprecated */
      public static AccessTimeoutType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, AccessTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AccessTimeoutType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AccessTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, AccessTimeoutType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AccessTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AccessTimeoutType.type, options);
      }

      private Factory() {
      }
   }
}
