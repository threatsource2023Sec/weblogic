package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface GeneratorTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GeneratorTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("generatortypetype2ee3type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static GeneratorTypeType newInstance() {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().newInstance(GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType newInstance(XmlOptions options) {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().newInstance(GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(String xmlAsString) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(File file) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(file, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(file, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(URL u) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(u, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(u, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(InputStream is) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(is, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(is, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(Reader r) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(r, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(r, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(XMLStreamReader sr) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(sr, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(sr, GeneratorTypeType.type, options);
      }

      public static GeneratorTypeType parse(Node node) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(node, GeneratorTypeType.type, (XmlOptions)null);
      }

      public static GeneratorTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(node, GeneratorTypeType.type, options);
      }

      /** @deprecated */
      public static GeneratorTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(xis, GeneratorTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GeneratorTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GeneratorTypeType)XmlBeans.getContextTypeLoader().parse(xis, GeneratorTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GeneratorTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GeneratorTypeType.type, options);
      }

      private Factory() {
      }
   }
}
