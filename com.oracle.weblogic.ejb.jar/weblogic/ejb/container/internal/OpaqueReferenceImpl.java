package weblogic.ejb.container.internal;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.interfaces.Ejb3StatefulHome;
import weblogic.jndi.ClassTypeOpaqueReference;
import weblogic.rmi.extensions.activation.Activator;

public final class OpaqueReferenceImpl implements ClassTypeOpaqueReference, Serializable {
   private final Ejb3StatefulHome home;
   private final String businessIntfName;
   private final transient Activator activator;
   private final transient Class clientView;
   private final transient Class businessImplClass;
   private final transient Class generatedRemoteInterface;

   OpaqueReferenceImpl(Ejb3StatefulHome home, Class businessImplClass, Activator activator, Class clientView, Class generatedRemoteInterface) {
      this.home = home;
      this.businessImplClass = businessImplClass;
      this.activator = activator;
      this.clientView = clientView;
      this.generatedRemoteInterface = generatedRemoteInterface;
      this.businessIntfName = clientView.getName();
   }

   public Class getBusinessIntfClass() {
      return this.clientView;
   }

   public Activator getActivator() {
      return this.activator;
   }

   public Class getBusinessImplClass() {
      return this.businessImplClass;
   }

   public Class getGeneratedRemoteInterface() {
      return this.generatedRemoteInterface;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      try {
         return this.clientView != null ? this.home.getBusinessImpl(this.businessImplClass, this.activator, this.clientView, this.generatedRemoteInterface) : this.home.getBusinessImpl(this.businessIntfName);
      } catch (Exception var6) {
         Throwable detail = var6;
         if (var6.getCause() != null) {
            detail = var6.getCause();
         }

         NamingException ne = new NamingException(((Throwable)detail).toString());
         ne.setRootCause((Throwable)detail);
         throw ne;
      }
   }

   public Class getObjectClass() {
      if (this.clientView != null) {
         return this.clientView;
      } else {
         try {
            return this.home.getBusinessImpl(this.businessIntfName).getClass();
         } catch (RemoteException var2) {
            return null;
         }
      }
   }
}
