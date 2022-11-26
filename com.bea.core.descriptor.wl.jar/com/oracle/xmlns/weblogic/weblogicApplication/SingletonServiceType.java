package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface SingletonServiceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SingletonServiceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("singletonservicetype5f05type");

   String getClassName();

   XmlString xgetClassName();

   void setClassName(String var1);

   void xsetClassName(XmlString var1);

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getSingletonUri();

   XmlString xgetSingletonUri();

   boolean isSetSingletonUri();

   void setSingletonUri(String var1);

   void xsetSingletonUri(XmlString var1);

   void unsetSingletonUri();

   public static final class Factory {
      public static SingletonServiceType newInstance() {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().newInstance(SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType newInstance(XmlOptions options) {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().newInstance(SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(String xmlAsString) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(File file) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(file, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(file, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(URL u) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(u, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(u, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(InputStream is) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(is, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(is, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(Reader r) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(r, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(r, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(XMLStreamReader sr) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(sr, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(sr, SingletonServiceType.type, options);
      }

      public static SingletonServiceType parse(Node node) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(node, SingletonServiceType.type, (XmlOptions)null);
      }

      public static SingletonServiceType parse(Node node, XmlOptions options) throws XmlException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(node, SingletonServiceType.type, options);
      }

      /** @deprecated */
      public static SingletonServiceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(xis, SingletonServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SingletonServiceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SingletonServiceType)XmlBeans.getContextTypeLoader().parse(xis, SingletonServiceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonServiceType.type, options);
      }

      private Factory() {
      }
   }
}
