package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.Harvester;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;

class DelegateHarvesterManagerImpl extends ArrayList implements DelegateHarvesterManager {
   static final long serialVersionUID = -1074325157133380056L;
   private static DebugLogger debugLogger = DebugSupport.getDebugLogger();
   private HashMap delegatesByName = new HashMap();
   private int modifiedCount;

   private DelegateHarvesterManagerImpl() {
   }

   static DelegateHarvesterManager createDelegateHarvesterManager() {
      return new DelegateHarvesterManagerImpl();
   }

   public synchronized void addDelegateHarvester(DelegateHarvesterControl delegateInfo) {
      this.add(delegateInfo);
      ++this.modifiedCount;
      this.delegatesByName.put(delegateInfo.getName(), delegateInfo);
      if (delegateInfo.getActivationPolicy() == DelegateHarvesterControl.ActivationPolicy.IMMEDIATE) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Activating delegate: " + delegateInfo.getName());
         }

         delegateInfo.activate();
      }

   }

   public void removeDelegateHarvesterByName(String harvesterName) {
      DelegateHarvesterControl control = (DelegateHarvesterControl)this.delegatesByName.remove(harvesterName);
      if (control != null) {
         this.remove(control);
         ++this.modifiedCount;
         control.deactivate();
      }

   }

   public synchronized void removeAll() {
      for(Iterator controlIt = this.iterator(); controlIt.hasNext(); ++this.modifiedCount) {
         DelegateHarvesterControl control = (DelegateHarvesterControl)controlIt.next();
         control.deactivate();
         this.delegatesByName.remove(control.getName());
         controlIt.remove();
      }

   }

   public synchronized Harvester getDefaultDelegate() {
      Harvester delegate = null;
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         DelegateHarvesterControl control = (DelegateHarvesterControl)var2.next();
         if (control.isDefaultDelegate()) {
            delegate = control.getDelegate();
         }
      }

      return delegate;
   }

   public Iterator harvesterIterator() {
      return new ActivatingIterator(DelegateHarvesterControl.ActivationPolicy.ON_REGISTRATION);
   }

   public Iterator activeOnlyIterator() {
      return new ActiveControlIterator();
   }

   public Iterator activatingIterator() {
      return new ActivatingIterator(DelegateHarvesterControl.ActivationPolicy.ON_REGISTRATION);
   }

   public Iterator activatingIterator(DelegateHarvesterControl.ActivationPolicy thresholdPolicy) {
      return new ActivatingIterator(thresholdPolicy);
   }

   public Iterator activatingIterator(List delegatesToSearch, DelegateHarvesterControl.ActivationPolicy thresholdPolicy) {
      return new ActivatingIterator(delegatesToSearch, thresholdPolicy);
   }

   public int getConfiguredHarvestersCount() {
      return this.size();
   }

   public int getActiveHarvestersCount() {
      int activeCount = 0;
      Iterator it = this.iterator();

      while(it.hasNext()) {
         DelegateHarvesterControl control = (DelegateHarvesterControl)it.next();
         if (control.isActive()) {
            ++activeCount;
         }
      }

      return activeCount;
   }

   public Harvester getHarvesterByName(String name) {
      DelegateHarvesterControl delegateHarvesterControl = (DelegateHarvesterControl)this.delegatesByName.get(name);
      return delegateHarvesterControl != null ? delegateHarvesterControl.getDelegate() : null;
   }

   protected int getModifiedCount() {
      return this.modifiedCount;
   }

   abstract class ControlIterator implements Iterator {
      protected DelegateHarvesterControl next;
      protected DelegateHarvesterControl current;
      protected int currentIndex = -1;
      protected int modcount = DelegateHarvesterManagerImpl.this.getModifiedCount();

      public ControlIterator() {
      }

      public boolean hasNext() {
         synchronized(this) {
            this.checkModification();
            if (this.next == null) {
               this.next = this.findNextNode();
            }
         }

         return this.next != null;
      }

      public Harvester next() {
         this.nextControl();
         return this.current.getDelegate();
      }

      protected void nextControl() {
         synchronized(this) {
            this.checkModification();
            this.current = this.next;
            this.currentIndex = DelegateHarvesterManagerImpl.this.indexOf(this.current);
            this.next = this.findNextNode();
         }
      }

      protected abstract DelegateHarvesterControl findNextNode();

      private final void checkModification() {
         if (DelegateHarvesterManagerImpl.this.getModifiedCount() != this.modcount) {
            throw new ConcurrentModificationException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   class ActiveControlIterator extends ControlIterator {
      public ActiveControlIterator() {
         super();
      }

      protected DelegateHarvesterControl findNextNode() {
         DelegateHarvesterControl next = null;

         for(int i = this.currentIndex + 1; i < DelegateHarvesterManagerImpl.this.size(); ++i) {
            DelegateHarvesterControl control = (DelegateHarvesterControl)DelegateHarvesterManagerImpl.this.get(i);
            if (control.isActive()) {
               next = control;
               break;
            }
         }

         return next;
      }
   }

   class ActivatingIterator extends ControlIterator {
      private DelegateHarvesterControl.ActivationPolicy threshold;
      private List delegatesSubset;

      public ActivatingIterator(DelegateHarvesterControl.ActivationPolicy thresholdPolicy) {
         this(new ArrayList(0), thresholdPolicy);
      }

      public ActivatingIterator(List subset, DelegateHarvesterControl.ActivationPolicy thresholdPolicy) {
         super();
         this.threshold = thresholdPolicy;
         this.delegatesSubset = subset;
      }

      protected DelegateHarvesterControl findNextNode() {
         DelegateHarvesterControl next = null;
         int nextIndex = this.currentIndex + 1;

         while(next == null && nextIndex < DelegateHarvesterManagerImpl.this.size()) {
            DelegateHarvesterControl control = (DelegateHarvesterControl)DelegateHarvesterManagerImpl.this.get(nextIndex++);
            if (this.delegatesSubset.isEmpty() || this.delegatesSubset.contains(control.getName())) {
               next = control;
            }
         }

         return next;
      }

      public Harvester next() {
         super.nextControl();
         if (this.current != null) {
            if (!this.current.isActive() && this.threshold.compareTo(this.current.getActivationPolicy()) >= 0) {
               if (DelegateHarvesterManagerImpl.debugLogger.isDebugEnabled()) {
                  DelegateHarvesterManagerImpl.debugLogger.debug("Activating delegate: " + this.current.getName());
               }

               this.current.activate();
            }

            return this.current.getDelegate();
         } else {
            return null;
         }
      }
   }
}
