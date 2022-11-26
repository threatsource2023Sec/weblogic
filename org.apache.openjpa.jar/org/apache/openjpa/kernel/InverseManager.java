package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.InvalidStateException;

public class InverseManager implements Configurable {
   private static final Localizer _loc = Localizer.forPackage(InverseManager.class);
   protected static final Object NONE = new Object();
   public static final int ACTION_MANAGE = 0;
   public static final int ACTION_WARN = 1;
   public static final int ACTION_EXCEPTION = 2;
   private boolean _manageLRS = false;
   private int _action = 0;
   private Log _log;

   public boolean getManageLRS() {
      return this._manageLRS;
   }

   public void setManageLRS(boolean manage) {
      this._manageLRS = manage;
   }

   public int getAction() {
      return this._action;
   }

   public void setAction(int action) {
      this._action = action;
   }

   public void setAction(String action) {
      if ("exception".equals(action)) {
         this._action = 2;
      } else if ("warn".equals(action)) {
         this._action = 1;
      } else {
         if (!"manage".equals(action)) {
            throw new IllegalArgumentException(action);
         }

         this._action = 0;
      }

   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setConfiguration(Configuration conf) {
      this._log = conf.getLog("openjpa.Runtime");
   }

   public void correctRelations(OpenJPAStateManager sm, FieldMetaData fmd, Object value) {
      if (fmd.getDeclaredTypeCode() == 15 || fmd.getDeclaredTypeCode() == 12 && fmd.getElement().getDeclaredTypeCode() == 15) {
         if (this.getManageLRS() || !fmd.isLRS()) {
            FieldMetaData[] inverses = fmd.getInverseMetaDatas();
            if (inverses.length != 0) {
               this.clearInverseRelations(sm, fmd, inverses, value);
               if (value != null) {
                  StoreContext ctx = sm.getContext();
                  switch (fmd.getDeclaredTypeCode()) {
                     case 12:
                        Iterator itr = ((Collection)value).iterator();

                        while(itr.hasNext()) {
                           this.createInverseRelations(ctx, sm.getManagedInstance(), itr.next(), fmd, inverses);
                        }

                        return;
                     case 15:
                        this.createInverseRelations(ctx, sm.getManagedInstance(), value, fmd, inverses);
                  }
               }

            }
         }
      }
   }

   protected void createInverseRelations(StoreContext ctx, Object fromRef, Object toRef, FieldMetaData fmd, FieldMetaData[] inverses) {
      OpenJPAStateManager other = ctx.getStateManager(toRef);
      if (other != null && !other.isDeleted()) {
         for(int i = 0; i < inverses.length; ++i) {
            if (this.getManageLRS() || !inverses[i].isLRS()) {
               boolean owned = fmd == inverses[i].getMappedByMetaData() && this._action == 0 && !this.isLoaded(other, inverses[i].getIndex());
               switch (inverses[i].getDeclaredTypeCode()) {
                  case 12:
                     if (!owned || inverses[i].getElement().getCascadeDelete() == 2) {
                        this.addToCollection(other, inverses[i], fromRef);
                     }
                     break;
                  case 15:
                     if (!owned || inverses[i].getCascadeDelete() == 2) {
                        this.storeField(other, inverses[i], NONE, fromRef);
                     }
               }
            }
         }

      }
   }

   private boolean isLoaded(OpenJPAStateManager sm, int field) {
      if (sm.getLoaded().get(field)) {
         return true;
      } else {
         DataCache cache = sm.getMetaData().getDataCache();
         if (cache == null) {
            return false;
         } else if (sm.isEmbedded()) {
            return true;
         } else {
            PCData pc = cache.get(sm.getObjectId());
            return pc == null ? false : pc.isLoaded(field);
         }
      }
   }

   protected void clearInverseRelations(OpenJPAStateManager sm, FieldMetaData fmd, FieldMetaData[] inverses, Object newValue) {
      if (!sm.isNew() || sm.getFlushed().get(fmd.getIndex())) {
         if (fmd.getDeclaredTypeCode() == 15) {
            Object initial = sm.fetchInitialField(fmd.getIndex());
            this.clearInverseRelations(sm, initial, fmd, inverses);
         } else {
            Collection initial = (Collection)sm.fetchInitialField(fmd.getIndex());
            if (initial != null) {
               Collection coll = (Collection)newValue;
               Iterator itr = initial.iterator();

               while(true) {
                  Object elem;
                  do {
                     if (!itr.hasNext()) {
                        return;
                     }

                     elem = itr.next();
                  } while(coll != null && coll.contains(elem));

                  this.clearInverseRelations(sm, elem, fmd, inverses);
               }
            }
         }
      }
   }

   protected void clearInverseRelations(OpenJPAStateManager sm, Object val, FieldMetaData fmd, FieldMetaData[] inverses) {
      if (val != null) {
         OpenJPAStateManager other = sm.getContext().getStateManager(val);
         if (other != null && !other.isDeleted()) {
            for(int i = 0; i < inverses.length; ++i) {
               if (this.getManageLRS() || !inverses[i].isLRS()) {
                  boolean owned = fmd == inverses[i].getMappedByMetaData() && this._action == 0 && !this.isLoaded(other, inverses[i].getIndex());
                  switch (inverses[i].getDeclaredTypeCode()) {
                     case 12:
                        if (!owned || inverses[i].getElement().getCascadeDelete() == 2) {
                           this.removeFromCollection(other, inverses[i], sm.getManagedInstance());
                        }
                        break;
                     case 15:
                        if (!owned || inverses[i].getCascadeDelete() == 2) {
                           this.storeNull(other, inverses[i], sm.getManagedInstance());
                        }
                  }
               }
            }

         }
      }
   }

   protected void storeNull(OpenJPAStateManager sm, FieldMetaData fmd, Object compare) {
      this.storeField(sm, fmd, compare, (Object)null);
   }

   protected void storeField(OpenJPAStateManager sm, FieldMetaData fmd, Object compare, Object val) {
      Object oldValue = sm.fetchObjectField(fmd.getIndex());
      if (oldValue != val) {
         if (compare == NONE || oldValue == compare) {
            switch (this._action) {
               case 0:
                  sm.settingObjectField(sm.getPersistenceCapable(), fmd.getIndex(), oldValue, val, 0);
                  break;
               case 1:
                  this.warnConsistency(sm, fmd);
                  break;
               case 2:
                  this.throwException(sm, fmd);
               default:
                  throw new IllegalStateException();
            }

         }
      }
   }

   protected void removeFromCollection(OpenJPAStateManager sm, FieldMetaData fmd, Object val) {
      Collection coll = (Collection)sm.fetchObjectField(fmd.getIndex());
      if (coll != null) {
         switch (this._action) {
            case 0:
               for(int i = 0; coll.remove(val) && (i != 0 || !(coll instanceof Set)); ++i) {
               }

               return;
            case 1:
               if (coll.contains(val)) {
                  this.warnConsistency(sm, fmd);
               }
               break;
            case 2:
               if (coll.contains(val)) {
                  this.throwException(sm, fmd);
               }
               break;
            default:
               throw new IllegalStateException();
         }
      }

   }

   protected void addToCollection(OpenJPAStateManager sm, FieldMetaData fmd, Object val) {
      Collection coll = (Collection)sm.fetchObjectField(fmd.getIndex());
      if (coll == null) {
         coll = (Collection)sm.newFieldProxy(fmd.getIndex());
         sm.storeObjectField(fmd.getIndex(), coll);
      }

      if (!coll.contains(val)) {
         switch (this._action) {
            case 0:
               coll.add(val);
               break;
            case 1:
               this.warnConsistency(sm, fmd);
               break;
            case 2:
               this.throwException(sm, fmd);
            default:
               throw new IllegalStateException();
         }
      }

   }

   protected void warnConsistency(OpenJPAStateManager sm, FieldMetaData fmd) {
      if (this._log.isWarnEnabled()) {
         this._log.warn(_loc.get("inverse-consistency", fmd, sm.getId(), sm.getContext()));
      }

   }

   protected void throwException(OpenJPAStateManager sm, FieldMetaData fmd) {
      throw (new InvalidStateException(_loc.get("inverse-consistency", fmd, sm.getId(), sm.getContext()))).setFailedObject(sm.getManagedInstance()).setFatal(true);
   }
}
