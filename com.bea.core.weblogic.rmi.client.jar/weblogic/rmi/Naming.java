package weblogic.rmi;

import java.net.MalformedURLException;
import java.util.ArrayList;
import javax.naming.CommunicationException;
import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.protocol.ServerURL;
import weblogic.rmi.registry.LocateRegistry;

public final class Naming {
   public static final String RMI_NAMING_JNDI_HOME = "weblogic.rmi";
   public static final String RMI_NAMING_JNDI_PATH = "weblogic.rmi/";

   private static synchronized JndiNamingInfo createJndiInfo(ServerURL url) throws MalformedURLException, java.rmi.RemoteException {
      try {
         return (JndiNamingInfo)JndiInfo.parse(url, false);
      } catch (CommunicationException var2) {
         throw new java.rmi.UnknownHostException("Could not init server [" + url.toString() + "]", var2);
      } catch (NamingException var3) {
         toWeblogicRmiException(var3, url.toString());
         return null;
      }
   }

   public static java.rmi.Remote lookup(String name) throws java.rmi.NotBoundException, MalformedURLException, java.rmi.RemoteException {
      java.rmi.Remote o = null;
      ServerURL url = LocateRegistry.getServerURL(name);
      JndiNamingInfo jndiInfo = createJndiInfo(url);

      try {
         o = (java.rmi.Remote)jndiInfo.getContext().lookup(jndiInfo.getPath());
      } catch (InvalidNameException var5) {
         throw (MalformedURLException)(new MalformedURLException(jndiInfo.getPath() + " provoked")).initCause(var5);
      } catch (NameNotFoundException var6) {
         throw (java.rmi.NotBoundException)(new java.rmi.NotBoundException(jndiInfo.getPath() + " provoked")).initCause(var6);
      } catch (NamingException var7) {
         toWeblogicRmiException(var7, jndiInfo.getPath());
      }

      return o;
   }

   public static void bindJava(String name, java.rmi.Remote impl) throws java.rmi.AlreadyBoundException, MalformedURLException, java.rmi.RemoteException {
      bindInternal(name, impl);
   }

   public static void bind(String name, java.rmi.Remote impl) throws java.rmi.AlreadyBoundException, MalformedURLException, java.rmi.RemoteException {
      bindInternal(name, impl);
   }

   private static void bindInternal(String name, Object impl) throws java.rmi.AlreadyBoundException, MalformedURLException, java.rmi.RemoteException {
      ServerURL url = LocateRegistry.getServerURL(name);
      JndiNamingInfo jndiInfo = createJndiInfo(url);

      try {
         jndiInfo.getContext().bind(jndiInfo.getPath(), impl);
      } catch (NameAlreadyBoundException var5) {
         throw (java.rmi.AlreadyBoundException)(new java.rmi.AlreadyBoundException(jndiInfo.getPath() + " provoked")).initCause(var5);
      } catch (NameNotFoundException var6) {
         throw (MalformedURLException)(new MalformedURLException(jndiInfo.getPath() + " provoked")).initCause(var6);
      } catch (NamingException var7) {
         toWeblogicRmiException(var7, jndiInfo.getPath());
      }

   }

   public static void unbind(String name) throws java.rmi.NotBoundException, MalformedURLException, java.rmi.RemoteException {
      ServerURL url = LocateRegistry.getServerURL(name);
      JndiNamingInfo jndiInfo = createJndiInfo(url);

      try {
         jndiInfo.getContext().unbind(jndiInfo.getPath());
      } catch (InvalidNameException | NameNotFoundException var4) {
         throw (java.rmi.NotBoundException)(new java.rmi.NotBoundException(jndiInfo.getPath() + " provoked")).initCause(var4);
      } catch (NamingException var5) {
         toWeblogicRmiException(var5, jndiInfo.getPath());
      }

   }

   public static void rebindJava(String name, java.rmi.Remote obj) throws MalformedURLException, java.rmi.RemoteException {
      rebindInternal(name, obj);
   }

   public static void rebind(String name, java.rmi.Remote obj) throws MalformedURLException, java.rmi.RemoteException {
      rebindInternal(name, obj);
   }

   private static void rebindInternal(String name, Object obj) throws MalformedURLException, java.rmi.RemoteException {
      ServerURL url = LocateRegistry.getServerURL(name);
      JndiNamingInfo jndiInfo = createJndiInfo(url);

      try {
         jndiInfo.getContext().rebind(jndiInfo.getPath(), obj);
      } catch (NameNotFoundException var5) {
         throw (MalformedURLException)(new MalformedURLException(jndiInfo.getPath() + " provoked")).initCause(var5);
      } catch (NamingException var6) {
         toWeblogicRmiException(var6, jndiInfo.getPath());
      }

   }

   public static String[] list(String name) throws MalformedURLException, java.rmi.RemoteException {
      String[] results = new String[0];
      ServerURL url = LocateRegistry.getServerURL(name);
      JndiInfo jndiInfo = createJndiInfo(url);

      try {
         ArrayList set = new ArrayList();
         NamingEnumeration e = jndiInfo.getContext().list("");

         while(e.hasMore()) {
            String binding_name = ((NameClassPair)e.next()).getName();
            set.add(jndiInfo.getFullURL(binding_name));
         }

         if (set.size() == 0) {
            return results;
         }

         results = new String[set.size()];
         set.toArray(results);
      } catch (NamingException var7) {
         toWeblogicRmiException(var7, jndiInfo.getProviderUrl());
      }

      return results;
   }

   private static void toWeblogicRmiException(NamingException ne, String url) throws MalformedURLException, java.rmi.RemoteException {
      Throwable t = ne.getRootCause();
      if (t == null) {
         if (ne instanceof InvalidNameException) {
            throw (MalformedURLException)(new MalformedURLException(url + " provoked")).initCause(ne);
         } else {
            throw new java.rmi.ConnectIOException("An unexpected exception occurred", ne);
         }
      } else if (t instanceof MalformedURLException) {
         throw (MalformedURLException)t;
      } else if (t instanceof java.net.UnknownHostException) {
         throw new java.rmi.UnknownHostException("Remapped jndi exception", (Exception)t);
      } else if (t instanceof java.rmi.UnknownHostException) {
         throw (java.rmi.UnknownHostException)t;
      } else if (t instanceof java.rmi.ConnectException) {
         throw (MalformedURLException)(new MalformedURLException("Remapped jndi exception provoked")).initCause(t);
      } else if (t instanceof java.rmi.RemoteException) {
         throw (java.rmi.RemoteException)t;
      } else {
         throw new java.rmi.MarshalException("Remapped jndi exception", (Exception)t);
      }
   }

   private Naming() {
   }
}
