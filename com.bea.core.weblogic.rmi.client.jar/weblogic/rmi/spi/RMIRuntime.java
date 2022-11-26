package weblogic.rmi.spi;

import java.io.IOException;
import java.util.Iterator;
import weblogic.utils.collections.ArraySet;

public class RMIRuntime {
   private static HostID localHostID;
   private static final ArraySet endPointFinderTable = new ArraySet(4);

   private static void setLocalHost(HostID local) {
      localHostID = local;
   }

   public static final RMIRuntime getRMIRuntime() {
      return RMIRuntime.RMIRuntimeSingleton.runtime;
   }

   public void setLocalHostID(HostID localHostID) {
      setLocalHost(localHostID);
   }

   public void addEndPointFinder(EndPointFinder endPointFinder) {
      endPointFinderTable.add(endPointFinder);
   }

   public static EndPoint findOrCreateEndPoint(HostID hostID) {
      return getEndPointFinder(hostID).findOrCreateEndPoint(hostID);
   }

   public static EndPoint findOrCreateEndPoint(String url) throws IOException {
      return getEndPointFinder(url).findOrCreateEndPoint(url);
   }

   public static EndPoint findEndPoint(String url) throws IOException {
      return getEndPointFinder(url).findEndPoint(url);
   }

   public static EndPoint findEndPoint(HostID hostID) {
      return getEndPointFinder(hostID).findEndPoint(hostID);
   }

   /** @deprecated */
   @Deprecated
   public static HostID getLocalHostID() {
      return localHostID;
   }

   private static EndPointFinder getEndPointFinder(String url) {
      Iterator i = endPointFinderTable.iterator();

      EndPointFinder endPointFinder;
      do {
         if (!i.hasNext()) {
            throw new AssertionError("unable to find EndPointFinder for " + url);
         }

         endPointFinder = (EndPointFinder)i.next();
      } while(!endPointFinder.claimServerURL(url));

      return endPointFinder;
   }

   private static EndPointFinder getEndPointFinder(HostID hostID) {
      Iterator i = endPointFinderTable.iterator();

      EndPointFinder endPointFinder;
      do {
         if (!i.hasNext()) {
            return new EndPointFinder() {
               public boolean claimHostID(HostID hostID) {
                  return true;
               }

               public boolean claimServerURL(String url) {
                  return false;
               }

               public EndPoint findOrCreateEndPoint(HostID hostID) {
                  return null;
               }

               public EndPoint findEndPoint(HostID hostID) {
                  return null;
               }

               public EndPoint findOrCreateEndPoint(String url) {
                  return null;
               }

               public EndPoint findEndPoint(String url) throws IOException {
                  return null;
               }
            };
         }

         endPointFinder = (EndPointFinder)i.next();
      } while(!endPointFinder.claimHostID(hostID));

      return endPointFinder;
   }

   public static boolean supportServerURL(String url) {
      Iterator i = endPointFinderTable.iterator();

      EndPointFinder endPointFinder;
      do {
         if (!i.hasNext()) {
            return false;
         }

         endPointFinder = (EndPointFinder)i.next();
      } while(!endPointFinder.claimServerURL(url));

      return true;
   }

   private static final class RMIRuntimeSingleton {
      private static final RMIRuntime runtime = new RMIRuntime();
   }
}
