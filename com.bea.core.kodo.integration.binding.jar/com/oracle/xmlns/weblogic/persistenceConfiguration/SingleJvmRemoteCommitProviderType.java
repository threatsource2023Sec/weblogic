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

public interface SingleJvmRemoteCommitProviderType extends RemoteCommitProviderType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SingleJvmRemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("singlejvmremotecommitprovidertypebc7btype");

   public static final class Factory {
      public static SingleJvmRemoteCommitProviderType newInstance() {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType newInstance(XmlOptions options) {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, SingleJvmRemoteCommitProviderType.type, options);
      }

      public static SingleJvmRemoteCommitProviderType parse(Node node) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static SingleJvmRemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, SingleJvmRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static SingleJvmRemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SingleJvmRemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SingleJvmRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, SingleJvmRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingleJvmRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingleJvmRemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }
}
