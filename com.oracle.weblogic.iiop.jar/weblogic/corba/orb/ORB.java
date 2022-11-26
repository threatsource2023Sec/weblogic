package weblogic.corba.orb;

import java.applet.Applet;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import javax.naming.NamingException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Current;
import org.omg.CORBA.DynAny;
import org.omg.CORBA.DynArray;
import org.omg.CORBA.DynEnum;
import org.omg.CORBA.DynSequence;
import org.omg.CORBA.DynStruct;
import org.omg.CORBA.DynUnion;
import org.omg.CORBA.Environment;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.Request;
import org.omg.CORBA.ServiceInformationHolder;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.UserException;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.ORBPackage.InconsistentTypeCode;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.portable.OutputStream;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.TimeBase.TimeTHelper;
import org.omg.TimeBase.UtcTHelper;
import weblogic.corba.cos.transactions.InvocationPolicyImpl;
import weblogic.corba.cos.transactions.OTSPolicyImpl;
import weblogic.corba.idl.AnyImpl;
import weblogic.corba.idl.EnvironmentImpl;
import weblogic.corba.idl.ExceptionListImpl;
import weblogic.corba.idl.NVListImpl;
import weblogic.corba.idl.NamedValueImpl;
import weblogic.corba.idl.TypeCodeImpl;
import weblogic.corba.idl.poa.POAImpl;
import weblogic.corba.j2ee.naming.NameParser;
import weblogic.corba.j2ee.naming.Utils;
import weblogic.corba.policies.RelativeRequestTimeoutPolicyImpl;
import weblogic.corba.policies.RelativeRoundtripTimeoutPolicyImpl;
import weblogic.corba.policies.ReplyEndTimePolicyImpl;
import weblogic.corba.policies.RequestEndTimePolicyImpl;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.IIOPClient;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.IORManager;
import weblogic.iiop.InvocationHandlerFactory;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.InitialReferences;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;

public class ORB extends org.omg.CORBA_2_3.ORB {
   public static boolean reconnectOnBootstrap = false;
   private String host;
   private String name;
   private int port;
   private Hashtable initialRefs;
   private ArrayList deferred;
   private String defaultInitialRef;
   private String protocol;
   private String channelName;
   private long timeout;
   private static final boolean DEBUG = false;
   private static final String ROOT_POA = "RootPOA";
   private POA rootPOA;

   public ORB() {
      this.port = -1;
      this.initialRefs = new Hashtable();
      this.deferred = new ArrayList();
      this.timeout = 0L;
      if (!IIOPClient.isEnabled()) {
         IIOPClient.initialize();
      }

   }

   private ORB(boolean notused) {
      this();
      this.set_parameters((String[])(new String[0]), (Properties)null);
   }

   public static final ORB getInstance() {
      return ORB.SingletonMaker.ORB;
   }

   public void set_delegate(Object servant) {
      try {
         ((POAImpl)this.getRootPOA()).activate_object((Servant)servant);
      } catch (Exception var3) {
      }

   }

   protected void set_parameters(String[] args, Properties props) {
      this.initialRefs.put("RootPOA", "RootPOA");
      String name;
      if (props != null) {
         try {
            this.host = props.getProperty("org.omg.CORBA.ORBInitialHost");
            this.protocol = props.getProperty("weblogic.corba.orb.ORBProtocol", "iiop");
            this.name = props.getProperty("weblogic.corba.orb.ORBName", Long.toHexString((long)this.hashCode()));
            this.channelName = props.getProperty("weblogic.jndi.provider.channel");
            this.port = Integer.parseInt(props.getProperty("org.omg.CORBA.ORBInitialPort", "-1"));
            this.timeout = Long.parseLong(props.getProperty("weblogic.jndi.requestTimeout", "0"));
            String initRef = props.getProperty("org.omg.CORBA.ORBInitRef");
            if (initRef != null) {
               int idx = initRef.indexOf(61);
               String name = initRef.substring(0, idx);
               name = initRef.substring(idx + 1);
               this.initialRefs.put(name, name);
            }

            this.defaultInitialRef = props.getProperty("org.omg.CORBA.ORBDefaultInitRef");
         } catch (IndexOutOfBoundsException var8) {
            throw new BAD_PARAM(var8.getMessage(), 1330446343, CompletionStatus.COMPLETED_NO);
         } catch (NumberFormatException var9) {
            throw new BAD_PARAM(var9.getMessage(), 1330446343, CompletionStatus.COMPLETED_NO);
         }
      } else {
         this.name = Long.toHexString((long)this.hashCode());
      }

      if (args != null) {
         for(int i = 0; i < args.length; ++i) {
            if (args[i].equals("-ORBDefaultInitRef")) {
               ++i;
               this.defaultInitialRef = args[i];
            } else if (args[i].equals("-ORBInitRef")) {
               ++i;
               String initRef = args[i];
               int idx = initRef.indexOf(61);
               name = initRef.substring(0, idx);
               String url = initRef.substring(idx + 1);
               this.initialRefs.put(name, url);
            }
         }
      }

   }

