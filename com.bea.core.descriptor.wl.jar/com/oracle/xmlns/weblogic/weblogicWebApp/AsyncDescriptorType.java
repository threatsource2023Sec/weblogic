package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AsyncDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AsyncDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("asyncdescriptortype6b13type");

   XsdIntegerType getTimeoutSecs();

   boolean isSetTimeoutSecs();

   void setTimeoutSecs(XsdIntegerType var1);

   XsdIntegerType addNewTimeoutSecs();

   void unsetTimeoutSecs();

   XsdIntegerType getTimeoutCheckIntervalSecs();

   boolean isSetTimeoutCheckIntervalSecs();

   void setTimeoutCheckIntervalSecs(XsdIntegerType var1);

   XsdIntegerType addNewTimeoutCheckIntervalSecs();

   void unsetTimeoutCheckIntervalSecs();

   String getAsyncWorkManager();

   XmlString xgetAsyncWorkManager();

   boolean isSetAsyncWorkManager();

   void setAsyncWorkManager(String var1);

   void xsetAsyncWorkManager(XmlString var1);

   void unsetAsyncWorkManager();

   public static final class Factory {
      public static AsyncDescriptorType newInstance() {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().newInstance(AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType newInstance(XmlOptions options) {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().newInstance(AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(String xmlAsString) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(File file) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(file, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(file, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(URL u) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(u, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(u, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(is, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(is, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(Reader r) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(r, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(r, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, AsyncDescriptorType.type, options);
      }

      public static AsyncDescriptorType parse(Node node) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(node, AsyncDescriptorType.type, (XmlOptions)null);
      }

      public static AsyncDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(node, AsyncDescriptorType.type, options);
      }

      /** @deprecated */
      public static AsyncDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, AsyncDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AsyncDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AsyncDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, AsyncDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsyncDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsyncDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
