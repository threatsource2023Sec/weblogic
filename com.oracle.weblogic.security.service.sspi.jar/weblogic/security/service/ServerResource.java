package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;

public final class ServerResource extends ResourceBase {
   private static final String ACTION = "action";
   private static final String[] KEYS = new String[]{"application", "server", "action"};
   private static final int REPEATING_FIELD_INDEX = 2;
   private static final int REPEATING_FIELD_TERMINATING_INDEX = 0;
   private static final Resource TOP = new ServerResource((String[])null, 0, (String)null, false);
   private String action = null;
   private boolean showAction = false;

   public ServerResource(String application, String server, String action) {
      if (application != null && application.length() == 0) {
         throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("Server", KEYS[0]));
      } else {
         String[] vals = new String[]{application, server};
         this.init(vals, vals.length, action, action != null);
      }
   }

   private ServerResource(String[] values, int length, String action, boolean showAction) {
      this.init(values, length, action, showAction);
   }

   private void init(String[] values, int length, String action, boolean showAction) {
      this.action = action;
      this.showAction = showAction;
      this.init(values, length, getSeed(this.action, this.showAction));
   }

   private static long getSeed(String action, boolean showAction) {
      return (action != null ? (long)("action".hashCode() + action.hashCode()) : 0L) * (long)(showAction ? 1 : -1);
   }

   public String getType() {
      return "<svr>";
   }

   protected Resource makeParent() {
      if (this.length == 0) {
         return this.showAction ? TOP : null;
      } else {
         String act = this.action;
         if (SCOPE_RESOURCE_ACTION) {
            act = null;
         }

         boolean showAct = !this.showAction && this.action != null;
         int len = this.showAction ? this.length : this.length - 1;
         Resource parent = TOP;
         if (len > 0 || showAct) {
            parent = new ServerResource(this.values, len, act, showAct);
         }

         if (this.length == 1 && !this.showAction) {
            parent = new ApplicationResource(this.values[0], (Resource)parent);
         }

         return (Resource)parent;
      }
   }

   public int getRepeatingFieldIndex() {
      return 2;
   }

   public int getRepeatingFieldTerminatingIndex() {
      return 0;
   }

   public String[] getKeys() {
      return KEYS;
   }

   public String getServerName() {
      return this.length > 1 ? this.values[1] : null;
   }

   public String getActionName() {
      return this.showAction ? this.action : null;
   }

   public String getApplicationName() {
      return this.length > 0 ? this.values[0] : null;
   }

   protected void writeResourceString(StringBuffer buf) {
      super.writeResourceString(buf);
      if (this.showAction) {
         buf.append(", ").append("action").append('=');
         appendValue(buf, this.action);
      }

   }

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else {
         ServerResource r = (ServerResource)obj;
         return this.showAction == r.showAction && (this.action == null && r.action == null || this.action != null && this.action.equals(r.action));
      }
   }
}
