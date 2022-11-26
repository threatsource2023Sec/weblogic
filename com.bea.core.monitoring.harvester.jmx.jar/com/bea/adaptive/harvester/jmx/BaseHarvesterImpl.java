package com.bea.adaptive.harvester.jmx;

import com.bea.adaptive.harvester.AttributeTrackedDataItemImpl;
import com.bea.adaptive.harvester.DataContextImpl;
import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.ValidationImpl;
import com.bea.adaptive.harvester.WatchedValues;
import com.bea.adaptive.harvester.WatchedValuesImpl;
import com.bea.adaptive.harvester.WatchedValues.ContextItem.AttributeTermType;
import com.bea.adaptive.harvester.utils.collections.CollectionUtils;
import com.bea.adaptive.harvester.utils.date.DateUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.management.InstanceNotFoundException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.ConcurrentHashMap;

public abstract class BaseHarvesterImpl implements Harvester, MetricCacheListener {
   private static final long serialVersionUID = 1L;
   protected static final HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();
   private static final HarvesterAdapterTextTextFormatter ADAPTER_TEXT_FORMATTER = HarvesterAdapterTextTextFormatter.getInstance();
   private boolean removeAttributesWithProblems = false;
   protected RegistrationManager metricCache;
   protected int useCount;
   private HashSet badAttributesSet = new HashSet(8);
   protected static final ConcurrentHashMap harvestersByName = new ConcurrentHashMap();
   static final String MBEAN_HARVESTABLE_TYPE_NAME = "DiagnosticTypeName";
   protected WatchedValuesArrayList watchedValuesIndex = new WatchedValuesArrayList();
   protected States.State state = BaseHarvesterImpl.States.Active.getInstance();
   private String name;
   private String namespace;
   long lastTimeStamp = -1L;
   protected MetricInfoManager metricMan;
   ArrayList wvidsWithCallbacks = new ArrayList();
   private DebugLogger logDbgC;
   private DebugLogger logDbgR;
   private DebugLogger logDbgH;
   private DebugLogger logDbgT;
   String levNameC;
   String levNameR;
   String levNameH;
   String levNameT;
   private String debugName;
   private HarvesterDebugLogger debugSpec;
   private boolean attributeValidationEnabled = true;

   public static Harvester getHarvester(String name) {
      return (Harvester)harvestersByName.get(name);
   }

   protected static void addHarvester(String name, MBeanHarvesterImpl harv) {
      harvestersByName.put(name, harv);
   }

   int[] getActiveWVIDs() {
      int[] wvids = new int[this.watchedValuesIndex.getActiveCount()];
      int i = 0;
      int reti = 0;

      for(Iterator it = this.watchedValuesIndex.iterator(); it.hasNext(); ++i) {
         WatchedValuesControlInfo ci = (WatchedValuesControlInfo)it.next();
         if (ci != null) {
            wvids[reti++] = i;
         }
      }

      return wvids;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
      if (this.dC()) {
         this.dbgC("Harvester name set to: " + name);
      }

   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String namespace) {
      this.namespace = namespace;
   }

   public void deallocate() {
      this.deallocate(false);
   }

