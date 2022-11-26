package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.util.ImplHelper;

class QueryResultCommand extends ResultCommand {
   private static final int FLAG_UNIQUE = 1;
   private static final int FLAG_AGG = 2;
   private static final int FLAG_GROUP = 3;
   private static final int FLAG_CANDIDATE = 4;
   private Query _query = null;
   private Object _params = null;
   private long _startIdx = 0L;
   private long _endIdx = Long.MAX_VALUE;
   private String _alias = null;
   private String[] _projAliases = null;
   private Class[] _projTypes = null;
   private int _flags = 0;
   private int _op = 1;
   private Class _resultClass = null;

   QueryResultCommand() {
      super((byte)16);
   }

   public QueryResultCommand(long brokerId, Query query, Object params, long startIdx, long endIdx) {
      super((byte)16, brokerId);
      this._query = query;
      this._params = params;
      this._startIdx = startIdx;
      this._endIdx = endIdx;
   }

   public String getAlias() {
      return this._alias;
   }

   public String[] getProjectionAliases() {
      return this._projAliases;
   }

   public Class[] getProjectionTypes() {
      return this._projTypes;
   }

   public boolean isAggregate() {
      return (this._flags & 2) > 0;
   }

   public boolean hasGrouping() {
      return (this._flags & 3) > 0;
   }

   public boolean isUnique() {
      return (this._flags & 1) > 0;
   }

   public int getOperation() {
      return this._op;
   }

   public Class getResultType() {
      return this._resultClass;
   }

   public boolean hasCandidateType() {
      return (this._flags & 4) > 0;
   }

   protected Result initialize(Broker broker) {
      ServerQueryInfo info = new ServerQueryInfo();
      info.query = broker.newQuery(this._query.getLanguage(), this._query);
      if (info.query.isUnique()) {
         this._flags |= 1;
      }

      this._resultClass = info.query.getResultType();
      if (info.query.getCandidateType() != null) {
         this._flags |= 4;
         Class resClass = info.query.getProjectionAliases().length == 0 ? null : Object[].class;
         info.query.setResultType(resClass);
      }

      info.query.setUnique(false);
      if (this._startIdx != 0L || this._endIdx != Long.MAX_VALUE) {
         info.query.setRange(this._startIdx, this._endIdx);
      }

      if (this._params instanceof Map) {
         Map map = (Map)this._params;
         restoreParameters(map, broker);
         info.list = (List)info.query.execute(map);
      } else {
         Object[] arr = (Object[])((Object[])this._params);
         restoreParameters(arr, broker);
         info.list = (List)info.query.execute(arr);
      }

      this._alias = info.query.getAlias();
      this._projAliases = info.query.getProjectionAliases();
      if (this._projAliases.length == 0) {
         this._projAliases = null;
      }

      this._projTypes = info.query.getProjectionTypes();
      if (this._projTypes.length == 0) {
         this._projTypes = null;
      }

      if (info.query.isAggregate()) {
         this._flags |= 2;
      }

      if (info.query.hasGrouping()) {
         this._flags |= 3;
      }

      this._op = info.query.getOperation();
      if (this.getInitializeOnly() && (this.isAggregate() || this.hasGrouping())) {
         this.setInitializeOnly(false);
      }

      return info;
   }

   protected Iterator iterator(Result rsrc, int startIndex) {
      ServerQueryInfo info = (ServerQueryInfo)rsrc;
      return info.list.listIterator(startIndex);
   }

   protected FetchConfiguration getFetchConfiguration(Result rsrc) {
      return ((ServerQueryInfo)rsrc).query.getFetchConfiguration();
   }

   protected void read(ObjectInput in) throws Exception {
      super.read(in);
      if (this.getInitialize()) {
         this._query = (Query)in.readObject();
         this._params = in.readObject();
         this._startIdx = in.readLong();
         this._endIdx = in.readLong();
      }

   }

   protected void write(ObjectOutput out) throws Exception {
      super.write(out);
      if (this.getInitialize()) {
         out.writeObject(this._query);
         if (this._params instanceof Map) {
            out.writeObject(replaceParameters((Map)this._params, this._query.getBroker()));
         } else {
            out.writeObject(replaceParameters((Object[])((Object[])this._params), this._query.getBroker()));
         }

         out.writeLong(this._startIdx);
         out.writeLong(this._endIdx);
      }

   }

   protected void readResponse(ObjectInput in) throws Exception {
      super.readResponse(in);
      if (this.getInitialize()) {
         this._alias = in.readUTF();
         if ("null".equals(this._alias)) {
            this._alias = null;
         }

         this._projAliases = (String[])((String[])in.readObject());
         this._projTypes = (Class[])((Class[])in.readObject());
         this._flags = in.readInt();
         this._op = in.readInt();
         this._resultClass = (Class)in.readObject();
      }

   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      super.writeResponse(out);
      if (this.getInitialize()) {
         out.writeUTF(this._alias == null ? "null" : this._alias);
         out.writeObject(this._projAliases);
         out.writeObject(this._projTypes);
         out.writeInt(this._flags);
         out.writeInt(this._op);
         out.writeObject(this._resultClass);
      }

   }

