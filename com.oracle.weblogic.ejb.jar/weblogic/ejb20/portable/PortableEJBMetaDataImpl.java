package weblogic.ejb20.portable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.HomeHandle;

public final class PortableEJBMetaDataImpl implements EJBMetaData, Serializable {
   static final long serialVersionUID = 2161422698991859090L;
   private Class keyClass;
   private Class homeClass;
   private Class remoteClass;
   private boolean isSessionBean;
   private boolean isStatelessSessionBean;
   private HomeHandle homeHandle;

   public PortableEJBMetaDataImpl(EJBMetaData metaData) {
      this.homeHandle = new PortableHomeHandleImpl(metaData.getEJBHome());
      this.isStatelessSessionBean = metaData.isStatelessSession();
      this.isSessionBean = metaData.isSession();
      if (!this.isSessionBean) {
         this.keyClass = metaData.getPrimaryKeyClass();
      } else {
         this.keyClass = null;
      }

      this.homeClass = metaData.getHomeInterfaceClass();
      this.remoteClass = metaData.getRemoteInterfaceClass();
   }

   public Class getHomeInterfaceClass() {
      return this.homeClass;
   }

   public Class getRemoteInterfaceClass() {
      return this.remoteClass;
   }

   public EJBHome getEJBHome() {
      try {
         return this.homeHandle.getEJBHome();
      } catch (RemoteException var2) {
         throw new RuntimeException("Could not get ejbHome from HomeHandle " + var2.getMessage());
      }
   }

   public Class getPrimaryKeyClass() {
      if (this.keyClass == null) {
         throw new RuntimeException("SessionBeans do not have a primary key");
      } else {
         return this.keyClass;
      }
   }

   public boolean isSession() {
      return this.isSessionBean;
   }

   public boolean isStatelessSession() {
      return this.isStatelessSessionBean;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.isSessionBean = in.readBoolean();
      this.isStatelessSessionBean = in.readBoolean();
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      this.remoteClass = loader.loadClass(in.readUTF());
      this.homeClass = loader.loadClass(in.readUTF());
      if (!this.isSessionBean) {
         this.keyClass = loader.loadClass(in.readUTF());
      }

      this.homeHandle = (HomeHandle)in.readObject();
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeBoolean(this.isSessionBean);
      out.writeBoolean(this.isStatelessSessionBean);
      out.writeUTF(this.remoteClass.getName());
      out.writeUTF(this.homeClass.getName());
      if (!this.isSessionBean) {
         out.writeUTF(this.keyClass.getName());
      }

      out.writeObject(this.homeHandle);
   }
}