   protected void set_parameters(Applet app, Properties props) {
      this.set_parameters((String[])null, props);
   }

   public org.omg.CORBA.Object resolve_initial_references(String object_name) throws InvalidName {
      Object initRef = this.initialRefs.get(object_name);
      if (initRef == "RootPOA") {
         return this.getRootPOA();
      } else {
         org.omg.CORBA.Object ref = null;
         if (initRef instanceof org.omg.CORBA.Object) {
            ref = (org.omg.CORBA.Object)initRef;
         } else if (initRef instanceof String) {
            ref = this.string_to_object((String)initRef);
            this.cacheInitialReference(object_name, ref);
         } else if (this.defaultInitialRef != null) {
            ref = this.string_to_object(this.defaultInitialRef + "/" + object_name);
            this.cacheInitialReference(object_name, ref);
         } else if (this.host != null && this.port >= 0) {
            try {
               IOR ior = IORManager.createIOR(this.protocol, this.host, this.port, object_name, 1, 2);
               if (object_name.equals("NameService")) {
                  ior = IORManager.locateNameService(ior, this.timeout);
               } else {
                  ior = IORManager.locateInitialReference(ior, this.channelName, this.timeout);
               }

               ref = (org.omg.CORBA.Object)IIOPReplacer.resolveObject(ior);
               this.cacheInitialReference(object_name, ref);
            } catch (IOException var5) {
               throw new InvalidName();
            }
         } else {
            ref = InitialReferences.getInitialReferenceObject(object_name);
         }

         if (ref == null) {
            throw new InvalidName();
         } else {
            return ref;
         }
      }
   }

   private void cacheInitialReference(String name, org.omg.CORBA.Object ref) {
      if (!name.equals("NameService") || !reconnectOnBootstrap) {
         this.initialRefs.put(name, ref);
      }

   }

   public String[] list_initial_services() {
      return InitialReferences.getServiceList();
   }

   public String object_to_string(org.omg.CORBA.Object obj) {
      try {
         Object ior = IIOPReplacer.getIIOPReplacer().replaceObject(obj);
         return ior instanceof IOR ? ((IOR)ior).stringify() : null;
      } catch (IOException var3) {
         throw new BAD_PARAM(var3.getMessage(), 1330446346, CompletionStatus.COMPLETED_NO);
      }
   }

   public org.omg.CORBA.Object string_to_object(String str) {
      try {
         if (str.startsWith("IOR:")) {
            IOR ior = IOR.destringify(str);
            if ("iiops".equals(this.protocol)) {
               ior.getProfile().makeSecure();
            }

            Object resolved = IIOPReplacer.resolveObject(ior);
            if (!(resolved instanceof org.omg.CORBA.Object)) {
               resolved = InvocationHandlerFactory.makeInvocationHandler(ior);
            }

            return (org.omg.CORBA.Object)resolved;
         } else if (NameParser.isGIOPProtocol(str)) {
            org.omg.CORBA.Object obj = IORManager.createInitialReference(str, this.timeout);
            String ns = NameParser.getNameString(str);
            return ns.length() > 0 ? Utils.narrowContext(obj).resolve(Utils.stringToNameComponent(ns)) : obj;
         } else {
            throw new NamingException("Bad stringified object reference: " + str);
         }
      } catch (NamingException var4) {
         throw Utils.unwrapNamingException(new BAD_PARAM(var4.getMessage(), 1330446343, CompletionStatus.COMPLETED_NO), var4);
      } catch (UserException var5) {
         throw Utils.initCORBAExceptionWithCause(new BAD_PARAM(var5.getMessage(), 1330446343, CompletionStatus.COMPLETED_NO), var5);
      } catch (IOException var6) {
         throw Utils.initCORBAExceptionWithCause(new BAD_PARAM(var6.getMessage(), 1330446346, CompletionStatus.COMPLETED_NO), var6);
      }
   }

