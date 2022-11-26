package weblogic.rmi.extensions;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.Iterator;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.rmi.extensions.server.DisconnectMonitorProvider;

public class DisconnectMonitorListImpl implements DisconnectMonitor, DisconnectMonitorList {
   private static ArrayList disconnectMonitorList = new ArrayList();

   private DisconnectMonitorListImpl() {
   }

   private static DisconnectMonitorListImpl getInstance() {
      return DisconnectMonitorListImpl.DisconnectMonitorListImplSingleton.theDisconnectMonitorList;
   }

   public static DisconnectMonitorList getDisconnectMonitorList() {
      return getInstance();
   }

   public static DisconnectMonitor getDisconnectMonitor() {
      return getInstance();
   }

   public synchronized void addDisconnectMonitor(DisconnectMonitorProvider monitor) {
      disconnectMonitorList.add(monitor);
   }

   public synchronized void removeDisconnectMonitor(DisconnectMonitorProvider monitor) {
      disconnectMonitorList.remove(monitor);
   }

   public synchronized void addDisconnectListener(Remote remoteStub, DisconnectListener listener) throws DisconnectMonitorUnavailableException {
      Iterator iter = disconnectMonitorList.iterator();

      DisconnectMonitorProvider next;
      do {
         if (!iter.hasNext()) {
            throw new DisconnectMonitorUnavailableException("Could not register a DisconnectListener for [" + remoteStub + "]");
         }

         next = (DisconnectMonitorProvider)iter.next();
      } while(!next.addDisconnectListener(remoteStub, listener));

   }

   public synchronized void removeDisconnectListener(Remote remoteObject, DisconnectListener listener) throws DisconnectMonitorUnavailableException {
      Iterator iter = disconnectMonitorList.iterator();

      DisconnectMonitorProvider dcm;
      do {
         if (!iter.hasNext()) {
            throw new DisconnectMonitorUnavailableException("No registration found for [" + remoteObject + "]");
         }

         dcm = (DisconnectMonitorProvider)iter.next();
      } while(!dcm.removeDisconnectListener(remoteObject, listener));

   }

   public static void bindToJNDI(Context ctx) throws NamingException {
      ctx.bind("weblogic.DisconnectMonitor", getDisconnectMonitor());
   }

   public static void unbindFromJNDI(Context ctx) throws NamingException {
      ctx.unbind("weblogic.DisconnectMonitor");
   }

   // $FF: synthetic method
   DisconnectMonitorListImpl(Object x0) {
      this();
   }

   private static final class DisconnectMonitorListImplSingleton {
      private static final DisconnectMonitorListImpl theDisconnectMonitorList = new DisconnectMonitorListImpl();
   }
}
