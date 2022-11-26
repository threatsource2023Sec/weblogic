package weblogic.ejb.container.deployer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.naming.Environment;
import weblogic.cluster.PartitionAwareSenderManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.jndi.WLContext;
import weblogic.jndi.internal.JNDIHelper;

final class EjbJndiService {
   private static final DebugLogger debugLogger;
   private final WLContext ctx;
   private final WLContext javaGlobalCtx;
   private final Context javaAppCtx;
   private final Context javaModuleCtx;
   private final List boundEnvs = new ArrayList();
   private final List boundGlobalNames = new ArrayList();
   private final List boundAppNames = new ArrayList();
   private final List boundModuleNames = new ArrayList();
   private final List allBoundNames = new ArrayList();

   EjbJndiService(boolean clientsOnSameServer, Environment eBuilder) throws NamingException {
      weblogic.jndi.Environment env = new weblogic.jndi.Environment();
      env.setCreateIntermediateContexts(true);
      this.ctx = (WLContext)env.getInitialContext();
      this.ctx.addToEnvironment("weblogic.jndi.replicateBindings", String.valueOf(!clientsOnSameServer));
      this.javaGlobalCtx = (WLContext)eBuilder.getGlobalNSContext();
      this.javaAppCtx = eBuilder.getAppNSContext();
      this.javaModuleCtx = eBuilder.getModuleNSContext();
   }

   void bind(String name, Object obj, boolean replicatedBind, boolean crossPartitionVisible) throws NamingException {
      this.ctx.addToEnvironment("weblogic.jndi.replicateBindings", String.valueOf(replicatedBind));
      this.ctx.bind(name, obj, crossPartitionVisible);
      this.boundEnvs.add(new BoundEnv(name, replicatedBind));
   }

   void bindInGlobalNS(String name, Object obj, boolean crossPartitionVisible) throws NamingException {
      this.javaGlobalCtx.bind(this.removeNamePrefix(name), obj, crossPartitionVisible);
      this.boundGlobalNames.add(name);
   }

   void bindInAppNS(String name, Object obj) throws NamingException {
      this.javaAppCtx.bind(this.removeNamePrefix(name), obj);
      this.boundAppNames.add(name);
   }

   void bindInModuleNS(String name, Object obj) throws NamingException {
      this.javaModuleCtx.bind(this.removeNamePrefix(name), obj);
      this.boundModuleNames.add(name);
   }

   private String removeNamePrefix(String name) {
      return !name.startsWith("java:") ? name : name.substring(name.indexOf("/") + 1, name.length());
   }

   private void unbind(String name, Context ctx) {
      if (name != null && ctx != null) {
         try {
            JNDIHelper.unbindWithDestroySubcontexts(name, ctx);
         } catch (NamingException var4) {
            EJBLogger.logUnbindFailureWarning(name, var4.toString());
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error unbinding " + name, var4);
            }
         }

      }
   }

   private void unbindByNames(List names, Context context) {
      if (names != null && context != null) {
         for(int i = names.size() - 1; i > -1; --i) {
            this.unbind(this.removeNamePrefix((String)names.get(i)), context);
         }

         names.clear();

         try {
            context.close();
         } catch (NamingException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error closing context after unbind", var4);
            }
         }

      }
   }

   private void unbindByEnvs(List envs, Context context) {
      if (envs != null && context != null) {
         for(int i = envs.size() - 1; i > -1; --i) {
            BoundEnv benv = (BoundEnv)envs.get(i);
            String name = this.removeNamePrefix(benv.name);

            try {
               context.addToEnvironment("weblogic.jndi.replicateBindings", String.valueOf(benv.replicatedBind));
            } catch (NamingException var8) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error in unbind", var8);
               }
            }

            this.unbind(name, context);
         }

         envs.clear();

         try {
            context.close();
         } catch (NamingException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Error closing context after unbind", var7);
            }
         }

      }
   }

   void unbindAll() {
      if (PartitionAwareSenderManager.theOne() != null) {
         PartitionAwareSenderManager.theOne().startBulkUpdate();
      }

      this.unbindByNames(this.boundModuleNames, this.javaModuleCtx);
      this.unbindByNames(this.boundAppNames, this.javaAppCtx);
      this.unbindByNames(this.boundGlobalNames, this.javaGlobalCtx);
      this.unbindByEnvs(this.boundEnvs, this.ctx);
      if (PartitionAwareSenderManager.theOne() != null) {
         PartitionAwareSenderManager.theOne().endBulkUpdate();
      }

   }

   boolean isNameBound(String name) {
      if (this.allBoundNames.isEmpty()) {
         this.allBoundNames.addAll(this.boundGlobalNames);
         this.allBoundNames.addAll(this.boundAppNames);
         this.allBoundNames.addAll(this.boundModuleNames);
         Iterator var2 = this.boundEnvs.iterator();

         while(var2.hasNext()) {
            BoundEnv be = (BoundEnv)var2.next();
            this.allBoundNames.add(be.name);
         }
      }

      return this.allBoundNames.contains(name);
   }

   void nonRepBindInGlobalNS(String portableName, Object obj, boolean crossPartitionVisible) throws NamingException {
      Object orgRep = this.javaGlobalCtx.getEnvironment().get("weblogic.jndi.replicateBindings");
      this.javaGlobalCtx.addToEnvironment("weblogic.jndi.replicateBindings", "false");
      this.javaGlobalCtx.bind(this.removeNamePrefix(portableName), obj, crossPartitionVisible);
      this.boundGlobalNames.add(portableName);
      this.javaGlobalCtx.addToEnvironment("weblogic.jndi.replicateBindings", orgRep == null ? "true" : orgRep.toString());
   }

   void nonRepBindInAppNS(String name, Object obj) throws NamingException {
      Object orgRep = this.javaAppCtx.getEnvironment().get("weblogic.jndi.replicateBindings");
      this.javaAppCtx.addToEnvironment("weblogic.jndi.replicateBindings", "false");
      this.javaAppCtx.bind(this.removeNamePrefix(name), obj);
      this.boundAppNames.add(name);
      this.javaAppCtx.addToEnvironment("weblogic.jndi.replicateBindings", orgRep == null ? "true" : orgRep.toString());
   }

   void nonRepBindInModuleNS(String name, Object obj) throws NamingException {
      Object orgRep = this.javaModuleCtx.getEnvironment().get("weblogic.jndi.replicateBindings");
      this.javaModuleCtx.addToEnvironment("weblogic.jndi.replicateBindings", "false");
      this.javaModuleCtx.bind(this.removeNamePrefix(name), obj);
      this.boundModuleNames.add(name);
      this.javaModuleCtx.addToEnvironment("weblogic.jndi.replicateBindings", orgRep == null ? "true" : orgRep.toString());
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }

   private static final class BoundEnv {
      final String name;
      final boolean replicatedBind;

      BoundEnv(String name, boolean replicatedBind) {
         this.name = name;
         this.replicatedBind = replicatedBind;
      }
   }
}
