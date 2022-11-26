package com.bea.adaptive.harvester;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface WatchedValues {
   int YES = 2;
   int NO = -1;
   int MAYBE = 1;
   int UNRESOLVED = 0;

   Iterator getWatchedMetrics();

   List getAllMetricValues();

   Iterator getAllMetricValuesFor(String var1, String var2);

   void setTimeStamp(long var1);

   Iterator getMostRecentValues();

   long getMostRecentValuesCount();

   Values getMetric(int var1);

   List getMostRecentSamplePeriods();

   Values addMetric(String var1, String var2, String var3, int var4, boolean var5, boolean var6, boolean var7, boolean var8, int var9);

   Values addMetric(String var1, String var2, String var3, int var4, boolean var5, boolean var6, boolean var7, boolean var8);

   Values addMetric(String var1, String var2, String var3, String var4, int var5, boolean var6, boolean var7, boolean var8, boolean var9, int var10);

   Values addMetric(String var1, String var2, String var3, String var4, boolean var5, boolean var6, boolean var7, boolean var8, int var9);

   Values addMetric(String var1, String var2, String var3, String var4, boolean var5, boolean var6, boolean var7, boolean var8);

   ArrayList extendValues(WatchedValues var1);

   List extendValues(WatchedValues var1, List var2);

   int getId();

   void setId(int var1);

   String getPartitionId();

   String getPartitionName();

   boolean isPartitionScoped();

   String getName();

   int setEnabledStatus(Integer[] var1, boolean var2);

   String dump(String var1, boolean var2, boolean var3, boolean var4);

   int getDefaultSamplePeriod();

   void setDefaultSamplePeriod(int var1);

   boolean isShared();

   void setShared(boolean var1);

   boolean isAttributeNameTrackingEnabled();

   void setAttributeNameTrackingEnabled(boolean var1);

   void resetRawValues();

   public interface ContextItem {
      AttributeTermType getAttributeTermType();

      Object getContext();

      public static enum AttributeTermType {
         SIMPLE,
         ARRAY_OR_LIST,
         MAP;
      }
   }

   public interface AttributeTrackedDataItem {
      List getDataContext();

      Object getData();
   }

   public interface Validation {
      Values getMetric();

      int getStatus();

      Set getIssues();

      String dump(String var1);
   }

   public interface ValueTuple {
      String getInstanceName();

      String getTypeName();

      Object getValue();
   }

   public interface Values {
      String getTypeName();

      String getInstanceName();

      String getNamespace();

      boolean isEnabled();

      boolean setEnabled(boolean var1);

      boolean typeIsPattern();

      boolean instanceIsPattern();

      boolean attributeIsPattern();

      void validate() throws IllegalArgumentException;

      boolean compareAttributes(Values var1);

      ValuesData getValues();

      void resetRawValues();

      String getAttributeName();

      int getVID();

      void addValue(String var1, String var2, String var3, Object var4);

      void addValue(String var1, String var2, Object var3);

      void addError(String var1, String var2, String var3, boolean var4);

      String dump(String var1, boolean var2, boolean var3);

      boolean typeNameMatches(String var1);

      boolean instNameMatches(String var1);

      boolean matches(String var1, String var2);

      boolean matches(String var1, String var2, String var3);

      boolean matchesNamespace(String var1);

      int getSamplePeriod();

      boolean isCurrent();

      long getTimestamp();

      public interface ErrorData {
         String getTypeName();

         String getInstanceName();

         String getReason();

         boolean isAttributeDeactivated();
      }

      public interface RawValueData {
         String getTypeName();

         String getInstanceName();

         String getAttributeName();

         Object getValue();
      }

      public interface ValuesData {
         List getRawValues();

         List getErrors();
      }
   }
}
