package weblogic.j2ee.descriptor.wl;

import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.LoginConfigBean;

public interface PortComponentBean {
   String getPortComponentName();

   void setPortComponentName(String var1);

   WebserviceAddressBean getServiceEndpointAddress();

   WebserviceAddressBean createServiceEndpointAddress();

   void destroyServiceEndpointAddress(WebserviceAddressBean var1);

   AuthConstraintBean getAuthConstraint();

   AuthConstraintBean createAuthConstraint();

   void destroyAuthConstraint(AuthConstraintBean var1);

   LoginConfigBean getLoginConfig();

   LoginConfigBean createLoginConfig();

   void destroyLoginConfig(LoginConfigBean var1);

   String getTransportGuarantee();

   void setTransportGuarantee(String var1);

   DeploymentListenerListBean getDeploymentListenerList();

   DeploymentListenerListBean createDeploymentListenerList();

   void destroyDeploymentListenerList(DeploymentListenerListBean var1);

   WsdlBean getWsdl();

   WsdlBean createWsdl();

   void destroyWsdl(WsdlBean var1);

   void setTransactionTimeout(int var1);

   int getTransactionTimeout();

   void setCallbackProtocol(String var1);

   String getCallbackProtocol();

   void setStreamAttachments(boolean var1);

   boolean getStreamAttachments();

   boolean isValidateRequest();

   void setValidateRequest(boolean var1);

   boolean isHttpFlushResponse();

   void setHttpFlushResponse(boolean var1);

   int getHttpResponseBuffersize();

   void setHttpResponseBuffersize(int var1);

   ReliabilityConfigBean getReliabilityConfig();

   ReliabilityConfigBean createReliabilityConfig();

   void destroyReliabilityConfig();

   PersistenceConfigBean getPersistenceConfig();

   PersistenceConfigBean createPersistenceConfig();

   void destroyPersistenceConfig();

   BufferingConfigBean getBufferingConfig();

   BufferingConfigBean createBufferingConfig();

   void destroyBufferingConfig();

   WSATConfigBean getWSATConfig();

   WSATConfigBean createWSATConfig();

   void destroyWSATConfig();

   OperationComponentBean createOperation();

   OperationComponentBean lookupOperation(String var1);

   void destroyOperation(OperationComponentBean var1);

   OperationComponentBean[] getOperations();

   SoapjmsServiceEndpointAddressBean getSoapjmsServiceEndpointAddress();

   SoapjmsServiceEndpointAddressBean createSoapjmsServiceEndpointAddress();

   void destroySoapjmsServiceEndpointAddress();

   boolean isFastInfoset();

   void setFastInfoset(boolean var1);

   String getLoggingLevel();

   void setLoggingLevel(String var1);
}
