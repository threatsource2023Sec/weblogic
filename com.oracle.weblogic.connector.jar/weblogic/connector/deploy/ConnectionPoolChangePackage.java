package weblogic.connector.deploy;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.RAOutboundManager;

class ConnectionPoolChangePackage implements ChangePackage {
   private RAInstanceManager raIM;
   private OutboundInfo outboundInfo;
   private OutboundInfo oldOutboundInfo;
   private Properties poolParamProperties;
   private Properties loggingProperties;
   private Map changedProperties;
   private ConnectorModuleChangePackage.ChangeType changeType;
   private ConnectionPool pool = null;
   private boolean poolFailed = false;
   private boolean reCreateFailedPool = false;
   private UniversalResourceKey key;

   protected ConnectionPoolChangePackage(RAInstanceManager raIM, OutboundInfo outboundInfo, Properties poolProps, Properties loggingProperties, Map changedProperties, ConnectorModuleChangePackage.ChangeType changeType) {
      this.raIM = raIM;
      this.outboundInfo = outboundInfo;
      String jndi = outboundInfo.getJndiName();
      this.oldOutboundInfo = raIM.getRAOutboundManager().getOutboundInfo(jndi);
      this.poolParamProperties = poolProps;
      this.loggingProperties = loggingProperties;
      this.changedProperties = changedProperties;
      this.changeType = changeType;
      this.key = new UniversalResourceKey(outboundInfo.getJndiName(), raIM.getVersionId());
   }

