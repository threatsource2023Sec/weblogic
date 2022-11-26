package weblogic.ejb.container.deployer.mbimpl;

import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.j2ee.descriptor.EjbRelationshipRoleBean;

public final class RelationshipRoleImpl implements EjbRelationshipRole {
   private final EjbRelationshipRoleBean bean;

   public RelationshipRoleImpl(EjbRelationshipRoleBean mb) {
      this.bean = mb;
   }

   public String[] getDescriptions() {
      return this.bean.getDescriptions();
   }

   public String getName() {
      return this.bean.getEjbRelationshipRoleName();
   }

   public String getMultiplicity() {
      return this.bean.getMultiplicity();
   }

   public boolean getCascadeDelete() {
      return this.bean.getCascadeDelete() != null;
   }

   public RoleSource getRoleSource() {
      return new RoleSourceImpl(this.bean);
   }

   public CmrField getCmrField() {
      return null != this.bean.getCmrField() && null != this.bean.getCmrField().getCmrFieldName() ? new CmrFieldImpl(this.bean) : null;
   }
}
