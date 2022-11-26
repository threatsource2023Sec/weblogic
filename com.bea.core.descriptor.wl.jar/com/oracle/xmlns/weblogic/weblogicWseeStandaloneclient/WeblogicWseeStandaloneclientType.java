package com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicWseeStandaloneclientType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicWseeStandaloneclientType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicwseestandaloneclienttypebf8atype");

   ServiceRefType getServiceRef();

   void setServiceRef(ServiceRefType var1);

   ServiceRefType addNewServiceRef();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicWseeStandaloneclientType newInstance() {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType newInstance(XmlOptions options) {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().newInstance(WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(String xmlAsString) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(File file) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(file, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(URL u) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(u, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(is, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(Reader r) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(r, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicWseeStandaloneclientType.type, options);
      }

      public static WeblogicWseeStandaloneclientType parse(Node node) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      public static WeblogicWseeStandaloneclientType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(node, WeblogicWseeStandaloneclientType.type, options);
      }

      /** @deprecated */
      public static WeblogicWseeStandaloneclientType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicWseeStandaloneclientType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicWseeStandaloneclientType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicWseeStandaloneclientType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeStandaloneclientType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicWseeStandaloneclientType.type, options);
      }

      private Factory() {
      }
   }
}
