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

public interface NoneLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NoneLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("nonelockmanagertypec8actype");

   public static final class Factory {
      public static NoneLockManagerType newInstance() {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().newInstance(NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType newInstance(XmlOptions options) {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().newInstance(NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(String xmlAsString) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(File file) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(file, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(file, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(URL u) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(u, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(u, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(is, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(is, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(Reader r) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(r, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(r, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, NoneLockManagerType.type, options);
      }

      public static NoneLockManagerType parse(Node node) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(node, NoneLockManagerType.type, (XmlOptions)null);
      }

      public static NoneLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(node, NoneLockManagerType.type, options);
      }

      /** @deprecated */
      public static NoneLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, NoneLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NoneLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NoneLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, NoneLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NoneLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
