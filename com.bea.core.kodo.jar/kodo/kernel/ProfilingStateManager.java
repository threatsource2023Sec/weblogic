package kodo.kernel;

import com.solarmetric.profile.MethodEnterEvent;
import com.solarmetric.profile.MethodExitEvent;
import com.solarmetric.profile.MethodInfo;
import com.solarmetric.profile.MethodInfoImpl;
import com.solarmetric.profile.ProfilingAgent;
import com.solarmetric.profile.ProfilingAgentProvider;
import com.solarmetric.profile.ProfilingCapable;
import com.solarmetric.profile.ProfilingEnvironment;
import java.util.BitSet;
import kodo.profile.IsLoadedEvent;
import kodo.profile.IsLoadedInfo;
import kodo.profile.KodoProfilingAgent;
import kodo.profile.PCFormatter;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.meta.ClassMetaData;

class ProfilingStateManager extends StateManagerImpl implements ProfilingCapable {
   private KodoBroker _broker = null;
   private BitSet _loadReported = null;
   private PersistenceCapable _pc;

   ProfilingStateManager(Object id, ClassMetaData meta, KodoBroker broker) {
      super(id, meta, broker);
      this._broker = broker;
   }

   protected void initialize(PersistenceCapable pc, PCState state) {
      super.initialize(pc, state);
      this._pc = pc;
      if (this._broker.getProfilingAgent() != null) {
         this._loadReported = new BitSet(this.getMetaData().getFields().length);
      }

   }

   protected void beforeAccessField(int field) {
      this.lock();

      try {
         KodoProfilingAgent agent = (KodoProfilingAgent)this._broker.getProfilingAgent();
         if (agent != null && !this._loadReported.get(field)) {
            this._loadReported.set(field);
            IsLoadedInfo isLoadedInfo = new IsLoadedInfo(this.getMetaData(), field, this.getLoaded().get(field));
            agent.registerMetaData(this.getMetaData());
            agent.handleEvent(new IsLoadedEvent(this._broker, isLoadedInfo));
         }

         super.beforeAccessField(field);
      } catch (RuntimeException var7) {
         throw this.translate(var7);
      } finally {
         this.unlock();
      }

   }

   protected void loadField(int field, int lockLevel, boolean forWrite, boolean fgs) {
      ProfilingAgent agent = this._broker.getProfilingAgent();
      MethodInfo methodInfo = null;
      if (agent != null) {
         methodInfo = new MethodInfoImpl("loadField", this.getMetaData().getField(field).getFullName(false));
         agent.handleEvent(new MethodEnterEvent(this._broker, methodInfo));
      }

      try {
         super.loadField(field, lockLevel, forWrite, fgs);
      } finally {
         if (agent != null) {
            agent.handleEvent(new MethodExitEvent(this._broker, methodInfo));
         }

      }

   }

   protected boolean load(FetchConfiguration fetch, int loadMode, BitSet exclude, Object sdata, boolean forWrite) {
      MethodInfoImpl var6 = null;
      ProfilingAgent var7 = null;
      if (this._broker instanceof ProfilingAgentProvider) {
         var7 = ((ProfilingAgentProvider)this._broker).getProfilingAgent();
         if (var7 != null) {
            String var8 = "Class: " + (new PCFormatter()).printType(this._pc) + "\n" + "Fetch Configuration: " + fetch + "\n" + "For Write: " + forWrite;
            var6 = new MethodInfoImpl("o.load()", var8);
            var7.handleEvent(new MethodEnterEvent((ProfilingEnvironment)this._broker, var6));
         }
      }

      boolean var11;
      try {
         var11 = super.load(fetch, loadMode, exclude, sdata, forWrite);
      } finally {
         if (var7 != null) {
            var7.handleEvent(new MethodExitEvent((ProfilingEnvironment)this._broker, var6));
         }

      }

      return var11;
   }
}
