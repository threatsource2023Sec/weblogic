package kodo.kernel;

import com.solarmetric.profile.ExecutionContextNameProvider;
import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfo;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import com.solarmetric.profile.RootEnterEvent;
import com.solarmetric.profile.RootExitEvent;
import java.util.Collection;
import kodo.manage.Management;
import kodo.profile.InitialLoadEvent;
import kodo.profile.InitialLoadInfo;
import kodo.profile.KodoProfilingAgent;
import kodo.profile.KodoRootInfo;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.AbstractBrokerFactory;
import org.apache.openjpa.kernel.BrokerImpl;
import org.apache.openjpa.kernel.DelegatingStoreManager;
import org.apache.openjpa.kernel.Extent;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.QueryImpl;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.meta.ClassMetaData;

public class KodoBroker extends BrokerImpl implements KodoStoreContext, ProfilingCapable {
   private KodoProfilingAgent _agent = null;
   private KodoRootInfo _rootInfo = null;
   private MethodInfo _transInfo = null;

   public void initialize(AbstractBrokerFactory factory, DelegatingStoreManager sm, boolean managed, int connMode, boolean fromDeserialization) {
      super.initialize(factory, sm, managed, connMode, fromDeserialization);
      OpenJPAConfiguration conf = factory.getConfiguration();
      Management mgmt = Management.getInstance(conf);
      if (mgmt != null) {
         this._agent = mgmt.getProfilingAgent();
      }

      if (this._agent != null) {
         ExecutionContextNameProvider provider = ExecutionContextNameProviderValue.getExecutionContextNameProvider(conf);
         String creationPoint = this.getCreationPoint(provider);
         this._rootInfo = new KodoRootInfo("Broker", creationPoint);
         this._agent.handleEvent(new RootEnterEvent(this, this._rootInfo));
      }

   }

   private String getCreationPoint(ExecutionContextNameProvider provider) {
      return provider == null ? "" : provider.getCreationPoint("PT_STORECTX_OPEN", this);
   }

   protected StateManagerImpl newStateManagerImpl(Object oid, ClassMetaData meta) {
      return new ProfilingStateManager(oid, meta, this);
   }

   protected StateManagerImpl initialize(StateManagerImpl sm, boolean load, FetchConfiguration fetch, Object edata) {
      sm = super.initialize(sm, load, fetch, edata);
      if (sm != null && this._agent != null) {
         this._agent.registerMetaData(sm.getMetaData());
         this._agent.handleEvent(new InitialLoadEvent(this, new InitialLoadInfo(sm, fetch)));
      }

      return sm;
   }

   public void begin() {
      this.lock();

      try {
         super.begin();
         if (this._agent != null) {
            ExecutionContextNameProvider provider = ExecutionContextNameProviderValue.getExecutionContextNameProvider(this.getConfiguration());
            String creationPoint = this.getCreationPoint(provider);
            this._transInfo = new MethodInfoImpl("transaction", creationPoint);
            this._agent.handleEvent(new MethodEnterEvent(this, this._transInfo));
         }
      } finally {
         this.unlock();
      }

   }

   public void commit() {
      MethodInfoImpl methodInfo = null;
      if (this._agent != null) {
         methodInfo = new MethodInfoImpl("commit()");
         this._agent.handleEvent(new MethodEnterEvent(this, methodInfo));
      }

      this.lock();

      try {
         super.commit();
      } finally {
         try {
            if (this._agent != null) {
               this._agent.handleEvent(new MethodExitEvent(this, methodInfo));
               if (this._transInfo != null) {
                  this._agent.handleEvent(new MethodExitEvent(this, this._transInfo));
                  this._transInfo = null;
               }
            }
         } finally {
            this.unlock();
         }

      }

   }

   public void rollback() {
      this.lock();

      try {
         super.rollback();
      } finally {
         try {
            if (this._agent != null && this._transInfo != null) {
               this._agent.handleEvent(new MethodExitEvent(this, this._transInfo));
               this._transInfo = null;
            }
         } finally {
            this.unlock();
         }

      }

   }

