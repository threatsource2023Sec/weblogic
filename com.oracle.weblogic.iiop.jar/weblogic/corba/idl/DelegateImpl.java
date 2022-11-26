package weblogic.corba.idl;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.Iterator;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.Utils;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.spi.MessageHolder;
import weblogic.iiop.spi.ServerReferenceDelegate;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;
import weblogic.utils.collections.NumericKeyHashMap;

class DelegateImpl extends Delegate implements IORDelegate, ServerReferenceDelegate {
   private static final boolean ENABLE_SERVANT_PREINVOKE = Boolean.getBoolean("weblogic.iiop.enableServantPreInvoke");
   private IOR ior;
   private NumericKeyHashMap policies;

   public DelegateImpl(IOR ior) {
      this.ior = ior;
   }

   public final IOR getIOR() {
      return this.ior;
   }

   public ORB orb(Object obj) {
      return weblogic.corba.orb.ORB.getInstance();
   }

   protected DelegateImpl(DelegateImpl d) {
      this.ior = d.ior;
      this.policies = d.policies;
   }

   protected Delegate copy() {
      return new DelegateImpl(this);
   }

   public int hash(Object self, int param2) {
      return this.ior.hashCode();
   }

   public Object duplicate(Object self) {
      return new IIOPReplacer.AnonymousCORBAStub(this.ior.getTypeId().toString(), this.copy());
   }

   public void release(Object self) {
   }

   public boolean is_a(Object self, String repository_id) {
      if (this.ior.getTypeId().toString().equals(repository_id)) {
         return true;
      } else {
         String[] ids = ((org.omg.CORBA.portable.ObjectImpl)self)._ids();

         for(int i = 0; i < ids.length; ++i) {
            if (ids[i].equals(repository_id)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean is_local(Object self) {
      return true;
   }

   public boolean non_existent(Object self) {
      return false;
   }

   public boolean is_equivalent(Object self, Object other) {
      if (other == self) {
         return true;
      } else {
         org.omg.CORBA.portable.ObjectImpl obj = (org.omg.CORBA.portable.ObjectImpl)other;
         return obj._get_delegate() instanceof DelegateImpl ? ((DelegateImpl)obj._get_delegate()).getIOR().equals(this.ior) : false;
      }
   }

   public Policy get_policy(Object self, int policy_type) {
      Policy policy = this.getPolicy(policy_type);
      if (policy == null) {
         throw new BAD_PARAM("No associated policy");
      } else {
         return policy;
      }
   }

   protected Policy getPolicy(int policyType) {
      return this.policies == null ? null : (Policy)this.policies.get((long)policyType);
   }

   public Object set_policy_override(Object self, Policy[] p, SetOverrideType set_add) {
      org.omg.CORBA.portable.ObjectImpl newref = (org.omg.CORBA.portable.ObjectImpl)self._duplicate();
      DelegateImpl delegate = (DelegateImpl)newref._get_delegate();
      delegate.policies = new NumericKeyHashMap();
      if (set_add.value() == 1 && this.policies != null) {
         Iterator i = this.policies.values().iterator();

         while(i.hasNext()) {
            Policy policy = (Policy)i.next();
            delegate.policies.put((long)policy.policy_type(), policy);
         }
      }

      for(int i = 0; i < p.length; ++i) {
         delegate.policies.put((long)p[i].policy_type(), p[i]);
      }

      return newref;
   }

   public OutputStream request(Object self, String operation, boolean responseExpected) {
      try {
         CollocatedRequest request = new CollocatedRequest(this.getServerReference(), operation, responseExpected, ObjectKey.getObjectKey(this.ior).getActivationID());
         return request.getOutputStream();
      } catch (IOException var5) {
         throw Utils.mapToCORBAException(var5);
      }
   }

   public void releaseReply(Object self, InputStream input) {
      if (input != null) {
         try {
            input.close();
         } catch (IOException var4) {
            throw Utils.mapToCORBAException(var4);
         }
      }
   }

   public InputStream invoke(Object self, OutputStream out) throws ApplicationException, RemarshalException {
      try {
         CollocatedRequest request = (CollocatedRequest)((MessageHolder)out).getMessage();
         request.invoke();
         return request.responseExpected() ? request.getInputStream() : null;
      } catch (IOException var4) {
         throw Utils.mapToCORBAException(var4);
      } catch (ApplicationException var5) {
         throw var5;
      } catch (RemarshalException var6) {
         throw var6;
      } catch (Exception var7) {
         throw (SystemException)(new OBJ_ADAPTER()).initCause(var7);
      }
   }

   public Request request(Object self, String param2) {
      throw new NO_IMPLEMENT();
   }

   public Request create_request(Object self, Context param2, String param3, NVList param4, NamedValue param5, ExceptionList param6, ContextList param7) {
      throw new NO_IMPLEMENT();
   }

   public Request create_request(Object self, Context param2, String param3, NVList param4, NamedValue param5) {
      throw new NO_IMPLEMENT();
   }

   public Object get_interface_def(Object c) {
      throw new NO_IMPLEMENT();
   }

   public String toString() {
      return "Delegate(" + System.identityHashCode(this) + ") [" + this.ior.toString() + "]";
   }

   public ServantObject servant_preinvoke(Object self, String operation, Class expectedType) {
      if (!ENABLE_SERVANT_PREINVOKE) {
         return null;
      } else {
         try {
            ServerReference sref = this.getServerReference();
            ServantObject impl = new ServantObject();
            if (sref instanceof ActivatableServerReference) {
               impl.servant = ((ActivatableServerReference)sref).getImplementation(ObjectKey.getObjectKey(this.ior).getActivationID());
            } else {
               impl.servant = sref.getImplementation();
            }

            return impl;
         } catch (IOException var6) {
            throw Utils.mapToCORBAException(var6);
         }
      }
   }

   public ServerReference getServerReference() throws NoSuchObjectException {
      return OIDManager.getInstance().getServerReference(ObjectKey.getObjectKey(this.ior).getObjectID());
   }
}
