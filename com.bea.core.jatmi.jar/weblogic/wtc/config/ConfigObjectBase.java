package weblogic.wtc.config;

import weblogic.wtc.gwt.WTCMBeanObject;

public class ConfigObjectBase {
   public static final int CONFIG_OBJ_TYPE_RES = 0;
   public static final int CONFIG_OBJ_TYPE_LAP = 1;
   public static final int CONFIG_OBJ_TYPE_RAP = 2;
   public static final int CONFIG_OBJ_TYPE_SG = 3;
   public static final int CONFIG_OBJ_TYPE_SGP = 4;
   public static final int CONFIG_OBJ_TYPE_IMP = 5;
   public static final int CONFIG_OBJ_TYPE_EXP = 6;
   public static final int CONFIG_OBJ_TYPE_PWD = 7;
   public static final int MBEAN_OBJ_TYPE_RES = 0;
   public static final int MBEAN_OBJ_TYPE_LAP = 1;
   public static final int MBEAN_OBJ_TYPE_RAP = 2;
   public static final int MBEAN_OBJ_TYPE_IMP = 3;
   public static final int MBEAN_OBJ_TYPE_EXP = 4;
   public static final int MBEAN_OBJ_TYPE_PWD = 5;
   public static final int UNKNOWN = -1;
   public static final int CONFIG_OBJ_ST_UNKNOWN = -1;
   public static final int CONFIG_OBJ_ST_ACTIVE = 0;
   public static final int CONFIG_OBJ_ST_SUSPEND = 1;
   public static final int CONFIG_OBJ_ST_INACTIVE = 2;
   protected int ctype = -1;
   protected int mtype = -1;
   protected WTCMBeanObject configSource = null;
   protected int state = -1;
   protected int coId = -1;

   public int getConfigObjType() {
      return this.ctype;
   }

   public int getMBeanObjType() {
      return this.mtype;
   }

   public WTCMBeanObject getConfigSource() {
      return this.configSource;
   }

   public void setConfigSource(WTCMBeanObject src) {
      this.configSource = src;
   }

   public void activate() {
      this.state = 0;
   }

   public void suspend() {
      this.state = 1;
   }

   public void deactivate() {
      this.state = 2;
   }

   public int getState() {
      return this.state;
   }

   public void setLookupId(int id) {
      this.coId = id;
   }

   public int getLookupId() {
      return this.coId;
   }

   public boolean equals(Object e) {
      return e != null && e instanceof ConfigObjectBase && ((ConfigObjectBase)e).getLookupId() == this.coId;
   }

   public int hashCode() {
      return this.coId;
   }
}
