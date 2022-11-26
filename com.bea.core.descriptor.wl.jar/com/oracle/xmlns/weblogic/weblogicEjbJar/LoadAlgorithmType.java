package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface LoadAlgorithmType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoadAlgorithmType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("loadalgorithmtype90betype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LoadAlgorithmType newInstance() {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().newInstance(LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType newInstance(XmlOptions options) {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().newInstance(LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(String xmlAsString) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(File file) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(file, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(file, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(URL u) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(u, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(u, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(InputStream is) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(is, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(is, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(Reader r) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(r, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(r, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(XMLStreamReader sr) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(sr, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(sr, LoadAlgorithmType.type, options);
      }

      public static LoadAlgorithmType parse(Node node) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(node, LoadAlgorithmType.type, (XmlOptions)null);
      }

      public static LoadAlgorithmType parse(Node node, XmlOptions options) throws XmlException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(node, LoadAlgorithmType.type, options);
      }

      /** @deprecated */
      public static LoadAlgorithmType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(xis, LoadAlgorithmType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoadAlgorithmType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoadAlgorithmType)XmlBeans.getContextTypeLoader().parse(xis, LoadAlgorithmType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadAlgorithmType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadAlgorithmType.type, options);
      }

      private Factory() {
      }
   }
}
