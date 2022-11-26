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

public interface WebservicesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebservicesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webservicestypee94btype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   WebserviceDescriptionType[] getWebserviceDescriptionArray();

   WebserviceDescriptionType getWebserviceDescriptionArray(int var1);

   int sizeOfWebserviceDescriptionArray();

   void setWebserviceDescriptionArray(WebserviceDescriptionType[] var1);

   void setWebserviceDescriptionArray(int var1, WebserviceDescriptionType var2);

   WebserviceDescriptionType insertNewWebserviceDescription(int var1);

   WebserviceDescriptionType addNewWebserviceDescription();

   void removeWebserviceDescription(int var1);

   java.lang.String getVersion();

   DeweyVersionType xgetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(DeweyVersionType var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WebservicesType newInstance() {
         return (WebservicesType)XmlBeans.getContextTypeLoader().newInstance(WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType newInstance(XmlOptions options) {
         return (WebservicesType)XmlBeans.getContextTypeLoader().newInstance(WebservicesType.type, options);
      }

      public static WebservicesType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicesType.type, options);
      }

      public static WebservicesType parse(File file) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(file, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(file, WebservicesType.type, options);
      }

      public static WebservicesType parse(URL u) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(u, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(u, WebservicesType.type, options);
      }

      public static WebservicesType parse(InputStream is) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(is, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(is, WebservicesType.type, options);
      }

      public static WebservicesType parse(Reader r) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(r, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(r, WebservicesType.type, options);
      }

      public static WebservicesType parse(XMLStreamReader sr) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(sr, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(sr, WebservicesType.type, options);
      }

      public static WebservicesType parse(Node node) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(node, WebservicesType.type, (XmlOptions)null);
      }

      public static WebservicesType parse(Node node, XmlOptions options) throws XmlException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(node, WebservicesType.type, options);
      }

      /** @deprecated */
      public static WebservicesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(xis, WebservicesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebservicesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebservicesType)XmlBeans.getContextTypeLoader().parse(xis, WebservicesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicesType.type, options);
      }

      private Factory() {
      }
   }
}
