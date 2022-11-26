package weblogic.ejb20.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import org.omg.CORBA.MARSHAL;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.ReplacerObjectInputStream;
import weblogic.common.internal.ReplacerObjectOutputStream;
import weblogic.iiop.PortableReplaceable;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.Resolver;

public final class HandleImpl implements Handle, Externalizable, PortableReplaceable {
   private static final long serialVersionUID = 5484839489411820318L;
   private transient EJBObject ejbObject = null;
   private HomeHandleImpl homeHandle = null;
   private Object primaryKey = null;
   private Object stubInfo = null;

   public HandleImpl() {
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      this.homeHandle.writeExternal(oo);
      oo.writeObject(this.primaryKey);
      if (oo instanceof WLObjectOutput) {
         oo.writeObject(this.stubInfo);
      } else {
         ReplacerObjectOutputStream oos = new ReplacerObjectOutputStream((OutputStream)oo, RemoteObjectReplacer.getReplacer());
         oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
         if (this.stubInfo != null) {
            oos.writeObject(this.stubInfo);
         } else {
            oo.writeObject(this.stubInfo);
         }
      }

   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.homeHandle = new HomeHandleImpl();
      this.homeHandle.readExternal(oi);
      this.primaryKey = oi.readObject();

      try {
         if (oi instanceof WLObjectInput) {
            this.stubInfo = oi.readObject();
         } else {
            Replacer replacer = RemoteObjectReplacer.getReplacer();
            ReplacerObjectInputStream ois = new ReplacerObjectInputStream((InputStream)oi, replacer, (Resolver)null);
            this.stubInfo = ois.readObject();
         }
      } catch (IOException var4) {
      } catch (MARSHAL var5) {
      }

   }

   public String toString() {
      return this.homeHandle + "#" + this.primaryKey;
   }

   public HandleImpl(EJBObject eo) throws RemoteException {
      this.ejbObject = eo;
      EJBHome home = eo.getEJBHome();

      assert home.getEJBMetaData().isStatelessSession();

      this.primaryKey = null;
      this.homeHandle = (HomeHandleImpl)home.getHomeHandle();
   }

   public HandleImpl(EJBObject eo, Object pk) throws RemoteException {
      assert eo != null;

      assert pk != null;

      this.ejbObject = eo;
      EJBHome home = eo.getEJBHome();

      assert !home.getEJBMetaData().isStatelessSession();

      this.primaryKey = pk;
      this.homeHandle = (HomeHandleImpl)home.getHomeHandle();
      if (home.getEJBMetaData().isSession()) {
         this.stubInfo = eo;
      }

   }

   private EJBObject allocateEO(EJBHome home, Class[] param, Object[] arg) throws RemoteException {
      Class homeClass = home.getClass();

      try {
         Method allocMethod = homeClass.getMethod("allocateEJBObject", param);
         return (EJBObject)allocMethod.invoke(home, arg);
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Class " + homeClass.getName() + " did not have allocateEO method");
      } catch (InvocationTargetException var8) {
         Throwable t = var8.getTargetException();
         if (t instanceof RemoteException) {
            throw (RemoteException)t;
         } else {
            throw new RemoteException("Exception re-establishing handle", t);
         }
      } catch (IllegalAccessException var9) {
         throw new RemoteException("Exception re-establishing handle", var9);
      }
   }

   public EJBObject getEJBObject() throws RemoteException {
      if (this.ejbObject != null) {
         return this.ejbObject;
      } else if (this.primaryKey == null) {
         EJBHome home = this.homeHandle.getEJBHome();

         assert home.getEJBMetaData().isStatelessSession();

         return this.allocateEO(home, (Class[])null, (Object[])null);
      } else {
         EJBObject ejbObj = null;
         if (this.stubInfo != null) {
            ejbObj = (EJBObject)this.stubInfo;
         } else {
            EJBHome home = this.homeHandle.getEJBHome();

            assert !home.getEJBMetaData().isStatelessSession();

            ejbObj = this.allocateEO(home, new Class[]{Object.class}, new Object[]{this.primaryKey});
         }

         return ejbObj;
      }
   }
}
