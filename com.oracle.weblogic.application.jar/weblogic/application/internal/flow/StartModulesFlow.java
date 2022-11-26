package weblogic.application.internal.flow;

import java.util.List;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Module;
import weblogic.management.DeploymentException;
import weblogic.utils.ErrorCollectionException;

public final class StartModulesFlow extends BaseFlow {
   private final ModuleStateDriver driver;

   public StartModulesFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
      this.driver = new ModuleStateDriver(this.appCtx);
   }

   public void activate() throws DeploymentException {
      Module[] modules = this.appCtx.getApplicationModules();
      if (this.isParallelActivateEnabled()) {
         List moduleGroups = this.partitionModules(modules);
         boolean isFirstListEmpty = ((Module[])moduleGroups.get(0)).length == 0;
         boolean isPartitionConcurrent = isFirstListEmpty;
         int startingIndex = isFirstListEmpty ? 1 : 0;

         for(int index = startingIndex; index < moduleGroups.size(); ++index) {
            Module[] moduleGroup = (Module[])moduleGroups.get(index);
            if (isPartitionConcurrent) {
               this.driver.parallelStart(moduleGroup);
            } else {
               this.driver.start(moduleGroup);
            }

            isPartitionConcurrent = !isPartitionConcurrent;
         }
      } else {
         this.driver.start(modules);
      }

   }

   public void start(String[] uris) throws DeploymentException {
      Module[] startingModules = this.appCtx.getStartingModules();

      try {
         this.driver.start(startingModules);
      } catch (Throwable var9) {
         ErrorCollectionException ece = new ErrorCollectionException(var9);

         try {
            this.driver.deactivate(startingModules);
         } catch (Throwable var8) {
            ece.add(var8);
         }

         try {
            this.driver.unprepare(startingModules);
         } catch (Throwable var7) {
            ece.add(var7);
         }

         try {
            this.driver.destroy(startingModules);
         } catch (Throwable var6) {
            ece.add(var6);
         }

         this.throwAppException(ece);
      }

   }
}
