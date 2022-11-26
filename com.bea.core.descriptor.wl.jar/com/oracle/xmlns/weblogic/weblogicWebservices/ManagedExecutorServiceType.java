package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface ManagedExecutorServiceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ManagedExecutorServiceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("managedexecutorservicetype2fa6type");

   DispatchPolicyType getName();

   void setName(DispatchPolicyType var1);

   DispatchPolicyType addNewName();

   DispatchPolicyType getDispatchPolicy();

   boolean isSetDispatchPolicy();

   void setDispatchPolicy(DispatchPolicyType var1);

   DispatchPolicyType addNewDispatchPolicy();

   void unsetDispatchPolicy();

   XsdNonNegativeIntegerType getMaxConcurrentLongRunningRequests();

   boolean isSetMaxConcurrentLongRunningRequests();

   void setMaxConcurrentLongRunningRequests(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxConcurrentLongRunningRequests();

   void unsetMaxConcurrentLongRunningRequests();

   XsdNonNegativeIntegerType getLongRunningPriority();

   boolean isSetLongRunningPriority();

   void setLongRunningPriority(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewLongRunningPriority();

   void unsetLongRunningPriority();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ManagedExecutorServiceType newInstance() {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().newInstance(ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType newInstance(XmlOptions options) {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().newInstance(ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(String xmlAsString) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(File file) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(file, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(file, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(URL u) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(u, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(u, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(InputStream is) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(is, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(is, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(Reader r) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(r, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(r, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(XMLStreamReader sr) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(sr, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(sr, ManagedExecutorServiceType.type, options);
      }

      public static ManagedExecutorServiceType parse(Node node) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(node, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedExecutorServiceType parse(Node node, XmlOptions options) throws XmlException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(node, ManagedExecutorServiceType.type, options);
      }

      /** @deprecated */
      public static ManagedExecutorServiceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xis, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ManagedExecutorServiceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ManagedExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xis, ManagedExecutorServiceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedExecutorServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedExecutorServiceType.type, options);
      }

      private Factory() {
      }
   }
}
