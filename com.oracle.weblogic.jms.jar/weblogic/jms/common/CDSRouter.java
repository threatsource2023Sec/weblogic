package weblogic.jms.common;

import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class CDSRouter implements Invocable {
   private static Invocable remoteProxy;
   private static Invocable localProxy;
   private static CDSRouter singleton = new CDSRouter();

   public static CDSRouter getSingleton() {
      return singleton;
   }

   public int invoke(Request request) throws Throwable {
      switch (request.getMethodId()) {
         case 18455:
            if (remoteProxy != null) {
               return remoteProxy.invoke(request);
            }

            throw new JMSException("No such method " + request.getMethodId());
         case 18711:
            return localProxy.invoke(request);
         case 18967:
            if (remoteProxy != null) {
               remoteProxy.invoke(request);
            }

            return localProxy.invoke(request);
         default:
            throw new JMSException("No such method " + request.getMethodId());
      }
   }

   public JMSID getJMSID() {
      return null;
   }

   public ID getId() {
      return null;
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return null;
   }

   public InvocableMonitor getInvocableMonitor() {
      return null;
   }

   static {
      try {
         remoteProxy = (Invocable)Class.forName("weblogic.jms.common.CDSRemoteProxy").getDeclaredMethod("getSingleton", (Class[])null).invoke((Object)null, (Object[])null);
      } catch (Exception var2) {
      }

      try {
         localProxy = (Invocable)Class.forName("weblogic.jms.common.CDSLocalProxy").getDeclaredMethod("getSingleton", (Class[])null).invoke((Object)null, (Object[])null);
      } catch (Exception var1) {
      }

   }
}
