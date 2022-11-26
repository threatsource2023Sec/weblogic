package com.oracle.xmlns.weblogic.weblogicApplicationClient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdBooleanType;
import com.sun.java.xml.ns.javaee.XsdStringType;
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
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("workmanagertypeff2ctype");

   DispatchPolicyType getName();

   void setName(DispatchPolicyType var1);

   DispatchPolicyType addNewName();

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

   XsdStringType getRequestClassName();

   boolean isSetRequestClassName();

   void setRequestClassName(XsdStringType var1);

   XsdStringType addNewRequestClassName();

   void unsetRequestClassName();

   MinThreadsConstraintType getMinThreadsConstraint();

   boolean isSetMinThreadsConstraint();

   void setMinThreadsConstraint(MinThreadsConstraintType var1);

   MinThreadsConstraintType addNewMinThreadsConstraint();

   void unsetMinThreadsConstraint();

   XsdStringType getMinThreadsConstraintName();

   boolean isSetMinThreadsConstraintName();

   void setMinThreadsConstraintName(XsdStringType var1);

   XsdStringType addNewMinThreadsConstraintName();

   void unsetMinThreadsConstraintName();

   MaxThreadsConstraintType getMaxThreadsConstraint();

   boolean isSetMaxThreadsConstraint();

   void setMaxThreadsConstraint(MaxThreadsConstraintType var1);

   MaxThreadsConstraintType addNewMaxThreadsConstraint();

   void unsetMaxThreadsConstraint();

   XsdStringType getMaxThreadsConstraintName();

   boolean isSetMaxThreadsConstraintName();

   void setMaxThreadsConstraintName(XsdStringType var1);

   XsdStringType addNewMaxThreadsConstraintName();

   void unsetMaxThreadsConstraintName();

   CapacityType getCapacity();

   boolean isSetCapacity();

   void setCapacity(CapacityType var1);

   CapacityType addNewCapacity();

   void unsetCapacity();

   XsdStringType getCapacityName();

   boolean isSetCapacityName();

   void setCapacityName(XsdStringType var1);

   XsdStringType addNewCapacityName();

   void unsetCapacityName();

   WorkManagerShutdownTriggerType getWorkManagerShutdownTrigger();

   boolean isSetWorkManagerShutdownTrigger();

   void setWorkManagerShutdownTrigger(WorkManagerShutdownTriggerType var1);

   WorkManagerShutdownTriggerType addNewWorkManagerShutdownTrigger();

   void unsetWorkManagerShutdownTrigger();

   XsdBooleanType getIgnoreStuckThreads();

   boolean isSetIgnoreStuckThreads();

   void setIgnoreStuckThreads(XsdBooleanType var1);

   XsdBooleanType addNewIgnoreStuckThreads();

   void unsetIgnoreStuckThreads();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

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
