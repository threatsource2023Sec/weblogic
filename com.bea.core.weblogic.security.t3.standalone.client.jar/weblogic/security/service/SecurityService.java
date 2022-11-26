package weblogic.security.service;

public interface SecurityService {
   void initialize(String var1);

   void start();

   void suspend();

   void shutdown();

   public static class ServiceType {
      private String type;
      public static final ServiceType AUDIT = new ServiceType("AUDIT");
      public static final ServiceType AUTHORIZE = new ServiceType("AUTHORIZE");
      public static final ServiceType BULKAUTHORIZE = new ServiceType("BULKAUTHORIZE");
      public static final ServiceType AUTHENTICATION = new ServiceType("AUTHENTICATION");
      public static final ServiceType PROFILE = new ServiceType("PROFILE");
      public static final ServiceType ROLE = new ServiceType("ROLE");
      public static final ServiceType BULKROLE = new ServiceType("BULKROLE");
      public static final ServiceType CREDENTIALMANAGER = new ServiceType("CREDENTIALMANAGER");
      public static final ServiceType KEYMANAGER = new ServiceType("KEYMANAGER");
      public static final ServiceType CERTPATH = new ServiceType("CERTPATH");

      private ServiceType(String type) {
         this.type = type;
      }

      public String toString() {
         return this.type;
      }
   }
}
