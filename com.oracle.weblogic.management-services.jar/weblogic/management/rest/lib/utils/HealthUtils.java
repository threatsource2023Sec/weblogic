package weblogic.management.rest.lib.utils;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.JsonUtil;
import weblogic.health.HealthState;
import weblogic.health.Symptom;

public class HealthUtils {
   public static JSONObject getHealth(HealthState health) {
      if (health != null) {
         JSONObject item = new JSONObject();
         JsonUtil.put(item, "state", getState(health));
         String reasons = getReasonCodeSummary(health);
         if (reasons != null && reasons.length() > 0) {
            JsonUtil.put(item, "reasons", reasons);
         }

         return item;
      } else {
         return null;
      }
   }

   public static JSONObject getDetailedHealth(HealthState health) throws Exception {
      if (health == null) {
         return null;
      } else {
         JSONObject item = new JSONObject();
         JsonUtil.put(item, "state", getState(health));
         JsonUtil.put(item, "subsystemName", health.getSubsystemName());
         JsonUtil.put(item, "partitionName", health.getPartitionName());
         Symptom[] symptoms = health.getSymptoms();
         JSONArray a = new JSONArray();
         item.put("symptoms", a);

         for(int i = 0; symptoms != null && i < symptoms.length; ++i) {
            Symptom symptom = symptoms[i];
            JSONObject s = new JSONObject();
            a.put(s);
            JsonUtil.put(s, "type", symptom.getType().toString());
            JsonUtil.put(s, "severity", symptom.getSeverity().toString());
            JsonUtil.put(s, "instanceId", symptom.getInstanceId());
            JsonUtil.put(s, "info", symptom.getInfo());
         }

         return item;
      }
   }

   private static String getState(HealthState health) {
      return health != null ? getState(HealthState.mapToString(health.getState())) : null;
   }

   public static String getState(String health) {
      if (health == null) {
         return null;
      } else if ("HEALTH_OK".equals(health)) {
         return "ok";
      } else if ("HEALTH_WARN".equals(health)) {
         return "warn";
      } else if ("HEALTH_CRITICAL".equals(health)) {
         return "critical";
      } else if ("HEALTH_FAILED".equals(health)) {
         return "failed";
      } else if ("HEALTH_OVERLOADED".equals(health)) {
         return "overloaded";
      } else if ("UNKNOWN".equals(health)) {
         return "unknown";
      } else {
         throw new IllegalArgumentException("Unknown Health value: " + health);
      }
   }

   public static HealthState integrateHealthState(HealthState previousHealthState, HealthState newHealthState) {
      int[] severityMap = new int[]{0, 1, 2, 4, 3};
      if (previousHealthState == null) {
         return newHealthState;
      } else {
         int previousHealthSeverity = severityMap[previousHealthState.getState()];
         int newHealthStateSeverity = severityMap[newHealthState.getState()];
         return newHealthStateSeverity > previousHealthSeverity ? newHealthState : previousHealthState;
      }
   }

   public static JSONObject integrateHealth(JSONObject previousHealth, JSONObject newHealth) {
      if (previousHealth == null) {
         return newHealth;
      } else {
         return getSeverity(newHealth) > getSeverity(previousHealth) ? newHealth : previousHealth;
      }
   }

   private static int getSeverity(JSONObject health) {
      try {
         String state = health.getString("state");
         if ("ok".equals(state)) {
            return 0;
         }

         if ("warn".equals(state)) {
            return 1;
         }

         if ("critical".equals(state)) {
            return 2;
         }

         if ("overloaded".equals(state)) {
            return 3;
         }

         if ("failed".equals(state)) {
            return 4;
         }
      } catch (Exception var2) {
      }

      return 5;
   }

   private static String getReasonCodeSummary(HealthState health) {
      if (health != null) {
         String[] reasons = health.getReasonCode();
         if (reasons.length > 0) {
            return reasons[0];
         }
      }

      return null;
   }
}
