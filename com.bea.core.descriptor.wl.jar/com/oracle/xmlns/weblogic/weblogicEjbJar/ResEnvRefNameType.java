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

public interface ResEnvRefNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResEnvRefNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resenvrefnametype2f28type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResEnvRefNameType newInstance() {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().newInstance(ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType newInstance(XmlOptions options) {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().newInstance(ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(String xmlAsString) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(File file) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(file, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(file, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(URL u) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(u, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(u, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(InputStream is) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(is, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(is, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(Reader r) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(r, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(r, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(XMLStreamReader sr) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(sr, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(sr, ResEnvRefNameType.type, options);
      }

      public static ResEnvRefNameType parse(Node node) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(node, ResEnvRefNameType.type, (XmlOptions)null);
      }

      public static ResEnvRefNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(node, ResEnvRefNameType.type, options);
      }

      /** @deprecated */
      public static ResEnvRefNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(xis, ResEnvRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResEnvRefNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResEnvRefNameType)XmlBeans.getContextTypeLoader().parse(xis, ResEnvRefNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResEnvRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResEnvRefNameType.type, options);
      }

      private Factory() {
      }
   }
}
