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

public interface LocaleEncodingMappingListType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocaleEncodingMappingListType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("localeencodingmappinglisttypedb97type");

   LocaleEncodingMappingType[] getLocaleEncodingMappingArray();

   LocaleEncodingMappingType getLocaleEncodingMappingArray(int var1);

   int sizeOfLocaleEncodingMappingArray();

   void setLocaleEncodingMappingArray(LocaleEncodingMappingType[] var1);

   void setLocaleEncodingMappingArray(int var1, LocaleEncodingMappingType var2);

   LocaleEncodingMappingType insertNewLocaleEncodingMapping(int var1);

   LocaleEncodingMappingType addNewLocaleEncodingMapping();

   void removeLocaleEncodingMapping(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LocaleEncodingMappingListType newInstance() {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().newInstance(LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType newInstance(XmlOptions options) {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().newInstance(LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(java.lang.String xmlAsString) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(File file) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(file, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(file, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(URL u) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(u, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(u, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(InputStream is) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(is, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(is, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(Reader r) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(r, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(r, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(XMLStreamReader sr) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(sr, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(sr, LocaleEncodingMappingListType.type, options);
      }

      public static LocaleEncodingMappingListType parse(Node node) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(node, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      public static LocaleEncodingMappingListType parse(Node node, XmlOptions options) throws XmlException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(node, LocaleEncodingMappingListType.type, options);
      }

      /** @deprecated */
      public static LocaleEncodingMappingListType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(xis, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocaleEncodingMappingListType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocaleEncodingMappingListType)XmlBeans.getContextTypeLoader().parse(xis, LocaleEncodingMappingListType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleEncodingMappingListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleEncodingMappingListType.type, options);
      }

      private Factory() {
      }
   }
}
