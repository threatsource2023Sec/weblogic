package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface JmsConnectionFactoryType extends TargetableType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsConnectionFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jmsconnectionfactorytype597etype");

   String getJndiName();

   XmlString xgetJndiName();

   boolean isNilJndiName();

   boolean isSetJndiName();

   void setJndiName(String var1);

   void xsetJndiName(XmlString var1);

   void setNilJndiName();

   void unsetJndiName();

   String getLocalJndiName();

   XmlString xgetLocalJndiName();

   boolean isNilLocalJndiName();

   boolean isSetLocalJndiName();

   void setLocalJndiName(String var1);

   void xsetLocalJndiName(XmlString var1);

   void setNilLocalJndiName();

   void unsetLocalJndiName();

   DefaultDeliveryParamsType getDefaultDeliveryParams();

   boolean isSetDefaultDeliveryParams();

   void setDefaultDeliveryParams(DefaultDeliveryParamsType var1);

   DefaultDeliveryParamsType addNewDefaultDeliveryParams();

   void unsetDefaultDeliveryParams();

   ClientParamsType getClientParams();

   boolean isSetClientParams();

   void setClientParams(ClientParamsType var1);

   ClientParamsType addNewClientParams();

   void unsetClientParams();

   TransactionParamsType getTransactionParams();

   boolean isSetTransactionParams();

   void setTransactionParams(TransactionParamsType var1);

   TransactionParamsType addNewTransactionParams();

   void unsetTransactionParams();

   FlowControlParamsType getFlowControlParams();

   boolean isSetFlowControlParams();

   void setFlowControlParams(FlowControlParamsType var1);

   FlowControlParamsType addNewFlowControlParams();

   void unsetFlowControlParams();

   LoadBalancingParamsType getLoadBalancingParams();

   boolean isSetLoadBalancingParams();

   void setLoadBalancingParams(LoadBalancingParamsType var1);

   LoadBalancingParamsType addNewLoadBalancingParams();

   void unsetLoadBalancingParams();

   SecurityParamsType getSecurityParams();

   boolean isSetSecurityParams();

   void setSecurityParams(SecurityParamsType var1);

   SecurityParamsType addNewSecurityParams();

   void unsetSecurityParams();

   public static final class Factory {
      public static JmsConnectionFactoryType newInstance() {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType newInstance(XmlOptions options) {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().newInstance(JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(String xmlAsString) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(File file) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(file, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(URL u) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(u, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(InputStream is) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(is, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(Reader r) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(r, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(sr, JmsConnectionFactoryType.type, options);
      }

      public static JmsConnectionFactoryType parse(Node node) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      public static JmsConnectionFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(node, JmsConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static JmsConnectionFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsConnectionFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsConnectionFactoryType)XmlBeans.getContextTypeLoader().parse(xis, JmsConnectionFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsConnectionFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsConnectionFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
