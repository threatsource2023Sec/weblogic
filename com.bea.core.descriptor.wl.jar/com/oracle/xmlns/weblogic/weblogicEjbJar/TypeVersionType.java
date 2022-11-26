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

public interface TypeVersionType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TypeVersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("typeversiontypef83btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TypeVersionType newInstance() {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().newInstance(TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType newInstance(XmlOptions options) {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().newInstance(TypeVersionType.type, options);
      }

      public static TypeVersionType parse(String xmlAsString) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(File file) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(file, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(file, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(URL u) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(u, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(u, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(InputStream is) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(is, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(is, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(Reader r) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(r, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(r, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(XMLStreamReader sr) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(sr, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(sr, TypeVersionType.type, options);
      }

      public static TypeVersionType parse(Node node) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(node, TypeVersionType.type, (XmlOptions)null);
      }

      public static TypeVersionType parse(Node node, XmlOptions options) throws XmlException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(node, TypeVersionType.type, options);
      }

      /** @deprecated */
      public static TypeVersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(xis, TypeVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TypeVersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TypeVersionType)XmlBeans.getContextTypeLoader().parse(xis, TypeVersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeVersionType.type, options);
      }

      private Factory() {
      }
   }
}
