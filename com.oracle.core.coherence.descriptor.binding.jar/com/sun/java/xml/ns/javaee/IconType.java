package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlLanguage;
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

public interface IconType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IconType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("icontype74e6type");

   PathType getSmallIcon();

   boolean isSetSmallIcon();

   void setSmallIcon(PathType var1);

   PathType addNewSmallIcon();

   void unsetSmallIcon();

   PathType getLargeIcon();

   boolean isSetLargeIcon();

   void setLargeIcon(PathType var1);

   PathType addNewLargeIcon();

   void unsetLargeIcon();

   java.lang.String getLang();

   XmlLanguage xgetLang();

   boolean isSetLang();

   void setLang(java.lang.String var1);

   void xsetLang(XmlLanguage var1);

   void unsetLang();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IconType newInstance() {
         return (IconType)XmlBeans.getContextTypeLoader().newInstance(IconType.type, (XmlOptions)null);
      }

      public static IconType newInstance(XmlOptions options) {
         return (IconType)XmlBeans.getContextTypeLoader().newInstance(IconType.type, options);
      }

      public static IconType parse(java.lang.String xmlAsString) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IconType.type, options);
      }

      public static IconType parse(File file) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(file, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(file, IconType.type, options);
      }

      public static IconType parse(URL u) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(u, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(u, IconType.type, options);
      }

      public static IconType parse(InputStream is) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(is, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(is, IconType.type, options);
      }

      public static IconType parse(Reader r) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(r, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(r, IconType.type, options);
      }

      public static IconType parse(XMLStreamReader sr) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(sr, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(sr, IconType.type, options);
      }

      public static IconType parse(Node node) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(node, IconType.type, (XmlOptions)null);
      }

      public static IconType parse(Node node, XmlOptions options) throws XmlException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(node, IconType.type, options);
      }

      /** @deprecated */
      public static IconType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(xis, IconType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IconType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IconType)XmlBeans.getContextTypeLoader().parse(xis, IconType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IconType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IconType.type, options);
      }

      private Factory() {
      }
   }
}
