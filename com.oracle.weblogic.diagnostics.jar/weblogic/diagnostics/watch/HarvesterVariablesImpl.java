package weblogic.diagnostics.watch;

import com.bea.adaptive.harvester.WatchedValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableInstance;
import weblogic.diagnostics.query.VariableResolver;

public class HarvesterVariablesImpl implements VariableResolver {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private WatchedValues watchedValues;

   HarvesterVariablesImpl(WatchedValues wv) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created HarvesterVariableImpl " + this);
      }

      this.watchedValues = wv;
   }

   public Object resolveVariable(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException("Variable resolution is only supported by index");
   }

   public Object resolveVariable(int index) throws UnknownVariableException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Getting value for variable at index" + index);
      }

      WatchedValues.Values valueData = this.watchedValues.getMetric(index);
      WatchedValues.Values.ValuesData rawData = valueData.getValues();
      Object results = null;
      if (rawData != null) {
         List rawValues = rawData.getRawValues();
         Object[] values = new Object[rawValues.size()];
         Iterator it = rawValues.iterator();
         int arrayIndex = 0;

         while(it.hasNext()) {
            WatchedValues.Values.RawValueData rawValue = (WatchedValues.Values.RawValueData)it.next();
            Object value = rawValue.getValue();
            if (value != null) {
               if (value instanceof WatchedValues.AttributeTrackedDataItem) {
                  WatchedValues.AttributeTrackedDataItem attributeTrackedDataItem = (WatchedValues.AttributeTrackedDataItem)value;
                  value = new HarvesterVariableValueInstance(rawValue.getInstanceName(), attributeTrackedDataItem);
               } else if (value.getClass().isArray()) {
                  value = getLeafValues(rawValue, (Object[])((Object[])value));
               }
            }

            values[arrayIndex++] = value;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Raw value for (" + rawValue.getInstanceName() + "//" + rawValue.getAttributeName() + "): " + value);
            }
         }

         if (values != null && values.length == 1) {
            results = values[0];
         } else {
            results = values;
         }

         if (debugLogger.isDebugEnabled()) {
            if (results != null && results.getClass().isArray()) {
               debugLogger.debug("Outgoing array of values (" + ((Object[])((Object[])results)).length + " total): " + Arrays.toString((Object[])((Object[])results)));
            } else {
               debugLogger.debug("Outgoing scalar value: " + results);
            }
         }
      }

      return results;
   }

   public static Object[] getLeafValues(WatchedValues.Values.RawValueData rawValue, Object[] data) {
      List result = new ArrayList();
      if (data != null) {
         addItems(rawValue, result, data);
      }

      return result.toArray();
   }

   private static void addItems(WatchedValues.Values.RawValueData rawValue, List result, Object[] items) {
      Object[] var3 = items;
      int var4 = items.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object item = var3[var5];
         if (item != null) {
            if (item instanceof WatchedValues.AttributeTrackedDataItem) {
               WatchedValues.AttributeTrackedDataItem dataItem = (WatchedValues.AttributeTrackedDataItem)item;
               if (dataItem.getData() == null) {
                  continue;
               }

               item = new HarvesterVariableValueInstance(rawValue.getInstanceName(), dataItem);
            }

            Class itemClass = item.getClass();
            if (itemClass.isArray()) {
               addItems(rawValue, result, (Object[])((Object[])item));
            } else {
               result.add(item);
            }
         }
      }

   }

   public int resolveInteger(int index) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public long resolveLong(int index) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public float resolveFloat(int index) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public double resolveDouble(int index) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public String resolveString(int index) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public int resolveInteger(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public long resolveLong(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public float resolveFloat(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public double resolveDouble(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   public String resolveString(String varName) throws UnknownVariableException {
      throw new UnsupportedOperationException();
   }

   private static class HarvesterVariableValueInstance implements VariableInstance {
      String instanceName;
      WatchedValues.AttributeTrackedDataItem item;

      public HarvesterVariableValueInstance(String instanceName, WatchedValues.AttributeTrackedDataItem item) {
         this.instanceName = instanceName;
         this.item = item;
      }

      public String getAttributeName() {
         return WLDFHarvesterUtils.buildDataContextString(this.item.getDataContext());
      }

      public String getInstanceName() {
         return this.instanceName;
      }

      public Object getInstanceValue() {
         return this.item.getData();
      }

      public String toString() {
         StringBuilder builder = new StringBuilder(128);
         builder.append(this.instanceName);
         builder.append("//");
         builder.append(this.getAttributeName());
         builder.append(":");
         builder.append(this.getInstanceValue());
         return builder.toString();
      }
   }
}
