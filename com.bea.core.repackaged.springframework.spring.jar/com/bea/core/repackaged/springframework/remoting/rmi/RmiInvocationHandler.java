package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocation;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiInvocationHandler extends Remote {
   @Nullable
   String getTargetInterfaceName() throws RemoteException;

   @Nullable
   Object invoke(RemoteInvocation var1) throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
}
