package weblogic.security.service;

import com.bea.common.security.SecurityLogger;
import weblogic.security.spi.Resource;
import weblogic.utils.UnsyncStringBuffer;

public class JNDIResource extends ResourceBase {
   private static final String ACTION = "action";
   private static final String[] KEYS = new String[]{"application", "path", "action"};
   private static final int REPEATING_FIELD_INDEX = 2;
   private static final int REPEATING_FIELD_TERMINATING_INDEX = 0;
   private static final Resource TOP = new JNDIResource((String)null, (String[])null, (String)null);
   private String action = null;
   private boolean showAction = false;
   private String[] path;
   private int pathLength;

   public JNDIResource(String application, String[] path, String actionName) throws InvalidParameterException {
      if (path != null) {
         for(int i = 0; i < path.length; ++i) {
            if (path[i] == null || path[i].length() == 0) {
               throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyArrayString("JNDI", KEYS[1]));
            }
         }
      }

      if (application != null && application.length() == 0) {
         throw new InvalidParameterException(SecurityLogger.getEmptyResourceKeyString("JNDI", KEYS[0]));
      } else {
         String[] vals = new String[]{application, path == null ? null : ""};
         this.init(vals, vals.length, path, path == null ? -1 : path.length, actionName, actionName != null);
      }
   }

   private JNDIResource(String[] values, int length, String[] path, int pathLength, String action, boolean showAction) {
      this.init(values, length, path, pathLength, action, showAction);
   }

   private void init(String[] values, int length, String[] path, int pathLength, String action, boolean showAction) {
      this.pathLength = pathLength;
      this.path = pathLength < 0 ? null : path;
      this.showAction = showAction;
      this.action = action;
      this.init(values, length, getSeed(this.path, this.pathLength, this.action, this.showAction));
   }

   private static long getSeed(String[] path, int pathLength, String action, boolean showAction) {
      long hash = (action != null ? (long)("action".hashCode() + action.hashCode()) : 0L) * (long)(showAction ? 1 : -1);

      for(int i = 0; i < pathLength; ++i) {
         hash = 31L * hash + (long)path[i].hashCode();
      }

      return hash;
   }

   public String getType() {
      return "<jndi>";
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
         int len = !this.showAction && this.pathLength <= 0 ? this.length - 1 : this.length;
         int plen = !this.showAction && this.pathLength >= 0 ? this.pathLength - 1 : this.pathLength;
         Resource parent = TOP;
         if (len > 0 || showAct) {
            parent = new JNDIResource(this.values, len, this.path, plen, act, showAct);
         }

         if (this.length == 1 && !this.showAction) {
            parent = new ApplicationResource(this.values[0], (Resource)parent);
         }

         return (Resource)parent;
      }
   }

   public String[] getKeys() {
      return KEYS;
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("path") ? 3 : 1;
   }

   public int getRepeatingFieldIndex() {
      return 2;
   }

   public int getRepeatingFieldTerminatingIndex() {
      return 0;
   }

   protected void writeResourceString(StringBuffer buf) {
      buf.append("type=").append(this.getType());
      if (this.length > 0) {
         buf.append(", application=");
         appendValue(buf, this.values[0]);
      }

      if (this.length > 1) {
         buf.append(", path=");
         appendArrayValue(buf, this.path, this.pathLength);
      }

      if (this.showAction) {
         buf.append(", ").append("action").append('=');
         appendValue(buf, this.action);
      }

   }

   public String getPathName() {
      if (this.pathLength <= 0) {
         return "";
      } else {
         UnsyncStringBuffer buf = new UnsyncStringBuffer(256);
         buf.append(this.path[0]);

         for(int i = 1; i < this.pathLength; ++i) {
            buf.append('.').append(this.path[i]);
         }

         return buf.toString();
      }
   }

   public String getActionName() {
      return this.showAction ? this.action : null;
   }

   public String getApplicationName() {
      return this.length > 0 ? this.values[0] : null;
   }

   public String[] getPath() {
      return this.path;
   }

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else {
         JNDIResource r = (JNDIResource)obj;
         if (this.showAction != r.showAction || this.action == null && r.action != null || this.action != null && !this.action.equals(r.action)) {
            return false;
         } else if (this.pathLength != r.pathLength) {
            return false;
         } else {
            if (this.path != r.path) {
               for(int i = 0; i < this.pathLength; ++i) {
                  if (!this.path[i].equals(r.path[i])) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }
}
