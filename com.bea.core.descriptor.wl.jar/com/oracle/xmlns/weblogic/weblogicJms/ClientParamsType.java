package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
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

public interface ClientParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("clientparamstype8ee0type");

   String getClientId();

   XmlString xgetClientId();

   boolean isNilClientId();

   boolean isSetClientId();

   void setClientId(String var1);

   void xsetClientId(XmlString var1);

   void setNilClientId();

   void unsetClientId();

   ClientIdPolicyType.Enum getClientIdPolicy();

   ClientIdPolicyType xgetClientIdPolicy();

   boolean isSetClientIdPolicy();

   void setClientIdPolicy(ClientIdPolicyType.Enum var1);

   void xsetClientIdPolicy(ClientIdPolicyType var1);

   void unsetClientIdPolicy();

   SubscriptionSharingPolicyType.Enum getSubscriptionSharingPolicy();

   SubscriptionSharingPolicyType xgetSubscriptionSharingPolicy();

   boolean isSetSubscriptionSharingPolicy();

   void setSubscriptionSharingPolicy(SubscriptionSharingPolicyType.Enum var1);

   void xsetSubscriptionSharingPolicy(SubscriptionSharingPolicyType var1);

   void unsetSubscriptionSharingPolicy();

   AcknowledgePolicyType.Enum getAcknowledgePolicy();

   AcknowledgePolicyType xgetAcknowledgePolicy();

   boolean isSetAcknowledgePolicy();

   void setAcknowledgePolicy(AcknowledgePolicyType.Enum var1);

   void xsetAcknowledgePolicy(AcknowledgePolicyType var1);

   void unsetAcknowledgePolicy();

   boolean getAllowCloseInOnMessage();

   XmlBoolean xgetAllowCloseInOnMessage();

   boolean isSetAllowCloseInOnMessage();

   void setAllowCloseInOnMessage(boolean var1);

   void xsetAllowCloseInOnMessage(XmlBoolean var1);

   void unsetAllowCloseInOnMessage();

   int getMessagesMaximum();

   XmlInt xgetMessagesMaximum();

   boolean isSetMessagesMaximum();

   void setMessagesMaximum(int var1);

   void xsetMessagesMaximum(XmlInt var1);

   void unsetMessagesMaximum();

   OverrunPolicyType.Enum getMulticastOverrunPolicy();

   OverrunPolicyType xgetMulticastOverrunPolicy();

   boolean isSetMulticastOverrunPolicy();

   void setMulticastOverrunPolicy(OverrunPolicyType.Enum var1);

   void xsetMulticastOverrunPolicy(OverrunPolicyType var1);

   void unsetMulticastOverrunPolicy();

   SynchronousPrefetchModeType.Enum getSynchronousPrefetchMode();

   SynchronousPrefetchModeType xgetSynchronousPrefetchMode();

   boolean isSetSynchronousPrefetchMode();

   void setSynchronousPrefetchMode(SynchronousPrefetchModeType.Enum var1);

   void xsetSynchronousPrefetchMode(SynchronousPrefetchModeType var1);

   void unsetSynchronousPrefetchMode();

   ReconnectPolicyType.Enum getReconnectPolicy();

   ReconnectPolicyType xgetReconnectPolicy();

   boolean isSetReconnectPolicy();

   void setReconnectPolicy(ReconnectPolicyType.Enum var1);

   void xsetReconnectPolicy(ReconnectPolicyType var1);

   void unsetReconnectPolicy();

   long getReconnectBlockingMillis();

   XmlLong xgetReconnectBlockingMillis();

   boolean isSetReconnectBlockingMillis();

   void setReconnectBlockingMillis(long var1);

   void xsetReconnectBlockingMillis(XmlLong var1);

   void unsetReconnectBlockingMillis();

   long getTotalReconnectPeriodMillis();

   XmlLong xgetTotalReconnectPeriodMillis();

   boolean isSetTotalReconnectPeriodMillis();

   void setTotalReconnectPeriodMillis(long var1);

   void xsetTotalReconnectPeriodMillis(XmlLong var1);

   void unsetTotalReconnectPeriodMillis();

   public static final class Factory {
      public static ClientParamsType newInstance() {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().newInstance(ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType newInstance(XmlOptions options) {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().newInstance(ClientParamsType.type, options);
      }

      public static ClientParamsType parse(String xmlAsString) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(File file) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(file, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(file, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(URL u) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(u, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(u, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(InputStream is) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(is, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(is, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(Reader r) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(r, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(r, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(sr, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(sr, ClientParamsType.type, options);
      }

      public static ClientParamsType parse(Node node) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(node, ClientParamsType.type, (XmlOptions)null);
      }

      public static ClientParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(node, ClientParamsType.type, options);
      }

      /** @deprecated */
      public static ClientParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(xis, ClientParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientParamsType)XmlBeans.getContextTypeLoader().parse(xis, ClientParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientParamsType.type, options);
      }

      private Factory() {
      }
   }
}