   protected QueryImpl newQueryImpl(String language, StoreQuery sq) {
      return new KodoQuery(this, language, sq);
   }

   protected void free() {
      try {
         if (this._agent != null) {
            this._agent.handleEvent(new RootExitEvent(this, this._rootInfo));
         }
      } finally {
         super.free();
      }

   }

   public ProfilingAgent getProfilingAgent() {
      return this._agent;
   }

   public void persist(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("persist(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.persist(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void persistAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("persistAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.persistAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void delete(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("delete(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.delete(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void deleteAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("deleteAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.deleteAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void refresh(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("refresh(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.refresh(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void refreshAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("refreshAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.refreshAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void release(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("release(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.release(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void releaseAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("releaseAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.releaseAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void transactional(Object obj, boolean versn, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("transactional(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      try {
         super.transactional(obj, versn, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

   }

   public void transactionalAll(Collection objs, boolean versn, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("transactionalAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      try {
         super.transactionalAll(objs, versn, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

   }

   public void nontransactional(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("nontransactional(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.nontransactional(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void nontransactionalAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("nontransactionalAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.nontransactionalAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void evict(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("evict(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.evict(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void evictAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("evictAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.evictAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void evictAll(Extent ext, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("evictAll(Extent extent)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      try {
         super.evictAll(ext, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

   }

   public void evictAll(OpCallbacks call) {
      MethodInfoImpl var2 = null;
      if (this._agent != null) {
         var2 = new MethodInfoImpl("evictAll()", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var2));
      }

      try {
         super.evictAll(call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var2));
         }

      }

   }

   public void retrieve(Object obj, boolean fgs, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("retrieve(Object obj, boolean fgs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      try {
         super.retrieve(obj, fgs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

   }

   public void retrieveAll(Collection objs, boolean fgs, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("retrieveAll(... objs, boolean fgs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      try {
         super.retrieveAll(objs, fgs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

   }

   public Object find(Object oid, boolean validate, FindCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("find(Object oid)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      Object var7;
      try {
         var7 = super.find(oid, validate, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

      return var7;
   }

   public Object[] findAll(Collection oids, boolean validate, FindCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("findAll(... oids)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      Object[] var7;
      try {
         var7 = super.findAll(oids, validate, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

      return var7;
   }

   public Query newQuery(String lang, Object query) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("newQuery(String language, Object query)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      Query var6;
      try {
         var6 = super.newQuery(lang, query);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

      return var6;
   }

   public Query newQuery(String lang, Class candidate, Object query) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("newQuery(String language, Class candidate, Object query)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      Query var7;
      try {
         var7 = super.newQuery(lang, candidate, query);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

      return var7;
   }

   public Object attach(Object obj, boolean copyNew, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("attach(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      Object var7;
      try {
         var7 = super.attach(obj, copyNew, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

      return var7;
   }

   public Object[] attachAll(Collection objs, boolean copyNew, OpCallbacks call) {
      MethodInfoImpl var4 = null;
      if (this._agent != null) {
         var4 = new MethodInfoImpl("attachAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var4));
      }

      Object[] var7;
      try {
         var7 = super.attachAll(objs, copyNew, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var4));
         }

      }

      return var7;
   }

   public Object detach(Object obj, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("detach(Object obj)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      Object var6;
      try {
         var6 = super.detach(obj, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

      return var6;
   }

   public Object[] detachAll(Collection objs, OpCallbacks call) {
      MethodInfoImpl var3 = null;
      if (this._agent != null) {
         var3 = new MethodInfoImpl("detachAll(... objs)", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var3));
      }

      Object[] var6;
      try {
         var6 = super.detachAll(objs, call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var3));
         }

      }

      return var6;
   }

   public void detachAll(OpCallbacks call) {
      MethodInfoImpl var2 = null;
      if (this._agent != null) {
         var2 = new MethodInfoImpl("detachAll()", "");
         this._agent.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this, var2));
      }

      try {
         super.detachAll(call);
      } finally {
         if (this._agent != null) {
            this._agent.handleEvent(new MethodExitEvent((ProfilingEnvironment)this, var2));
         }

      }

   }
}
