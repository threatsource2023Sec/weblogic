package weblogic.server;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.LifecycleManagerEndPointMBean;

public class PartitionLCMHelper {
   private static DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugLifecycleManager");
   private static final String DEBUG_CLASS = "PartitionLCMHelper ";

   public static void registerPartition(String partitionName, String partitionId) {
      if (LCMHelper.isOOBEnabled()) {
         LifecycleManagerEndPointMBean lcmEndPoint = LCMHelper.getLCMEndPoint();
         if (lcmEndPoint != null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("PartitionLCMHelper registerPartition : " + partitionName + " " + partitionId);
            }

            URL url = null;

            try {
               url = getRuntimePartitionURL(lcmEndPoint);
               JSONObject details = new JSONObject();
               details.put("name", partitionName);
               details.put("id", partitionId);
               LCMHelper.asyncDoPost(url, details, "wls", lcmEndPoint);
            } catch (UnsupportedEncodingException | JSONException | MalformedURLException var5) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("PartitionLCMHelper Error in PartitionLCMHelper.registerPartition ", var5);
               }
            }

         }
      }
   }

   public static void deletePartition(String partitionName) {
      if (LCMHelper.isOOBEnabled()) {
         LifecycleManagerEndPointMBean lcmEndPoint = LCMHelper.getLCMEndPoint();
         if (lcmEndPoint != null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("PartitionLCMHelper deletePartition : " + partitionName);
            }

            URL url = null;

            try {
               url = getRuntimePartitionURL(partitionName, lcmEndPoint);
            } catch (UnsupportedEncodingException | MalformedURLException var4) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("PartitionLCMHelper PartitionLCMHelper.deletePartition ", var4);
               }

               return;
            }

            LCMHelper.doDelete(url);
         }
      }
   }

   public static void updatePartition(String partitionName) throws Exception {
      if (LCMHelper.isOOBEnabled()) {
         updatePartition(partitionName, (String)null, (HashMap)null);
      }
   }

   public static void updatePartition(String partitionName, String operation) throws Exception {
      if (LCMHelper.isOOBEnabled()) {
         updatePartition(partitionName, operation, (HashMap)null);
      }
   }

   public static void updatePartition(String partitionName, String operation, HashMap propMap) throws Exception {
      if (LCMHelper.isOOBEnabled()) {
         sendUpdateNotification(partitionName, operation, propMap);
      }
   }

   public static void startPartition(String partitionName, HashMap propMap) throws Exception {
      sendUpdateNotification(partitionName, "startPartition", propMap);
   }

   private static void sendUpdateNotification(String partitionName, String operation, HashMap propMap) throws Exception {
      LifecycleManagerEndPointMBean lcmEndPoint = LCMHelper.getLCMEndPoint();
      if (lcmEndPoint != null) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("PartitionLCMHelper updatePartition : " + partitionName + " operation = " + operation);
         }

         URL url = null;
         if (partitionName.equals("DOMAIN")) {
            partitionName = LCMHelper.getLCMEndPoint().getRuntimeName() + "-" + "DOMAIN";
         }

         try {
            url = getRuntimePartitionURL(partitionName, lcmEndPoint);
         } catch (MalformedURLException var11) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("PartitionLCMHelper PartitionLCMHelper.updatePartition", var11);
            }

            return;
         }

         JSONObject details = new JSONObject();
         JSONArray propArray = new JSONArray();
         if (propMap != null) {
            Set propSet = propMap.entrySet();
            Iterator var8 = propSet.iterator();

            while(var8.hasNext()) {
               Map.Entry entry = (Map.Entry)var8.next();
               JSONObject propObject = new JSONObject();
               propObject.put("name", entry.getKey());
               propObject.put("value", entry.getValue());
               propArray.put(propObject);
            }
         }

         details.put("properties", propArray);
         LCMHelper.asyncDoPost(url, details, "wls", operation, lcmEndPoint);
      }
   }

   private static URL getRuntimePartitionURL(LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException, UnsupportedEncodingException {
      return new URL(String.format("%s/runtimes/%s/partitions", LCMHelper.getLCMEndPointUrl(lcmEndPoint), URLEncoder.encode(lcmEndPoint.getRuntimeName(), "UTF-8")));
   }

   private static URL getRuntimePartitionURL(String partitionName, LifecycleManagerEndPointMBean lcmEndPoint) throws MalformedURLException, UnsupportedEncodingException {
      return new URL(String.format("%s/runtimes/%s/partitions/%s", LCMHelper.getLCMEndPointUrl(lcmEndPoint), URLEncoder.encode(lcmEndPoint.getRuntimeName(), "UTF-8"), URLEncoder.encode(partitionName, "UTF-8")));
   }
}
