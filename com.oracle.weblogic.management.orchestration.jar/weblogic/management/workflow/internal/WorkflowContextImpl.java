package weblogic.management.workflow.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.NoSuchElementException;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.command.WorkflowContext;

public class WorkflowContextImpl implements WorkflowContext, Serializable {
   private final WorkUnit workUnit;

   public WorkflowContextImpl(WorkUnit workUnit) {
      this.workUnit = workUnit;
   }

   WorkUnit getWorkUnit() {
      return this.workUnit;
   }

   public synchronized boolean isStored() {
      return this.workUnit.getStoreFile().exists();
   }

   protected WorkUnit findWorkUnitWithState(String key) {
      WorkUnit wu;
      for(wu = this.workUnit; !wu.getShareState().containsKey(key); wu = wu.getParentWorkUnit()) {
         if (wu.getParentWorkUnit() == null) {
            return null;
         }
      }

      return wu;
   }

   public Serializable getSharedState(String name) throws NoSuchElementException {
      WorkUnit wu = this.findWorkUnitWithState(name);
      if (wu == null) {
         throw new NoSuchElementException("No shared state instance for " + name);
      } else {
         if (wu != this.getWorkUnit()) {
            this.getWorkUnit().getWorkUnitsToStore().put(wu, wu);
         }

         return (Serializable)wu.getShareState().get(name);
      }
   }

   public String getWorkflowName() {
      return this.workUnit.getRootWorkflow().getName();
   }

   public String getId() {
      return this.workUnit.getId();
   }

   public String getWorkflowId() {
      return this.workUnit.getWorkflowId();
   }

   protected synchronized void storeOnlyThis() throws CorruptedStoreException {
      if (this.workUnit.getRootDirectory() == null) {
         throw new IllegalStateException("Workflow store directory is not set.");
      } else {
         if (!this.workUnit.getRootDirectory().exists()) {
            this.workUnit.getRootDirectory().mkdirs();
         }

         File tempFile;
         try {
            tempFile = File.createTempFile("svng" + this.workUnit.getId(), (String)null, this.workUnit.getRootDirectory());
         } catch (IOException var17) {
            throw new CorruptedStoreException("[" + this.workUnit.getId() + "] Can not create temp file!", this.workUnit.getRootDirectory().getPath(), var17);
         }

         try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));
            Throwable var3 = null;

            try {
               oos.writeObject(this.workUnit);
            } catch (Throwable var16) {
               var3 = var16;
               throw var16;
            } finally {
               if (oos != null) {
                  if (var3 != null) {
                     try {
                        oos.close();
                     } catch (Throwable var14) {
                        var3.addSuppressed(var14);
                     }
                  } else {
                     oos.close();
                  }
               }

            }
         } catch (IOException var19) {
            throw new CorruptedStoreException("[" + this.workUnit.getId() + "] Can not store state!", tempFile.getPath(), var19);
         }

         try {
            Files.deleteIfExists(this.workUnit.getStoreFile().toPath());
            Files.move(tempFile.toPath(), this.workUnit.getStoreFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
         } catch (IOException var15) {
            throw new CorruptedStoreException("Can not move (" + tempFile.getPath() + ") to (" + this.workUnit.getStoreFile().getPath() + ")", this.workUnit.getRootDirectory().getPath(), var15);
         }
      }
   }

   public synchronized void store() throws CorruptedStoreException {
      this.storeOnlyThis();
      Iterator var1 = this.getWorkUnit().getWorkUnitsToStore().keySet().iterator();

      while(var1.hasNext()) {
         WorkUnit wu = (WorkUnit)var1.next();
         wu.getContext().storeOnlyThis();
      }

   }

   public boolean isCancel() {
      return this.getWorkUnit().isCancel();
   }

   public synchronized void storeAll() throws CorruptedStoreException {
      this.storeOnlyThis();
      if (this.getWorkUnit() instanceof Workflow) {
         Workflow workflow = (Workflow)this.getWorkUnit();
         Iterator var2 = workflow.getWorkUnits().iterator();

         while(var2.hasNext()) {
            WorkUnit wu = (WorkUnit)var2.next();
            wu.getContext().storeAll();
         }
      }

   }
}
