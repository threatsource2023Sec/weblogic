package org.apache.openjpa.kernel;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.rop.ListResultObjectProvider;
import org.apache.openjpa.lib.rop.RangeResultObjectProvider;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class MethodStoreQuery extends AbstractStoreQuery {
   public static final String LANGUAGE = "openjpa.MethodQL";
   private static final Class[] ARGS_DATASTORE;
   private static final Class[] ARGS_INMEM;
   private static final int OBJ_INDEX = 3;
   private static final Localizer _loc;
   private LinkedMap _params = null;

   public void invalidateCompilation() {
      if (this._params != null) {
         this._params.clear();
      }

   }

   public StoreQuery.Executor newInMemoryExecutor(ClassMetaData meta, boolean subs) {
      return new MethodExecutor(this, meta, subs, true);
   }

   public StoreQuery.Executor newDataStoreExecutor(ClassMetaData meta, boolean subs) {
      return new MethodExecutor(this, meta, subs, false);
   }

   public boolean supportsInMemoryExecution() {
      return true;
   }

   public boolean supportsDataStoreExecution() {
      return true;
   }

   private LinkedMap bindParameterTypes() {
      this.ctx.lock();

      LinkedMap var2;
      try {
         if (this._params != null) {
            LinkedMap var10 = this._params;
            return var10;
         }

         String params = this.ctx.getParameterDeclaration();
         if (params != null) {
            List decs = Filters.parseDeclaration(params, ',', "parameters");
            if (this._params == null) {
               this._params = new LinkedMap((int)((double)(decs.size() / 2) * 1.33 + 1.0));
            }

            for(int i = 0; i < decs.size(); i += 2) {
               String name = (String)decs.get(i);
               Class cls = this.ctx.classForName(name, (String[])null);
               if (cls == null) {
                  throw new UserException(_loc.get("bad-param-type", (Object)name));
               }

               this._params.put(decs.get(i + 1), cls);
            }

            LinkedMap var12 = this._params;
            return var12;
         }

         var2 = EMPTY_PARAMS;
      } finally {
         this.ctx.unlock();
      }

      return var2;
   }

   static {
      ARGS_DATASTORE = new Class[]{StoreContext.class, ClassMetaData.class, Boolean.TYPE, Map.class, FetchConfiguration.class};
      ARGS_INMEM = new Class[]{StoreContext.class, ClassMetaData.class, Boolean.TYPE, Object.class, Map.class, FetchConfiguration.class};
      _loc = Localizer.forPackage(MethodStoreQuery.class);
   }

   private static class MethodExecutor extends AbstractStoreQuery.AbstractExecutor implements StoreQuery.Executor {
      private final ClassMetaData _meta;
      private final boolean _subs;
      private final boolean _inMem;
      private Method _meth = null;

      public MethodExecutor(MethodStoreQuery q, ClassMetaData candidate, boolean subclasses, boolean inMem) {
         this._meta = candidate;
         this._subs = subclasses;
         this._inMem = inMem;
      }

      public ResultObjectProvider executeQuery(StoreQuery q, Object[] params, StoreQuery.Range range) {
         Object paramMap;
         if (params.length == 0) {
            paramMap = Collections.EMPTY_MAP;
         } else {
            Map paramTypes = q.getContext().getParameterTypes();
            paramMap = new HashMap((int)((double)params.length * 1.33 + 1.0));
            int idx = 0;

            for(Iterator itr = paramTypes.keySet().iterator(); itr.hasNext(); ++idx) {
               ((Map)paramMap).put(itr.next(), params[idx]);
            }
         }

         FetchConfiguration fetch = q.getContext().getFetchConfiguration();
         StoreContext sctx = q.getContext().getStoreContext();
         Object[] args;
         Object rop;
         if (this._inMem) {
            args = new Object[]{sctx, this._meta, this._subs ? Boolean.TRUE : Boolean.FALSE, null, paramMap, fetch};
            Iterator itr = null;
            Collection coll = q.getContext().getCandidateCollection();
            if (coll == null) {
               Extent ext = q.getContext().getQuery().getCandidateExtent();
               itr = ext.iterator();
            } else {
               itr = coll.iterator();
            }

            List results = new ArrayList();

            try {
               while(itr.hasNext()) {
                  Object obj = itr.next();
                  if (obj != null && this._meta.getDescribedType().isInstance(obj)) {
                     args[3] = obj;
                     if ((Boolean)this.invoke(q, args)) {
                        results.add(obj);
                     }
                  }
               }
            } finally {
               ImplHelper.close(itr);
            }

            rop = new ListResultObjectProvider(results);
         } else {
            args = new Object[]{sctx, this._meta, this._subs ? Boolean.TRUE : Boolean.FALSE, paramMap, fetch};
            rop = (ResultObjectProvider)this.invoke(q, args);
         }

         if (range.start != 0L || range.end != Long.MAX_VALUE) {
            rop = new RangeResultObjectProvider((ResultObjectProvider)rop, range.start, range.end);
         }

         return (ResultObjectProvider)rop;
      }

      private Object invoke(StoreQuery q, Object[] args) {
         this.validate(q);

         try {
            return this._meth.invoke((Object)null, args);
         } catch (OpenJPAException var4) {
            throw var4;
         } catch (Exception var5) {
            throw new UserException(MethodStoreQuery._loc.get("method-error", this._meth, Exceptions.toString((Collection)Arrays.asList(args))), var5);
         }
      }

      public void validate(StoreQuery q) {
         if (this._meth == null) {
            String methName = q.getContext().getQueryString();
            if (StringUtils.isEmpty(methName)) {
               throw new UserException(MethodStoreQuery._loc.get("no-method"));
            } else {
               int dotIdx = methName.lastIndexOf(46);
               Class cls;
               if (dotIdx == -1) {
                  cls = this._meta.getDescribedType();
               } else {
                  cls = q.getContext().classForName(methName.substring(0, dotIdx), (String[])null);
                  if (cls == null) {
                     throw new UserException(MethodStoreQuery._loc.get("bad-method-class", methName.substring(0, dotIdx), methName));
                  }

                  methName = methName.substring(dotIdx + 1);
               }

               Class[] types = this._inMem ? MethodStoreQuery.ARGS_INMEM : MethodStoreQuery.ARGS_DATASTORE;

               Method meth;
               try {
                  meth = cls.getMethod(methName, types);
               } catch (Exception var9) {
                  String msg = this._inMem ? "bad-inmem-method" : "bad-datastore-method";
                  throw new UserException(MethodStoreQuery._loc.get(msg, methName, cls));
               }

               if (!Modifier.isStatic(meth.getModifiers())) {
                  throw new UserException(MethodStoreQuery._loc.get("method-not-static", (Object)meth));
               } else if (!ResultObjectProvider.class.isAssignableFrom(meth.getReturnType())) {
                  throw new UserException(MethodStoreQuery._loc.get("method-return-type-invalid", meth, meth.getReturnType()));
               } else {
                  this._meth = meth;
               }
            }
         }
      }

      public LinkedMap getParameterTypes(StoreQuery q) {
         return ((MethodStoreQuery)q).bindParameterTypes();
      }
   }
}
