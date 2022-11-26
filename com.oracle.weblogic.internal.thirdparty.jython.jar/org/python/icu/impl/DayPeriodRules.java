package org.python.icu.impl;

import java.util.HashMap;
import java.util.Map;
import org.python.icu.util.ICUException;
import org.python.icu.util.ULocale;

public final class DayPeriodRules {
   private static final DayPeriodRulesData DATA = loadData();
   private boolean hasMidnight;
   private boolean hasNoon;
   private DayPeriod[] dayPeriodForHour;

   private DayPeriodRules() {
      this.hasMidnight = false;
      this.hasNoon = false;
      this.dayPeriodForHour = new DayPeriod[24];
   }

   public static DayPeriodRules getInstance(ULocale locale) {
      String localeCode = locale.getName();
      if (localeCode.isEmpty()) {
         localeCode = "root";
      }

      Integer ruleSetNum = null;

      while(ruleSetNum == null) {
         ruleSetNum = (Integer)DATA.localesToRuleSetNumMap.get(localeCode);
         if (ruleSetNum != null) {
            break;
         }

         localeCode = ULocale.getFallback(localeCode);
         if (localeCode.isEmpty()) {
            break;
         }
      }

      return ruleSetNum != null && DATA.rules[ruleSetNum] != null ? DATA.rules[ruleSetNum] : null;
   }

   public double getMidPointForDayPeriod(DayPeriod dayPeriod) {
      int startHour = this.getStartHourForDayPeriod(dayPeriod);
      int endHour = this.getEndHourForDayPeriod(dayPeriod);
      double midPoint = (double)(startHour + endHour) / 2.0;
      if (startHour > endHour) {
         midPoint += 12.0;
         if (midPoint >= 24.0) {
            midPoint -= 24.0;
         }
      }

      return midPoint;
   }

   private static DayPeriodRulesData loadData() {
      DayPeriodRulesData data = new DayPeriodRulesData();
      ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "dayPeriods", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
      DayPeriodRulesCountSink countSink = new DayPeriodRulesCountSink(data);
      rb.getAllItemsWithFallback("rules", countSink);
      data.rules = new DayPeriodRules[data.maxRuleSetNum + 1];
      DayPeriodRulesDataSink sink = new DayPeriodRulesDataSink(data);
      rb.getAllItemsWithFallback("", sink);
      return data;
   }

