package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface WebType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webtypeac69type");

   PathType getWebUri();

   void setWebUri(PathType var1);

   PathType addNewWebUri();

   String getContextRoot();

   void setContextRoot(String var1);

   String addNewContextRoot();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WebType newInstance() {
         return (WebType)XmlBeans.getContextTypeLoader().newInstance(WebType.type, (XmlOptions)null);
      }

      public static WebType newInstance(XmlOptions options) {
         return (WebType)XmlBeans.getContextTypeLoader().newInstance(WebType.type, options);
      }

      public static WebType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebType.type, options);
      }

      public static WebType parse(File file) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(file, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(file, WebType.type, options);
      }

      public static WebType parse(URL u) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(u, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(u, WebType.type, options);
      }

      public static WebType parse(InputStream is) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(is, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(is, WebType.type, options);
      }

      public static WebType parse(Reader r) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(r, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(r, WebType.type, options);
      }

      public static WebType parse(XMLStreamReader sr) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(sr, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(sr, WebType.type, options);
      }

      public static WebType parse(Node node) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(node, WebType.type, (XmlOptions)null);
      }

      public static WebType parse(Node node, XmlOptions options) throws XmlException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(node, WebType.type, options);
      }

      /** @deprecated */
      public static WebType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(xis, WebType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebType)XmlBeans.getContextTypeLoader().parse(xis, WebType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebType.type, options);
      }

      private Factory() {
      }
   }
}
