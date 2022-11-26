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

public interface OperationOrderUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OperationOrderUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("operationorderupdatemanagertype5e8ctype");

   public static final class Factory {
      public static OperationOrderUpdateManagerType newInstance() {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType newInstance(XmlOptions options) {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(File file) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, OperationOrderUpdateManagerType.type, options);
      }

      public static OperationOrderUpdateManagerType parse(Node node) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      public static OperationOrderUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, OperationOrderUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static OperationOrderUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OperationOrderUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OperationOrderUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, OperationOrderUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationOrderUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationOrderUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
