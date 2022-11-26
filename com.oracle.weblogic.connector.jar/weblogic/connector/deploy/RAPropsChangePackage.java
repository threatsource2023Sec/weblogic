package weblogic.connector.deploy;

import java.util.Map;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAException;

class RAPropsChangePackage implements ChangePackage {
   private RAInstanceManager raIM;
   private Map changedProperties;
   private String newJNDIName;

   public void setNewJNDIName(String newJNDIName) {
      this.newJNDIName = newJNDIName;
   }

   protected RAPropsChangePackage(RAInstanceManager raIM, Map changedProperties) {
      this.raIM = raIM;
      this.changedProperties = changedProperties;
   }

   public void rollback() throws RAException {
   }

   public void prepare() throws RAException {
      if (this.newJNDIName != null) {
         new UniversalResourceKey(this.newJNDIName, this.raIM.getVersionId());
         if (JNDIHandler.verifyJNDIName(this.newJNDIName)) {
            String exMsg = Debug.getExceptionJndiNameAlreadyBound(this.newJNDIName);
            throw new RAException(exMsg);
         }
      }

   }

   public void activate() throws RAException {
      if (this.changedProperties != null && !this.changedProperties.isEmpty()) {
         Object raObj = this.raIM.getResourceAdapter();
         Utils.setProperties(this.raIM, raObj, this.changedProperties.values(), this.raIM.getRAValidationInfo().getRAPropSetterTable());
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Updating ResourceAdapter " + this.raIM.getJndiName() + this.changedProperties);
         }

         this.raIM.getBeanValidator().validate(raObj, "ResourceAdapter module '" + this.raIM.getModuleName() + "' with JNDI '" + this.raIM.getJndiName() + "'");
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Validated ResourceAdapter " + this.raIM.getJndiName());
         }
      }

      if (this.newJNDIName != null) {
         this.raIM.rebindRA(this.newJNDIName);
      }

   }
}
