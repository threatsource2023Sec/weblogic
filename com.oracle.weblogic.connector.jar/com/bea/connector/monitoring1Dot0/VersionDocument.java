package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface VersionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VersionDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("versiona928doctype");

   String getVersion();

   XmlString xgetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   public static final class Factory {
      public static VersionDocument newInstance() {
         return (VersionDocument)XmlBeans.getContextTypeLoader().newInstance(VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument newInstance(XmlOptions options) {
         return (VersionDocument)XmlBeans.getContextTypeLoader().newInstance(VersionDocument.type, options);
      }

      public static VersionDocument parse(String xmlAsString) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionDocument.type, options);
      }

      public static VersionDocument parse(File file) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(file, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(file, VersionDocument.type, options);
      }

      public static VersionDocument parse(URL u) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(u, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(u, VersionDocument.type, options);
      }

      public static VersionDocument parse(InputStream is) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(is, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(is, VersionDocument.type, options);
      }

      public static VersionDocument parse(Reader r) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(r, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(r, VersionDocument.type, options);
      }

      public static VersionDocument parse(XMLStreamReader sr) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(sr, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(sr, VersionDocument.type, options);
      }

      public static VersionDocument parse(Node node) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(node, VersionDocument.type, (XmlOptions)null);
      }

      public static VersionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(node, VersionDocument.type, options);
      }

      /** @deprecated */
      public static VersionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(xis, VersionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VersionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VersionDocument)XmlBeans.getContextTypeLoader().parse(xis, VersionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionDocument.type, options);
      }

      private Factory() {
      }
   }
}
