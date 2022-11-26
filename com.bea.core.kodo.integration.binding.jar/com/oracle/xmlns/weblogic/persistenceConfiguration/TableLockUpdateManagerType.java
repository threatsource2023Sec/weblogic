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

public interface TableLockUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableLockUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tablelockupdatemanagertypec4a0type");

   boolean getMaximizeBatchSize();

   XmlBoolean xgetMaximizeBatchSize();

   boolean isSetMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);

   void xsetMaximizeBatchSize(XmlBoolean var1);

   void unsetMaximizeBatchSize();

   public static final class Factory {
      public static TableLockUpdateManagerType newInstance() {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType newInstance(XmlOptions options) {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(File file) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, TableLockUpdateManagerType.type, options);
      }

      public static TableLockUpdateManagerType parse(Node node) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      public static TableLockUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, TableLockUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static TableLockUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableLockUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableLockUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, TableLockUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableLockUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableLockUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
