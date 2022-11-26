package weblogic.management.visibility;

import java.io.Serializable;

public class WLSMBeanVisibility implements Serializable {
   private static final long serialVersionUID = 12221L;
   private final int ordinal;
   private final String name;
   public static final WLSMBeanVisibility ALL = new WLSMBeanVisibility(0, "ALL");
   public static final WLSMBeanVisibility NONE = new WLSMBeanVisibility(1, "None");
   public static final WLSMBeanVisibility SOME = new WLSMBeanVisibility(2, "SOME");

   protected WLSMBeanVisibility(int ordinal, String name) {
      this.ordinal = ordinal;
      this.name = name;
   }

   public int getValue() {
      return this.ordinal;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof WLSMBeanVisibility)) {
         return false;
      } else {
         WLSMBeanVisibility that = (WLSMBeanVisibility)o;
         return this.ordinal == that.ordinal;
      }
   }

   public int hashCode() {
      return this.ordinal;
   }

   public String toString() {
      return this.name;
   }
}
