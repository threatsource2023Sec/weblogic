package weblogic.ejb.container.persistence.spi;

import java.util.Map;

public interface Relationships {
   String[] getDescriptions();

   EjbEntityRef getEjbEntityRef(String var1);

   Map getAllEjbEntityRefs();

   EjbRelation getEjbRelation(String var1);

   Map getAllEjbRelations();
}
