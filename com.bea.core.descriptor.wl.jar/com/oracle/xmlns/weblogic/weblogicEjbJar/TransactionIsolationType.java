package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface TransactionIsolationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionIsolationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("transactionisolationtypeb7e3type");

   IsolationLevelType getIsolationLevel();

   void setIsolationLevel(IsolationLevelType var1);

   IsolationLevelType addNewIsolationLevel();

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TransactionIsolationType newInstance() {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().newInstance(TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType newInstance(XmlOptions options) {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().newInstance(TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(String xmlAsString) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(File file) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(file, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(file, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(URL u) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(u, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(u, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(InputStream is) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(is, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(is, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(Reader r) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(r, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(r, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(sr, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(sr, TransactionIsolationType.type, options);
      }

      public static TransactionIsolationType parse(Node node) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(node, TransactionIsolationType.type, (XmlOptions)null);
      }

      public static TransactionIsolationType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(node, TransactionIsolationType.type, options);
      }

      /** @deprecated */
      public static TransactionIsolationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(xis, TransactionIsolationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionIsolationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionIsolationType)XmlBeans.getContextTypeLoader().parse(xis, TransactionIsolationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionIsolationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionIsolationType.type, options);
      }

      private Factory() {
      }
   }
}
