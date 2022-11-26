package weblogic.corba.idl;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Set;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import weblogic.corba.utils.RemoteInfo;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ior.IOR;

public class ObjectImpl implements Object, Remote, InvocationHandler {
   private static final boolean DEBUG = false;
   private RepositoryId typeid;
   private Set repIds = new HashSet();
   private Remote delegate;
   private IOR ior;

   public ObjectImpl(IOR ior) {
      this.ior = ior;
      this.delegate = null;
      this.typeid = ior.getTypeId();
      this.repIds.add(this.typeid.toString());
   }

   protected ObjectImpl() {
      this.delegate = this;
      this.typeid = RepositoryId.createFromRemote(this.getClass());
      this.repIds.addAll(RepositoryId.getRemoteIDs(this.getClass()));
   }

   protected ObjectImpl(String repId) {
      this.delegate = this;
      this.typeid = new RepositoryId(repId);
      this.repIds.add(this.typeid.toString());
   }

   public ObjectImpl(Remote delegate) {
      this.delegate = delegate;
      if (delegate != null) {
         this.typeid = RepositoryId.createFromRemote(delegate.getClass());
         this.repIds.addAll(RepositoryId.getRemoteIDs(delegate.getClass()));
      } else {
         this.typeid = RepositoryId.OBJECT;
         this.repIds.add(this.typeid.toString());
      }

   }

   public RepositoryId getTypeId() {
      return this.typeid;
   }

   public IOR getIOR() throws IOException {
      if (this.ior != null) {
         return this.ior;
      } else {
         return this.delegate != null && !(this.delegate instanceof ObjectImpl) ? IIOPReplacer.getIIOPReplacer().replaceRemote(this.delegate) : null;
      }
   }

   public java.lang.Object getRemote() throws IOException {
      if (this.delegate != null) {
         return this.delegate;
      } else if (this.ior != null) {
         IIOPReplacer.getIIOPReplacer();
         return IIOPReplacer.resolveObject(this.ior);
      } else {
         return null;
      }
   }

   public int hashCode() {
      if (this.ior != null) {
         return this.ior.hashCode();
      } else {
         return this.delegate != this ? this.delegate.hashCode() : this.typeid.hashCode();
      }
   }

   public boolean equals(java.lang.Object obj) {
      if (!(obj instanceof ObjectImpl)) {
         return false;
      } else {
         ObjectImpl other = (ObjectImpl)obj;
         return this.typeid.equals((java.lang.Object)other.typeid) && (this.ior == other.ior || (this.ior == null || this.ior.equals(other.ior)) && this.ior != null) && (this.delegate == this || this.delegate == other.delegate || this.delegate.equals(other.delegate));
      }
   }

   public boolean _is_a(String repository_id) {
      boolean result;
      if (repository_id != null && repository_id.length() != 0) {
         if (this.repIds.contains(repository_id)) {
            result = true;
         } else {
            RemoteInfo rinfo = RemoteInfo.findRemoteInfo(new RepositoryId(repository_id));
            result = rinfo != null && (rinfo.getTheClass().isAssignableFrom(this.getClass()) || this.delegate != null && rinfo.getTheClass().isAssignableFrom(this.delegate.getClass()));
         }
      } else {
         result = false;
      }

      return result;
   }

   public boolean _is_equivalent(Object other) {
      return other instanceof ObjectImpl && other.equals(this);
   }

   public boolean _non_existent() {
      return this.delegate == null;
   }

   public int _hash(int maximum) {
      return this.delegate != null ? this.delegate.hashCode() : this.hashCode();
   }

   public void _release() {
   }

   public Object _duplicate() {
      throw new NO_IMPLEMENT();
   }

   public Object _get_interface_def() {
      throw new NO_IMPLEMENT();
   }

   public Request _request(String operation) {
      throw new NO_IMPLEMENT();
   }

   public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
      throw new NO_IMPLEMENT();
   }

   public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exclist, ContextList ctxlist) {
      throw new NO_IMPLEMENT();
   }

   public Policy _get_policy(int policy_type) {
      throw new NO_IMPLEMENT();
   }

   public DomainManager[] _get_domain_managers() {
      throw new NO_IMPLEMENT();
   }

   public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
      throw new NO_IMPLEMENT();
   }

   public String toString() {
      return super.toString() + " delegate: " + (this.delegate == null ? "<null>" : this.delegate.getClass().getName()) + ", typeids: " + this.typeid + ", repIds: " + this.repIds;
   }

   public java.lang.Object invoke(java.lang.Object proxy, Method m, java.lang.Object[] args) throws Throwable {
      Class declaringClass = m.getDeclaringClass();
      if (declaringClass != Object.class && declaringClass != java.lang.Object.class) {
         if (declaringClass.isAssignableFrom(this.delegate.getClass())) {
            try {
               return m.invoke(this.delegate, args);
            } catch (InvocationTargetException var6) {
               throw var6.getTargetException();
            }
         }
      } else {
         m.invoke(this, args);
      }

      throw new InternalError("unexpected method: " + m);
   }

   protected static void p(String s) {
      System.err.println("<ObjectImpl> " + s);
   }
}
