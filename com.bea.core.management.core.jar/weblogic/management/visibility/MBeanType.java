package weblogic.management.visibility;

import java.io.Serializable;

public class MBeanType implements Serializable {
   private static final long serialVersionUID = 12221L;
   private final int ordinal;
   private final String name;
   public static final MBeanType SECURITY = new MBeanType(1, "SECURITY");
   public static final MBeanType WLS_COHERENCE = new MBeanType(2, "WLS_COHERENCE");
   public static final MBeanType WLS_VIRTUAL_TARGET = new MBeanType(3, "WLS_VIRTUAL_TARGET");
   public static final MBeanType WLS_DEPLOYMENT_RUNTIME = new MBeanType(4, "WLS_DEPLOYMENT_RUNTIME");
   public static final MBeanType WLS_CONFIGURATION_RUNTIME = new MBeanType(5, "WLS_CONFIGURATION_RUNTIME");
   public static final MBeanType WLS_GLOBAL_OTHER = new MBeanType(6, "WLS_GLOBAL_OTHER");
   public static final MBeanType WLS_PARTITION = new MBeanType(7, "WLS_PARTITION");
   public static final MBeanType JRF_GLOBAL = new MBeanType(8, "JRF_GLOBAL");
   public static final MBeanType JRF_PARTITION = new MBeanType(9, "JRF_PARTITION");
   public static final MBeanType JDK = new MBeanType(10, "JDK");
   public static final MBeanType OTHER = new MBeanType(0, "OTHER");

   protected MBeanType(int type, String name) {
      this.ordinal = type;
      this.name = name;
   }

   public int getType() {
      return this.ordinal;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MBeanType)) {
         return false;
      } else {
         MBeanType mBeanType = (MBeanType)o;
         return this.ordinal == mBeanType.ordinal;
      }
   }

   public int hashCode() {
      return this.ordinal;
   }

   public String toString() {
      return this.name;
   }
}
