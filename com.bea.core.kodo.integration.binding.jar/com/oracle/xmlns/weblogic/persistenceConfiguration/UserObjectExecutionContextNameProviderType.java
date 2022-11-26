package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface UserObjectExecutionContextNameProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UserObjectExecutionContextNameProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("userobjectexecutioncontextnameprovidertype6a58type");

   String getKey();

   XmlString xgetKey();

   void setKey(String var1);

   void xsetKey(XmlString var1);

   public static final class Factory {
      public static UserObjectExecutionContextNameProviderType newInstance() {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType newInstance(XmlOptions options) {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().newInstance(UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(String xmlAsString) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(File file) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(file, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(URL u) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(u, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(InputStream is) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(is, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(Reader r) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(r, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(XMLStreamReader sr) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(sr, UserObjectExecutionContextNameProviderType.type, options);
      }

      public static UserObjectExecutionContextNameProviderType parse(Node node) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      public static UserObjectExecutionContextNameProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(node, UserObjectExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static UserObjectExecutionContextNameProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UserObjectExecutionContextNameProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UserObjectExecutionContextNameProviderType)XmlBeans.getContextTypeLoader().parse(xis, UserObjectExecutionContextNameProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UserObjectExecutionContextNameProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UserObjectExecutionContextNameProviderType.type, options);
      }

      private Factory() {
      }
   }
}
