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

public interface GeneratorNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GeneratorNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("generatornametypef334type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static GeneratorNameType newInstance() {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().newInstance(GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType newInstance(XmlOptions options) {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().newInstance(GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(String xmlAsString) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(File file) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(file, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(file, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(URL u) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(u, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(u, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(InputStream is) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(is, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(is, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(Reader r) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(r, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(r, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(XMLStreamReader sr) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(sr, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(sr, GeneratorNameType.type, options);
      }

      public static GeneratorNameType parse(Node node) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(node, GeneratorNameType.type, (XmlOptions)null);
      }

      public static GeneratorNameType parse(Node node, XmlOptions options) throws XmlException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(node, GeneratorNameType.type, options);
      }

      /** @deprecated */
      public static GeneratorNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(xis, GeneratorNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GeneratorNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GeneratorNameType)XmlBeans.getContextTypeLoader().parse(xis, GeneratorNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GeneratorNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GeneratorNameType.type, options);
      }

      private Factory() {
      }
   }
}