   public void connect(org.omg.CORBA.Object obj) {
      try {
         StubReference var2 = OIDManager.getInstance().getReplacement(obj);
      } catch (ClassCastException var3) {
         throw new BAD_PARAM("Couldn't connect Object to the ORB", 1330446346, CompletionStatus.COMPLETED_NO);
      } catch (RemoteException var4) {
         throw (BAD_PARAM)(new BAD_PARAM(var4.getMessage(), 1330446346, CompletionStatus.COMPLETED_NO)).initCause(var4);
      }
   }

   public void disconnect(org.omg.CORBA.Object obj) {
      ServerReference sref = OIDManager.getInstance().getServerReference(obj);
      if (sref != null) {
         OIDManager.getInstance().removeServerReference(sref);
      }

   }

   public void destroy() {
   }

   public NVList create_list(int count) {
      return new NVListImpl(count);
   }

   public NVList create_operation_list(org.omg.CORBA.Object oper) {
      throw new NO_IMPLEMENT();
   }

   public NamedValue create_named_value(String s, Any any, int flags) {
      return new NamedValueImpl(s, any, flags);
   }

   public ExceptionList create_exception_list() {
      return new ExceptionListImpl();
   }

   public ContextList create_context_list() {
      throw new NO_IMPLEMENT();
   }

   public Context get_default_context() {
      throw new NO_IMPLEMENT();
   }

   public Environment create_environment() {
      return new EnvironmentImpl();
   }

   public OutputStream create_output_stream() {
      return new IIOPOutputStream(this);
   }

   public void send_multiple_requests_oneway(Request[] req) {
      for(int i = 0; i < req.length; ++i) {
         req[i].send_oneway();
      }

   }

   public void send_multiple_requests_deferred(Request[] req) {
      for(int i = 0; i < req.length; ++i) {
         req[i].send_deferred();
         this.deferred.add(req[i]);
      }

   }

   public synchronized boolean poll_next_response() {
      Iterator i = this.deferred.iterator();

      do {
         if (!i.hasNext()) {
            return false;
         }
      } while(!((Request)i.next()).poll_response());

      return true;
   }

   public synchronized Request get_next_response() throws WrongTransaction {
      Iterator i = this.deferred.iterator();

      Request req;
      do {
         if (!i.hasNext()) {
            throw new BAD_INV_ORDER("No ready response");
         }

         req = (Request)i.next();
      } while(!req.poll_response());

      i.remove();
      req.get_response();
      return req;
   }

   public void run() {
   }

   public void shutdown(boolean wait_for_completion) {
   }

   public boolean work_pending() {
      throw new NO_IMPLEMENT();
   }

   public void perform_work() {
      throw new NO_IMPLEMENT();
   }

   public TypeCode get_primitive_tc(TCKind tcKind) {
      return TypeCodeImpl.get_primitive_tc(tcKind);
   }

   public TypeCode create_struct_tc(String id, String name, StructMember[] members) {
      return TypeCodeImpl.create_struct_tc(15, id, name, members);
   }

   public TypeCode create_union_tc(String id, String name, TypeCode discriminator_type, UnionMember[] members) {
      return TypeCodeImpl.create_union_tc(id, name, discriminator_type, members);
   }

   public TypeCode create_enum_tc(String id, String name, String[] members) {
      return TypeCodeImpl.create_enum_tc(id, name, members);
   }

   public TypeCode create_alias_tc(String id, String name, TypeCode original_type) {
      return new TypeCodeImpl(21, new RepositoryId(id), name, original_type);
   }

   public TypeCode create_exception_tc(String id, String name, StructMember[] members) {
      return TypeCodeImpl.create_struct_tc(22, id, name, members);
   }

