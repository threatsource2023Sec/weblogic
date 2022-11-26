package weblogic.j2ee.descriptor.wl;

public interface WeblogicApplicationClientBean extends WeblogicEnvironmentBean {
   String getServerApplicationName();

   ResourceDescriptionBean[] getResourceDescriptions();

   ResourceDescriptionBean createResourceDescription();

   void destroyResourceDescription(ResourceDescriptionBean var1);

   ResourceEnvDescriptionBean[] getResourceEnvDescriptions();

   ResourceEnvDescriptionBean createResourceEnvDescription();

   void destroyResourceEnvDescription(ResourceEnvDescriptionBean var1);

   EjbReferenceDescriptionBean[] getEjbReferenceDescriptions();

   EjbReferenceDescriptionBean createEjbReferenceDescription();

   void destroyEjbReferenceDescription(EjbReferenceDescriptionBean var1);

   ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions();

   ServiceReferenceDescriptionBean createServiceReferenceDescription();

   void destroyServiceReferenceDescription(ServiceReferenceDescriptionBean var1);

   MessageDestinationDescriptorBean[] getMessageDestinationDescriptors();

   MessageDestinationDescriptorBean createMessageDestinationDescriptor();

   void destroyMessageDestinationDescriptor(MessageDestinationDescriptorBean var1);

   MessageDestinationDescriptorBean lookupMessageDestinationDescriptor(String var1);

   String getId();

   void setId(String var1);

   String getVersion();

   void setVersion(String var1);

   CdiDescriptorBean getCdiDescriptor();
}
