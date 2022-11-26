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

public interface WlDispatchPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlDispatchPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("wldispatchpolicytype34a0type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WlDispatchPolicyType newInstance() {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().newInstance(WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType newInstance(XmlOptions options) {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().newInstance(WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(String xmlAsString) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(File file) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(file, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(file, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(URL u) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(u, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(u, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(InputStream is) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(is, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(is, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(Reader r) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(r, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(r, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(sr, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(sr, WlDispatchPolicyType.type, options);
      }

      public static WlDispatchPolicyType parse(Node node) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(node, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      public static WlDispatchPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(node, WlDispatchPolicyType.type, options);
      }

      /** @deprecated */
      public static WlDispatchPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xis, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlDispatchPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlDispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xis, WlDispatchPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlDispatchPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlDispatchPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
