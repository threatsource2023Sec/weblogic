package weblogic.j2ee.descriptor.customizers;

import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
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

public class ApplicationBeanCustomizerImpl implements ApplicationBeanCustomizer {
   private ApplicationBean bean;
   private AppEnvironmentBean appBean;

   public ApplicationBeanCustomizerImpl(ApplicationBean bean) {
      this.bean = bean;
   }

   public J2eeEnvironmentBean convertToJ2eeEnvironmentBean() {
      if (this.appBean == null) {
         this.appBean = new AppEnvironmentBean(this.bean);
      }

      return this.appBean;
   }

   private class AppEnvironmentBean implements J2eeEnvironmentBean {
      private ApplicationBean dd;

      private AppEnvironmentBean(ApplicationBean dd) {
         this.dd = dd;
      }

      public EnvEntryBean[] getEnvEntries() {
         return this.dd.getEnvEntries();
      }

      public EnvEntryBean createEnvEntry() {
         throw new UnsupportedOperationException();
      }

      public void destroyEnvEntry(EnvEntryBean envEntry) {
         throw new UnsupportedOperationException();
      }

      public EjbRefBean[] getEjbRefs() {
         return this.dd.getEjbRefs();
      }

      public EjbRefBean createEjbRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyEjbRef(EjbRefBean ejbRef) {
         throw new UnsupportedOperationException();
      }

      public ServiceRefBean[] getServiceRefs() {
         return this.dd.getServiceRefs();
      }

      public ServiceRefBean createServiceRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyServiceRef(ServiceRefBean serviceRef) {
         throw new UnsupportedOperationException();
      }

      public ResourceRefBean[] getResourceRefs() {
         return this.dd.getResourceRefs();
      }

      public ResourceRefBean createResourceRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyResourceRef(ResourceRefBean resourceRef) {
         throw new UnsupportedOperationException();
      }

      public ResourceEnvRefBean[] getResourceEnvRefs() {
         return this.dd.getResourceEnvRefs();
      }

      public ResourceEnvRefBean createResourceEnvRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyResourceEnvRef(ResourceEnvRefBean resourceEnvRef) {
         throw new UnsupportedOperationException();
      }

      public MessageDestinationRefBean[] getMessageDestinationRefs() {
         return this.dd.getMessageDestinationRefs();
      }

      public MessageDestinationRefBean createMessageDestinationRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyMessageDestinationRef(MessageDestinationRefBean messageDestinationRef) {
         throw new UnsupportedOperationException();
      }

      public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
         return this.dd.getPersistenceUnitRefs();
      }

      public PersistenceUnitRefBean createPersistenceUnitRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyPersistenceUnitRef(PersistenceUnitRefBean persistenceUnitRef) {
         throw new UnsupportedOperationException();
      }

      public LifecycleCallbackBean[] getPostConstructs() {
         throw new UnsupportedOperationException();
      }

      public LifecycleCallbackBean createPostConstruct() {
         throw new UnsupportedOperationException();
      }

      public void destroyPostConstruct(LifecycleCallbackBean postConstruct) {
         throw new UnsupportedOperationException();
      }

      public LifecycleCallbackBean[] getPreDestroys() {
         throw new UnsupportedOperationException();
      }

      public LifecycleCallbackBean createPreDestroy() {
         throw new UnsupportedOperationException();
      }

      public void destroyPreDestroy(LifecycleCallbackBean preDestroy) {
         throw new UnsupportedOperationException();
      }

      public DataSourceBean[] getDataSources() {
         return this.dd.getDataSources();
      }

      public DataSourceBean createDataSource() {
         throw new UnsupportedOperationException();
      }

      public void destroyDataSource(DataSourceBean ds) {
         throw new UnsupportedOperationException();
      }

      public EjbLocalRefBean[] getEjbLocalRefs() {
         return this.dd.getEjbLocalRefs();
      }

      public EjbLocalRefBean createEjbLocalRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyEjbLocalRef(EjbLocalRefBean ejbLocalRef) {
         throw new UnsupportedOperationException();
      }

      public PersistenceContextRefBean[] getPersistenceContextRefs() {
         return this.dd.getPersistenceContextRefs();
      }

      public PersistenceContextRefBean createPersistenceContextRef() {
         throw new UnsupportedOperationException();
      }

      public void destroyPersistenceContextRef(PersistenceContextRefBean persistenceContextRef) {
         throw new UnsupportedOperationException();
      }

      public JmsConnectionFactoryBean[] getJmsConnectionFactories() {
         return this.dd.getJmsConnectionFactories();
      }

      public JmsConnectionFactoryBean createJmsConnectionFactory() {
         throw new UnsupportedOperationException();
      }

      public void destroyJmsConnectionFactory(JmsConnectionFactoryBean jmsConnectionFactoryBean) {
         throw new UnsupportedOperationException();
      }

      public JmsDestinationBean[] getJmsDestinations() {
         return this.dd.getJmsDestinations();
      }

      public JmsDestinationBean createJmsDestination() {
         throw new UnsupportedOperationException();
      }

      public void destroyJmsDestination(JmsDestinationBean jmsDestinationBean) {
         throw new UnsupportedOperationException();
      }

      public MailSessionBean[] getMailSessions() {
         return this.dd.getMailSessions();
      }

      public MailSessionBean createMailSession() {
         throw new UnsupportedOperationException();
      }

      public void destroyMailSession(MailSessionBean mailSessionBean) {
         throw new UnsupportedOperationException();
      }

      public ConnectionFactoryResourceBean[] getConnectionFactories() {
         return this.dd.getConnectionFactories();
      }

      public ConnectionFactoryResourceBean createConnectionFactoryResourceBean() {
         throw new UnsupportedOperationException();
      }

      public void destroyConnectionFactory(ConnectionFactoryResourceBean connectionFactoryResourceBean) {
         throw new UnsupportedOperationException();
      }

      public AdministeredObjectBean[] getAdministeredObjects() {
         return this.dd.getAdministeredObjects();
      }

      public AdministeredObjectBean createAdministeredObjectBean() {
         throw new UnsupportedOperationException();
      }

      public void destroyAdministeredObject(AdministeredObjectBean administeredObjectBean) {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      AppEnvironmentBean(ApplicationBean x1, Object x2) {
         this(x1);
      }
   }
}
