package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface SafImportedDestinationsType extends TargetableType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafImportedDestinationsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safimporteddestinationstypee16ftype");

   SafQueueType[] getSafQueueArray();

   SafQueueType getSafQueueArray(int var1);

   int sizeOfSafQueueArray();

   void setSafQueueArray(SafQueueType[] var1);

   void setSafQueueArray(int var1, SafQueueType var2);

   SafQueueType insertNewSafQueue(int var1);

   SafQueueType addNewSafQueue();

   void removeSafQueue(int var1);

   SafTopicType[] getSafTopicArray();

   SafTopicType getSafTopicArray(int var1);

   int sizeOfSafTopicArray();

   void setSafTopicArray(SafTopicType[] var1);

   void setSafTopicArray(int var1, SafTopicType var2);

   SafTopicType insertNewSafTopic(int var1);

   SafTopicType addNewSafTopic();

   void removeSafTopic(int var1);

   String getJndiPrefix();

   XmlString xgetJndiPrefix();

   boolean isNilJndiPrefix();

   boolean isSetJndiPrefix();

   void setJndiPrefix(String var1);

   void xsetJndiPrefix(XmlString var1);

   void setNilJndiPrefix();

   void unsetJndiPrefix();

   String getSafRemoteContext();

   XmlString xgetSafRemoteContext();

   boolean isNilSafRemoteContext();

   boolean isSetSafRemoteContext();

   void setSafRemoteContext(String var1);

   void xsetSafRemoteContext(XmlString var1);

   void setNilSafRemoteContext();

   void unsetSafRemoteContext();

   String getSafErrorHandling();

   XmlString xgetSafErrorHandling();

   boolean isNilSafErrorHandling();

   boolean isSetSafErrorHandling();

   void setSafErrorHandling(String var1);

   void xsetSafErrorHandling(XmlString var1);

   void setNilSafErrorHandling();

   void unsetSafErrorHandling();

   long getTimeToLiveDefault();

   XmlLong xgetTimeToLiveDefault();

   boolean isSetTimeToLiveDefault();

   void setTimeToLiveDefault(long var1);

   void xsetTimeToLiveDefault(XmlLong var1);

   void unsetTimeToLiveDefault();

   boolean getUseSafTimeToLiveDefault();

   XmlBoolean xgetUseSafTimeToLiveDefault();

   boolean isSetUseSafTimeToLiveDefault();

   void setUseSafTimeToLiveDefault(boolean var1);

   void xsetUseSafTimeToLiveDefault(XmlBoolean var1);

   void unsetUseSafTimeToLiveDefault();

   UnitOfOrderRoutingType.Enum getUnitOfOrderRouting();

   UnitOfOrderRoutingType xgetUnitOfOrderRouting();

   boolean isSetUnitOfOrderRouting();

   void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum var1);

   void xsetUnitOfOrderRouting(UnitOfOrderRoutingType var1);

   void unsetUnitOfOrderRouting();

   MessageLoggingParamsType getMessageLoggingParams();

   boolean isSetMessageLoggingParams();

   void setMessageLoggingParams(MessageLoggingParamsType var1);

   MessageLoggingParamsType addNewMessageLoggingParams();

   void unsetMessageLoggingParams();

   ExactlyOnceLoadBalancingPolicyType.Enum getExactlyOnceLoadBalancingPolicy();

   ExactlyOnceLoadBalancingPolicyType xgetExactlyOnceLoadBalancingPolicy();

   boolean isSetExactlyOnceLoadBalancingPolicy();

   void setExactlyOnceLoadBalancingPolicy(ExactlyOnceLoadBalancingPolicyType.Enum var1);

   void xsetExactlyOnceLoadBalancingPolicy(ExactlyOnceLoadBalancingPolicyType var1);

   void unsetExactlyOnceLoadBalancingPolicy();

   public static final class Factory {
      public static SafImportedDestinationsType newInstance() {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().newInstance(SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType newInstance(XmlOptions options) {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().newInstance(SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(String xmlAsString) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(File file) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(file, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(file, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(URL u) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(u, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(u, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(InputStream is) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(is, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(is, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(Reader r) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(r, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(r, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(XMLStreamReader sr) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(sr, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(sr, SafImportedDestinationsType.type, options);
      }

      public static SafImportedDestinationsType parse(Node node) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(node, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      public static SafImportedDestinationsType parse(Node node, XmlOptions options) throws XmlException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(node, SafImportedDestinationsType.type, options);
      }

      /** @deprecated */
      public static SafImportedDestinationsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(xis, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafImportedDestinationsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafImportedDestinationsType)XmlBeans.getContextTypeLoader().parse(xis, SafImportedDestinationsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafImportedDestinationsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafImportedDestinationsType.type, options);
      }

      private Factory() {
      }
   }
}
