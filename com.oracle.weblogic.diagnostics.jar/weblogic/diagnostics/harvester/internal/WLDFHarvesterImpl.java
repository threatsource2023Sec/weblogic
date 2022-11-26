package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.Harvester;
import com.bea.adaptive.harvester.WatchedValues;
import com.bea.adaptive.harvester.WatchedValuesImpl;
import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import javax.management.JMException;
import javax.management.MBeanServer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.HarvesterConstants;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;
import weblogic.diagnostics.harvester.I18NSupport;
import weblogic.diagnostics.harvester.InvalidHarvesterNamespaceException;
import weblogic.diagnostics.harvester.LogSupport;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.harvester.WLDFHarvesterUtils;
import weblogic.diagnostics.i18n.DiagnosticsHarvesterLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextHarvesterTextFormatter;
import weblogic.diagnostics.utils.SecurityHelper;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class WLDFHarvesterImpl implements WLDFHarvester, HarvesterConstants {
   static final String DOMAIN_RUNTIME_JMX_DELEGATE_NAME = "DomainRuntimeHarvester";
   static final String SERVER_RUNTIME_JMX_DELEGATE_NAME = "ServerRuntimeHarvester";
   static final String BEAN_TREE_DELEGATE_NAME = "BeanTreeHarvester";
   public static final String WLDFHARVESTER_NAME = "WLDFHarvester";
   private static final String[] localDelegates = new String[]{"BeanTreeHarvester", "ServerRuntimeHarvester"};
   private static final String[] fullDelegates = new String[]{"BeanTreeHarvester", "ServerRuntimeHarvester", "DomainRuntimeHarvester"};
   private static final ArrayList localDelegatesList;
   private static ArrayList fullDelegatesList;
   private final DebugLogger debugLogger = DebugSupport.getDebugLogger();
   private static final WLDFHarvesterImpl self;
   private static final DebugLogger DBG;
   private final String name;
   private static AuthenticatedSubject KERNEL_ID;
   private final DelegateHarvesterManager harvesterManager = DelegateHarvesterManagerImpl.createDelegateHarvesterManager();
   private final ConcurrentHashMap wvidToDelegatesMap = new ConcurrentHashMap();
   private int nextWVID;
   private final int defaultSamplePeriod = 300;
   private boolean adminServer;
   private boolean attributeValidationEnabled;
   private ComponentInvocationContextManager cicManager;

   public static WLDFHarvesterImpl getInstance() {
      SecurityHelper.checkAnyAdminRole();
      return self;
   }

   WLDFHarvesterImpl(String harvesterName) {
      this.name = harvesterName;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
   }

   public synchronized int addWatchedValues(String name, WatchedValues watchedValues, HarvestCallback callback) throws IOException, JMException {
      SecurityHelper.checkAnyAdminRole();
      int parentWVID = -1;
      Collection voteResults = this.mapWatchedValues(watchedValues);
      if (voteResults == null) {
         return -1;
      } else {
         List wvMap = null;
         Iterator hIt = this.harvesterManager.activeOnlyIterator();

         while(hIt.hasNext()) {
            Harvester harvester = (Harvester)hIt.next();
            ArrayList vidList = this.findVidsForHarvester(harvester, voteResults);
            if (vidList != null) {
               if (parentWVID < 0) {
                  parentWVID = this.computeNextWVID();
               }

               if (DBG.isDebugEnabled()) {
                  DBG.debug("Found " + vidList.size() + " metrics claimed by harvester " + harvester.getName());
                  DBG.debug("VIDS claimed: " + vidList.toString());
               }

               int childWVID = harvester.addWatchedValues(name, watchedValues, callback);
               if (wvMap == null) {
                  wvMap = new ArrayList(this.harvesterManager.getConfiguredHarvestersCount());
               }

               WatchedValuesDelegateMap delegateMap = new WatchedValuesDelegateMap(harvester, vidList, childWVID, watchedValues);
               wvMap.add(delegateMap);
            }
         }

         if (parentWVID > -1) {
            this.wvidToDelegatesMap.put(parentWVID, new WatchedValuesControl(watchedValues, callback, wvMap));
            watchedValues.setId(parentWVID);
         }

         if (voteResults.size() > 0) {
            WLDFHarvesterUtils.processValidationResults(watchedValues.getName(), this.extractValidations(voteResults));
         }

         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("addWatchedValues(): " + this.wvidToDelegatesMap.size() + " watched values are now active");
         }

         return parentWVID;
      }
   }

   public Collection validateWatchedValues(WatchedValues watchedValues) {
      Collection validationMap = this.mapWatchedValues(watchedValues);
      ArrayList validations = new ArrayList(validationMap.size());
      Iterator mapIt = validationMap.iterator();

      while(mapIt.hasNext()) {
         validations.add(((ValidationMap)mapIt.next()).vote);
      }

      return validations;
   }

   public synchronized int deleteMetrics(int wvid, Collection slots) {
      int count = 0;
      List mapList = this.getMapListForParentWVID(wvid);

      WatchedValuesDelegateMap map;
      Collection delegateSlots;
      for(Iterator var5 = mapList.iterator(); var5.hasNext(); count += map.getDelegateHarvester().deleteMetrics(wvid, delegateSlots)) {
         map = (WatchedValuesDelegateMap)var5.next();
         delegateSlots = map.findMatchingValuesSlots(slots);
      }

      return count;
   }

   public synchronized int deleteWatchedValues(int[] wvids) {
      int count = 0;
      int[] var3 = wvids;
      int var4 = wvids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int wvidFromCaller = var3[var5];
         count += this.deleteWatchedValuesInstance(wvidFromCaller);
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("deleteWatchedValues(): " + this.wvidToDelegatesMap.size() + " watched values are now active");
      }

      return count;
   }

   public int disableMetrics(int wvid, Integer[] mids) {
      int totalEnabledMetrics = 0;
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      WatchedValuesDelegateMap delegateMap;
      if (control != null) {
         for(Iterator mapIt = control.getMapList().iterator(); mapIt.hasNext(); totalEnabledMetrics += delegateMap.disableMetrics(mids)) {
            delegateMap = (WatchedValuesDelegateMap)mapIt.next();
         }
      }

      return totalEnabledMetrics;
   }

   public int enableMetrics(int wvid, Integer[] mids) {
      int totalEnabledMetrics = 0;
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      WatchedValuesDelegateMap delegateMap;
      if (control != null) {
         for(Iterator mapIt = control.getMapList().iterator(); mapIt.hasNext(); totalEnabledMetrics += delegateMap.enableMetrics(mids)) {
            delegateMap = (WatchedValuesDelegateMap)mapIt.next();
         }
      }

      return totalEnabledMetrics;
   }

   public void extendWatchedValues(int wvid, WatchedValues newWV) {
      SecurityHelper.checkAnyAdminRole();
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control == null) {
         throw new HarvesterRuntimeException(DiagnosticsTextHarvesterTextFormatter.getInstance().getWatchedValuesIdNotFoundText(wvid));
      } else {
         Collection voteResults = this.mapWatchedValues(newWV);
         if (voteResults != null) {
            Iterator hIt = this.harvesterManager.activeOnlyIterator();

            while(hIt.hasNext()) {
               Harvester delegate = (Harvester)hIt.next();
               ArrayList newDelegateVids = this.findVidsForHarvester(delegate, voteResults);
               if (newDelegateVids != null) {
                  WatchedValuesDelegateMap map = control.findDelegateMap(delegate);
                  if (map != null) {
                     map.extendWatchedValues(newWV, newDelegateVids);
                  } else {
                     ArrayList newVIDs = control.extendDelegateMap(delegate, newWV, newDelegateVids);
                     if (DBG.isDebugEnabled()) {
                        DBG.debug("extendWatchedValues: Found " + newVIDs.size() + " metrics claimed by harvester " + delegate.getName());
                        DBG.debug("extendWatchedValues: VIDS claimed: " + newVIDs.toString());
                     }
                  }
               }
            }
         }

      }
   }

   public String[][] getHarvestableAttributes(String typeName, String attrNameRegex) throws IOException {
      ArrayList maybeList = new ArrayList();
      Iterator it = this.harvesterManager.activatingIterator();

      while(it.hasNext()) {
         Harvester harvester = (Harvester)it.next();
         int typeHandled = harvester.isTypeHandled(typeName);
         if (typeHandled == 2) {
            return harvester.getHarvestableAttributes(typeName, attrNameRegex);
         }

         if (typeHandled == 1) {
            maybeList.add(harvester);
         }
      }

      String[][] attributes = (String[][])null;
      if (maybeList.size() > 0) {
         Iterator maybesIt = maybeList.iterator();

         while(maybesIt.hasNext()) {
            attributes = ((Harvester)maybesIt.next()).getHarvestableAttributes(typeName, attrNameRegex);
            if (attributes != null) {
               break;
            }
         }
      }

      return attributes;
   }

   public List getHarvestedAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      List result = null;
      WatchedValuesControl watchedValuesControl = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (watchedValuesControl != null) {
         List delegateMapList = watchedValuesControl.getMapList();
         Iterator var7 = delegateMapList.iterator();

         while(var7.hasNext()) {
            WatchedValuesDelegateMap map = (WatchedValuesDelegateMap)var7.next();
            List harvestedAttributes = map.getDelegateHarvester().getHarvestedAttributes(map.getDelegateWVID(), typeNameRegex, attrNameRegex);
            if (harvestedAttributes != null && harvestedAttributes.size() > 0) {
               result = harvestedAttributes;
               break;
            }
         }
      }

      return result;
   }

   public Set getHarvestedAttributes(String typeNameRegex, String attrNameRegex) {
      HashSet attributes = null;
      if (this.wvidToDelegatesMap.size() > 0) {
         attributes = new HashSet();
         Enumeration wvidsIt = this.wvidToDelegatesMap.keys();

         while(wvidsIt.hasMoreElements()) {
            Integer wvid = (Integer)wvidsIt.nextElement();
            List attributesForWvid = this.getHarvestedAttributes(wvid, typeNameRegex, attrNameRegex);
            if (attributesForWvid != null && attributesForWvid.size() > 0) {
               attributes.addAll(attributesForWvid);
            }
         }
      }

      return attributes;
   }

   public List getHarvestedInstances(int wvid, String typeName, String instNameRegex) {
      List result = null;
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control != null) {
         List delegateMapList = control.getMapList();
         Iterator var7 = delegateMapList.iterator();

         while(var7.hasNext()) {
            WatchedValuesDelegateMap map = (WatchedValuesDelegateMap)var7.next();
            List harvestedInstances = map.getDelegateHarvester().getHarvestedInstances(map.getDelegateWVID(), typeName, instNameRegex);
            if (harvestedInstances != null && harvestedInstances.size() > 0) {
               result = harvestedInstances;
               break;
            }
         }
      }

      return result;
   }

   public Set getHarvestedInstances(String typeName, String instNameRegex) {
      HashSet instances = null;
      if (this.wvidToDelegatesMap.size() > 0) {
         instances = new HashSet();
         Enumeration wvidsIt = this.wvidToDelegatesMap.keys();

         while(wvidsIt.hasMoreElements()) {
            Integer wvid = (Integer)wvidsIt.nextElement();
            List instancesForWvid = this.getHarvestedInstances(wvid, typeName, instNameRegex);
            if (instances.size() > 0) {
               instances.addAll(instancesForWvid);
            }
         }
      }

      return instances;
   }

   public List getHarvestedTypes(int wvid, String typeNameRegex) {
      HashSet aggregateResult = new HashSet();
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control != null) {
         Iterator mapIt = control.getMapList().iterator();

         while(mapIt.hasNext()) {
            WatchedValuesDelegateMap delegateMap = (WatchedValuesDelegateMap)mapIt.next();
            List delegateResult = delegateMap.getHarvestedTypes(typeNameRegex);
            aggregateResult.addAll(delegateResult);
         }
      }

      return new ArrayList(aggregateResult);
   }

   public List getKnownHarvestableInstances(String typeName, String instNameRegex) throws IOException {
      return this.getKnownHarvestableInstances((String)null, typeName, instNameRegex);
   }

   public List getKnownHarvestableInstances(String namespaceRegex, String typeName, String instNameRegex) throws IOException {
      Pattern nsPattern = namespaceRegex == null ? null : Pattern.compile(namespaceRegex);
      ArrayList maybeProviders = new ArrayList();
      Iterator i = this.harvesterManager.activatingIterator();

      while(i.hasNext()) {
         Harvester prov = (Harvester)i.next();
         boolean nsMatch = nsPattern == null || nsPattern.matcher(prov.getNamespace()).matches();
         if (nsMatch) {
            int isHandled = prov.isTypeHandled(typeName);
            if (isHandled == 2) {
               return prov.getKnownHarvestableInstances(typeName, instNameRegex);
            }

            if (isHandled == 1) {
               maybeProviders.add(prov);
            }
         }
      }

      List insts = null;
      Iterator pp = maybeProviders.iterator();
      if (pp.hasNext()) {
         Harvester prov = (Harvester)pp.next();
         insts = prov.getKnownHarvestableInstances(typeName, instNameRegex);
      }

      return insts;
   }

   public String[][] getKnownHarvestableTypes(String typeNameRegex) throws IOException {
      return this.getKnownHarvestableTypes((String)null, typeNameRegex);
   }

   public String[][] getKnownHarvestableTypes(String namespaceRegex, String typeNameRegex) throws IOException {
      String[][] types = (String[][])null;
      Map typesMap = this.getKnownTypesMap(namespaceRegex, typeNameRegex);
      if (typesMap.size() > 0) {
         types = new String[typesMap.size()][2];
         int i = 0;

         for(Iterator it = typesMap.values().iterator(); it.hasNext(); types[i++] = (String[])it.next()) {
         }
      }

      return types;
   }

   public String getName() {
      return this.name;
   }

   public WatchedValues.Values[] getPendingMetrics(int wvid) {
      ArrayList pendingMetrics = new ArrayList();
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control != null) {
         Iterator mapIt = control.getMapList().iterator();

         while(mapIt.hasNext()) {
            WatchedValuesDelegateMap delegateMap = (WatchedValuesDelegateMap)mapIt.next();
            List delegatePendingMetrics = delegateMap.getPendingMetrics();
            pendingMetrics.addAll(delegatePendingMetrics);
         }
      }

      return (WatchedValues.Values[])pendingMetrics.toArray(new WatchedValues.Values[0]);
   }

   public List getUnharvestableAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      ArrayList aggregateResult = new ArrayList();
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control != null) {
         Iterator mapIt = control.getMapList().iterator();

         while(mapIt.hasNext()) {
            WatchedValuesDelegateMap delegateMap = (WatchedValuesDelegateMap)mapIt.next();
            List delegateResult = delegateMap.getUnharvestableAttributes(typeNameRegex, attrNameRegex);
            aggregateResult.addAll(delegateResult);
         }
      }

      return aggregateResult;
   }

   public List getDisabledAttributes(int wvid, String typeNameRegex, String attrNameRegex) {
      HashSet aggregateResult = new HashSet();
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control != null) {
         Iterator mapIt = control.getMapList().iterator();

         while(mapIt.hasNext()) {
            WatchedValuesDelegateMap delegateMap = (WatchedValuesDelegateMap)mapIt.next();
            List delegateResult = delegateMap.getDisabledAttributes(typeNameRegex, attrNameRegex);
            aggregateResult.addAll(delegateResult);
         }
      }

      List result = new ArrayList(aggregateResult);
      return result;
   }

   public void harvest(int wvid) {
      if (wvid < 0) {
         throw new IllegalArgumentException(Integer.toString(wvid));
      } else {
         this.harvest(wvid, (Set)null);
      }
   }

   public void harvest(Map vidsPerWVID) {
      Iterator wvcIt;
      if (vidsPerWVID == null) {
         wvcIt = this.wvidToDelegatesMap.values().iterator();

         while(wvcIt.hasNext()) {
            WatchedValuesControl control = (WatchedValuesControl)wvcIt.next();
            this.harvestWatchedValuesControl(control, (Set)null);
         }
      } else {
         wvcIt = vidsPerWVID.keySet().iterator();

         while(wvcIt.hasNext()) {
            Integer requestedWvid = (Integer)wvcIt.next();
            this.harvest(requestedWvid, (Set)vidsPerWVID.get(requestedWvid));
         }
      }

   }

   public int isTypeHandled(String typeName) {
      int result = 0;
      ArrayList potentialProviders = new ArrayList();
      Harvester resolvedProvider = null;
      Iterator hit = this.harvesterManager.activatingIterator();

      while(hit.hasNext()) {
         Harvester provider = (Harvester)hit.next();

         try {
            int providerCapability = provider.isTypeHandled(typeName);
            switch (providerCapability) {
               case -1:
                  if (result == 0) {
                     result = providerCapability;
                  }
                  break;
               case 0:
               default:
                  String providerName = provider.getName();
                  LogSupport.logUnexpectedProblem("Provider " + providerName + " returned an invalid value from method isTypeHandled (" + providerCapability + "). Value must be " + 2 + "(yes), " + -1 + "(no), or " + 1 + "(maybe). " + providerName + " is not eligible to handle type " + typeName + ".");
                  break;
               case 1:
                  if (resolvedProvider == null) {
                     result = providerCapability;
                     potentialProviders.add(provider);
                  }
                  break;
               case 2:
                  result = providerCapability;
                  if (resolvedProvider != null) {
                     throw new HarvesterException.AmbiguousTypeName(I18NSupport.formatter().getAmbiguousTypeNameMessage(this.name, provider.getName(), resolvedProvider.getName()));
                  }

                  potentialProviders = new ArrayList(1);
                  potentialProviders.add(provider);
                  resolvedProvider = provider;
            }
         } catch (Exception var9) {
            LogSupport.logUnexpectedException("Exception thrown in call to harvester plugIn " + provider.getName() + " for type " + typeName + ". This type will be removed from consideration by the plug-in", var9);
         }
      }

      return result;
   }

   public void deallocate(boolean force) {
      this.deactivate();
   }

   public void deallocate() {
      this.deallocate(false);
   }

   public void prepare() {
      if (DBG.isDebugEnabled()) {
         DBG.debug("In WLDFHarvesterImpl.prepare");
      }

   }

   public void activate() {
      if (DBG.isDebugEnabled()) {
         DBG.debug("In WLDFHarvesterImpl.activate()");
      }

      try {
         this.initializeHarvesterDelegates();
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }

   public synchronized void deactivate() {
      if (DBG.isDebugEnabled()) {
         DBG.debug("In WLDFHarvesterImpl.deactivate()");
      }

      this.deleteAllWatchedValues();
      this.harvesterManager.removeAll();
   }

   public void unprepare() {
      if (DBG.isDebugEnabled()) {
         DBG.debug("In WLDFHarvester.unprepare()");
      }

   }

   public WatchedValues createWatchedValues(String name) {
      return this.createWatchedValues(name, (String)null, (String)null);
   }

   public WatchedValues createWatchedValues(String name, String partitionId, String partitionName) {
      Set instanceFilters = WLDFHarvesterUtils.getInstanceFilters(partitionName);
      WatchedValuesImpl wv = new WatchedValuesImpl(name, partitionId, partitionName, instanceFilters);
      wv.setDefaultSamplePeriod(300);
      wv.setAttributeNameTrackingEnabled(true);
      wv.setShared(true);
      wv.setId(-1);
      return wv;
   }

   public void deleteWatchedValues(WatchedValues wv) {
      if (wv.getId() > -1) {
         this.deleteWatchedValuesInstance(wv.getId());
         wv.setId(-1);
      }

   }

   public boolean isAttributeValidationEnabled() {
      return this.attributeValidationEnabled;
   }

   public void setAttributeValidationEnabled(boolean validate) {
      this.attributeValidationEnabled = validate;
      Iterator i = this.harvesterManager.iterator();

      while(i.hasNext()) {
         ((DelegateHarvesterControl)i.next()).setAttributeValidationEnabled(this.attributeValidationEnabled);
      }

   }

   public String getTypeForInstance(String instName) {
      String type = null;
      Iterator i = this.harvesterManager.activatingIterator();

      while(i.hasNext()) {
         type = ((Harvester)i.next()).getTypeForInstance(instName);
         if (type != null) {
            break;
         }
      }

      return type;
   }

   public void validateNamespace(String namespace) throws InvalidHarvesterNamespaceException {
      boolean found = false;
      if (namespace != null) {
         String[] var3 = this.getSupportedNamespaces();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String ns = var3[var5];
            if (ns.equals(namespace)) {
               found = true;
               break;
            }
         }

         if (!found) {
            throw new InvalidHarvesterNamespaceException(DiagnosticsTextHarvesterTextFormatter.getInstance().getInvalidHarvesterNamespaceText(namespace));
         }

         if (namespace.equals("DomainRuntime") && !this.adminServer) {
            throw new InvalidHarvesterNamespaceException(DiagnosticsTextHarvesterTextFormatter.getInstance().getDomainRuntimeNamespaceWarningText(namespace));
         }
      }

   }

   public String[] getSupportedNamespaces() {
      HashSet nsSet = new HashSet(this.harvesterManager.getConfiguredHarvestersCount());
      Iterator it = this.harvesterManager.iterator();

      while(it.hasNext()) {
         DelegateHarvesterControl h = (DelegateHarvesterControl)it.next();
         nsSet.add(h.getNamespace());
      }

      return (String[])nsSet.toArray(new String[nsSet.size()]);
   }

   public String getNamespace() {
      return "weblogic";
   }

   public String getDefaultNamespace() {
      String defaultns = "ServerRuntime";
      Iterator it = this.harvesterManager.iterator();

      while(it.hasNext()) {
         DelegateHarvesterControl h = (DelegateHarvesterControl)it.next();
         if (h.isDefaultDelegate()) {
            defaultns = h.getNamespace();
         }
      }

      return defaultns;
   }

   public void resolveAllMetrics(int[] wvids) {
      int[] var2 = wvids;
      int var3 = wvids.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int wvid = var2[var4];
         this.resolveMetrics(wvid, (Set)null);
      }

   }

   public void resolveMetrics(int wvid, Set vidsToResolve) {
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control == null) {
         throw new HarvesterRuntimeException(DiagnosticsTextHarvesterTextFormatter.getInstance().getWatchedValuesIdNotFoundText(wvid));
      } else {
         control.resolveMetrics(vidsToResolve);
      }
   }

   public boolean removeAttributesWithProblems() {
      return true;
   }

   public void setRemoveAttributesWithProblems(boolean removeAttributes) {
   }

   public void oneShotHarvest(WatchedValues metricsToHarvest) {
      Iterator iterator = this.harvesterManager.activeOnlyIterator();

      while(iterator.hasNext()) {
         ((Harvester)iterator.next()).oneShotHarvest(metricsToHarvest);
      }

   }

   private void initializeHarvesterDelegates() throws IOException, JMException {
      this.harvesterManager.addDelegateHarvester(new BeanTreeHarvesterControlImpl("BeanTreeHarvester", "ServerRuntime", DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST, true));
      MBeanCategorizer.Impl categorizer = new MBeanCategorizer.Impl(new MBeanCategorizer.Plugin[]{new WLSRuntimeCategorizerPlugin(), new NonWLSRuntimeCategorizerPlugin()});
      MBeanServer runtimeMBeanServer = ManagementService.getRuntimeMBeanServer(KERNEL_ID);
      if (runtimeMBeanServer != null) {
         this.addHarvesterForMBeanServer(categorizer, "ServerRuntimeHarvester", "ServerRuntime", runtimeMBeanServer, DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST);
      } else {
         DiagnosticsHarvesterLogger.logServerRuntimeMBeanServerNotAvailable();
      }

      MBeanServer domainRuntimeMBeanServer = null;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.adminServer = runtimeAccess.isAdminServer();
      if (this.adminServer) {
         domainRuntimeMBeanServer = ManagementService.getDomainRuntimeMBeanServer(KERNEL_ID);
         if (domainRuntimeMBeanServer == null) {
            DiagnosticsLogger.logDomainRuntimeHarvesterUnavailable();
         } else {
            MBeanCategorizer.Impl domainRuntimeCategorizer = new MBeanCategorizer.Impl(new MBeanCategorizer.Plugin[]{new WLSRuntimeCategorizerPlugin(), new NonWLSRuntimeCategorizerPlugin()});
            this.addHarvesterForMBeanServer(domainRuntimeCategorizer, "DomainRuntimeHarvester", "DomainRuntime", domainRuntimeMBeanServer, DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST);
         }
      }

   }

   private int computeNextWVID() {
      return this.nextWVID++;
   }

   private ArrayList findVidsForHarvester(Harvester harvester, Collection voteResults) {
      ArrayList vidList = null;
      Iterator vmapIt = voteResults.iterator();

      while(vmapIt.hasNext()) {
         ValidationMap map = (ValidationMap)vmapIt.next();
         if (map.harvester == harvester) {
            if (vidList == null) {
               vidList = new ArrayList();
            }

            vidList.add(map.vote.getMetric().getVID());
         }
      }

      return vidList;
   }

   private Map getKnownTypesMap(String namespaceRegex, String typeNameRegex) throws IOException {
      Pattern nsPattern = namespaceRegex == null ? null : Pattern.compile(namespaceRegex);
      HashMap typesMap = new HashMap();
      Iterator i = this.harvesterManager.activatingIterator(DelegateHarvesterControl.ActivationPolicy.ON_METADATA_REQUEST);

      while(true) {
         Harvester h;
         do {
            if (!i.hasNext()) {
               return typesMap;
            }

            h = (Harvester)i.next();
         } while(nsPattern != null && !nsPattern.matcher(h.getNamespace()).matches());

         String[][] typesForH = h.getKnownHarvestableTypes(typeNameRegex);

         for(int outer = 0; outer < typesForH.length; ++outer) {
            typesMap.put(typesForH[outer][0], typesForH[outer]);
         }
      }
   }

   private Collection extractValidations(Collection voteResults) {
      ArrayList validationSet = null;
      if (voteResults != null) {
         validationSet = new ArrayList(voteResults.size());
         Iterator var3 = voteResults.iterator();

         while(var3.hasNext()) {
            ValidationMap vmap = (ValidationMap)var3.next();
            validationSet.add(vmap.vote);
         }
      }

      return validationSet;
   }

   private List getMapListForParentWVID(int wvid) throws RuntimeException {
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control == null) {
         throw new RuntimeException("Unable to map watched values identifier: " + wvid);
      } else {
         return control.getMapList();
      }
   }

   private void addHarvesterForMBeanServer(MBeanCategorizer.Impl categorizer, String harvesterName, String namespace, MBeanServer mbeanServer, DelegateHarvesterControl.ActivationPolicy policy) throws IOException, JMException {
      this.harvesterManager.addDelegateHarvester(new JMXHarvesterControlImpl(new JMXHarvesterConfig(harvesterName, namespace, categorizer, true, mbeanServer), policy));
   }

   private Collection mapWatchedValues(WatchedValues watchedValues) {
      boolean useDomainRuntime = false;
      int numValues = 0;

      for(Iterator i = watchedValues.getAllMetricValues().iterator(); i.hasNext(); ++numValues) {
         WatchedValues.Values values = (WatchedValues.Values)i.next();
         String namespace = values.getNamespace();
         if (namespace != null && namespace.equals("DomainRuntime")) {
            useDomainRuntime = true;
         }
      }

      HashMap finalResultMap = new HashMap(numValues);
      if (numValues > 0) {
         int valuesUnresolved = numValues;
         List useDelegates = this.buildDelegateHarvestersList(watchedValues.isPartitionScoped(), useDomainRuntime);
         if (DBG.isDebugEnabled()) {
            DBG.debug("Mapping watched values " + watchedValues.getName() + " using the following delegate harvesters: " + useDelegates.toString());
         }

         Iterator it = this.harvesterManager.activatingIterator(useDelegates, DelegateHarvesterControl.ActivationPolicy.EXPLICIT);

         while(true) {
            while(true) {
               Harvester h;
               Collection childResult;
               do {
                  if (!it.hasNext() || valuesUnresolved <= 0) {
                     return finalResultMap.values();
                  }

                  h = (Harvester)it.next();
                  if (DBG.isDebugEnabled()) {
                     DBG.debug("Harvester " + h.getName() + " now bidding on metrics, metrics not definitively claimed: " + valuesUnresolved);
                  }

                  childResult = h.validateWatchedValues(watchedValues);
               } while(childResult == null);

               if (childResult.size() != numValues) {
                  throw new RuntimeException("Child validation result length did number of metrics in WatchedValues!");
               }

               Iterator newIt;
               WatchedValues.Validation newVote;
               if (finalResultMap.size() == 0) {
                  newIt = childResult.iterator();

                  while(newIt.hasNext()) {
                     newVote = (WatchedValues.Validation)newIt.next();
                     if (DBG.isDebugEnabled()) {
                        DBG.debug("Harvester " + h.getName() + " has bid + " + voteToString(newVote.getStatus()) + " for metric " + newVote.getMetric().toString());
                     }

                     if (newVote.getStatus() == 2) {
                        --valuesUnresolved;
                     } else if (DBG.isDebugEnabled()) {
                        DBG.debug("Did not fully resolve metric " + newVote.getMetric());
                     }

                     finalResultMap.put(newVote.getMetric().getVID(), new ValidationMap(h, newVote));
                     if (DBG.isDebugEnabled()) {
                        this.debugVoteResult(h, newVote);
                     }
                  }
               } else {
                  newIt = childResult.iterator();

                  while(newIt.hasNext()) {
                     newVote = (WatchedValues.Validation)newIt.next();
                     if (DBG.isDebugEnabled()) {
                        this.debugVoteResult(h, newVote);
                     }

                     int vid = newVote.getMetric().getVID();
                     ValidationMap vMap = (ValidationMap)finalResultMap.get(vid);
                     WatchedValues.Validation oldVote = vMap.vote;
                     if (newVote.getStatus() > oldVote.getStatus()) {
                        if (newVote.getStatus() == 2) {
                           --valuesUnresolved;
                        }

                        if (DBG.isDebugEnabled()) {
                           DBG.debug("Harvester " + h.getName() + " has voted " + voteToString(newVote.getStatus()) + " outbidding harvester " + vMap.harvester.getName() + " for metric " + vMap.vote.getMetric().toString());
                        }

                        finalResultMap.put(vid, new ValidationMap(h, newVote));
                     }
                  }
               }
            }
         }
      } else if (DBG.isDebugEnabled()) {
         DBG.debug("mapWatchedValues(): No values in watched values list");
      }

      return finalResultMap.values();
   }

   private List buildDelegateHarvestersList(boolean isPartitionScoped, boolean useDomainRuntime) {
      ArrayList useDelegates = new ArrayList();
      if (!isPartitionScoped) {
         useDelegates.add("BeanTreeHarvester");
      }

      useDelegates.add("ServerRuntimeHarvester");
      if (useDomainRuntime) {
         useDelegates.add("DomainRuntimeHarvester");
      }

      return useDelegates;
   }

   private static String voteToString(int status) {
      switch (status) {
         case -1:
            return "NO";
         case 0:
         default:
            return "UNRESOLVED";
         case 1:
            return "MAYBE";
         case 2:
            return "YES";
      }
   }

   private void debugVoteResult(Harvester h, WatchedValues.Validation v) {
      DBG.debug("Harvester " + h.getName() + " has bid " + voteToString(v.getStatus()) + " for metric " + v.getMetric().toString() + ", issues: " + (v.getIssues() != null ? v.getIssues().toString() : ""));
   }

   private int deleteWatchedValuesInstance(int wvid) throws RuntimeException {
      int count = 0;
      if (this.wvidToDelegatesMap.containsKey(new Integer(wvid))) {
         List mapList = this.getMapListForParentWVID(wvid);
         Iterator var4 = mapList.iterator();

         while(var4.hasNext()) {
            WatchedValuesDelegateMap map = (WatchedValuesDelegateMap)var4.next();
            if (map.getDelegateHarvester().deleteWatchedValues(new int[]{map.getDelegateWVID()}) > 0) {
               ++count;
            }
         }

         mapList.clear();
         this.wvidToDelegatesMap.remove(wvid);
      } else if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("WLDFHarvester does not contain a watched values set corresponding to the id " + wvid);
      }

      return count;
   }

   private void deleteAllWatchedValues() {
      Set wvidSet = this.wvidToDelegatesMap.keySet();
      Iterator var2 = wvidSet.iterator();

      while(var2.hasNext()) {
         Integer wvid = (Integer)var2.next();
         this.deleteWatchedValuesInstance(wvid);
      }

      this.wvidToDelegatesMap.clear();
   }

   private void harvest(int wvid, Set requestedVIDs) {
      WatchedValuesControl control = (WatchedValuesControl)this.wvidToDelegatesMap.get(wvid);
      if (control == null) {
         throw new IllegalArgumentException(Integer.toString(wvid));
      } else {
         this.harvestWatchedValuesControl(control, requestedVIDs);
      }
   }

   private void harvestWatchedValuesControl(final WatchedValuesControl control, final Set requestedVIDs) {
      final WatchedValues watchedValues = control.getWatchedValues();
      if (watchedValues.isPartitionScoped()) {
         ComponentInvocationContext cic = this.cicManager.createComponentInvocationContext(watchedValues.getPartitionName());

         try {
            ComponentInvocationContextManager.runAs(KERNEL_ID, cic, new Runnable() {
               public void run() {
                  WLDFHarvesterImpl.this.harvestFromDelegates(control, requestedVIDs, watchedValues);
               }
            });
         } catch (ExecutionException var6) {
            throw new HarvesterRuntimeException(var6);
         }
      } else {
         this.harvestFromDelegates(control, requestedVIDs, watchedValues);
      }

   }

   private void harvestFromDelegates(WatchedValuesControl control, Set requestedVIDs, WatchedValues watchedValues) {
      watchedValues.setTimeStamp(System.nanoTime());
      List delegateList = control.getMapList();
      Iterator var5 = delegateList.iterator();

      while(var5.hasNext()) {
         WatchedValuesDelegateMap map = (WatchedValuesDelegateMap)var5.next();
         map.harvest(requestedVIDs);
      }

   }

   DelegateHarvesterManager getHarvesterManager() {
      return this.harvesterManager;
   }

   static {
      localDelegatesList = new ArrayList(Arrays.asList(localDelegates));
      fullDelegatesList = new ArrayList(Arrays.asList(fullDelegates));
      self = new WLDFHarvesterImpl("WLDFHarvester");
      DBG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvester");
   }

   private static class ValidationMap {
      WatchedValues.Validation vote;
      Harvester harvester;

      public ValidationMap(Harvester harvester, WatchedValues.Validation vote) {
         this.harvester = harvester;
         this.vote = vote;
      }
   }
}
