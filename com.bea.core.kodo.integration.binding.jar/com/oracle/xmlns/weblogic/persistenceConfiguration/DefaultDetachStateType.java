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

public interface DefaultDetachStateType extends DetachStateType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDetachStateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultdetachstatetypeba77type");

   public static final class Factory {
      public static DefaultDetachStateType newInstance() {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().newInstance(DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType newInstance(XmlOptions options) {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().newInstance(DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(String xmlAsString) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(File file) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(file, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(file, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(URL u) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(u, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(u, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(is, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(is, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(Reader r) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(r, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(r, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDetachStateType.type, options);
      }

      public static DefaultDetachStateType parse(Node node) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(node, DefaultDetachStateType.type, (XmlOptions)null);
      }

      public static DefaultDetachStateType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(node, DefaultDetachStateType.type, options);
      }

      /** @deprecated */
      public static DefaultDetachStateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDetachStateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDetachStateType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDetachStateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDetachStateType.type, options);
      }

      private Factory() {
      }
   }
}
