package weblogic.ejb.container.persistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;

public final class EjbRelationImpl implements EjbRelation {
   protected String[] description;
   protected String ejbRelationName;
   protected String tableName;
   protected Map ejbRelationshipRoles = new HashMap();

   public void setDescriptions(String[] d) {
      this.description = d;
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public void setTableName(String tn) {
      this.tableName = tn;
   }

   public String getTableName() {
      return this.tableName;
   }

   public void setEjbRelationName(String ejbRelationName) {
      this.ejbRelationName = ejbRelationName;
   }

   public String getEjbRelationName() {
      return this.ejbRelationName;
   }

   public void addEjbRelationshipRole(EjbRelationshipRole role) {
      this.ejbRelationshipRoles.put(role.getName(), role);
   }

   public EjbRelationshipRole removeEjbRelationshipRole(String roleName) {
      return (EjbRelationshipRole)this.ejbRelationshipRoles.remove(roleName);
   }

   public EjbRelationshipRole getEjbRelationshipRole(String roleName) {
      return (EjbRelationshipRole)this.ejbRelationshipRoles.get(roleName);
   }

   public Collection getAllEjbRelationshipRoles() {
      return this.ejbRelationshipRoles.values();
   }
}
