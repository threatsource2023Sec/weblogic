package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TcpRemoteCommitProviderType extends RemoteCommitProviderType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TcpRemoteCommitProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tcpremotecommitprovidertype284etype");

   int getMaxIdle();

   XmlInt xgetMaxIdle();

   boolean isSetMaxIdle();

   void setMaxIdle(int var1);

   void xsetMaxIdle(XmlInt var1);

   void unsetMaxIdle();

   int getNumBroadcastThreads();

   XmlInt xgetNumBroadcastThreads();

   boolean isSetNumBroadcastThreads();

   void setNumBroadcastThreads(int var1);

   void xsetNumBroadcastThreads(XmlInt var1);

   void unsetNumBroadcastThreads();

   int getRecoveryTimeMillis();

   XmlInt xgetRecoveryTimeMillis();

   boolean isSetRecoveryTimeMillis();

   void setRecoveryTimeMillis(int var1);

   void xsetRecoveryTimeMillis(XmlInt var1);

   void unsetRecoveryTimeMillis();

   int getMaxActive();

   XmlInt xgetMaxActive();

   boolean isSetMaxActive();

   void setMaxActive(int var1);

   void xsetMaxActive(XmlInt var1);

   void unsetMaxActive();

   int getPort();

   XmlInt xgetPort();

   boolean isSetPort();

   void setPort(int var1);

   void xsetPort(XmlInt var1);

   void unsetPort();

   String getAddresses();

   XmlString xgetAddresses();

   boolean isNilAddresses();

   boolean isSetAddresses();

   void setAddresses(String var1);

   void xsetAddresses(XmlString var1);

   void setNilAddresses();

   void unsetAddresses();

   public static final class Factory {
      public static TcpRemoteCommitProviderType newInstance() {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType newInstance(XmlOptions options) {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().newInstance(TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(String xmlAsString) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(File file) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(file, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(URL u) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(u, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(InputStream is) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(is, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(Reader r) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(r, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(XMLStreamReader sr) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(sr, TcpRemoteCommitProviderType.type, options);
      }

      public static TcpRemoteCommitProviderType parse(Node node) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      public static TcpRemoteCommitProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(node, TcpRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static TcpRemoteCommitProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TcpRemoteCommitProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TcpRemoteCommitProviderType)XmlBeans.getContextTypeLoader().parse(xis, TcpRemoteCommitProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TcpRemoteCommitProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TcpRemoteCommitProviderType.type, options);
      }

      private Factory() {
      }
   }
}