   public TypeCode create_interface_tc(String id, String name) {
      return new TypeCodeImpl(14, new RepositoryId(id), name);
   }

   public TypeCode create_string_tc(int bound) {
      return TypeCodeImpl.create_string_tc(bound);
   }

   public TypeCode create_wstring_tc(int bound) {
      return TypeCodeImpl.create_wstring_tc(bound);
   }

   public TypeCode create_sequence_tc(int bound, TypeCode element_type) {
      return TypeCodeImpl.create_sequence_tc(bound, element_type);
   }

   public TypeCode create_recursive_sequence_tc(int bound, int offset) {
      throw new NO_IMPLEMENT();
   }

   public TypeCode create_array_tc(int length, TypeCode element_type) {
      return TypeCodeImpl.create_array_tc(length, element_type);
   }

   public TypeCode create_native_tc(String id, String name) {
      return new TypeCodeImpl(31, new RepositoryId(id), name);
   }

   public TypeCode create_abstract_interface_tc(String id, String name) {
      return new TypeCodeImpl(32, new RepositoryId(id), name);
   }

   public TypeCode create_fixed_tc(short digits, short scale) {
      return TypeCodeImpl.create_fixed_tc(digits, scale);
   }

   public TypeCode create_value_tc(String id, String name, short type_modifier, TypeCode concrete_base, ValueMember[] members) {
      return TypeCodeImpl.create_value_tc(id, name, type_modifier, concrete_base, members);
   }

   public TypeCode create_recursive_tc(String id) {
      return TypeCodeImpl.get_primitive_tc(1);
   }

   public TypeCode create_value_box_tc(String id, String name, TypeCode boxed_type) {
      return new TypeCodeImpl(30, new RepositoryId(id), name, boxed_type);
   }

   public Any create_any() {
      return new AnyImpl();
   }

   public Policy create_policy(int type, Any val) throws PolicyError {
      Policy policy = ((POAImpl)this.getRootPOA()).create_policy(type, val);
      if (policy != null) {
         return policy;
      } else {
         switch (type) {
            case 28:
               return new RequestEndTimePolicyImpl(UtcTHelper.extract(val));
            case 30:
               return new ReplyEndTimePolicyImpl(UtcTHelper.extract(val));
            case 31:
               return new RelativeRequestTimeoutPolicyImpl(TimeTHelper.extract(val));
            case 32:
               return new RelativeRoundtripTimeoutPolicyImpl(TimeTHelper.extract(val));
            case 55:
               return new InvocationPolicyImpl(val.extract_ushort());
            case 56:
               return new OTSPolicyImpl(val.extract_ushort());
            default:
               throw new PolicyError("create_policy()", (short)1);
         }
      }
   }

   public Current get_current() {
      throw new NO_IMPLEMENT();
   }

   public boolean get_service_information(short service_type, ServiceInformationHolder service_info) {
      throw new NO_IMPLEMENT();
   }

   public DynAny create_dyn_any(Any value) {
      throw new NO_IMPLEMENT();
   }

   public DynAny create_basic_dyn_any(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   public DynStruct create_dyn_struct(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   public DynSequence create_dyn_sequence(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   public DynArray create_dyn_array(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   public DynUnion create_dyn_union(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   public DynEnum create_dyn_enum(TypeCode type) throws InconsistentTypeCode {
      throw new NO_IMPLEMENT();
   }

   protected static void p(String s) {
      System.out.println("<ORB> " + s);
   }

   private static final POA createRootPOA(String orbName) {
      try {
         return orbName == null ? (new POAImpl("RootPOA")).export() : (new POAImpl(orbName + "/" + "RootPOA")).export();
      } catch (IOException var2) {
         throw (BAD_PARAM)(new BAD_PARAM(var2.getMessage(), 1330446346, CompletionStatus.COMPLETED_NO)).initCause(var2);
      }
   }

   private final synchronized POA getRootPOA() {
      if (this.rootPOA == null) {
         this.rootPOA = createRootPOA(this.name);
         this.initialRefs.put("RootPOA", this.rootPOA);
      }

      return this.rootPOA;
   }

   // $FF: synthetic method
   ORB(boolean x0, Object x1) {
      this(x0);
   }

   private static final class SingletonMaker {
      private static final ORB ORB = new ORB(true);
   }
}
