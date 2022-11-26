package weblogic.diagnostics.harvester;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.WatchedValues;
import com.bea.adaptive.harvester.WatchedValues.ContextItem.AttributeTermType;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.management.JMException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.internal.AttributeNormalizerUtil;
import weblogic.diagnostics.harvester.internal.BeanTreeHarvesterImpl;
import weblogic.diagnostics.harvester.internal.TreeBeanHarvestableDataProviderHelper;
import weblogic.diagnostics.harvester.internal.Validators;
import weblogic.diagnostics.i18n.DiagnosticsHarvesterLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLDFHarvesterUtils {
   static final String PARTITION_INTERNAL_KEY = "_partitionName_=";
   static final String PARTITION_KEY = "Partition=";
   static final String PARTITION_RUNTIME_KEY = "PartitionRuntime=";
   private static final String HARVESTER_UTILS_GET_VALUE = "HarvesterUtils_getValue()";
   private static WLDFHarvester harvesterInstance;
   private static final DebugLogger DBG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private WLDFHarvesterUtils() {
   }

   private static WLDFHarvester getHarvesterInstance() {
      Class var0 = WLDFHarvesterUtils.class;
      synchronized(WLDFHarvesterUtils.class) {
         if (harvesterInstance == null) {
            harvesterInstance = WLDFHarvesterManager.getInstance().getHarvesterSingleton();
         }
      }

      return harvesterInstance;
   }

   public static Object getValue(String namespace, String typeSpec, String instanceSpec, String attributeSpec) throws JMException, InvalidHarvesterInstanceNameException {
      WLDFHarvester harvester = getHarvesterInstance();
      int vid = 1;
      WatchedValues wv = harvester.createWatchedValues("HarvesterUtils_getValue()");
      InstanceNameNormalizer translator = new InstanceNameNormalizer(instanceSpec);
      wv.addMetric(namespace, typeSpec, translator.translateHarvesterSpec(), attributeSpec, false, translator.isPattern(), false, true, vid);
      int wvid = true;

      int wvid;
      try {
         wvid = harvester.addWatchedValues(wv.getName(), wv, (HarvestCallback)null);
      } catch (IOException var17) {
         throw new HarvesterRuntimeException(var17);
      }

      HashMap harvestMap = new HashMap(1);
      HashSet hashSet = new HashSet(1);
      hashSet.add(Integer.valueOf(vid));
      harvestMap.put(wvid, hashSet);
      harvester.harvest(harvestMap);
      Object retValue = null;
      WatchedValues.Values metric = wv.getMetric(vid);
      List rawValues = metric.getValues().getRawValues();
      if (rawValues.size() == 1) {
         retValue = ((WatchedValues.Values.RawValueData)rawValues.get(0)).getValue();
      } else {
         ArrayList returnSet = new ArrayList();
         Iterator var15 = rawValues.iterator();

         while(var15.hasNext()) {
            WatchedValues.Values.RawValueData rv = (WatchedValues.Values.RawValueData)var15.next();
            returnSet.add(rv.getValue());
         }

         retValue = returnSet;
      }

      harvester.deleteWatchedValues(wv);
      return retValue;
   }

   public static ArrayList validateWatchedValues(Harvester harvester, WatchedValues wv) {
      Collection validationSet = harvester.validateWatchedValues(wv);
      ArrayList invalidInstances = new ArrayList();
      processValidationResults(wv.getName(), validationSet, invalidInstances);
      return invalidInstances;
   }

   public static void processValidationResults(String wvName, Collection validationSet) {
      processValidationResults(wvName, validationSet, (ArrayList)null);
   }

   public static void processValidationResults(String wvName, Collection validationSet, ArrayList invalidInstances) {
      boolean errsOccurred = false;
      String errs = "";
      int ec = 0;
      Iterator it = validationSet.iterator();

      while(true) {
         Set issues;
         do {
            WatchedValues.Validation v;
            do {
               if (!it.hasNext()) {
                  if (errsOccurred) {
                     errs = DiagnosticsTextTextFormatter.getInstance().getErrorsOccurredValidatingWatchedValues(errs, ec);
                     DiagnosticsHarvesterLogger.logValidationErrors(wvName, errs);
                  }

                  return;
               }

               v = (WatchedValues.Validation)it.next();
            } while(v.getStatus() != -1);

            if (invalidInstances != null) {
               invalidInstances.add(v.getMetric().getInstanceName());
            }

            issues = v.getIssues();
         } while(issues.size() <= 0);

         if (!errsOccurred) {
            errsOccurred = true;
         }

         for(Iterator issuesIt = issues.iterator(); issuesIt.hasNext(); ++ec) {
            String errmsg = (String)issuesIt.next();
            errs = errs + errmsg;
         }
      }
   }

   public static ArrayList validateWatchedValues(WatchedValues wv) {
      return validateWatchedValues(getHarvesterInstance(), wv);
   }

   public static void validateNamespace(String namespace) throws InvalidHarvesterNamespaceException {
      Validators.validateNamespace(namespace);
   }

   public static String normalizeInstanceName(String instanceName) throws InvalidHarvesterInstanceNameException {
      InstanceNameNormalizer normalizer = new InstanceNameNormalizer(instanceName);
      return normalizer.translateHarvesterSpec();
   }

   public static String normalizeAttributeSpecification(String typeName, String attributeSpec) {
      return AttributeNormalizerUtil.getNormalizedAttributeName(typeName, attributeSpec);
   }

   public static String[] normalizeAttributeSpecs(String typeName, String[] attributes) {
      if (attributes == null) {
         return null;
      } else {
         String[] normalizedAttributes = new String[attributes.length];

         for(int i = 0; i < attributes.length; ++i) {
            normalizedAttributes[i] = normalizeAttributeSpecification(typeName, attributes[i]);
         }

         return normalizedAttributes;
      }
   }

   public static String normalizeAttributeForInstance(String instanceName, String attributeSpec) {
      String type = getTypeForObjectName(instanceName);
      return type != null ? normalizeAttributeSpecification(type, attributeSpec) : attributeSpec;
   }

   public static Set getInstanceFilters(String partitionName) {
      Set result = new HashSet();
      if (partitionName != null && !partitionName.isEmpty()) {
         result.add(new String[]{"PartitionRuntime=", partitionName});
         result.add(new String[]{"Partition=", partitionName});
         result.add(new String[]{"_partitionName_=", partitionName});
      }

      return result;
   }

   public static String getTypeForInstance(String instanceName) {
      BeanTreeHarvesterImpl bth = BeanTreeHarvesterImpl.getInstance();
      String typeForInstance = bth.getTypeForInstance(instanceName);
      if (typeForInstance == null) {
         String wlsType = getTypeForObjectName(instanceName);
         if (wlsType != null && bth.isTypeHandled(wlsType) == 2) {
            typeForInstance = wlsType;
         }
      }

      return typeForInstance;
   }

   static String getTypeForObjectName(String objectName) {
      String retType = null;

      try {
         if (objectName != null) {
            ObjectName on = new ObjectName(objectName);
            if (on.getDomain().equals("com.bea")) {
               String type = on.getKeyProperty("Type");
               if (type != null && type.indexOf(42) < 0 && type.indexOf(63) < 0) {
                  retType = "weblogic.management.runtime." + type + "MBean";
               }
            }
         }
      } catch (Exception var4) {
      }

      return retType;
   }

   public static String buildDataContextString(List dataContext) {
      StringBuilder buffer = new StringBuilder();
      Iterator var2 = dataContext.iterator();

      while(var2.hasNext()) {
         WatchedValues.ContextItem ctxItem = (WatchedValues.ContextItem)var2.next();
         WatchedValues.ContextItem.AttributeTermType termType = ctxItem.getAttributeTermType();
         if (termType == AttributeTermType.ARRAY_OR_LIST) {
            buffer.append('[');
         } else if (termType == AttributeTermType.MAP) {
            buffer.append('(');
         } else if (termType == AttributeTermType.SIMPLE) {
            buffer.append('.');
         }

         buffer.append(ctxItem.getContext().toString());
         if (termType == AttributeTermType.ARRAY_OR_LIST) {
            buffer.append(']');
         } else if (termType == AttributeTermType.MAP) {
            buffer.append(')');
         }
      }

      int len = buffer.length();
      if (len > 0) {
         char first = buffer.charAt(0);
         if (first == '.') {
            buffer.deleteCharAt(0);
         }
      }

      return buffer.toString();
   }

   public static Object[] getLeafValues(Object[] data) {
      List result = new ArrayList();
      if (data != null) {
         addItems(result, data);
      }

      return result.toArray();
   }

   private static void addItems(List result, Object[] items) {
      Object[] var2 = items;
      int var3 = items.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object item = var2[var4];
         if (item != null) {
            if (item instanceof WatchedValues.AttributeTrackedDataItem) {
               item = ((WatchedValues.AttributeTrackedDataItem)item).getData();
               if (item == null) {
                  continue;
               }
            }

            Class itemClass = item.getClass();
            if (itemClass.isArray()) {
               addItems(result, (Object[])((Object[])item));
            } else {
               result.add(item);
            }
         }
      }

   }

   public static boolean isHarvesterValidationAvailable() {
      if (!TreeBeanHarvestableDataProviderHelper.isAvailable()) {
         return false;
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (runtimeAccess == null) {
            if (DBG.isDebugEnabled()) {
               DBG.debug("UNABLE to validate, RuntimeAccess not available");
            }

            return false;
         } else {
            if (DBG.isDebugEnabled()) {
               DBG.debug("ABLE to validate, RuntimeAccess available");
            }

            return true;
         }
      }
   }
}
