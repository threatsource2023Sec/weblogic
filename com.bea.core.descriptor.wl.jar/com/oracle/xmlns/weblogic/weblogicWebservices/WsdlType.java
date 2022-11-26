package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface WsdlType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsdlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("wsdltypebaa1type");

   boolean getExposed();

   XmlBoolean xgetExposed();

   void setExposed(boolean var1);

   void xsetExposed(XmlBoolean var1);

   public static final class Factory {
      public static WsdlType newInstance() {
         return (WsdlType)XmlBeans.getContextTypeLoader().newInstance(WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType newInstance(XmlOptions options) {
         return (WsdlType)XmlBeans.getContextTypeLoader().newInstance(WsdlType.type, options);
      }

      public static WsdlType parse(String xmlAsString) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlType.type, options);
      }

      public static WsdlType parse(File file) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(file, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(file, WsdlType.type, options);
      }

      public static WsdlType parse(URL u) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(u, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(u, WsdlType.type, options);
      }

      public static WsdlType parse(InputStream is) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(is, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(is, WsdlType.type, options);
      }

      public static WsdlType parse(Reader r) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(r, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(r, WsdlType.type, options);
      }

      public static WsdlType parse(XMLStreamReader sr) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(sr, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(sr, WsdlType.type, options);
      }

      public static WsdlType parse(Node node) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(node, WsdlType.type, (XmlOptions)null);
      }

      public static WsdlType parse(Node node, XmlOptions options) throws XmlException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(node, WsdlType.type, options);
      }

      /** @deprecated */
      public static WsdlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(xis, WsdlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsdlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsdlType)XmlBeans.getContextTypeLoader().parse(xis, WsdlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlType.type, options);
      }

      private Factory() {
      }
   }
}
