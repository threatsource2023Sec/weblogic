package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLanguage;
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

public interface DisplayNameType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DisplayNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("displaynametypef095type");

   java.lang.String getLang();

   XmlLanguage xgetLang();

   boolean isSetLang();

   void setLang(java.lang.String var1);

   void xsetLang(XmlLanguage var1);

   void unsetLang();

   public static final class Factory {
      public static DisplayNameType newInstance() {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().newInstance(DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType newInstance(XmlOptions options) {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().newInstance(DisplayNameType.type, options);
      }

      public static DisplayNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(File file) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(file, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(file, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(URL u) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(u, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(u, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(InputStream is) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(is, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(is, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(Reader r) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(r, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(r, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(XMLStreamReader sr) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(sr, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(sr, DisplayNameType.type, options);
      }

      public static DisplayNameType parse(Node node) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(node, DisplayNameType.type, (XmlOptions)null);
      }

      public static DisplayNameType parse(Node node, XmlOptions options) throws XmlException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(node, DisplayNameType.type, options);
      }

      /** @deprecated */
      public static DisplayNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(xis, DisplayNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DisplayNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DisplayNameType)XmlBeans.getContextTypeLoader().parse(xis, DisplayNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisplayNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisplayNameType.type, options);
      }

      private Factory() {
      }
   }
}
