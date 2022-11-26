package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WorkManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("workmanagertypec953type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   ResponseTimeRequestClassType getResponseTimeRequestClass();

   boolean isSetResponseTimeRequestClass();

   void setResponseTimeRequestClass(ResponseTimeRequestClassType var1);

   ResponseTimeRequestClassType addNewResponseTimeRequestClass();

   void unsetResponseTimeRequestClass();

   FairShareRequestClassType getFairShareRequestClass();

   boolean isSetFairShareRequestClass();

   void setFairShareRequestClass(FairShareRequestClassType var1);

   FairShareRequestClassType addNewFairShareRequestClass();

   void unsetFairShareRequestClass();

   ContextRequestClassType getContextRequestClass();

   boolean isSetContextRequestClass();

   void setContextRequestClass(ContextRequestClassType var1);

   ContextRequestClassType addNewContextRequestClass();

   void unsetContextRequestClass();

   String getRequestClassName();

   XmlString xgetRequestClassName();

   boolean isSetRequestClassName();

   void setRequestClassName(String var1);

   void xsetRequestClassName(XmlString var1);

   void unsetRequestClassName();

   MinThreadsConstraintType getMinThreadsConstraint();

   boolean isSetMinThreadsConstraint();

   void setMinThreadsConstraint(MinThreadsConstraintType var1);

   MinThreadsConstraintType addNewMinThreadsConstraint();

   void unsetMinThreadsConstraint();

   String getMinThreadsConstraintName();

   XmlString xgetMinThreadsConstraintName();

   boolean isSetMinThreadsConstraintName();

   void setMinThreadsConstraintName(String var1);

   void xsetMinThreadsConstraintName(XmlString var1);

   void unsetMinThreadsConstraintName();

   MaxThreadsConstraintType getMaxThreadsConstraint();

   boolean isSetMaxThreadsConstraint();

   void setMaxThreadsConstraint(MaxThreadsConstraintType var1);

   MaxThreadsConstraintType addNewMaxThreadsConstraint();

   void unsetMaxThreadsConstraint();

   String getMaxThreadsConstraintName();

   XmlString xgetMaxThreadsConstraintName();

   boolean isSetMaxThreadsConstraintName();

   void setMaxThreadsConstraintName(String var1);

   void xsetMaxThreadsConstraintName(XmlString var1);

   void unsetMaxThreadsConstraintName();

   CapacityType getCapacity();

   boolean isSetCapacity();

   void setCapacity(CapacityType var1);

   CapacityType addNewCapacity();

   void unsetCapacity();

   String getCapacityName();

   XmlString xgetCapacityName();

   boolean isSetCapacityName();

   void setCapacityName(String var1);

   void xsetCapacityName(XmlString var1);

   void unsetCapacityName();

   WorkManagerShutdownTriggerType getWorkManagerShutdownTrigger();

   boolean isSetWorkManagerShutdownTrigger();

   void setWorkManagerShutdownTrigger(WorkManagerShutdownTriggerType var1);

   WorkManagerShutdownTriggerType addNewWorkManagerShutdownTrigger();

   void unsetWorkManagerShutdownTrigger();

   boolean getIgnoreStuckThreads();

   XmlBoolean xgetIgnoreStuckThreads();

   boolean isSetIgnoreStuckThreads();

   void setIgnoreStuckThreads(boolean var1);

   void xsetIgnoreStuckThreads(XmlBoolean var1);

   void unsetIgnoreStuckThreads();

   public static final class Factory {
      public static WorkManagerType newInstance() {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType newInstance(XmlOptions options) {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerType.type, options);
      }

      public static WorkManagerType parse(String xmlAsString) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(File file) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(URL u) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(InputStream is) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(Reader r) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(XMLStreamReader sr) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(Node node) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerType.type, options);
      }

      /** @deprecated */
      public static WorkManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WorkManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerType.type, options);
      }

      private Factory() {
      }
   }
}
