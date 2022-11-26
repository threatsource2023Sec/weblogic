package org.apache.openjpa.util;

import java.util.Map;

public class MapChangeTrackerImpl extends AbstractChangeTracker implements MapChangeTracker {
   private final Map _map;
   private boolean _keys = true;

   public MapChangeTrackerImpl(Map map, boolean autoOff) {
      this._map = map;
      this.setAutoOff(autoOff);
   }

   public boolean getTrackKeys() {
      return this._keys;
   }

   public void setTrackKeys(boolean keys) {
      this._keys = keys;
   }

   public void added(Object key, Object val) {
      if (this._keys) {
         super.added(key);
      } else {
         super.added(val);
      }

   }

   public void removed(Object key, Object val) {
      if (this._keys) {
         super.removed(key);
      } else {
         super.removed(val);
      }

   }

   public void changed(Object key, Object oldVal, Object newVal) {
      if (this._keys) {
         super.changed(key);
      } else {
         super.removed(oldVal);
         super.added(newVal);
      }

   }

   protected void add(Object obj) {
      if (this.rem != null && this.rem.remove(obj)) {
         if (this.change == null) {
            this.change = this.newSet();
         }

         this.change.add(obj);
      } else if (this.getAutoOff() && this.getAdded().size() + this.getChanged().size() + this.getRemoved().size() >= this._map.size()) {
         this.stopTracking();
      } else {
         if (this.add == null) {
            this.add = this.newSet();
         }

         this.add.add(obj);
      }

   }

   protected void remove(Object obj) {
      if (this.change != null) {
         this.change.remove(obj);
      }

      if (this.add == null || !this.add.remove(obj)) {
         if (this.getAutoOff() && this.getAdded().size() + this.getChanged().size() + this.getRemoved().size() >= this._map.size()) {
            this.stopTracking();
         } else {
            if (this.rem == null) {
               this.rem = this.newSet();
            }

            this.rem.add(obj);
         }
      }

   }

   protected void change(Object key) {
      if ((this.change == null || !this.change.contains(key)) && (this.add == null || !this.add.contains(key))) {
         if (this.getAutoOff() && this.getAdded().size() + this.getChanged().size() + this.getRemoved().size() >= this._map.size()) {
            this.stopTracking();
         } else {
            if (this.change == null) {
               this.change = this.newSet();
            }

            this.change.add(key);
         }

      }
   }
}
