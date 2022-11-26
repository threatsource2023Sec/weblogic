package com.oracle.xmlns.weblogic.webservicePolicyRef;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WsPolicyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("wspolicytype4da5type");

   String getUri();

   void setUri(String var1);

   String addNewUri();

   String getDirection();

   boolean isSetDirection();

   void setDirection(String var1);

   String addNewDirection();

   void unsetDirection();

   StatusType.Enum getStatus();

   StatusType xgetStatus();

   boolean isSetStatus();

   void setStatus(StatusType.Enum var1);

   void xsetStatus(StatusType var1);

   void unsetStatus();

   public static final class Factory {
      public static WsPolicyType newInstance() {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().newInstance(WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType newInstance(XmlOptions options) {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().newInstance(WsPolicyType.type, options);
      }

      public static WsPolicyType parse(java.lang.String xmlAsString) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(File file) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(file, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(file, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(URL u) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(u, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(u, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(InputStream is) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(is, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(is, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(Reader r) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(r, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(r, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(sr, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(sr, WsPolicyType.type, options);
      }

      public static WsPolicyType parse(Node node) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(node, WsPolicyType.type, (XmlOptions)null);
      }

      public static WsPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(node, WsPolicyType.type, options);
      }

      /** @deprecated */
      public static WsPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(xis, WsPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsPolicyType)XmlBeans.getContextTypeLoader().parse(xis, WsPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
