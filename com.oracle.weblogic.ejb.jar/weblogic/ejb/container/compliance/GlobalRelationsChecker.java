package weblogic.ejb.container.compliance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.persistence.spi.CmrField;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.persistence.spi.RoleSource;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public final class GlobalRelationsChecker extends BaseComplianceChecker {
   private final Relationships relationships;

   public GlobalRelationsChecker(DeploymentInfo di) {
      this.relationships = di.getRelationships();
   }

   public void checkRelations() throws ErrorCollectionException {
      this.checkNoDupCmrFields();
   }

   private void checkNoDupCmrFields() throws ErrorCollectionException {
      ErrorCollectionException errs = new ErrorCollectionException();
      Map srcMap = new HashMap();
      Iterator relations = this.relationships.getAllEjbRelations().values().iterator();

      while(relations.hasNext()) {
         EjbRelation rel = (EjbRelation)relations.next();
         Iterator roles = rel.getAllEjbRelationshipRoles().iterator();
         EjbRelationshipRole role1 = (EjbRelationshipRole)roles.next();
         EjbRelationshipRole role2 = (EjbRelationshipRole)roles.next();
         RoleSource src1 = role1.getRoleSource();
         RoleSource src2 = role2.getRoleSource();
         CmrField field1 = role1.getCmrField();
         CmrField field2 = role2.getCmrField();
         this.dupCMRCheck(srcMap, src1, field1, rel, errs);
         if (!this.isSymmetric(src1, field1, src2, field2)) {
            this.dupCMRCheck(srcMap, src2, field2, rel, errs);
         }
      }

      if (!errs.isEmpty()) {
         throw errs;
      }
   }

   private void dupCMRCheck(Map srcMap, RoleSource roleSource, CmrField cmrField, EjbRelation rel, ErrorCollectionException errs) {
      if (cmrField != null) {
         String encodedSrcName = this.getEncodedName(roleSource);
         String fName = cmrField.getName();
         if (srcMap.containsKey(encodedSrcName)) {
            Set cmrFields = (Set)srcMap.get(encodedSrcName);
            if (cmrFields.contains(fName)) {
               errs.add(new ComplianceException(this.getFmt().DUPLICATE_CMR_FIELD(rel.getEjbRelationName() + " <relationship-role-source>: " + roleSource.getEjbName() + " <cmr-field>: " + fName), new DescriptorErrorInfo("<relationship-role-source>", rel.getEjbRelationName(), roleSource.getEjbName())));
            } else {
               cmrFields.add(fName);
            }
         } else {
            Set cmrFields = new HashSet();
            srcMap.put(encodedSrcName, cmrFields);
            cmrFields.add(fName);
         }

      }
   }

   private String getEncodedName(RoleSource rs) {
      return "EJBNAME_" + rs.getEjbName();
   }

   private boolean isSymmetric(RoleSource src1, CmrField field1, RoleSource src2, CmrField field2) {
      boolean isBidirectional = field1 != null && field2 != null;
      return src1.getEjbName().equals(src2.getEjbName()) && isBidirectional && field1.getName().equals(field2.getName());
   }
}
