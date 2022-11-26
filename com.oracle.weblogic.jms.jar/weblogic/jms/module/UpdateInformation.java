package weblogic.jms.module;

import java.util.HashMap;
import java.util.LinkedList;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.TargetMBean;

public class UpdateInformation {
   private int maxType;
   private LinkedList[] addedEntities;
   private HashMap[] addedEntitiesHash;
   private LinkedList[] deletedEntities;
   private HashMap[] deletedEntitiesHash;
   private LinkedList[] changedEntities;
   private TargetMBean[] defaultTargets;
   private boolean defaultTargetsChanged = false;
   private HashMap addedGroups;
   private HashMap removedGroups;
   private HashMap changedGroups;
   private HashMap addedLocalGroups;
   private HashMap removedLocalGroups;
   private HashMap changedLocalGroups;
   private DomainMBean proposedDomain;

   public UpdateInformation(int paramMaxType) {
      this.maxType = paramMaxType;
      this.addedEntities = new LinkedList[this.maxType];
      this.addedEntitiesHash = new HashMap[this.maxType];
      this.deletedEntities = new LinkedList[this.maxType];
      this.deletedEntitiesHash = new HashMap[this.maxType];
      this.changedEntities = new LinkedList[this.maxType];

      for(int lcv = 0; lcv < this.maxType; ++lcv) {
         this.addedEntities[lcv] = new LinkedList();
         this.addedEntitiesHash[lcv] = new HashMap();
         this.deletedEntities[lcv] = new LinkedList();
         this.deletedEntitiesHash[lcv] = new HashMap();
         this.changedEntities[lcv] = new LinkedList();
      }

   }

   synchronized LinkedList[] getAddedEntities() {
      return this.addedEntities;
   }

   HashMap[] getAddedEntitiesHash() {
      return this.addedEntitiesHash;
   }

   LinkedList[] getDeletedEntities() {
      return this.deletedEntities;
   }

   HashMap[] getDeletedEntitiesHash() {
      return this.deletedEntitiesHash;
   }

   LinkedList[] getChangedEntities() {
      return this.changedEntities;
   }

   void setAddedLocalGroups(HashMap paramAddedLocalGroups) {
      this.addedLocalGroups = paramAddedLocalGroups;
   }

   HashMap getAddedLocalGroups() {
      return this.addedLocalGroups;
   }

   void setRemovedLocalGroups(HashMap paramRemovedLocalGroups) {
      this.removedLocalGroups = paramRemovedLocalGroups;
   }

   HashMap getRemovedLocalGroups() {
      return this.removedLocalGroups;
   }

   void setChangedLocalGroups(HashMap paramChangedLocalGroups) {
      this.changedLocalGroups = paramChangedLocalGroups;
   }

   HashMap getChangedLocalGroups() {
      return this.changedLocalGroups;
   }

   void setAddedGroups(HashMap paramAddedGroups) {
      this.addedGroups = paramAddedGroups;
   }

   HashMap getAddedGroups() {
      return this.addedGroups;
   }

   void setRemovedGroups(HashMap paramRemovedGroups) {
      this.removedGroups = paramRemovedGroups;
   }

   HashMap getRemovedGroups() {
      return this.removedGroups;
   }

   void setChangedGroups(HashMap paramChangedGroups) {
      this.changedGroups = paramChangedGroups;
   }

   HashMap getChangedGroups() {
      return this.changedGroups;
   }

   DomainMBean getProposedDomain() {
      return this.proposedDomain;
   }

   void setProposedDomain(DomainMBean paramProposedDomain) {
      this.proposedDomain = paramProposedDomain;
   }

   void setDefaultTargets(TargetMBean[] targets) {
      if (this.defaultTargets != null && targets != null) {
         if (this.defaultTargets.length != targets.length) {
            this.defaultTargetsChanged = true;
         } else {
            LinkedList originalList = new LinkedList();

            int i;
            for(i = 0; i < this.defaultTargets.length; ++i) {
               originalList.add(this.defaultTargets[i]);
            }

            for(i = 0; i < targets.length; ++i) {
               if (!originalList.contains(targets[i])) {
                  this.defaultTargetsChanged = true;
               }
            }
         }
      }

      if (this.defaultTargets != null && targets == null || this.defaultTargets == null && targets != null) {
         this.defaultTargetsChanged = true;
      }

      if (this.defaultTargetsChanged) {
         this.defaultTargets = targets;
      }

   }

   TargetMBean[] getDefaultTargets() {
      return this.defaultTargets;
   }

   boolean hasDefaultTargetsChanged() {
      return this.defaultTargetsChanged;
   }

   void clearTargetUpdates() {
      this.getAddedGroups().clear();
      this.setAddedGroups((HashMap)null);
      this.getAddedLocalGroups().clear();
      this.setAddedLocalGroups((HashMap)null);
      this.getRemovedGroups().clear();
      this.setRemovedGroups((HashMap)null);
      this.getRemovedLocalGroups().clear();
      this.setRemovedLocalGroups((HashMap)null);
      this.getChangedGroups().clear();
      this.setChangedGroups((HashMap)null);
      this.getChangedLocalGroups().clear();
      this.setChangedLocalGroups((HashMap)null);
   }

   void close() {
      for(int lcv = 0; lcv < this.maxType; ++lcv) {
         this.addedEntities[lcv].clear();
         this.addedEntities[lcv] = null;
         this.addedEntitiesHash[lcv].clear();
         this.addedEntitiesHash[lcv] = null;
         this.deletedEntities[lcv].clear();
         this.deletedEntities[lcv] = null;
         this.deletedEntitiesHash[lcv].clear();
         this.deletedEntitiesHash[lcv] = null;
         this.changedEntities[lcv].clear();
         this.changedEntities[lcv] = null;
      }

      this.addedEntities = null;
      this.addedEntitiesHash = null;
      this.deletedEntities = null;
      this.deletedEntitiesHash = null;
      this.changedEntities = null;
      this.proposedDomain = null;
      this.defaultTargets = null;
      this.defaultTargetsChanged = false;
   }
}
