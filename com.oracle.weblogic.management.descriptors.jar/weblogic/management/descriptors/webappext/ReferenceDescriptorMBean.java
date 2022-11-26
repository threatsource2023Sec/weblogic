package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.weblogic.ResourceEnvDescriptionMBean;

public interface ReferenceDescriptorMBean extends WebElementMBean {
   void setResourceReferences(ResourceDescriptionMBean[] var1);

   ResourceDescriptionMBean[] getResourceReferences();

   void addResourceReference(ResourceDescriptionMBean var1);

   void removeResourceReference(ResourceDescriptionMBean var1);

   void setResourceEnvReferences(ResourceEnvDescriptionMBean[] var1);

   ResourceEnvDescriptionMBean[] getResourceEnvReferences();

   void addResourceEnvReference(ResourceEnvDescriptionMBean var1);

   void removeResourceEnvReference(ResourceEnvDescriptionMBean var1);

   void setEjbReferences(EjbReferenceDescriptionMBean[] var1);

   EjbReferenceDescriptionMBean[] getEjbReferences();

   void addEjbReference(EjbReferenceDescriptionMBean var1);

   void removeEjbReference(EjbReferenceDescriptionMBean var1);
}
