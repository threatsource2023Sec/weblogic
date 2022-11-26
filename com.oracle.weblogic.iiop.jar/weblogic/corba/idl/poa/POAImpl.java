package weblogic.corba.idl.poa;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.NamingException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA_2_3.portable.ObjectImpl;
import org.omg.PortableInterceptor.PolicyFactory;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;
import org.omg.PortableServer.ServantLocator;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import weblogic.corba.idl.DelegateFactory;
import weblogic.corba.policies.IdAssignmentPolicyImpl;
import weblogic.corba.policies.IdUniquenessPolicyImpl;
import weblogic.corba.policies.ImplicitActivationPolicyImpl;
import weblogic.corba.policies.LifespanPolicyImpl;
import weblogic.corba.policies.PolicyImpl;
import weblogic.corba.policies.RequestProcessingPolicyImpl;
import weblogic.corba.policies.ServantRetentionPolicyImpl;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.InvocationHandlerFactory;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.server.ior.ServerIORBuilder;
import weblogic.jndi.Environment;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.cluster.ClusterableActivatableServerRef;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.utils.Debug;
import weblogic.utils.collections.NumericKeyHashMap;

public class POAImpl extends ObjectImpl implements POA, PolicyFactory {
   private static final boolean DEBUG = false;
   private static int nextOID = 0;
   private static final int POFF = 16;
   private final String name;
   private final int[] policyValues = new int[8];
   private final HashMap children = new HashMap();
   private final HashMap aom = new HashMap();
   private final HashMap asm = new HashMap();
   private final NumericKeyHashMap policies = new NumericKeyHashMap();
   private final POA parent;
   private final POAManager manager;
   private Servant defaultServant;
   private ServantManager servantManager;
   private int objectId = -1;
   private String applicationName;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public POAImpl(String name) {
      this.name = name;
      this.parent = null;
      this.manager = new POAManagerImpl(this.composeName());
      this.setDefaultPolicies();
   }

   public POAImpl(String name, Policy[] policies, POA parent, POAManager manager) throws InvalidPolicy {
      this.name = name;
      this.parent = parent;
      if (manager == null) {
         this.manager = new POAManagerImpl(this.composeName());
      } else {
         this.manager = manager;
      }

      this.setDefaultPolicies();

      for(int i = 0; i < policies.length; ++i) {
         PolicyImpl p = (PolicyImpl)policies[i];
         if (p.policy_type() >= 16 && p.policy_type() <= 24) {
            this.setPolicy(p.policy_type(), p.policy_value());
         } else {
            this.policies.put((long)p.policy_type(), p);
         }
      }

   }

   private void setPolicy(int id, int value) throws InvalidPolicy {
      if (id >= 16 && id <= 24) {
         this.policyValues[id - 16] = value;
      } else {
         throw new InvalidPolicy();
      }
   }

   private int getPolicy(int id) {
      Debug.assertion(id >= 16 && id < 24);
      return this.policyValues[id - 16];
   }

   private void setDefaultPolicies() {
      try {
         this.setPolicy(16, 0);
         this.setPolicy(17, 0);
         this.setPolicy(18, 0);
         this.setPolicy(19, 1);
         this.setPolicy(21, 0);
         this.setPolicy(22, 0);
         this.setPolicy(20, 0);
      } catch (InvalidPolicy var2) {
      }

   }

   public byte[] id() {
      return POAHelper.id().getBytes();
   }

