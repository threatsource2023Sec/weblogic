package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LoadBalancingParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoadBalancingParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("loadbalancingparamstype37c7type");

   boolean getLoadBalancingEnabled();

   XmlBoolean xgetLoadBalancingEnabled();

   boolean isSetLoadBalancingEnabled();

   void setLoadBalancingEnabled(boolean var1);

   void xsetLoadBalancingEnabled(XmlBoolean var1);

   void unsetLoadBalancingEnabled();

   boolean getServerAffinityEnabled();

   XmlBoolean xgetServerAffinityEnabled();

   boolean isSetServerAffinityEnabled();

   void setServerAffinityEnabled(boolean var1);

   void xsetServerAffinityEnabled(XmlBoolean var1);

   void unsetServerAffinityEnabled();

   ProducerLoadBalancingPolicyType.Enum getProducerLoadBalancingPolicy();

   ProducerLoadBalancingPolicyType xgetProducerLoadBalancingPolicy();

   boolean isSetProducerLoadBalancingPolicy();

   void setProducerLoadBalancingPolicy(ProducerLoadBalancingPolicyType.Enum var1);

   void xsetProducerLoadBalancingPolicy(ProducerLoadBalancingPolicyType var1);

   void unsetProducerLoadBalancingPolicy();

   public static final class Factory {
      public static LoadBalancingParamsType newInstance() {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().newInstance(LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType newInstance(XmlOptions options) {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().newInstance(LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(String xmlAsString) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(File file) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(file, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(file, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(URL u) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(u, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(u, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(InputStream is) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(is, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(is, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(Reader r) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(r, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(r, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(XMLStreamReader sr) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(sr, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(sr, LoadBalancingParamsType.type, options);
      }

      public static LoadBalancingParamsType parse(Node node) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(node, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      public static LoadBalancingParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(node, LoadBalancingParamsType.type, options);
      }

      /** @deprecated */
      public static LoadBalancingParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(xis, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoadBalancingParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoadBalancingParamsType)XmlBeans.getContextTypeLoader().parse(xis, LoadBalancingParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadBalancingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadBalancingParamsType.type, options);
      }

      private Factory() {
      }
   }
}
