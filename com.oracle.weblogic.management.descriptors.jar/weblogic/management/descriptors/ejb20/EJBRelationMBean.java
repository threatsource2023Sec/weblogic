package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface EJBRelationMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   String getEJBRelationName();

   void setEJBRelationName(String var1);

   EJBRelationshipRoleMBean[] getEJBRelationshipRoles();

   void setEJBRelationshipRoles(EJBRelationshipRoleMBean[] var1);

   void addEJBRelationshipRole(EJBRelationshipRoleMBean var1);

   void removeEJBRelationshipRole(EJBRelationshipRoleMBean var1);
}
