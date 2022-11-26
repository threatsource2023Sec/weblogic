package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WorkManagerShutdownTriggerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerShutdownTriggerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("workmanagershutdowntriggertype8483type");

   XsdIntegerType getMaxStuckThreadTime();

   boolean isSetMaxStuckThreadTime();

   void setMaxStuckThreadTime(XsdIntegerType var1);

   XsdIntegerType addNewMaxStuckThreadTime();

   void unsetMaxStuckThreadTime();

   XsdIntegerType getStuckThreadCount();

   void setStuckThreadCount(XsdIntegerType var1);

   XsdIntegerType addNewStuckThreadCount();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WorkManagerShutdownTriggerType newInstance() {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType newInstance(XmlOptions options) {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(String xmlAsString) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(File file) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(URL u) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(InputStream is) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(Reader r) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(XMLStreamReader sr) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerShutdownTriggerType.type, options);
      }

      public static WorkManagerShutdownTriggerType parse(Node node) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      public static WorkManagerShutdownTriggerType parse(Node node, XmlOptions options) throws XmlException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerShutdownTriggerType.type, options);
      }

      /** @deprecated */
      public static WorkManagerShutdownTriggerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WorkManagerShutdownTriggerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WorkManagerShutdownTriggerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerShutdownTriggerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerShutdownTriggerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerShutdownTriggerType.type, options);
      }

      private Factory() {
      }
   }
}
