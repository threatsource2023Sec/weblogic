package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ConnectionPoolParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionPoolParamsType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("connectionpoolparamstypeea7ctype");

   BigInteger getInitialCapacity();

   XmlInteger xgetInitialCapacity();

   void setInitialCapacity(BigInteger var1);

   void xsetInitialCapacity(XmlInteger var1);

   BigInteger getMaxCapacity();

   XmlInteger xgetMaxCapacity();

   void setMaxCapacity(BigInteger var1);

   void xsetMaxCapacity(XmlInteger var1);

   BigInteger getCapacityIncrement();

   XmlInteger xgetCapacityIncrement();

   void setCapacityIncrement(BigInteger var1);

   void xsetCapacityIncrement(XmlInteger var1);

   boolean getShrinkingEnabled();

   XmlBoolean xgetShrinkingEnabled();

   void setShrinkingEnabled(boolean var1);

   void xsetShrinkingEnabled(XmlBoolean var1);

   BigInteger getShrinkFrequencySeconds();

   XmlInteger xgetShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(BigInteger var1);

   void xsetShrinkFrequencySeconds(XmlInteger var1);

   BigInteger getHighestNumWaiters();

   XmlInteger xgetHighestNumWaiters();

   void setHighestNumWaiters(BigInteger var1);

   void xsetHighestNumWaiters(XmlInteger var1);

   BigInteger getHighestNumUnavailable();

   XmlInteger xgetHighestNumUnavailable();

   void setHighestNumUnavailable(BigInteger var1);

   void xsetHighestNumUnavailable(XmlInteger var1);

   BigInteger getConnectionCreationRetryFrequencySeconds();

   XmlInteger xgetConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(BigInteger var1);

   void xsetConnectionCreationRetryFrequencySeconds(XmlInteger var1);

   BigInteger getConnectionReserveTimeoutSeconds();

   XmlInteger xgetConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(BigInteger var1);

   void xsetConnectionReserveTimeoutSeconds(XmlInteger var1);

   BigInteger getTestFrequencySeconds();

   XmlInteger xgetTestFrequencySeconds();

   void setTestFrequencySeconds(BigInteger var1);

   void xsetTestFrequencySeconds(XmlInteger var1);

   boolean getTestConnectionsOnCreate();

   XmlBoolean xgetTestConnectionsOnCreate();

   void setTestConnectionsOnCreate(boolean var1);

   void xsetTestConnectionsOnCreate(XmlBoolean var1);

   boolean getTestConnectionsOnRelease();

   XmlBoolean xgetTestConnectionsOnRelease();

   void setTestConnectionsOnRelease(boolean var1);

   void xsetTestConnectionsOnRelease(XmlBoolean var1);

   boolean getTestConnectionsOnReserve();

   XmlBoolean xgetTestConnectionsOnReserve();

   void setTestConnectionsOnReserve(boolean var1);

   void xsetTestConnectionsOnReserve(XmlBoolean var1);

   BigInteger getProfileHarvestFrequencySeconds();

   XmlInteger xgetProfileHarvestFrequencySeconds();

   void setProfileHarvestFrequencySeconds(BigInteger var1);

   void xsetProfileHarvestFrequencySeconds(XmlInteger var1);

   boolean getIgnoreInUseConnectionsEnabled();

   XmlBoolean xgetIgnoreInUseConnectionsEnabled();

   void setIgnoreInUseConnectionsEnabled(boolean var1);

   void xsetIgnoreInUseConnectionsEnabled(XmlBoolean var1);

   boolean getMatchConnectionsSupported();

   XmlBoolean xgetMatchConnectionsSupported();

   void setMatchConnectionsSupported(boolean var1);

   void xsetMatchConnectionsSupported(XmlBoolean var1);

   boolean getUseFirstAvailable();

   XmlBoolean xgetUseFirstAvailable();

   boolean isSetUseFirstAvailable();

   void setUseFirstAvailable(boolean var1);

   void xsetUseFirstAvailable(XmlBoolean var1);

   void unsetUseFirstAvailable();

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
