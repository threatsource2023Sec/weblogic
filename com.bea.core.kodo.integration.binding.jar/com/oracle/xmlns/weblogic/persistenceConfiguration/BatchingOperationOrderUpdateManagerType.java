package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface BatchingOperationOrderUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BatchingOperationOrderUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("batchingoperationorderupdatemanagertype9359type");

   boolean getMaximizeBatchSize();

   XmlBoolean xgetMaximizeBatchSize();

   boolean isSetMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);

   void xsetMaximizeBatchSize(XmlBoolean var1);

   void unsetMaximizeBatchSize();

   public static final class Factory {
      public static BatchingOperationOrderUpdateManagerType newInstance() {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType newInstance(XmlOptions options) {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(File file) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, BatchingOperationOrderUpdateManagerType.type, options);
      }

      public static BatchingOperationOrderUpdateManagerType parse(Node node) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static BatchingOperationOrderUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, BatchingOperationOrderUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static BatchingOperationOrderUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BatchingOperationOrderUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BatchingOperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, BatchingOperationOrderUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BatchingOperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BatchingOperationOrderUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
