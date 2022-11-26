package weblogic.corba.idl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.util.HashMap;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.IIOPRemoteRef;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.server.ior.ServerIORBuilder;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.rmi.utils.Utilities;

public final class CorbaServerRef extends BasicServerRef implements IORDelegate, IDLEntity {
   private static final long serialVersionUID = 6604979373837293000L;
   private static final String OPERATIONS = "Operations";
   private static final String TIE_CLASS_SUFFIX = "_Tie";
   private static HashMap objectMethods = new HashMap();
   public static final ResponseHandler NULL_RESPONSE;
   private InvokeHandler delegate;
   private StubReference stub;
   private RemoteInfo rinfo;

   public CorbaServerRef(Object impl) throws RemoteException {
      this(OIDManager.getInstance().getNextObjectID(), impl);
   }

   public CorbaServerRef(int oid, Object impl) throws RemoteException {
      super(oid, getTie(impl));
      this.initialize((InvokeHandler)this.getImplementation());
   }

   private void initialize(InvokeHandler delegate) {
      this.delegate = delegate;
      this.incrementRefCount();
      org.omg.CORBA.portable.ObjectImpl oimpl = (org.omg.CORBA.portable.ObjectImpl)delegate;
      this.rinfo = setDelegate(oimpl, this.getObjectID());
   }

   public static RemoteInfo setDelegate(org.omg.CORBA.portable.ObjectImpl oimpl, int oid) {
      String typeid = oimpl._ids()[0];
      ServerChannel channel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerIIOP.PROTOCOL_IIOP);
      ServerIORBuilder builder = createIorBuilder(typeid, channel);
      builder.setKey(ObjectKey.createTransientObjectKey(typeid, oid, LocalServerIdentity.getIdentity()));
      builder.setApplicationName(Utilities.getAnnotationString(oimpl));
      RemoteInfo rinfo = getRemoteInfo(oimpl, typeid);
      IOR ior = builder.createWithRuntimeDescriptor(rinfo.getDescriptor());
      oimpl._set_delegate(new DelegateImpl(ior));
      return rinfo;
   }

   private static ServerIORBuilder createIorBuilder(String typeId, ServerChannel channel) {
      return channel == null ? ServerIORBuilder.createBuilder(typeId, "localhost", -1) : ServerIORBuilder.createBuilder(typeId, channel.getPublicAddress(), channel.getPublicPort());
   }

   private static RemoteInfo getRemoteInfo(org.omg.CORBA.portable.ObjectImpl oimpl, String typeid) {
      RepositoryId repid = new RepositoryId(typeid);
      return RemoteInfo.findRemoteInfo(repid, oimpl.getClass());
   }

   private static InvokeHandler getTie(Object impl) throws NoSuchObjectException {
      if (impl instanceof InvokeHandler) {
         return (InvokeHandler)impl;
      } else {
         Class delegateArg = getOperationsClass(impl);
         if (delegateArg == null) {
            throw new NoSuchObjectException("Couldn't find Tie for class: " + impl.getClass().getName());
         } else {
            String name = delegateArg.getName();
            String tieName = name.substring(0, name.length() - "Operations".length()) + "_Tie";

            try {
               Class tieClass = CorbaUtils.loadClass(tieName, (String)null, impl.getClass().getClassLoader());
               Constructor tieCons = tieClass.getDeclaredConstructor(delegateArg);
               return (InvokeHandler)tieCons.newInstance(impl);
            } catch (NoSuchMethodException var6) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var6.getMessage())).initCause(var6);
            } catch (InstantiationException var7) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var7.getMessage())).initCause(var7);
            } catch (InvocationTargetException var8) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var8.getTargetException().getMessage())).initCause(var8);
            } catch (IllegalAccessException var9) {
               throw (NoSuchObjectException)(new NoSuchObjectException(var9.getMessage())).initCause(var9);
            } catch (ClassNotFoundException var10) {
               throw new NoSuchObjectException("Couldn't load Tie class: " + tieName);
            }
         }
      }
   }

   public IOR getIOR() {
      return ((IORDelegate)((IORDelegate)((org.omg.CORBA.portable.ObjectImpl)this.delegate)._get_delegate())).getIOR();
   }

   public StubReference getStubReference() {
      if (this.stub == null) {
         this.stub = new StubInfo(new IIOPRemoteRef(this.getIOR(), this.rinfo), this.getDescriptor().getClientRuntimeDescriptor((String)null), (String)null, CorbaStub.class.getName());
      }

      return this.stub;
   }

   public void invoke(RuntimeMethodDescriptor notused, InboundRequest request, OutboundResponse response) throws Exception {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_CORBA_SERVER_REF_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var7 = null;

         try {
            weblogic.iiop.server.InboundRequest iioprequest = (weblogic.iiop.server.InboundRequest)request;
            if (!iioprequest.isCollocated() && iioprequest.getEndPoint().isDead()) {
               throw new ConnectException("Connection is already shutdown for " + request);
            }

            Integer m = (Integer)objectMethods.get(iioprequest.getMethod());
            ResponseHandler rh;
            if (response == null) {
               rh = NULL_RESPONSE;
            } else {
               rh = ((weblogic.iiop.server.OutboundResponse)response).createResponseHandler(iioprequest);
            }

            if (m != null) {
               this.invokeObjectMethod(m, iioprequest.getInputStream(), rh);
            } else {
               this.delegate._invoke(iioprequest.getMethod(), iioprequest.getInputStream(), rh);
            }

            if (response != null) {
               response.transferThreadLocalContext(request);
            }
         } catch (Throwable var19) {
            var7 = var19;
            throw var19;
         } finally {
            if (mic != null) {
               if (var7 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var18) {
                     var7.addSuppressed(var18);
                  }
               } else {
                  mic.close();
               }
            }

         }

      } catch (ClassCastException var21) {
         throw new NoSuchObjectException("CORBA ties are only supported with IIOP");
      }
   }

   protected OutputStream invokeObjectMethod(Integer method, InputStream in, ResponseHandler rh) {
      OutputStream out = null;
      org.omg.CORBA.portable.ObjectImpl impl = (org.omg.CORBA.portable.ObjectImpl)this.delegate;
      boolean result;
      switch (method) {
         case 0:
            result = impl._is_a(in.read_string());
            rh.createReply().write_boolean(result);
            break;
         case 1:
            result = impl._is_equivalent(in.read_Object());
            rh.createReply().write_boolean(result);
            break;
         case 2:
            result = impl._non_existent();
            rh.createReply().write_boolean(result);
            break;
         case 3:
            org.omg.CORBA.Object obj = impl._get_interface_def();
            rh.createReply().write_Object(obj);
            break;
         case 4:
            impl._release();
            break;
         default:
            throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
      }

      return (OutputStream)out;
   }

   public static Class getOperationsClass(Object impl) {
      for(Class cl = impl.getClass(); cl != null; cl = cl.getSuperclass()) {
         Class[] interfaces = cl.getInterfaces();

         for(int i = 0; i < interfaces.length; ++i) {
            String name = interfaces[i].getName();
            if (name.endsWith("Operations")) {
               return interfaces[i];
            }
         }
      }

      return null;
   }

   static {
      objectMethods.put("_is_a", new Integer(0));
      objectMethods.put("_is_equivalent", new Integer(1));
      objectMethods.put("_non_existent", new Integer(2));
      objectMethods.put("_interface", new Integer(3));
      objectMethods.put("_release", new Integer(4));
      NULL_RESPONSE = new ResponseHandler() {
         public OutputStream createReply() {
            return null;
         }

         public OutputStream createExceptionReply() {
            return null;
         }
      };
   }
}