   public void deallocate(boolean force) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("deallocate", this, (Object)null);
         }

         String name = this.getName();
         synchronized(this) {
            this.afterSync("deallocate");
            if (this.state instanceof States.Terminated) {
               return;
            }

            if (!(this.state instanceof States.Active)) {
               throw new RuntimeException(mtf_base.getInvalidStateStr(name, "" + this.state));
            }

            --this.useCount;
            if (this.dC()) {
               this.dbgC("Decremented  useCount to: " + this.useCount + " for " + name);
            }

            if (this.useCount <= 0) {
               this.state = BaseHarvesterImpl.States.ShuttingDown.getInstance();
               if (this.metricCache != null) {
                  this.metricCache.shutdown(force);
               }

               if (this.dC()) {
                  this.dbgC("RegistrationManager has been shut down.");
               }

               this.metricCache = null;
               if (this.metricMan != null) {
                  this.metricMan.shutdown(force);
               }

               if (this.dC()) {
                  this.dbgC("MetricManager has been shut down.");
               }

               this.metricMan = null;
               this.watchedValuesIndex = null;
               this.wvidsWithCallbacks = null;
               this.state = BaseHarvesterImpl.States.Terminated.getInstance();
               harvestersByName.remove(name);
               if (this.dC()) {
                  this.dbgC("Shutdown completed for " + name + ". Remaining harvesters: " + ats(harvestersByName.keySet()));
               }

               return;
            }
         }
      } finally {
         this.unsynced("deallocate");
      }

   }

   public String[][] getKnownHarvestableTypes(String typeNameRegex) throws IOException {
      String[][] var3;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getKnownHarvestableTypes", this, "   typeNameRegex=" + typeNameRegex);
         }

         synchronized(this) {
            this.afterSync("getKnownHarvestableTypes");
            var3 = this.metricCache.getKnownHarvestableTypes(typeNameRegex);
         }
      } finally {
         this.unsynced("getKnownHarvestableTypes");
      }

      return var3;
   }

   public WatchedValues.Values[] getPendingMetrics(int wvid) {
      WatchedValues.Values[] var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getPendingMetrics", this, "   wvid=" + wvid);
         }

         synchronized(this) {
            this.afterSync("getPendingMetrics");
            WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
            if (ci.pendingMetrics.size() > 0) {
               ArrayList um = new ArrayList(ci.pendingMetrics.size());
               Iterator it = ci.pendingMetrics.iterator();

               while(it.hasNext()) {
                  WatchedValues.Values metric = (WatchedValues.Values)it.next();
                  um.add(metric);
               }

               WatchedValues.Values[] var14 = (WatchedValues.Values[])((WatchedValues.Values[])um.toArray(new WatchedValues.Values[um.size()]));
               return var14;
            }

            var4 = new WatchedValues.Values[0];
         }
      } finally {
         this.unsynced("getPendingMetrics");
      }

      return var4;
   }

   public List getKnownHarvestableInstances(String typeName, String instNameRegex) throws IOException {
      List var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getKnownHarvestableInstances", this, "   typeName=" + typeName + "   instNameRegex=" + instNameRegex);
         }

         synchronized(this) {
            this.afterSync("getKnownHarvestableInstances");
            if (this.state instanceof States.Inactive) {
               throw new RuntimeException(mtf_base.getInvalidStateStr(this.getName(), "" + this.state));
            }

            var4 = this.metricCache.getAllCurrentInstanceNames(typeName, instNameRegex);
         }
      } finally {
         this.unsynced("getKnownHarvestableInstances");
      }

      return var4;
   }

   public List getHarvestedTypes(int wvid, String typeNameRegex) {
      List var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getHarvestedTypes", this, "   wvid=" + wvid + "   typeNameRegex=" + typeNameRegex);
         }

         synchronized(this) {
            this.afterSync("getHarvestedTypes");
            var4 = this.metricMan.getActiveTypes(wvid, typeNameRegex);
         }
      } finally {
         this.unsynced("getHarvestedTypes");
      }

      return var4;
   }

   private long getNewTimeStamp() {
      long timeStamp = DateUtils.getNanoTimestamp();
      if (timeStamp <= this.lastTimeStamp) {
         timeStamp = this.lastTimeStamp + 1L;
      }

      this.lastTimeStamp = timeStamp;
      return timeStamp;
   }

   public void harvest(Map vidsPerWVID) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("harvest", this, "   map=" + CollectionUtils.aggregateToString(vidsPerWVID));
         }

         synchronized(this) {
            this.afterSync("harvest");
            if (this.dH()) {
               this.dbgH("Attempting to harvest the following: " + CollectionUtils.aggregateToString(vidsPerWVID));
            }

            long timestamp = this.getNewTimeStamp();
            if (vidsPerWVID != null) {
               Iterator it = vidsPerWVID.entrySet().iterator();

               while(it.hasNext()) {
                  Map.Entry nextEntry = (Map.Entry)it.next();
                  Integer wvid = (Integer)nextEntry.getKey();
                  WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
                  if (!ci.watchedValues.isShared()) {
                     ci.watchedValues.setTimeStamp(timestamp);
                  }

                  Map vidsToHarvest = this.metricMan.getMetrics(wvid, (Set)nextEntry.getValue());
                  this.harvestX(vidsToHarvest, ci.watchedValues.isAttributeNameTrackingEnabled());
               }
            } else {
               int[] activeWVIDs = this.getActiveWVIDs();
               int[] var19 = activeWVIDs;
               int var20 = activeWVIDs.length;

               for(int var21 = 0; var21 < var20; ++var21) {
                  int wvid = var19[var21];
                  WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
                  if (!ci.watchedValues.isShared()) {
                     ci.watchedValues.setTimeStamp(timestamp);
                  }

                  Map metrics = this.metricMan.getMetrics((Map)null);
                  this.harvestX(metrics, ci.watchedValues.isAttributeNameTrackingEnabled());
               }
            }
         }
      } finally {
         this.unsynced("harvest");
      }

   }

   private void harvestX(Map metrics, boolean trackAttributeNames) {
      Iterator slotKeyIterator = metrics.entrySet().iterator();

      while(true) {
         SlotKey key;
         Set slotSpecs;
         do {
            if (!slotKeyIterator.hasNext()) {
               return;
            }

            Map.Entry nextEntry = (Map.Entry)slotKeyIterator.next();
            key = (SlotKey)nextEntry.getKey();
            slotSpecs = (Set)nextEntry.getValue();
            Iterator slotSpecsIt = slotSpecs.iterator();

            while(slotSpecsIt.hasNext()) {
               SlotSpec slotSpec = (SlotSpec)slotSpecsIt.next();
               WatchedValues.Values slot = slotSpec.getSlot();
               if (!slot.isEnabled()) {
                  slotSpecsIt.remove();
               }
            }
         } while(slotSpecs.isEmpty());

         AttributeSpec attrSpec = key.getAttributeSpec();
         String attrName = attrSpec.getName();
         String instName = key.getInstanceName();
         String typeName = key.getTypeName();
         if (!attrSpec.isResolved()) {
            if (this.dH()) {
               this.dbgH("Resolving attribute " + attrName + " for type " + typeName);
            }

            AttributeSpec actualSpec = this.metricCache.getAttributeSpec(typeName, attrName);
            if (actualSpec != null) {
               if (this.dH()) {
                  this.dbgH("Attribute " + attrName + " for type " + typeName + " resolved, updating spec");
               }

               attrSpec.setDataType(actualSpec.getDataType());
               attrSpec.setEvalChain(actualSpec.getEvalChain());
               attrSpec.setResolved(true);
            }
         }

         try {
            Object attrValue = this.getBeanValue(instName, attrSpec, trackAttributeNames);
            if (this.dH()) {
               this.dbgH("Harvested value for attribute " + attrName + " on item " + instName + ": " + attrValue + ".");
            }

            if (attrValue != null) {
               Iterator it3 = slotSpecs.iterator();

               while(it3.hasNext()) {
                  SlotSpec slotSpec = (SlotSpec)it3.next();
                  WatchedValues.Values slot = slotSpec.getSlot();
                  slot.addValue(typeName, instName, attrValue);
               }
            }

            this.badAttributesSet.remove(key);
         } catch (InstanceNotFoundException var19) {
            if (this.dH()) {
               this.dbgH("BaseHarvesterImpl: caught INFE");
            }
         } catch (Throwable var20) {
            Throwable x = var20;

            try {
               MetricInfoManager metricMan = this.getMetricInfoManager();
               List wvidsToRemoveFrom = new ArrayList(slotSpecs.size());
               Iterator it = slotSpecs.iterator();

               while(it.hasNext()) {
                  SlotSpec slotSpec = (SlotSpec)it.next();
                  Integer wvid = slotSpec.getWVID();
                  wvidsToRemoveFrom.add(wvid);
                  WatchedValues.Values slot = slotSpec.getSlot();
                  slot.addError(typeName, instName, "" + x, true);
               }

               if (!this.badAttributesSet.contains(key)) {
                  LogSupport.logUnexpectedCondition(this.getName(), mtf_base.getDataCollectionProblemStr(attrName, instName));
                  if (this.dH()) {
                     this.dbgH("First encounter of error for slot " + key.dump("\t"));
                     x.printStackTrace();
                  }

                  this.badAttributesSet.add(key);
               }

               if (this.removeAttributesWithProblems()) {
                  metricMan.removeSamples(attrName, typeName, instName, wvidsToRemoveFrom);
                  it = wvidsToRemoveFrom.iterator();

                  while(it.hasNext()) {
                     int wvid = (Integer)it.next();
                     WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
                     ci.unharvestableAttributes.add(attrSpec);
                  }
               } else if (this.dH()) {
                  this.dbgH(mtf_base.getDataCollectionProblemStr(attrName, instName) + x);
                  x.printStackTrace();
               }
            } catch (Throwable var18) {
               throw new RuntimeException(var18);
            }
         }
      }
   }

   protected Object getBeanValue(String inst, AttributeSpec attrSpec, boolean trackAttributeNames) throws Exception {
      Object attrVal = null;
      Object instance = this.getInstance(inst);
      if (instance != null) {
         AttributeTerm term = attrSpec.getEvalChain();
         attrVal = this.evaluateComplexType(instance, (Object)null, term, trackAttributeNames);
         if (attrVal == null) {
            return null;
         }

         attrVal = this.accumulateAggegateTypes(attrVal);
      }

      return attrVal;
   }

   protected abstract Object getInstance(String var1) throws Exception;

   protected Object accumulateAggegateTypes(Object value) {
      if (value == null) {
         return null;
      } else {
         Object[] ret;
         int i;
         Iterator it;
         if (value instanceof Collection) {
            Collection c = (Collection)value;
            ret = new Object[c.size()];
            i = 0;

            for(it = c.iterator(); it.hasNext(); ++i) {
               ret[i] = it.next();
            }

            return ret;
         } else if (!(value instanceof Map)) {
            Class clz = value.getClass();
            return clz.isArray() ? value : value;
         } else {
            Map m = (Map)value;
            ret = new Object[m.size()];
            i = 0;

            for(it = m.values().iterator(); it.hasNext(); ret[i++] = it.next()) {
            }

            return ret;
         }
      }
   }

   protected Object evaluateComplexType(Object bean, Object attrVal, AttributeTerm term, boolean trackAttributeNames) throws Exception {
      if (term == null) {
         return attrVal;
      } else {
         Object val;
         int i;
         String key;
         int i;
         ArrayList dataContext;
         if (term.isSimple()) {
            String attrName = term.getName();
            if (attrName == null) {
               throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprUnnamedSimpleTermErrStr()));
            }

            WatchedValues.AttributeTrackedDataItem trackedData = null;
            if (attrVal instanceof WatchedValues.AttributeTrackedDataItem) {
               trackedData = (WatchedValues.AttributeTrackedDataItem)attrVal;
               attrVal = trackedData.getData();
            }

            ArrayList dataContext;
            if (bean != null) {
               val = this.getAttribute(bean, attrName);
               if (trackAttributeNames) {
                  dataContext = new ArrayList();
                  if (trackedData != null) {
                     dataContext.addAll(trackedData.getDataContext());
                  }

                  dataContext.add(new DataContextImpl(AttributeTermType.SIMPLE, attrName));
                  attrVal = new AttributeTrackedDataItemImpl(dataContext, val);
               } else {
                  attrVal = val;
               }
            } else {
               AttributeTerm.SimpleTerm sTerm = (AttributeTerm.SimpleTerm)term;
               dataContext = null;
               Object data;
               if (attrVal instanceof CompositeData) {
                  data = ((CompositeData)attrVal).get(attrName);
               } else {
                  boolean usingCache = false;
                  Field field = sTerm.getCachedField();
                  Method meth = sTerm.getCachedMethod();
                  if (field == null && meth == null) {
                     Class clz = attrVal.getClass();

                     try {
                        field = clz.getField(attrName);
                     } catch (NoSuchFieldException var19) {
                     }

                     if (field == null) {
                        for(i = 0; i < 2; ++i) {
                           key = i == 0 ? "get" : "is";
                           String methName = key + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
                           meth = null;

                           try {
                              meth = clz.getMethod(methName);
                           } catch (NoSuchMethodException var18) {
                           }

                           if (meth != null) {
                              break;
                           }
                        }
                     }
                  } else {
                     usingCache = true;
                  }

                  try {
                     if (field != null) {
                        data = field.get(attrVal);
                     } else {
                        if (meth == null) {
                           throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprCantLocateAccessors(term.getName())));
                        }

                        data = meth.invoke(attrVal);
                     }
                  } catch (Exception var20) {
                     if (usingCache) {
                        this.dbgH("Our cached accessors failed to retreive the value of attribute " + attrName + " on Bean " + bean + ". Refreshing the cache and retrying...");
                        sTerm.setCachedField((Field)null);
                        sTerm.setCachedMethod((Method)null);
                        return this.evaluateComplexType(bean, attrVal, term, trackAttributeNames);
                     }

                     throw var20;
                  }
               }

               if (trackAttributeNames) {
                  dataContext = new ArrayList();
                  if (trackedData != null) {
                     dataContext.addAll(trackedData.getDataContext());
                  }

                  dataContext.add(new DataContextImpl(AttributeTermType.SIMPLE, attrName));
                  attrVal = new AttributeTrackedDataItemImpl(dataContext, data);
               } else {
                  attrVal = data;
               }
            }
         } else if (term.isArray()) {
            AttributeTerm.ArrayTerm at = (AttributeTerm.ArrayTerm)term;
            i = at.getIndex();
            WatchedValues.AttributeTrackedDataItem trackedData = null;
            if (attrVal instanceof WatchedValues.AttributeTrackedDataItem) {
               trackedData = (WatchedValues.AttributeTrackedDataItem)attrVal;
               attrVal = trackedData.getData();
            }

            Class clz = attrVal.getClass();
            if (i >= 0) {
               if (clz.isArray()) {
                  attrVal = Array.get(attrVal, i);
               } else {
                  if (!(attrVal instanceof List)) {
                     throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprExpectedListOrArray()));
                  }

                  List co = (List)attrVal;
                  attrVal = co.get(i);
               }

               if (trackAttributeNames) {
                  dataContext = new ArrayList();
                  if (trackedData != null) {
                     dataContext.addAll(trackedData.getDataContext());
                  }

                  dataContext.add(new DataContextImpl(AttributeTermType.ARRAY_OR_LIST, i));
                  attrVal = new AttributeTrackedDataItemImpl(dataContext, attrVal);
               }
            } else {
               dataContext = null;
               int size = false;
               Object it;
               int size;
               if (clz.isArray()) {
                  Object[] array = (Object[])((Object[])attrVal);
                  it = new ArrayIterator(array);
                  size = array.length;
               } else {
                  if (!(attrVal instanceof List)) {
                     throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprExpectedListOrArray()));
                  }

                  List list = (List)attrVal;
                  it = list.iterator();
                  size = list.size();
               }

               SpecialList sList = new SpecialList(size);
               i = 0;

               while(((Iterator)it).hasNext()) {
                  if (trackAttributeNames) {
                     Object data = ((Iterator)it).next();
                     List dataContext = new ArrayList();
                     if (trackedData != null) {
                        dataContext.addAll(trackedData.getDataContext());
                     }

                     dataContext.add(new DataContextImpl(AttributeTermType.ARRAY_OR_LIST, i++));
                     Object data = new AttributeTrackedDataItemImpl(dataContext, data);
                     sList.add(data);
                  } else {
                     sList.add(((Iterator)it).next());
                  }
               }

               attrVal = sList;
            }
         } else {
            if (!term.isMap()) {
               throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprUnknownTermType()));
            }

            WatchedValues.AttributeTrackedDataItem trackedData = null;
            if (attrVal instanceof WatchedValues.AttributeTrackedDataItem) {
               trackedData = (WatchedValues.AttributeTrackedDataItem)attrVal;
               attrVal = trackedData.getData();
            }

            if (attrVal == null) {
               return null;
            }

            if (!(attrVal instanceof Map)) {
               throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprExpectedMap()));
            }

            AttributeTerm.MapTerm mt = (AttributeTerm.MapTerm)term;
            String[] keys = mt.getKeys();
            Pattern keyPat = mt.getPattern();
            Map map = (Map)attrVal;
            if (keys != null) {
               SpecialList sList;
               ArrayList dataContext;
               if (keys.length > 0) {
                  sList = new SpecialList(keys.length);
                  String[] var46 = keys;
                  int var43 = keys.length;

                  for(i = 0; i < var43; ++i) {
                     key = var46[i];
                     key = key.trim();
                     if (trackAttributeNames) {
                        dataContext = new ArrayList();
                        if (trackedData != null) {
                           dataContext.addAll(trackedData.getDataContext());
                        }

                        dataContext.add(new DataContextImpl(AttributeTermType.MAP, key));
                        Object val = new AttributeTrackedDataItemImpl(dataContext, map.get(key));
                        sList.add(val);
                     } else {
                        sList.add(map.get(key));
                     }
                  }

                  if (keys.length == 1) {
                     attrVal = sList.get(0);
                  } else {
                     attrVal = sList;
                  }
               } else {
                  Map.Entry nextEntry;
                  Iterator mapIt;
                  if (keyPat != null) {
                     sList = new SpecialList(map.size());
                     mapIt = map.entrySet().iterator();

                     while(mapIt.hasNext()) {
                        nextEntry = (Map.Entry)mapIt.next();

                        try {
                           Object key = (String)nextEntry.getKey();
                           key = (String)key;
                           if (keyPat.matcher(key).matches()) {
                              Object mapVal = nextEntry.getValue();
                              if (trackAttributeNames) {
                                 List dataContext = new ArrayList();
                                 if (trackedData != null) {
                                    dataContext.addAll(trackedData.getDataContext());
                                 }

                                 dataContext.add(new DataContextImpl(AttributeTermType.MAP, key));
                                 sList.add(new AttributeTrackedDataItemImpl(dataContext, mapVal));
                              } else {
                                 sList.add(mapVal);
                              }
                           }
                        } catch (ClassCastException var17) {
                           throw new RuntimeException(mtf_base.getAttrExprErrStr("" + term, mtf_base.getAttrExprExpectedStringMapKey()));
                        }
                     }

                     attrVal = sList;
                  } else {
                     sList = new SpecialList(map.size());
                     mapIt = map.entrySet().iterator();

                     while(mapIt.hasNext()) {
                        nextEntry = (Map.Entry)mapIt.next();
                        Object key = nextEntry.getKey();
                        Object mapVal = nextEntry.getValue();
                        if (trackAttributeNames) {
                           dataContext = new ArrayList();
                           if (trackedData != null) {
                              dataContext.addAll(trackedData.getDataContext());
                           }

                           dataContext.add(new DataContextImpl(AttributeTermType.MAP, key));
                           sList.add(new AttributeTrackedDataItemImpl(dataContext, mapVal));
                        } else {
                           sList.add(mapVal);
                        }
                     }

                     attrVal = sList;
                  }
               }
            }
         }

         if (attrVal instanceof SpecialList) {
            SpecialList l = (SpecialList)attrVal;

            for(i = 0; i < l.size(); ++i) {
               val = l.get(i);
               l.set(i, this.recurse(val, term.getNext(), trackAttributeNames));
            }

            attrVal = l.toArray();
         } else {
            attrVal = this.recurse(attrVal, term.getNext(), trackAttributeNames);
         }

         return attrVal;
      }
   }

   protected abstract Object getAttribute(Object var1, String var2) throws Exception;

   protected Object recurse(Object attrVal, AttributeTerm term, boolean trackAttributeNames) throws Exception {
      ObjectName mbean = null;
      if (attrVal != null) {
         if (attrVal instanceof ObjectName) {
            mbean = (ObjectName)attrVal;
         }

         if (attrVal instanceof WatchedValues.AttributeTrackedDataItem) {
            WatchedValues.AttributeTrackedDataItem trackedItem = (WatchedValues.AttributeTrackedDataItem)attrVal;
            Object data = trackedItem.getData();
            if (data != null && data instanceof ObjectName) {
               mbean = (ObjectName)data;
            }
         }

         attrVal = this.evaluateComplexType(mbean, attrVal, term, trackAttributeNames);
      }

      return attrVal;
   }

   public List getHarvestedAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      List var5;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getHarvestedAttributes", this, "   wvid=" + wvid + "   typeNameRegex=" + typeNameRegex + "   attrNameRegex=" + attrNameRegex);
         }

         synchronized(this) {
            this.afterSync("getHarvestedAttributes");
            var5 = this.metricMan.getActiveAttributes(wvid, typeNameRegex, attrNameRegex);
         }
      } finally {
         this.unsynced("getHarvestedAttributes");
      }

      return var5;
   }

   public List getHarvestedInstances(int wvid, String typeName, String instNameRegex) {
      List var5;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getHarvestedInstances", this, "   wvid=" + wvid + "   typeName=" + typeName + "   instNameRegex=" + instNameRegex);
         }

         synchronized(this) {
            this.afterSync("getHarvestedInstances");
            var5 = this.metricMan.getActiveInstances(wvid, typeName, instNameRegex);
         }
      } finally {
         this.unsynced("getHarvestedInstances");
      }

      return var5;
   }

   public List getUnharvestableAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getUnharvestableAttributes", this, "   wvid=" + wvid + "   typeNameRegex=" + typeNameRegex + "   attrNameRegex=" + attrNameRegex);
         }

         synchronized(this) {
            this.afterSync("getUnharvestableAttributes");
            Pattern attrPat = attrNameRegex != null ? Pattern.compile(attrNameRegex) : null;
            Pattern typePat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
            HashSet set = new HashSet();
            WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
            Iterator it = ci.unharvestableAttributes.iterator();

            while(it.hasNext()) {
               AttributeSpec attr = (AttributeSpec)it.next();
               String attrName = attr.getName();
               String typeName = attr.getMBeanType();
               if ((typePat == null || typePat.matcher(typeName).matches()) && (attrPat == null || attrPat.matcher(attrName).matches())) {
                  set.add(attrName);
               }
            }

            List ret = new ArrayList(set.size());
            ret.addAll(set);
            ArrayList var19 = ret;
            return var19;
         }
      } finally {
         this.unsynced("getUnharvestableAttributes");
      }
   }

   public List getDisabledAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getDisabledAttributes", this, "   wvid=" + wvid + "   typeNameRegex=" + typeNameRegex + "   attrNameRegex=" + attrNameRegex);
         }

         synchronized(this) {
            this.afterSync("getDisabledAttributes");
            Pattern attrPat = attrNameRegex != null ? Pattern.compile(attrNameRegex) : null;
            Pattern typePat = typeNameRegex != null ? Pattern.compile(typeNameRegex) : null;
            HashSet set = new HashSet();
            WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
            Iterator it = ci.watchedValues.getWatchedMetrics();

            while(it.hasNext()) {
               WatchedValues.Values metric = (WatchedValues.Values)it.next();
               if (!metric.isEnabled()) {
                  String attrName = metric.getAttributeName();
                  String typeName = metric.getTypeName();
                  if ((typePat == null || typePat.matcher(typeName).matches()) && (attrPat == null || attrPat.matcher(attrName).matches())) {
                     set.add(attrName);
                  }
               }
            }

            List ret = new ArrayList(set.size());
            ret.addAll(set);
            ArrayList var19 = ret;
            return var19;
         }
      } finally {
         this.unsynced("getDisabledAttributes");
      }
   }

   public String[][] getHarvestableAttributes(String typeName, String attrNameRegex) throws IOException {
      String[][] var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("getHarvestableAttributes", this, "   typeName=" + typeName + "   attrNameRegex=" + attrNameRegex);
         }

         synchronized(this) {
            this.afterSync("getHarvestableAttributes");
            if (this.state instanceof States.Inactive) {
               throw new RuntimeException(mtf_base.getInvalidStateStr(this.getName(), "" + this.state));
            }

            var4 = this.metricCache.getHarvestableAttributes(typeName, attrNameRegex);
         }
      } finally {
         this.unsynced("getHarvestableAttributes");
      }

      return var4;
   }

   public int isTypeHandled(String typeName) {
      byte var11;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("isTypeHandled", this, "   typeName=" + typeName);
         }

         synchronized(this) {
            this.afterSync("isTypeHandled");
            int retVal = 1;
            if (this.state instanceof States.Inactive) {
               throw new RuntimeException(mtf_base.getInvalidStateStr(this.getName(), "" + this.state));
            }

            if (typeName != null) {
               boolean exists = this.metricCache.doesTypeExist(typeName);
               if (exists) {
                  retVal = 2;
               }

               if (this.dR()) {
                  this.dbgR("Provider " + this.getName() + " has decided whether or not type " + typeName + " is handled by itSELF - " + (retVal == 2 ? "yes" : (retVal == -1 ? "no" : "maybe")));
               }
            }

            var11 = retVal;
         }
      } finally {
         this.unsynced("isTypeHandled");
      }

      return var11;
   }

   public abstract String findTypeName(String var1) throws Exception;

   public int addWatchedValues(String name, WatchedValues watchedValues, HarvestCallback callback) throws IOException {
      int wvid;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("addWatchedValues", this, "   name=" + name + "   watchedValues=" + watchedValues.dump("", false, true, true) + "   callback=" + callback);
         }

         synchronized(this) {
            this.afterSync("addWatchedValues");
            if (watchedValues != null) {
               wvid = this.watchedValuesIndex.indexOf(watchedValues);
               if (wvid >= 0) {
                  int var14 = wvid;
                  return var14;
               }

               WatchedValuesControlInfo ci = new WatchedValuesControlInfo(name, watchedValues, callback);
               this.watchedValuesIndex.add(ci);
               wvid = this.watchedValuesIndex.size() - 1;
               ci.setWVID(wvid);
               this.metricMan.addWLID(wvid);
               this.addWatchedValues(watchedValues, callback, ci, false);
               int var7 = wvid;
               return var7;
            }

            wvid = -1;
         }
      } finally {
         this.unsynced("addWatchedValues");
      }

      return wvid;
   }

   public Collection validateWatchedValues(WatchedValues watchedValues) {
      Collection var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("validateWatchedValues", this, "   watchedValues=" + watchedValues.dump("", false, true, true));
         }

         synchronized(this) {
            this.afterSync("validateWatchedValues");
            WatchedValuesControlInfo ci = new WatchedValuesControlInfo("TEST", watchedValues, (HarvestCallback)null);
            var4 = this.addWatchedValues(watchedValues, (HarvestCallback)null, ci, true);
         }
      } finally {
         this.unsynced("validateWatchedValues");
      }

      return var4;
   }

   private Collection addWatchedValues(WatchedValues watchedValues, HarvestCallback callback, WatchedValuesControlInfo ci, boolean dryRun) {
      ValidationSet validations = dryRun ? new ValidationSet(ci.watchedValues) : null;
      this.initializePendingMetrics(watchedValues, ci, validations);
      if (this.dR()) {
         this.dbgR("After setting the watched values, the following metrics will need to be resolved for watch list " + ci.getWVID() + ": " + CollectionUtils.iteratorToString(ci.pendingMetrics.iterator()));
      }

      Collection instanceNames = this.metricCache.getAllCurrentInstanceNames();
      this.resolveAll(instanceNames, ci, validations);
      if (this.dR()) {
         this.dbgR("After doing full resolution against all registered MBeans  of new watch list " + ci.getWVID() + " the following remain unresolved: " + CollectionUtils.iteratorToString(ci.pendingMetrics.iterator()));
      }

      if (dryRun && validations != null) {
         Iterator var7 = validations.getValidations().iterator();

         while(var7.hasNext()) {
            WatchedValues.Validation v = (WatchedValues.Validation)var7.next();
            if (v.getStatus() == -1) {
               boolean removed = ci.pendingMetrics.remove(v.getMetric());
               if (removed && this.dR()) {
                  this.dbgR("Removed " + v.getMetric() + " from pending metrics");
               }
            }
         }
      }

      if (!dryRun && callback != null) {
         this.wvidsWithCallbacks.add(ci.getWVID());
      }

      return validations != null ? validations.getValidations() : null;
   }

   private void initializePendingMetrics(WatchedValues watchedValues, WatchedValuesControlInfo ci, ValidationSet validations) {
      if (ci != null) {
         ci.pendingMetrics.clear();
         Iterator valuesIt = watchedValues.getAllMetricValues().iterator();

         while(valuesIt.hasNext()) {
            ci.pendingMetrics.add(valuesIt.next());
         }

         this.doInitialChecking(ci, validations);
      }

   }

   private void resolveAll(Collection instanceNames, WatchedValuesControlInfo ci, ValidationSet validations) {
      Iterator it = instanceNames.iterator();

      while(it.hasNext()) {
         String instName = (String)it.next();
         String typeName = this.metricCache.getTypeName(instName);
         this.handlePotentialUnresolvedMetric(instName, typeName, ci, validations);
      }

   }

   public void extendWatchedValues(int wvid, WatchedValues newWV) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("extendWatchedValues", this, "   wvid'" + wvid + "   watchedValues=" + newWV.dump("", false, true, true));
         }

         synchronized(this) {
            this.afterSync("extendWatchedValues");
            this.extendWatchedValues(wvid, newWV, false);
         }
      } finally {
         this.unsynced("extendWatchedValues");
      }

   }

   public Collection validateExtendedWatchedValues(int wvid, WatchedValues newWV) {
      Collection var4;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("validateExtendedWatchedValues", this, "   wvid'" + wvid + "   watchedValues=" + newWV.dump("", false, true, true));
         }

         synchronized(this) {
            this.afterSync("validateExtendedWatchedValues");
            var4 = this.extendWatchedValues(wvid, newWV, true);
         }
      } finally {
         this.unsynced("validateExtendedWatchedValues");
      }

      return var4;
   }

   private Collection extendWatchedValues(int wvid, WatchedValues newWV, boolean dryRun) {
      if (this.dC()) {
         this.dbgC("Extending the watched value set: " + wvid + ".");
      }

      if (this.watchedValuesIndex == null) {
         throw new RuntimeException(mtf_base.getNoSuchWVID(wvid));
      } else {
         WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
         WatchedValues watchedValues = ci.watchedValues;
         ArrayList newMetrics = watchedValues.extendValues(newWV);
         Iterator it = newMetrics.iterator();

         while(it.hasNext()) {
            WatchedValues.Values newMetric = (WatchedValues.Values)it.next();
            ci.pendingMetrics.add(newMetric);
         }

         ValidationSet validations = dryRun ? new ValidationSet(ci.watchedValues) : null;
         this.doInitialChecking(ci, validations);
         Collection names = this.metricCache.getAllCurrentInstanceNames();
         this.resolveAll(names, ci, validations);
         return validations != null ? validations.getValidations() : null;
      }
   }

   private void doInitialChecking(WatchedValuesControlInfo ci, ValidationSet validations) {
      Iterator it = ci.pendingMetrics.iterator();

      while(it.hasNext()) {
         WatchedValues.Values metric = (WatchedValues.Values)it.next();
         int vote = false;
         String issue = null;
         int vote;
         if (!metric.matchesNamespace(this.namespace)) {
            vote = -1;
            issue = ADAPTER_TEXT_FORMATTER.getMetricNamespaceRejected(metric.getNamespace(), this.namespace);
         } else {
            vote = this.resolveByType(metric);
            switch (vote) {
               case -1:
                  issue = HarvesterJMXTextTextFormatter.getInstance().getTypeDefinitivelyNotHandledByHarvester(this.getName(), metric.getTypeName());
                  break;
               default:
                  try {
                     metric.validate();
                     if (metric.getInstanceName() != null && !metric.instanceIsPattern()) {
                        this.validateInstanceName(metric.getInstanceName());
                     }
                  } catch (IllegalArgumentException var8) {
                     issue = mtf_base.getInvInstNameMess(metric.getInstanceName());
                     vote = -1;
                     if (this.dC()) {
                        this.dbgC("Found validation issue: " + issue);
                     }
                  }
            }
         }

         if (this.dR()) {
            this.dbgR("doInitialChecking(): Voted " + voteToString(vote) + " for " + metric.dump("\t", true, false));
         }

         if (validations != null) {
            validations.add(metric, vote, issue, true);
         }
      }

   }

   protected abstract void validateInstanceName(String var1) throws IllegalArgumentException;

   MetricInfoManager getMetricInfoManager() {
      return this.metricMan;
   }

   public HarvesterDebugLogger getDebugLogger() {
      return this.debugSpec;
   }

   protected abstract boolean instanceNameIsValid(String var1);

   public void resolveAllMetrics(int[] wvids) {
      if (wvids != null && wvids.length > 0) {
         ArrayList wvidList = new ArrayList(wvids.length);
         int[] var3 = wvids;
         int var4 = wvids.length;

         int var5;
         int wvid;
         for(var5 = 0; var5 < var4; ++var5) {
            wvid = var3[var5];
            wvidList.add(wvid);
         }

         this.metricMan.removeSamples((String)null, (String)null, (String)null, wvidList);
         var3 = wvids;
         var4 = wvids.length;

         for(var5 = 0; var5 < var4; ++var5) {
            wvid = var3[var5];
            WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
            if (ci != null) {
               this.initializePendingMetrics(ci.watchedValues, ci, (ValidationSet)null);
            }

            Collection names = this.metricCache.getAllCurrentInstanceNames();
            this.resolveAll(names, ci, (ValidationSet)null);
         }
      }

   }

   public void resolveMetrics(int wvid, Set vidsToResolve) {
      ArrayList wvidList = new ArrayList(1);
      wvidList.add(wvid);
      WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
      if (ci != null) {
         Iterator var5 = vidsToResolve.iterator();

         while(var5.hasNext()) {
            Integer i = (Integer)var5.next();
            WatchedValues.Values v = ci.watchedValues.getMetric(i);
            this.metricMan.removeSamples(v.getAttributeName(), v.getTypeName(), v.getInstanceName(), wvidList);
            if (!ci.pendingMetrics.contains(v)) {
               ci.pendingMetrics.add(v);
            }
         }

         this.doInitialChecking(ci, (ValidationSet)null);
         this.resolveAllForVids(ci, vidsToResolve);
      }

   }

   private void resolveAllForVids(WatchedValuesControlInfo ci, Set vids) {
      Collection instanceNames = this.metricCache.getAllCurrentInstanceNames();
      Iterator it = instanceNames.iterator();

      while(it.hasNext()) {
         String instName = (String)it.next();
         String typeName = this.metricCache.getTypeName(instName);
         this.handlePotentialUnresolvedMetric(instName, typeName, ci, vids, (ValidationSet)null);
      }

   }

   void resolve(String instName, String typeName) {
      if (this.dR()) {
         this.dbgR("Attempting to resolve any unresolved metrics using new instance: " + instName + " (" + typeName + ").");
      }

      if (this.dT() || this.dC()) {
         this.beforeSync("resolve", this, (Object)null);
      }

      synchronized(this) {
         this.afterSync("resolve");
         if (this.state == BaseHarvesterImpl.States.Active.getInstance()) {
            for(int wvid = 0; wvid < this.watchedValuesIndex.size(); ++wvid) {
               WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
               if (ci != null) {
                  int unresolvedCountBefore = ci.pendingMetrics.size();
                  if (unresolvedCountBefore != 0) {
                     if (this.dR()) {
                        this.dbgR("Attempting to resolve against unresolved watch metrics  for watch set " + ci + CollectionUtils.iteratorToString(ci.pendingMetrics.iterator()));
                     }

                     this.handlePotentialUnresolvedMetric(instName, typeName, ci, (ValidationSet)null);
                     if (this.dR()) {
                        int resolvedCount = unresolvedCountBefore - ci.pendingMetrics.size();
                        if (resolvedCount > 0) {
                           if (resolvedCount == unresolvedCountBefore) {
                              this.dbgR("For WVID: " + ci + ", all unresolved metrics have been resolved!");
                           } else {
                              this.dbgR("For WVID: " + ci + ", " + resolvedCount + " items were resolved.  Remaining unresolved: " + CollectionUtils.iteratorToString(ci.pendingMetrics.iterator()));
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      this.unsynced("resolve");
   }

   private void handlePotentialUnresolvedMetric(String actualInstanceName, String actualTypeName, WatchedValuesControlInfo ci, ValidationSet validations) {
      this.handlePotentialUnresolvedMetric(actualInstanceName, actualTypeName, ci, (Set)null, validations);
   }

   private void handlePotentialUnresolvedMetric(String actualInstanceName, String actualTypeName, WatchedValuesControlInfo ci, Set vidsToResolve, ValidationSet validations) {
      if (this.dR()) {
         this.dbgR("Testing instance " + actualInstanceName + "(" + actualTypeName + ") against unresolved and wildcarded metrics for watch set: " + ci + ".");
      }

      Iterator it = ci.pendingMetrics.iterator();

      while(true) {
         WatchedValues.Values metric;
         List resolutions;
         do {
            do {
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  metric = (WatchedValues.Values)it.next();
               } while(vidsToResolve != null && !vidsToResolve.contains(metric.getVID()));

               resolutions = this.resolveSingleton(metric, actualInstanceName, this.attributeValidationEnabled);
            } while(resolutions == null);
         } while(resolutions.size() <= 0);

         Iterator var9 = resolutions.iterator();

         while(var9.hasNext()) {
            Resolution res = (Resolution)var9.next();
            if (res.error != null) {
               if (validations != null) {
                  validations.add(metric, -1, res.error, !metric.typeIsPattern());
               } else {
                  LogSupport.logUnexpectedCondition(this.getName(), res.error);
               }
            } else if (validations == null) {
               if (res.attrSpec != null) {
                  String attrName = res.attrSpec.getName();
                  AttributeSpec attrSpec = res.attrSpec;
                  if (this.dR()) {
                     this.dbgR("Adding " + attrName + " to metric manager.");
                  }

                  this.metricMan.addSample(attrSpec.getMBeanType(), actualInstanceName, attrSpec, ci.getWVID(), metric);
               } else if (this.dR()) {
                  this.dbgR("Attribute spec was null for instance {" + res.instance + "}, error: " + res.error);
               }
            } else {
               validations.add(metric, 2, (String)null, !metric.typeIsPattern());
            }
         }

         if (this.dR()) {
            this.dbgR("Instance metric  " + metric + " is now resolved!");
         }

         if (metric.getInstanceName() != null && !metric.instanceIsPattern() && metric.getTypeName() != null && !metric.typeIsPattern()) {
            it.remove();
         }
      }
   }

   protected int resolveByType(WatchedValues.Values metric) {
      int vote = false;
      String metricTypeName = metric.getTypeName();
      int vote = this.isTypeHandled(metricTypeName);
      switch (vote) {
         case -1:
            if (this.dR()) {
               this.dbgR("BaseHarvesterImpl.resolveByType(): Harvester " + this.getName() + " has voted NO for type " + metricTypeName);
            }
            break;
         case 2:
            if (this.dR()) {
               this.dbgR("BaseHarvesterImpl.resolveByType(): Harvester " + this.getName() + " has voted YES for type " + metricTypeName);
            }
      }

      return vote;
   }

   private WatchedValuesControlInfo getWatchedValuesInfo(int wvid) {
      WatchedValuesControlInfo ci = null;
      if (wvid < this.watchedValuesIndex.size()) {
         ci = this.watchedValuesIndex.get(wvid);
      }

      if (ci == null) {
         throw new RuntimeException(mtf_base.getNoSuchWVID(wvid));
      } else {
         return ci;
      }
   }

   public int deleteMetrics(int wvid, Collection slots) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("deleteMetrics", this, "   wvid=" + wvid + "   slots=" + CollectionUtils.aggregateToString(slots));
         }

         synchronized(this) {
            this.afterSync("deleteMetrics");
            this.metricMan.deleteSelectedMetricsForWVID(wvid, slots);
            int count = 0;
            WatchedValuesControlInfo ci = this.watchedValuesIndex.get(wvid);
            WatchedValues wv = ci.watchedValues;
            Collection masterSlots = wv.getAllMetricValues();
            Iterator it = masterSlots.iterator();

            label72:
            while(it.hasNext()) {
               WatchedValues.Values masterSlot = (WatchedValues.Values)it.next();
               Iterator it2 = slots.iterator();

               WatchedValues.Values slot;
               do {
                  if (!it2.hasNext()) {
                     continue label72;
                  }

                  slot = (WatchedValues.Values)it2.next();
               } while(slot != masterSlot);

               ++count;
               it.remove();
            }

            int var18 = count;
            return var18;
         }
      } finally {
         this.unsynced("deleteMetrics");
      }
   }

   public void deleteWatchedValues(WatchedValues wv) {
      if (wv.getId() > -1) {
         int[] wvids = new int[]{wv.getId()};
         this.deleteWatchedValues(wvids);
      }

   }

   public int deleteWatchedValues(int[] wvids) {
      int i;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("deleteWatchedValues", this, "   wvids=" + CollectionUtils.aggregateToString(wvids));
         }

         synchronized(this) {
            this.afterSync("deleteWatchedValues");
            int count = 0;

            for(i = 0; i < wvids.length; ++i) {
               int wvid = wvids[i];
               if (this.watchedValuesIndex.remove(wvid)) {
                  ++count;
               }

               this.wvidsWithCallbacks.remove(wvid);
            }

            this.metricMan.deleteSelectedWVIDs(wvids);
            i = count;
         }
      } finally {
         this.unsynced("deleteWatchedValues");
      }

      return i;
   }

   public int disableMetrics(int wvid, Integer[] mids) {
      int var5;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("disableMetrics", this, "   wvid=" + wvid + "   mids=" + CollectionUtils.aggregateToString(mids));
         }

         synchronized(this) {
            this.afterSync("disableMetrics");
            WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
            var5 = this.setMetricsEnabledStatus(ci, mids, false);
         }
      } finally {
         this.unsynced("disableMetrics");
      }

      return var5;
   }

   public int enableMetrics(int wvid, Integer[] mids) {
      int var5;
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("enableMetrics", this, "   wvid=" + wvid + "   mids=" + CollectionUtils.aggregateToString(mids));
         }

         synchronized(this) {
            this.afterSync("enableMetrics");
            WatchedValuesControlInfo ci = this.getWatchedValuesInfo(wvid);
            var5 = this.setMetricsEnabledStatus(ci, mids, true);
         }
      } finally {
         this.unsynced("enableMetrics");
      }

      return var5;
   }

   private int setMetricsEnabledStatus(WatchedValuesControlInfo ci, Integer[] mids, boolean status) {
      Arrays.sort(mids);
      WatchedValues watchedValues = ci.watchedValues;
      return watchedValues.setEnabledStatus(mids, status);
   }

   public void newInstance(String instName, String typeName) {
      this.resolve(instName, typeName);
   }

   public void instanceDeleted(String instName, String typeName) {
      if (this.dR()) {
         this.dbgR("Instance deleted : " + instName + " (type=" + typeName + ").");
      }

      synchronized(this) {
         int[] activeWVIDS = this.getActiveWVIDs();
         List affectedWatchedValuesList = new ArrayList(activeWVIDS.length);
         int[] var6 = activeWVIDS;
         int var7 = activeWVIDS.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            int wvid = var6[var8];
            WatchedValuesControlInfo watchedValuesControlInfo = this.watchedValuesIndex.get(wvid);
            List removedValues = ((WatchedValuesImpl)watchedValuesControlInfo.watchedValues).instanceRemoved(typeName, instName);
            if (removedValues.size() > 0) {
               affectedWatchedValuesList.add(wvid);
               if (this.dR()) {
                  this.dbgR("instanceDeleted: Removed values from " + wvid);
               }

               Iterator var12 = removedValues.iterator();

               while(var12.hasNext()) {
                  WatchedValues.Values v = (WatchedValues.Values)var12.next();
                  if (!watchedValuesControlInfo.pendingMetrics.contains(v)) {
                     if (this.dR()) {
                        this.dbgR("instanceDeleted: adding " + v.toString() + " to pending metics");
                     }

                     watchedValuesControlInfo.pendingMetrics.add(v);
                  }
               }
            }
         }

         if (affectedWatchedValuesList.size() > 0) {
            this.metricMan.removeSamples((String)null, typeName, instName, affectedWatchedValuesList);
         }

      }
   }

   protected boolean dC() {
      return this.logDbgC.isDebugEnabled();
   }

   protected boolean dR() {
      return this.logDbgR.isDebugEnabled();
   }

   protected boolean dH() {
      return this.logDbgH.isDebugEnabled();
   }

   protected boolean dT() {
      return this.logDbgT.isDebugEnabled();
   }

   protected void setUpDebugFlags(String baseName) {
      this.debugName = baseName;
      this.levNameC = this.debugName + "Control";
      this.levNameR = this.debugName + "Resolution";
      this.levNameH = this.debugName + "DataCollection";
      this.levNameT = this.debugName + "Threading";
      this.logDbgC = DebugLogger.getDebugLogger(this.levNameC);
      this.logDbgR = DebugLogger.getDebugLogger(this.levNameR);
      this.logDbgH = DebugLogger.getDebugLogger(this.levNameH);
      this.logDbgT = DebugLogger.getDebugLogger(this.levNameT);
      if (this.dC()) {
         String s = "The following debug flags are set\n";
         s = s + "  control(log): " + (this.logDbgC != null && this.logDbgC.isDebugEnabled()) + "\n";
         s = s + "  ObjectResolution(log): " + (this.logDbgR != null && this.logDbgR.isDebugEnabled()) + "\n";
         s = s + "  DataCollection(log): " + (this.logDbgH != null && this.logDbgH.isDebugEnabled()) + "\n";
         s = s + "  Threading(log): " + (this.logDbgT != null && this.logDbgT.isDebugEnabled()) + "\n";
         this.dbgC(s);
      }

      this.debugSpec = new HarvesterDebugLogger() {
         public void dbgC(Object o) {
            BaseHarvesterImpl.this.dbgC(o);
         }

         public void dbgC(Object o, Throwable t) {
            BaseHarvesterImpl.this.logDbgC.debug(o.toString(), t);
         }

         public void dbgR(Object o) {
            BaseHarvesterImpl.this.dbgR(o);
         }

         public void dbgR(Object o, Throwable t) {
            BaseHarvesterImpl.this.logDbgR.debug(o.toString(), t);
         }

         public void dbgH(Object o) {
            BaseHarvesterImpl.this.dbgH(o);
         }

         public void dbgH(Object o, Throwable t) {
            BaseHarvesterImpl.this.logDbgH.debug(o.toString(), t);
         }

         public void dbgT(Object o) {
            BaseHarvesterImpl.this.dbgT(o);
         }

         public void dbgT(Object o, Throwable t) {
            BaseHarvesterImpl.this.logDbgT.debug(o.toString(), t);
         }

         public boolean isDebugCEnabled() {
            return BaseHarvesterImpl.this.dC();
         }

         public boolean isDebugREnabled() {
            return BaseHarvesterImpl.this.dR();
         }

         public boolean isDebugHEnabled() {
            return BaseHarvesterImpl.this.dH();
         }

         public boolean isDebugTEnabled() {
            return BaseHarvesterImpl.this.dT();
         }
      };
   }

   public void dbgC(Object o) {
      if (this.dC()) {
         this.logDbgC.debug(this.msgLabel() + o);
      }
   }

   public void dbgR(Object o) {
      if (this.dR()) {
         this.logDbgR.debug(this.msgLabel() + o);
      }
   }

   public void dbgH(Object o) {
      if (this.dH()) {
         this.logDbgH.debug(this.msgLabel() + o);
      }
   }

   public void dbgT(Object o) {
      if (this.dT()) {
         this.logDbgT.debug(this.msgLabel() + o);
      }
   }

   public void beforeSync(Object o, Object syncObj, Object data) {
      if (this.dT()) {
         this.dbgT("*OT-SYNCING*   - " + Thread.currentThread().getId() + " " + o + " " + DateUtils.getNanoTimestampAsString() + " " + syncObj);
         if (data != null) {
            this.dbgT("              " + data);
         }
      } else if (this.dC()) {
         this.dbgC("Call to: " + o + (data != null ? " - " + data : ""));
      }

   }

   public void afterSync(Object o) {
      if (this.dT()) {
         this.dbgT("*OT-SYNCED*    - " + Thread.currentThread().getId() + " " + DateUtils.getNanoTimestampAsString());
      }

   }

   public void unsynced(Object o) {
      if (this.dT()) {
         this.dbgT("*OT-UNSYNCED*  - " + Thread.currentThread().getId() + " " + o + " " + DateUtils.getNanoTimestampAsString());
      }

   }

   private String msgLabel() {
      return "[" + mtf_base.getHarvesterDebugMessageLabel() + ": " + this.getName() + " - " + Thread.currentThread().getId() + "] ";
   }

   protected static String ats(Object o) {
      return CollectionUtils.aggregateToString(o);
   }

   protected static void checkHarvesterName(String name) throws RuntimeException {
      if (harvestersByName.get(name) != null) {
         throw new RuntimeException(mtf_base.getAlreadyRegisteredStr(name));
      }
   }

   protected static void initializeHarvesterInstance(BaseHarvesterImpl harv, String name, String namespace, RegistrationManager cacheMgr, MetricInfoManager mim) {
      synchronized(harv) {
         harv.setName(name);
         harv.setNamespace(namespace);
         harv.metricCache = cacheMgr;
         harv.metricMan = mim;
         harv.useCount = 1;
      }

      if (harv.dC()) {
         harv.dbgC("UseCount set to 1 for " + harv.getName());
      }

      harvestersByName.put(name, harv);
   }

   public String getTypeForInstance(String instName) {
      return this.metricCache.getTypeName(instName);
   }

   public void setRemoveAttributesWithProblems(boolean removeAttributesWithProblems) {
      this.removeAttributesWithProblems = removeAttributesWithProblems;
   }

   public final boolean removeAttributesWithProblems() {
      return this.removeAttributesWithProblems;
   }

   public void oneShotHarvest(WatchedValues metricsToHarvest) {
      try {
         if (this.dT() || this.dC()) {
            this.beforeSync("oneShotHarvest", this, "   metrics=" + metricsToHarvest.dump("", false, true, true));
         }

         synchronized(this) {
            this.afterSync("oneShotHarvest");
            if (this.dH()) {
               this.dbgH("Attempting to harvest the following: " + metricsToHarvest.dump("", false, true, true));
            }

            metricsToHarvest.setTimeStamp(System.currentTimeMillis());
            List metrics = metricsToHarvest.getAllMetricValues();
            List resolutionsByMetric = this.oneShotResolve(metrics);
            int index = 0;

            for(Iterator var6 = metrics.iterator(); var6.hasNext(); ++index) {
               WatchedValues.Values metric = (WatchedValues.Values)var6.next();
               List resolutions = (List)resolutionsByMetric.get(index);
               if (resolutions.size() > 0) {
                  Iterator var9 = resolutions.iterator();

                  while(var9.hasNext()) {
                     Resolution resolution = (Resolution)var9.next();
                     if (resolution.error == null) {
                        AttributeSpec attrSpec = resolution.attrSpec;

                        try {
                           Object rawValue = this.getBeanValue(resolution.instance, attrSpec, metricsToHarvest.isAttributeNameTrackingEnabled());
                           metric.addValue(attrSpec.getMBeanType(), resolution.instance, rawValue);
                        } catch (Exception var19) {
                           String reason = var19.getMessage();
                           metric.addError(attrSpec.getMBeanType(), resolution.instance, mtf_base.getHarvestingErrorPrefixLabel() + reason, false);
                        }
                     } else {
                        String typeName = resolution.attrSpec == null ? null : resolution.attrSpec.getMBeanType();
                        metric.addError(typeName, resolution.instance, mtf_base.getResolutionErrorPrefixLabel() + resolution.error, false);
                     }
                  }
               } else {
                  metric.addError((String)null, (String)null, mtf_base.getNoMBeansForMetricErr(), false);
               }
            }

         }
      } finally {
         this.unsynced("oneShotHarvest");
      }
   }

   private List oneShotResolve(List metricsToResolve) {
      List resolutionsByMetric = new ArrayList();
      Iterator var3 = metricsToResolve.iterator();

      label42:
      while(var3.hasNext()) {
         WatchedValues.Values metricToResolve = (WatchedValues.Values)var3.next();
         List resolutions = new ArrayList();
         resolutionsByMetric.add(resolutions);
         Collection instanceNames = this.metricCache.getAllCurrentInstanceNames();
         Iterator it = instanceNames.iterator();

         while(true) {
            List resolved;
            do {
               do {
                  if (!it.hasNext()) {
                     continue label42;
                  }

                  String candidateInstName = (String)it.next();
                  resolved = this.resolveSingleton(metricToResolve, candidateInstName, false);
               } while(resolved == null);
            } while(resolved.size() <= 0);

            Iterator var10 = resolved.iterator();

            while(var10.hasNext()) {
               Resolution res = (Resolution)var10.next();
               resolutions.add(res);
            }

            if (!metricToResolve.typeIsPattern() && !metricToResolve.instanceIsPattern()) {
               break;
            }
         }
      }

      return resolutionsByMetric;
   }

   private List resolveSingleton(WatchedValues.Values metricToResolve, String candidateInstName, boolean testItIfMatched) {
      ArrayList resolutions = new ArrayList();
      String candidateTypeName = this.metricCache.getTypeName(candidateInstName);
      if (!metricToResolve.matches(this.getNamespace(), candidateTypeName, candidateInstName)) {
         return null;
      } else {
         if (this.dR()) {
            this.dbgR("Metric instance data has been matched!");
         }

         if (this.dR()) {
            this.dbgR("In process of resolving instance " + candidateInstName + "(" + candidateTypeName + ") to " + metricToResolve + ".");
         }

         String attrName = metricToResolve.getAttributeName();
         AttributeSpec[] allAttributeSpecs = this.metricCache.getAttributeSpecs(candidateTypeName, attrName);
         AttributeSpec[] var8 = allAttributeSpecs;
         int var9 = allAttributeSpecs.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            AttributeSpec spec = var8[var10];

            try {
               Resolution res = this.testAttributeSpec(candidateInstName, candidateTypeName, attrName, spec, testItIfMatched);
               resolutions.add(res);
            } catch (Exception var14) {
               LogSupport.logUnexpectedException(this.getName(), mtf_base.getExcpResolvingMBean(candidateInstName, candidateTypeName), var14);
               if (this.dH()) {
                  var14.printStackTrace();
               }

               String issue = var14.getMessage();
               resolutions.add(new Resolution(candidateInstName, spec, issue));
            }
         }

         return resolutions;
      }
   }

   protected boolean isNonWLSModelMBean(String instance) {
      return false;
   }

   private Resolution testAttributeSpec(String candidateInstName, String candidateTypeName, String attrName, AttributeSpec attrSpec, boolean testItIfMatched) {
      String issue;
      if (attrSpec == null) {
         if (this.isNonWLSModelMBean(candidateInstName)) {
            if (this.dH()) {
               this.dbgH("Model MBean special case, attr spec is null");
            }

            AttributeSpec dummySpec = new AttributeSpec(attrName, candidateTypeName, (String)null, true, new AttributeTerm.SimpleTerm(attrName, (AttributeTerm)null), false);
            return new Resolution(candidateInstName, dummySpec, (String)null);
         } else {
            String[] terms = attrName.split("\\.", 2);
            issue = mtf_base.getTypeDoesNotContainAttrMess(candidateTypeName, terms[0]);
            if (this.dC()) {
               this.dbgC("Found validation issue: " + issue);
            }

            return new Resolution(candidateInstName, (AttributeSpec)null, issue);
         }
      } else {
         if (testItIfMatched) {
            try {
               this.getBeanValue(candidateInstName, attrSpec, false);
            } catch (Exception var8) {
               issue = mtf_base.getExcpWhilstTryingToAccessAttrMess(attrName, candidateTypeName, var8.getMessage());
               if (this.dC()) {
                  this.dbgC("Found validation issue: " + issue);
               }

               return new Resolution(candidateInstName, (AttributeSpec)null, issue);
            }
         }

         return new Resolution(candidateInstName, attrSpec, (String)null);
      }
   }

   public void setAttributeValidationEnabled(boolean validate) {
      this.attributeValidationEnabled = validate;
   }

   public boolean isAttributeValidationEnabled() {
      return this.attributeValidationEnabled;
   }

   public static String voteToString(int vote) {
      String voteString = null;
      switch (vote) {
         case -1:
            voteString = "NO";
            break;
         case 0:
            voteString = "UNRESOLVED";
            break;
         case 1:
            voteString = "MAYBE";
            break;
         case 2:
            voteString = "YES";
      }

      return voteString;
   }

   interface States {
      public static class Terminated extends Inactive {
         static Terminated unitary = new Terminated();

         public String toString() {
            return BaseHarvesterImpl.mtf_base.getStateTerminatedLabel();
         }

         static Terminated getInstance() {
            return unitary;
         }
      }

      public static class ShuttingDown extends Inactive {
         static ShuttingDown unitary = new ShuttingDown();

         public String toString() {
            return BaseHarvesterImpl.mtf_base.getStateShuttingDownLabel();
         }

         static ShuttingDown getInstance() {
            return unitary;
         }
      }

      public static class Active extends State {
         static Active unitary = new Active();

         public String toString() {
            return BaseHarvesterImpl.mtf_base.getStateActiveLabel();
         }

         static Active getInstance() {
            return unitary;
         }
      }

      public abstract static class Inactive extends State {
         public String toString() {
            return BaseHarvesterImpl.mtf_base.getStateInactiveLabel();
         }
      }

      public static class State {
      }
   }

   protected static class ValidationSet {
      private HashMap list;

      private ValidationSet(WatchedValues wv) {
         this.list = new HashMap();
         Iterator it = wv.getAllMetricValues().iterator();

         while(it.hasNext()) {
            WatchedValues.Values metric = (WatchedValues.Values)it.next();
            this.list.put(metric, new ValidationImpl(metric, 1, (String)null));
         }

      }

      private void add(WatchedValues.Values metric, int status, String issue, boolean noOk) {
         ValidationImpl v = (ValidationImpl)this.list.get(metric);
         int oldStatus = v.getStatus();
         if (status == 2) {
            v.setStatus(2);
         } else if (status == -1) {
            if (issue != null) {
               v.addIssue(issue);
            }

            if (noOk && oldStatus == 1) {
               v.setStatus(-1);
            }
         }

      }

      private Collection getValidations() {
         return this.list.values();
      }

      // $FF: synthetic method
      ValidationSet(WatchedValues x0, Object x1) {
         this(x0);
      }
   }

   static class SpecialList extends ArrayList {
      static final long serialVersionUID = 1L;

      SpecialList(int size) {
         super(size);
      }
   }

   static class WatchedValuesArrayList {
      private ArrayList delegate = new ArrayList();
      private int activeCount = 0;

      private int getActiveCount() {
         return this.activeCount;
      }

      boolean add(WatchedValuesControlInfo wvci) {
         this.delegate.add(wvci);
         ++this.activeCount;
         return true;
      }

      boolean remove(int wvid) {
         Object old = this.delegate.get(wvid);
         this.delegate.set(wvid, (Object)null);
         if (old != null) {
            --this.activeCount;
         }

         return old != null;
      }

      WatchedValuesControlInfo get(int wvid) {
         return (WatchedValuesControlInfo)this.delegate.get(wvid);
      }

      int size() {
         return this.delegate.size();
      }

      int indexOf(WatchedValues wv) {
         for(int i = 0; i < this.delegate.size(); ++i) {
            WatchedValuesControlInfo ci = (WatchedValuesControlInfo)this.delegate.get(i);
            if (ci != null && ci.watchedValues == wv) {
               return i;
            }
         }

         return -1;
      }

      Iterator iterator() {
         return this.delegate.iterator();
      }

      void extendTo(int size) {
         CollectionUtils.extendTo(this.delegate, size);
      }
   }
}
