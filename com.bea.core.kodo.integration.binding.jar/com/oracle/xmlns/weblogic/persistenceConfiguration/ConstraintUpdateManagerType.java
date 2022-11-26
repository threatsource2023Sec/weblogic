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

public interface ConstraintUpdateManagerType extends UpdateManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConstraintUpdateManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("constraintupdatemanagertype4973type");

   boolean getMaximizeBatchSize();

   XmlBoolean xgetMaximizeBatchSize();

   boolean isSetMaximizeBatchSize();

   void setMaximizeBatchSize(boolean var1);

   void xsetMaximizeBatchSize(XmlBoolean var1);

   void unsetMaximizeBatchSize();

   public static final class Factory {
      public static ConstraintUpdateManagerType newInstance() {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType newInstance(XmlOptions options) {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().newInstance(ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(String xmlAsString) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(File file) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(file, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(URL u) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(u, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(InputStream is) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(is, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(Reader r) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(r, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(XMLStreamReader sr) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(sr, ConstraintUpdateManagerType.type, options);
      }

      public static ConstraintUpdateManagerType parse(Node node) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      public static ConstraintUpdateManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(node, ConstraintUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static ConstraintUpdateManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConstraintUpdateManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConstraintUpdateManagerType)XmlBeans.getContextTypeLoader().parse(xis, ConstraintUpdateManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConstraintUpdateManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConstraintUpdateManagerType.type, options);
      }

      private Factory() {
      }
   }
}
