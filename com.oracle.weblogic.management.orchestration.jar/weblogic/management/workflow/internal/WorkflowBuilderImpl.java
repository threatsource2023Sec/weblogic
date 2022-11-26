package weblogic.management.workflow.internal;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.WorkflowException;
import weblogic.management.workflow.command.CommandInterface;

public class WorkflowBuilderImpl extends WorkflowBuilder {
   private String name;
   private final List list = new ArrayList();
   private final Map shareStates = new HashMap();
   private FailurePlan failurePlan;
   private final Map meta = new HashMap();

   public WorkflowBuilder add(String key, Serializable value) {
      this.shareStates.put(key, value);
      return this;
   }

   public WorkflowBuilder add(Serializable value) {
      return this.add(value.getClass().getName(), value);
   }

   public WorkflowBuilderImpl name(String name) {
      this.name = name;
      return this;
   }

   public Map getShareStates() {
      Map map = null;
      if (this.list.size() >= 1) {
         map = ((BuilderUnit)this.list.get(0)).state;
      }

      return map;
   }

   public List getClassList() {
      ArrayList classList = new ArrayList();
      Iterator var2 = this.list.iterator();

      while(var2.hasNext()) {
         BuilderUnit bu = (BuilderUnit)var2.next();
         if (bu.provider != null) {
            classList.add(bu.provider.getCommandClass());
         } else if (bu.builder != null) {
            classList.addAll(((WorkflowBuilderImpl)bu.builder).getClassList());
         }
      }

      return classList;
   }

   public WorkflowBuilder add(Class clazz) {
      return this.add(clazz, (Map)null, (FailurePlan)null);
   }

   public WorkflowBuilder add(Class clazz, FailurePlan failurePlan) throws WorkflowException {
      return this.add(clazz, (Map)null, failurePlan);
   }

   public WorkflowBuilder add(Class clazz, Map states, FailurePlan failurePlan) throws WorkflowException {
      BuilderUnit builderUnit = new BuilderUnit(new BeanCommandProvider(clazz));
      if (failurePlan != null) {
         builderUnit.failurePlan = failurePlan;
      }

      if (states != null) {
         builderUnit.state.putAll(states);
      }

      this.list.add(builderUnit);
      return this;
   }

   public WorkflowBuilder add(Class clazz, Map states) throws WorkflowException {
      return this.add(clazz, states, (FailurePlan)null);
   }

   public WorkflowBuilder add(CommandInterface instance) throws WorkflowException {
      return this.add((CommandInterface)instance, (FailurePlan)null);
   }

   public WorkflowBuilder add(CommandInterface instance, FailurePlan failurePlan) throws WorkflowException {
      BuilderUnit builderUnit = new BuilderUnit(new InstanceCommandProvider(instance));
      if (failurePlan != null) {
         builderUnit.failurePlan = failurePlan;
      }

      this.list.add(builderUnit);
      return this;
   }

   public WorkflowBuilder add(WorkflowBuilder builder) {
      return this.add((WorkflowBuilder)builder, (FailurePlan)null);
   }

   public WorkflowBuilder add(WorkflowBuilder builder, FailurePlan failurePlan) {
      BuilderUnit builderUnit = new BuilderUnit(builder);
      if (failurePlan != null) {
         builderUnit.failurePlan = failurePlan;
      }

      this.list.add(builderUnit);
      return this;
   }

   public WorkflowBuilder add(Map sharedStates) {
      if (sharedStates != null) {
         this.shareStates.putAll(sharedStates);
      }

      return this;
   }

   public WorkflowBuilder failurePlan(FailurePlan failurePlan) {
      this.failurePlan = failurePlan;
      return this;
   }

   public WorkflowBuilder meta(String key, String value) {
      this.meta.put(key, value);
      return this;
   }

