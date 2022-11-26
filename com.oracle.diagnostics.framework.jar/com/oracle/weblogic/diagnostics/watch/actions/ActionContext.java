package com.oracle.weblogic.diagnostics.watch.actions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionContext {
   private Map watchData = new ConcurrentHashMap();
   private ActionConfigBean configBean;

   public ActionContext(ActionConfigBean config) {
      this.configBean = config;
   }

   public ActionConfigBean getActionConfig() {
      return this.configBean;
   }

   public Map getWatchData() {
      return this.watchData;
   }

   public void addWatchData(String key, Object value) {
      this.watchData.put(key, value);
   }

   public void addWatchData(Map values) {
      this.watchData.putAll(values);
   }
}
