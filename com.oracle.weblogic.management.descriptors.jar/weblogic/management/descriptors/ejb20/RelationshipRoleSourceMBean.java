package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface RelationshipRoleSourceMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBName();

   void setEJBName(String var1);
}
