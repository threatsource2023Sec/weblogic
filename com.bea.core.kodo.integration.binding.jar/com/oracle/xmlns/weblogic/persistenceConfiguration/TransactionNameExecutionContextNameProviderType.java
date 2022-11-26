package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface TransactionNameExecutionContextNameProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionNameExecutionContextNameProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("transactionnameexecutioncontextnameprovidertype85edtype");

   public static final class Factory {
      public static TransactionNameExecutionContextNameProviderType newInstance() {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType newInstance(XmlOptions options) {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(String xmlAsString) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(File file) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(URL u) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(InputStream is) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(Reader r) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, TransactionNameExecutionContextNameProviderType.type, options);
      }

      public static TransactionNameExecutionContextNameProviderType parse(Node node) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static TransactionNameExecutionContextNameProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, TransactionNameExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static TransactionNameExecutionContextNameProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionNameExecutionContextNameProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionNameExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, TransactionNameExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionNameExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionNameExecutionContextNameProviderType.type, options);
      }

      private Factory() {
      }
   }
}
