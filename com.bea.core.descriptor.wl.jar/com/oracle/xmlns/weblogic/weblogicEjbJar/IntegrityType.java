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

public interface IntegrityType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IntegrityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("integritytype6c91type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IntegrityType newInstance() {
         return (IntegrityType)XmlBeans.getContextTypeLoader().newInstance(IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType newInstance(XmlOptions options) {
         return (IntegrityType)XmlBeans.getContextTypeLoader().newInstance(IntegrityType.type, options);
      }

      public static IntegrityType parse(String xmlAsString) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IntegrityType.type, options);
      }

      public static IntegrityType parse(File file) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(file, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(file, IntegrityType.type, options);
      }

      public static IntegrityType parse(URL u) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(u, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(u, IntegrityType.type, options);
      }

      public static IntegrityType parse(InputStream is) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(is, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(is, IntegrityType.type, options);
      }

      public static IntegrityType parse(Reader r) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(r, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(r, IntegrityType.type, options);
      }

      public static IntegrityType parse(XMLStreamReader sr) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(sr, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(sr, IntegrityType.type, options);
      }

      public static IntegrityType parse(Node node) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(node, IntegrityType.type, (XmlOptions)null);
      }

      public static IntegrityType parse(Node node, XmlOptions options) throws XmlException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(node, IntegrityType.type, options);
      }

      /** @deprecated */
      public static IntegrityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(xis, IntegrityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IntegrityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IntegrityType)XmlBeans.getContextTypeLoader().parse(xis, IntegrityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IntegrityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IntegrityType.type, options);
      }

      private Factory() {
      }
   }
}
