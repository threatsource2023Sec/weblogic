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

public interface VerifyColumnsType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VerifyColumnsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("verifycolumnstype0478type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static VerifyColumnsType newInstance() {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().newInstance(VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType newInstance(XmlOptions options) {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().newInstance(VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(String xmlAsString) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(File file) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(file, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(file, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(URL u) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(u, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(u, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(InputStream is) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(is, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(is, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(Reader r) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(r, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(r, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(XMLStreamReader sr) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(sr, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(sr, VerifyColumnsType.type, options);
      }

      public static VerifyColumnsType parse(Node node) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(node, VerifyColumnsType.type, (XmlOptions)null);
      }

      public static VerifyColumnsType parse(Node node, XmlOptions options) throws XmlException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(node, VerifyColumnsType.type, options);
      }

      /** @deprecated */
      public static VerifyColumnsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(xis, VerifyColumnsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VerifyColumnsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VerifyColumnsType)XmlBeans.getContextTypeLoader().parse(xis, VerifyColumnsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VerifyColumnsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VerifyColumnsType.type, options);
      }

      private Factory() {
      }
   }
}
