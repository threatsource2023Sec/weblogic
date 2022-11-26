package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
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

public interface FastSwapType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FastSwapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("fastswaptype488dtype");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   XsdIntegerType getRefreshInterval();

   boolean isSetRefreshInterval();

   void setRefreshInterval(XsdIntegerType var1);

   XsdIntegerType addNewRefreshInterval();

   void unsetRefreshInterval();

   XsdIntegerType getRedefinitionTaskLimit();

   boolean isSetRedefinitionTaskLimit();

   void setRedefinitionTaskLimit(XsdIntegerType var1);

   XsdIntegerType addNewRedefinitionTaskLimit();

   void unsetRedefinitionTaskLimit();

   public static final class Factory {
      public static FastSwapType newInstance() {
         return (FastSwapType)XmlBeans.getContextTypeLoader().newInstance(FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType newInstance(XmlOptions options) {
         return (FastSwapType)XmlBeans.getContextTypeLoader().newInstance(FastSwapType.type, options);
      }

      public static FastSwapType parse(String xmlAsString) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FastSwapType.type, options);
      }

      public static FastSwapType parse(File file) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(file, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(file, FastSwapType.type, options);
      }

      public static FastSwapType parse(URL u) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(u, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(u, FastSwapType.type, options);
      }

      public static FastSwapType parse(InputStream is) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(is, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(is, FastSwapType.type, options);
      }

      public static FastSwapType parse(Reader r) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(r, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(r, FastSwapType.type, options);
      }

      public static FastSwapType parse(XMLStreamReader sr) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(sr, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(sr, FastSwapType.type, options);
      }

      public static FastSwapType parse(Node node) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(node, FastSwapType.type, (XmlOptions)null);
      }

      public static FastSwapType parse(Node node, XmlOptions options) throws XmlException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(node, FastSwapType.type, options);
      }

      /** @deprecated */
      public static FastSwapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(xis, FastSwapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FastSwapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FastSwapType)XmlBeans.getContextTypeLoader().parse(xis, FastSwapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FastSwapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FastSwapType.type, options);
      }

      private Factory() {
      }
   }
}
