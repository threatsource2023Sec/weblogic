package weblogic.corba.idl.poa;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantLocator;
import org.omg.PortableServer.POAManagerPackage.State;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
import weblogic.corba.idl.CorbaServerRef;
import weblogic.corba.utils.CorbaUtils;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkManager;

public final class POAServerRef extends BasicServerRef implements ActivatableServerReference {
   private static final String OPERATIONS = "Operations";
   private static final String TIE_CLASS_SUFFIX = "POATie";
   private static HashMap objectMethods = new HashMap();
   private POAImpl poa;

   public POAServerRef(POAImpl poa) throws RemoteException {
      super(poa);
      this.incrementRefCount();
      this.poa = poa;
      CorbaServerRef.setDelegate(poa, this.getObjectID());
   }

   public static Servant getTie(Object impl, POAImpl poa) throws NoSuchObjectException {
      if (impl instanceof Servant) {
         return (Servant)impl;
      } else {
         Class delegateArg = CorbaServerRef.getOperationsClass(impl);
         if (delegateArg == null) {
            throw new NoSuchObjectException("Couldn't find Tie for class: " + impl.getClass().getName());
         } else {
            String name = delegateArg.getName();
            String tieName = name.substring(0, name.length() - "Operations".length()) + "POATie";

            try {
               Class tieClass = CorbaUtils.loadClass(tieName, (String)null, impl.getClass().getClassLoader());
               Constructor tieCons = tieClass.getDeclaredConstructor(delegateArg, POA.class);
               return (Servant)tieCons.newInstance(impl, poa);
            } catch (NoSuchMethodException var7) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var7.getMessage())).initCause(var7);
            } catch (InstantiationException var8) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var8.getMessage())).initCause(var8);
            } catch (InvocationTargetException var9) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var9.getTargetException().getMessage())).initCause(var9);
            } catch (IllegalAccessException var10) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var10.getMessage())).initCause(var10);
            } catch (ClassNotFoundException var11) {
               throw new NoSuchObjectException("Couldn't load Tie class: " + tieName);
            }
         }
      }
   }

   public StubReference getStubReference() {
      throw new UnsupportedOperationException("getStubReference()");
   }

   public void invoke(RuntimeMethodDescriptor ignored, InboundRequest request, OutboundResponse response) throws Exception {
      try {
         weblogic.iiop.server.InboundRequest iioprequest = (weblogic.iiop.server.InboundRequest)request;
         ResponseHandler rh;
         if (response == null) {
            rh = CorbaServerRef.NULL_RESPONSE;
         } else {
            rh = ((weblogic.iiop.server.OutboundResponse)response).createResponseHandler(iioprequest);
         }

         State state = this.poa.the_POAManager().get_state();
         if (!state.equals(State.DISCARDING)) {
            if (state.equals(State.INACTIVE)) {
               throw new OBJECT_NOT_EXIST("POAManager not active");
            } else {
               byte[] oid = (byte[])((byte[])request.getActivationID());
               Object cookie = null;
               Servant delegate;
               if (this.poa.getServantManager() instanceof ServantLocator) {
                  CookieHolder holder = new CookieHolder();

                  try {
                     delegate = ((ServantLocator)this.poa.getServantManager()).preinvoke(oid, this.poa, iioprequest.getMethod(), holder);
                  } catch (ForwardRequest var12) {
                     throw new NO_IMPLEMENT("ForwardRequest()");
                  }

                  cookie = holder.value;
               } else {
                  delegate = this.poa.id_to_servant(oid);
               }

               Integer m = (Integer)objectMethods.get(iioprequest.getMethod());
               if (m != null) {
                  if (m == 4) {
                     this.poa.deactivate_object(oid);
                  } else {
                     invokeObjectMethod(m, iioprequest.getInputStream(), rh, delegate);
                  }
               } else {
                  ((InvokeHandler)delegate)._invoke(iioprequest.getMethod(), iioprequest.getInputStream(), rh);
               }

               if (this.poa.getServantManager() instanceof ServantLocator) {
                  ((ServantLocator)this.poa.getServantManager()).postinvoke(oid, this.poa, iioprequest.getMethod(), cookie, delegate);
               }

            }
         }
      } catch (ClassCastException var13) {
         throw new NoSuchObjectException("CORBA ties are only supported with IIOP");
      }
   }

   protected WorkManager getWorkManager(RuntimeMethodDescriptor md, AuthenticatedSubject subject) {
      return ((POAManagerImpl)this.poa.the_POAManager()).getWorkManager();
   }

   protected static OutputStream invokeObjectMethod(Integer method, InputStream in, ResponseHandler rh, Servant impl) {
      OutputStream out = null;
      boolean result;
      switch (method) {
         case 0:
            result = impl._is_a(in.read_string());
            rh.createReply().write_boolean(result);
            break;
         case 1:
         default:
            throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
         case 2:
            result = impl._non_existent();
            rh.createReply().write_boolean(result);
            break;
         case 3:
            org.omg.CORBA.Object obj = impl._get_interface_def();
            rh.createReply().write_Object(obj);
      }

      return (OutputStream)out;
   }

   public Object getImplementation(Object aid) throws RemoteException {
      if (aid == null) {
         return this.getImplementation();
      } else {
         try {
            return this.poa.id_to_servant((byte[])((byte[])aid));
         } catch (ObjectNotActive var3) {
            throw new RemoteException(var3.getMessage(), var3);
         } catch (WrongPolicy var4) {
            throw new RemoteException(var4.getMessage(), var4);
         }
      }
   }

   public StubReference getStubReference(Object id) {
      throw new UnsupportedOperationException("getStubReference()");
   }

   public Activator getActivator() {
      throw new UnsupportedOperationException("getActivator()");
   }

   static {
      objectMethods.put("_is_a", new Integer(0));
      objectMethods.put("_is_equivalent", new Integer(1));
      objectMethods.put("_non_existent", new Integer(2));
      objectMethods.put("_interface", new Integer(3));
      objectMethods.put("_release", new Integer(4));
   }
}
