package org.apache.openjpa.kernel;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;

abstract class AttachStrategy extends TransferFieldManager {
   private static final Localizer _loc = Localizer.forPackage(AttachStrategy.class);

   public abstract Object attach(AttachManager var1, Object var2, ClassMetaData var3, PersistenceCapable var4, OpenJPAStateManager var5, ValueMetaData var6, boolean var7);

   protected abstract Object getDetachedObjectId(AttachManager var1, Object var2);

   protected abstract void provideField(Object var1, StateManagerImpl var2, int var3);

   protected StateManagerImpl persist(AttachManager manager, PersistenceCapable pc, ClassMetaData meta, Object appId, boolean explicit) {
      PersistenceCapable newInstance;
      if (!manager.getCopyNew()) {
         newInstance = pc;
      } else if (appId == null) {
         newInstance = pc.pcNewInstance((StateManager)null, false);
      } else {
         newInstance = pc.pcNewInstance((StateManager)null, appId, false);
      }

      return (StateManagerImpl)manager.getBroker().persist(newInstance, appId, explicit, manager.getBehavior());
   }

   protected boolean attachField(AttachManager manager, Object toAttach, StateManagerImpl sm, FieldMetaData fmd, boolean nullLoaded) {
      if (!fmd.isVersion() && fmd.getManagement() == 3) {
         PersistenceCapable into = sm.getPersistenceCapable();
         int i = fmd.getIndex();
         this.provideField(toAttach, sm, i);
         int set = 2;
         switch (fmd.getDeclaredTypeCode()) {
            case 0:
               sm.settingBooleanField(into, i, sm.fetchBooleanField(i), this.fetchBooleanField(i), set);
               break;
            case 1:
               sm.settingByteField(into, i, sm.fetchByteField(i), this.fetchByteField(i), set);
               break;
            case 2:
               sm.settingCharField(into, i, sm.fetchCharField(i), this.fetchCharField(i), set);
               break;
            case 3:
               sm.settingDoubleField(into, i, sm.fetchDoubleField(i), this.fetchDoubleField(i), set);
               break;
            case 4:
               sm.settingFloatField(into, i, sm.fetchFloatField(i), this.fetchFloatField(i), set);
               break;
            case 5:
               sm.settingIntField(into, i, sm.fetchIntField(i), this.fetchIntField(i), set);
               break;
            case 6:
               sm.settingLongField(into, i, sm.fetchLongField(i), this.fetchLongField(i), set);
               break;
            case 7:
               sm.settingShortField(into, i, sm.fetchShortField(i), this.fetchShortField(i), set);
               break;
            case 8:
            case 10:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
               Object val = this.fetchObjectField(i);
               if (val == null && !nullLoaded) {
                  return false;
               }

               sm.settingObjectField(into, i, sm.fetchObjectField(i), val, set);
               break;
            case 9:
               String sval = this.fetchStringField(i);
               if (sval == null && !nullLoaded) {
                  return false;
               }

               sm.settingStringField(into, i, sm.fetchStringField(i), sval, set);
               break;
            case 11:
               Object frma = this.fetchObjectField(i);
               if (frma == null && !nullLoaded) {
                  return false;
               }

               Object toa = sm.fetchObjectField(i);
               if (toa != null && Array.getLength(toa) > 0 || frma != null && Array.getLength(frma) > 0) {
                  if (frma == null) {
                     sm.settingObjectField(into, i, toa, (Object)null, set);
                  } else {
                     sm.settingObjectField(into, i, toa, this.replaceArray(manager, frma, toa, sm, fmd), set);
                  }
               }
               break;
            case 12:
               Collection frmc = (Collection)this.fetchObjectField(i);
               if (frmc == null && !nullLoaded) {
                  return false;
               }

               Collection toc = (Collection)sm.fetchObjectField(i);
               if (toc != null && !toc.isEmpty() || frmc != null && !frmc.isEmpty()) {
                  if (frmc == null) {
                     sm.settingObjectField(into, i, toc, (Object)null, set);
                  } else if (toc == null) {
                     sm.settingObjectField(into, i, (Object)null, this.attachCollection(manager, frmc, sm, fmd), set);
                  } else if (toc instanceof Set && frmc instanceof Set) {
                     this.replaceCollection(manager, frmc, toc, sm, fmd);
                  } else {
                     sm.settingObjectField(into, i, toc, this.replaceList(manager, frmc, toc, sm, fmd), set);
                  }
               }
               break;
            case 13:
               Map frmm = (Map)this.fetchObjectField(i);
               if (frmm == null && !nullLoaded) {
                  return false;
               }

               Map tom = (Map)sm.fetchObjectField(i);
               if (tom != null && !tom.isEmpty() || frmm != null && !frmm.isEmpty()) {
                  if (frmm == null) {
                     sm.settingObjectField(into, i, tom, (Object)null, set);
                  } else if (tom == null) {
                     sm.settingObjectField(into, i, (Object)null, this.attachMap(manager, frmm, sm, fmd), set);
                  } else {
                     this.replaceMap(manager, frmm, tom, sm, fmd);
                  }
               }
               break;
            case 15:
            case 27:
               Object frmpc = this.fetchObjectField(i);
               if (frmpc == null && !nullLoaded) {
                  return false;
               }

               OpenJPAStateManager tosm = manager.getBroker().getStateManager(sm.fetchObjectField(i));
               PersistenceCapable topc = tosm == null ? null : tosm.getPersistenceCapable();
               if (frmpc != null || topc != null) {
                  if (fmd.getCascadeAttach() == 0) {
                     frmpc = this.getReference(manager, frmpc, sm, fmd);
                  } else {
                     PersistenceCapable intopc = topc;
                     if (!fmd.isEmbeddedPC() && frmpc != null && topc != null && !ObjectUtils.equals(topc.pcFetchObjectId(), manager.getDetachedObjectId(frmpc))) {
                        intopc = null;
                     }

                     frmpc = manager.attach(frmpc, intopc, sm, fmd, false);
                  }

                  if (frmpc != topc) {
                     sm.settingObjectField(into, i, topc, frmpc, set);
                  }
               }
               break;
            default:
               throw new InternalException(fmd.toString());
         }

         return true;
      } else {
         return false;
      }
   }

