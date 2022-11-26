package weblogic.iiop;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import javax.rmi.CORBA.UtilDelegate;
import javax.rmi.CORBA.ValueHandler;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import weblogic.corba.utils.CorbaUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.ior.IORDelegate;
import weblogic.kernel.Kernel;

public final class UtilDelegateImpl implements UtilDelegate {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");

   public RemoteException mapSystemException(SystemException ex) {
      return CorbaUtils.mapSystemException(ex);
   }

   public void writeAny(OutputStream out, Object obj) {
      if (out instanceof IIOPOutputStream) {
         ((IIOPOutputStream)out).writeAny(obj);
      } else {
         UtilDelegateImpl.Singleton.delegate.writeAny(out, obj);
      }

   }

   public Object readAny(InputStream in) {
      return in instanceof IIOPInputStream ? ((IIOPInputStream)in).readAny() : UtilDelegateImpl.Singleton.delegate.readAny(in);
   }

   public void writeRemoteObject(OutputStream out, Object obj) {
      if (out instanceof IIOPOutputStream) {
         try {
            IIOPReplacer.getIIOPReplacer().replaceRemote(obj).write((IIOPOutputStream)out);
         } catch (IOException var4) {
            throw new MARSHAL("IOException writing RemoteObject " + var4.getMessage());
         }
      } else {
         UtilDelegateImpl.Singleton.delegate.writeRemoteObject(out, obj);
      }

   }

   public void writeAbstractObject(OutputStream out, Object obj) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("writeAbstractObject(OutputStream, " + obj + ")");
      }

      try {
         ((org.omg.CORBA_2_3.portable.OutputStream)out).write_abstract_interface(IIOPReplacer.getIIOPReplacer().replaceObject(obj));
      } catch (IOException var4) {
         throw new MARSHAL("IOException writing AbstractObject " + var4.getMessage());
      }
   }

   public void registerTarget(Tie tie, Remote target) {
   }

   public void unexportObject(Remote target) {
   }

   public Tie getTie(Remote target) {
      return null;
   }

   public ValueHandler createValueHandler() {
      return UtilDelegateImpl.Singleton.delegate.createValueHandler();
   }

   public String getCodebase(Class c) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("getCodebase for " + c);
      }

      return UtilDelegateImpl.Singleton.delegate.getCodebase(c);
   }

   public Class loadClass(String className, String remoteCodebase, ClassLoader loadingContext) throws ClassNotFoundException {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("className = " + className + " remoteCodebase = " + remoteCodebase + " loadingContext = " + loadingContext);
      }

      return CorbaUtils.loadClass(className, remoteCodebase, loadingContext);
   }

   public boolean isLocal(Stub stub) throws RemoteException {
      try {
         Delegate delegate = stub._get_delegate();
         return delegate instanceof IORDelegate && IiopConfigurationFacade.isLocal(((IORDelegate)delegate).getIOR());
      } catch (SystemException var3) {
         throw Util.mapSystemException(var3);
      }
   }

   public RemoteException wrapException(Throwable orig) {
      return UtilDelegateImpl.Singleton.delegate.wrapException(orig);
   }

   public Object[] copyObjects(Object[] o, ORB orb) throws RemoteException {
      return UtilDelegateImpl.Singleton.delegate.copyObjects(o, orb);
   }

   public Object copyObject(Object o, ORB orb) throws RemoteException {
      return UtilDelegateImpl.Singleton.delegate.copyObject(o, orb);
   }

   private static void p(String msg) {
      System.err.println("<UtilDelegateImpl>: " + msg);
   }

   private static class Singleton {
      static final UtilDelegate delegate = new com.sun.corba.ee.impl.javax.rmi.CORBA.Util();
   }
}