   Workflow toWorkflow(String id, String superName, Workflow parentWorkflow, File storeDirectory) {
      if (parentWorkflow == null) {
      }

      String fullName = this.name;
      if (superName != null) {
         fullName = superName + " / " + fullName;
      }

      FailureDecider failureDecider = new FailurePlanDecider(this.failurePlan);
      Workflow result = new SerialWorkflow(id, fullName, failureDecider, parentWorkflow, storeDirectory);
      result.getShareState().putAll(this.shareStates);
      int ind = 0;
      Iterator var9 = this.list.iterator();

      while(var9.hasNext()) {
         BuilderUnit builderUnit = (BuilderUnit)var9.next();
         if (builderUnit.provider != null) {
            FailureDecider fd = builderUnit.failurePlan == null ? failureDecider : new FailurePlanDecider(builderUnit.failurePlan);
            WorkUnit wu = new WorkUnit(id + "-" + ind++, builderUnit.provider, fd, result, storeDirectory);
            wu.getShareState().putAll(builderUnit.state);
            result.add(wu);
         } else {
            if (builderUnit.builder == null) {
               throw new IllegalStateException("Internal error in workflow builder");
            }

            WorkflowBuilderImpl subBuilder = (WorkflowBuilderImpl)builderUnit.builder;
            boolean removePlan = false;
            if (subBuilder.failurePlan == null) {
               subBuilder.failurePlan = this.failurePlan;
               removePlan = true;
            }

            result.add(subBuilder.toWorkflow(id + "-" + ind++, fullName, result, storeDirectory));
            if (removePlan) {
               subBuilder.failurePlan = null;
            }
         }
      }

      return result;
   }

   public Workflow toWorkflow(String workflowId, File storeDirectory) {
      return this.toWorkflow(workflowId, (String)null, (Workflow)null, storeDirectory);
   }

   public Map getMeta() {
      return Collections.unmodifiableMap(this.meta);
   }

   private String toString(String idPrefix, String indetation) {
      if (indetation == null) {
         indetation = "";
      }

      StringBuilder result = new StringBuilder();
      result.append(indetation).append("WorkflowBuilder: ").append(this.list.size()).append(" items");
      if (this.name != null && this.name.length() > 0) {
         result.append(" - ").append(this.name);
      }

      Iterator var5;
      if (!this.meta.isEmpty()) {
         result.append('\n').append(indetation).append("#META: ");
         boolean first = true;

         Map.Entry entry;
         for(var5 = this.meta.entrySet().iterator(); var5.hasNext(); result.append((String)entry.getKey()).append("=").append((String)entry.getValue())) {
            entry = (Map.Entry)var5.next();
            if (first) {
               first = false;
            } else {
               result.append(", ");
            }
         }
      }

      if (this.failurePlan != null) {
         result.append('\n').append(indetation).append(this.failurePlan);
      }

      int subIdInt = 0;

      for(var5 = this.list.iterator(); var5.hasNext(); ++subIdInt) {
         BuilderUnit unit = (BuilderUnit)var5.next();
         String subId;
         if (idPrefix != null && idPrefix.length() != 0) {
            subId = idPrefix + "." + subIdInt;
         } else {
            subId = String.valueOf(subIdInt);
         }

         result.append('\n');
         result.append(unit.toString(subId, indetation));
      }

      return result.toString();
   }

   public String toString() {
      return this.toString((String)null, (String)null);
   }

   private class BuilderUnit {
      private final CommandProvider provider;
      private final WorkflowBuilder builder;
      private final Map state;
      private FailurePlan failurePlan;

      private BuilderUnit(CommandProvider provider) {
         this.state = new HashMap();
         this.provider = provider;
         this.builder = null;
      }

      private BuilderUnit(WorkflowBuilder builder) {
         this.state = new HashMap();
         this.builder = builder;
         this.provider = null;
      }

      private String toString(String id, String indentation) {
         StringBuilder result = new StringBuilder();
         if (indentation != null && indentation.length() > 0) {
            result.append(indentation);
         }

         if (id != null && id.length() > 0) {
            result.append(id).append(" - ");
         }

         if (this.provider != null) {
            result.append(this.provider).append(' ');
         }

         if (this.failurePlan != null) {
            result.append('{').append(this.failurePlan).append("} ");
         }

         if (!this.state.isEmpty()) {
            result.append('[');
            boolean first = true;

            String s;
            for(Iterator var5 = this.state.keySet().iterator(); var5.hasNext(); result.append(s)) {
               s = (String)var5.next();
               if (first) {
                  first = false;
               } else {
                  result.append(", ");
               }
            }

            result.append("] ");
         }

         if (this.builder != null && this.builder instanceof WorkflowBuilderImpl) {
            WorkflowBuilderImpl builderImpl = (WorkflowBuilderImpl)this.builder;
            result.append('\n').append(builderImpl.toString(id, indentation + "  "));
         }

         return result.toString();
      }

      public String toString() {
         return this.toString((String)null, (String)null);
      }

      // $FF: synthetic method
      BuilderUnit(CommandProvider x1, Object x2) {
         this((CommandProvider)x1);
      }

      // $FF: synthetic method
      BuilderUnit(WorkflowBuilder x1, Object x2) {
         this((WorkflowBuilder)x1);
      }
   }
}
