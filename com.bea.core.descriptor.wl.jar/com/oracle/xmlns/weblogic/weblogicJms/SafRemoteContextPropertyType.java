package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface SafRemoteContextPropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafRemoteContextPropertyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safremotecontextpropertytype31e3type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getValue();

   XmlString xgetValue();

   void setValue(String var1);

   void xsetValue(XmlString var1);

   public static final class Factory {
      public static SafRemoteContextPropertyType newInstance() {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().newInstance(SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType newInstance(XmlOptions options) {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().newInstance(SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(String xmlAsString) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(File file) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(file, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(file, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(URL u) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(u, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(u, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(InputStream is) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(is, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(is, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(Reader r) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(r, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(r, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(XMLStreamReader sr) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(sr, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(sr, SafRemoteContextPropertyType.type, options);
      }

      public static SafRemoteContextPropertyType parse(Node node) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(node, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      public static SafRemoteContextPropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(node, SafRemoteContextPropertyType.type, options);
      }

      /** @deprecated */
      public static SafRemoteContextPropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(xis, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafRemoteContextPropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafRemoteContextPropertyType)XmlBeans.getContextTypeLoader().parse(xis, SafRemoteContextPropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafRemoteContextPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafRemoteContextPropertyType.type, options);
      }

      private Factory() {
      }
   }
}
