package org.apache.openjpa.datacache;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.util.ImplHelper;

public class QueryKey implements Externalizable {
   private static Collection s_unmod = new HashSet();
   private String _candidateClassName;
   private boolean _subclasses;
   private Set _accessPathClassNames;
   private String _query;
   private boolean _ignoreChanges;
   private Map _params;
   private long _rangeStart;
   private long _rangeEnd;
   private int _timeout = -1;

   public static QueryKey newInstance(Query q) {
      return newInstance(q, (Object[])null);
   }

   public static QueryKey newInstance(Query q, Object[] args) {
      q.compile();
      return newInstance(q, false, (Object[])args, q.getCandidateType(), q.hasSubclasses(), q.getStartRange(), q.getEndRange());
   }

   public static QueryKey newInstance(Query q, Map args) {
      q.compile();
      return newInstance(q, false, (Map)args, q.getCandidateType(), q.hasSubclasses(), q.getStartRange(), q.getEndRange());
   }

   static QueryKey newInstance(QueryContext q, boolean packed, Object[] args, Class candidate, boolean subs, long startIdx, long endIdx) {
      QueryKey key = createKey(q, packed, candidate, subs, startIdx, endIdx);
      return key != null && setParams(key, q, args) ? key : null;
   }

   static QueryKey newInstance(QueryContext q, boolean packed, Map args, Class candidate, boolean subs, long startIdx, long endIdx) {
      QueryKey key = createKey(q, packed, candidate, subs, startIdx, endIdx);
      return key == null || args != null && !args.isEmpty() && !setParams(key, (StoreContext)q.getStoreContext(), (Map)(new HashMap(args))) ? null : key;
   }

   private static QueryKey createKey(QueryContext var0, boolean var1, Class var2, boolean var3, long var4, long var6) {
      // $FF: Couldn't be decompiled
   }

   private static boolean setParams(QueryKey key, QueryContext q, Object[] args) {
      if (args != null && args.length != 0) {
         Map types = q.getParameterTypes();
         Map map = new HashMap((int)((double)types.size() * 1.33 + 1.0));
         int idx = 0;

         for(Iterator iter = types.keySet().iterator(); iter.hasNext(); ++idx) {
            map.put(iter.next(), args[idx]);
         }

         return setParams(key, (StoreContext)q.getStoreContext(), (Map)map);
      } else {
         return true;
      }
   }

   private static boolean setParams(QueryKey key, StoreContext ctx, Map params) {
      if (params != null && !params.isEmpty()) {
         Iterator iter = params.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry e = (Map.Entry)iter.next();
            Object v = e.getValue();
            if (ImplHelper.isManageable(v)) {
               if (!ctx.isPersistent(v) || ctx.isNew(v) || ctx.isDeleted(v)) {
                  return false;
               }

               e.setValue(ctx.getObjectId(v));
            }

            if (v instanceof Collection) {
               Collection c = (Collection)v;
               boolean contentsAreDates = false;
               if (c.iterator().hasNext()) {
                  Object o = c.iterator().next();
                  if (ImplHelper.isManageable(o)) {
                     return false;
                  }

                  if (o instanceof Date) {
                     contentsAreDates = true;
                  }

                  if (contentsAreDates || !s_unmod.contains(c.getClass())) {
                     Object copy;
                     if (c instanceof SortedSet) {
                        copy = new TreeSet();
                     } else if (c instanceof Set) {
                        copy = new HashSet();
                     } else {
                        copy = new ArrayList(c.size());
                     }

                     if (contentsAreDates) {
                        Iterator itr2 = c.iterator();

                        while(itr2.hasNext()) {
                           ((Collection)copy).add(((Date)itr2.next()).clone());
                        }
                     } else {
                        ((Collection)copy).addAll(c);
                     }

                     e.setValue(copy);
                  }
               }
            } else if (v instanceof Date) {
               e.setValue(((Date)v).clone());
            }
         }

         key._params = params;
         return true;
      } else {
         return true;
      }
   }

   public String getCandidateTypeName() {
      return this._candidateClassName;
   }

   public int getTimeout() {
      return this._timeout;
   }

   public boolean changeInvalidatesQuery(Collection changed) {
      return intersects(this._accessPathClassNames, changed);
   }

   private static boolean intersects(Collection names, Collection changed) {
      Iterator iter = changed.iterator();

      Class cls;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         Class sup;
         for(cls = (Class)iter.next(); (sup = PCRegistry.getPersistentSuperclass(cls)) != null; cls = sup) {
         }
      } while(!names.contains(cls.getName()));

      return true;
   }

   public boolean equals(Object ob) {
      if (this == ob) {
         return true;
      } else if (ob != null && this.getClass() == ob.getClass()) {
         QueryKey other = (QueryKey)ob;
         return StringUtils.equals(this._candidateClassName, other._candidateClassName) && this._subclasses == other._subclasses && this._ignoreChanges == other._ignoreChanges && this._rangeStart == other._rangeStart && this._rangeEnd == other._rangeEnd && StringUtils.equals(this._query, other._query) && ObjectUtils.equals(this._params, other._params);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int code = 629 + this._candidateClassName.hashCode();
      if (this._query != null) {
         code = 37 * code + this._query.hashCode();
      }

      if (this._params != null) {
         code = 37 * code + this._params.hashCode();
      }

      return code;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(255);
      buf.append(super.toString()).append("[query:[").append(this._query).append("]").append(",access path:").append(this._accessPathClassNames).append(",subs:").append(this._subclasses).append(",ignoreChanges:").append(this._ignoreChanges).append(",startRange:").append(this._rangeStart).append(",endRange:").append(this._rangeEnd).append(",timeout:").append(this._timeout).append("]");
      return buf.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this._candidateClassName);
      out.writeBoolean(this._subclasses);
      out.writeObject(this._accessPathClassNames);
      out.writeObject(this._query);
      out.writeBoolean(this._ignoreChanges);
      out.writeObject(this._params);
      out.writeLong(this._rangeStart);
      out.writeLong(this._rangeEnd);
      out.writeInt(this._timeout);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this._candidateClassName = (String)in.readObject();
      this._subclasses = in.readBoolean();
      this._accessPathClassNames = (Set)in.readObject();
      this._query = (String)in.readObject();
      this._ignoreChanges = in.readBoolean();
      this._params = (Map)in.readObject();
      this._rangeStart = in.readLong();
      this._rangeEnd = in.readLong();
      this._timeout = in.readInt();
   }

   static {
      TreeSet s = new TreeSet();
      s_unmod.add(Collections.unmodifiableCollection(s).getClass());
      s_unmod.add(Collections.unmodifiableSet(s).getClass());
      s_unmod.add(Collections.unmodifiableSortedSet(s).getClass());
      List l = new LinkedList();
      s_unmod.add(Collections.unmodifiableList(l).getClass());
      List l = new ArrayList(0);
      s_unmod.add(Collections.unmodifiableList(l).getClass());
      s_unmod.add(Collections.EMPTY_SET.getClass());
      s_unmod.add(Collections.EMPTY_LIST.getClass());
   }
}
