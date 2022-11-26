package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SoapjmsServiceEndpointAddressType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SoapjmsServiceEndpointAddressType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("soapjmsserviceendpointaddresstype9d39type");

   String getLookupVariant();

   boolean isNilLookupVariant();

   boolean isSetLookupVariant();

   void setLookupVariant(String var1);

   String addNewLookupVariant();

   void setNilLookupVariant();

   void unsetLookupVariant();

   String getDestinationName();

   boolean isNilDestinationName();

   void setDestinationName(String var1);

   String addNewDestinationName();

   void setNilDestinationName();

   String getDestinationType();

   boolean isNilDestinationType();

   boolean isSetDestinationType();

   void setDestinationType(String var1);

   String addNewDestinationType();

   void setNilDestinationType();

   void unsetDestinationType();

   String getJndiConnectionFactoryName();

   boolean isSetJndiConnectionFactoryName();

   void setJndiConnectionFactoryName(String var1);

   String addNewJndiConnectionFactoryName();

   void unsetJndiConnectionFactoryName();

   String getJndiInitialContextFactory();

   boolean isSetJndiInitialContextFactory();

   void setJndiInitialContextFactory(String var1);

   String addNewJndiInitialContextFactory();

   void unsetJndiInitialContextFactory();

   String getJndiUrl();

   boolean isNilJndiUrl();

   boolean isSetJndiUrl();

   void setJndiUrl(String var1);

   String addNewJndiUrl();

   void setNilJndiUrl();

   void unsetJndiUrl();

   String getJndiContextParameter();

   boolean isNilJndiContextParameter();

   boolean isSetJndiContextParameter();

   void setJndiContextParameter(String var1);

   String addNewJndiContextParameter();

   void setNilJndiContextParameter();

   void unsetJndiContextParameter();

   long getTimeToLive();

   XmlLong xgetTimeToLive();

   boolean isSetTimeToLive();

   void setTimeToLive(long var1);

   void xsetTimeToLive(XmlLong var1);

   void unsetTimeToLive();

   int getPriority();

   XmlInt xgetPriority();

   boolean isSetPriority();

   void setPriority(int var1);

   void xsetPriority(XmlInt var1);

   void unsetPriority();

   java.lang.String getDeliveryMode();

   XmlString xgetDeliveryMode();

   boolean isSetDeliveryMode();

   void setDeliveryMode(java.lang.String var1);

   void xsetDeliveryMode(XmlString var1);

   void unsetDeliveryMode();

   String getReplyToName();

   boolean isNilReplyToName();

   boolean isSetReplyToName();

   void setReplyToName(String var1);

   String addNewReplyToName();

   void setNilReplyToName();

   void unsetReplyToName();

   String getTargetService();

   boolean isNilTargetService();

   boolean isSetTargetService();

   void setTargetService(String var1);

   String addNewTargetService();

   void setNilTargetService();

   void unsetTargetService();

   String getBindingVersion();

   boolean isSetBindingVersion();

   void setBindingVersion(String var1);

   String addNewBindingVersion();

   void unsetBindingVersion();

   String getMessageType();

   boolean isSetMessageType();

   void setMessageType(String var1);

   String addNewMessageType();

   void unsetMessageType();

   boolean getEnableHttpWsdlAccess();

   XmlBoolean xgetEnableHttpWsdlAccess();

   boolean isSetEnableHttpWsdlAccess();

   void setEnableHttpWsdlAccess(boolean var1);

   void xsetEnableHttpWsdlAccess(XmlBoolean var1);

   void unsetEnableHttpWsdlAccess();

   String getRunAsPrincipal();

   boolean isNilRunAsPrincipal();

   boolean isSetRunAsPrincipal();

   void setRunAsPrincipal(String var1);

   String addNewRunAsPrincipal();

   void setNilRunAsPrincipal();

   void unsetRunAsPrincipal();

   String getRunAsRole();

   boolean isNilRunAsRole();

   boolean isSetRunAsRole();

   void setRunAsRole(String var1);

   String addNewRunAsRole();

   void setNilRunAsRole();

   void unsetRunAsRole();

   boolean getMdbPerDestination();

   XmlBoolean xgetMdbPerDestination();

   boolean isSetMdbPerDestination();

   void setMdbPerDestination(boolean var1);

   void xsetMdbPerDestination(XmlBoolean var1);

   void unsetMdbPerDestination();

   String getActivationConfig();

   boolean isSetActivationConfig();

   void setActivationConfig(String var1);

   String addNewActivationConfig();

   void unsetActivationConfig();

   String getJmsMessageHeader();

   boolean isSetJmsMessageHeader();

   void setJmsMessageHeader(String var1);

   String addNewJmsMessageHeader();

   void unsetJmsMessageHeader();

   String getJmsMessageProperty();

   boolean isSetJmsMessageProperty();

   void setJmsMessageProperty(String var1);

   String addNewJmsMessageProperty();

   void unsetJmsMessageProperty();

   public static final class Factory {
      public static SoapjmsServiceEndpointAddressType newInstance() {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().newInstance(SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType newInstance(XmlOptions options) {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().newInstance(SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(java.lang.String xmlAsString) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(File file) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(file, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(file, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(URL u) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(u, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(u, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(InputStream is) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(is, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(is, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(Reader r) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(r, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(r, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(XMLStreamReader sr) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(sr, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(sr, SoapjmsServiceEndpointAddressType.type, options);
      }

      public static SoapjmsServiceEndpointAddressType parse(Node node) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(node, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      public static SoapjmsServiceEndpointAddressType parse(Node node, XmlOptions options) throws XmlException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(node, SoapjmsServiceEndpointAddressType.type, options);
      }

      /** @deprecated */
      public static SoapjmsServiceEndpointAddressType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(xis, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SoapjmsServiceEndpointAddressType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SoapjmsServiceEndpointAddressType)XmlBeans.getContextTypeLoader().parse(xis, SoapjmsServiceEndpointAddressType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SoapjmsServiceEndpointAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SoapjmsServiceEndpointAddressType.type, options);
      }

      private Factory() {
      }
   }
}
