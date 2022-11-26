package weblogic.sca.binding.ejb.service;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EjbServiceDelegate extends Remote {
   Object invoke(String var1, Class[] var2, Object[] var3) throws RemoteException, EjbServiceException;

   Object invoke(Method var1, Object[] var2) throws EjbServiceException;
}
