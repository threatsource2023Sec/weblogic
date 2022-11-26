package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CoherenceClusterParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceClusterParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceclusterparamstyped971type");

   XsdNonNegativeIntegerType getClusterListenPort();

   boolean isSetClusterListenPort();

   void setClusterListenPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewClusterListenPort();

   void unsetClusterListenPort();

   String getUnicastListenAddress();

   XmlString xgetUnicastListenAddress();

   boolean isNilUnicastListenAddress();

   boolean isSetUnicastListenAddress();

   void setUnicastListenAddress(String var1);

   void xsetUnicastListenAddress(XmlString var1);

   void setNilUnicastListenAddress();

   void unsetUnicastListenAddress();

   XsdNonNegativeIntegerType getUnicastListenPort();

   boolean isSetUnicastListenPort();

   void setUnicastListenPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewUnicastListenPort();

   void unsetUnicastListenPort();

   TrueFalseType getUnicastPortAutoAdjust();

   boolean isSetUnicastPortAutoAdjust();

   void setUnicastPortAutoAdjust(TrueFalseType var1);

   TrueFalseType addNewUnicastPortAutoAdjust();

   void unsetUnicastPortAutoAdjust();

   String getMulticastListenAddress();

   XmlString xgetMulticastListenAddress();

   boolean isNilMulticastListenAddress();

   boolean isSetMulticastListenAddress();

   void setMulticastListenAddress(String var1);

   void xsetMulticastListenAddress(XmlString var1);

   void setNilMulticastListenAddress();

   void unsetMulticastListenAddress();

   XsdNonNegativeIntegerType getMulticastListenPort();

   boolean isSetMulticastListenPort();

   void setMulticastListenPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMulticastListenPort();

   void unsetMulticastListenPort();

   XsdNonNegativeIntegerType getTimeToLive();

   boolean isSetTimeToLive();

   void setTimeToLive(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewTimeToLive();

   void unsetTimeToLive();

   CoherenceClusterWellKnownAddressesType getCoherenceClusterWellKnownAddresses();

   boolean isNilCoherenceClusterWellKnownAddresses();

   boolean isSetCoherenceClusterWellKnownAddresses();

   void setCoherenceClusterWellKnownAddresses(CoherenceClusterWellKnownAddressesType var1);

   CoherenceClusterWellKnownAddressesType addNewCoherenceClusterWellKnownAddresses();

   void setNilCoherenceClusterWellKnownAddresses();

   void unsetCoherenceClusterWellKnownAddresses();

   ClusteringModeType.Enum getClusteringMode();

   ClusteringModeType xgetClusteringMode();

   boolean isNilClusteringMode();

   boolean isSetClusteringMode();

   void setClusteringMode(ClusteringModeType.Enum var1);

   void xsetClusteringMode(ClusteringModeType var1);

   void setNilClusteringMode();

   void unsetClusteringMode();

   TransportType.Enum getTransport();

   TransportType xgetTransport();

   boolean isNilTransport();

   boolean isSetTransport();

   void setTransport(TransportType.Enum var1);

   void xsetTransport(TransportType var1);

   void setNilTransport();

   void unsetTransport();

   TrueFalseType getSecurityFrameworkEnabled();

   boolean isSetSecurityFrameworkEnabled();

   void setSecurityFrameworkEnabled(TrueFalseType var1);

   TrueFalseType addNewSecurityFrameworkEnabled();

   void unsetSecurityFrameworkEnabled();

   IdentityAsserterType getCoherenceIdentityAsserter();

   boolean isNilCoherenceIdentityAsserter();

   boolean isSetCoherenceIdentityAsserter();

   void setCoherenceIdentityAsserter(IdentityAsserterType var1);

   IdentityAsserterType addNewCoherenceIdentityAsserter();

   void setNilCoherenceIdentityAsserter();

   void unsetCoherenceIdentityAsserter();

   KeystoreParamsType getCoherenceKeystoreParams();

   boolean isNilCoherenceKeystoreParams();

   boolean isSetCoherenceKeystoreParams();

   void setCoherenceKeystoreParams(KeystoreParamsType var1);

   KeystoreParamsType addNewCoherenceKeystoreParams();

   void setNilCoherenceKeystoreParams();

   void unsetCoherenceKeystoreParams();

   CoherenceCacheType[] getCoherenceCacheArray();

   CoherenceCacheType getCoherenceCacheArray(int var1);

   int sizeOfCoherenceCacheArray();

   void setCoherenceCacheArray(CoherenceCacheType[] var1);

   void setCoherenceCacheArray(int var1, CoherenceCacheType var2);

   CoherenceCacheType insertNewCoherenceCache(int var1);

   CoherenceCacheType addNewCoherenceCache();

   void removeCoherenceCache(int var1);

   CoherenceServiceType[] getCoherenceServiceArray();

   CoherenceServiceType getCoherenceServiceArray(int var1);

   int sizeOfCoherenceServiceArray();

   void setCoherenceServiceArray(CoherenceServiceType[] var1);

   void setCoherenceServiceArray(int var1, CoherenceServiceType var2);

   CoherenceServiceType insertNewCoherenceService(int var1);

   CoherenceServiceType addNewCoherenceService();

   void removeCoherenceService(int var1);

   public static final class Factory {
      public static CoherenceClusterParamsType newInstance() {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType newInstance(XmlOptions options) {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(String xmlAsString) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(File file) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(URL u) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(Reader r) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterParamsType.type, options);
      }

      public static CoherenceClusterParamsType parse(Node node) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      public static CoherenceClusterParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterParamsType.type, options);
      }

      /** @deprecated */
      public static CoherenceClusterParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceClusterParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceClusterParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterParamsType.type, options);
      }

      private Factory() {
      }
   }
}
