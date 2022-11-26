package weblogic.ejb.container.deployer.mbimpl;

import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.j2ee.descriptor.EjbRelationshipRoleBean;

class CmrFieldImpl implements CmrField {
   private final EjbRelationshipRoleBean bean;

   CmrFieldImpl(EjbRelationshipRoleBean mb) {
      this.bean = mb;
   }

   public String[] getDescriptions() {
      return this.bean.getCmrField().getDescriptions();
   }

   public String getName() {
      return this.bean.getCmrField().getCmrFieldName();
   }

   public String getType() {
      return this.bean.getCmrField().getCmrFieldType();
   }
}
