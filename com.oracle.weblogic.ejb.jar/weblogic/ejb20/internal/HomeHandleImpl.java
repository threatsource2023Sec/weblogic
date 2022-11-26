package weblogic.ejb20.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import javax.ejb.EJBHome;
import javax.ejb.HomeHandle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.internal.URLDelegate;
import weblogic.iiop.PortableReplaceable;
import weblogic.protocol.ChannelHelperBase;
import weblogic.rmi.extensions.PortableRemoteObject;

public final class HomeHandleImpl implements HomeHandle, Externalizable, PortableReplaceable {
   private static final long serialVersionUID = -4517252809192149290L;
   private String serverURL;
   private Name jndiName;
   private transient EJBHome home = null;
   private transient URLDelegate urlDelegate = null;

   public HomeHandleImpl() {
   }

   public HomeHandleImpl(EJBHome h, Name jndiName, URLDelegate delegate) {
      this.home = h;
      this.jndiName = jndiName;
      this.urlDelegate = delegate;
   }

   public String toString() {
      return this.jndiName + ":" + this.serverURL;
   }

   public EJBHome getEJBHome() throws RemoteException {
      if (this.home == null) {
         String url = this.serverURL;
         if (url == null) {
            url = ChannelHelperBase.getDefaultURL();
         }

         try {
            Hashtable p = new Hashtable();
            p.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
            p.put("java.naming.provider.url", url);
            Context ctx = new InitialContext(p);
            this.home = (EJBHome)PortableRemoteObject.narrow(ctx.lookup(this.jndiName), EJBHome.class);

            assert this.home != null;
         } catch (ClassCastException var4) {
            EJBLogger.logStackTrace(var4);
         } catch (NamingException var5) {
            throw new NoSuchObjectException("Unable to locate EJBHome: '" + this.jndiName + "' on server: '" + url);
         }
      }

      return this.home;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      if (this.serverURL == null) {
         oo.writeObject(this.urlDelegate.getURL(oo));
      } else {
         oo.writeObject(this.serverURL);
      }

      oo.writeObject(this.jndiName);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.serverURL = (String)oi.readObject();
      this.jndiName = (Name)oi.readObject();
      this.urlDelegate = URLDelegate.CHANNEL_URL_DELEGATE;
   }
}
