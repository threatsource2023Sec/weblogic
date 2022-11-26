package weblogic.management.workflow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import weblogic.management.workflow.internal.WorkflowProgressImpl;

@Singleton
public class WorkflowProgressStore {
   private final ConcurrentHashMap partitionWorkFlowMap = new ConcurrentHashMap();

   synchronized void put(String partitionName, String workflowId, WorkflowProgressImpl progress) {
      Map workflowProgressMap = null;
      if (!this.partitionWorkFlowMap.containsKey(partitionName)) {
         workflowProgressMap = new ConcurrentHashMap();
         this.partitionWorkFlowMap.put(partitionName, workflowProgressMap);
      } else {
         workflowProgressMap = (Map)this.partitionWorkFlowMap.get(partitionName);
      }

      Objects.requireNonNull(workflowProgressMap);
      ((Map)workflowProgressMap).put(workflowId, progress);
   }

   synchronized void remove(String partitionName, String workflowId) {
      if (this.partitionWorkFlowMap.containsKey(partitionName)) {
         Map workflowProgressMap = (Map)this.partitionWorkFlowMap.get(partitionName);
         workflowProgressMap.remove(workflowId);
         if (workflowProgressMap.isEmpty()) {
            this.partitionWorkFlowMap.remove(partitionName);
         }
      }

   }

   synchronized WorkflowProgressImpl get(String partitionName, String workflowId) {
      if (this.partitionWorkFlowMap.containsKey(partitionName)) {
         Map workflowProgressMap = (Map)this.partitionWorkFlowMap.get(partitionName);
         return (WorkflowProgressImpl)workflowProgressMap.get(workflowId);
      } else {
         return null;
      }
   }

   synchronized WorkflowProgressImpl get(String workflowId) {
      Enumeration partitionNameList = this.partitionWorkFlowMap.keys();

      Map workflowProgressMap;
      do {
         if (!partitionNameList.hasMoreElements()) {
            return null;
         }

         String partitionName = (String)partitionNameList.nextElement();
         workflowProgressMap = (Map)this.partitionWorkFlowMap.get(partitionName);
      } while(!workflowProgressMap.containsKey(workflowId));

      return (WorkflowProgressImpl)workflowProgressMap.get(workflowId);
   }

   synchronized Collection values(String partitionName) {
      Collection emptyList = new ArrayList();
      if (this.partitionWorkFlowMap.containsKey(partitionName)) {
         Map workflowProgressMap = (Map)this.partitionWorkFlowMap.get(partitionName);
         return workflowProgressMap.values();
      } else {
         return emptyList;
      }
   }

   Enumeration keys() {
      return this.partitionWorkFlowMap.keys();
   }

   synchronized boolean containsKey(Object key) {
      Collection innerMapCollection = this.partitionWorkFlowMap.values();
      Iterator iterator = innerMapCollection.iterator();

      Map innerMap;
      do {
         if (!iterator.hasNext()) {
            return false;
         }

         innerMap = (Map)iterator.next();
      } while(!innerMap.containsKey(key));

      return true;
   }

   synchronized List getAllWorkflows() {
      List progressList = new ArrayList();
      Enumeration partitionNames = this.partitionWorkFlowMap.keys();

      while(partitionNames.hasMoreElements()) {
         String partitionName = (String)partitionNames.nextElement();
         Iterator var4 = this.values(partitionName).iterator();

         while(var4.hasNext()) {
            WorkflowProgressImpl workflowProgress = (WorkflowProgressImpl)var4.next();
            progressList.add(workflowProgress);
         }
      }

      return progressList;
   }
}