   public void prepare() throws RAException {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         if (RAOutboundManager.hasValiadtionOrRuntimeError(this.outboundInfo)) {
            this.poolFailed = true;
            if (this.raIM.isDeployAsAWhole()) {
               throw new RAException("internal error: new configuration of pool " + this.outboundInfo.getJndiName() + " still have error. dynamic update should fail already during validation check since deploy-as-a-whole is true.");
            }
         } else {
            this.preparePool();
         }
      } else if (this.changeType.equals(ConnectorModuleChangePackage.ChangeType.UPDATE) && this.raIM.getRAOutboundManager().isFailedPool(this.outboundInfo.getJndiName())) {
         if (RAOutboundManager.hasValiadtionOrRuntimeError(this.outboundInfo)) {
            this.poolFailed = true;
            if (this.raIM.isDeployAsAWhole()) {
               throw new RAException("internal error: new configuration of pool " + this.outboundInfo.getJndiName() + " still have error. dynamic update should fail already during validation check since deploy-as-a-whole is true.");
            }
         } else {
            this.reCreateFailedPool = true;
            this.raIM.getRAOutboundManager().shutdownAndCleanupFailedPool(this.oldOutboundInfo.getJndiName());
            this.preparePool();
         }
      }

   }

   public void activate() {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         if (!this.poolFailed) {
            this.activatePool();
         }
      } else if (this.changeType.equals(ConnectorModuleChangePackage.ChangeType.REMOVE)) {
         this.shutdownAdnCleanupPool();
      } else if (!this.poolFailed) {
         if (this.reCreateFailedPool) {
            this.activatePool();
         } else {
            this.updateExistOkPool();
         }
      }

      if (this.poolFailed) {
         this.raIM.getRAOutboundManager().shutdownAndCleanupNormalPool(this.outboundInfo);
         this.raIM.getRAOutboundManager().markPoolAsFailed(this.outboundInfo);
         this.raIM.getRAOutboundManager().setupFailedPool(this.outboundInfo);
      }

   }

   public void rollback() {
      if (ConnectorModuleChangePackage.ChangeType.NEW.equals(this.changeType)) {
         this.raIM.getRAOutboundManager().shutdownAndCleanupNormalPool(this.outboundInfo);
      } else if (this.changeType.equals(ConnectorModuleChangePackage.ChangeType.UPDATE) && this.reCreateFailedPool) {
         this.raIM.getRAOutboundManager().markPoolAsFailed(this.oldOutboundInfo);
         this.raIM.getRAOutboundManager().shutdownAndCleanupNormalPool(this.outboundInfo);
         this.raIM.getRAOutboundManager().setupFailedPool(this.oldOutboundInfo);
      }

   }

   private void preparePool() throws RAOutboundException {
      try {
         this.raIM.getRAOutboundManager().createConnectionFactoryInternal(this.outboundInfo);
         this.raIM.getRAOutboundManager().preparePoolInternal(this.key);
      } catch (Throwable var2) {
         this.poolFailed = true;
         this.raIM.getRAOutboundManager().recordPoolCreationError(this.outboundInfo, var2);
         if (this.raIM.isDeployAsAWhole()) {
            this.raIM.getRAOutboundManager().rethrowRAOutboundException(var2);
         }
      }

   }

   private void shutdownAdnCleanupPool() {
      if (this.raIM.getRAOutboundManager().isFailedPool(this.outboundInfo.getJndiName())) {
         this.raIM.getRAOutboundManager().shutdownAndCleanupFailedPool(this.outboundInfo.getJndiName());
      } else {
         this.raIM.getRAOutboundManager().shutdownAndCleanupNormalPool(this.outboundInfo);
      }

   }

   private void updateExistOkPool() {
      try {
         this.pool = this.raIM.getRAOutboundManager().getConnectionPool(this.outboundInfo.getJndiName());
         if (this.poolParamProperties != null && !this.poolParamProperties.isEmpty()) {
            this.pool.applyPoolParamChanges(this.poolParamProperties);
         }

         if (this.loggingProperties != null && !this.loggingProperties.isEmpty()) {
            this.pool.applyLoggingChanges(this.loggingProperties, this.outboundInfo);
         }

         Map dynamicProperty = this.filteringNoDynamicProperty(this.changedProperties);
         if (!dynamicProperty.isEmpty()) {
            Object mcfObj = this.pool.getManagedConnectionFactory();
            Utils.setProperties(this.raIM, mcfObj, dynamicProperty.values(), this.raIM.getRAValidationInfo().getConnectionFactoryPropSetterTable(this.outboundInfo.getCFInterface()));
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("Updating ManagedConnectionFactory " + this.pool.getKey() + this.changedProperties);
            }

            this.raIM.getBeanValidator().validate(mcfObj, "Outbound Connection Pool '" + this.pool.getKey() + "'");
            if (Debug.isDeploymentEnabled()) {
               Debug.deployment("Validated ManagedConnectionFactory " + this.pool.getKey());
            }
         }

         this.raIM.getRAOutboundManager().updateOutBoundInfo(this.outboundInfo.getJndiName(), this.outboundInfo);
      } catch (Throwable var3) {
         this.poolFailed = true;
         this.raIM.getRAOutboundManager().recordPoolCreationError(this.outboundInfo, var3);
      }

   }

   private void activatePool() {
      try {
         this.raIM.getRAOutboundManager().activatePoolInternal(this.key);
         if (Debug.isDeploymentEnabled()) {
            Debug.deployment("Active Connection Pool " + this.key);
         }
      } catch (Throwable var2) {
         this.poolFailed = true;
         this.raIM.getRAOutboundManager().recordPoolCreationError(this.outboundInfo, var2);
      }

   }

   private Map filteringNoDynamicProperty(Map in) {
      Map result = new HashMap();
      if (in != null) {
         Set entrySet = in.entrySet();
         Iterator var4 = entrySet.iterator();

         while(var4.hasNext()) {
            Map.Entry entry = (Map.Entry)var4.next();
            if (((ConfigPropInfo)entry.getValue()).isDynamicUpdatable()) {
               result.put(entry.getKey(), entry.getValue());
            }
         }
      }

      return result;
   }

   public String toString() {
      return this.changeType.toString() + " " + this.outboundInfo.getJndiName();
   }
}
