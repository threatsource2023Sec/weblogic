package weblogic.management.workflow.internal;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import weblogic.management.workflow.CommandFailedException;
import weblogic.management.workflow.CorruptedStoreException;
import weblogic.management.workflow.WorkflowStateChangeListener;

public abstract class Workflow extends WorkUnit implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String name;
   private transient List workUnits = new ArrayList();
   private final List workUnitsIds = new ArrayList();
   private boolean finished;
   private boolean revert = false;
   private transient int sequence = -1;

   public Workflow(String id, String name, FailureDecider failureDecider, WorkUnit parentWorkUnit, File storeDirectory) {
      super(id, (CommandProvider)null, failureDecider, parentWorkUnit, storeDirectory);
      this.name = name;
   }

   public void setPostLoadAttributes(WorkUnit parentWorkUnit, File storeFile) {
      super.setPostLoadAttributes(parentWorkUnit, storeFile);
      if (!this.workUnits.isEmpty() && this.workUnits.size() != this.workUnitsIds.size()) {
         throw new CorruptedStoreException(OrchestrationLogger.getMissingWorkUnits(this.getId()), storeFile.getPath());
      } else {
         if (this.workUnits.isEmpty()) {
            Iterator var3 = this.workUnitsIds.iterator();

            while(var3.hasNext()) {
               IdPair subId = (IdPair)var3.next();
               File file = getStoreFileForId(this.getRootDirectory(), subId.id);
               WorkUnit wu = load(file);
               if (!wu.getValidationId().equals(subId.validationId)) {
                  throw new CorruptedStoreException(OrchestrationLogger.getCorruptedStorage(this.getId()), file.getPath());
               }

               wu.setPostLoadAttributes(this, file);
               this.workUnits.add(wu);
            }
         }

      }
   }

   public void add(WorkUnit wu) {
      this.workUnits.add(wu);
      this.workUnitsIds.add(new IdPair(wu));
   }

   protected List getWorkUnits() {
      return Collections.unmodifiableList(this.workUnits);
   }

   public List getSubProgresses() {
      List result = new ArrayList(this.workUnits.size());
      result.addAll(this.workUnits);
      return Collections.unmodifiableList(result);
   }

   public void setCancel(boolean cancel) throws IllegalStateException {
      IllegalStateException ise = null;

      try {
         super.setCancel(cancel);
      } catch (IllegalStateException var7) {
         ise = var7;
      }

      Iterator var3 = this.getWorkUnits().iterator();

      while(var3.hasNext()) {
         WorkUnit workUnit = (WorkUnit)var3.next();

         try {
            workUnit.setCancel(cancel);
         } catch (IllegalStateException var6) {
            ise = var6;
         }
      }

      if (ise != null) {
         throw ise;
      }
   }

   public String getName() {
      return this.name;
   }

   protected Collection collectAllWorkUnits(Collection workUnits) {
      if (workUnits == null) {
         workUnits = new ArrayList();
      }

      ((Collection)workUnits).addAll(this.getWorkUnits());
      Iterator var2 = this.workUnits.iterator();

      while(var2.hasNext()) {
         WorkUnit workUnit = (WorkUnit)var2.next();
         if (workUnit instanceof Workflow) {
            ((Workflow)workUnit).collectAllWorkUnits((Collection)workUnits);
         }
      }

      return (Collection)workUnits;
   }

   public List getStatusHistory() {
      Collection wus = this.collectAllWorkUnits((Collection)null);
      List messages = new ArrayList();
      Iterator var3 = wus.iterator();

      while(true) {
         WorkUnit wu;
         do {
            do {
               if (!var3.hasNext()) {
                  Collections.sort(messages);
                  return messages;
               }

               wu = (WorkUnit)var3.next();
            } while(wu instanceof Workflow);
         } while(wu.getOperationResults() == null);

         Iterator var5 = wu.getOperationResults().iterator();

         while(var5.hasNext()) {
            ProgressInfo.OperationResult or = (ProgressInfo.OperationResult)var5.next();
            messages.add(new StatusHistoryMessage.ProgressHistoryMessage(wu, or));
         }
      }
   }

   public abstract FailureDecider.Decision execute();

   public abstract FailureDecider.Decision resume();

   public abstract FailureDecider.Decision revert();

   public abstract FailureDecider.Decision retry();

   private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
      ois.defaultReadObject();
      this.workUnits = new ArrayList();
      this.sequence = -1;
   }

   public Set listAllUnsatisfiedSharedStateIds() {
      Set result = new TreeSet();
      Iterator var2 = this.workUnits.iterator();

      while(var2.hasNext()) {
         WorkUnit workUnit = (WorkUnit)var2.next();
         result.addAll(workUnit.listAllUnsatisfiedSharedStateIds());
      }

      return result;
   }

   public void registerListener(WorkflowStateChangeListener listener, boolean recursive) {
      if (listener != null) {
         super.registerListener(listener, recursive);
         if (recursive) {
            Iterator var3 = this.getWorkUnits().iterator();

            while(var3.hasNext()) {
               WorkUnit workUnit = (WorkUnit)var3.next();
               workUnit.registerListener(listener, recursive);
            }
         }
      }

   }

   CommandFailedException getFailureCause() {
      Iterator var1 = this.getWorkUnits().iterator();

      CommandFailedException result;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         WorkUnit workUnit = (WorkUnit)var1.next();
         result = workUnit.getFailureCause();
      } while(result == null);

      return result;
   }

   public List getAllErrors() {
      Collection wus = this.collectAllWorkUnits((Collection)null);
      List result = new ArrayList();
      Iterator var3 = wus.iterator();

      while(true) {
         WorkUnit wu;
         do {
            do {
               if (!var3.hasNext()) {
                  Collections.sort(result, new Comparator() {
                     public int compare(CommandFailedException o1, CommandFailedException o2) {
                        long time1 = Long.MAX_VALUE;
                        long time2 = Long.MAX_VALUE;
                        if (o1 != null && o1.getOperationStartTimestamp() != null) {
                           time1 = o1.getOperationStartTimestamp().getTime();
                        }

                        if (o2 != null && o2.getOperationStartTimestamp() != null) {
                           time2 = o2.getOperationStartTimestamp().getTime();
                        }

                        long result = time2 - time1;
                        if (result > 0L) {
                           return 1;
                        } else {
                           return result < 0L ? -1 : 0;
                        }
                     }
                  });
                  return result;
               }

               wu = (WorkUnit)var3.next();
            } while(wu instanceof Workflow);
         } while(wu.getOperationResults() == null);

         Iterator var5 = wu.getOperationResults().iterator();

         while(var5.hasNext()) {
            ProgressInfo.OperationResult or = (ProgressInfo.OperationResult)var5.next();
            if (!or.isSuccess()) {
               Exception originalException = or.getException();
               CommandFailedException commandFailedException;
               if (originalException != null) {
                  commandFailedException = new CommandFailedException(or.getStartTime(), wu.getIdentifierForLogMessages(), or.getOperation().toLocalizedString(), originalException);
                  commandFailedException.setStackTrace(originalException.getStackTrace());
               } else {
                  commandFailedException = new CommandFailedException(or.getStartTime(), wu.getIdentifierForLogMessages(), or.getOperation().toLocalizedString());
               }

               result.add(commandFailedException);
            }
         }
      }
   }

   protected int findMaxUsedFromSequence() {
      int result = 0;
      Iterator var2 = this.workUnits.iterator();

      while(var2.hasNext()) {
         WorkUnit wu = (WorkUnit)var2.next();
         int val = wu.findMaxUsedFromSequence();
         if (val > result) {
            result = val;
         }
      }

      return result;
   }

   protected int getNextFromSequence() {
      if (this.getParentWorkUnit() == null) {
         synchronized(this) {
            if (this.sequence < 0) {
               this.sequence = this.findMaxUsedFromSequence();
            }

            return ++this.sequence;
         }
      } else {
         return this.getParentWorkUnit().getNextFromSequence();
      }
   }

   abstract WorkUnit getNextStep();

   abstract WorkUnit getNextRevertStep();

   private static class IdPair implements Serializable {
      private static final long serialVersionUID = 1L;
      private final String id;
      private final UUID validationId;

      public IdPair(String id, UUID validationId) {
         this.id = id;
         this.validationId = validationId;
      }

      public IdPair(WorkUnit wu) {
         this(wu.getId(), wu.getValidationId());
      }
   }
}
