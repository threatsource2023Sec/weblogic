package weblogic.ejb.container.persistence;

import java.util.Arrays;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.RoleSource;

public final class EjbRelationshipRoleImpl implements EjbRelationshipRole {
   protected String[] description;
   protected String ejbRelationshipRoleName;
   protected String multiplicity;
   protected boolean cascadeDelete;
   protected RoleSource roleSource;
   protected CmrField cmrField;

   public void setDescriptions(String[] d) {
      this.description = d;
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public void setName(String ejbRelationshipRoleName) {
      this.ejbRelationshipRoleName = ejbRelationshipRoleName;
   }

   public String getName() {
      return this.ejbRelationshipRoleName;
   }

   public void setMultiplicity(String multiplicity) {
      this.multiplicity = multiplicity;
   }

   public String getMultiplicity() {
      return this.multiplicity;
   }

   public void setCascadeDelete(boolean cascadeDelete) {
      this.cascadeDelete = cascadeDelete;
   }

   public boolean getCascadeDelete() {
      return this.cascadeDelete;
   }

   public void setRoleSource(RoleSource roleSource) {
      this.roleSource = roleSource;
   }

   public RoleSource getRoleSource() {
      return this.roleSource;
   }

   public void setCmrField(CmrField cmrField) {
      this.cmrField = cmrField;
   }

   public CmrField getCmrField() {
      return this.cmrField;
   }

   public String toString() {
      return "[EjbRelationshipRoleImpl name: " + this.ejbRelationshipRoleName + " description: " + Arrays.toString(this.description) + " multiplicity: " + this.multiplicity + " " + (this.roleSource == null ? "[RoleSource null]" : this.roleSource.toString()) + (this.cmrField == null ? "[CmrField null]" : this.cmrField.toString()) + "]";
   }
}
