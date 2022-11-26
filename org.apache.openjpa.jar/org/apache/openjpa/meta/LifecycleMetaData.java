package org.apache.openjpa.meta;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.event.LifecycleEvent;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;

public class LifecycleMetaData implements Serializable {
   public static final int IGNORE_NONE = 0;
   public static final int IGNORE_HIGH = 2;
   public static final int IGNORE_LOW = 4;
   private static final LifecycleCallbacks[] EMPTY_CALLBACKS = new LifecycleCallbacks[0];
   private static final Localizer _loc = Localizer.forPackage(LifecycleMetaData.class);
   private final ClassMetaData _meta;
   private LifecycleCallbacks[][] _declared = (LifecycleCallbacks[][])null;
   private LifecycleCallbacks[][] _super = (LifecycleCallbacks[][])null;
   private LifecycleCallbacks[][] _all = (LifecycleCallbacks[][])null;
   private int[] _high = null;
   private int[] _superHigh = null;
   private boolean _resolved = false;
   private boolean _ignoreSystem = false;
   private int _ignoreSups = 0;

   LifecycleMetaData(ClassMetaData meta) {
      this._meta = meta;
   }

   public boolean getIgnoreSystemListeners() {
      return this._ignoreSystem;
   }

   public void setIgnoreSystemListeners(boolean ignore) {
      this._ignoreSystem = ignore;
   }

   public int getIgnoreSuperclassCallbacks() {
      return this._ignoreSups;
   }

   public void setIgnoreSuperclassCallbacks(int ignore) {
      this._ignoreSups = ignore;
   }

   public LifecycleCallbacks[] getDeclaredCallbacks(int eventType) {
      return this._declared != null && this._declared[eventType] != null ? this._declared[eventType] : EMPTY_CALLBACKS;
   }

   public LifecycleCallbacks[] getCallbacks(int eventType) {
      this.resolve();
      return this._all != null && this._all[eventType] != null ? this._all[eventType] : EMPTY_CALLBACKS;
   }

   public void setDeclaredCallbacks(int eventType, LifecycleCallbacks[] callbacks, int highPriority) {
      if (this._resolved) {
         throw new InternalException(_loc.get("lifecycle-resolved", this._meta, Arrays.asList(callbacks)));
      } else {
         if (this._declared == null) {
            this._declared = new LifecycleCallbacks[LifecycleEvent.ALL_EVENTS.length][];
            this._high = new int[LifecycleEvent.ALL_EVENTS.length];
         }

         this._declared[eventType] = callbacks;
         this._high[eventType] = highPriority;
      }
   }

   public LifecycleCallbacks[] getNonPCSuperclassCallbacks(int eventType) {
      return this._super != null && this._super[eventType] != null ? this._super[eventType] : EMPTY_CALLBACKS;
   }

   public void setNonPCSuperclassCallbacks(int eventType, LifecycleCallbacks[] callbacks, int highPriority) {
      if (this._resolved) {
         throw new InternalException(_loc.get("lifecycle-resolved", this._meta, Arrays.asList(callbacks)));
      } else {
         if (this._super == null) {
            this._super = new LifecycleCallbacks[LifecycleEvent.ALL_EVENTS.length][];
            this._superHigh = new int[LifecycleEvent.ALL_EVENTS.length];
         }

         this._super[eventType] = callbacks;
         this._superHigh[eventType] = highPriority;
      }
   }

   void resolve() {
      if (!this._resolved) {
         this._all = this.combineCallbacks();
         this._resolved = true;
      }

   }

   private LifecycleCallbacks[][] combineCallbacks() {
      if (this._ignoreSups == 6) {
         return this._declared;
      } else {
         LifecycleMetaData supMeta = this._meta.getPCSuperclass() == null ? null : this._meta.getPCSuperclassMetaData().getLifecycleMetaData();
         if (supMeta == null && this._super == null) {
            return this._declared;
         } else {
            if (supMeta != null) {
               supMeta.resolve();
               if (supMeta._all == null) {
                  return this._declared;
               }

               if (this._declared == null && this._ignoreSups == 0) {
                  this._high = supMeta._high;
                  return supMeta._all;
               }

               this._super = (LifecycleCallbacks[][])null;
               this._superHigh = null;
            }

            LifecycleCallbacks[][] all = new LifecycleCallbacks[LifecycleEvent.ALL_EVENTS.length][];

            for(int i = 0; i < all.length; ++i) {
               LifecycleCallbacks[] decs = this.getDeclaredCallbacks(i);
               LifecycleCallbacks[] sups;
               int supHigh;
               if (supMeta == null) {
                  sups = this._super[i] == null ? EMPTY_CALLBACKS : this._super[i];
                  supHigh = this._superHigh == null ? 0 : this._superHigh[i];
               } else {
                  sups = supMeta.getCallbacks(i);
                  supHigh = supMeta._high == null ? 0 : supMeta._high[i];
               }

               int supStart = (this._ignoreSups & 2) != 0 ? supHigh : 0;
               int supEnd = (this._ignoreSups & 4) != 0 ? supHigh : sups.length;
               if (supEnd - supStart == 0) {
                  all[i] = decs;
               } else if (decs.length == 0) {
                  if (supEnd - supStart == sups.length) {
                     all[i] = sups;
                  } else {
                     all[i] = new LifecycleCallbacks[supEnd - supStart];
                     System.arraycopy(sups, supStart, all[i], 0, all[i].length);
                  }

                  if (this._high == null) {
                     this._high = new int[all.length];
                  }

                  this._high[i] = supHigh - supStart;
               } else {
                  all[i] = new LifecycleCallbacks[decs.length + supEnd - supStart];
                  int count = 0;
                  int j;
                  if ((this._ignoreSups & 2) == 0) {
                     for(j = 0; j < supHigh; ++j) {
                        all[i][count++] = sups[j];
                     }
                  }

                  for(j = 0; j < this._high[i]; ++j) {
                     all[i][count++] = decs[j];
                  }

                  if ((this._ignoreSups & 4) == 0) {
                     for(j = supHigh; j < sups.length; ++j) {
                        all[i][count++] = sups[j];
                     }
                  }

                  for(j = this._high[i]; j < decs.length; ++j) {
                     all[i][count++] = decs[j];
                  }

                  if ((this._ignoreSups & 2) == 0) {
                     int[] var11 = this._high;
                     var11[i] += supHigh;
                  }
               }
            }

            return all;
         }
      }
   }
}
