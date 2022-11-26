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

public interface DefaultLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("defaultlockmanagertypefcc3type");

   public static final class Factory {
      public static DefaultLockManagerType newInstance() {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType newInstance(XmlOptions options) {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().newInstance(DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(String xmlAsString) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(File file) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(file, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(URL u) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(u, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(is, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(Reader r) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(r, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, DefaultLockManagerType.type, options);
      }

      public static DefaultLockManagerType parse(Node node) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultLockManagerType.type, (XmlOptions)null);
      }

      public static DefaultLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(node, DefaultLockManagerType.type, options);
      }

      /** @deprecated */
      public static DefaultLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, DefaultLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
