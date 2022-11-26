package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface AbstractStoreBrokerFactoryType extends BrokerFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AbstractStoreBrokerFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("abstractstorebrokerfactorytype00bftype");

   public static final class Factory {
      public static AbstractStoreBrokerFactoryType newInstance() {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType newInstance(XmlOptions options) {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().newInstance(AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(String xmlAsString) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(File file) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(file, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(URL u) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(u, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(InputStream is) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(is, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(Reader r) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(r, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(sr, AbstractStoreBrokerFactoryType.type, options);
      }

      public static AbstractStoreBrokerFactoryType parse(Node node) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      public static AbstractStoreBrokerFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(node, AbstractStoreBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static AbstractStoreBrokerFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AbstractStoreBrokerFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AbstractStoreBrokerFactoryType)XmlBeans.getContextTypeLoader().parse(xis, AbstractStoreBrokerFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AbstractStoreBrokerFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AbstractStoreBrokerFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
