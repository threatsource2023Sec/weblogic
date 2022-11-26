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

public interface StatefulTimeoutType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatefulTimeoutType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("statefultimeouttype558dtype");

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
      public static StatefulTimeoutType newInstance() {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().newInstance(StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType newInstance(XmlOptions options) {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().newInstance(StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(java.lang.String xmlAsString) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(File file) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(file, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(file, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(URL u) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(u, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(u, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(InputStream is) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(is, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(is, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(Reader r) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(r, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(r, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(XMLStreamReader sr) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, StatefulTimeoutType.type, options);
      }

      public static StatefulTimeoutType parse(Node node) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(node, StatefulTimeoutType.type, (XmlOptions)null);
      }

      public static StatefulTimeoutType parse(Node node, XmlOptions options) throws XmlException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(node, StatefulTimeoutType.type, options);
      }

      /** @deprecated */
      public static StatefulTimeoutType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, StatefulTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatefulTimeoutType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatefulTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, StatefulTimeoutType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulTimeoutType.type, options);
      }

      private Factory() {
      }
   }
}
