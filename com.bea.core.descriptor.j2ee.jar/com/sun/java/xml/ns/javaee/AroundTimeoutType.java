package com.sun.java.xml.ns.javaee;

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

public interface AroundTimeoutType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AroundTimeoutType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("aroundtimeouttype048etype");

   FullyQualifiedClassType getClass1();

   boolean isSetClass1();

   void setClass1(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewClass1();

   void unsetClass1();

   JavaIdentifierType getMethodName();

   void setMethodName(JavaIdentifierType var1);

   JavaIdentifierType addNewMethodName();

   public static final class Factory {
      public static AroundTimeoutType newInstance() {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().newInstance(AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType newInstance(XmlOptions options) {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().newInstance(AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(java.lang.String xmlAsString) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(File file) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(file, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(file, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(URL u) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(u, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(u, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(InputStream is) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(is, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(is, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(Reader r) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(r, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(r, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(XMLStreamReader sr) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(sr, AroundTimeoutType.type, options);
      }

      public static AroundTimeoutType parse(Node node) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(node, AroundTimeoutType.type, (XmlOptions)null);
      }

      public static AroundTimeoutType parse(Node node, XmlOptions options) throws XmlException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(node, AroundTimeoutType.type, options);
      }

      /** @deprecated */
      public static AroundTimeoutType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, AroundTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AroundTimeoutType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AroundTimeoutType)XmlBeans.getContextTypeLoader().parse(xis, AroundTimeoutType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AroundTimeoutType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AroundTimeoutType.type, options);
      }

      private Factory() {
      }
   }
}
