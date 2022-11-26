package weblogic.diagnostics.instrumentation.action;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.AbstractDiagnosticAction;
import weblogic.diagnostics.instrumentation.AroundDiagnosticAction;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitorControl;
import weblogic.diagnostics.instrumentation.InstrumentationScope;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.utils.time.Timer;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class MethodInvocationStatisticsAction extends AbstractDiagnosticAction implements AroundDiagnosticAction {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationActions");
   static final long serialVersionUID = -4957607339728632810L;
   private static final String COUNT = "count";
   private static final String MIN = "min";
   private static final String MAX = "max";
   private static final String AVG = "avg";
   private static final String SUM = "sum";
   private static final String SUM_OF_SQUARES = "sum_of_squares";
   private static final String STD_DEVIATION = "std_deviation";
   private Map parsedMethodDescriptorCache = new ConcurrentHashMap();

   public MethodInvocationStatisticsAction() {
      this.setType("MethodInvocationStatisticsAction");
   }

   public String[] getAttributeNames() {
      return null;
   }

   public DiagnosticActionState createState() {
      return new MethodInvocationStatisticsActionState();
   }

   public void preProcess(JoinPoint jp, DiagnosticActionState actionState) {
      MethodInvocationStatisticsActionState state = (MethodInvocationStatisticsActionState)actionState;
      state.initBeginTimestamp();
   }

   public void postProcess(final JoinPoint jp, DiagnosticActionState actionState) {
      DiagnosticMonitor mon = this.getDiagnosticMonitor();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Monitor class = " + mon.getClass().getName());
      }

      if (mon instanceof DiagnosticMonitorControl) {
         DiagnosticMonitorControl dmc = (DiagnosticMonitorControl)mon;
         InstrumentationScope is = dmc.getInstrumentationScope();
         final Map statsMap = is.getInstrumentationStatistics().getMethodInvocationStatistics();
         MethodInvocationStatisticsActionState state = (MethodInvocationStatisticsActionState)actionState;
         state.initEndTimestamp();
         final long execTime = state.computeElaspedTime();
         MethodInvocationStatisticsAction.WORKMANAGER_GETTER.workManager.schedule(new Runnable() {
            public void run() {
               MethodInvocationStatisticsAction.this.updateMethodInvocationStatistics(jp, statsMap, execTime);
            }
         });
      }

   }

   private void updateMethodInvocationStatistics(JoinPoint jp, Map statsMap, long execTime) {
      long t0 = System.nanoTime();
      String className = jp.getClassName();
      String methodName = jp.getMethodName();
      String methodDescriptor = jp.getMethodDescriptor();
      if (DEBUG.isDebugEnabled()) {
         StringBuilder sb = new StringBuilder();
         sb.append("Class=");
         sb.append(className);
         sb.append(";");
         sb.append("Method=");
         sb.append(methodName);
         sb.append(";");
         sb.append("MethodDescriptor=");
         sb.append(methodDescriptor);
         sb.append(";");
         DEBUG.debug(sb.toString());
      }

      String md = (String)this.parsedMethodDescriptorCache.get(methodDescriptor);
      if (md == null) {
         md = this.parseMethodDescriptor(methodDescriptor);
         this.parsedMethodDescriptorCache.put(methodDescriptor, md);
      } else if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Got md from cache " + methodDescriptor + "=" + md);
      }

      if (md != null) {
         ConcurrentHashMap cs = (ConcurrentHashMap)statsMap;
         cs.putIfAbsent(className, new ConcurrentHashMap());
         ConcurrentHashMap methodMap = (ConcurrentHashMap)cs.get(className);
         methodMap.putIfAbsent(methodName, new ConcurrentHashMap());
         ConcurrentHashMap methodDescMap = (ConcurrentHashMap)methodMap.get(methodName);
         methodDescMap.putIfAbsent(md, new ConcurrentHashMap());
         ConcurrentHashMap methodDataMap = (ConcurrentHashMap)methodDescMap.get(md);
         synchronized(methodDataMap) {
            Number value = null;
            long invocationCount = 0L;
            long minExecutionTime = -1L;
            long maxExecutionTime = -1L;
            double sumExecutionTime = 0.0;
            double averageExecutionTime = 0.0;
            double sumSquares = 0.0;
            double stdDev = 0.0;
            value = (Number)methodDataMap.get("count");
            if (value != null) {
               invocationCount = value.longValue();
            }

            value = (Number)methodDataMap.get("min");
            if (value != null) {
               minExecutionTime = value.longValue();
            }

            value = (Number)methodDataMap.get("max");
            if (value != null) {
               maxExecutionTime = value.longValue();
            }

            value = (Number)methodDataMap.get("sum");
            if (value != null) {
               sumExecutionTime = value.doubleValue();
            }

            value = (Number)methodDataMap.get("avg");
            if (value != null) {
               averageExecutionTime = value.doubleValue();
            }

            value = (Number)methodDataMap.get("sum_of_squares");
            if (value != null) {
               sumSquares = value.doubleValue();
            }

            value = (Number)methodDataMap.get("std_deviation");
            if (value != null) {
               stdDev = value.doubleValue();
            }

            ++invocationCount;
            if (minExecutionTime == -1L) {
               minExecutionTime = execTime;
            } else {
               minExecutionTime = execTime < minExecutionTime ? execTime : minExecutionTime;
            }

            if (maxExecutionTime == -1L) {
               maxExecutionTime = execTime;
            } else {
               maxExecutionTime = execTime > maxExecutionTime ? execTime : maxExecutionTime;
            }

            sumExecutionTime += (double)execTime;
            long t1 = System.nanoTime();
            averageExecutionTime = sumExecutionTime / (double)invocationCount;
            long t2 = System.nanoTime();
            double d = (double)execTime;
            sumSquares += d * d;
            long t3 = System.nanoTime();
            double stdDev2 = sumSquares / (double)invocationCount - averageExecutionTime * averageExecutionTime;
            stdDev = Math.sqrt(stdDev2);
            long t4 = System.nanoTime();
            if (DEBUG.isDebugEnabled()) {
               StringBuilder sb = new StringBuilder();
               sb.append("Count=");
               sb.append(invocationCount);
               sb.append(";");
               sb.append("Min=");
               sb.append(minExecutionTime);
               sb.append(";");
               sb.append("Max=");
               sb.append(maxExecutionTime);
               sb.append(";");
               sb.append("Avg=");
               sb.append(averageExecutionTime);
               sb.append(";");
               sb.append("Sum=");
               sb.append(sumExecutionTime);
               sb.append(";");
               sb.append("Sum of squares=");
               sb.append(sumSquares);
               sb.append(";");
               sb.append("Std Deviation=");
               sb.append(stdDev);
               sb.append(";");
               DEBUG.debug(sb.toString());
            }

            methodDataMap.put("count", new Long(invocationCount));
            methodDataMap.put("min", new Long(minExecutionTime));
            methodDataMap.put("max", new Long(maxExecutionTime));
            methodDataMap.put("avg", new Double(averageExecutionTime));
            methodDataMap.put("sum", new Double(sumExecutionTime));
            methodDataMap.put("sum_of_squares", new Double(sumSquares));
            methodDataMap.put("std_deviation", new Double(stdDev));
            long t5 = System.nanoTime();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Stats overhead:" + execTime + "," + (t2 - t1) + "," + (t3 - t2) + "," + (t4 - t3) + "," + (t5 - t0));
            }
         }
      }

   }

   private String parseMethodDescriptor(String methodDescriptor) {
      StringReader r = new StringReader(methodDescriptor);
      MethodDescriptorLexer l = new MethodDescriptorLexer(r);
      MethodDescriptorParser p = new MethodDescriptorParser(l);

      try {
         p.methodDescriptor();
         List params = p.getInputParameters();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Input params = " + params);
         }

         StringBuilder sb = new StringBuilder();
         int size = params.size();
         if (size > 0) {
            for(int i = 0; i < size - 1; ++i) {
               sb.append(params.get(i));
               sb.append(',');
            }

            sb.append(params.get(size - 1));
         }

         return sb.toString();
      } catch (Exception var9) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Exception parsing", var9);
         }

         return null;
      }
   }

   private static class MethodInvocationStatisticsActionState implements DiagnosticActionState {
      private static Timer timer = Timer.createTimer();
      private long beginTimestamp;
      private long endTimestamp;

      private MethodInvocationStatisticsActionState() {
      }

      void initBeginTimestamp() {
         this.beginTimestamp = timer.timestamp();
      }

      void initEndTimestamp() {
         this.endTimestamp = timer.timestamp();
      }

      long computeElaspedTime() {
         return this.endTimestamp - this.beginTimestamp;
      }

      // $FF: synthetic method
      MethodInvocationStatisticsActionState(Object x0) {
         this();
      }
   }

   static class WORKMANAGER_GETTER {
      static WorkManager workManager;

      static {
         workManager = workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.diagnostics.instrumentation.MethodInvocationStatisticsActionWorkManager", 1, -1);
      }
   }
}
