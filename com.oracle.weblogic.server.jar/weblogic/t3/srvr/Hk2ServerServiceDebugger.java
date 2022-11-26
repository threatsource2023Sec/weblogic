package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.runlevel.ChangeableRunLevelFuture;
import org.glassfish.hk2.runlevel.ErrorInformation;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelListener;
import org.jvnet.hk2.external.runtime.ServiceLocatorRuntimeBean;
import weblogic.diagnostics.debug.DebugLogger;

@Singleton
public class Hk2ServerServiceDebugger implements InstanceLifecycleListener, RunLevelListener {
   private static final boolean IN_LINE = getBooleanProperty("weblogic.debug.DebugServerLifeCycle.inline");
   private static final boolean MEMORY = getBooleanProperty("weblogic.debug.DebugServerLifeCycle.memory");
   private static final String TYPE_NAME_PREFIX = "interface ";
   private static final String SERVER_SERVICE = "weblogic.server.ServerService";
   private static final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private final LinkedHashMap startTimes = new LinkedHashMap();
   private Long shutdownTime;
   private Long startingTime;
   private Long standbyTime;
   private Long adminTime;
   private Long runningTime;
   private int finishOrder = 1;
   private int lastStartupServiceNumber = -1;
   private int lastStandbyServiceNumber = -1;
   private int lastAdminServiceNumber = -1;
   @Inject
   private ServiceLocatorRuntimeBean runtimeBean;

   private static boolean getBooleanProperty(final String prop) {
      return (Boolean)AccessController.doPrivileged(new PrivilegedAction() {
         public Boolean run() {
            return "true".equalsIgnoreCase(System.getProperty(prop, "false"));
         }
      });
   }

   public Filter getFilter() {
      return ServerServiceProgressMeter.FILTER;
   }

   public void lifecycleEvent(InstanceLifecycleEvent lifecycleEvent) {
      if (InstanceLifecycleEventType.PRE_PRODUCTION.equals(lifecycleEvent.getEventType())) {
         this.doPreProduction(lifecycleEvent.getActiveDescriptor());
      } else if (InstanceLifecycleEventType.POST_PRODUCTION.equals(lifecycleEvent.getEventType())) {
         this.doPostProduction(lifecycleEvent);
      } else if (InstanceLifecycleEventType.PRE_DESTRUCTION.equals(lifecycleEvent.getEventType())) {
         this.doPreDestruction(lifecycleEvent.getActiveDescriptor());
      }
   }

   private static String getSimpleName(String clazzName) {
      int index = clazzName.lastIndexOf(46);
      return index > 0 ? clazzName.substring(index + 1) : clazzName;
   }

   private static boolean isServerService(Injectee injectee) {
      String typeName = injectee.getRequiredType().toString();
      if (typeName.startsWith("interface ")) {
         typeName = typeName.substring("interface ".length());
      }

      return "weblogic.server.ServerService".equalsIgnoreCase(typeName);
   }

   private static final String getNameFromInjectee(Injectee injectee) {
      Set qualifiers = injectee.getRequiredQualifiers();
      Iterator var2 = qualifiers.iterator();

      Annotation qualifier;
      do {
         if (!var2.hasNext()) {
            ActiveDescriptor injecteeDescriptor = injectee.getInjecteeDescriptor();
            if (injecteeDescriptor != null) {
               return getSimpleName(injecteeDescriptor.getImplementation());
            }

            return injectee.getInjecteeClass().getSimpleName();
         }

         qualifier = (Annotation)var2.next();
      } while(!Named.class.equals(qualifier.annotationType()));

      Named named = (Named)qualifier;
      return named.value();
   }

   private static final Set getDependenciesFromDescriptor(ActiveDescriptor descriptor) {
      LinkedHashSet retVal = new LinkedHashSet();
      List injectees = descriptor.getInjectees();
      Iterator var3 = injectees.iterator();

      while(var3.hasNext()) {
         Injectee injectee = (Injectee)var3.next();
         if (isServerService(injectee)) {
            retVal.add(injectee);
         }
      }

      return retVal;
   }

   private static String getUniqueId(ActiveDescriptor descriptor) {
      return descriptor.getName() + "/" + descriptor.getImplementation() + "(" + descriptor.getServiceId() + "," + descriptor.getLocatorId() + ")";
   }

   private synchronized void doPreProduction(ActiveDescriptor descriptor) {
      if (IN_LINE) {
         debugSLCWLDF.debug("Starting service " + getUniqueId(descriptor) + " on thread " + Thread.currentThread().getId());
      }

      this.startTimes.put(descriptor, new TimeAndPosition(System.currentTimeMillis(), getDependenciesFromDescriptor(descriptor)));
   }

   private synchronized void doPostProduction(InstanceLifecycleEvent event) {
      ActiveDescriptor descriptor = event.getActiveDescriptor();
      TimeAndPosition startupTime = (TimeAndPosition)this.startTimes.get(descriptor);
      if (startupTime != null) {
         startupTime.finished(this.finishOrder++);
         if (IN_LINE) {
            debugSLCWLDF.debug("Completed service " + getUniqueId(descriptor) + " on thread " + Thread.currentThread().getId() + ".  It took " + startupTime.elapsedTime + " ms");
         }

         if (MEMORY) {
            debugSLCWLDF.debug("Completed service " + getUniqueId(descriptor) + " started with total memory " + startupTime.memory + " bytes and added " + startupTime.memoryDifference + " bytes");
         }

      }
   }

