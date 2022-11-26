package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ChangeTracker;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.MapChangeTracker;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.Proxies;
import org.apache.openjpa.util.Proxy;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.UserException;

class SingleFieldManager extends TransferFieldManager implements Serializable {
   private static final Localizer _loc = Localizer.forPackage(SingleFieldManager.class);
   private final StateManagerImpl _sm;
   private final BrokerImpl _broker;

   public SingleFieldManager(StateManagerImpl sm, BrokerImpl broker) {
      this._sm = sm;
      this._broker = broker;
   }

   public boolean proxy(boolean reset, boolean replaceNull) {
      FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
      Proxy proxy = null;
      boolean ret = false;
      switch (fmd.getDeclaredTypeCode()) {
         case 8:
            if (this.objval == null) {
               return false;
            }

            proxy = this.checkProxy();
            if (proxy == null) {
               proxy = this.getProxyManager().newCustomProxy(this.objval, this._sm.getBroker().getConfiguration().getCompatibilityInstance().getAutoOff());
               ret = proxy != null;
            }
            break;
         case 12:
            if (this.objval == null && !replaceNull) {
               return false;
            }

            proxy = this.checkProxy();
            if (proxy == null) {
               proxy = (Proxy)this._sm.newFieldProxy(this.field);
               if (this.objval != null) {
                  ((Collection)proxy).addAll((Collection)this.objval);
               }

               ret = true;
            }
            break;
         case 13:
            if (this.objval == null && !replaceNull) {
               return false;
            }

            proxy = this.checkProxy();
            if (proxy == null) {
               proxy = (Proxy)this._sm.newFieldProxy(this.field);
               if (this.objval != null) {
                  ((Map)proxy).putAll((Map)this.objval);
               }

               ret = true;
            }
            break;
         case 14:
            if (this.objval == null) {
               return false;
            }

            proxy = this.checkProxy();
            if (proxy == null) {
               proxy = (Proxy)this._sm.newFieldProxy(this.field);
               ((Date)proxy).setTime(((Date)this.objval).getTime());
               if (proxy instanceof Timestamp && this.objval instanceof Timestamp) {
                  ((Timestamp)proxy).setNanos(((Timestamp)this.objval).getNanos());
               }

               ret = true;
            }
            break;
         case 28:
            if (this.objval == null) {
               return false;
            }

            proxy = this.checkProxy();
            if (proxy == null) {
               proxy = (Proxy)this._sm.newFieldProxy(this.field);
               ((Calendar)proxy).setTime(((Calendar)this.objval).getTime());
               ret = true;
            }
      }

      if (proxy != null) {
         proxy.setOwner(this._sm, this.field);
         ChangeTracker tracker = proxy.getChangeTracker();
         if (reset && tracker != null) {
            if (fmd.getDeclaredTypeCode() == 13) {
               boolean keys = fmd.getKey().getValueMappedBy() == null;
               ((MapChangeTracker)tracker).setTrackKeys(keys);
            }

            tracker.startTracking();
         }

         this.objval = proxy;
      }

      return ret;
   }

   private Proxy checkProxy() {
      if (!(this.objval instanceof Proxy)) {
         return null;
      } else {
         Proxy proxy = (Proxy)this.objval;
         return proxy.getOwner() != null && !Proxies.isOwner(proxy, this._sm, this.field) ? null : proxy;
      }
   }

   public void unproxy() {
      if (this.objval != null) {
         FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
         switch (fmd.getDeclaredTypeCode()) {
            case 8:
            case 12:
            case 13:
            case 14:
               if (this.objval instanceof Proxy) {
                  Proxy proxy = (Proxy)this.objval;
                  proxy.setOwner((OpenJPAStateManager)null, -1);
                  if (proxy.getChangeTracker() != null) {
                     proxy.getChangeTracker().stopTracking();
                  }
               }
            case 9:
            case 10:
            case 11:
            default:
         }
      }
   }

