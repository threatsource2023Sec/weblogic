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

public interface ExecutionContextNameProviderDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExecutionContextNameProviderDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("executioncontextnameprovider4e36doctype");

   ExecutionContextNameProviderType getExecutionContextNameProvider();

   void setExecutionContextNameProvider(ExecutionContextNameProviderType var1);

   ExecutionContextNameProviderType addNewExecutionContextNameProvider();

   public static final class Factory {
      public static ExecutionContextNameProviderDocument newInstance() {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().newInstance(ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument newInstance(XmlOptions options) {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().newInstance(ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(String xmlAsString) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(File file) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(file, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(file, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(URL u) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(u, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(u, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(InputStream is) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(is, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(is, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(Reader r) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(r, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(r, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(XMLStreamReader sr) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(sr, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(sr, ExecutionContextNameProviderDocument.type, options);
      }

      public static ExecutionContextNameProviderDocument parse(Node node) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(node, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(node, ExecutionContextNameProviderDocument.type, options);
      }

      /** @deprecated */
      public static ExecutionContextNameProviderDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(xis, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExecutionContextNameProviderDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExecutionContextNameProviderDocument)XmlBeans.getContextTypeLoader().parse(xis, ExecutionContextNameProviderDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExecutionContextNameProviderDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExecutionContextNameProviderDocument.type, options);
      }

      private Factory() {
      }
   }
}
