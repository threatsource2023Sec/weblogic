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

public interface LocaleEncodingMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocaleEncodingMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("localeencodingmappingtype7a62type");

   java.lang.String getLocale();

   LocaleType xgetLocale();

   void setLocale(java.lang.String var1);

   void xsetLocale(LocaleType var1);

   java.lang.String getEncoding();

   EncodingType xgetEncoding();

   void setEncoding(java.lang.String var1);

   void xsetEncoding(EncodingType var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LocaleEncodingMappingType newInstance() {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().newInstance(LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType newInstance(XmlOptions options) {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().newInstance(LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(File file) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(file, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(file, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(URL u) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(u, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(u, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(InputStream is) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(is, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(is, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(Reader r) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(r, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(r, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(XMLStreamReader sr) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(sr, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(sr, LocaleEncodingMappingType.type, options);
      }

      public static LocaleEncodingMappingType parse(Node node) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(node, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(node, LocaleEncodingMappingType.type, options);
      }

      /** @deprecated */
      public static LocaleEncodingMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(xis, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocaleEncodingMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocaleEncodingMappingType)XmlBeans.getContextTypeLoader().parse(xis, LocaleEncodingMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleEncodingMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleEncodingMappingType.type, options);
      }

      private Factory() {
      }
   }
}
