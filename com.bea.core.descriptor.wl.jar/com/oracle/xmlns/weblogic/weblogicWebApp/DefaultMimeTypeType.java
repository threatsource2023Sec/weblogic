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

public interface DefaultMimeTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultMimeTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultmimetypetypeb6e8type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DefaultMimeTypeType newInstance() {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().newInstance(DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType newInstance(XmlOptions options) {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().newInstance(DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(String xmlAsString) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(File file) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(file, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(file, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(URL u) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(u, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(u, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(InputStream is) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(is, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(is, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(Reader r) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(r, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(r, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(sr, DefaultMimeTypeType.type, options);
      }

      public static DefaultMimeTypeType parse(Node node) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(node, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      public static DefaultMimeTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(node, DefaultMimeTypeType.type, options);
      }

      /** @deprecated */
      public static DefaultMimeTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultMimeTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultMimeTypeType)XmlBeans.getContextTypeLoader().parse(xis, DefaultMimeTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMimeTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultMimeTypeType.type, options);
      }

      private Factory() {
      }
   }
}
