package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.UserException;

class ManagedCache implements Serializable {
   private static final Localizer _loc = Localizer.forPackage(ManagedCache.class);
   private Map _main;
   private Map _conflicts = null;
   private Map _news = null;
   private Collection _embeds = null;
   private Collection _untracked = null;
   private BrokerImpl broker;

   ManagedCache(BrokerImpl broker) {
      this.broker = broker;
      this._main = broker.newManagedObjectCache();
   }

   public StateManagerImpl getById(Object oid, boolean allowNew) {
      if (oid == null) {
         return null;
      } else {
         StateManagerImpl sm = (StateManagerImpl)this._main.get(oid);
         StateManagerImpl sm2;
         if (sm != null) {
            if (sm.isNew() && !sm.isDeleted()) {
               return allowNew ? sm : null;
            }

            if (!allowNew || !sm.isDeleted()) {
               return sm;
            }

            if (this._conflicts != null) {
               sm2 = (StateManagerImpl)this._conflicts.get(oid);
               if (sm2 != null) {
                  return sm2;
               }
            }
         }

         if (allowNew && this._news != null && !this._news.isEmpty()) {
            sm2 = (StateManagerImpl)this._news.get(oid);
            if (sm2 != null) {
               return sm2;
            }
         }

         return sm;
      }
   }

   public void add(StateManagerImpl sm) {
      if (!sm.isIntercepting()) {
         if (this._untracked == null) {
            this._untracked = new HashSet();
         }

         this._untracked.add(sm);
      }

      if (sm.isPersistent() && !sm.isEmbedded()) {
         if (sm.isNew()) {
            if (this._news == null) {
               this._news = new HashMap();
            }

            this._news.put(sm.getId(), sm);
         } else {
            StateManagerImpl orig = (StateManagerImpl)this._main.put(sm.getObjectId(), sm);
            if (orig != null) {
               this._main.put(sm.getObjectId(), orig);
               throw (new UserException(_loc.get("dup-load", sm.getObjectId(), Exceptions.toString(orig.getManagedInstance())))).setFailedObject(sm.getManagedInstance());
            }
         }
      } else {
         if (this._embeds == null) {
            this._embeds = new ReferenceHashSet(2);
         }

         this._embeds.add(sm);
      }
   }

   public void remove(Object id, StateManagerImpl sm) {
      Object orig;
      if (sm.getObjectId() != null) {
         orig = this._main.remove(id);
         if (orig != sm) {
            if (orig != null) {
               this._main.put(id, orig);
            }

            if (this._conflicts != null) {
               orig = this._conflicts.remove(id);
               if (orig != null && orig != sm) {
                  this._conflicts.put(id, orig);
               }
            }
         }
      } else if ((this._embeds == null || !this._embeds.remove(sm)) && this._news != null) {
         orig = this._news.remove(id);
         if (orig != null && orig != sm) {
            this._news.put(id, orig);
         }
      }

      if (this._untracked != null) {
         this._untracked.remove(sm);
      }

   }

   public void persist(StateManagerImpl sm) {
      if (this._embeds != null) {
         this._embeds.remove(sm);
      }

   }

   public void assignObjectId(Object id, StateManagerImpl sm) {
      StateManagerImpl orig = null;
      if (this._news != null) {
         orig = (StateManagerImpl)this._news.remove(id);
         if (orig != null && orig != sm) {
            this._news.put(id, orig);
         }
      }

      orig = (StateManagerImpl)this._main.put(sm.getObjectId(), sm);
      if (orig != null) {
         this._main.put(sm.getObjectId(), orig);
         if (!orig.isDeleted()) {
            throw (new UserException(_loc.get("dup-oid-assign", sm.getObjectId(), Exceptions.toString(sm.getManagedInstance())))).setFailedObject(sm.getManagedInstance());
         }

         if (this._conflicts == null) {
            this._conflicts = new HashMap();
         }

         this._conflicts.put(sm.getObjectId(), sm);
      }

   }

   public void commitNew(Object id, StateManagerImpl sm) {
      StateManagerImpl orig;
      if (sm.getObjectId() == id) {
         orig = this._conflicts == null ? null : (StateManagerImpl)this._conflicts.remove(id);
         if (orig == sm) {
            orig = (StateManagerImpl)this._main.put(id, sm);
            if (orig != null && !orig.isDeleted()) {
               this._main.put(sm.getObjectId(), orig);
               throw (new UserException(_loc.get("dup-oid-assign", sm.getObjectId(), Exceptions.toString(sm.getManagedInstance())))).setFailedObject(sm.getManagedInstance()).setFatal(true);
            }
         }

      } else {
         if (this._news != null) {
            this._news.remove(id);
         }

         orig = (StateManagerImpl)this._main.put(sm.getObjectId(), sm);
         if (orig != null && orig != sm && !orig.isDeleted()) {
            this._main.put(sm.getObjectId(), orig);
            throw (new UserException(_loc.get("dup-oid-assign", sm.getObjectId(), Exceptions.toString(sm.getManagedInstance())))).setFailedObject(sm.getManagedInstance()).setFatal(true);
         }
      }
   }

   public Collection copy() {
      int size = this._main.size();
      if (this._conflicts != null) {
         size += this._conflicts.size();
      }

      if (this._news != null) {
         size += this._news.size();
      }

      if (this._embeds != null) {
         size += this._embeds.size();
      }

      if (size == 0) {
         return Collections.EMPTY_LIST;
      } else {
         List copy = new ArrayList(size);
         Iterator itr = this._main.values().iterator();

         while(itr.hasNext()) {
            copy.add(itr.next());
         }

         if (this._conflicts != null && !this._conflicts.isEmpty()) {
            itr = this._conflicts.values().iterator();

            while(itr.hasNext()) {
               copy.add(itr.next());
            }
         }

         if (this._news != null && !this._news.isEmpty()) {
            itr = this._news.values().iterator();

            while(itr.hasNext()) {
               copy.add(itr.next());
            }
         }

         if (this._embeds != null && !this._embeds.isEmpty()) {
            itr = this._embeds.iterator();

            while(itr.hasNext()) {
               copy.add(itr.next());
            }
         }

         return copy;
      }
   }

   public void clear() {
      this._main = this.broker.newManagedObjectCache();
      if (this._conflicts != null) {
         this._conflicts = null;
      }

      if (this._news != null) {
         this._news = null;
      }

      if (this._embeds != null) {
         this._embeds = null;
      }

      if (this._untracked != null) {
         this._untracked = null;
      }

   }

   public void clearNew() {
      if (this._news != null) {
         this._news = null;
      }

   }

   void dirtyCheck() {
      if (this._untracked != null) {
         Iterator iter = this._untracked.iterator();

         while(iter.hasNext()) {
            ((StateManagerImpl)iter.next()).dirtyCheck();
         }

      }
   }
}
