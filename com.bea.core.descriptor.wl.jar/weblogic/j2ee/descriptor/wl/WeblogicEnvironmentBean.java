package weblogic.j2ee.descriptor.wl;

public interface WeblogicEnvironmentBean {
   ResourceDescriptionBean[] getResourceDescriptions();

   ResourceEnvDescriptionBean[] getResourceEnvDescriptions();

   EjbReferenceDescriptionBean[] getEjbReferenceDescriptions();

   ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions();
}