   private static Object[] replaceParameters(Object[] params, Broker broker) {
      if (params.length == 0) {
         return params;
      } else {
         Object[] replace = null;

         for(int i = 0; i < params.length; ++i) {
            Object param = replaceParameter(params[i], broker);
            if (param != params[i]) {
               if (replace == null) {
                  replace = new Object[params.length];
                  System.arraycopy(params, 0, replace, 0, params.length);
               }

               replace[i] = param;
            }
         }

         return replace == null ? params : replace;
      }
   }

   private static Map replaceParameters(Map params, Broker broker) {
      if (params.isEmpty()) {
         return params;
      } else {
         Map replace = null;
         Iterator itr = params.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            Object param = replaceParameter(entry.getValue(), broker);
            if (param != entry.getValue()) {
               if (replace == null) {
                  replace = new HashMap(params);
               }

               replace.put(entry.getKey(), param);
            }
         }

         return (Map)(replace == null ? params : replace);
      }
   }

   private static Object replaceParameter(Object param, Broker broker) {
      if (ImplHelper.isManageable(param)) {
         return replacePC(param, broker);
      } else if (param instanceof Collection) {
         Collection value = (Collection)param;
         Collection newCollection = new ArrayList(value.size());
         Iterator ci = value.iterator();

         while(ci.hasNext()) {
            newCollection.add(replacePC(ci.next(), broker));
         }

         return newCollection;
      } else if (!(param instanceof Map)) {
         return param;
      } else {
         Map map = (Map)param;
         Map newMap = new HashMap();
         Iterator mi = map.entrySet().iterator();

         while(mi.hasNext()) {
            Map.Entry value = (Map.Entry)mi.next();
            newMap.put(replacePC(value.getKey(), broker), replacePC(value.getValue(), broker));
         }

         return newMap;
      }
   }

   private static Object replacePC(Object pc, Broker broker) {
      Object oid = broker.getObjectId(pc);
      if (oid == null) {
         return pc;
      } else {
         PCParameter param = new PCParameter();
         param.oid = oid;
         return param;
      }
   }

   private static void restoreParameters(Object[] params, Broker broker) {
      for(int i = 0; i < params.length; ++i) {
         params[i] = restoreParameter(params[i], broker);
      }

   }

   private static void restoreParameters(Map params, Broker broker) {
      Iterator itr = params.entrySet().iterator();

      while(itr.hasNext()) {
         Map.Entry entry = (Map.Entry)itr.next();
         entry.setValue(replaceParameter(entry.getValue(), broker));
      }

   }

   private static Object restoreParameter(Object param, Broker broker) {
      if (param instanceof PCParameter) {
         return restorePC(param, broker);
      } else {
         Object pc;
         Object replace;
         if (param instanceof List) {
            List list = (List)param;
            ListIterator li = list.listIterator();

            while(li.hasNext()) {
               pc = li.next();
               replace = restorePC(pc, broker);
               if (replace != pc) {
                  li.set(replace);
               }
            }

            return list;
         } else if (param instanceof Collection) {
            Collection value = (Collection)param;
            Collection toAdd = null;
            Iterator ci = value.iterator();

            while(ci.hasNext()) {
               pc = ci.next();
               replace = restorePC(pc, broker);
               if (replace != pc) {
                  if (toAdd == null) {
                     toAdd = new ArrayList();
                  }

                  toAdd.add(replace);
                  ci.remove();
               }
            }

            if (toAdd != null) {
               value.addAll(toAdd);
            }

            return value;
         } else if (param instanceof Map) {
            Map toAdd = null;
            Map map = (Map)param;
            Iterator mi = map.entrySet().iterator();

            while(mi.hasNext()) {
               Map.Entry value = (Map.Entry)mi.next();
               replace = restorePC(value.getValue(), broker);
               if (replace != value.getValue()) {
                  value.setValue(replace);
               }

               replace = restorePC(value.getKey(), broker);
               if (replace != value.getKey()) {
                  if (toAdd == null) {
                     toAdd = new HashMap();
                  }

                  toAdd.put(replace, value.getValue());
                  mi.remove();
               }
            }

            if (toAdd != null) {
               map.putAll(toAdd);
            }

            return map;
         } else {
            return param;
         }
      }
   }

   private static Object restorePC(Object oid, Broker broker) {
      return !(oid instanceof PCParameter) ? oid : broker.find(((PCParameter)oid).oid, false, (FindCallbacks)null);
   }

   private static class ServerQueryInfo implements Result {
      public Query query;
      public List list;

      private ServerQueryInfo() {
         this.query = null;
         this.list = null;
      }

      public int size() {
         return this.list.size();
      }

      public void close() {
         ImplHelper.close(this.list);
      }

      // $FF: synthetic method
      ServerQueryInfo(Object x0) {
         this();
      }
   }

   private static class PCParameter implements Serializable {
      public Object oid;

      private PCParameter() {
      }

      // $FF: synthetic method
      PCParameter(Object x0) {
         this();
      }
   }
}
