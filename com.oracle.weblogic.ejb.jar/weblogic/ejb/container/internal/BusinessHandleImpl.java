package weblogic.ejb.container.internal;

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
import javax.rmi.PortableRemoteObject;
import org.omg.CORBA.MARSHAL;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.ReplacerObjectInputStream;
import weblogic.common.internal.ReplacerObjectOutputStream;
import weblogic.ejb.spi.BusinessHandle;
import weblogic.ejb.spi.BusinessObject;
import weblogic.ejb20.internal.HomeHandleImpl;
import weblogic.iiop.PortableReplaceable;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.Resolver;

public final class BusinessHandleImpl implements BusinessHandle, Externalizable, PortableReplaceable {
   private static final long serialVersionUID = -339809761282601060L;
   private transient BusinessObject businessObject = null;
   private HomeHandleImpl homeHandle = null;
   private Object primaryKey = null;
   private Object stubInfo = null;
   private Object ifaceName = null;

   public BusinessHandleImpl() {
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      this.homeHandle.writeExternal(oo);
      oo.writeObject(this.primaryKey);
      oo.writeObject(this.ifaceName);
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
      this.ifaceName = oi.readObject();

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

   public BusinessHandleImpl(EJBHome home, BusinessObject bo, String ifaceName) throws RemoteException {
      this.businessObject = bo;
      this.ifaceName = ifaceName;
      this.primaryKey = null;
      this.homeHandle = (HomeHandleImpl)home.getHomeHandle();
   }

   public BusinessHandleImpl(EJBHome home, BusinessObject bo, Object pk) throws RemoteException {
      assert bo != null;

      assert pk != null;

      this.businessObject = bo;
      this.ifaceName = null;
      this.primaryKey = pk;
      this.homeHandle = (HomeHandleImpl)home.getHomeHandle();
      this.stubInfo = bo;
   }

   private BusinessObject allocateBO(EJBHome home, Class[] param, Object[] arg) throws RemoteException {
      Class homeClass = home.getClass();

      try {
         Method allocMethod = homeClass.getMethod("getBusinessImpl", param);
         Object object = allocMethod.invoke(home, arg);
         return (BusinessObject)PortableRemoteObject.narrow(object, BusinessObject.class);
      } catch (NoSuchMethodException var7) {
         throw new AssertionError("Class " + homeClass.getName() + " did not have allocateBO method");
      } catch (InvocationTargetException var8) {
         Throwable t = var8.getTargetException();
         throw EJBRuntimeUtils.asRemoteException("Exception re-establishing business handle", t);
      } catch (IllegalAccessException var9) {
         throw EJBRuntimeUtils.asRemoteException("Exception re-establishing business handle", var9);
      }
   }

   public BusinessObject getBusinessObject() throws RemoteException {
      if (this.businessObject != null) {
         return this.businessObject;
      } else if (this.primaryKey == null) {
         EJBHome home = this.homeHandle.getEJBHome();
         return this.allocateBO(home, new Class[]{Object.class, String.class}, new Object[]{this.primaryKey, this.ifaceName});
      } else {
         return (BusinessObject)this.stubInfo;
      }
   }
}
