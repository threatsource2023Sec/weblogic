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

public interface RemoteCommitProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("remotecommitprovidertype3cbatype");

   String getName();

   XmlString xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   void unsetName();

   public static final class Factory {
      public static RemoteCommitProviderType newInstance() {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType newInstance(XmlOptions options) {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, RemoteCommitProviderType.type, options);
      }

      public static RemoteCommitProviderType parse(Node node) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static RemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, RemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static RemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, RemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }
}
