package weblogic.application.internal.flow;

import java.util.Iterator;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.internal.FlowContext;
import weblogic.application.internal.ModuleAttributes;
import weblogic.application.naming.JavaAppNamingHelper;
import weblogic.jndi.Environment;
import weblogic.management.DeploymentException;

public class JavaAppNamingFlow extends BaseFlow {
   public JavaAppNamingFlow(FlowContext appCtx) {
      super(appCtx);
   }

   public void activate() throws DeploymentException {
      if (this.needsAppEnvContextCopy()) {
         this.appCtx.setAppEnvContextCopy();

         try {
            Context appNameHolderCtx = this.getOrCreateSubContext(this.getGlobalCtx(), JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationName()));
            if (this.checkIfAppAlreadyBound(appNameHolderCtx)) {
               if (this.isDebugEnabled()) {
                  this.debug("Application " + this.appCtx.getApplicationId() + " is already bound to the global java app context");
               }

               return;
            }

            Context javaAppNamingCtx = this.appCtx.getEnvContext();
            appNameHolderCtx.addToEnvironment("weblogic.jndi.createIntermediateContexts", "true");
            appNameHolderCtx.addToEnvironment("weblogic.jndi.replicateBindings", "false");
            if (this.isDebugEnabled()) {
               this.debug("Binding application to jndi under name: " + this.appCtx.getApplicationId());
            }

            appNameHolderCtx.bind(JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationId()), javaAppNamingCtx);
         } catch (Exception var3) {
            throw new DeploymentException(var3);
         }
      }

   }

   private boolean checkIfAppAlreadyBound(Context appNameHolderCtx) {
      try {
         appNameHolderCtx.lookup(JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationId()));
         return true;
      } catch (NamingException var3) {
         return false;
      }
   }

   public void adminToProduction() throws DeploymentException {
      if (this.appCtx.needsAppEnvContextCopy()) {
         try {
            Context appNameHolderCtx = this.getOrCreateSubContext(this.getGlobalCtx(), JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationName()));
            if (this.isDebugEnabled()) {
               this.debug("Identifying the active version of the application " + this.appCtx.getApplicationName() + " as " + this.appCtx.getApplicationId() + " in application naming environment");
            }

            appNameHolderCtx.rebind("__WL_Active_Application_Version", this.appCtx.getApplicationId());
         } catch (Exception var2) {
            throw new DeploymentException(var2);
         }
      }

   }

   public void forceProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      if (this.appCtx.needsAppEnvContextCopy()) {
         try {
            Context appNameHolderCtx = this.getOrCreateSubContext(this.getGlobalCtx(), JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationName()));

            try {
               String activeVersionId = (String)appNameHolderCtx.lookup("__WL_Active_Application_Version");
               if (this.appCtx.getApplicationId().equals(activeVersionId)) {
                  if (this.isDebugEnabled()) {
                     this.debug("Unmarking the active version of the application " + this.appCtx.getApplicationName() + " currently identified as " + this.appCtx.getApplicationId() + " in application naming environment");
                  }

                  appNameHolderCtx.unbind("__WL_Active_Application_Version");
               }
            } catch (NamingException var4) {
            }
         } catch (Exception var5) {
            throw new DeploymentException(var5);
         }
      }

   }

   public void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) throws DeploymentException {
      this.forceProductionToAdmin(barrier);
   }

   public void deactivate() throws DeploymentException {
      if (this.appCtx.needsAppEnvContextCopy()) {
         try {
            Context appNameHolderCtx = this.getOrCreateSubContext(this.getGlobalCtx(), JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationName()));
            if (this.isDebugEnabled()) {
               this.debug("Un-binding application from jndi: " + this.appCtx.getApplicationId());
            }

            appNameHolderCtx.unbind(JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationId()));
            NamingEnumeration contents = appNameHolderCtx.list("");
            if (!contents.hasMoreElements() && this.isDebugEnabled()) {
               this.debug("No more contexts under " + this.appCtx.getApplicationName() + ". Unbinding it.");
               Context ctx = this.getGlobalCtx();
               ctx.unbind(JavaAppNamingHelper.indivisableJndiApplicationName(this.appCtx.getApplicationName()));
            }
         } catch (Exception var4) {
            throw new DeploymentException(var4);
         }
      }

   }

   protected Context getGlobalCtx() throws NamingException {
      Context rootCtx = (new Environment()).getInitialContext();
      return (Context)rootCtx.lookup("__WL_GlobalJavaApp");
   }

   private Context getOrCreateSubContext(Context ctx, String subContextName) throws NamingException {
      try {
         return (Context)ctx.lookup(subContextName);
      } catch (NamingException var4) {
         if (this.isDebugEnabled()) {
            this.debug("Creating sub-context " + subContextName);
         }

         return ctx.createSubcontext(subContextName);
      }
   }

   private boolean needsAppEnvContextCopy() {
      Iterator var1 = this.appCtx.getModuleManager().getAllAttributes().iterator();

      ModuleAttributes attribute;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         attribute = (ModuleAttributes)var1.next();
      } while(!attribute.needsAppEnvContextCopy());

      return true;
   }
}
