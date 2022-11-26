package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdBooleanType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TrueFalseType extends XsdBooleanType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TrueFalseType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("truefalsetype32c3type");

   public static final class Factory {
      public static TrueFalseType newInstance() {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().newInstance(TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType newInstance(XmlOptions options) {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().newInstance(TrueFalseType.type, options);
      }

      public static TrueFalseType parse(String xmlAsString) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(File file) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(file, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(file, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(URL u) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(u, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(u, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(InputStream is) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(is, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(is, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(Reader r) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(r, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(r, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(XMLStreamReader sr) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(sr, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(sr, TrueFalseType.type, options);
      }

      public static TrueFalseType parse(Node node) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(node, TrueFalseType.type, (XmlOptions)null);
      }

      public static TrueFalseType parse(Node node, XmlOptions options) throws XmlException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(node, TrueFalseType.type, options);
      }

      /** @deprecated */
      public static TrueFalseType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(xis, TrueFalseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TrueFalseType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TrueFalseType)XmlBeans.getContextTypeLoader().parse(xis, TrueFalseType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TrueFalseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TrueFalseType.type, options);
      }

      private Factory() {
      }
   }
}
