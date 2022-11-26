package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WorkManagerShutdownTriggerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerShutdownTriggerType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("workmanagershutdowntriggertype2199type");

   BigInteger getMaxStuckThreadTime();

   XmlInteger xgetMaxStuckThreadTime();

   boolean isSetMaxStuckThreadTime();

   void setMaxStuckThreadTime(BigInteger var1);

   void xsetMaxStuckThreadTime(XmlInteger var1);

   void unsetMaxStuckThreadTime();

   BigInteger getStuckThreadCount();

   XmlInteger xgetStuckThreadCount();

   void setStuckThreadCount(BigInteger var1);

   void xsetStuckThreadCount(XmlInteger var1);

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
