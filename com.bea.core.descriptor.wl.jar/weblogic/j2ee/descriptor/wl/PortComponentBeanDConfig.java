package weblogic.j2ee.descriptor.wl;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.LoginConfigBean;

public class PortComponentBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PortComponentBean beanTreeNode;

   public PortComponentBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PortComponentBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return this.getPortComponentName();
   }

   public void initKeyPropertyValue(String value) {
      this.setPortComponentName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("PortComponentName: ");
      sb.append(this.beanTreeNode.getPortComponentName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getPortComponentName() {
      return this.beanTreeNode.getPortComponentName();
   }

   public void setPortComponentName(String value) {
      this.beanTreeNode.setPortComponentName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "PortComponentName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public WebserviceAddressBean getServiceEndpointAddress() {
      return this.beanTreeNode.getServiceEndpointAddress();
   }

   public AuthConstraintBean getAuthConstraint() {
      return this.beanTreeNode.getAuthConstraint();
   }

   public LoginConfigBean getLoginConfig() {
      return this.beanTreeNode.getLoginConfig();
   }

   public String getTransportGuarantee() {
      return this.beanTreeNode.getTransportGuarantee();
   }

   public void setTransportGuarantee(String value) {
      this.beanTreeNode.setTransportGuarantee(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TransportGuarantee", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DeploymentListenerListBean getDeploymentListenerList() {
      return this.beanTreeNode.getDeploymentListenerList();
   }

   public WsdlBean getWsdl() {
      return this.beanTreeNode.getWsdl();
   }

   public int getTransactionTimeout() {
      return this.beanTreeNode.getTransactionTimeout();
   }

   public void setTransactionTimeout(int value) {
      this.beanTreeNode.setTransactionTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "TransactionTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getCallbackProtocol() {
      return this.beanTreeNode.getCallbackProtocol();
   }

   public void setCallbackProtocol(String value) {
      this.beanTreeNode.setCallbackProtocol(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "CallbackProtocol", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getStreamAttachments() {
      return this.beanTreeNode.getStreamAttachments();
   }

   public void setStreamAttachments(boolean value) {
      this.beanTreeNode.setStreamAttachments(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "StreamAttachments", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isValidateRequest() {
      return this.beanTreeNode.isValidateRequest();
   }

   public void setValidateRequest(boolean value) {
      this.beanTreeNode.setValidateRequest(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ValidateRequest", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean isHttpFlushResponse() {
      return this.beanTreeNode.isHttpFlushResponse();
   }

   public void setHttpFlushResponse(boolean value) {
      this.beanTreeNode.setHttpFlushResponse(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpFlushResponse", (Object)null, (Object)null));
      this.setModified(true);
   }

   public int getHttpResponseBuffersize() {
      return this.beanTreeNode.getHttpResponseBuffersize();
   }

   public void setHttpResponseBuffersize(int value) {
      this.beanTreeNode.setHttpResponseBuffersize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "HttpResponseBuffersize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ReliabilityConfigBean getReliabilityConfig() {
      return this.beanTreeNode.getReliabilityConfig();
   }

   public PersistenceConfigBean getPersistenceConfig() {
      return this.beanTreeNode.getPersistenceConfig();
   }

   public BufferingConfigBean getBufferingConfig() {
      return this.beanTreeNode.getBufferingConfig();
   }

   public WSATConfigBean getWSATConfig() {
      return this.beanTreeNode.getWSATConfig();
   }

   public OperationComponentBean[] getOperations() {
      return this.beanTreeNode.getOperations();
   }

   public SoapjmsServiceEndpointAddressBean getSoapjmsServiceEndpointAddress() {
      return this.beanTreeNode.getSoapjmsServiceEndpointAddress();
   }

   public boolean isFastInfoset() {
      return this.beanTreeNode.isFastInfoset();
   }

   public void setFastInfoset(boolean value) {
      this.beanTreeNode.setFastInfoset(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FastInfoset", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getLoggingLevel() {
      return this.beanTreeNode.getLoggingLevel();
   }

   public void setLoggingLevel(String value) {
      this.beanTreeNode.setLoggingLevel(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LoggingLevel", (Object)null, (Object)null));
      this.setModified(true);
   }
}
