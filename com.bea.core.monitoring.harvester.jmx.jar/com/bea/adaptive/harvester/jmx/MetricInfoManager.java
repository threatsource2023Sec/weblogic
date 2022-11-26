package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.WatchedValues;
import com.bea.adaptive.harvester.utils.collections.ExtensibleList;
import com.bea.adaptive.harvester.utils.collections.ListSet;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class MetricInfoManager {
   private HarvesterDebugLogger dbg;
   private static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   ExtensibleList wvidCategories = new ExtensibleList(3);

   public MetricInfoManager(BaseHarvesterImpl harv) {
      this.dbg = harv.getDebugLogger();
   }

   public boolean addSample(String type, String inst, AttributeSpec attr, int wvid, WatchedValues.Values slot) {
      WVIDCategory cat = this.getCategory(wvid, false);
      boolean added = cat.addMetric(type, inst, attr, wvid, slot);
      return added;
   }

   void addWLID(int wvid) {
      if (this.getCategory(wvid, true) != null) {
         throw new RuntimeException(mtf_base.getWVIDExistsMsg(wvid));
      } else {
         this.wvidCategories.set(wvid, new WVIDCategory(wvid));
      }
   }

   List getActiveTypes(int wvid, String typeNameRegex) {
      Pattern pat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
      WVIDCategory cat = this.getCategory(wvid, true);
      if (cat == null) {
         return Collections.EMPTY_LIST;
      } else {
         Set set = new HashSet();
         Map attrs = cat.slotKeyListByWVSlot;
         Iterator it = attrs.values().iterator();

         label36:
         while(it.hasNext()) {
            Set slotKeys = (Set)it.next();
            Iterator it2 = slotKeys.iterator();

            while(true) {
               String typeName;
               do {
                  if (!it2.hasNext()) {
                     continue label36;
                  }

                  SlotKey key = (SlotKey)it2.next();
                  typeName = key.getTypeName();
               } while(pat != null && !pat.matcher(typeName).matches());

               set.add(typeName);
            }
         }

         List ret = new ArrayList(set.size());
         ret.addAll(set);
         return ret;
      }
   }

   List getActiveAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      Pattern typePat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
      Pattern attrPat = attrNameRegex != null ? Pattern.compile(attrNameRegex) : null;
      WVIDCategory cat = this.getCategory(wvid, true);
      if (cat == null) {
         return Collections.EMPTY_LIST;
      } else {
         Set set = new HashSet();
         Map attrs = cat.slotKeyListByWVSlot;
         Iterator it = attrs.values().iterator();

         label59:
         while(it.hasNext()) {
            Set slotKeys = (Set)it.next();
            Iterator it2 = slotKeys.iterator();

            while(true) {
               String attrName;
               do {
                  do {
                     SlotKey key;
                     String typeName;
                     do {
                        do {
                           if (!it2.hasNext()) {
                              continue label59;
                           }

                           key = (SlotKey)it2.next();
                           typeName = key.getTypeName();
                        } while(typeName == null);
                     } while(typePat != null && !typePat.matcher(typeName).matches());

                     attrName = key.getAttributeName();
                  } while(attrName == null);
               } while(attrPat != null && !attrPat.matcher(attrName).matches());

               set.add(attrName);
            }
         }

         List ret = new ArrayList(set.size());
         ret.addAll(set);
         return ret;
      }
   }

   List getActiveInstances(int wvid, String typeNameRegex, String instNameRegex) {
      Pattern typePat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
      Pattern instPat = instNameRegex != null ? Pattern.compile(instNameRegex) : null;
      WVIDCategory cat = this.getCategory(wvid, true);
      if (cat == null) {
         return Collections.EMPTY_LIST;
      } else {
         Set set = new HashSet();
         Map attrs = cat.slotKeyListByWVSlot;
         Iterator it = attrs.values().iterator();

         label49:
         while(it.hasNext()) {
            Set slotKeys = (Set)it.next();
            Iterator it2 = slotKeys.iterator();

            while(true) {
               String instName;
               do {
                  SlotKey key;
                  String typeName;
                  do {
                     if (!it2.hasNext()) {
                        continue label49;
                     }

                     key = (SlotKey)it2.next();
                     typeName = key.getTypeName();
                  } while(typePat != null && !typePat.matcher(typeName).matches());

                  instName = key.getInstanceName();
               } while(instPat != null && !instPat.matcher(instName).matches());

               set.add(instName);
            }
         }

         List ret = new ArrayList(set.size());
         ret.addAll(set);
         return ret;
      }
   }

   private WVIDCategory getCategory(int wvid, boolean errOK) {
      WVIDCategory cat = null;
      if (wvid < this.wvidCategories.size()) {
         cat = (WVIDCategory)this.wvidCategories.get(wvid);
      }

      if (cat == null && !errOK) {
         throw new RuntimeException(mtf_base.getNoSuchWVID(wvid));
      } else {
         return cat;
      }
   }

   public int deleteSelectedMetricsForWVID(int wvid, Collection slots) {
      int count = 0;
      WVIDCategory cat = this.getCategory(wvid, true);
      if (cat == null) {
         return 0;
      } else {
         Iterator it = slots.iterator();

         while(it.hasNext()) {
            WatchedValues.Values wvSlot = (WatchedValues.Values)it.next();
            Set slotKeys = (Set)cat.slotKeyListByWVSlot.remove(wvSlot);
            if (slotKeys != null) {
               count += slotKeys.size();
            }
         }

         return count;
      }
   }

   int deleteSelectedWVIDs(int[] wvids) {
      int count = this.deleteAllMetricsForSelectedWVIDs(wvids);
      int[] var3 = wvids;
      int var4 = wvids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int wvid = var3[var5];
         this.wvidCategories.remove(wvid);
      }

      return count;
   }

   int deleteAllMetricsForSelectedWVIDs(int[] wvids) {
      int count = 0;

      for(int i = 0; i < wvids.length; ++i) {
         int wvid = wvids[i];
         WVIDCategory cat = this.getCategory(wvid, false);

         Set keys;
         for(Iterator it = cat.slotKeyListByWVSlot.values().iterator(); it.hasNext(); count += keys.size()) {
            keys = (Set)it.next();
         }
      }

      return count;
   }

   public void removeSamples(String attrName, String typeName, List wvids) {
      Iterator var4 = wvids.iterator();

      while(var4.hasNext()) {
         Integer wvid = (Integer)var4.next();
         WVIDCategory cat = this.getCategory(wvid, false);
         cat.removeMetrics(attrName, typeName);
      }

   }

   public void removeSamples(String attrName, String typeName, String instName, List wvids) {
      Iterator var5 = wvids.iterator();

      while(var5.hasNext()) {
         Integer wvid = (Integer)var5.next();
         WVIDCategory cat = this.getCategory(wvid, false);
         cat.removeMetrics(attrName, typeName, instName);
      }

   }

   public Map getMetrics(Map vidsByWLID) {
      if (this.dbg.isDebugREnabled()) {
         this.dbg.dbgR(this.dumpCategoriesAsString("   ", this.wvidCategories));
      }

      Map ret = new HashMap();
      Iterator it;
      if (vidsByWLID != null) {
         it = vidsByWLID.keySet().iterator();

         while(it.hasNext()) {
            int wvid = (Integer)it.next();
            Set vidsWanted = (Set)vidsByWLID.get(wvid);
            this.addMetricsForWVID(wvid, vidsWanted, ret);
         }
      } else {
         it = this.wvidCategories.iterator();

         while(it.hasNext()) {
            WVIDCategory cat = (WVIDCategory)it.next();
            int wvid = cat.wvid;
            this.addMetricsForWVID(wvid, (Set)null, ret);
         }
      }

      return ret;
   }

   public Map getMetrics(int wvid, Set vids) {
      if (this.dbg.isDebugREnabled()) {
         this.dbg.dbgR(this.dumpCategoriesAsString("   ", this.wvidCategories));
      }

      Map ret = new HashMap();
      this.addMetricsForWVID(wvid, vids, ret);
      return ret;
   }

   private void addMetricsForWVID(int wvid, Set vidsWanted, Map accum) {
      WVIDCategory cat = this.getCategory(wvid, false);
      Iterator it2 = cat.slotKeyListByWVSlot.keySet().iterator();

      while(true) {
         while(it2.hasNext()) {
            WatchedValues.Values wvSlot = (WatchedValues.Values)it2.next();
            int vid = wvSlot.getVID();
            if (vidsWanted != null) {
               Iterator it3 = vidsWanted.iterator();

               while(it3.hasNext()) {
                  if (vid == (Integer)it3.next()) {
                     this.addMetric(wvid, cat, wvSlot, accum);
                  }
               }
            } else {
               this.addMetric(wvid, cat, wvSlot, accum);
            }
         }

         return;
      }
   }

   private void addMetric(int wvid, WVIDCategory cat, WatchedValues.Values wvSlot, Map accum) {
      Set keys = (Set)cat.slotKeyListByWVSlot.get(wvSlot);

      ListSet specs;
      for(Iterator it4 = keys.iterator(); it4.hasNext(); specs.addNoCheck(new SlotSpec(wvSlot, wvid))) {
         SlotKey key = (SlotKey)it4.next();
         specs = (ListSet)accum.get(key);
         if (specs == null) {
            specs = new ListSet();
            accum.put(key, specs);
         }
      }

   }

   public void shutdown(boolean forceShutdown) {
      this.wvidCategories = null;
   }

   private String dumpCategoriesAsString(String prefix, ArrayList categories) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      this.dumpCategories(pw, prefix, categories);
      pw.flush();
      String s = sw.toString();
      pw.close();
      return s;
   }

   public void dumpCategories(PrintWriter strm, String prefix) {
      this.dumpCategories(strm, prefix, (ArrayList)null);
   }

   public void dumpCategories(PrintWriter strm, String prefix, ArrayList categories) {
      if (categories == null) {
         categories = this.wvidCategories;
      }

      strm.println(prefix + mtf_base.getCategoriesLabel() + ":");
      Iterator it = ((ArrayList)categories).iterator();

      while(it.hasNext()) {
         WVIDCategory sc = (WVIDCategory)it.next();
         strm.println("");
         sc.dump(strm, prefix + "  ");
      }

      strm.println("");
   }

   private static class WVIDCategory {
      private int wvid;
      private Map slotKeyListByWVSlot = new HashMap();

      public void dump(PrintWriter strm, String prefix) {
         strm.println(prefix + this);
         prefix = prefix + "  ";
         strm.println(prefix + MetricInfoManager.mtf_base.getWVIDLabel() + "=" + this.wvid);
         Map attrs = this.slotKeyListByWVSlot;
         Iterator it = attrs.keySet().iterator();

         while(it.hasNext()) {
            WatchedValues.Values wvSlot = (WatchedValues.Values)it.next();
            strm.println("");
            strm.println(prefix + MetricInfoManager.mtf_base.getForMetricDescrLabel() + ":");
            strm.println(wvSlot.dump(prefix + "  ", true, false));
            Set slotKeys = (Set)this.slotKeyListByWVSlot.get(wvSlot);
            Iterator it2 = slotKeys.iterator();

            while(it2.hasNext()) {
               SlotKey key = (SlotKey)it2.next();
               strm.println(prefix + "  " + MetricInfoManager.mtf_base.getInstLabel() + ": " + key.getInstanceName());
               strm.println(prefix + "  " + MetricInfoManager.mtf_base.getAttrLabel() + ": " + key.getAttributeSpec().getName());
            }
         }

         strm.println("");
      }

      WVIDCategory(int wvid) {
         this.wvid = wvid;
      }

      private boolean addMetric(String typeName, String instName, AttributeSpec attr, int wvid, WatchedValues.Values wvSlot) {
         SlotKey key = new SlotKey(typeName, instName, attr);
         Set slotKeyListForWVSlot = (Set)this.slotKeyListByWVSlot.get(wvSlot);
         if (slotKeyListForWVSlot == null) {
            slotKeyListForWVSlot = new HashSet();
            this.slotKeyListByWVSlot.put(wvSlot, slotKeyListForWVSlot);
         }

         boolean added = ((Set)slotKeyListForWVSlot).add(key);
         return added;
      }

      public int removeMetrics(String attrName, String typeName) {
         return this.removeMetrics(attrName, typeName, (String)null);
      }

      public int removeMetrics(String attrName, String typeName, String instName) {
         int count = 0;
         Map attrs = this.slotKeyListByWVSlot;
         Iterator slotSetIterator = attrs.values().iterator();

         while(slotSetIterator.hasNext()) {
            Set slotKeys = (Set)slotSetIterator.next();
            Iterator slotIterator = slotKeys.iterator();

            while(slotIterator.hasNext()) {
               SlotKey key = (SlotKey)slotIterator.next();
               String slotInstanceName = key.getInstanceName();
               boolean attrMatches = attrName == null || key.getAttributeName().equals(attrName);
               boolean instanceMatches = instName == null || slotInstanceName == null || slotInstanceName.equals(instName);
               if (attrMatches && key.getTypeName().equals(typeName) && instanceMatches) {
                  slotIterator.remove();
                  ++count;
               }
            }

            if (slotKeys.isEmpty()) {
               slotSetIterator.remove();
            }
         }

         return count;
      }

      public String toString() {
         return MetricInfoManager.mtf_base.getPullCategoryForWVIDLabel(this.wvid);
      }
   }
}
