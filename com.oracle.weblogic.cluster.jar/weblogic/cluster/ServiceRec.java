package weblogic.cluster;

class ServiceRec {
   public int id;
   public Object service;

   public ServiceRec(int id, Object service) {
      this.id = id;
      this.service = service;
   }

   public boolean equals(Object o) {
      try {
         ServiceRec sr = (ServiceRec)o;
         if (this.id != -1 && sr.id != -1) {
            return this.service == null || this.id == sr.id && this.service.equals(sr.service);
         } else {
            return this.service == null || this.service.equals(sr.service);
         }
      } catch (ClassCastException var3) {
         return false;
      }
   }
}