   public void releaseEmbedded() {
      if (this.objval != null) {
         FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
               if (fmd.getElement().isEmbeddedPC()) {
                  this.releaseEmbedded(fmd.getElement(), (Object[])((Object[])this.objval));
               }
               break;
            case 12:
               if (fmd.getElement().isEmbeddedPC()) {
                  this.releaseEmbedded(fmd.getElement(), (Collection)this.objval);
               }
               break;
            case 13:
               if (fmd.getKey().isEmbeddedPC()) {
                  this.releaseEmbedded(fmd.getKey(), (Collection)((Map)this.objval).keySet());
               }

               if (fmd.getElement().isEmbeddedPC()) {
                  this.releaseEmbedded(fmd.getElement(), ((Map)this.objval).values());
               }
            case 14:
            default:
               break;
            case 15:
               if (fmd.isEmbeddedPC()) {
                  this.releaseEmbedded(fmd, (Object)this.objval);
               }
         }

      }
   }

   private void releaseEmbedded(ValueMetaData vmd, Object[] objs) {
      for(int i = 0; i < objs.length; ++i) {
         this.releaseEmbedded(vmd, objs[i]);
      }

   }

   private void releaseEmbedded(ValueMetaData vmd, Collection objs) {
      Iterator itr = objs.iterator();

      while(itr.hasNext()) {
         this.releaseEmbedded(vmd, itr.next());
      }

   }

   private void releaseEmbedded(ValueMetaData vmd, Object obj) {
      if (obj != null) {
         StateManagerImpl sm = this._broker.getStateManagerImpl(obj, false);
         if (sm != null && sm.getOwner() == this._sm && sm.getOwnerIndex() == vmd.getFieldMetaData().getIndex()) {
            sm.release(true);
         }

      }
   }

   public void persist(OpCallbacks call) {
      if (this.objval != null) {
         FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
               this._broker.persistAll(Arrays.asList((Object[])((Object[])this.objval)), true, call);
               break;
            case 12:
               this._broker.persistAll((Collection)this.objval, true, call);
               break;
            case 13:
               if (fmd.getKey().getCascadePersist() == 1) {
                  this._broker.persistAll(((Map)this.objval).keySet(), true, call);
               }

               if (fmd.getElement().getCascadePersist() == 1) {
                  this._broker.persistAll(((Map)this.objval).values(), true, call);
               }
               break;
            case 15:
            case 27:
               if (!this._broker.isDetachedNew() && this._broker.isDetached(this.objval)) {
                  return;
               }

               this._broker.persist(this.objval, true, call);
         }

      }
   }

   public void delete(OpCallbacks call) {
      this.delete(true, call);
   }

   public void dereferenceDependent() {
      this.delete(false, (OpCallbacks)null);
   }

   private void delete(boolean immediate, OpCallbacks call) {
      if (this.objval != null) {
         FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
         if (fmd.getCascadeDelete() != 0) {
            if ((immediate || fmd.isEmbeddedPC()) && fmd.getCascadeDelete() == 1) {
               this.delete(fmd, (Object)this.objval, call);
            } else if (fmd.getCascadeDelete() == 2) {
               this.dereferenceDependent(fmd.getExternalValue(this.objval, this._broker));
            }

         } else {
            Object external = null;
            ValueMetaData vmd = fmd.getKey();
            if ((immediate || vmd.isEmbeddedPC()) && vmd.getCascadeDelete() == 1) {
               this.delete(vmd, (Collection)((Map)this.objval).keySet(), call);
            } else if (vmd.getCascadeDelete() == 2) {
               external = fmd.getExternalValue(this.objval, this._broker);
               if (external == null) {
                  return;
               }

               this.dereferenceDependent((Collection)((Map)external).keySet());
            }

            vmd = fmd.getElement();
            if ((immediate || vmd.isEmbeddedPC()) && vmd.getCascadeDelete() == 1) {
               switch (fmd.getDeclaredTypeCode()) {
                  case 11:
                     this.delete(vmd, (Object[])((Object[])this.objval), call);
                     break;
                  case 12:
                     this.delete(vmd, (Collection)this.objval, call);
                     break;
                  case 13:
                     this.delete(vmd, ((Map)this.objval).values(), call);
               }
            } else if (vmd.getCascadeDelete() == 2) {
               if (external == null) {
                  external = fmd.getExternalValue(this.objval, this._broker);
                  if (external == null) {
                     return;
                  }
               }

               switch (fmd.getTypeCode()) {
                  case 11:
                     this.dereferenceDependent((Object[])((Object[])external));
                     break;
                  case 12:
                     this.dereferenceDependent((Collection)external);
                     break;
                  case 13:
                     this.dereferenceDependent(((Map)external).values());
               }
            }

         }
      }
   }

   private void delete(ValueMetaData vmd, Object[] objs, OpCallbacks call) {
      for(int i = 0; i < objs.length; ++i) {
         this.delete(vmd, objs[i], call);
      }

   }

   private void delete(ValueMetaData vmd, Collection objs, OpCallbacks call) {
      Iterator itr = objs.iterator();

      while(itr.hasNext()) {
         this.delete(vmd, itr.next(), call);
      }

   }

   void delete(ValueMetaData vmd, Object obj, OpCallbacks call) {
      if (obj != null) {
         StateManagerImpl sm = this._broker.getStateManagerImpl(obj, false);
         if (sm != null && (sm.getOwner() == null || !vmd.isEmbeddedPC() || sm.getOwner() == this._sm && sm.getOwnerIndex() == vmd.getFieldMetaData().getIndex())) {
            this._broker.delete(sm.getManagedInstance(), sm, call);
         }

      }
   }

   private void dereferenceDependent(Object[] objs) {
      for(int i = 0; i < objs.length; ++i) {
         this.dereferenceDependent(objs[i]);
      }

   }

   private void dereferenceDependent(Collection objs) {
      Iterator itr = objs.iterator();

      while(itr.hasNext()) {
         this.dereferenceDependent(itr.next());
      }

   }

   void dereferenceDependent(Object obj) {
      if (obj != null) {
         StateManagerImpl sm = this._broker.getStateManagerImpl(obj, false);
         if (sm != null) {
            sm.setDereferencedDependent(true, true);
         }

      }
   }

   public void gatherCascadeRefresh(OpCallbacks call) {
      if (this.objval != null) {
         FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
         switch (fmd.getDeclaredTypeCode()) {
            case 11:
               this.gatherCascadeRefresh((Object[])((Object[])this.objval), call);
               break;
            case 12:
               this.gatherCascadeRefresh((Collection)this.objval, call);
               break;
            case 13:
               if (fmd.getKey().getCascadeRefresh() == 1) {
                  this.gatherCascadeRefresh((Collection)((Map)this.objval).keySet(), call);
               }

               if (fmd.getElement().getCascadeRefresh() == 1) {
                  this.gatherCascadeRefresh(((Map)this.objval).values(), call);
               }
               break;
            case 15:
            case 27:
               this._broker.gatherCascadeRefresh(this.objval, call);
         }

      }
   }

   private void gatherCascadeRefresh(Object[] arr, OpCallbacks call) {
      for(int i = 0; i < arr.length; ++i) {
         this._broker.gatherCascadeRefresh(arr[i], call);
      }

   }

   private void gatherCascadeRefresh(Collection coll, OpCallbacks call) {
      Iterator itr = coll.iterator();

      while(itr.hasNext()) {
         this._broker.gatherCascadeRefresh(itr.next(), call);
      }

   }

   public boolean preFlush(boolean logical, OpCallbacks call) {
      FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
      if (fmd.getDeclaredTypeCode() < 8) {
         return false;
      } else {
         boolean ret = this.preFlush(fmd, logical, call);
         InverseManager manager = this._broker.getInverseManager();
         if (manager != null) {
            manager.correctRelations(this._sm, fmd, this.objval);
         }

         return ret;
      }
   }

   public boolean isDefaultValue() {
      return this.dblval == 0.0 && this.longval == 0L && (this.objval == null || "".equals(this.objval));
   }

   public void serialize(ObjectOutput out, boolean def) throws IOException {
      FieldMetaData fmd = this._sm.getMetaData().getField(this.field);
      switch (fmd.getDeclaredTypeCode()) {
         case 0:
            out.writeBoolean(!def && this.longval == 1L);
            break;
         case 1:
            out.writeByte(def ? 0 : (byte)((int)this.longval));
            break;
         case 2:
            out.writeChar(def ? 0 : (char)((int)this.longval));
            break;
         case 3:
            out.writeDouble(def ? 0.0 : this.dblval);
            break;
         case 4:
            out.writeFloat(def ? 0.0F : (float)this.dblval);
            break;
         case 5:
            out.writeInt(def ? 0 : (int)this.longval);
            break;
         case 6:
            out.writeLong(def ? 0L : this.longval);
            break;
         case 7:
            out.writeShort(def ? 0 : (short)((int)this.longval));
            break;
         default:
            out.writeObject(def ? null : this.objval);
      }

   }

   private boolean preFlush(FieldMetaData fmd, boolean logical, OpCallbacks call) {
      if (this.objval == null) {
         if (fmd.getNullValue() != 2 && fmd.getDeclaredTypeCode() != 29) {
            return false;
         } else {
            throw (new InvalidStateException(_loc.get("null-value", fmd.getName(), this._sm.getManagedInstance()))).setFatal(true);
         }
      } else if (fmd.getManagement() != 3) {
         return false;
      } else {
         if (fmd.getDeclaredTypeCode() == 29) {
            this._sm.assertNotManagedObjectId(this.objval);
            if (this._sm.getObjectId() != null && !this.objval.equals(((ObjectId)this._sm.getObjectId()).getId())) {
               throw (new InvalidStateException(_loc.get("changed-oid", this._sm.getObjectId(), this.objval, Exceptions.toString(this._sm.getManagedInstance())))).setFatal(true);
            }
         }

         if (this.preFlush(fmd, fmd.getDeclaredTypeCode(), fmd.getKey().getDeclaredTypeCode(), fmd.getElement().getDeclaredTypeCode(), false, logical, call)) {
            return true;
         } else {
            if (fmd.isExternalized()) {
               this.preFlush(fmd, fmd.getTypeCode(), fmd.getKey().getTypeCode(), fmd.getElement().getTypeCode(), true, logical, call);
            }

            return false;
         }
      }
   }

   private boolean preFlush(FieldMetaData fmd, int type, int keyType, int elemType, boolean external, boolean logical, OpCallbacks call) {
      Object val = this.objval;
      if (val == null) {
         return false;
      } else {
         boolean copy = false;
         boolean flushed;
         switch (type) {
            case 11:
               if (fmd.getElement().isEmbeddedPC()) {
                  this.embed(fmd.getElement(), (Object[])((Object[])val));
               } else if (elemType == 15 || elemType == 27) {
                  if (external) {
                     val = fmd.getExternalValue(val, this._broker);
                  }

                  if (val != null) {
                     this.preFlushPCs(fmd.getElement(), (Object[])((Object[])val), logical, call);
                  }
               }
               break;
            case 12:
               if (fmd.getElement().isEmbeddedPC()) {
                  this.objval = this.embed(fmd.getElement(), (Collection)val);
                  copy = true;
               } else if (elemType == 15 || elemType == 27) {
                  flushed = false;
                  if (external) {
                     val = fmd.getExternalValue(val, this._broker);
                  } else if (val instanceof Proxy) {
                     ChangeTracker ct = ((Proxy)val).getChangeTracker();
                     if (ct != null && ct.isTracking()) {
                        this.preFlushPCs(fmd.getElement(), ct.getAdded(), logical, call);
                        this.preFlushPCs(fmd.getElement(), ct.getChanged(), logical, call);
                        flushed = true;
                     }
                  }

                  if (!flushed && val != null) {
                     this.preFlushPCs(fmd.getElement(), (Collection)val, logical, call);
                  }
               }
               break;
            case 13:
               flushed = fmd.getKey().isEmbeddedPC();
               boolean valEmbed = fmd.getElement().isEmbeddedPC();
               if (flushed || valEmbed) {
                  this.objval = this.embed(fmd, (Map)val, flushed, valEmbed);
                  copy = flushed;
               }

               boolean flushed;
               MapChangeTracker ct;
               if (!flushed && (keyType == 15 || keyType == 27)) {
                  flushed = false;
                  if (external) {
                     val = fmd.getExternalValue(val, this._broker);
                     external = false;
                  } else if (val instanceof Proxy) {
                     ct = (MapChangeTracker)((Proxy)val).getChangeTracker();
                     if (ct != null && ct.isTracking() && ct.getTrackKeys()) {
                        this.preFlushPCs(fmd.getKey(), ct.getAdded(), logical, call);
                        this.preFlushPCs(fmd.getKey(), ct.getChanged(), logical, call);
                        flushed = true;
                     }
                  }

                  if (!flushed && val != null) {
                     this.preFlushPCs(fmd.getKey(), (Collection)((Map)val).keySet(), logical, call);
                  }
               }

               if (!valEmbed && (elemType == 15 || elemType == 27)) {
                  flushed = false;
                  if (external) {
                     val = fmd.getExternalValue(val, this._broker);
                  } else if (val instanceof Proxy) {
                     ct = (MapChangeTracker)((Proxy)val).getChangeTracker();
                     if (ct != null && ct.isTracking()) {
                        if (ct.getTrackKeys()) {
                           this.preFlushPCs(fmd.getElement(), ct.getAdded(), (Map)val, logical, call);
                           this.preFlushPCs(fmd.getElement(), ct.getChanged(), (Map)val, logical, call);
                        } else {
                           this.preFlushPCs(fmd.getElement(), ct.getAdded(), logical, call);
                           this.preFlushPCs(fmd.getElement(), ct.getChanged(), logical, call);
                        }

                        flushed = true;
                     }
                  }

                  if (!flushed && val != null) {
                     this.preFlushPCs(fmd.getElement(), ((Map)val).values(), logical, call);
                  }
               }
               break;
            case 15:
               if (fmd.isEmbeddedPC()) {
                  this.objval = this.embed(fmd, (Object)val);
                  copy = true;
               } else {
                  if (external) {
                     val = fmd.getExternalValue(val, this._broker);
                  }

                  if (val != null) {
                     this.preFlushPC(fmd, val, logical, call);
                  }
               }
               break;
            case 27:
               if (external) {
                  val = fmd.getExternalValue(val, this._broker);
               }

               if (val != null) {
                  this.preFlushPC(fmd, val, logical, call);
               }
         }

         return copy;
      }
   }

   private void preFlushPCs(ValueMetaData vmd, Collection keys, Map map, boolean logical, OpCallbacks call) {
      Iterator itr = keys.iterator();

      while(itr.hasNext()) {
         this.preFlushPC(vmd, map.get(itr.next()), logical, call);
      }

   }

   private void preFlushPCs(ValueMetaData vmd, Object[] objs, boolean logical, OpCallbacks call) {
      for(int i = 0; i < objs.length; ++i) {
         this.preFlushPC(vmd, objs[i], logical, call);
      }

   }

   private void preFlushPCs(ValueMetaData vmd, Collection objs, boolean logical, OpCallbacks call) {
      Iterator itr = objs.iterator();

      while(itr.hasNext()) {
         this.preFlushPC(vmd, itr.next(), logical, call);
      }

   }

   private void preFlushPC(ValueMetaData vmd, Object obj, boolean logical, OpCallbacks call) {
      if (obj != null) {
         OpenJPAStateManager sm;
         if (vmd.getCascadePersist() == 0) {
            if (!this._broker.isDetachedNew() && this._broker.isDetached(obj)) {
               return;
            }

            sm = this._broker.getStateManager(obj);
            if (sm == null || !sm.isPersistent()) {
               throw (new InvalidStateException(_loc.get("cant-cascade-persist", (Object)vmd))).setFailedObject(obj);
            }
         } else {
            sm = this._broker.getStateManager(obj);
            if (sm == null || !sm.isProvisional()) {
               sm = this._broker.persist(obj, (Object)null, true, call);
            }
         }

         if (sm != null) {
            if (sm.isDeleted() && (this._broker.getInverseManager() == null || vmd.getFieldMetaData().getInverseMetaDatas().length == 0)) {
               throw (new UserException(_loc.get("ref-to-deleted", Exceptions.toString(obj), vmd, Exceptions.toString(this._sm.getManagedInstance())))).setFailedObject(obj);
            }

            StateManagerImpl smimpl = (StateManagerImpl)sm;
            smimpl.nonprovisional(logical, call);
            smimpl.setDereferencedDependent(false, true);
         }

      }
   }

   private void embed(ValueMetaData vmd, Object[] arr) {
      for(int i = 0; i < arr.length; ++i) {
         arr[i] = this.embed(vmd, arr[i]);
      }

   }

   private Collection embed(ValueMetaData vmd, Collection orig) {
      Collection coll = this.getProxyManager().copyCollection(orig);
      if (coll == null) {
         throw new UserException(_loc.get("not-copyable", (Object)vmd.getFieldMetaData()));
      } else {
         coll.clear();
         Iterator itr = orig.iterator();

         while(itr.hasNext()) {
            coll.add(this.embed(vmd, itr.next()));
         }

         return coll;
      }
   }

   private Map embed(FieldMetaData fmd, Map orig, boolean keyEmbed, boolean valEmbed) {
      Map map;
      Map.Entry entry;
      if (keyEmbed) {
         map = this.getProxyManager().copyMap(orig);
         if (map == null) {
            throw new UserException(_loc.get("not-copyable", (Object)fmd));
         }

         map.clear();

         Object key;
         Object val;
         for(Iterator itr = orig.entrySet().iterator(); itr.hasNext(); map.put(key, val)) {
            entry = (Map.Entry)itr.next();
            key = this.embed(fmd.getKey(), entry.getKey());
            val = entry.getValue();
            if (valEmbed) {
               val = this.embed(fmd.getElement(), val);
            }
         }
      } else {
         map = orig;
         Iterator itr = orig.entrySet().iterator();

         while(itr.hasNext()) {
            entry = (Map.Entry)itr.next();
            entry.setValue(this.embed(fmd.getElement(), entry.getValue()));
         }
      }

      return map;
   }

   private Object embed(ValueMetaData vmd, Object obj) {
      return obj == null ? null : this._broker.embed(obj, (Object)null, this._sm, vmd).getManagedInstance();
   }

   private ProxyManager getProxyManager() {
      return this._broker.getConfiguration().getProxyManagerInstance();
   }
}
