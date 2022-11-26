package weblogic.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleException;

public class PersistenceUnitRegistryInitializer {
   private final List prepareTasks = new ArrayList();

   public static synchronized PersistenceUnitRegistryInitializer getInstance(ApplicationContextInternal appCtx) {
      PersistenceUnitRegistryInitializer obj = (PersistenceUnitRegistryInitializer)appCtx.getUserObject(PersistenceUnitRegistryInitializer.class.getName());
      if (obj == null) {
         obj = new PersistenceUnitRegistryInitializer();
         appCtx.putUserObject(PersistenceUnitRegistryInitializer.class.getName(), obj);
      }

      return obj;
   }

   public synchronized void addPersistenceUnitRegistryPrepareTask(PersistenceUnitRegistryPrepareTask task) {
      this.prepareTasks.add(task);
   }

   public synchronized void setupPersistenceUnitRegistries() throws ModuleException {
      Iterator var1 = this.prepareTasks.iterator();

      while(var1.hasNext()) {
         PersistenceUnitRegistryPrepareTask task = (PersistenceUnitRegistryPrepareTask)var1.next();
         task.execute();
      }

      this.prepareTasks.clear();
   }

   public interface PersistenceUnitRegistryPrepareTask {
      void execute() throws ModuleException;
   }
}
