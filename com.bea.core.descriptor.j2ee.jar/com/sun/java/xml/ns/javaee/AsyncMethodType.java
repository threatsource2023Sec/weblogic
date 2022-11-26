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

public interface AsyncMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AsyncMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("asyncmethodtype49fftype");

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
      public static AsyncMethodType newInstance() {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().newInstance(AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType newInstance(XmlOptions options) {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().newInstance(AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(File file) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(file, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(file, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(URL u) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(u, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(u, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(InputStream is) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(is, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(is, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(Reader r) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(r, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(r, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(XMLStreamReader sr) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(sr, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(sr, AsyncMethodType.type, options);
      }

      public static AsyncMethodType parse(Node node) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(node, AsyncMethodType.type, (XmlOptions)null);
      }

      public static AsyncMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(node, AsyncMethodType.type, options);
      }

      /** @deprecated */
      public static AsyncMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(xis, AsyncMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AsyncMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AsyncMethodType)XmlBeans.getContextTypeLoader().parse(xis, AsyncMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsyncMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsyncMethodType.type, options);
      }

      private Factory() {
      }
   }
}
