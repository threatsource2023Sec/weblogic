package weblogic.osgi.internal;

import java.util.HashMap;
import java.util.Map;
import weblogic.osgi.OSGiServer;
import weblogic.osgi.OSGiServerManager;

public class OSGiServerManagerImpl implements OSGiServerManager {
   private final Object lock = new Object();
   private final Map database = new HashMap();

   public OSGiServer getOSGiServer(String name) {
      synchronized(this.lock) {
         return (OSGiServer)this.database.get(name);
      }
   }

   void add(OSGiServerImpl toAdd) {
      synchronized(this.lock) {
         this.database.put(toAdd.getName(), toAdd);
      }
   }

   OSGiServerImpl remove(String name) {
      synchronized(this.lock) {
         return (OSGiServerImpl)this.database.remove(name);
      }
   }

   public String toString() {
      return "OSGiServerManagerImpl(" + System.identityHashCode(this) + ")";
   }
}
