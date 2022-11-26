package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface WeblogicWebservicesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWebservicesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwebservicestype136ctype");

   WebserviceDescriptionType[] getWebserviceDescriptionArray();

   WebserviceDescriptionType getWebserviceDescriptionArray(int var1);

   int sizeOfWebserviceDescriptionArray();

   void setWebserviceDescriptionArray(WebserviceDescriptionType[] var1);

   void setWebserviceDescriptionArray(int var1, WebserviceDescriptionType var2);

   WebserviceDescriptionType insertNewWebserviceDescription(int var1);

   WebserviceDescriptionType addNewWebserviceDescription();

   void removeWebserviceDescription(int var1);

   WebserviceSecurityType getWebserviceSecurity();

   boolean isSetWebserviceSecurity();

   void setWebserviceSecurity(WebserviceSecurityType var1);

   WebserviceSecurityType addNewWebserviceSecurity();

   void unsetWebserviceSecurity();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicWebservicesType newInstance() {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType newInstance(XmlOptions options) {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(String xmlAsString) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(File file) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(URL u) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(Reader r) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWebservicesType.type, options);
      }

      public static WeblogicWebservicesType parse(Node node) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      public static WeblogicWebservicesType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWebservicesType.type, options);
      }

      /** @deprecated */
      public static WeblogicWebservicesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWebservicesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWebservicesType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWebservicesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebservicesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWebservicesType.type, options);
      }

      private Factory() {
      }
   }
}
