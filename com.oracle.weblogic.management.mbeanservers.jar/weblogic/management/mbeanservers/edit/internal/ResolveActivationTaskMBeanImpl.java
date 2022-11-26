package weblogic.management.mbeanservers.edit.internal;

import java.util.Collection;
import weblogic.descriptor.conflict.DiffConflict;
import weblogic.management.provider.ResolveTask;

public class ResolveActivationTaskMBeanImpl extends ActivationTaskMBeanImpl {
   private Collection conflicts;
   private String patchDescription;

   public ResolveActivationTaskMBeanImpl(ConfigurationManagerMBeanImpl manager, ResolveTask resolveTask) {
      super(manager, resolveTask);
   }

   private ResolveTask getResolveTask() {
      return (ResolveTask)super.activateTask;
   }

   private Collection getConflicts() {
      return this.completed ? this.conflicts : this.getResolveTask().getConflicts();
   }

   private String getPatchDescription() {
      return this.completed ? this.patchDescription : this.getResolveTask().getPatchDescription();
   }

   public String getDetails() {
      StringBuilder result = new StringBuilder();
      result.append(DiffConflict.constructMessage(this.getConflicts()));
      result.append('\n');
      result.append("Patch:\n");
      result.append(this.getPatchDescription());
      return result.toString();
   }

   void movingToCompleted() {
      this.conflicts = this.getResolveTask().getConflicts();
      this.patchDescription = this.getResolveTask().getPatchDescription();
      super.movingToCompleted();
   }
}
