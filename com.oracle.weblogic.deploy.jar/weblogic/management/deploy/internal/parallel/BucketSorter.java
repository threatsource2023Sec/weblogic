package weblogic.management.deploy.internal.parallel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.internal.DeploymentType;
import weblogic.management.configuration.BasicDeploymentMBean;

public abstract class BucketSorter {
   private Bucket currentBucket = null;
   private List buckets = null;
   private Deque savedApps = null;

   public Collection sortForParallelTransition(Collection deployments) {
      return this.sortForParallelTransition(deployments, false);
   }

   public Collection sortForParallelTransition(Collection deployments, boolean isPreferenceLibraries) {
      this.savedApps = new ArrayDeque();
      this.buckets = new ArrayList();
      SortState state = new SortState(isPreferenceLibraries, new Bucket(false, new ArrayDeque()));
      this.currentBucket = new Bucket(false, new ArrayDeque());
      Iterator var4 = deployments.iterator();

      while(var4.hasNext()) {
         Object dep = var4.next();
         if (!this.preSort(dep, state, this.savedApps)) {
            this.sort(dep, state);
         }
      }

      if (!this.currentBucket.contents.isEmpty()) {
         this.buckets.add(this.currentBucket);
      }

      if (!this.savedApps.isEmpty()) {
         this.currentBucket = new Bucket(false, new ArrayDeque());
         this.currentBucket.contents.addAll(this.savedApps);
         this.buckets.add(this.currentBucket);
      }

      if (!state.libraryBucket.contents.isEmpty()) {
         this.buckets.add(0, state.libraryBucket);
      }

      return this.buckets;
   }

   protected void sort(Object dep, SortState state) {
      DeploymentType type = this.getDeploymentType(dep);
      if (state.isPreferenceLibraries && type == DeploymentType.LIBRARY) {
         state.libraryBucket.contents.add(dep);
      } else {
         int deploymentOrder = false;
         boolean parallel = this.isDeploymentTypeParallelForTransition(type);
         int deploymentOrder = this.getDeploymentOrder(type, dep);
         boolean canUseCurrentBucket = (state.lastDeploymentType == null || type == state.lastDeploymentType) && this.currentBucket.isParallel == parallel && (type == null || !this.usesDeploymentOrderForTransition(type) || state.lastDeploymentOrder == deploymentOrder);
         if (!canUseCurrentBucket) {
            if (!this.currentBucket.contents.isEmpty()) {
               this.buckets.add(this.currentBucket);
            }

            this.currentBucket = new Bucket(parallel, new ArrayDeque());
         }

         this.currentBucket.contents.add(dep);
         state.lastDeploymentType = type;
         state.lastDeploymentOrder = deploymentOrder;
      }
   }

   protected boolean preSort(Object dep, SortState state, Deque savedApps) {
      return false;
   }

   protected DeploymentType getDeploymentType(Object dep) {
      BasicDeploymentMBean mbean = this.getDeploymentMBean(dep);
      return mbean != null ? DeploymentType.findType(mbean) : null;
   }

   protected boolean isDeploymentTypeParallelForTransition(DeploymentType type) {
      return true;
   }

   protected boolean usesDeploymentOrderForTransition(DeploymentType type) {
      return true;
   }

   protected int getDeploymentOrder(DeploymentType type, Object dep) {
      BasicDeploymentMBean mbean = this.getDeploymentMBean(dep);
      return mbean != null ? mbean.getDeploymentOrder() : 0;
   }

   protected abstract BasicDeploymentMBean getDeploymentMBean(Object var1);

   protected void emptyCurrentBucket() {
      if (!this.currentBucket.contents.isEmpty()) {
         this.buckets.add(this.currentBucket);
      }

      this.currentBucket = new Bucket(this.currentBucket.isParallel, new ArrayDeque());
   }

   protected void addBucket(boolean isParallel, Deque contents) {
      this.buckets.add(new Bucket(isParallel, contents));
   }

   protected Deque recreateSaved() {
      this.savedApps = new ArrayDeque();
      return this.savedApps;
   }

   protected class SortState {
      final boolean isPreferenceLibraries;
      final Bucket libraryBucket;
      int lastDeploymentOrder;
      DeploymentType lastDeploymentType;

      public SortState(boolean isPreferenceLibraries, Bucket libraryBucket) {
         this.isPreferenceLibraries = isPreferenceLibraries;
         this.libraryBucket = libraryBucket;
         this.lastDeploymentOrder = Integer.MIN_VALUE;
         this.lastDeploymentType = null;
      }
   }
}
