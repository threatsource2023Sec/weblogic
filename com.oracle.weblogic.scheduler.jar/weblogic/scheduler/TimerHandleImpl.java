package weblogic.scheduler;

public class TimerHandleImpl implements TimerHandle {
   private static final long serialVersionUID = -4508470517503684354L;
   private String id;
   private String domainId;

   public TimerHandleImpl(String id, String domainId) {
      this.id = id;
      this.domainId = domainId;
   }

   public Timer getTimer() throws NoSuchObjectLocalException, TimerException {
      return new TimerImpl(this.id, this.domainId);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TimerHandleImpl that = (TimerHandleImpl)o;
         if (this.domainId != null) {
            if (!this.domainId.equals(that.domainId)) {
               return false;
            }
         } else if (that.domainId != null) {
            return false;
         }

         if (this.id != null) {
            if (this.id.equals(that.id)) {
               return true;
            }
         } else if (that.id == null) {
            return true;
         }

         return false;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.id != null ? this.id.hashCode() : 0;
      result = 31 * result + (this.domainId != null ? this.domainId.hashCode() : 0);
      return result;
   }
}
