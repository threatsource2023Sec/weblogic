package kodo.profile;

import com.solarmetric.profile.EventInfo;
import com.solarmetric.profile.ProfilingAgentImpl;
import com.solarmetric.profile.ProfilingEvent;
import com.solarmetric.profile.ProfilingStack;
import com.solarmetric.profile.ProfilingStackItem;
import java.util.HashMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.meta.ClassMetaData;

public class KodoProfilingAgentImpl extends ProfilingAgentImpl implements KodoProfilingAgent {
   private static final long serialVersionUID = 1L;
   private HashMap pmetas = new HashMap();

   public KodoProfilingAgentImpl() {
   }

   public KodoProfilingAgentImpl(OpenJPAConfiguration conf) {
      super(conf);
   }

   protected void handleExtendedEvent(ProfilingEvent ev) {
      int env;
      int threadHash;
      ProfilingStack stack;
      ProfilingStackItem root;
      EventInfo info;
      KodoRootInfo kinfo;
      if (ev instanceof InitialLoadEvent) {
         env = ev.getProfilingEnvironmentHash();
         threadHash = ev.getThreadHash();
         stack = this.getStack(env, threadHash, true);
         if (stack == null) {
            return;
         }

         root = stack.firstElementItem();
         info = root.getNode().getInfo();
         if (info instanceof KodoRootInfo) {
            InitialLoadEvent ilev = (InitialLoadEvent)ev;
            kinfo = (KodoRootInfo)info;
            kinfo.recordInitialLoad(ilev.getInitialLoadInfo(), (ProfilingClassMetaData)this.pmetas.get(ilev.getInitialLoadInfo().getClassName()));
         }
      } else if (ev instanceof IsLoadedEvent) {
         env = ev.getProfilingEnvironmentHash();
         threadHash = ev.getThreadHash();
         stack = this.getStack(env, threadHash, true);
         if (stack == null) {
            return;
         }

         root = stack.firstElementItem();
         info = root.getNode().getInfo();
         if (info instanceof KodoRootInfo) {
            IsLoadedEvent ilev = (IsLoadedEvent)ev;
            kinfo = (KodoRootInfo)info;
            kinfo.recordIsLoaded(ilev.getIsLoadedInfo(), (ProfilingClassMetaData)this.pmetas.get(ilev.getIsLoadedInfo().getClassName()));
         }
      } else if (ev instanceof ProxyUpdateEvent) {
         env = ev.getProfilingEnvironmentHash();
         threadHash = ev.getThreadHash();
         stack = this.getStack(env, threadHash, true);
         if (stack == null) {
            return;
         }

         root = stack.firstElementItem();
         info = root.getNode().getInfo();
         if (info instanceof KodoRootInfo) {
            ProxyUpdateEvent puev = (ProxyUpdateEvent)ev;
            kinfo = (KodoRootInfo)info;
            kinfo.recordProxyStats(puev.getProxyUpdateInfo());
         }
      } else if (ev instanceof ResultListSummaryEvent) {
         env = ev.getProfilingEnvironmentHash();
         threadHash = ev.getThreadHash();
         stack = this.getStack(env, threadHash, true);
         if (stack == null) {
            return;
         }

         root = stack.firstElementItem();
         info = root.getNode().getInfo();
         if (info instanceof KodoRootInfo) {
            ResultListSummaryEvent rlsev = (ResultListSummaryEvent)ev;
            kinfo = (KodoRootInfo)info;
            kinfo.recordResultListStats(rlsev.getResultListSummaryInfo());
         }
      }

   }

   public ProfilingClassMetaData getMetaData(String className) {
      ProfilingClassMetaData pmeta = (ProfilingClassMetaData)this.pmetas.get(className);
      return pmeta;
   }

   public ProfilingClassMetaData registerMetaData(ClassMetaData meta) {
      ProfilingClassMetaData pmeta = (ProfilingClassMetaData)this.pmetas.get(meta.getDescribedType().getName());
      if (pmeta == null) {
         pmeta = new ProfilingClassMetaData(meta);
         this.pmetas.put(meta.getDescribedType().getName(), pmeta);
      }

      return pmeta;
   }

   protected void registerMetaData(String className, ProfilingClassMetaData pmeta) {
      this.pmetas.put(className, pmeta);
   }
}
