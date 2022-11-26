package org.apache.openjpa.util;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionChangeTrackerImpl extends AbstractChangeTracker implements CollectionChangeTracker {
   private final Collection _coll;
   private final boolean _dups;
   private final boolean _order;

   public CollectionChangeTrackerImpl(Collection coll, boolean dups, boolean order, boolean autoOff) {
      this._coll = coll;
      this._dups = dups;
      this._order = order;
      this.setAutoOff(autoOff);
   }

   public boolean allowsDuplicates() {
      return this._dups;
   }

   public boolean isOrdered() {
      return this._order;
   }

   public void added(Object elem) {
      super.added(elem);
   }

   public void removed(Object elem) {
      super.removed(elem);
   }

   protected int initialSequence() {
      return this._order ? this._coll.size() : super.initialSequence();
   }

   protected void add(Object elem) {
      if (this.rem != null && this.rem.remove(elem)) {
         if (this._order) {
            this.stopTracking();
         } else {
            if (this.change == null) {
               this.change = this.newSet();
            }

            this.change.add(elem);
         }
      } else if (this.getAutoOff() && this.getAdded().size() + this.getRemoved().size() >= this._coll.size()) {
         this.stopTracking();
      } else {
         if (this.add == null) {
            if (!this._dups && !this._order) {
               this.add = this.newSet();
            } else {
               this.add = new ArrayList();
            }
         }

         this.add.add(elem);
      }

   }

   protected void remove(Object elem) {
      if (this._dups && this.getAutoOff() && this._coll.contains(elem)) {
         this.stopTracking();
      } else if (this.add == null || !this.add.remove(elem)) {
         if (this.getAutoOff() && this.getRemoved().size() + this.getAdded().size() >= this._coll.size()) {
            this.stopTracking();
         } else {
            if (this.rem == null) {
               this.rem = this.newSet();
            }

            this.rem.add(elem);
         }
      }

   }

   protected void change(Object elem) {
      throw new InternalException();
   }
}
