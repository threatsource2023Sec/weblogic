package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WeblogicCoherenceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicCoherenceType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("weblogiccoherencetypef8f0type");

   String getName();

   XmlString xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   void unsetName();

   String getCustomClusterConfigurationFileName();

   XmlString xgetCustomClusterConfigurationFileName();

   boolean isNilCustomClusterConfigurationFileName();

   boolean isSetCustomClusterConfigurationFileName();

   void setCustomClusterConfigurationFileName(String var1);

   void xsetCustomClusterConfigurationFileName(XmlString var1);

   void setNilCustomClusterConfigurationFileName();

   void unsetCustomClusterConfigurationFileName();

   long getCustomClusterConfigurationFileLastUpdatedTimestamp();

   XmlLong xgetCustomClusterConfigurationFileLastUpdatedTimestamp();

   boolean isNilCustomClusterConfigurationFileLastUpdatedTimestamp();

   boolean isSetCustomClusterConfigurationFileLastUpdatedTimestamp();

   void setCustomClusterConfigurationFileLastUpdatedTimestamp(long var1);

   void xsetCustomClusterConfigurationFileLastUpdatedTimestamp(XmlLong var1);

   void setNilCustomClusterConfigurationFileLastUpdatedTimestamp();

   void unsetCustomClusterConfigurationFileLastUpdatedTimestamp();

   CoherenceClusterParamsType getCoherenceClusterParams();

   boolean isNilCoherenceClusterParams();

   boolean isSetCoherenceClusterParams();

   void setCoherenceClusterParams(CoherenceClusterParamsType var1);

   CoherenceClusterParamsType addNewCoherenceClusterParams();

   void setNilCoherenceClusterParams();

   void unsetCoherenceClusterParams();

   CoherenceLoggingParamsType getCoherenceLoggingParams();

   boolean isNilCoherenceLoggingParams();

   boolean isSetCoherenceLoggingParams();

   void setCoherenceLoggingParams(CoherenceLoggingParamsType var1);

   CoherenceLoggingParamsType addNewCoherenceLoggingParams();

   void setNilCoherenceLoggingParams();

   void unsetCoherenceLoggingParams();

   CoherenceAddressProvidersType getCoherenceAddressProviders();

   boolean isNilCoherenceAddressProviders();

   boolean isSetCoherenceAddressProviders();

   void setCoherenceAddressProviders(CoherenceAddressProvidersType var1);

   CoherenceAddressProvidersType addNewCoherenceAddressProviders();

   void setNilCoherenceAddressProviders();

   void unsetCoherenceAddressProviders();

   CoherencePersistenceParamsType getCoherencePersistenceParams();

   boolean isNilCoherencePersistenceParams();

   boolean isSetCoherencePersistenceParams();

   void setCoherencePersistenceParams(CoherencePersistenceParamsType var1);

   CoherencePersistenceParamsType addNewCoherencePersistenceParams();

   void setNilCoherencePersistenceParams();

   void unsetCoherencePersistenceParams();

   CoherenceFederationParamsType getCoherenceFederationParams();

   boolean isNilCoherenceFederationParams();

   boolean isSetCoherenceFederationParams();

   void setCoherenceFederationParams(CoherenceFederationParamsType var1);

   CoherenceFederationParamsType addNewCoherenceFederationParams();

   void setNilCoherenceFederationParams();

   void unsetCoherenceFederationParams();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicCoherenceType newInstance() {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().newInstance(WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType newInstance(XmlOptions options) {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().newInstance(WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(String xmlAsString) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(File file) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(file, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(file, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(URL u) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(u, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(u, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(is, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(is, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(Reader r) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(r, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(r, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCoherenceType.type, options);
      }

      public static WeblogicCoherenceType parse(Node node) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(node, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(node, WeblogicCoherenceType.type, options);
      }

      /** @deprecated */
      public static WeblogicCoherenceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicCoherenceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicCoherenceType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCoherenceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCoherenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCoherenceType.type, options);
      }

      private Factory() {
      }
   }
}
