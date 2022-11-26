package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface InitAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InitAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("initasprincipalnametype033btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InitAsPrincipalNameType newInstance() {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType newInstance(XmlOptions options) {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, InitAsPrincipalNameType.type, options);
      }

      public static InitAsPrincipalNameType parse(Node node) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static InitAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, InitAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static InitAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InitAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InitAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, InitAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
