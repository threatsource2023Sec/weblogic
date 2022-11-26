package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.jdo.JDOFatalDataStoreException;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public abstract class TopId implements Serializable {
   public TopId() {
   }

   public TopId(String binding) {
   }

   public TopId(Top obj) {
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof TopId;
      }
   }

   public int hashCode() {
      return 0;
   }

   public String toString() {
      return "";
   }

   protected abstract Pair prepareQuery();

   private Class getDataClass() {
      String myName = this.getClass().getName();

      try {
         return Class.forName(myName.substring(0, myName.length() - 2));
      } catch (ClassNotFoundException var3) {
         throw new JDOFatalDataStoreException("Id class not found", var3);
      }
   }

   public Object getObject(PersistenceManager pm) {
      return this.getObject(pm, false);
   }

   public Object getObject(PersistenceManager pm, boolean isDetach) {
      Query q = null;

      Object o;
      try {
         q = pm.newQuery(this.getDataClass());
         Pair p = this.prepareQuery();
         q.setFilter((String)p.getLeft());
         Map params = (Map)p.getRight();
         Collection results;
         if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator it = params.entrySet().iterator();

            while(it.hasNext()) {
               Map.Entry e = (Map.Entry)it.next();
               Object v = e.getValue();
               if (v != null) {
                  sb.append(v.getClass().getName());
                  sb.append(' ');
                  sb.append((String)e.getKey());
               }

               if (it.hasNext()) {
                  sb.append(", ");
               }
            }

            q.declareParameters(sb.toString());
            results = (Collection)q.executeWithMap(params);
         } else {
            results = (Collection)q.execute();
         }

         if (results == null || results.isEmpty()) {
            throw new JDOObjectNotFoundException();
         }

         o = results.iterator().next();
      } finally {
         if (q != null) {
            q.closeAll();
         }

      }

      return isDetach ? (o != null ? pm.detachCopy(o) : null) : o;
   }
}
