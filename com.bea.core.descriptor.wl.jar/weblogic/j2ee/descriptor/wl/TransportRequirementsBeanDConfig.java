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

public class TransportRequirementsBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private TransportRequirementsBean beanTreeNode;

   public TransportRequirementsBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (TransportRequirementsBean)btn;
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
      return null;
   }

   public void initKeyPropertyValue(String value) {
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getIntegrity() {
      return this.beanTreeNode.getIntegrity();
   }

   public void setIntegrity(String value) {
      this.beanTreeNode.setIntegrity(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Integrity", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConfidentiality() {
      return this.beanTreeNode.getConfidentiality();
   }

   public void setConfidentiality(String value) {
      this.beanTreeNode.setConfidentiality(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Confidentiality", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getClientCertAuthentication() {
      return this.beanTreeNode.getClientCertAuthentication();
   }

   public void setClientCertAuthentication(String value) {
      this.beanTreeNode.setClientCertAuthentication(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ClientCertAuthentication", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getId() {
      return this.beanTreeNode.getId();
   }

   public void setId(String value) {
      this.beanTreeNode.setId(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Id", (Object)null, (Object)null));
      this.setModified(true);
   }
}
