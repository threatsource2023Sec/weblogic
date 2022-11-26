package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TransactionDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("transactiondescriptortypec4b4type");

   XsdNonNegativeIntegerType getTransTimeoutSeconds();

   boolean isSetTransTimeoutSeconds();

   void setTransTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewTransTimeoutSeconds();

   void unsetTransTimeoutSeconds();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TransactionDescriptorType newInstance() {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType newInstance(XmlOptions options) {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(String xmlAsString) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(File file) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(URL u) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(Reader r) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, TransactionDescriptorType.type, options);
      }

      public static TransactionDescriptorType parse(Node node) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, TransactionDescriptorType.type, (XmlOptions)null);
      }

      public static TransactionDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, TransactionDescriptorType.type, options);
      }

      /** @deprecated */
      public static TransactionDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, TransactionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, TransactionDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
