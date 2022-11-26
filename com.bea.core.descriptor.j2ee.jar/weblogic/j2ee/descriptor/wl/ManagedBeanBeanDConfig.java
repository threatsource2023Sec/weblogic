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
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.AroundInvokeBean;
import weblogic.j2ee.descriptor.AroundTimeoutBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.JmsConnectionFactoryBean;
import weblogic.j2ee.descriptor.JmsDestinationBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;

public class ManagedBeanBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private ManagedBeanBean beanTreeNode;

   public ManagedBeanBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (ManagedBeanBean)btn;
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
      return this.getManagedBeanName();
   }

   public void initKeyPropertyValue(String value) {
      this.setManagedBeanName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("ManagedBeanName: ");
      sb.append(this.beanTreeNode.getManagedBeanName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getManagedBeanName() {
      return this.beanTreeNode.getManagedBeanName();
   }

   public void setManagedBeanName(String value) {
      this.beanTreeNode.setManagedBeanName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ManagedBeanName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getManagedBeanClass() {
      return this.beanTreeNode.getManagedBeanClass();
   }

   public void setManagedBeanClass(String value) {
      this.beanTreeNode.setManagedBeanClass(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ManagedBeanClass", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AroundInvokeBean[] getAroundInvokes() {
      return this.beanTreeNode.getAroundInvokes();
   }

   public AroundTimeoutBean[] getAroundTimeouts() {
      return this.beanTreeNode.getAroundTimeouts();
   }

   public EjbLocalRefBean[] getEjbLocalRefs() {
      return this.beanTreeNode.getEjbLocalRefs();
   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this.beanTreeNode.getPersistenceContextRefs();
   }

   public EnvEntryBean[] getEnvEntries() {
      return this.beanTreeNode.getEnvEntries();
   }

   public EjbRefBean[] getEjbRefs() {
      return this.beanTreeNode.getEjbRefs();
   }

   public ServiceRefBean[] getServiceRefs() {
      return this.beanTreeNode.getServiceRefs();
   }

   public ResourceRefBean[] getResourceRefs() {
      return this.beanTreeNode.getResourceRefs();
   }

   public ResourceEnvRefBean[] getResourceEnvRefs() {
      return this.beanTreeNode.getResourceEnvRefs();
   }

   public MessageDestinationRefBean[] getMessageDestinationRefs() {
      return this.beanTreeNode.getMessageDestinationRefs();
   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this.beanTreeNode.getPersistenceUnitRefs();
   }

   public LifecycleCallbackBean[] getPostConstructs() {
      return this.beanTreeNode.getPostConstructs();
   }

   public LifecycleCallbackBean[] getPreDestroys() {
      return this.beanTreeNode.getPreDestroys();
   }

   public DataSourceBean[] getDataSources() {
      return this.beanTreeNode.getDataSources();
   }

   public JmsConnectionFactoryBean[] getJmsConnectionFactories() {
      return this.beanTreeNode.getJmsConnectionFactories();
   }

   public JmsDestinationBean[] getJmsDestinations() {
      return this.beanTreeNode.getJmsDestinations();
   }

   public MailSessionBean[] getMailSessions() {
      return this.beanTreeNode.getMailSessions();
   }

   public ConnectionFactoryResourceBean[] getConnectionFactories() {
      return this.beanTreeNode.getConnectionFactories();
   }

   public AdministeredObjectBean[] getAdministeredObjects() {
      return this.beanTreeNode.getAdministeredObjects();
   }
}
