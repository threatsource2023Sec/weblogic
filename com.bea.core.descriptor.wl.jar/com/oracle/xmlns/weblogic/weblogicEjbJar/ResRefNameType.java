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

public interface ResRefNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResRefNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resrefnametype4d48type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResRefNameType newInstance() {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().newInstance(ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType newInstance(XmlOptions options) {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().newInstance(ResRefNameType.type, options);
      }

      public static ResRefNameType parse(String xmlAsString) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(File file) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(file, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(file, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(URL u) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(u, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(u, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(InputStream is) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(is, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(is, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(Reader r) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(r, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(r, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(XMLStreamReader sr) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(sr, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(sr, ResRefNameType.type, options);
      }

      public static ResRefNameType parse(Node node) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(node, ResRefNameType.type, (XmlOptions)null);
      }

      public static ResRefNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(node, ResRefNameType.type, options);
      }

      /** @deprecated */
      public static ResRefNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(xis, ResRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResRefNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResRefNameType)XmlBeans.getContextTypeLoader().parse(xis, ResRefNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResRefNameType.type, options);
      }

      private Factory() {
      }
   }
}
