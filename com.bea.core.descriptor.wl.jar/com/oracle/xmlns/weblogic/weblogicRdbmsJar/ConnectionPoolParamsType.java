package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectionPoolParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionPoolParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("connectionpoolparamstype0fd7type");

   XsdNonNegativeIntegerType getInitialCapacity();

   boolean isSetInitialCapacity();

   void setInitialCapacity(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewInitialCapacity();

   void unsetInitialCapacity();

   XsdNonNegativeIntegerType getMaxCapacity();

   boolean isSetMaxCapacity();

   void setMaxCapacity(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxCapacity();

   void unsetMaxCapacity();

   XsdPositiveIntegerType getCapacityIncrement();

   boolean isSetCapacityIncrement();

   void setCapacityIncrement(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewCapacityIncrement();

   void unsetCapacityIncrement();

   TrueFalseType getShrinkingEnabled();

   boolean isSetShrinkingEnabled();

   void setShrinkingEnabled(TrueFalseType var1);

   TrueFalseType addNewShrinkingEnabled();

   void unsetShrinkingEnabled();

   XsdNonNegativeIntegerType getShrinkFrequencySeconds();

   boolean isSetShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewShrinkFrequencySeconds();

   void unsetShrinkFrequencySeconds();

   XsdNonNegativeIntegerType getHighestNumWaiters();

   boolean isSetHighestNumWaiters();

   void setHighestNumWaiters(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewHighestNumWaiters();

   void unsetHighestNumWaiters();

   XsdNonNegativeIntegerType getHighestNumUnavailable();

   boolean isSetHighestNumUnavailable();

   void setHighestNumUnavailable(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewHighestNumUnavailable();

   void unsetHighestNumUnavailable();

   XsdIntegerType getConnectionCreationRetryFrequencySeconds();

   boolean isSetConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(XsdIntegerType var1);

   XsdIntegerType addNewConnectionCreationRetryFrequencySeconds();

   void unsetConnectionCreationRetryFrequencySeconds();

   XsdIntegerType getConnectionReserveTimeoutSeconds();

   boolean isSetConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(XsdIntegerType var1);

   XsdIntegerType addNewConnectionReserveTimeoutSeconds();

   void unsetConnectionReserveTimeoutSeconds();

   XsdNonNegativeIntegerType getTestFrequencySeconds();

   boolean isSetTestFrequencySeconds();

   void setTestFrequencySeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewTestFrequencySeconds();

   void unsetTestFrequencySeconds();

   TrueFalseType getTestConnectionsOnCreate();

   boolean isSetTestConnectionsOnCreate();

   void setTestConnectionsOnCreate(TrueFalseType var1);

   TrueFalseType addNewTestConnectionsOnCreate();

   void unsetTestConnectionsOnCreate();

   TrueFalseType getTestConnectionsOnRelease();

   boolean isSetTestConnectionsOnRelease();

   void setTestConnectionsOnRelease(TrueFalseType var1);

   TrueFalseType addNewTestConnectionsOnRelease();

   void unsetTestConnectionsOnRelease();

   TrueFalseType getTestConnectionsOnReserve();

   boolean isSetTestConnectionsOnReserve();

   void setTestConnectionsOnReserve(TrueFalseType var1);

   TrueFalseType addNewTestConnectionsOnReserve();

   void unsetTestConnectionsOnReserve();

   XsdNonNegativeIntegerType getProfileHarvestFrequencySeconds();

   boolean isSetProfileHarvestFrequencySeconds();

   void setProfileHarvestFrequencySeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewProfileHarvestFrequencySeconds();

   void unsetProfileHarvestFrequencySeconds();

   TrueFalseType getIgnoreInUseConnectionsEnabled();

   boolean isSetIgnoreInUseConnectionsEnabled();

   void setIgnoreInUseConnectionsEnabled(TrueFalseType var1);

   TrueFalseType addNewIgnoreInUseConnectionsEnabled();

   void unsetIgnoreInUseConnectionsEnabled();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectionPoolParamsType newInstance() {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType newInstance(XmlOptions options) {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(String xmlAsString) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(File file) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(URL u) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(Reader r) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionPoolParamsType.type, options);
      }

      public static ConnectionPoolParamsType parse(Node node) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      public static ConnectionPoolParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionPoolParamsType.type, options);
      }

      /** @deprecated */
      public static ConnectionPoolParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionPoolParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionPoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionPoolParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionPoolParamsType.type, options);
      }

      private Factory() {
      }
   }
}
