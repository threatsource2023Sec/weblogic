package weblogic.cache.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.cache.Action;
import weblogic.cache.util.AbstractAction;

public class WorkspaceFlushAction extends AbstractAction implements DivisibleAction {
   protected final Map adds;
   protected final Map updates;
   protected final Set removes;
   protected final boolean clear;

   public WorkspaceFlushAction(Workspace workspace) {
      this(workspace.getAdds(), workspace.getUpdates(), workspace.getRemoves().keySet(), workspace.isCleared());
   }

   protected WorkspaceFlushAction(Map adds, Map updates, Set removes, boolean clear) {
      this.adds = adds;
      this.updates = updates;
      this.removes = removes;
      this.clear = clear;
   }

   public Object run() {
      if (this.clear) {
         this.clear();
      }

      Iterator itr = this.adds.entrySet().iterator();

      Map.Entry entry;
      while(itr.hasNext()) {
         entry = (Map.Entry)itr.next();
         this.add(entry.getKey(), entry.getValue());
      }

      itr = this.updates.entrySet().iterator();

      while(itr.hasNext()) {
         entry = (Map.Entry)itr.next();
         this.update(entry.getKey(), entry.getValue());
      }

      itr = this.removes.iterator();

      while(itr.hasNext()) {
         this.remove(itr.next());
      }

      return null;
   }

   protected void clear() {
      this.cache.clear();
   }

   protected void add(Object key, Object value) {
      this.cache.put(key, value);
   }

   protected Object update(Object key, Object value) {
      return this.cache.put(key, value);
   }

   protected Object remove(Object key) {
      return this.cache.remove(key);
   }

   public void close() {
   }

   public boolean includesClear() {
      return this.clear;
   }

   public Set getAffectedKeys() {
      HashSet set = new HashSet();
      set.addAll(this.adds.keySet());
      set.addAll(this.updates.keySet());
      set.addAll(this.removes);
      return set;
   }

   public Action divide(Set keys) {
      Map dadds = null;
      Map dupdates = null;
      Set dremoves = null;
      Iterator itr = keys.iterator();

      while(true) {
         while(true) {
            while(itr.hasNext()) {
               Object key = itr.next();
               Object val = this.adds.get(key);
               if (val == null && !this.adds.containsKey(key)) {
                  val = this.updates.get(key);
                  if (val == null && !this.updates.containsKey(key)) {
                     if (this.removes.contains(key)) {
                        if (dremoves == null) {
                           dremoves = new HashSet();
                        }

                        dremoves.add(key);
                     }
                  } else {
                     if (dupdates == null) {
                        dupdates = new HashMap();
                     }

                     dupdates.put(key, val);
                  }
               } else {
                  if (dadds == null) {
                     dadds = new HashMap();
                  }

                  dadds.put(key, val);
               }
            }

            return this.newDividedInstance(keys, (Map)(dadds == null ? Collections.EMPTY_MAP : dadds), (Map)(dupdates == null ? Collections.EMPTY_MAP : dupdates), (Set)(dremoves == null ? Collections.EMPTY_SET : dremoves), this.clear);
         }
      }
   }

   protected Action newDividedInstance(Set keys, Map adds, Map updates, Set removes, boolean clear) {
      return new WorkspaceFlushAction(adds, updates, removes, clear);
   }
}
