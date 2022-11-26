package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface InitialContextFactoryType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InitialContextFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("initialcontextfactorytype07eetype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InitialContextFactoryType newInstance() {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().newInstance(InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType newInstance(XmlOptions options) {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().newInstance(InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(String xmlAsString) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(File file) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(file, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(file, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(URL u) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(u, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(u, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(InputStream is) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(is, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(is, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(Reader r) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(r, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(r, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(sr, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(sr, InitialContextFactoryType.type, options);
      }

      public static InitialContextFactoryType parse(Node node) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(node, InitialContextFactoryType.type, (XmlOptions)null);
      }

      public static InitialContextFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(node, InitialContextFactoryType.type, options);
      }

      /** @deprecated */
      public static InitialContextFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(xis, InitialContextFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InitialContextFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InitialContextFactoryType)XmlBeans.getContextTypeLoader().parse(xis, InitialContextFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitialContextFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitialContextFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
