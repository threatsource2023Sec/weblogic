package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ManagedScheduledExecutorServiceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ManagedScheduledExecutorServiceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("managedscheduledexecutorservicetype29a4type");

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
      public static ManagedScheduledExecutorServiceType newInstance() {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().newInstance(ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType newInstance(XmlOptions options) {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().newInstance(ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(String xmlAsString) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(File file) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(file, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(file, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(URL u) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(u, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(u, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(InputStream is) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(is, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(is, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(Reader r) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(r, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(r, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(XMLStreamReader sr) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(sr, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(sr, ManagedScheduledExecutorServiceType.type, options);
      }

      public static ManagedScheduledExecutorServiceType parse(Node node) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(node, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      public static ManagedScheduledExecutorServiceType parse(Node node, XmlOptions options) throws XmlException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(node, ManagedScheduledExecutorServiceType.type, options);
      }

      /** @deprecated */
      public static ManagedScheduledExecutorServiceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xis, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ManagedScheduledExecutorServiceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ManagedScheduledExecutorServiceType)XmlBeans.getContextTypeLoader().parse(xis, ManagedScheduledExecutorServiceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedScheduledExecutorServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedScheduledExecutorServiceType.type, options);
      }

      private Factory() {
      }
   }
}
