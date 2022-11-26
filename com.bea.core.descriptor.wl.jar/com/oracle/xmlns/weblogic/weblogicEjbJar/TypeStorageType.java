package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface TypeStorageType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TypeStorageType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("typestoragetypea59etype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TypeStorageType newInstance() {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().newInstance(TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType newInstance(XmlOptions options) {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().newInstance(TypeStorageType.type, options);
      }

      public static TypeStorageType parse(String xmlAsString) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(File file) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(file, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(file, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(URL u) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(u, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(u, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(InputStream is) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(is, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(is, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(Reader r) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(r, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(r, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(XMLStreamReader sr) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(sr, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(sr, TypeStorageType.type, options);
      }

      public static TypeStorageType parse(Node node) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(node, TypeStorageType.type, (XmlOptions)null);
      }

      public static TypeStorageType parse(Node node, XmlOptions options) throws XmlException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(node, TypeStorageType.type, options);
      }

      /** @deprecated */
      public static TypeStorageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(xis, TypeStorageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TypeStorageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TypeStorageType)XmlBeans.getContextTypeLoader().parse(xis, TypeStorageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeStorageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeStorageType.type, options);
      }

      private Factory() {
      }
   }
}
