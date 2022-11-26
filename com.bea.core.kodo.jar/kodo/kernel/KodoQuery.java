package kodo.kernel;

import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.util.Map;
import kodo.profile.ProfilingResultList;
import org.apache.openjpa.kernel.QueryImpl;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.lib.rop.ResultList;

class KodoQuery extends QueryImpl implements ProfilingCapable {
   private transient KodoBroker _broker = null;

   public KodoQuery(KodoBroker broker, String language, StoreQuery storeQuery) {
      super(broker, language, storeQuery);
      this._broker = broker;
   }

   protected ResultList decorateResultList(ResultList res) {
      if (this._broker.getProfilingAgent() != null) {
         res = new ProfilingResultList((ResultList)res, this, this._broker);
      }

      return super.decorateResultList((ResultList)res);
   }

   public Object execute() {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Query.execute(...)", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var1));
         }
      }

      Object var5;
      try {
         var5 = super.execute();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var1));
         }

      }

      return var5;
   }

   public Object execute(Object[] params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.execute(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      Object var6;
      try {
         var6 = super.execute(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public Object execute(Map params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.execute(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      Object var6;
      try {
         var6 = super.execute(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public long deleteAll() {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Query.deleteAll(...)", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var1));
         }
      }

      long var5;
      try {
         var5 = super.deleteAll();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var1));
         }

      }

      return var5;
   }

   public long deleteAll(Object[] params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.deleteAll(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      long var6;
      try {
         var6 = super.deleteAll(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public long deleteAll(Map params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.deleteAll(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      long var6;
      try {
         var6 = super.deleteAll(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public long updateAll() {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Query.updateAll(...)", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var1));
         }
      }

      long var5;
      try {
         var5 = super.updateAll();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var1));
         }

      }

      return var5;
   }

   public long updateAll(Object[] params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.updateAll(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      long var6;
      try {
         var6 = super.updateAll(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public long updateAll(Map params) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            var2 = new MethodInfoImpl("Query.updateAll(...)", "");
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      long var6;
      try {
         var6 = super.updateAll(params);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var6;
   }

   public void compile() {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Query.compile()", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var1));
         }
      }

      try {
         super.compile();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var1));
         }

      }

   }

   public void closeAll() {
      MethodInfoImpl var1 = null;
      ProfilingAgent var2 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var2 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var2 != null) {
            var1 = new MethodInfoImpl("Query.closeAll()", "");
            var2.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var1));
         }
      }

      try {
         super.closeAll();
      } finally {
         if (var2 != null) {
            var2.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var1));
         }

      }

   }

   public boolean setQuery(Object query) {
      MethodInfoImpl var2 = null;
      ProfilingAgent var3 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var3 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var3 != null) {
            String var4 = "Query: " + query;
            var2 = new MethodInfoImpl("Query.setQuery(Object query)", var4);
            var3.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var2));
         }
      }

      boolean var7;
      try {
         var7 = super.setQuery(query);
      } finally {
         if (var3 != null) {
            var3.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var2));
         }

      }

      return var7;
   }
}
