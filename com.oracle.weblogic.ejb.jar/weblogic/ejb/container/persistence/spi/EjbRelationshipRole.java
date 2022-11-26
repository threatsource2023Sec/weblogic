package weblogic.ejb.container.persistence.spi;

public interface EjbRelationshipRole {
   String[] getDescriptions();

   String getName();

   String getMultiplicity();

   boolean getCascadeDelete();

   RoleSource getRoleSource();

   CmrField getCmrField();
}