   private void doPreDestruction(ActiveDescriptor descriptor) {
      debugSLCWLDF.debug("Releasing service " + getUniqueId(descriptor));
   }

   private synchronized void printServiceTable() {
      StringBuilder sb = new StringBuilder();
      sb.append("The startup services were run in this order:\n");
      int lcv = 1;
      Iterator var3 = this.startTimes.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         Set dependencies = ((TimeAndPosition)entry.getValue()).getDependencies();
         if (lcv - 1 == this.lastStartupServiceNumber) {
            sb.append("<<Finished with STARTUP>>\n");
         } else if (lcv - 1 == this.lastStandbyServiceNumber) {
            sb.append("<<Finished with STANDBY>>\n");
         } else if (lcv - 1 == this.lastAdminServiceNumber) {
            sb.append("<<Finished with ADMIN>>\n");
         }

         String uniqueKey = getUniqueId((ActiveDescriptor)entry.getKey());
         sb.append(lcv++ + " (" + ((TimeAndPosition)entry.getValue()).getFinishPosition() + "). " + uniqueKey + " took " + ((TimeAndPosition)entry.getValue()).getElapsedTime() + " milliseconds on thread " + ((TimeAndPosition)entry.getValue()).getThreadId() + " and added " + ((TimeAndPosition)entry.getValue()).memoryDifference + " bytes\n");
         Iterator var7 = dependencies.iterator();

         while(var7.hasNext()) {
            Injectee dependency = (Injectee)var7.next();
            sb.append("\t-> " + getNameFromInjectee(dependency));
            if (dependency.isOptional()) {
               sb.append("  OPTIONAL\n");
            } else {
               sb.append("\n");
            }
         }
      }

      debugSLCWLDF.debug(sb.toString());
      debugSLCWLDF.debug("HK2 service cache size is " + this.runtimeBean.getServiceCacheSize() + " and the reflection cache size is " + this.runtimeBean.getReflectionCacheSize());
      if (this.shutdownTime != null && this.startingTime != null) {
         debugSLCWLDF.debug("STARTING level took " + (this.startingTime - this.shutdownTime) + " milliseconds");
         if (this.standbyTime != null) {
            debugSLCWLDF.debug("STANDBY level took " + (this.standbyTime - this.startingTime) + " milliseconds");
            if (this.adminTime != null) {
               debugSLCWLDF.debug("ADMIN level took " + (this.adminTime - this.standbyTime) + " milliseconds");
               if (this.runningTime != null) {
                  debugSLCWLDF.debug("RUNNING level took " + (this.runningTime - this.adminTime) + " milliseconds");
                  debugSLCWLDF.debug("Total service startup time took " + (this.runningTime - this.shutdownTime) + " milliseconds");
               }
            }
         }
      }

      this.shutdownTime = null;
      this.startingTime = null;
      this.standbyTime = null;
      this.adminTime = null;
      this.runningTime = null;
      this.startTimes.clear();
      this.finishOrder = 1;
      this.lastStartupServiceNumber = -1;
      this.lastStandbyServiceNumber = -1;
      this.lastAdminServiceNumber = -1;
   }

   public void onCancelled(RunLevelFuture arg0, int arg1) {
      if (!arg0.isDown()) {
         this.printServiceTable();
      }
   }

   public void onError(RunLevelFuture arg0, ErrorInformation arg1) {
      if (!arg0.isDown()) {
         this.printServiceTable();
      }
   }

   public void onProgress(ChangeableRunLevelFuture arg0, int arg1) {
      if (!arg0.isDown()) {
         synchronized(this) {
            switch (arg1) {
               case 0:
                  this.shutdownTime = System.currentTimeMillis();
                  break;
               case 5:
                  this.startingTime = System.currentTimeMillis();
                  this.lastStartupServiceNumber = this.startTimes.size();
                  break;
               case 10:
                  this.standbyTime = System.currentTimeMillis();
                  this.lastStandbyServiceNumber = this.startTimes.size();
                  break;
               case 15:
                  this.adminTime = System.currentTimeMillis();
                  this.lastAdminServiceNumber = this.startTimes.size();
                  break;
               case 20:
                  this.runningTime = System.currentTimeMillis();
            }
         }

         if (arg1 == 20) {
            this.printServiceTable();
         }
      }
   }

   private static class TimeAndPosition {
      private final long time;
      private final Set dependencies;
      private final long threadId;
      private long elapsedTime;
      private int finishPosition;
      private long memory;
      private long memoryDifference;

      private TimeAndPosition(Long time, Set dependencies) {
         this.time = time;
         this.dependencies = dependencies;
         this.threadId = Thread.currentThread().getId();
         this.memory = Runtime.getRuntime().totalMemory();
      }

      private void finished(int finishPosition) {
         this.elapsedTime = System.currentTimeMillis() - this.time;
         this.finishPosition = finishPosition;
         this.memoryDifference = Runtime.getRuntime().totalMemory() - this.memory;
      }

      private final Set getDependencies() {
         return this.dependencies;
      }

      private final long getThreadId() {
         return this.threadId;
      }

      private final long getElapsedTime() {
         return this.elapsedTime;
      }

      private final int getFinishPosition() {
         return this.finishPosition;
      }

      // $FF: synthetic method
      TimeAndPosition(Long x0, Set x1, Object x2) {
         this(x0, x1);
      }
   }
}
