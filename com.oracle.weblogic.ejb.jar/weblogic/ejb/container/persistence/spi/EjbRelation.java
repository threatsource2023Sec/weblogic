package weblogic.ejb.container.persistence.spi;

import java.util.Collection;

public interface EjbRelation {
   String[] getDescriptions();

   String getTableName();

   String getEjbRelationName();

   EjbRelationshipRole getEjbRelationshipRole(String var1);

   Collection getAllEjbRelationshipRoles();
}
