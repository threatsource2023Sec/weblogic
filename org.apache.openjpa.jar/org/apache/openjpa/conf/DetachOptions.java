package org.apache.openjpa.conf;

import org.apache.openjpa.kernel.DetachState;

public abstract class DetachOptions implements DetachState {
   private boolean _field = true;
   private boolean _transient = true;
   private boolean _manager = true;
   private boolean _access = true;

   public abstract int getDetachState();

   public boolean getDetachedStateField() {
      return this._field;
   }

   public void setDetachedStateField(boolean val) {
      this._field = val;
      if (!val) {
         this._manager = false;
      }

   }

   public void setDetachedStateField(String val) {
      if (val != null) {
         if ("transient".equals(val)) {
            this.setDetachedStateField(true);
            this._transient = true;
         } else if ("true".equals(val)) {
            this.setDetachedStateField(true);
            this._transient = false;
         } else {
            if (!"false".equals(val)) {
               throw new IllegalArgumentException("DetachedStateField=" + val);
            }

            this.setDetachedStateField(false);
            this._transient = false;
         }

      }
   }

   public boolean isDetachedStateTransient() {
      return this._transient;
   }

   public void setDetachedStateTransient(boolean val) {
      this._transient = val;
   }

   public boolean getDetachedStateManager() {
      return this._manager;
   }

   public void setDetachedStateManager(boolean val) {
      this._manager = val;
   }

   public boolean getAccessUnloaded() {
      return this._access;
   }

   public void setAccessUnloaded(boolean val) {
      this._access = val;
   }

   public static class All extends DetachOptions {
      public int getDetachState() {
         return 2;
      }
   }

   public static class FetchGroups extends DetachOptions {
      public int getDetachState() {
         return 0;
      }
   }

   public static class Loaded extends DetachOptions {
      public int getDetachState() {
         return 1;
      }
   }
}
