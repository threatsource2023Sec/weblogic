package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface RelationshipDescriptorMBean extends XMLElementMBean {
   EJBEntityRefDescriptionMBean[] getEJBEntityRefDescriptions();

   void setEJBEntityRefDescriptions(EJBEntityRefDescriptionMBean[] var1);

   void addEJBEntityRefDescription(EJBEntityRefDescriptionMBean var1);

   void removeEJBEntityRefDescription(EJBEntityRefDescriptionMBean var1);
}
