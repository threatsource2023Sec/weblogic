package com.sun.java.xml.ns.javaee;

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

public interface NamedMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NamedMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("namedmethodtype8462type");

   String getMethodName();

   void setMethodName(String var1);

   String addNewMethodName();

   MethodParamsType getMethodParams();

   boolean isSetMethodParams();

   void setMethodParams(MethodParamsType var1);

   MethodParamsType addNewMethodParams();

   void unsetMethodParams();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static NamedMethodType newInstance() {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().newInstance(NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType newInstance(XmlOptions options) {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().newInstance(NamedMethodType.type, options);
      }

      public static NamedMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(File file) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(file, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(file, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(URL u) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(u, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(u, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(InputStream is) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(is, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(is, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(Reader r) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(r, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(r, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(XMLStreamReader sr) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(sr, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(sr, NamedMethodType.type, options);
      }

      public static NamedMethodType parse(Node node) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(node, NamedMethodType.type, (XmlOptions)null);
      }

      public static NamedMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(node, NamedMethodType.type, options);
      }

      /** @deprecated */
      public static NamedMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(xis, NamedMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NamedMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NamedMethodType)XmlBeans.getContextTypeLoader().parse(xis, NamedMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NamedMethodType.type, options);
      }

      private Factory() {
      }
   }
}
