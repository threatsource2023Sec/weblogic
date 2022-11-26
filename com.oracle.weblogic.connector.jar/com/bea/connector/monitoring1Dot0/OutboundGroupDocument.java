package com.bea.connector.monitoring1Dot0;

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

public interface OutboundGroupDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundGroupDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("outboundgroupc6ccdoctype");

   OutboundGroup getOutboundGroup();

   void setOutboundGroup(OutboundGroup var1);

   OutboundGroup addNewOutboundGroup();

   public static final class Factory {
      public static OutboundGroupDocument newInstance() {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().newInstance(OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument newInstance(XmlOptions options) {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().newInstance(OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(String xmlAsString) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(File file) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(file, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(file, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(URL u) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(u, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(u, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(InputStream is) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(is, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(is, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(Reader r) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(r, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(r, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(XMLStreamReader sr) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(sr, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(sr, OutboundGroupDocument.type, options);
      }

      public static OutboundGroupDocument parse(Node node) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(node, OutboundGroupDocument.type, (XmlOptions)null);
      }

      public static OutboundGroupDocument parse(Node node, XmlOptions options) throws XmlException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(node, OutboundGroupDocument.type, options);
      }

      /** @deprecated */
      public static OutboundGroupDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(xis, OutboundGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OutboundGroupDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OutboundGroupDocument)XmlBeans.getContextTypeLoader().parse(xis, OutboundGroupDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundGroupDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface OutboundGroup extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundGroup.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("outboundgroupde4aelemtype");

      String getManagedconnectionfactoryClass();

      XmlString xgetManagedconnectionfactoryClass();

      void setManagedconnectionfactoryClass(String var1);

      void xsetManagedconnectionfactoryClass(XmlString var1);

      String getConnectionFactoryInterface();

      XmlString xgetConnectionFactoryInterface();

      void setConnectionFactoryInterface(String var1);

      void xsetConnectionFactoryInterface(XmlString var1);

      String getConnectionfactoryImplClass();

      XmlString xgetConnectionfactoryImplClass();

      void setConnectionfactoryImplClass(String var1);

      void xsetConnectionfactoryImplClass(XmlString var1);

      String getConnectionInterface();

      XmlString xgetConnectionInterface();

      void setConnectionInterface(String var1);

      void xsetConnectionInterface(XmlString var1);

      String getConnectionImplClass();

      XmlString xgetConnectionImplClass();

      void setConnectionImplClass(String var1);

      void xsetConnectionImplClass(XmlString var1);

      ConnectionInstanceDocument.ConnectionInstance[] getConnectionInstanceArray();

      ConnectionInstanceDocument.ConnectionInstance getConnectionInstanceArray(int var1);

      int sizeOfConnectionInstanceArray();

      void setConnectionInstanceArray(ConnectionInstanceDocument.ConnectionInstance[] var1);

      void setConnectionInstanceArray(int var1, ConnectionInstanceDocument.ConnectionInstance var2);

      ConnectionInstanceDocument.ConnectionInstance insertNewConnectionInstance(int var1);

      ConnectionInstanceDocument.ConnectionInstance addNewConnectionInstance();

      void removeConnectionInstance(int var1);

      public static final class Factory {
         public static OutboundGroup newInstance() {
            return (OutboundGroup)XmlBeans.getContextTypeLoader().newInstance(OutboundGroupDocument.OutboundGroup.type, (XmlOptions)null);
         }

         public static OutboundGroup newInstance(XmlOptions options) {
            return (OutboundGroup)XmlBeans.getContextTypeLoader().newInstance(OutboundGroupDocument.OutboundGroup.type, options);
         }

         private Factory() {
         }
      }
   }
}
