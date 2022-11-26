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

public interface TypeIdentifierType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TypeIdentifierType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("typeidentifiertypec7e0type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TypeIdentifierType newInstance() {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().newInstance(TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType newInstance(XmlOptions options) {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().newInstance(TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(String xmlAsString) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(File file) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(file, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(file, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(URL u) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(u, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(u, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(InputStream is) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(is, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(is, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(Reader r) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(r, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(r, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(XMLStreamReader sr) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(sr, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(sr, TypeIdentifierType.type, options);
      }

      public static TypeIdentifierType parse(Node node) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(node, TypeIdentifierType.type, (XmlOptions)null);
      }

      public static TypeIdentifierType parse(Node node, XmlOptions options) throws XmlException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(node, TypeIdentifierType.type, options);
      }

      /** @deprecated */
      public static TypeIdentifierType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(xis, TypeIdentifierType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TypeIdentifierType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TypeIdentifierType)XmlBeans.getContextTypeLoader().parse(xis, TypeIdentifierType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeIdentifierType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeIdentifierType.type, options);
      }

      private Factory() {
      }
   }
}