   protected Object getReference(AttachManager manager, Object toAttach, OpenJPAStateManager sm, ValueMetaData vmd) {
      if (toAttach == null) {
         return null;
      } else if (!manager.getBroker().isNew(toAttach) && !manager.getBroker().isPersistent(toAttach)) {
         if (manager.getBroker().isDetached(toAttach)) {
            Object oid = manager.getDetachedObjectId(toAttach);
            if (oid != null) {
               return manager.getBroker().find(oid, false, (FindCallbacks)null);
            }
         }

         throw (new UserException(_loc.get("cant-cascade-attach", (Object)vmd))).setFailedObject(toAttach);
      } else {
         return toAttach;
      }
   }

   private void replaceCollection(AttachManager manager, Collection frmc, Collection toc, OpenJPAStateManager sm, FieldMetaData fmd) {
      if (frmc.isEmpty()) {
         if (!toc.isEmpty()) {
            toc.clear();
         }

      } else {
         boolean pc = fmd.getElement().isDeclaredTypePC();
         if (pc) {
            frmc = this.attachCollection(manager, frmc, sm, fmd);
         }

         toc.retainAll(frmc);
         if (frmc.size() != toc.size()) {
            Iterator i = frmc.iterator();

            while(i.hasNext()) {
               Object ob = i.next();
               if (!toc.contains(ob)) {
                  toc.add(ob);
               }
            }
         }

      }
   }

   protected Collection attachCollection(AttachManager manager, Collection orig, OpenJPAStateManager sm, FieldMetaData fmd) {
      Collection coll = this.copyCollection(manager, orig, fmd);
      ValueMetaData vmd = fmd.getElement();
      if (!vmd.isDeclaredTypePC()) {
         return coll;
      } else {
         coll.clear();

         Object elem;
         for(Iterator itr = orig.iterator(); itr.hasNext(); coll.add(elem)) {
            if (vmd.getCascadeAttach() == 0) {
               elem = this.getReference(manager, itr.next(), sm, vmd);
            } else {
               elem = manager.attach(itr.next(), (PersistenceCapable)null, sm, vmd, false);
            }
         }

         return coll;
      }
   }

   private Collection copyCollection(AttachManager manager, Collection orig, FieldMetaData fmd) {
      Collection coll = manager.getProxyManager().copyCollection(orig);
      if (coll == null) {
         throw new UserException(_loc.get("not-copyable", (Object)fmd));
      } else {
         return coll;
      }
   }

