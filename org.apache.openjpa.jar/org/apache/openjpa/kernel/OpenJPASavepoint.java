package org.apache.openjpa.kernel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OpenJPASavepoint implements Serializable {
   private final Broker _broker;
   private final String _name;
   private final boolean _copy;
   private Map _saved;

   public OpenJPASavepoint(Broker broker, String name, boolean copy) {
      this._broker = broker;
      this._name = name;
      this._copy = copy;
   }

   public Broker getBroker() {
      return this._broker;
   }

   public String getName() {
      return this._name;
   }

   public boolean getCopyFieldState() {
      return this._copy;
   }

   protected Map getStates() {
      return this._saved;
   }

   public void save(Collection states) {
      if (this._saved != null) {
         throw new IllegalStateException();
      } else {
         this._saved = new HashMap((int)((double)states.size() * 1.33 + 1.0));
         Iterator i = states.iterator();

         while(i.hasNext()) {
            StateManagerImpl sm = (StateManagerImpl)i.next();
            this._saved.put(sm, new SavepointFieldManager(sm, this._copy));
         }

      }
   }

   public void release(boolean user) {
      this._saved = null;
   }

   public Collection rollback(Collection previous) {
      Object saved;
      if (previous.isEmpty()) {
         saved = this._saved;
      } else {
         saved = new HashMap();
         Iterator i = previous.iterator();

         while(i.hasNext()) {
            ((Map)saved).putAll(((OpenJPASavepoint)i.next()).getStates());
         }

         ((Map)saved).putAll(this._saved);
      }

      this._saved = null;
      return ((Map)saved).values();
   }
}
