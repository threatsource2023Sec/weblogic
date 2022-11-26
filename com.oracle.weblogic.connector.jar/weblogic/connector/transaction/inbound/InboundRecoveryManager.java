package weblogic.connector.transaction.inbound;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAInboundException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.internal.IgnoreXAResource;

public class InboundRecoveryManager {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private HashMap activationSpec2ResName = new HashMap();

   public synchronized void onActivateEndpoint(RAInstanceManager raIM, ActivationSpec activationSpec, String endpointName) throws SystemException {
      XAResource[] reses = null;
      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(raIM.getClassloader());
         Debug.xaIn("set context classloader to adapter's CL: " + raIM.getClassloader());
         reses = raIM.getAdapterLayer().getXAResources(raIM.getResourceAdapter(), new ActivationSpec[]{activationSpec}, kernelId);
      } catch (ResourceException var11) {
         Debug.xaIn("Unable to get XAResource from adapter due to exception " + var11 + "; recovery is disabled for endpoint " + endpointName);
      } finally {
         Debug.xaIn("restore context classloader to original CL: " + cl);
         Thread.currentThread().setContextClassLoader(cl);
      }

      if (reses != null && reses.length == 1 && reses[0] != null) {
         XAResource xar = reses[0];
         if (xar instanceof IgnoreXAResource) {
            Debug.xaIn("Adatper returns an IgnoreXAResource; skip recovery for endpoint " + endpointName);
         } else {
            String resName = raIM.getJndiNameWithPartitionName() + "_" + endpointName;
            Debug.xaIn("Register for recovery: resource name: " + resName + "; XAResource: " + xar + "; activationSpec: " + activationSpec);
            Hashtable props = new Hashtable();
            props.put("weblogic.transaction.registration.type", "dynamic");
            props.put("weblogic.transaction.registration.settransactiontimeout", "true");
            ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(resName, xar, props);
            this.activationSpec2ResName.put(activationSpec, resName);
         }
      } else {
         Debug.xaIn("Adatper doesn't support recovery for endpoint " + endpointName);
      }

   }

   public synchronized void onDeActivateEndpoint(ActivationSpec activationSpec) throws SystemException {
      String resName = (String)this.activationSpec2ResName.remove(activationSpec);
      this.unregister(activationSpec, resName);
   }

   public synchronized void onRAStop(RAInboundException raInboundException) {
      Iterator var2 = this.activationSpec2ResName.keySet().iterator();

      while(var2.hasNext()) {
         ActivationSpec spec = (ActivationSpec)var2.next();

         try {
            String resName = (String)this.activationSpec2ResName.get(spec);
            this.unregister(spec, resName);
         } catch (Exception var5) {
            Utils.consolidateException(raInboundException, var5);
         }
      }

      this.activationSpec2ResName.clear();
   }

   private void unregister(ActivationSpec activationSpec, String resName) throws SystemException {
      if (resName != null) {
         Debug.xaIn("Unregister: resource name: " + resName + "; activationSpec: " + activationSpec);
         ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(resName);
      }

   }
}
