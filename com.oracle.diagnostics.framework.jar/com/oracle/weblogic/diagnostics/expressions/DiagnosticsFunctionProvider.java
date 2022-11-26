package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.expressions.poller.Poller;
import com.oracle.weblogic.diagnostics.expressions.poller.PollerFactory;
import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;
import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import java.util.ArrayList;
import java.util.Iterator;
import javax.el.LambdaExpression;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.PropertyHelper;

@FunctionProvider(
   prefix = "wls"
)
@Singleton
@Service
@AdminServer
@ManagedServer
@Partition
@MetricRuleType
public class DiagnosticsFunctionProvider {
   private static final DiagnosticsFrameworkTextTextFormatter txtFmt = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionFunctions");
   public static final String PREFIX = "wls";
   static final int SAMPLE_SET_LIMIT = PropertyHelper.getInteger("weblogic.diagnostics.extract.samplesmax", 240);

   @Function
   @WLDFI18n(
      value = "extract.short",
      fullDescription = "extract.full",
      displayName = "extract.displayName"
   )
   public static Iterable extract(@WLDFI18n(value = "extract.spec.short",name = "inputExpression") TrackedValueSource spec, @WLDFI18n(value = "extract.schedule.short",name = "schedule") String schedule, @WLDFI18n(value = "extract.duration.short",name = "duration") String duration) {
      if (spec != null && schedule != null && duration != null) {
         EvaluationContext evalContext = EvaluationContextHelper.getCurrentContext();
         if (evalContext == null) {
            throw new FunctionProviderRuntimeException(txtFmt.getNoEvaluationContextForExtractFunction());
         } else {
            DiagnosticsELContext currentELContext = evalContext.getELContext();
            if (currentELContext == null) {
               throw new FunctionProviderRuntimeException(txtFmt.getExtractEvaluationContext());
            } else {
               Object tlFactory = currentELContext.getContext(PollerFactory.class);
               if (tlFactory == null) {
                  throw new FunctionProviderRuntimeException(txtFmt.getPollerFactoryNotFoundForExtract());
               } else {
                  PollerFactory factory = (PollerFactory)tlFactory;
                  int frequency = DiagnosticsUtils.parseScheduleString(schedule);
                  int durationSecs = DiagnosticsUtils.parseScheduleString(duration);
                  if (frequency > 0 && frequency <= durationSecs) {
                     if (durationSecs <= 0) {
                        throw new IllegalArgumentException(txtFmt.getIllegalExtractDurationArgument(duration));
                     } else {
                        int maxSamples = durationSecs / frequency;
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("extract(): Invoking extract() for " + spec.getKey() + ", frequency=" + frequency + ", duration: " + duration + " (max samples: " + maxSamples + ")");
                        }

                        if (maxSamples > SAMPLE_SET_LIMIT) {
                           throw new FunctionProviderRuntimeException(txtFmt.getExtractSampleSizeLimitExceeded((long)maxSamples, (long)SAMPLE_SET_LIMIT));
                        } else {
                           Poller poller = factory.findOrCreatePoller(spec, frequency, maxSamples);
                           return poller;
                        }
                     }
                  } else {
                     throw new IllegalArgumentException(txtFmt.getIllegalExtractFrequencyArgument(schedule, duration));
                  }
               }
            }
         }
      } else {
         throw new NullPointerException();
      }
   }

   @Function
   @WLDFI18n(
      value = "tableAverages.short",
      fullDescription = "tableAverages.full",
      displayName = "tableAverages.displayName"
   )
   public static Iterable tableAverages(@WLDFI18n(value = "tableAverages.table.short",fullDescription = "tableAverages.table.full",name = "valuesTable") Iterable valuesTable) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("tableAverages(): calculating average");
      }

      ArrayList averagesVector = new ArrayList();

      Double rowAverage;
      for(Iterator outerIt = valuesTable.iterator(); outerIt.hasNext(); averagesVector.add(rowAverage)) {
         Object inner = outerIt.next();
         rowAverage = null;
         if (!(inner instanceof Iterable)) {
            throw new IllegalArgumentException(txtFmt.getTableAveragesInvalidTableRowStructureText(inner.getClass().getName()));
         }

         rowAverage = average((Iterable)inner);
         if (debugLogger.isDebugEnabled() && !isValidNumber(rowAverage)) {
            debugLogger.debug("tableAverages(): Discovered non-numeric average for a row");
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("tableAverages(): Vector of averages: " + printResult(averagesVector));
      }

      return averagesVector;
   }

   @Function
   @WLDFI18n(
      value = "average.short",
      fullDescription = "average.full",
      displayName = "average.displayName"
   )
   public static Double average(@WLDFI18n(value = "averages.inputValues.short",fullDescription = "average.inputValues.full",name = "inputValues") Iterable inputValues) {
      Iterator rowIt = inputValues.iterator();
      if (!rowIt.hasNext()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("average(): Value set is empty, returning NaN");
         }

         return Double.NaN;
      } else {
         int count = 0;

         double sum;
         double numVal;
         for(sum = 0.0; rowIt.hasNext(); sum += numVal) {
            Object next = rowIt.next();
            if (!isValidNumber(next)) {
               throw new IllegalArgumentException(txtFmt.getAverageInputValueNotANumberText(next));
            }

            numVal = ((Number)next).doubleValue();
            ++count;
         }

         return count > 0 ? sum / (double)count : Double.NaN;
      }
   }

   @Function
   @WLDFI18n(
      value = "changes.short",
      fullDescription = "changes.full",
      displayName = "changes.displayName"
   )
   public static Iterable changes(@WLDFI18n(value = "changes.inputValues.short",fullDescription = "changes.inputValues.full",name = "inputValues") Iterable inputValues) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("changes() input: " + (inputValues == null ? "" : inputValues.toString()));
      }

      ArrayList result = new ArrayList();
      if (inputValues != null) {
         Iterator it = inputValues.iterator();
         Number previous = null;

         while(it.hasNext()) {
            Object next = it.next();
            if (!isValidNumber(next)) {
               throw new IllegalArgumentException(txtFmt.getChangesValueNotANumber(next.toString()));
            }

            Number current = (Number)next;
            double currentValue = current.doubleValue();
            if (isValidNumber(currentValue)) {
               if (previous != null) {
                  result.add(currentValue - previous.doubleValue());
               }

               previous = current;
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("changes() result: " + result.toString());
      }

      return result;
   }

   @Function
   @WLDFI18n(
      value = "tableChanges.short",
      fullDescription = "tableChanges.full",
      displayName = "tableChanges.displayName"
   )
   public static Iterable tableChanges(@WLDFI18n(value = "tableChanges.inputTable.short",fullDescription = "tableChanges.inputTable.full",name = "inputTable") Iterable inputTable) {
      ArrayList result = new ArrayList();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("tableChanges(): calculating average");
      }

      Iterator outerIt = inputTable.iterator();

      while(outerIt.hasNext()) {
         Object inner = outerIt.next();
         Iterable changeVector = null;
         if (!(inner instanceof Iterable)) {
            throw new IllegalArgumentException(txtFmt.getTableAveragesNonIterableRowText(inner.toString()));
         }

         changeVector = changes((Iterable)inner);
         result.add(changeVector);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("tableChanges(): Vectors of changes: " + printResult(result));
      }

      return result;
   }

   static float percentMatch(Iterable stream, LambdaExpression lambda) {
      float percentMatch = 0.0F;
      int totalElements = 0;
      int matchingElements = 0;
      Iterator var5 = stream.iterator();

      while(true) {
         while(var5.hasNext()) {
            Object o = var5.next();
            ++totalElements;
            if (!(o instanceof UnknownValueType) && (!(o instanceof Number) || !isNaNOrInfinite((Number)o))) {
               Object value = lambda.invoke(new Object[]{o});
               if ((Boolean)value) {
                  ++matchingElements;
               }
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("percentMatch(): Not a valid type: " + o.toString());
            }
         }

         if (totalElements > 0) {
            percentMatch = (float)matchingElements / (float)totalElements;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("percentMatch(): " + matchingElements + " out of " + totalElements + " satisfy predicate, percentage == " + percentMatch);
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("percentMatch(): No elements to compare");
         }

         return percentMatch;
      }
   }

   private static String printResult(Object result) {
      StringBuffer sb = new StringBuffer(256);
      sb.append("set: [");
      if (result instanceof Iterable) {
         Iterable set = (Iterable)result;
         Iterator it = set.iterator();
         int i = 0;

         while(it.hasNext()) {
            if (i > 0) {
               sb.append(",");
            }

            ++i;
            sb.append(it.next());
         }

         sb.append("]");
      } else {
         sb.append("value = ");
         sb.append(result);
      }

      return sb.toString();
   }

   private static boolean isNaNOrInfinite(Number val) {
      double doubleVal = val.doubleValue();
      return Double.isNaN(doubleVal) || Double.isInfinite(doubleVal);
   }

   private static boolean isValidNumber(Object val) {
      return val instanceof Number && !isNaNOrInfinite((Number)val);
   }
}
