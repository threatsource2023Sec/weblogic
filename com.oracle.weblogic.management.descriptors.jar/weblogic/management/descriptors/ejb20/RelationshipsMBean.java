package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBean;

public interface RelationshipsMBean extends XMLElementMBean {
   String getDescription();

   void setDescription(String var1);

   EJBRelationMBean[] getEJBRelations();

   void setEJBRelations(EJBRelationMBean[] var1);

   void addEJBRelation(EJBRelationMBean var1);

   void removeEJBRelation(EJBRelationMBean var1);
}
