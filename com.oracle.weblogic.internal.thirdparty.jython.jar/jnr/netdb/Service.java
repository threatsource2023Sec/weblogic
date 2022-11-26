package jnr.netdb;

import java.util.Collection;
import java.util.Collections;

public final class Service {
   private final String serviceName;
   private final int port;
   final String proto;
   private final Collection aliases;

   Service(String name, int port, String proto, Collection aliases) {
      this.serviceName = name;
      this.port = port;
      this.proto = proto;
      this.aliases = aliases;
   }

   public static final Service getServiceByName(String name, String proto) {
      return getServicesDB().getServiceByName(name, proto);
   }

   public static final Service getServiceByPort(int port, String proto) {
      return getServicesDB().getServiceByPort(port, proto);
   }

   public static final Collection getAllServices() {
      return Collections.emptyList();
   }

   public final String getName() {
      return this.serviceName;
   }

   public final int getPort() {
      return this.port;
   }

   public final Collection getAliases() {
      return this.aliases;
   }

   private static final ServicesDB getServicesDB() {
      return Service.ServicesDBSingletonHolder.INSTANCE;
   }

   public String toString() {
      return String.format("<Service: Name: %s, Port: %d, Proto: %s, Aliases: %s>", this.serviceName, this.port, this.proto, this.aliases);
   }

   private static final class ServicesDBSingletonHolder {
      static final ServicesDB INSTANCE = load();

      private static final ServicesDB load() {
         ServicesDB db = NativeServicesDB.load();
         if (db == null) {
            db = FileServicesDB.getInstance();
         }

         return (ServicesDB)(db != null ? db : IANAServicesDB.getInstance());
      }
   }
}
