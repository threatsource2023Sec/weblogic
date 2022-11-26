package weblogic.ejb.container.deployer.mbimpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.j2ee.descriptor.EjbRelationBean;
import weblogic.j2ee.descriptor.EjbRelationshipRoleBean;
import weblogic.utils.Debug;

public final class RelationImpl implements EjbRelation {
   private final EjbRelationBean ejbRelationBean;
   private final Map roles = new HashMap();

   public RelationImpl(EjbRelationBean mb) {
      this.ejbRelationBean = mb;
      EjbRelationshipRoleBean[] var2 = mb.getEjbRelationshipRoles();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EjbRelationshipRoleBean role = var2[var4];
         this.roles.put(role.getEjbRelationshipRoleName(), new RelationshipRoleImpl(role));
      }

   }

   public String[] getDescriptions() {
      return this.ejbRelationBean.getDescriptions();
   }

   public String getTableName() {
      Debug.assertion(false);
      return null;
   }

   public String getEjbRelationName() {
      return this.ejbRelationBean.getEjbRelationName();
   }

   public EjbRelationshipRole getEjbRelationshipRole(String roleName) {
      return (EjbRelationshipRole)this.roles.get(roleName);
   }

   public Collection getAllEjbRelationshipRoles() {
      return this.roles.values();
   }
}
