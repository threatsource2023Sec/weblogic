package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface ReferenceDescriptorMBean extends XMLElementMBean {
   ResourceDescriptionMBean[] getResourceDescriptions();

   void setResourceDescriptions(ResourceDescriptionMBean[] var1);

   void addResourceDescription(ResourceDescriptionMBean var1);

   void removeResourceDescription(ResourceDescriptionMBean var1);

   ResourceEnvDescriptionMBean[] getResourceEnvDescriptions();

   void setResourceEnvDescriptions(ResourceEnvDescriptionMBean[] var1);

   void addResourceEnvDescription(ResourceEnvDescriptionMBean var1);

   void removeResourceEnvDescription(ResourceEnvDescriptionMBean var1);

   EJBReferenceDescriptionMBean[] getEJBReferenceDescriptions();

   void setEJBReferenceDescriptions(EJBReferenceDescriptionMBean[] var1);

   void addEJBReferenceDescription(EJBReferenceDescriptionMBean var1);

   void removeEJBReferenceDescription(EJBReferenceDescriptionMBean var1);

   EJBLocalReferenceDescriptionMBean[] getEJBLocalReferenceDescriptions();

   void setEJBLocalReferenceDescriptions(EJBLocalReferenceDescriptionMBean[] var1);

   void addEJBLocalReferenceDescription(EJBLocalReferenceDescriptionMBean var1);

   void removeEJBLocalReferenceDescription(EJBLocalReferenceDescriptionMBean var1);
}
