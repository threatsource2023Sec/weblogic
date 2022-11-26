package org.apache.openjpa.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;
import org.apache.commons.collections.set.MapBackedSet;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public abstract class AbstractChangeTracker implements ChangeTracker {
   protected Collection add = null;
   protected Collection rem = null;
   protected Collection change = null;
   private boolean _autoOff = true;
   private boolean _track = false;
   private Boolean _identity = null;
   private int _seq = -1;

   public boolean getAutoOff() {
      return this._autoOff;
   }

   public void setAutoOff(boolean autoOff) {
      this._autoOff = autoOff;
   }

   public boolean isTracking() {
      return this._track;
   }

   public void startTracking() {
      this._track = true;
      if (this._seq == -1) {
         this._seq = this.initialSequence();
      }

      this.reset();
   }

   protected int initialSequence() {
      return 0;
   }

   public void stopTracking() {
      this._track = false;
      this._seq = -1;
      this.reset();
   }

   protected void reset() {
      if (this.add != null) {
         this.add.clear();
      }

      if (this.rem != null) {
         this.rem.clear();
      }

      if (this.change != null) {
         this.change.clear();
      }

      this._identity = null;
   }

   public Collection getAdded() {
      return (Collection)(this.add == null ? Collections.EMPTY_LIST : this.add);
   }

   public Collection getRemoved() {
      return (Collection)(this.rem == null ? Collections.EMPTY_LIST : this.rem);
   }

   public Collection getChanged() {
      return (Collection)(this.change == null ? Collections.EMPTY_LIST : this.change);
   }

   protected void added(Object val) {
      if (this._track) {
         this.setIdentity(val);
         this.add(val);
      }
   }

   protected abstract void add(Object var1);

   protected void removed(Object val) {
      if (this._track) {
         this.setIdentity(val);
         this.remove(val);
      }
   }

   protected abstract void remove(Object var1);

   protected void changed(Object val) {
      if (this._track) {
         this.setIdentity(val);
         this.change(val);
      }
   }

   protected abstract void change(Object var1);

   public int getNextSequence() {
      return this._seq;
   }

   public void setNextSequence(int seq) {
      this._seq = seq;
   }

   protected Set newSet() {
      return (Set)(this._identity == Boolean.TRUE ? MapBackedSet.decorate(new IdentityHashMap()) : new HashSet());
   }

   private void setIdentity(Object val) {
      if (val != null && this._identity == null) {
         if (ImplHelper.isManagedType((OpenJPAConfiguration)null, val.getClass())) {
            this._identity = Boolean.TRUE;
         } else {
            this._identity = Boolean.FALSE;
         }

         this.add = switchStructure(this.add, this._identity);
         this.rem = switchStructure(this.rem, this._identity);
         this.change = switchStructure(this.change, this._identity);
      }
   }

   private static Collection switchStructure(Collection cur, boolean identity) {
      if (cur == null) {
         return null;
      } else if (identity && cur instanceof HashSet) {
         if (cur.isEmpty()) {
            return null;
         } else {
            Set replace = MapBackedSet.decorate(new IdentityHashMap());
            replace.addAll(cur);
            return replace;
         }
      } else if (!identity && !(cur instanceof HashSet) && cur instanceof Set) {
         return cur.isEmpty() ? null : new HashSet(cur);
      } else {
         return cur;
      }
   }
}
