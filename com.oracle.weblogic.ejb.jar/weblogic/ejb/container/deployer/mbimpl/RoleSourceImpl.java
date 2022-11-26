package weblogic.ejb.container.deployer.mbimpl;

import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.j2ee.descriptor.EjbRelationshipRoleBean;

class RoleSourceImpl implements RoleSource {
   private final EjbRelationshipRoleBean bean;

   RoleSourceImpl(EjbRelationshipRoleBean mb) {
      this.bean = mb;
   }

   public String[] getDescriptions() {
      return this.bean.getRelationshipRoleSource().getDescriptions();
   }

   public String getEjbName() {
      return this.bean.getRelationshipRoleSource().getEjbName();
   }
}
