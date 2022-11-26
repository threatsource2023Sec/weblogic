package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlNonNegativeInteger;
import com.oracle.xmlns.weblogic.weblogicWebservices.BufferingConfigType;
import com.oracle.xmlns.weblogic.weblogicWebservices.CallbackProtocolType;
import com.oracle.xmlns.weblogic.weblogicWebservices.DeploymentListenerListType;
import com.oracle.xmlns.weblogic.weblogicWebservices.OperationComponentType;
import com.oracle.xmlns.weblogic.weblogicWebservices.PersistenceConfigType;
import com.oracle.xmlns.weblogic.weblogicWebservices.PortComponentType;
import com.oracle.xmlns.weblogic.weblogicWebservices.ReliabilityConfigType;
import com.oracle.xmlns.weblogic.weblogicWebservices.SoapjmsServiceEndpointAddressType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WebserviceAddressType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WsatConfigType;
import com.oracle.xmlns.weblogic.weblogicWebservices.WsdlType;
import com.sun.java.xml.ns.j2Ee.AuthConstraintType;
import com.sun.java.xml.ns.j2Ee.LoginConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.TransportGuaranteeType;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PortComponentTypeImpl extends XmlComplexContentImpl implements PortComponentType {
   private static final long serialVersionUID = 1L;
   private static final QName PORTCOMPONENTNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "port-component-name");
   private static final QName SERVICEENDPOINTADDRESS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "service-endpoint-address");
   private static final QName AUTHCONSTRAINT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "auth-constraint");
   private static final QName LOGINCONFIG$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "login-config");
   private static final QName TRANSPORTGUARANTEE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "transport-guarantee");
   private static final QName DEPLOYMENTLISTENERLIST$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "deployment-listener-list");
   private static final QName WSDL$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "wsdl");
   private static final QName TRANSACTIONTIMEOUT$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "transaction-timeout");
   private static final QName CALLBACKPROTOCOL$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "callback-protocol");
   private static final QName STREAMATTACHMENTS$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "stream-attachments");
   private static final QName VALIDATEREQUEST$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "validate-request");
   private static final QName HTTPFLUSHRESPONSE$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "http-flush-response");
   private static final QName HTTPRESPONSEBUFFERSIZE$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "http-response-buffersize");
   private static final QName RELIABILITYCONFIG$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "reliability-config");
   private static final QName PERSISTENCECONFIG$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "persistence-config");
   private static final QName BUFFERINGCONFIG$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "buffering-config");
   private static final QName WSATCONFIG$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "wsat-config");
   private static final QName OPERATION$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "operation");
   private static final QName SOAPJMSSERVICEENDPOINTADDRESS$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "soapjms-service-endpoint-address");
   private static final QName FASTINFOSET$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "fast-infoset");
   private static final QName LOGGINGLEVEL$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "logging-level");

   public PortComponentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getPortComponentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTCOMPONENTNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPortComponentName(String portComponentName) {
      this.generatedSetterHelperImpl(portComponentName, PORTCOMPONENTNAME$0, 0, (short)1);
   }

   public String addNewPortComponentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTCOMPONENTNAME$0);
         return target;
      }
   }

   public WebserviceAddressType getServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceAddressType target = null;
         target = (WebserviceAddressType)this.get_store().find_element_user(SERVICEENDPOINTADDRESS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEENDPOINTADDRESS$2) != 0;
      }
   }

   public void setServiceEndpointAddress(WebserviceAddressType serviceEndpointAddress) {
      this.generatedSetterHelperImpl(serviceEndpointAddress, SERVICEENDPOINTADDRESS$2, 0, (short)1);
   }

   public WebserviceAddressType addNewServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebserviceAddressType target = null;
         target = (WebserviceAddressType)this.get_store().add_element_user(SERVICEENDPOINTADDRESS$2);
         return target;
      }
   }

   public void unsetServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEENDPOINTADDRESS$2, 0);
      }
   }

   public AuthConstraintType getAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthConstraintType target = null;
         target = (AuthConstraintType)this.get_store().find_element_user(AUTHCONSTRAINT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHCONSTRAINT$4) != 0;
      }
   }

   public void setAuthConstraint(AuthConstraintType authConstraint) {
      this.generatedSetterHelperImpl(authConstraint, AUTHCONSTRAINT$4, 0, (short)1);
   }

   public AuthConstraintType addNewAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthConstraintType target = null;
         target = (AuthConstraintType)this.get_store().add_element_user(AUTHCONSTRAINT$4);
         return target;
      }
   }

   public void unsetAuthConstraint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHCONSTRAINT$4, 0);
      }
   }

   public LoginConfigType getLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().find_element_user(LOGINCONFIG$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGINCONFIG$6) != 0;
      }
   }

   public void setLoginConfig(LoginConfigType loginConfig) {
      this.generatedSetterHelperImpl(loginConfig, LOGINCONFIG$6, 0, (short)1);
   }

   public LoginConfigType addNewLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoginConfigType target = null;
         target = (LoginConfigType)this.get_store().add_element_user(LOGINCONFIG$6);
         return target;
      }
   }

   public void unsetLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINCONFIG$6, 0);
      }
   }

   public TransportGuaranteeType getTransportGuarantee() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportGuaranteeType target = null;
         target = (TransportGuaranteeType)this.get_store().find_element_user(TRANSPORTGUARANTEE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransportGuarantee() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSPORTGUARANTEE$8) != 0;
      }
   }

   public void setTransportGuarantee(TransportGuaranteeType transportGuarantee) {
      this.generatedSetterHelperImpl(transportGuarantee, TRANSPORTGUARANTEE$8, 0, (short)1);
   }

   public TransportGuaranteeType addNewTransportGuarantee() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportGuaranteeType target = null;
         target = (TransportGuaranteeType)this.get_store().add_element_user(TRANSPORTGUARANTEE$8);
         return target;
      }
   }

   public void unsetTransportGuarantee() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSPORTGUARANTEE$8, 0);
      }
   }

   public DeploymentListenerListType getDeploymentListenerList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentListenerListType target = null;
         target = (DeploymentListenerListType)this.get_store().find_element_user(DEPLOYMENTLISTENERLIST$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeploymentListenerList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPLOYMENTLISTENERLIST$10) != 0;
      }
   }

   public void setDeploymentListenerList(DeploymentListenerListType deploymentListenerList) {
      this.generatedSetterHelperImpl(deploymentListenerList, DEPLOYMENTLISTENERLIST$10, 0, (short)1);
   }

   public DeploymentListenerListType addNewDeploymentListenerList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeploymentListenerListType target = null;
         target = (DeploymentListenerListType)this.get_store().add_element_user(DEPLOYMENTLISTENERLIST$10);
         return target;
      }
   }

   public void unsetDeploymentListenerList() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPLOYMENTLISTENERLIST$10, 0);
      }
   }

   public WsdlType getWsdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlType target = null;
         target = (WsdlType)this.get_store().find_element_user(WSDL$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDL$12) != 0;
      }
   }

   public void setWsdl(WsdlType wsdl) {
      this.generatedSetterHelperImpl(wsdl, WSDL$12, 0, (short)1);
   }

   public WsdlType addNewWsdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsdlType target = null;
         target = (WsdlType)this.get_store().add_element_user(WSDL$12);
         return target;
      }
   }

   public void unsetWsdl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDL$12, 0);
      }
   }

   public BigInteger getTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTIMEOUT$14, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlNonNegativeInteger xgetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_element_user(TRANSACTIONTIMEOUT$14, 0);
         return target;
      }
   }

   public boolean isSetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONTIMEOUT$14) != 0;
      }
   }

   public void setTransactionTimeout(BigInteger transactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONTIMEOUT$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONTIMEOUT$14);
         }

         target.setBigIntegerValue(transactionTimeout);
      }
   }

   public void xsetTransactionTimeout(XmlNonNegativeInteger transactionTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_element_user(TRANSACTIONTIMEOUT$14, 0);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_store().add_element_user(TRANSACTIONTIMEOUT$14);
         }

         target.set(transactionTimeout);
      }
   }

   public void unsetTransactionTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONTIMEOUT$14, 0);
      }
   }

   public CallbackProtocolType getCallbackProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallbackProtocolType target = null;
         target = (CallbackProtocolType)this.get_store().find_element_user(CALLBACKPROTOCOL$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCallbackProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CALLBACKPROTOCOL$16) != 0;
      }
   }

   public void setCallbackProtocol(CallbackProtocolType callbackProtocol) {
      this.generatedSetterHelperImpl(callbackProtocol, CALLBACKPROTOCOL$16, 0, (short)1);
   }

   public CallbackProtocolType addNewCallbackProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallbackProtocolType target = null;
         target = (CallbackProtocolType)this.get_store().add_element_user(CALLBACKPROTOCOL$16);
         return target;
      }
   }

   public void unsetCallbackProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CALLBACKPROTOCOL$16, 0);
      }
   }

   public boolean getStreamAttachments() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STREAMATTACHMENTS$18, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStreamAttachments() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STREAMATTACHMENTS$18, 0);
         return target;
      }
   }

   public boolean isSetStreamAttachments() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STREAMATTACHMENTS$18) != 0;
      }
   }

   public void setStreamAttachments(boolean streamAttachments) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STREAMATTACHMENTS$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STREAMATTACHMENTS$18);
         }

         target.setBooleanValue(streamAttachments);
      }
   }

   public void xsetStreamAttachments(XmlBoolean streamAttachments) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STREAMATTACHMENTS$18, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STREAMATTACHMENTS$18);
         }

         target.set(streamAttachments);
      }
   }

   public void unsetStreamAttachments() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STREAMATTACHMENTS$18, 0);
      }
   }

   public boolean getValidateRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATEREQUEST$20, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetValidateRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATEREQUEST$20, 0);
         return target;
      }
   }

   public boolean isSetValidateRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATEREQUEST$20) != 0;
      }
   }

   public void setValidateRequest(boolean validateRequest) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATEREQUEST$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATEREQUEST$20);
         }

         target.setBooleanValue(validateRequest);
      }
   }

   public void xsetValidateRequest(XmlBoolean validateRequest) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATEREQUEST$20, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(VALIDATEREQUEST$20);
         }

         target.set(validateRequest);
      }
   }

   public void unsetValidateRequest() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATEREQUEST$20, 0);
      }
   }

   public boolean getHttpFlushResponse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPFLUSHRESPONSE$22, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetHttpFlushResponse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(HTTPFLUSHRESPONSE$22, 0);
         return target;
      }
   }

   public boolean isSetHttpFlushResponse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPFLUSHRESPONSE$22) != 0;
      }
   }

   public void setHttpFlushResponse(boolean httpFlushResponse) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPFLUSHRESPONSE$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HTTPFLUSHRESPONSE$22);
         }

         target.setBooleanValue(httpFlushResponse);
      }
   }

   public void xsetHttpFlushResponse(XmlBoolean httpFlushResponse) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(HTTPFLUSHRESPONSE$22, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(HTTPFLUSHRESPONSE$22);
         }

         target.set(httpFlushResponse);
      }
   }

   public void unsetHttpFlushResponse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPFLUSHRESPONSE$22, 0);
      }
   }

   public BigInteger getHttpResponseBuffersize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPRESPONSEBUFFERSIZE$24, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlNonNegativeInteger xgetHttpResponseBuffersize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_element_user(HTTPRESPONSEBUFFERSIZE$24, 0);
         return target;
      }
   }

   public boolean isSetHttpResponseBuffersize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPRESPONSEBUFFERSIZE$24) != 0;
      }
   }

   public void setHttpResponseBuffersize(BigInteger httpResponseBuffersize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HTTPRESPONSEBUFFERSIZE$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HTTPRESPONSEBUFFERSIZE$24);
         }

         target.setBigIntegerValue(httpResponseBuffersize);
      }
   }

   public void xsetHttpResponseBuffersize(XmlNonNegativeInteger httpResponseBuffersize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_element_user(HTTPRESPONSEBUFFERSIZE$24, 0);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_store().add_element_user(HTTPRESPONSEBUFFERSIZE$24);
         }

         target.set(httpResponseBuffersize);
      }
   }

   public void unsetHttpResponseBuffersize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPRESPONSEBUFFERSIZE$24, 0);
      }
   }

   public ReliabilityConfigType getReliabilityConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ReliabilityConfigType target = null;
         target = (ReliabilityConfigType)this.get_store().find_element_user(RELIABILITYCONFIG$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetReliabilityConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELIABILITYCONFIG$26) != 0;
      }
   }

   public void setReliabilityConfig(ReliabilityConfigType reliabilityConfig) {
      this.generatedSetterHelperImpl(reliabilityConfig, RELIABILITYCONFIG$26, 0, (short)1);
   }

   public ReliabilityConfigType addNewReliabilityConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ReliabilityConfigType target = null;
         target = (ReliabilityConfigType)this.get_store().add_element_user(RELIABILITYCONFIG$26);
         return target;
      }
   }

   public void unsetReliabilityConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELIABILITYCONFIG$26, 0);
      }
   }

   public PersistenceConfigType getPersistenceConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceConfigType target = null;
         target = (PersistenceConfigType)this.get_store().find_element_user(PERSISTENCECONFIG$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistenceConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCECONFIG$28) != 0;
      }
   }

   public void setPersistenceConfig(PersistenceConfigType persistenceConfig) {
      this.generatedSetterHelperImpl(persistenceConfig, PERSISTENCECONFIG$28, 0, (short)1);
   }

   public PersistenceConfigType addNewPersistenceConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceConfigType target = null;
         target = (PersistenceConfigType)this.get_store().add_element_user(PERSISTENCECONFIG$28);
         return target;
      }
   }

   public void unsetPersistenceConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCECONFIG$28, 0);
      }
   }

   public BufferingConfigType getBufferingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingConfigType target = null;
         target = (BufferingConfigType)this.get_store().find_element_user(BUFFERINGCONFIG$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBufferingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUFFERINGCONFIG$30) != 0;
      }
   }

   public void setBufferingConfig(BufferingConfigType bufferingConfig) {
      this.generatedSetterHelperImpl(bufferingConfig, BUFFERINGCONFIG$30, 0, (short)1);
   }

   public BufferingConfigType addNewBufferingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BufferingConfigType target = null;
         target = (BufferingConfigType)this.get_store().add_element_user(BUFFERINGCONFIG$30);
         return target;
      }
   }

   public void unsetBufferingConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUFFERINGCONFIG$30, 0);
      }
   }

   public WsatConfigType getWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().find_element_user(WSATCONFIG$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSATCONFIG$32) != 0;
      }
   }

   public void setWsatConfig(WsatConfigType wsatConfig) {
      this.generatedSetterHelperImpl(wsatConfig, WSATCONFIG$32, 0, (short)1);
   }

   public WsatConfigType addNewWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WsatConfigType target = null;
         target = (WsatConfigType)this.get_store().add_element_user(WSATCONFIG$32);
         return target;
      }
   }

   public void unsetWsatConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSATCONFIG$32, 0);
      }
   }

   public OperationComponentType[] getOperationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OPERATION$34, targetList);
         OperationComponentType[] result = new OperationComponentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OperationComponentType getOperationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationComponentType target = null;
         target = (OperationComponentType)this.get_store().find_element_user(OPERATION$34, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOperationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPERATION$34);
      }
   }

   public void setOperationArray(OperationComponentType[] operationArray) {
      this.check_orphaned();
      this.arraySetterHelper(operationArray, OPERATION$34);
   }

   public void setOperationArray(int i, OperationComponentType operation) {
      this.generatedSetterHelperImpl(operation, OPERATION$34, i, (short)2);
   }

   public OperationComponentType insertNewOperation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationComponentType target = null;
         target = (OperationComponentType)this.get_store().insert_element_user(OPERATION$34, i);
         return target;
      }
   }

   public OperationComponentType addNewOperation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationComponentType target = null;
         target = (OperationComponentType)this.get_store().add_element_user(OPERATION$34);
         return target;
      }
   }

   public void removeOperation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPERATION$34, i);
      }
   }

   public SoapjmsServiceEndpointAddressType getSoapjmsServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SoapjmsServiceEndpointAddressType target = null;
         target = (SoapjmsServiceEndpointAddressType)this.get_store().find_element_user(SOAPJMSSERVICEENDPOINTADDRESS$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSoapjmsServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOAPJMSSERVICEENDPOINTADDRESS$36) != 0;
      }
   }

   public void setSoapjmsServiceEndpointAddress(SoapjmsServiceEndpointAddressType soapjmsServiceEndpointAddress) {
      this.generatedSetterHelperImpl(soapjmsServiceEndpointAddress, SOAPJMSSERVICEENDPOINTADDRESS$36, 0, (short)1);
   }

   public SoapjmsServiceEndpointAddressType addNewSoapjmsServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SoapjmsServiceEndpointAddressType target = null;
         target = (SoapjmsServiceEndpointAddressType)this.get_store().add_element_user(SOAPJMSSERVICEENDPOINTADDRESS$36);
         return target;
      }
   }

   public void unsetSoapjmsServiceEndpointAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOAPJMSSERVICEENDPOINTADDRESS$36, 0);
      }
   }

   public boolean getFastInfoset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FASTINFOSET$38, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetFastInfoset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FASTINFOSET$38, 0);
         return target;
      }
   }

   public boolean isSetFastInfoset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FASTINFOSET$38) != 0;
      }
   }

   public void setFastInfoset(boolean fastInfoset) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FASTINFOSET$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FASTINFOSET$38);
         }

         target.setBooleanValue(fastInfoset);
      }
   }

   public void xsetFastInfoset(XmlBoolean fastInfoset) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(FASTINFOSET$38, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(FASTINFOSET$38);
         }

         target.set(fastInfoset);
      }
   }

   public void unsetFastInfoset() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FASTINFOSET$38, 0);
      }
   }

   public String getLoggingLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(LOGGINGLEVEL$40, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoggingLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGINGLEVEL$40) != 0;
      }
   }

   public void setLoggingLevel(String loggingLevel) {
      this.generatedSetterHelperImpl(loggingLevel, LOGGINGLEVEL$40, 0, (short)1);
   }

   public String addNewLoggingLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(LOGGINGLEVEL$40);
         return target;
      }
   }

   public void unsetLoggingLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGINGLEVEL$40, 0);
      }
   }
}
