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

public interface FinderSqlType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FinderSqlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("findersqltypee006type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FinderSqlType newInstance() {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().newInstance(FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType newInstance(XmlOptions options) {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().newInstance(FinderSqlType.type, options);
      }

      public static FinderSqlType parse(String xmlAsString) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(File file) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(file, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(file, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(URL u) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(u, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(u, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(InputStream is) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(is, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(is, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(Reader r) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(r, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(r, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(XMLStreamReader sr) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(sr, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(sr, FinderSqlType.type, options);
      }

      public static FinderSqlType parse(Node node) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(node, FinderSqlType.type, (XmlOptions)null);
      }

      public static FinderSqlType parse(Node node, XmlOptions options) throws XmlException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(node, FinderSqlType.type, options);
      }

      /** @deprecated */
      public static FinderSqlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(xis, FinderSqlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FinderSqlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FinderSqlType)XmlBeans.getContextTypeLoader().parse(xis, FinderSqlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderSqlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderSqlType.type, options);
      }

      private Factory() {
      }
   }
}
