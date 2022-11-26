package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.map.IdentityMap;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.CallbackException;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.UserException;

class AttachManager {
   private static final Localizer _loc = Localizer.forPackage(AttachManager.class);
   private final BrokerImpl _broker;
   private final ProxyManager _proxy;
   private final OpCallbacks _call;
   private final boolean _copyNew;
   private final boolean _failFast;
   private final IdentityMap _attached = new IdentityMap();
   private AttachStrategy _version = null;
   private AttachStrategy _detach = null;

   public AttachManager(BrokerImpl broker, boolean copyNew, OpCallbacks call) {
      this._broker = broker;
      this._proxy = broker.getConfiguration().getProxyManagerInstance();
      this._call = call;
      this._copyNew = copyNew;
      this._failFast = (broker.getConfiguration().getMetaDataRepositoryInstance().getMetaDataFactory().getDefaults().getCallbackMode() & 2) != 0;
   }

   public OpCallbacks getBehavior() {
      return this._call;
   }

   public boolean getCopyNew() {
      return this._copyNew;
   }

   public Object attach(Object pc) {
      if (pc == null) {
         return null;
      } else {
         CallbackException excep = null;

         Object var4;
         try {
            Object var3 = this.attach(pc, (PersistenceCapable)null, (OpenJPAStateManager)null, (ValueMetaData)null, true);
            return var3;
         } catch (CallbackException var10) {
            excep = var10;
            var4 = null;
         } finally {
            List exceps = null;
            if (excep != null && this._failFast) {
               exceps = Collections.singletonList(excep);
            } else {
               exceps = this.invokeAfterAttach((List)null);
            }

            this._attached.clear();
            this.throwExceptions(exceps, (List)null, false);
         }

         return var4;
      }
   }

   public Object[] attachAll(Collection instances) {
      Object[] attached = new Object[instances.size()];
      List exceps = null;
      List failed = null;
      boolean opt = true;
      boolean failFast = false;

      try {
         int i = 0;

         for(Iterator itr = instances.iterator(); itr.hasNext(); ++i) {
            try {
               attached[i] = this.attach(itr.next(), (PersistenceCapable)null, (OpenJPAStateManager)null, (ValueMetaData)null, true);
            } catch (OpenJPAException var15) {
               if (opt && !(var15 instanceof OptimisticException)) {
                  opt = false;
               }

               if (opt && var15.getFailedObject() != null) {
                  failed = this.add(failed, var15.getFailedObject());
               }

               exceps = this.add(exceps, var15);
               if (var15 instanceof CallbackException && this._failFast) {
                  failFast = true;
                  break;
               }
            } catch (RuntimeException var16) {
               exceps = this.add(exceps, var16);
            }
         }
      } finally {
         if (!failFast && (exceps == null || exceps.size() < instances.size())) {
            exceps = this.invokeAfterAttach(exceps);
         }

         this._attached.clear();
      }

      this.throwExceptions(exceps, failed, opt);
      return attached;
   }

   private List invokeAfterAttach(List exceps) {
      Set entries = this._attached.entrySet();
      Iterator i = entries.iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         Object attached = entry.getValue();
         StateManagerImpl sm = this._broker.getStateManagerImpl(attached, true);
         if (!sm.isNew()) {
            try {
               this._broker.fireLifecycleEvent(attached, entry.getKey(), sm.getMetaData(), 16);
            } catch (RuntimeException var8) {
               exceps = this.add(exceps, var8);
               if (this._failFast && var8 instanceof CallbackException) {
                  break;
               }
            }
         }
      }

      return exceps;
   }

   private List add(List list, Object obj) {
      if (list == null) {
         list = new LinkedList();
      }

      ((List)list).add(obj);
      return (List)list;
   }

   private void throwExceptions(List exceps, List failed, boolean opt) {
      if (exceps != null) {
         if (exceps.size() == 1) {
            throw (RuntimeException)exceps.get(0);
         } else {
            Throwable[] t = (Throwable[])((Throwable[])exceps.toArray(new Throwable[exceps.size()]));
            if (opt && failed != null) {
               throw new OptimisticException(failed, t);
            } else if (opt) {
               throw new OptimisticException(t);
            } else {
               throw (new UserException(_loc.get("nested-exceps"))).setNestedThrowables(t);
            }
         }
      }
   }

   Object attach(Object toAttach, PersistenceCapable into, OpenJPAStateManager owner, ValueMetaData ownerMeta, boolean explicit) {
      if (toAttach == null) {
         return null;
      } else {
         Object attached = this._attached.get(toAttach);
         if (attached != null) {
            return attached;
         } else {
            int action = this.processArgument(toAttach);
            if ((action & 4) == 0) {
               return toAttach;
            } else {
               ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(ImplHelper.getManagedInstance(toAttach).getClass(), this._broker.getClassLoader(), true);
               return this.getStrategy(toAttach).attach(this, toAttach, meta, into, owner, ownerMeta, explicit);
            }
         }
      }
   }

   private int processArgument(Object obj) {
      return this._call == null ? 4 : this._call.processArgument(6, obj, this._broker.getStateManager(obj));
   }

   private AttachStrategy getStrategy(Object toAttach) {
      PersistenceCapable pc = ImplHelper.toPersistenceCapable(toAttach, this.getBroker().getConfiguration());
      if (pc.pcGetStateManager() instanceof AttachStrategy) {
         return (AttachStrategy)pc.pcGetStateManager();
      } else {
         Object obj = pc.pcGetDetachedState();
         if (obj instanceof AttachStrategy) {
            return (AttachStrategy)obj;
         } else if (obj != null && obj != PersistenceCapable.DESERIALIZED) {
            if (this._detach == null) {
               this._detach = new DetachedStateAttachStrategy();
            }

            return this._detach;
         } else {
            if (this._version == null) {
               this._version = new VersionAttachStrategy();
            }

            return this._version;
         }
      }
   }

   BrokerImpl getBroker() {
      return this._broker;
   }

   ProxyManager getProxyManager() {
      return this._proxy;
   }

   PersistenceCapable getAttachedCopy(Object pc) {
      return ImplHelper.toPersistenceCapable(this._attached.get(pc), this.getBroker().getConfiguration());
   }

   void setAttachedCopy(Object from, PersistenceCapable into) {
      this._attached.put(from, into);
   }

   void fireBeforeAttach(Object pc, ClassMetaData meta) {
      this._broker.fireLifecycleEvent(pc, (Object)null, meta, 15);
   }

   Object getDetachedObjectId(Object pc) {
      return pc == null ? null : this.getStrategy(pc).getDetachedObjectId(this, pc);
   }

   StateManagerImpl assertManaged(Object obj) {
      StateManagerImpl sm = this._broker.getStateManagerImpl(obj, true);
      if (sm == null) {
         throw (new UserException(_loc.get("not-managed", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
      } else {
         return sm;
      }
   }
}
