package weblogic.j2ee.descriptor;

public interface InterceptorBean extends J2eeEnvironmentBean, EjbCallbackBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   String getInterceptorClass();

   void setInterceptorClass(String var1);

   AroundInvokeBean[] getAroundInvokes();

   AroundInvokeBean createAroundInvoke();

   void destroyAroundInvoke(AroundInvokeBean var1);

   AroundTimeoutBean[] getAroundTimeouts();

   AroundTimeoutBean createAroundTimeout();

   void destroyAroundTimeout(AroundTimeoutBean var1);

   LifecycleCallbackBean[] getAroundConstructs();

   LifecycleCallbackBean createAroundConstruct();

   void destroyAroundConstruct(LifecycleCallbackBean var1);

   EnvEntryBean[] getEnvEntries();

   EnvEntryBean createEnvEntry();

   void destroyEnvEntry(EnvEntryBean var1);

   EjbRefBean[] getEjbRefs();

   EjbRefBean createEjbRef();

   void destroyEjbRef(EjbRefBean var1);

   EjbLocalRefBean[] getEjbLocalRefs();

   EjbLocalRefBean createEjbLocalRef();

   void destroyEjbLocalRef(EjbLocalRefBean var1);

   ServiceRefBean[] getServiceRefs();

   ServiceRefBean createServiceRef();

   void destroyServiceRef(ServiceRefBean var1);

   ResourceRefBean[] getResourceRefs();

   ResourceRefBean createResourceRef();

   void destroyResourceRef(ResourceRefBean var1);

   ResourceEnvRefBean[] getResourceEnvRefs();

   ResourceEnvRefBean createResourceEnvRef();

   void destroyResourceEnvRef(ResourceEnvRefBean var1);

   MessageDestinationRefBean[] getMessageDestinationRefs();

   MessageDestinationRefBean createMessageDestinationRef();

   void destroyMessageDestinationRef(MessageDestinationRefBean var1);

   PersistenceContextRefBean[] getPersistenceContextRefs();

   PersistenceContextRefBean createPersistenceContextRef();

   void destroyPersistenceContextRef(PersistenceContextRefBean var1);

   PersistenceUnitRefBean[] getPersistenceUnitRefs();

   PersistenceUnitRefBean createPersistenceUnitRef();

   void destroyPersistenceUnitRef(PersistenceUnitRefBean var1);

   LifecycleCallbackBean[] getPostConstructs();

   LifecycleCallbackBean createPostConstruct();

   void destroyPostConstruct(LifecycleCallbackBean var1);

   LifecycleCallbackBean[] getPreDestroys();

   LifecycleCallbackBean createPreDestroy();

   void destroyPreDestroy(LifecycleCallbackBean var1);

   DataSourceBean[] getDataSources();

   DataSourceBean createDataSource();

   void destroyDataSource(DataSourceBean var1);

   JmsConnectionFactoryBean[] getJmsConnectionFactories();

   JmsConnectionFactoryBean createJmsConnectionFactory();

   void destroyJmsConnectionFactory(JmsConnectionFactoryBean var1);

   JmsDestinationBean[] getJmsDestinations();

   JmsDestinationBean createJmsDestination();

   void destroyJmsDestination(JmsDestinationBean var1);

   MailSessionBean[] getMailSessions();

   MailSessionBean createMailSession();

   void destroyMailSession(MailSessionBean var1);

   ConnectionFactoryResourceBean[] getConnectionFactories();

   ConnectionFactoryResourceBean createConnectionFactoryResourceBean();

   void destroyConnectionFactory(ConnectionFactoryResourceBean var1);

   AdministeredObjectBean[] getAdministeredObjects();

   AdministeredObjectBean createAdministeredObjectBean();

   void destroyAdministeredObject(AdministeredObjectBean var1);

   LifecycleCallbackBean[] getPostActivates();

   LifecycleCallbackBean createPostActivate();

   void destroyPostActivate(LifecycleCallbackBean var1);

   LifecycleCallbackBean[] getPrePassivates();

   LifecycleCallbackBean createPrePassivate();

   void destroyPrePassivate(LifecycleCallbackBean var1);

   String getId();

   void setId(String var1);
}
