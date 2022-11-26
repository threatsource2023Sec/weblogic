package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AppinfoDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AppinfoDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("appinfo2ea6doctype");

   Appinfo getAppinfo();

   void setAppinfo(Appinfo var1);

   Appinfo addNewAppinfo();

   public static final class Factory {
      public static AppinfoDocument newInstance() {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().newInstance(AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument newInstance(XmlOptions options) {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().newInstance(AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(String xmlAsString) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(File file) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((File)file, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(file, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(URL u) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(u, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(InputStream is) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(is, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(Reader r) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(r, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(XMLStreamReader sr) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(sr, AppinfoDocument.type, options);
      }

      public static AppinfoDocument parse(Node node) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AppinfoDocument.type, (XmlOptions)null);
      }

      public static AppinfoDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(node, AppinfoDocument.type, options);
      }

      /** @deprecated */
      public static AppinfoDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AppinfoDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AppinfoDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AppinfoDocument)XmlBeans.getContextTypeLoader().parse(xis, AppinfoDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AppinfoDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AppinfoDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Appinfo extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Appinfo.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("appinfo650belemtype");

      String getSource();

      XmlAnyURI xgetSource();

      boolean isSetSource();

      void setSource(String var1);

      void xsetSource(XmlAnyURI var1);

      void unsetSource();

      public static final class Factory {
         public static Appinfo newInstance() {
            return (Appinfo)XmlBeans.getContextTypeLoader().newInstance(AppinfoDocument.Appinfo.type, (XmlOptions)null);
         }

         public static Appinfo newInstance(XmlOptions options) {
            return (Appinfo)XmlBeans.getContextTypeLoader().newInstance(AppinfoDocument.Appinfo.type, options);
         }

         private Factory() {
         }
      }
   }
}
