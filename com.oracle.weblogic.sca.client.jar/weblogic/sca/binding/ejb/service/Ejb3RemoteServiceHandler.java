package weblogic.sca.binding.ejb.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.PortableRemoteObject;

public class Ejb3RemoteServiceHandler extends BaseServiceHandler implements Serializable {
   EjbServiceDelegate delegate;

   public Ejb3RemoteServiceHandler(EjbServiceDelegate delegate) {
      this.delegate = delegate;
   }

   protected Object invoke(Method method, Object[] args) throws EjbServiceException, RemoteException {
      return this.delegate.invoke(method.getName(), method.getParameterTypes(), args);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      ClassLoader save = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(EjbServiceDelegate.class.getClassLoader());
         Object obj = in.readObject();
         this.delegate = (EjbServiceDelegate)PortableRemoteObject.narrow(obj, EjbServiceDelegate.class);
      } finally {
         Thread.currentThread().setContextClassLoader(save);
      }

   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeObject(this.delegate);
   }
}
