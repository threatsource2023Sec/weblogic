package weblogic.ejb.container.deployer.mbimpl;

import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.CmrFieldBean;
import weblogic.j2ee.descriptor.EjbRelationBean;
import weblogic.j2ee.descriptor.EjbRelationshipRoleBean;
import weblogic.j2ee.descriptor.RelationshipsBean;
import weblogic.logging.Loggable;

public final class RelationshipsImpl implements Relationships {
   private String[] description;
   private final Map ejbEntityRefs = new HashMap();
   private final Map relations = new HashMap();

   public RelationshipsImpl(RelationshipsBean mb) throws WLDeploymentException {
      if (null != mb) {
         this.description = mb.getDescriptions();
         EjbRelationBean[] var2 = mb.getEjbRelations();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            EjbRelationBean rel = var2[var4];
            this.validateEjbRelationshipRoleNames(rel);
            String relName = rel.getEjbRelationName();
            if (relName == null || relName.length() == 0) {
               this.createEjbRelationName(rel);
               relName = rel.getEjbRelationName();
            }

            this.relations.put(relName, new RelationImpl(rel));
         }
      }

   }

   private void createEjbRelationName(EjbRelationBean ejbRelation) {
      EjbRelationshipRoleBean[] roles = ejbRelation.getEjbRelationshipRoles();
      String name1 = this.defaultRoleName(roles[0]);
      String name2 = this.defaultRoleName(roles[1]);
      if (name1.compareTo(name2) < 0) {
         ejbRelation.setEjbRelationName(name1 + "-" + name2);
      } else {
         if (name1.compareTo(name2) <= 0) {
            throw new AssertionError("Error: role names " + name1 + " and " + name2 + " are equal in relationship.");
         }

         ejbRelation.setEjbRelationName(name2 + "-" + name1);
      }

   }

   private void validateEjbRelationshipRoleNames(EjbRelationBean ejbRelation) throws WLDeploymentException {
      EjbRelationshipRoleBean[] roles = ejbRelation.getEjbRelationshipRoles();
      String roleName1 = roles[0].getEjbRelationshipRoleName();
      String roleName2 = roles[1].getEjbRelationshipRoleName();
      if (roleName1 == null) {
         roleName1 = this.defaultRoleName(roles[0]);
         roles[0].setEjbRelationshipRoleName(roleName1);
      }

      if (roleName2 == null) {
         roleName2 = this.defaultRoleName(roles[1]);
         roles[1].setEjbRelationshipRoleName(roleName2);
      }

      if (roleName1.compareTo(roleName2) == 0) {
         String ejbName = roles[0].getRelationshipRoleSource().getEjbName();
         CmrFieldBean cmrField = roles[0].getCmrField();
         String cmrName = cmrField == null ? "" : cmrField.getCmrFieldName();
         Loggable l = EJBLogger.logduplicateRelationshipRoleNameLoggable(ejbName, cmrName);
         throw new WLDeploymentException(l.getMessageText());
      }
   }

   private String defaultRoleName(EjbRelationshipRoleBean ejbRelRole) {
      CmrFieldBean cmrField = ejbRelRole.getCmrField();
      return cmrField == null ? ejbRelRole.getRelationshipRoleSource().getEjbName() : ejbRelRole.getRelationshipRoleSource().getEjbName() + "." + cmrField.getCmrFieldName();
   }

   public String[] getDescriptions() {
      return this.description;
   }

   public EjbEntityRef getEjbEntityRef(String remoteName) {
      return (EjbEntityRef)this.ejbEntityRefs.get(remoteName);
   }

   public Map getAllEjbEntityRefs() {
      return this.ejbEntityRefs;
   }

   public EjbRelation getEjbRelation(String relName) {
      return (EjbRelation)this.relations.get(relName);
   }

   public Map getAllEjbRelations() {
      return this.relations;
   }
}
