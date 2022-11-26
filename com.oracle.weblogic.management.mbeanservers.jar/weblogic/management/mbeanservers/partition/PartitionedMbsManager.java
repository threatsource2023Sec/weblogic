package weblogic.management.mbeanservers.partition;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.jndi.WLContext;
import weblogic.jndi.internal.NonListableRef;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;

public abstract class PartitionedMbsManager {
   private static final Logger logger = Logger.getLogger(PartitionedMbsManager.class.getName());

   public void newMbs(AuthenticatedSubject subject, MBeanServer mbs) {
      this.newMbs(subject, this.getJndiName(), mbs);
   }

   private void newMbs(AuthenticatedSubject subject, String jndiName, MBeanServer mbs) {
      if (logger.isLoggable(Level.FINER)) {
         logger.entering("PartitionedMbsManager", "newMbs", new Object[]{jndiName});
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(subject);
      this.registerInJndi("DOMAIN", jndiName, mbs);
   }

   protected abstract String getJndiName();

   protected abstract PartitionedMbsRefObjFactory getPartitionedMbsRefObjFactory();

   private Object wrap(MBeanServer mbs, String pName) {
      return this.getPartitionedMbsRefObjFactory().createReference(mbs, pName);
   }

   protected void registerInJndi(String pName, String jndiName, MBeanServer mbs) {
      try {
         WLContext ctx = this.getCtxFor(pName);
         Object wrappedMbs = this.wrap(mbs, pName);
         ctx.bind(jndiName, wrappedMbs, true);
         String NEW_PREFIX = "weblogic/";

         assert jndiName.startsWith(NEW_PREFIX);

         ctx.bind(jndiName.substring(NEW_PREFIX.length()), new NonListableRef(wrappedMbs), true);
         if (logger.isLoggable(Level.FINER)) {
            logger.logp(Level.FINE, "PartitionedMbsManager", "registerInJndi", "partitionName = {0}, jndiName = {1}", new Object[]{pName, jndiName});
         }

      } catch (NamingException var7) {
         throw new RuntimeException(var7);
      }
   }

   protected void unregisterFromJndi(String pName, String jndiName) {
      try {
         WLContext ctx = this.getCtxFor(pName);
         ctx.unbind(jndiName);
         if (logger.isLoggable(Level.FINER)) {
            logger.logp(Level.FINE, "PartitionedMbsManager", "unregisterFromJndi", "partitionName = {0}, jndiName = {1}", new Object[]{pName, jndiName});
         }
      } catch (NamingException var4) {
         var4.printStackTrace();
      }

   }

   private WLContext getCtxFor(String pName) throws NamingException {
      Environment env = new Environment();
      env.setReplicateBindings(false);
      env.setCreateIntermediateContexts(true);
      env.setProviderURL("local://?partitionName=" + pName);
      return (WLContext)env.getContext();
   }
}
