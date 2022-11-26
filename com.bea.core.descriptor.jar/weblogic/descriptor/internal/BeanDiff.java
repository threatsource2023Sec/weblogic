package weblogic.descriptor.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class BeanDiff extends BeanUpdateEvent {
   private static final Set EMPTY_SET = new LinkedHashSet(0);
   private Set updateSet;
   private Set referenceRemovalExcludeSet;
   private BeanUpdateEvent.PropertyUpdate[] updateList;
   private boolean hasNonDynamicUpdates = false;
   private int beanDiffID;
   private final Map restartPropertyToElementMap = new ConcurrentHashMap();

   public BeanDiff(DescriptorBean original, DescriptorBean update, int diffID, int beanDiffID) {
      super(original, update, diffID);
      this.updateSet = EMPTY_SET;
      this.referenceRemovalExcludeSet = EMPTY_SET;
      this.beanDiffID = beanDiffID;
   }

   public BeanDiff(BeanDiff beanDiff) {
      super((DescriptorBean)beanDiff.source, beanDiff.getProposedBean(), beanDiff.getUpdateID());
      this.updateSet = (Set)(beanDiff.updateSet == EMPTY_SET ? EMPTY_SET : new LinkedHashSet(beanDiff.updateSet));
      this.referenceRemovalExcludeSet = (Set)(beanDiff.referenceRemovalExcludeSet == EMPTY_SET ? EMPTY_SET : new LinkedHashSet(beanDiff.referenceRemovalExcludeSet));
      if (beanDiff.updateList == null) {
         this.updateList = null;
      } else {
         this.updateList = new BeanUpdateEvent.PropertyUpdate[beanDiff.updateList.length];
         System.arraycopy(beanDiff.updateList, 0, this.updateList, 0, this.updateList.length);
      }

      this.hasNonDynamicUpdates = beanDiff.hasNonDynamicUpdates;
      this.beanDiffID = beanDiff.beanDiffID;
   }

   public void recordChange(String propName, boolean isDynamic) {
      this.record(propName, isDynamic, 1, (Object)null);
   }

   public void recordRemoval(String propName, Object old, boolean isDynamic) {
      this.record(propName, isDynamic, 3, old);
   }

   public void recordAddition(String propName, Object addition, boolean isDynamic) {
      this.record(propName, isDynamic, 2, addition);
   }

   private void record(String propName, boolean isDynamic, int updateType, Object addedOrRemoved) {
      Set restartElements = (Set)this.getRestartPropertyToElementMap().get(propName);
      if (restartElements == null) {
         restartElements = Collections.EMPTY_SET;
      }

      this.addUpdate(new BeanUpdateEvent.PropertyUpdate(propName, updateType, addedOrRemoved, isDynamic, this.getSourceBean().isSet(propName), this.getProposedBean().isSet(propName), restartElements, this.getRestartPropertyToElementMap().containsKey(propName)));
      this.checkAndSetNonDynamicUpdates(isDynamic);
   }

   public int size() {
      return this.updateSet.size();
   }

   public BeanUpdateEvent.PropertyUpdate[] getUpdateList() {
      if (this.updateList == null) {
         this.updateList = (BeanUpdateEvent.PropertyUpdate[])((BeanUpdateEvent.PropertyUpdate[])this.updateSet.toArray(new BeanUpdateEvent.PropertyUpdate[0]));
      }

      return this.updateList;
   }

   public BeanDiff getReferenceRemovalOnlyBeanDiff() {
      Set referenceRemovalOnlySet = new LinkedHashSet(this.updateSet);
      referenceRemovalOnlySet.removeAll(this.referenceRemovalExcludeSet);
      if (referenceRemovalOnlySet.size() > 0) {
         BeanDiff diff = new ReadOnlyBeanDiff(this.getSourceBean(), this.getProposedBean(), this.getUpdateID(), this.getBeanDiffID());
         diff.hasNonDynamicUpdates = this.hasNonDynamicUpdates;
         diff.updateSet = referenceRemovalOnlySet;
         return diff;
      } else {
         return null;
      }
   }

   public BeanDiff getReferenceRemovalExcludeBeanDiff() {
      if (this.referenceRemovalExcludeSet.size() > 0) {
         BeanDiff diff = new ReadOnlyBeanDiff(this.getSourceBean(), this.getProposedBean(), this.getUpdateID(), this.getBeanDiffID());
         diff.hasNonDynamicUpdates = this.hasNonDynamicUpdates;
         diff.updateSet = this.referenceRemovalExcludeSet;
         return diff;
      } else {
         return null;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.getSource() + " (" + this.updateSet.size() + " updateSet)");
      Iterator it = this.updateSet.iterator();

      while(it.hasNext()) {
         buf.append("\n  " + it.next().toString());
      }

      return buf.toString();
   }

   private boolean isReferenceRemovel(BeanUpdateEvent.PropertyUpdate update) {
      DescriptorBean bean = this.getSourceBean();
      ReferenceManager refManager = ((DescriptorImpl)bean.getDescriptor()).getReferenceManager();
      AbstractDescriptorBeanHelper helper = ((AbstractDescriptorBean)bean)._getHelper();
      String propName = update.getPropertyName();
      int propIndex = helper.getPropertyIndex(propName);
      return refManager.isReference(bean, propIndex) && (update.isRemoveUpdate() || update.isChangeUpdate() && update.isUnsetUpdate());
   }

   private void addUpdate(BeanUpdateEvent.PropertyUpdate update) {
      if (this.updateSet == EMPTY_SET) {
         this.updateSet = new LinkedHashSet();
      }

      this.updateSet.add(update);
      this.updateList = null;
      if (!this.isReferenceRemovel(update)) {
         if (this.referenceRemovalExcludeSet == EMPTY_SET) {
            this.referenceRemovalExcludeSet = new LinkedHashSet();
         }

         this.referenceRemovalExcludeSet.add(update);
      }

   }

   private void checkAndSetNonDynamicUpdates(boolean isDynamic) {
      if (!isDynamic && !this.hasNonDynamicUpdates) {
         this.hasNonDynamicUpdates = true;
      }

   }

   public boolean hasNonDynamicUpdates() {
      return this.hasNonDynamicUpdates;
   }

   public int getBeanDiffID() {
      return this.beanDiffID;
   }

   public void addRestartElements(String prop, Set restartElements) {
      if (restartElements != null) {
         this.restartPropertyToElementMap.put(prop, restartElements);
      }

   }

   public Map getRestartPropertyToElementMap() {
      return this.restartPropertyToElementMap;
   }

   private class ReadOnlyBeanDiff extends BeanDiff {
      public ReadOnlyBeanDiff(DescriptorBean original, DescriptorBean update, int diffID, int beanDiffID) {
         super(original, update, diffID, beanDiffID);
      }

      public void recordChange(String propName, boolean isDynamic) {
         throw new UnsupportedOperationException("Readonly BeanDiff");
      }

      public void recordRemoval(String propName, Object old, boolean isDynamic) {
         throw new UnsupportedOperationException("Readonly BeanDiff");
      }

      public void recordAddition(String propName, Object addition, boolean isDynamic) {
         throw new UnsupportedOperationException("Readonly BeanDiff");
      }
   }
}
