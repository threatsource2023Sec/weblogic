package weblogic.connector.deploy;

import java.security.AccessController;
import java.util.Map;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAException;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.PropSetterTable;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class AdminObjectChangePackage implements ChangePackage {
   private RAInstanceManager raIM = null;
   private ConnectorModuleChangePackage.ChangeType changeType;
   private Map changedProperties;
   private AdminObjInfo adminInfo = null;

   protected AdminObjectChangePackage(RAInstanceManager raIM, AdminObjInfo adminInfo, ConnectorModuleChangePackage.ChangeType changeType, Map changedProperties) {
      this.raIM = raIM;
      this.adminInfo = adminInfo;
      this.changeType = changeType;
      this.changedProperties = changedProperties;
   }

   public void rollback() throws RAException {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         this.raIM.removeAdminObject(this.adminInfo);
      }

   }

   public void prepare() throws RAException {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.raIM.createAdminObject(this.adminInfo, kernelId);
      }

   }

   public void activate() throws RAException {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         this.raIM.bindAdminObject(this.adminInfo.getKey());
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Binding new Admin Object " + this.adminInfo.getJndiName());
         }
      } else if (ConnectorModuleChangePackage.ChangeType.REMOVE.equals(this.changeType)) {
         this.raIM.removeAdminObject(this.adminInfo);
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Unbinding Admin Object " + this.adminInfo.getJndiName());
         }
      } else if (this.changedProperties != null && !this.changedProperties.isEmpty()) {
         Object aoObj = this.raIM.getAdminObjectInstance(this.adminInfo.getJndiName());
         PropSetterTable propSetterTable = this.raIM.getRAValidationInfo().getAdminPropSetterTable(this.adminInfo.getInterface(), this.adminInfo.getAdminObjClass());
         Utils.setProperties(this.raIM, aoObj, this.changedProperties.values(), propSetterTable);
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Updating Admin Object " + this.adminInfo.getJndiName() + this.changedProperties);
         }

         this.raIM.getBeanValidator().validate(aoObj, "AdminObject instance '" + this.adminInfo.getKey() + "'");
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Validated Admin Object " + this.adminInfo.getJndiName());
         }
      }

   }

   public String toString() {
      return this.changeType.toString() + " " + this.adminInfo.getJndiName();
   }
}