   private Collection replaceList(AttachManager manager, Collection frml, Collection tol, OpenJPAStateManager sm, FieldMetaData fmd) {
      boolean pc = fmd.getElement().isDeclaredTypePC();
      if (pc) {
         frml = this.attachCollection(manager, frml, sm, fmd);
      }

      if (frml.size() < tol.size()) {
         return pc ? frml : this.copyCollection(manager, frml, fmd);
      } else {
         Iterator frmi = frml.iterator();
         Iterator toi = tol.iterator();

         while(toi.hasNext()) {
            if (!equals(frmi.next(), toi.next(), pc)) {
               return pc ? frml : this.copyCollection(manager, frml, fmd);
            }
         }

         while(frmi.hasNext()) {
            tol.add(frmi.next());
         }

         return tol;
      }
   }

   private void replaceMap(AttachManager manager, Map frmm, Map tom, OpenJPAStateManager sm, FieldMetaData fmd) {
      if (frmm.isEmpty()) {
         if (!tom.isEmpty()) {
            tom.clear();
         }

      } else {
         boolean keyPC = fmd.getKey().isDeclaredTypePC();
         boolean valPC = fmd.getElement().isDeclaredTypePC();
         if (keyPC || valPC) {
            frmm = this.attachMap(manager, frmm, sm, fmd);
         }

         Iterator i = frmm.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            do {
               if (!i.hasNext()) {
                  if (tom.size() != frmm.size()) {
                     i = tom.keySet().iterator();

                     while(i.hasNext()) {
                        if (!frmm.containsKey(i.next())) {
                           i.remove();
                        }
                     }
                  }

                  return;
               }

               entry = (Map.Entry)i.next();
            } while(tom.containsKey(entry.getKey()) && equals(tom.get(entry.getKey()), entry.getValue(), valPC));

            tom.put(entry.getKey(), entry.getValue());
         }
      }
   }

   protected Map attachMap(AttachManager manager, Map orig, OpenJPAStateManager sm, FieldMetaData fmd) {
      Map map = manager.getProxyManager().copyMap(orig);
      if (map == null) {
         throw new UserException(_loc.get("not-copyable", (Object)fmd));
      } else {
         ValueMetaData keymd = fmd.getKey();
         ValueMetaData valmd = fmd.getElement();
         if (!keymd.isDeclaredTypePC() && !valmd.isDeclaredTypePC()) {
            return map;
         } else {
            Map.Entry entry;
            Object key;
            if (keymd.isDeclaredTypePC()) {
               map.clear();

               Object val;
               for(Iterator itr = orig.entrySet().iterator(); itr.hasNext(); map.put(key, val)) {
                  entry = (Map.Entry)itr.next();
                  key = entry.getKey();
                  if (keymd.getCascadeAttach() == 0) {
                     key = this.getReference(manager, key, sm, keymd);
                  } else {
                     key = manager.attach(key, (PersistenceCapable)null, sm, keymd, false);
                  }

                  val = entry.getValue();
                  if (valmd.isDeclaredTypePC()) {
                     if (valmd.getCascadeAttach() == 0) {
                        val = this.getReference(manager, val, sm, valmd);
                     } else {
                        val = manager.attach(val, (PersistenceCapable)null, sm, valmd, false);
                     }
                  }
               }
            } else {
               for(Iterator itr = map.entrySet().iterator(); itr.hasNext(); entry.setValue(key)) {
                  entry = (Map.Entry)itr.next();
                  if (valmd.getCascadeAttach() == 0) {
                     key = this.getReference(manager, entry.getValue(), sm, valmd);
                  } else {
                     key = manager.attach(entry.getValue(), (PersistenceCapable)null, sm, valmd, false);
                  }
               }
            }

            return map;
         }
      }
   }

   private Object replaceArray(AttachManager manager, Object frma, Object toa, OpenJPAStateManager sm, FieldMetaData fmd) {
      int len = Array.getLength(frma);
      boolean diff = toa == null || len != Array.getLength(toa);
      Object newa = Array.newInstance(fmd.getElement().getDeclaredType(), len);
      ValueMetaData vmd = fmd.getElement();
      boolean pc = vmd.isDeclaredTypePC();

      for(int i = 0; i < len; ++i) {
         Object elem = Array.get(frma, i);
         if (pc) {
            if (vmd.getCascadeAttach() == 0) {
               elem = this.getReference(manager, elem, sm, vmd);
            } else {
               elem = manager.attach(elem, (PersistenceCapable)null, sm, vmd, false);
            }
         }

         diff = diff || !equals(elem, Array.get(toa, i), pc);
         Array.set(newa, i, elem);
      }

      return diff ? newa : toa;
   }

   private static boolean equals(Object a, Object b, boolean pc) {
      if (a == b) {
         return true;
      } else {
         return !pc && a != null && b != null ? a.equals(b) : false;
      }
   }
}
