package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface BindingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BindingType.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bindingtypefb9ftype");

   String getXmlcomponent();

   XmlSignature xgetXmlcomponent();

   void setXmlcomponent(String var1);

   void xsetXmlcomponent(XmlSignature var1);

   String getJavatype();

   JavaClassName xgetJavatype();

   void setJavatype(String var1);

   void xsetJavatype(JavaClassName var1);

   public static final class Factory {
      /** @deprecated */
      public static BindingType newInstance() {
         return (BindingType)XmlBeans.getContextTypeLoader().newInstance(BindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingType newInstance(XmlOptions options) {
         return (BindingType)XmlBeans.getContextTypeLoader().newInstance(BindingType.type, options);
      }

      public static BindingType parse(String xmlAsString) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingType.type, options);
      }

      public static BindingType parse(File file) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(file, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(file, BindingType.type, options);
      }

      public static BindingType parse(URL u) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(u, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(u, BindingType.type, options);
      }

      public static BindingType parse(InputStream is) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(is, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(is, BindingType.type, options);
      }

      public static BindingType parse(Reader r) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(r, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(r, BindingType.type, options);
      }

      public static BindingType parse(XMLStreamReader sr) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(sr, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(sr, BindingType.type, options);
      }

      public static BindingType parse(Node node) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(node, BindingType.type, (XmlOptions)null);
      }

      public static BindingType parse(Node node, XmlOptions options) throws XmlException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(node, BindingType.type, options);
      }

      /** @deprecated */
      public static BindingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(xis, BindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BindingType)XmlBeans.getContextTypeLoader().parse(xis, BindingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingType.type, options);
      }

      private Factory() {
      }
   }
}
