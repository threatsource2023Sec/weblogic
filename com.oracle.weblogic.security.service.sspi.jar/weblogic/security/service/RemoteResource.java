package weblogic.security.service;

import weblogic.security.spi.Resource;

public final class RemoteResource extends ResourceBase {
   private static final String ACTION = "method";
   private static final String[] KEYS = new String[]{"protocol", "remoteHost", "remotePort", "path", "method"};
   private static final int REPEATING_FIELD_INDEX = 4;
   private static final int REPEATING_FIELD_TERMINATING_INDEX = 0;
   private static final Resource TOP = new RemoteResource((String)null, (String)null, (String)null, (String)null, (String)null);
   private String action = null;
   private boolean showAction = false;

   public RemoteResource(String protocol, String remoteHost, String remotePort, String path, String method) {
      String[] vals = new String[]{protocol, remoteHost, remotePort, path, method};
      this.init(vals, 4, 0L, method, true);
   }

   private RemoteResource(String[] values, int length, String method, boolean showAction) {
      this.action = method;
      this.init(values, length, 0L, method, showAction);
   }

   protected void init(String[] values, int len, long seed, String method, boolean showAction) {
      if (len == 4 && values[3] != null && "/".equals(values[3])) {
         len = 3;
      }

      this.action = method;
      this.showAction = showAction && this.action != null && this.action.length() > 0;
      seed += 31L * (long)(method == null ? 0 : method.hashCode() + 1);
      seed += showAction ? 1L : 0L;
      super.init(values, len, seed);
   }

   public String getType() {
      return "<remote>";
   }

   protected Resource makeParent() {
      if (this.length == 0) {
         Resource rtn = this.showAction ? TOP : null;
         return rtn;
      } else if (this.showAction) {
         RemoteResource rtn = new RemoteResource(this.values, this.length, this.action, false);
         return rtn;
      } else {
         int len = this.length - 1;
         if (this.length == 4) {
            String path = this.values[3];
            if (path != null) {
               int finalSlash = path.lastIndexOf("/");
               if (finalSlash > 0) {
                  String newPath = path.substring(0, finalSlash);
                  RemoteResource rtn = this.remoteResourceWithNewPath(newPath, this.action, true);
                  return rtn;
               }
            }
         }

         RemoteResource rtn = new RemoteResource(this.values, len, this.action, true);
         return rtn;
      }
   }

   private RemoteResource remoteResourceWithNewPath(String newPath, String method, boolean showAct) {
      int size = 3;
      if (method != null) {
         size = 4;
      }

      return new RemoteResource(new String[]{this.values[0], this.values[1], this.values[2], newPath, method}, size, method, showAct);
   }

   public String[] getKeys() {
      return KEYS;
   }

   public int getFieldType(String fieldName) {
      return fieldName.equals("path") ? 2 : 1;
   }

   public int getRepeatingFieldIndex() {
      return 4;
   }

   public int getRepeatingFieldTerminatingIndex() {
      return 0;
   }

   public String getProtocol() {
      return this.getValue(0);
   }

   public String getRemoteHost() {
      return this.getValue(1);
   }

   public String getRemotePort() {
      return this.getValue(2);
   }

   public String getPath() {
      return this.getValue(3);
   }

   public String getMethod() {
      return this.getValue(4);
   }

   private String getValue(int index) {
      return this.length > index ? this.values[index] : null;
   }

   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else {
         RemoteResource r = (RemoteResource)obj;
         return this.showAction == r.showAction && (this.action == null && r.action == null || this.action != null && this.action.equals(r.action));
      }
   }

   protected void writeResourceString(StringBuffer buf) {
      super.writeResourceString(buf);
      if (this.showAction) {
         buf.append(", ").append("method").append('=');
         appendValue(buf, this.action);
      }

   }
}
