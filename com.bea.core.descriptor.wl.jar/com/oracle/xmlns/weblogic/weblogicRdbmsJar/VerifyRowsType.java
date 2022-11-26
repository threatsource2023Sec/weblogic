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

public interface VerifyRowsType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VerifyRowsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("verifyrowstype75eatype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static VerifyRowsType newInstance() {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().newInstance(VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType newInstance(XmlOptions options) {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().newInstance(VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(String xmlAsString) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(File file) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(file, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(file, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(URL u) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(u, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(u, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(InputStream is) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(is, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(is, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(Reader r) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(r, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(r, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(XMLStreamReader sr) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(sr, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(sr, VerifyRowsType.type, options);
      }

      public static VerifyRowsType parse(Node node) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(node, VerifyRowsType.type, (XmlOptions)null);
      }

      public static VerifyRowsType parse(Node node, XmlOptions options) throws XmlException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(node, VerifyRowsType.type, options);
      }

      /** @deprecated */
      public static VerifyRowsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(xis, VerifyRowsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VerifyRowsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VerifyRowsType)XmlBeans.getContextTypeLoader().parse(xis, VerifyRowsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VerifyRowsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VerifyRowsType.type, options);
      }

      private Factory() {
      }
   }
}
