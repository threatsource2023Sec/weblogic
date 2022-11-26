package weblogic.rmi.registry;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.protocol.ServerURL;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rmi.JndiInfo;
import weblogic.rmi.JndiNamingInfo;
import weblogic.rmi.ProviderUrlInfo;

public final class LocateRegistry implements Registry {
   private ProviderUrlInfo providerUrlInfo;

   private LocateRegistry(ServerURL url) throws NamingException {
      this.providerUrlInfo = (ProviderUrlInfo)JndiInfo.parse(url, true);
   }

   private LocateRegistry() throws NamingException {
      this(ServerURL.DEFAULT_URL);
   }

   private LocateRegistry(int i) throws MalformedURLException, NamingException {
      this(new ServerURL(RJVMEnvironment.getEnvironment().getDefaultProtocolName(), ServerURL.DEFAULT_URL.getHost(), i, ""));
   }

   private LocateRegistry(String name, int i) throws MalformedURLException, NamingException {
      this(new ServerURL(RJVMEnvironment.getEnvironment().getDefaultProtocolName(), name, i, ""));
   }

   private LocateRegistry(String name) throws MalformedURLException, NamingException {
      this(getServerURL(name));
   }

   public Remote lookup(String name) throws RemoteException, NotBoundException {
      try {
         JndiNamingInfo bindInfo = this.providerUrlInfo.parse(name);
         Context context = bindInfo.getContext();
         Remote o = (Remote)context.lookup(bindInfo.getPath());
         return o;
      } catch (MalformedURLException var5) {
         throw new UnknownHostException("Bad url in lookup for " + name, var5);
      } catch (NamingException var6) {
         if (var6.getRootCause() != null && var6.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var6.getRootCause();
         } else if (var6 instanceof NameNotFoundException) {
            throw new NotBoundException(name);
         } else {
            throw new UnknownHostException("Failed lookup for " + name, var6);
         }
      }
   }

   public void bind(String name, Remote o) throws RemoteException, AlreadyBoundException {
      try {
         JndiNamingInfo bindInfo = this.providerUrlInfo.parse(name);
         bindInfo.getContext().bind(bindInfo.getPath(), o);
      } catch (MalformedURLException var4) {
         throw new UnknownHostException("Bad url in bind for " + name, var4);
      } catch (NamingException var5) {
         if (var5.getRootCause() != null && var5.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var5.getRootCause();
         } else if (var5 instanceof NameAlreadyBoundException) {
            throw new AlreadyBoundException(name);
         } else {
            throw new UnknownHostException("Failed bind for " + name, var5);
         }
      }
   }

   public void unbind(String name) throws RemoteException, NotBoundException {
      try {
         JndiNamingInfo bindInfo = this.providerUrlInfo.parse(name);
         bindInfo.getContext().unbind(bindInfo.getPath());
      } catch (MalformedURLException var3) {
         throw new UnknownHostException("Bad url in unbind for " + name, var3);
      } catch (NamingException var4) {
         if (var4.getRootCause() != null && var4.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var4.getRootCause();
         } else if (var4 instanceof NameNotFoundException) {
            throw new NotBoundException(name);
         } else {
            throw new UnknownHostException("Failed unbind for " + name, var4);
         }
      }
   }

   public void rebind(String name, Remote o) throws RemoteException {
      try {
         JndiNamingInfo bindInfo = this.providerUrlInfo.parse(name);
         bindInfo.getContext().rebind(bindInfo.getPath(), o);
      } catch (MalformedURLException var4) {
         throw new UnknownHostException("Bad url in rebind for " + name, var4);
      } catch (NamingException var5) {
         if (var5.getRootCause() != null && var5.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var5.getRootCause();
         } else {
            throw new UnknownHostException("Failed rebind for " + name, var5);
         }
      }
   }

   public String[] list() throws RemoteException {
      String[] results = new String[0];

      try {
         ArrayList set = new ArrayList();
         NamingEnumeration e = this.providerUrlInfo.getContext().list("");

         while(e.hasMore()) {
            String binding_name = ((NameClassPair)e.next()).getName();
            set.add(this.providerUrlInfo.getFullURL(binding_name));
         }

         if (set.size() == 0) {
            return results;
         } else {
            results = new String[set.size()];
            set.toArray(results);
            return results;
         }
      } catch (NamingException var5) {
         if (var5.getRootCause() != null && var5.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var5.getRootCause();
         } else {
            throw new UnknownHostException("Failed list for [weblogic.rmi]", var5);
         }
      }
   }

   public static Registry getRegistry() throws RemoteException {
      try {
         return new LocateRegistry();
      } catch (NamingException var1) {
         if (var1.getRootCause() != null && var1.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var1.getRootCause();
         } else {
            throw new UnknownHostException("Bad URL for " + ServerURL.DEFAULT_URL, var1);
         }
      }
   }

   public static Registry getRegistry(int port) throws RemoteException {
      try {
         return new LocateRegistry(port);
      } catch (NamingException var2) {
         if (var2.getRootCause() != null && var2.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var2.getRootCause();
         } else {
            throw new UnknownHostException("Could not connect to localhost at port " + port, var2);
         }
      } catch (MalformedURLException var3) {
         throw new UnknownHostException("Could not connect to port " + port + " on local host", var3);
      }
   }

   public static Registry getRegistry(String name) throws RemoteException {
      try {
         return new LocateRegistry(name);
      } catch (NamingException var2) {
         if (var2.getRootCause() != null && var2.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var2.getRootCause();
         } else {
            throw new UnknownHostException("Could not connect to " + name, var2);
         }
      } catch (MalformedURLException var3) {
         throw new UnknownHostException("Could not connect to " + name + " on local host", var3);
      }
   }

   public static Registry getRegistry(String name, int port) throws RemoteException {
      try {
         return new LocateRegistry(name, port);
      } catch (NamingException var3) {
         if (var3.getRootCause() != null && var3.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var3.getRootCause();
         } else {
            throw new UnknownHostException("Could not connect to " + name + " at port " + port, var3);
         }
      } catch (MalformedURLException var4) {
         throw new UnknownHostException("Could not connect to port " + port + " on local host", var4);
      }
   }

   public static Registry createRegistry(int port) throws RemoteException {
      try {
         return new LocateRegistry();
      } catch (NamingException var2) {
         if (var2.getRootCause() != null && var2.getRootCause() instanceof RemoteException) {
            throw (RemoteException)var2.getRootCause();
         } else {
            throw new UnknownHostException("Could not connect to port " + port + " on local host", var2);
         }
      }
   }

   public static String getURL(ServerURL url) {
      return url.getQuery().equals("") ? url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() : url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + url.getQuery();
   }

   public static ServerURL getServerURL(String name) throws MalformedURLException {
      if (name.startsWith("rmi://")) {
         return new ServerURL(ServerURL.DEFAULT_URL, RJVMEnvironment.getEnvironment().getDefaultProtocolName() + name.substring(3));
      } else if (name.startsWith("rmis://")) {
         return new ServerURL(ServerURL.DEFAULT_URL, RJVMEnvironment.getEnvironment().getDefaultProtocolName() + name.substring(4));
      } else if (name.startsWith("//")) {
         return new ServerURL(ServerURL.DEFAULT_URL, RJVMEnvironment.getEnvironment().getDefaultProtocolName() + ":" + name);
      } else {
         return !name.contains(":/") ? new ServerURL(ServerURL.DEFAULT_URL, RJVMEnvironment.getEnvironment().getDefaultProtocolName() + ":" + name) : new ServerURL(ServerURL.DEFAULT_URL, name);
      }
   }
}
