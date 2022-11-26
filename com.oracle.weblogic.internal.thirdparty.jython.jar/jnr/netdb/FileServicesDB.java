package jnr.netdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

final class FileServicesDB implements ServicesDB {
   public static String fileName = "/etc/services";

   public static final ServicesDB getInstance() {
      return FileServicesDB.SingletonHolder.INSTANCE;
   }

   private static final ServicesDB load() {
      try {
         NetDBParser parser = parseServicesFile();

         try {
            parser.iterator().next();
         } finally {
            parser.close();
         }

         return new FileServicesDB();
      } catch (Throwable var5) {
         return null;
      }
   }

   static final NetDBParser parseServicesFile() {
      try {
         return new NetDBParser(new FileReader(new File(fileName)));
      } catch (FileNotFoundException var1) {
         return new NetDBParser(new StringReader(""));
      }
   }

   private static final Service parseServicesEntry(NetDBEntry e) {
      String[] portproto = e.data.split("/");
      if (portproto.length < 2) {
         return null;
      } else {
         int port;
         try {
            port = Integer.parseInt(portproto[0], 10);
         } catch (NumberFormatException var4) {
            return null;
         }

         return new Service(e.name, port, portproto[1], e.aliases);
      }
   }

   private final Service parse(Filter filter) {
      NetDBParser parser = parseServicesFile();

      Service var6;
      try {
         Iterator i$ = parser.iterator();

         Service s;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            NetDBEntry e = (NetDBEntry)i$.next();
            s = parseServicesEntry(e);
         } while(s == null || !filter.filter(s));

         var6 = s;
      } finally {
         try {
            parser.close();
         } catch (IOException var14) {
            throw new RuntimeException(var14);
         }
      }

      return var6;
   }

   public Service getServiceByName(final String name, final String proto) {
      return this.parse(new Filter() {
         public boolean filter(Service s) {
            if (!s.proto.equals(proto) && proto != null) {
               return false;
            } else if (s.getName().equals(name)) {
               return true;
            } else {
               Iterator i$ = s.getAliases().iterator();

               String alias;
               do {
                  if (!i$.hasNext()) {
                     return false;
                  }

                  alias = (String)i$.next();
               } while(!alias.equals(name));

               return true;
            }
         }
      });
   }

   public Service getServiceByPort(final Integer port, final String proto) {
      return this.parse(new Filter() {
         public boolean filter(Service s) {
            return s.getPort() == port && (s.proto.equals(proto) || proto == null);
         }
      });
   }

   public Collection getAllServices() {
      final List allServices = new LinkedList();
      this.parse(new Filter() {
         public boolean filter(Service s) {
            allServices.add(s);
            return false;
         }
      });
      return Collections.unmodifiableList(allServices);
   }

   private interface Filter {
      boolean filter(Service var1);
   }

   private static final class SingletonHolder {
      public static final ServicesDB INSTANCE = FileServicesDB.load();
   }
}
