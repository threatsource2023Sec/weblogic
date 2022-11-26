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

public interface WebResourceCollectionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebResourceCollectionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webresourcecollectiontype8179type");

   String getWebResourceName();

   void setWebResourceName(String var1);

   String addNewWebResourceName();

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   UrlPatternType[] getUrlPatternArray();

   UrlPatternType getUrlPatternArray(int var1);

   int sizeOfUrlPatternArray();

   void setUrlPatternArray(UrlPatternType[] var1);

   void setUrlPatternArray(int var1, UrlPatternType var2);

   UrlPatternType insertNewUrlPattern(int var1);

   UrlPatternType addNewUrlPattern();

   void removeUrlPattern(int var1);

   java.lang.String[] getHttpMethodArray();

   java.lang.String getHttpMethodArray(int var1);

   HttpMethodType[] xgetHttpMethodArray();

   HttpMethodType xgetHttpMethodArray(int var1);

   int sizeOfHttpMethodArray();

   void setHttpMethodArray(java.lang.String[] var1);

   void setHttpMethodArray(int var1, java.lang.String var2);

   void xsetHttpMethodArray(HttpMethodType[] var1);

   void xsetHttpMethodArray(int var1, HttpMethodType var2);

   void insertHttpMethod(int var1, java.lang.String var2);

   void addHttpMethod(java.lang.String var1);

   HttpMethodType insertNewHttpMethod(int var1);

   HttpMethodType addNewHttpMethod();

   void removeHttpMethod(int var1);

   java.lang.String[] getHttpMethodOmissionArray();

   java.lang.String getHttpMethodOmissionArray(int var1);

   HttpMethodType[] xgetHttpMethodOmissionArray();

   HttpMethodType xgetHttpMethodOmissionArray(int var1);

   int sizeOfHttpMethodOmissionArray();

   void setHttpMethodOmissionArray(java.lang.String[] var1);

   void setHttpMethodOmissionArray(int var1, java.lang.String var2);

   void xsetHttpMethodOmissionArray(HttpMethodType[] var1);

   void xsetHttpMethodOmissionArray(int var1, HttpMethodType var2);

   void insertHttpMethodOmission(int var1, java.lang.String var2);

   void addHttpMethodOmission(java.lang.String var1);

   HttpMethodType insertNewHttpMethodOmission(int var1);

   HttpMethodType addNewHttpMethodOmission();

   void removeHttpMethodOmission(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WebResourceCollectionType newInstance() {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().newInstance(WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType newInstance(XmlOptions options) {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().newInstance(WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(File file) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(file, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(file, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(URL u) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(u, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(u, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(InputStream is) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(is, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(is, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(Reader r) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(r, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(r, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(XMLStreamReader sr) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(sr, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(sr, WebResourceCollectionType.type, options);
      }

      public static WebResourceCollectionType parse(Node node) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(node, WebResourceCollectionType.type, (XmlOptions)null);
      }

      public static WebResourceCollectionType parse(Node node, XmlOptions options) throws XmlException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(node, WebResourceCollectionType.type, options);
      }

      /** @deprecated */
      public static WebResourceCollectionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(xis, WebResourceCollectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebResourceCollectionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebResourceCollectionType)XmlBeans.getContextTypeLoader().parse(xis, WebResourceCollectionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebResourceCollectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebResourceCollectionType.type, options);
      }

      private Factory() {
      }
   }
}
