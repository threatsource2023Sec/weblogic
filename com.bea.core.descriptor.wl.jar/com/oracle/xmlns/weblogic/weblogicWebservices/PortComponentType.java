package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNonNegativeInteger;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.AuthConstraintType;
import com.sun.java.xml.ns.j2Ee.LoginConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.TransportGuaranteeType;
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

public interface PortComponentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortComponentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("portcomponenttypea76etype");

   String getPortComponentName();

   void setPortComponentName(String var1);

   String addNewPortComponentName();

   WebserviceAddressType getServiceEndpointAddress();

   boolean isSetServiceEndpointAddress();

   void setServiceEndpointAddress(WebserviceAddressType var1);

   WebserviceAddressType addNewServiceEndpointAddress();

   void unsetServiceEndpointAddress();

   AuthConstraintType getAuthConstraint();

   boolean isSetAuthConstraint();

   void setAuthConstraint(AuthConstraintType var1);

   AuthConstraintType addNewAuthConstraint();

   void unsetAuthConstraint();

   LoginConfigType getLoginConfig();

   boolean isSetLoginConfig();

   void setLoginConfig(LoginConfigType var1);

   LoginConfigType addNewLoginConfig();

   void unsetLoginConfig();

   TransportGuaranteeType getTransportGuarantee();

   boolean isSetTransportGuarantee();

   void setTransportGuarantee(TransportGuaranteeType var1);

   TransportGuaranteeType addNewTransportGuarantee();

   void unsetTransportGuarantee();

   DeploymentListenerListType getDeploymentListenerList();

   boolean isSetDeploymentListenerList();

   void setDeploymentListenerList(DeploymentListenerListType var1);

   DeploymentListenerListType addNewDeploymentListenerList();

   void unsetDeploymentListenerList();

   WsdlType getWsdl();

   boolean isSetWsdl();

   void setWsdl(WsdlType var1);

   WsdlType addNewWsdl();

   void unsetWsdl();

   BigInteger getTransactionTimeout();

   XmlNonNegativeInteger xgetTransactionTimeout();

   boolean isSetTransactionTimeout();

   void setTransactionTimeout(BigInteger var1);

   void xsetTransactionTimeout(XmlNonNegativeInteger var1);

   void unsetTransactionTimeout();

   CallbackProtocolType getCallbackProtocol();

   boolean isSetCallbackProtocol();

   void setCallbackProtocol(CallbackProtocolType var1);

   CallbackProtocolType addNewCallbackProtocol();

   void unsetCallbackProtocol();

   boolean getStreamAttachments();

   XmlBoolean xgetStreamAttachments();

   boolean isSetStreamAttachments();

   void setStreamAttachments(boolean var1);

   void xsetStreamAttachments(XmlBoolean var1);

   void unsetStreamAttachments();

   boolean getValidateRequest();

   XmlBoolean xgetValidateRequest();

   boolean isSetValidateRequest();

   void setValidateRequest(boolean var1);

   void xsetValidateRequest(XmlBoolean var1);

   void unsetValidateRequest();

   boolean getHttpFlushResponse();

   XmlBoolean xgetHttpFlushResponse();

   boolean isSetHttpFlushResponse();

   void setHttpFlushResponse(boolean var1);

   void xsetHttpFlushResponse(XmlBoolean var1);

   void unsetHttpFlushResponse();

   BigInteger getHttpResponseBuffersize();

   XmlNonNegativeInteger xgetHttpResponseBuffersize();

   boolean isSetHttpResponseBuffersize();

   void setHttpResponseBuffersize(BigInteger var1);

   void xsetHttpResponseBuffersize(XmlNonNegativeInteger var1);

   void unsetHttpResponseBuffersize();

   ReliabilityConfigType getReliabilityConfig();

   boolean isSetReliabilityConfig();

   void setReliabilityConfig(ReliabilityConfigType var1);

   ReliabilityConfigType addNewReliabilityConfig();

   void unsetReliabilityConfig();

   PersistenceConfigType getPersistenceConfig();

   boolean isSetPersistenceConfig();

   void setPersistenceConfig(PersistenceConfigType var1);

   PersistenceConfigType addNewPersistenceConfig();

   void unsetPersistenceConfig();

   BufferingConfigType getBufferingConfig();

   boolean isSetBufferingConfig();

   void setBufferingConfig(BufferingConfigType var1);

   BufferingConfigType addNewBufferingConfig();

   void unsetBufferingConfig();

   WsatConfigType getWsatConfig();

   boolean isSetWsatConfig();

   void setWsatConfig(WsatConfigType var1);

   WsatConfigType addNewWsatConfig();

   void unsetWsatConfig();

   OperationComponentType[] getOperationArray();

   OperationComponentType getOperationArray(int var1);

   int sizeOfOperationArray();

   void setOperationArray(OperationComponentType[] var1);

   void setOperationArray(int var1, OperationComponentType var2);

   OperationComponentType insertNewOperation(int var1);

   OperationComponentType addNewOperation();

   void removeOperation(int var1);

   SoapjmsServiceEndpointAddressType getSoapjmsServiceEndpointAddress();

   boolean isSetSoapjmsServiceEndpointAddress();

   void setSoapjmsServiceEndpointAddress(SoapjmsServiceEndpointAddressType var1);

   SoapjmsServiceEndpointAddressType addNewSoapjmsServiceEndpointAddress();

   void unsetSoapjmsServiceEndpointAddress();

   boolean getFastInfoset();

   XmlBoolean xgetFastInfoset();

   boolean isSetFastInfoset();

   void setFastInfoset(boolean var1);

   void xsetFastInfoset(XmlBoolean var1);

   void unsetFastInfoset();

   String getLoggingLevel();

   boolean isSetLoggingLevel();

   void setLoggingLevel(String var1);

   String addNewLoggingLevel();

   void unsetLoggingLevel();

   public static final class Factory {
      public static PortComponentType newInstance() {
         return (PortComponentType)XmlBeans.getContextTypeLoader().newInstance(PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType newInstance(XmlOptions options) {
         return (PortComponentType)XmlBeans.getContextTypeLoader().newInstance(PortComponentType.type, options);
      }

      public static PortComponentType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentType.type, options);
      }

      public static PortComponentType parse(File file) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(file, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(file, PortComponentType.type, options);
      }

      public static PortComponentType parse(URL u) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(u, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(u, PortComponentType.type, options);
      }

      public static PortComponentType parse(InputStream is) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(is, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(is, PortComponentType.type, options);
      }

      public static PortComponentType parse(Reader r) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(r, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(r, PortComponentType.type, options);
      }

      public static PortComponentType parse(XMLStreamReader sr) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentType.type, options);
      }

      public static PortComponentType parse(Node node) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(node, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(Node node, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(node, PortComponentType.type, options);
      }

      /** @deprecated */
      public static PortComponentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortComponentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentType.type, options);
      }

      private Factory() {
      }
   }
}
