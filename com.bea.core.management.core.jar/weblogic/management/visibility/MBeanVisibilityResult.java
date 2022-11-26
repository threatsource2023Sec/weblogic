package weblogic.management.visibility;

public final class MBeanVisibilityResult {
   private WLSMBeanVisibility Visibility;
   private MBeanType mbeanType;

   public MBeanType getMbeanType() {
      return this.mbeanType;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MBeanVisibilityResult)) {
         return false;
      } else {
         MBeanVisibilityResult that = (MBeanVisibilityResult)o;
         if (!this.Visibility.equals(that.Visibility)) {
            return false;
         } else {
            return this.mbeanType.equals(that.mbeanType);
         }
      }
   }

   public int hashCode() {
      int result = this.Visibility != null ? this.Visibility.hashCode() : 0;
      result = 31 * result + (this.mbeanType != null ? this.mbeanType.hashCode() : 0);
      return result;
   }

   public WLSMBeanVisibility getVisibility() {
      return this.Visibility;
   }

   public MBeanVisibilityResult(WLSMBeanVisibility visibility, MBeanType mbeanType) {
      this.Visibility = visibility;
      this.mbeanType = mbeanType;
   }
}
