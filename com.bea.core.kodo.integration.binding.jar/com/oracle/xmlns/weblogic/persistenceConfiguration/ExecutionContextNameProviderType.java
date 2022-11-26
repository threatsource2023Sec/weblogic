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

public interface ExecutionContextNameProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExecutionContextNameProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("executioncontextnameprovidertype540ctype");

   StackExecutionContextNameProviderType getStackExecutionContextNameProvider();

   boolean isNilStackExecutionContextNameProvider();

   boolean isSetStackExecutionContextNameProvider();

   void setStackExecutionContextNameProvider(StackExecutionContextNameProviderType var1);

   StackExecutionContextNameProviderType addNewStackExecutionContextNameProvider();

   void setNilStackExecutionContextNameProvider();

   void unsetStackExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderType getTransactionNameExecutionContextNameProvider();

   boolean isNilTransactionNameExecutionContextNameProvider();

   boolean isSetTransactionNameExecutionContextNameProvider();

   void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderType var1);

   TransactionNameExecutionContextNameProviderType addNewTransactionNameExecutionContextNameProvider();

   void setNilTransactionNameExecutionContextNameProvider();

   void unsetTransactionNameExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderType getUserObjectExecutionContextNameProvider();

   boolean isNilUserObjectExecutionContextNameProvider();

   boolean isSetUserObjectExecutionContextNameProvider();

   void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderType var1);

   UserObjectExecutionContextNameProviderType addNewUserObjectExecutionContextNameProvider();

   void setNilUserObjectExecutionContextNameProvider();

   void unsetUserObjectExecutionContextNameProvider();

   public static final class Factory {
      public static ExecutionContextNameProviderType newInstance() {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType newInstance(XmlOptions options) {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(String xmlAsString) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(File file) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(URL u) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(InputStream is) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(Reader r) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(XMLStreamReader sr) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, ExecutionContextNameProviderType.type, options);
      }

      public static ExecutionContextNameProviderType parse(Node node) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static ExecutionContextNameProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, ExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static ExecutionContextNameProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExecutionContextNameProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, ExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExecutionContextNameProviderType.type, options);
      }

      private Factory() {
      }
   }
}