   private int getStartHourForDayPeriod(DayPeriod dayPeriod) throws IllegalArgumentException {
      if (dayPeriod == DayPeriodRules.DayPeriod.MIDNIGHT) {
         return 0;
      } else if (dayPeriod == DayPeriodRules.DayPeriod.NOON) {
         return 12;
      } else {
         int i;
         if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for(i = 22; i >= 1; --i) {
               if (this.dayPeriodForHour[i] != dayPeriod) {
                  return i + 1;
               }
            }
         } else {
            for(i = 0; i <= 23; ++i) {
               if (this.dayPeriodForHour[i] == dayPeriod) {
                  return i;
               }
            }
         }

         throw new IllegalArgumentException();
      }
   }

   private int getEndHourForDayPeriod(DayPeriod dayPeriod) {
      if (dayPeriod == DayPeriodRules.DayPeriod.MIDNIGHT) {
         return 0;
      } else if (dayPeriod == DayPeriodRules.DayPeriod.NOON) {
         return 12;
      } else {
         int i;
         if (this.dayPeriodForHour[0] == dayPeriod && this.dayPeriodForHour[23] == dayPeriod) {
            for(i = 1; i <= 22; ++i) {
               if (this.dayPeriodForHour[i] != dayPeriod) {
                  return i;
               }
            }
         } else {
            for(i = 23; i >= 0; --i) {
               if (this.dayPeriodForHour[i] == dayPeriod) {
                  return i + 1;
               }
            }
         }

         throw new IllegalArgumentException();
      }
   }

   public boolean hasMidnight() {
      return this.hasMidnight;
   }

   public boolean hasNoon() {
      return this.hasNoon;
   }

   public DayPeriod getDayPeriodForHour(int hour) {
      return this.dayPeriodForHour[hour];
   }

   private void add(int startHour, int limitHour, DayPeriod period) {
      for(int i = startHour; i != limitHour; ++i) {
         if (i == 24) {
            i = 0;
         }

         this.dayPeriodForHour[i] = period;
      }

   }

   private static int parseSetNum(String setNumStr) {
      if (!setNumStr.startsWith("set")) {
         throw new ICUException("Set number should start with \"set\".");
      } else {
         String numStr = setNumStr.substring(3);
         return Integer.parseInt(numStr);
      }
   }

   // $FF: synthetic method
   DayPeriodRules(Object x0) {
      this();
   }

   private static class DayPeriodRulesCountSink extends UResource.Sink {
      private DayPeriodRulesData data;

      private DayPeriodRulesCountSink(DayPeriodRulesData data) {
         this.data = data;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table rules = value.getTable();

         for(int i = 0; rules.getKeyAndValue(i, key, value); ++i) {
            int setNum = DayPeriodRules.parseSetNum(key.toString());
            if (setNum > this.data.maxRuleSetNum) {
               this.data.maxRuleSetNum = setNum;
            }
         }

      }

      // $FF: synthetic method
      DayPeriodRulesCountSink(DayPeriodRulesData x0, Object x1) {
         this(x0);
      }
   }

   private static final class DayPeriodRulesDataSink extends UResource.Sink {
      private DayPeriodRulesData data;
      private int[] cutoffs;
      private int ruleSetNum;
      private DayPeriod period;
      private CutoffType cutoffType;

      private DayPeriodRulesDataSink(DayPeriodRulesData data) {
         this.cutoffs = new int[25];
         this.data = data;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table dayPeriodData = value.getTable();

         for(int i = 0; dayPeriodData.getKeyAndValue(i, key, value); ++i) {
            UResource.Table rules;
            if (key.contentEquals("locales")) {
               rules = value.getTable();

               for(int j = 0; rules.getKeyAndValue(j, key, value); ++j) {
                  int setNum = DayPeriodRules.parseSetNum(value.getString());
                  this.data.localesToRuleSetNumMap.put(key.toString(), setNum);
               }
            } else if (key.contentEquals("rules")) {
               rules = value.getTable();
               this.processRules(rules, key, value);
            }
         }

      }

      private void processRules(UResource.Table rules, UResource.Key key, UResource.Value value) {
         for(int i = 0; rules.getKeyAndValue(i, key, value); ++i) {
            this.ruleSetNum = DayPeriodRules.parseSetNum(key.toString());
            this.data.rules[this.ruleSetNum] = new DayPeriodRules();
            UResource.Table ruleSet = value.getTable();

            int k;
            for(int j = 0; ruleSet.getKeyAndValue(j, key, value); ++j) {
               this.period = DayPeriodRules.DayPeriod.fromStringOrNull(key);
               if (this.period == null) {
                  throw new ICUException("Unknown day period in data.");
               }

               UResource.Table periodDefinition = value.getTable();

               for(k = 0; periodDefinition.getKeyAndValue(k, key, value); ++k) {
                  if (value.getType() == 0) {
                     CutoffType type = DayPeriodRules.CutoffType.fromStringOrNull(key);
                     this.addCutoff(type, value.getString());
                  } else {
                     this.cutoffType = DayPeriodRules.CutoffType.fromStringOrNull(key);
                     UResource.Array cutoffArray = value.getArray();
                     int length = cutoffArray.getSize();

                     for(int l = 0; l < length; ++l) {
                        cutoffArray.getValue(l, value);
                        this.addCutoff(this.cutoffType, value.getString());
                     }
                  }
               }

               this.setDayPeriodForHoursFromCutoffs();

               for(k = 0; k < this.cutoffs.length; ++k) {
                  this.cutoffs[k] = 0;
               }
            }

            DayPeriod[] var12 = this.data.rules[this.ruleSetNum].dayPeriodForHour;
            int var13 = var12.length;

            for(k = 0; k < var13; ++k) {
               DayPeriod period = var12[k];
               if (period == null) {
                  throw new ICUException("Rules in data don't cover all 24 hours (they should).");
               }
            }
         }

      }

      private void addCutoff(CutoffType type, String hourStr) {
         if (type == null) {
            throw new ICUException("Cutoff type not recognized.");
         } else {
            int hour = parseHour(hourStr);
            int[] var10000 = this.cutoffs;
            var10000[hour] |= 1 << type.ordinal();
         }
      }

      private void setDayPeriodForHoursFromCutoffs() {
         DayPeriodRules rule = this.data.rules[this.ruleSetNum];

         label54:
         for(int startHour = 0; startHour <= 24; ++startHour) {
            if ((this.cutoffs[startHour] & 1 << DayPeriodRules.CutoffType.AT.ordinal()) > 0) {
               if (startHour == 0 && this.period == DayPeriodRules.DayPeriod.MIDNIGHT) {
                  rule.hasMidnight = true;
               } else {
                  if (startHour != 12 || this.period != DayPeriodRules.DayPeriod.NOON) {
                     throw new ICUException("AT cutoff must only be set for 0:00 or 12:00.");
                  }

                  rule.hasNoon = true;
               }
            }

            if ((this.cutoffs[startHour] & 1 << DayPeriodRules.CutoffType.FROM.ordinal()) > 0 || (this.cutoffs[startHour] & 1 << DayPeriodRules.CutoffType.AFTER.ordinal()) > 0) {
               for(int hour = startHour + 1; hour != startHour; ++hour) {
                  if (hour == 25) {
                     hour = 0;
                  }

                  if ((this.cutoffs[hour] & 1 << DayPeriodRules.CutoffType.BEFORE.ordinal()) > 0) {
                     rule.add(startHour, hour, this.period);
                     continue label54;
                  }
               }

               throw new ICUException("FROM/AFTER cutoffs must have a matching BEFORE cutoff.");
            }
         }

      }

      private static int parseHour(String str) {
         int firstColonPos = str.indexOf(58);
         if (firstColonPos >= 0 && str.substring(firstColonPos).equals(":00")) {
            String hourStr = str.substring(0, firstColonPos);
            if (firstColonPos != 1 && firstColonPos != 2) {
               throw new ICUException("Cutoff time must begin with h: or hh:");
            } else {
               int hour = Integer.parseInt(hourStr);
               if (hour >= 0 && hour <= 24) {
                  return hour;
               } else {
                  throw new ICUException("Cutoff hour must be between 0 and 24, inclusive.");
               }
            }
         } else {
            throw new ICUException("Cutoff time must end in \":00\".");
         }
      }

      // $FF: synthetic method
      DayPeriodRulesDataSink(DayPeriodRulesData x0, Object x1) {
         this(x0);
      }
   }

   private static final class DayPeriodRulesData {
      Map localesToRuleSetNumMap;
      DayPeriodRules[] rules;
      int maxRuleSetNum;

      private DayPeriodRulesData() {
         this.localesToRuleSetNumMap = new HashMap();
         this.maxRuleSetNum = -1;
      }

      // $FF: synthetic method
      DayPeriodRulesData(Object x0) {
         this();
      }
   }

   private static enum CutoffType {
      BEFORE,
      AFTER,
      FROM,
      AT;

      private static CutoffType fromStringOrNull(CharSequence str) {
         if ("from".contentEquals(str)) {
            return FROM;
         } else if ("before".contentEquals(str)) {
            return BEFORE;
         } else if ("after".contentEquals(str)) {
            return AFTER;
         } else {
            return "at".contentEquals(str) ? AT : null;
         }
      }
   }

   public static enum DayPeriod {
      MIDNIGHT,
      NOON,
      MORNING1,
      AFTERNOON1,
      EVENING1,
      NIGHT1,
      MORNING2,
      AFTERNOON2,
      EVENING2,
      NIGHT2,
      AM,
      PM;

      public static DayPeriod[] VALUES = values();

      private static DayPeriod fromStringOrNull(CharSequence str) {
         if ("midnight".contentEquals(str)) {
            return MIDNIGHT;
         } else if ("noon".contentEquals(str)) {
            return NOON;
         } else if ("morning1".contentEquals(str)) {
            return MORNING1;
         } else if ("afternoon1".contentEquals(str)) {
            return AFTERNOON1;
         } else if ("evening1".contentEquals(str)) {
            return EVENING1;
         } else if ("night1".contentEquals(str)) {
            return NIGHT1;
         } else if ("morning2".contentEquals(str)) {
            return MORNING2;
         } else if ("afternoon2".contentEquals(str)) {
            return AFTERNOON2;
         } else if ("evening2".contentEquals(str)) {
            return EVENING2;
         } else if ("night2".contentEquals(str)) {
            return NIGHT2;
         } else if ("am".contentEquals(str)) {
            return AM;
         } else {
            return "pm".contentEquals(str) ? PM : null;
         }
      }
   }
}
