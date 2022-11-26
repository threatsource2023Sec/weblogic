package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface Mapping extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Mapping.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("mapping81f9type");

   String getXmlcomponent();

   XmlSignature xgetXmlcomponent();

   void setXmlcomponent(String var1);

   void xsetXmlcomponent(XmlSignature var1);

   String getJavatype();

   JavaClassName xgetJavatype();

   void setJavatype(String var1);

   void xsetJavatype(JavaClassName var1);

   public static final class Factory {
      public static Mapping newInstance() {
         return (Mapping)XmlBeans.getContextTypeLoader().newInstance(Mapping.type, (XmlOptions)null);
      }

      public static Mapping newInstance(XmlOptions options) {
         return (Mapping)XmlBeans.getContextTypeLoader().newInstance(Mapping.type, options);
      }

      public static Mapping parse(String xmlAsString) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(xmlAsString, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(xmlAsString, Mapping.type, options);
      }

      public static Mapping parse(File file) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(file, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(file, Mapping.type, options);
      }

      public static Mapping parse(URL u) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(u, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(u, Mapping.type, options);
      }

      public static Mapping parse(InputStream is) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(is, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(is, Mapping.type, options);
      }

      public static Mapping parse(Reader r) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(r, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(r, Mapping.type, options);
      }

      public static Mapping parse(XMLStreamReader sr) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(sr, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(sr, Mapping.type, options);
      }

      public static Mapping parse(Node node) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(node, Mapping.type, (XmlOptions)null);
      }

      public static Mapping parse(Node node, XmlOptions options) throws XmlException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(node, Mapping.type, options);
      }

      /** @deprecated */
      public static Mapping parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(xis, Mapping.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Mapping parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Mapping)XmlBeans.getContextTypeLoader().parse(xis, Mapping.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Mapping.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Mapping.type, options);
      }

      private Factory() {
      }
   }
}