   public synchronized void destroy(boolean etherealize_objects, boolean wait_for_completion) {
      synchronized(this.children) {
         HashMap clonedChildren = (HashMap)this.children.clone();
         Iterator i = clonedChildren.values().iterator();

         while(true) {
            if (!i.hasNext()) {
               break;
            }

            ((POA)i.next()).destroy(etherealize_objects, wait_for_completion);
         }
      }

      ((POAImpl)this.parent).destroyChild(this.name);
      ServerReference sref = OIDManager.getInstance().getServerReference(this);
      OIDManager.getInstance().removeServerReference(sref);
      if (KernelStatus.isServer() && sref.getDescriptor().isClusterable()) {
         try {
            SecurityManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
               public Object run() throws NamingException {
                  Environment env = new Environment();
                  env.setCreateIntermediateContexts(true);
                  env.getInitialContext().unbind(POAImpl.this.composeName() + "/thePOA");
                  return null;
               }
            });
         } catch (PrivilegedActionException var7) {
            throw (BAD_PARAM)(new BAD_PARAM(var7.getException().getMessage(), 1330446346, CompletionStatus.COMPLETED_NO)).initCause(var7.getException());
         }
      }

      if (this.servantManager instanceof ServantActivator) {
         Iterator i = this.aom.entrySet().iterator();

         while(i.hasNext()) {
            Map.Entry e = (Map.Entry)i.next();
            ((ServantActivator)this.servantManager).etherealize(((String)e.getKey()).getBytes(), this, (Servant)e.getValue(), etherealize_objects, false);
         }
      }

   }

   private void destroyChild(String name) {
      synchronized(this.children) {
         if (this.children.remove(name) == null) {
            throw new BAD_PARAM(name);
         }
      }
   }

   public void deactivate_object(byte[] oid) throws ObjectNotActive, WrongPolicy {
      synchronized(this.aom) {
         Servant servant = (Servant)this.aom.remove(new String(oid, 0));
         if (servant == null) {
            throw new ObjectNotActive();
         } else {
            this.asm.remove(servant);
            if (this.servantManager instanceof ServantActivator) {
               ((ServantActivator)this.servantManager).etherealize(oid, this, servant, false, false);
            }

         }
      }
   }

   public String the_name() {
      return this.name;
   }

   public byte[] reference_to_id(org.omg.CORBA.Object reference) throws WrongAdapter, WrongPolicy {
      if (!(reference instanceof ObjectImpl)) {
         throw new WrongAdapter("Not an ObjectImpl");
      } else {
         ObjectImpl impl = (ObjectImpl)reference;
         Delegate delegate = impl._get_delegate();
         if (!(delegate instanceof IORDelegate)) {
            throw new WrongAdapter("Not an IORDelegate");
         } else {
            ObjectKey key = ObjectKey.getObjectKey(((IORDelegate)delegate).getIOR());
            if (key.getObjectID() == this.objectId && key.getActivationID() != null) {
               return (byte[])((byte[])key.getActivationID());
            } else {
               throw new WrongAdapter("Wrong Object Key");
            }
         }
      }
   }

   public org.omg.CORBA.Object id_to_reference(byte[] oid) throws ObjectNotActive, WrongPolicy {
      synchronized(this.aom) {
         Servant servant = (Servant)this.aom.get(new String(oid, 0));
         if (servant == null) {
            throw new ObjectNotActive("No active object: " + new String(oid, 0));
         } else {
            org.omg.CORBA.Object var10000;
            try {
               var10000 = InvocationHandlerFactory.makeInvocationHandler((IOR)IIOPReplacer.getReplacer().replaceObject(servant));
            } catch (IOException var6) {
               throw (INTERNAL)(new INTERNAL("id_to_reference()")).initCause(var6);
            }

            return var10000;
         }
      }
   }

   public AdapterActivator the_activator() {
      throw new NO_IMPLEMENT("the_activator");
   }

   public void the_activator(AdapterActivator newThe_activator) {
      throw new NO_IMPLEMENT("the_activator");
   }

   public POAManager the_POAManager() {
      return this.manager;
   }

   public Servant get_servant() throws NoServant, WrongPolicy {
      if (this.defaultServant == null) {
         throw new NoServant();
      } else if (this.getPolicy(22) != 1) {
         throw new WrongPolicy("RequestProcessingPolicyValue: " + this.getPolicy(22));
      } else {
         return this.defaultServant;
      }
   }

   public void set_servant(Servant p_servant) throws WrongPolicy {
      if (this.getPolicy(22) != 1) {
         throw new WrongPolicy("RequestProcessingPolicyValue: " + this.getPolicy(22));
      } else {
         this.defaultServant = p_servant;
      }
   }

   public byte[] activate_object(Servant p_servant) throws ServantAlreadyActive, WrongPolicy {
      synchronized(this.aom) {
         if (this.getPolicy(18) == 0 && this.aom.containsValue(p_servant)) {
            throw new ServantAlreadyActive();
         } else if (this.getPolicy(19) == 1 && this.getPolicy(21) == 0) {
            String oid = Integer.toString(this.getNextObjectID());
            p_servant._set_delegate(this.createDelegate(p_servant, oid.getBytes()));
            this.aom.put(oid, p_servant);
            this.asm.put(p_servant, oid);
            return oid.getBytes();
         } else {
            throw new WrongPolicy();
         }
      }
   }

   public synchronized int getNextObjectID() {
      return ++nextOID;
   }

   public byte[] servant_to_id(Servant p_servant) throws ServantNotActive, WrongPolicy {
      if (this.getPolicy(22) != 1 && (this.getPolicy(21) != 0 || this.getPolicy(18) != 0 && this.getPolicy(20) != 0)) {
         throw new WrongPolicy("servant_to_id()");
      } else {
         synchronized(this.aom) {
            String s;
            if (this.getPolicy(18) == 0 && (s = (String)this.asm.get(p_servant)) != null) {
               return s.getBytes();
            } else if (this.getPolicy(20) != 0 || this.getPolicy(18) != 1 && this.asm.get(p_servant) != null) {
               if (this.getPolicy(22) == 1 && this.defaultServant != null && p_servant.equals(this.defaultServant)) {
                  throw new NO_IMPLEMENT("servant_to_id()");
               } else {
                  throw new ServantNotActive("servant_to_id: " + p_servant.toString());
               }
            } else {
               byte[] var10000;
               try {
                  var10000 = this.activate_object(p_servant);
               } catch (ServantAlreadyActive var6) {
                  throw (AssertionError)(new AssertionError()).initCause(var6);
               }

               return var10000;
            }
         }
      }
   }

   public Servant id_to_servant(byte[] oid) throws ObjectNotActive, WrongPolicy {
      if (this.getPolicy(21) == 0) {
         synchronized(this.aom) {
            Servant s = (Servant)this.aom.get(new String(oid, 0));
            if (s == null) {
               if (this.getPolicy(22) != 2) {
                  throw new ObjectNotActive(new String(oid, 0));
               }

               try {
                  s = ((ServantActivator)this.servantManager).incarnate(oid, this);
               } catch (ForwardRequest var6) {
                  throw new NO_IMPLEMENT("ForwardRequest");
               }
            }

            return s;
         }
      } else if (this.getPolicy(22) == 1) {
         if (this.defaultServant == null) {
            throw new ObjectNotActive("No default Servant registered");
         } else {
            return this.defaultServant;
         }
      } else {
         throw new WrongPolicy("Neither RETAIN or USE_DEFAULT_SERVANT were specified");
      }
   }

   public void activate_object_with_id(byte[] id, Servant p_servant) throws ServantAlreadyActive, ObjectAlreadyActive, WrongPolicy {
      synchronized(this.aom) {
         String aomid = new String(id, 0);
         if (this.getPolicy(18) == 0 && this.aom.get(aomid) != null) {
            throw new ServantAlreadyActive();
         } else if (this.aom.containsValue(p_servant)) {
            throw new ObjectAlreadyActive();
         } else if (this.getPolicy(21) != 0) {
            throw new WrongPolicy();
         } else {
            p_servant._set_delegate(this.createDelegate(p_servant, id));
            this.aom.put(aomid, p_servant);
            this.asm.put(p_servant, aomid);
         }
      }
   }

   private org.omg.PortableServer.portable.Delegate createDelegate(Servant servant, byte[] oid) {
      String typeid = servant._all_interfaces(this, oid)[0];
      ServerIORBuilder builder = this.createServerIORBuilder(typeid, oid);
      RemoteInfo rinfo = this.getRemoteInfo(servant, typeid);
      return new POADelegateImpl(this, this.createIOR(builder, typeid, rinfo.getDescriptor()), rinfo, oid);
   }

   private ServerIORBuilder createServerIORBuilder(String typeid, byte[] oid) {
      ServerChannel channel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerIIOP.PROTOCOL_IIOP);
      ServerIORBuilder builder = ServerIORBuilder.createBuilder(typeid, channel.getPublicAddress(), channel.getPublicPort());
      ObjectKey oKey = ObjectKey.createActivatableObjectKey(typeid, this.objectId, oid);
      builder.setKey(oKey);
      return builder;
   }

   private IOR createIOR(ServerIORBuilder builder, String typeid, RuntimeDescriptor descriptor) {
      return descriptor != null ? builder.createWithRuntimeDescriptor(descriptor) : builder.createWithPoaPolicies(this.policies);
   }

   private RemoteInfo getRemoteInfo(Servant servant, String typeid) {
      RepositoryId repid = new RepositoryId(typeid);
      return RemoteInfo.findRemoteInfo(repid, servant.getClass());
   }

   private IOR createIOR(String typeid, byte[] oid) {
      return this.createServerIORBuilder(typeid, oid).createWithPoaPolicies(this.policies);
   }

   public ServantManager get_servant_manager() throws WrongPolicy {
      if (this.getPolicy(22) != 2) {
         throw new WrongPolicy("RequestProcessingPolicyValue: " + this.getPolicy(22));
      } else {
         return this.servantManager;
      }
   }

   public void set_servant_manager(ServantManager imgr) throws WrongPolicy {
      if (this.servantManager != null) {
         throw new BAD_INV_ORDER("ServantManager already set");
      } else if (imgr == null || this.getPolicy(21) == 0 && !(imgr instanceof ServantActivator) || this.getPolicy(21) != 0 && !(imgr instanceof ServantLocator)) {
         throw new OBJ_ADAPTER("Wrong ServantManager");
      } else if (this.getPolicy(22) != 2) {
         throw new WrongPolicy("RequestProcessingPolicyValue: " + this.getPolicy(22));
      } else {
         this.servantManager = imgr;
      }
   }

   public ServantManager getServantManager() {
      return this.servantManager;
   }

   public org.omg.CORBA.Object create_reference(String intf) throws WrongPolicy {
      if (this.getPolicy(19) != 1) {
         throw new WrongPolicy("create_reference() requires SYSTEM_ID");
      } else {
         byte[] oid = Integer.toString(this.getNextObjectID()).getBytes();
         IOR ior = this.createIOR(intf, oid);
         return new IIOPReplacer.AnonymousCORBAStub(intf, DelegateFactory.createDelegate(ior));
      }
   }

   public org.omg.CORBA.Object create_reference_with_id(byte[] oid, String intf) {
      if (this.getPolicy(19) == 1) {
         try {
            Integer.parseInt(new String(oid, 0));
         } catch (NumberFormatException var4) {
            throw new BAD_PARAM("create_reference_with_id() requires system id for SYSTEM_ID");
         }
      }

      IOR ior = this.createIOR(intf, oid);
      return new IIOPReplacer.AnonymousCORBAStub(intf, DelegateFactory.createDelegate(ior));
   }

   public org.omg.CORBA.Object servant_to_reference(Servant p_servant) throws ServantNotActive, WrongPolicy {
      if (this.getPolicy(21) == 0 && (this.getPolicy(18) == 0 || this.getPolicy(20) == 0)) {
         synchronized(this.aom) {
            if (this.getPolicy(18) == 0 && this.asm.get(p_servant) != null) {
               return p_servant._this_object();
            } else if (this.getPolicy(20) == 0 && (this.getPolicy(18) == 1 || this.asm.get(p_servant) == null)) {
               org.omg.CORBA.Object var10000;
               try {
                  this.activate_object(p_servant);
                  var10000 = p_servant._this_object();
               } catch (ServantAlreadyActive var5) {
                  throw (AssertionError)(new AssertionError()).initCause(var5);
               }

               return var10000;
            } else {
               throw new ServantNotActive("servant_to_reference: " + p_servant.toString());
            }
         }
      } else {
         throw new WrongPolicy("servant_to_reference()");
      }
   }

   public Servant reference_to_servant(org.omg.CORBA.Object reference) throws ObjectNotActive, WrongPolicy, WrongAdapter {
      if (!(reference instanceof ObjectImpl)) {
         throw new WrongAdapter("Not an ObjectImpl");
      } else {
         ObjectImpl impl = (ObjectImpl)reference;
         Delegate delegate = impl._get_delegate();
         if (!(delegate instanceof IORDelegate)) {
            throw new WrongAdapter("Not an IORDelegate");
         } else {
            ObjectKey key = ObjectKey.getObjectKey(((IORDelegate)delegate).getIOR());
            if (key.getObjectID() == this.objectId && key.getActivationID() != null) {
               String oid = new String((byte[])((byte[])key.getActivationID()), 0);
               if (this.getPolicy(21) == 0) {
                  synchronized(this.aom) {
                     Servant s = (Servant)this.aom.get(oid);
                     if (s == null) {
                        throw new ObjectNotActive(oid);
                     } else {
                        return s;
                     }
                  }
               } else if (this.getPolicy(22) == 1) {
                  if (this.defaultServant == null) {
                     throw new ObjectNotActive("No default Servant registered");
                  } else {
                     return this.defaultServant;
                  }
               } else {
                  throw new WrongPolicy("Neither RETAIN or USE_DEFAULT_SERVANT were specified");
               }
            } else {
               throw new WrongAdapter("Wrong Object Key");
            }
         }
      }
   }

   public POA the_parent() {
      return this.parent;
   }

   public POA[] the_children() {
      synchronized(this.children) {
         return (POA[])((POA[])this.children.values().toArray(new POA[this.children.size()]));
      }
   }

   public POA create_POA(String adapter_name, POAManager a_POAManager, Policy[] policies) throws AdapterAlreadyExists, InvalidPolicy {
      try {
         synchronized(this.children) {
            if (this.children.get(adapter_name) != null) {
               throw new AdapterAlreadyExists(adapter_name);
            } else {
               POAImpl poa = new POAImpl(adapter_name, policies, this, a_POAManager);
               this.children.put(adapter_name, poa);
               return poa.export();
            }
         }
      } catch (IOException var8) {
         throw (BAD_PARAM)(new BAD_PARAM(var8.getMessage(), 1330446346, CompletionStatus.COMPLETED_NO)).initCause(var8);
      }
   }

   public POA export() throws IOException {
      if (this.objectId < 0) {
         synchronized(this) {
            if (this.objectId < 0) {
               ActivatableServerReference sref = new POAServerRef(this);
               if (((ActivatableServerReference)sref).getDescriptor().isClusterable()) {
                  sref = new ClusterableActivatableServerRef((ActivatableServerReference)sref);
               }

               this.objectId = ((ActivatableServerReference)sref).exportObject().getObjectID();
               if (KernelStatus.isServer() && ((ActivatableServerReference)sref).getDescriptor().isClusterable()) {
                  final String jndiName = this.composeName() + "/thePOA";

                  try {
                     SecurityManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
                        public Object run() throws NamingException {
                           Environment env = new Environment();
                           env.setCreateIntermediateContexts(true);
                           env.getInitialContext().bind(jndiName, this);
                           return null;
                        }
                     });
                  } catch (PrivilegedActionException var6) {
                     throw (IOException)(new IOException(var6.getException().getMessage())).initCause(var6.getException());
                  }

                  ((ClusterableActivatableServerRef)sref).initialize(jndiName);
               }
            }
         }
      }

      return this;
   }

   private String composeName() {
      return this.parent == null ? "weblogic/corba/" + this.name : ((POAImpl)this.parent).composeName() + "/" + this.name;
   }

   public POA find_POA(String adapter_name, boolean activate_it) throws AdapterNonExistent {
      synchronized(this.children) {
         POA poa = (POA)this.children.get(adapter_name);
         if (poa == null) {
            throw new AdapterNonExistent();
         } else {
            return poa;
         }
      }
   }

   public IdAssignmentPolicy create_id_assignment_policy(IdAssignmentPolicyValue value) {
      return new IdAssignmentPolicyImpl(value.value());
   }

   public IdUniquenessPolicy create_id_uniqueness_policy(IdUniquenessPolicyValue value) {
      return new IdUniquenessPolicyImpl(value.value());
   }

   public ImplicitActivationPolicy create_implicit_activation_policy(ImplicitActivationPolicyValue value) {
      return new ImplicitActivationPolicyImpl(value.value());
   }

   public LifespanPolicy create_lifespan_policy(LifespanPolicyValue value) {
      return new LifespanPolicyImpl(value.value());
   }

   public RequestProcessingPolicy create_request_processing_policy(RequestProcessingPolicyValue value) {
      return new RequestProcessingPolicyImpl(value.value());
   }

   public ServantRetentionPolicy create_servant_retention_policy(ServantRetentionPolicyValue value) {
      return new ServantRetentionPolicyImpl(value.value());
   }

   public ThreadPolicy create_thread_policy(ThreadPolicyValue value) {
      return new ThreadPolicyImpl(value.value());
   }

   public Policy create_policy(int type, Any val) throws PolicyError {
      switch (type) {
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
            throw new PolicyError("POA policies are only supported via poa functions", (short)1);
         default:
            return null;
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof POAImpl)) {
         return false;
      } else {
         POAImpl poa = (POAImpl)o;
         return this.name.equals(poa.name);
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public String toString() {
      return "POA[" + this.name + ", " + this.objectId + "]";
   }

   public String[] _ids() {
      return new String[]{POAHelper.id()};
   }

   protected static void p(String s) {
      System.out.println("<POAImpl> " + s);
   }
}
