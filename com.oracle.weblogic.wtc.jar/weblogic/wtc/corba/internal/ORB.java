package weblogic.wtc.corba.internal;

import com.bea.core.jatmi.common.ntrace;
import java.applet.Applet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
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
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.Request;
import org.omg.CORBA.ServiceInformationHolder;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.ORBPackage.InconsistentTypeCode;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.portable.OutputStream;
import weblogic.corba.cos.naming.RootNamingContextImpl;
import weblogic.iiop.ior.IOR;

public class ORB extends org.omg.CORBA_2_3.ORB {
   private static final String ORBClassProperty = "org.omg.CORBA.ORBClass";
   private static final String ORBClassValue = "weblogic.wtc.corba.internal.DelegatedSunORB";
   private static final String ORBSocketFactoryProperty = "org.omg.CORBA.ORBSocketFactoryClass";
   private static final String ORBSocketFactoryValue = "weblogic.wtc.corba.internal.ORBSocketFactory";
   private static final String ORBWtcSocketFactoryValue = "weblogic.wtc.corba.internal.ORBWtcSocketFactory";
   private static final String ORBInitialHost = "org.omg.CORBA.ORBInitialHost";
   private static final String ORBInitialPort = "org.omg.CORBA.ORBInitialPort";
   private org.omg.CORBA.ORB delegate;
   private String defaultInitialRef;
   private Hashtable initialRefs = new Hashtable();
   private boolean useInitialHost = false;
   private ORBInitialRef orbInitialRef = new ORBInitialRef(this);

   protected void set_parameters(String[] args, Properties props) {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORB/set_parameters/" + Arrays.deepToString(args) + "/" + props);
      }

      String[] emptyArgs = null;
      this.ProcessInitialReferences(args);
      Properties delegateProps = new Properties();
      delegateProps.setProperty("org.omg.CORBA.ORBClass", "weblogic.wtc.corba.internal.DelegatedSunORB");
      if (System.getProperty("java.specification.version").equals("1.4")) {
         delegateProps.setProperty("org.omg.CORBA.ORBSocketFactoryClass", "weblogic.wtc.corba.internal.ORBWtcSocketFactory");
      } else {
         delegateProps.setProperty("org.omg.CORBA.ORBSocketFactoryClass", "weblogic.wtc.corba.internal.ORBSocketFactory");
      }

      if (props != null) {
         String val = props.getProperty("org.omg.CORBA.ORBInitialHost");
         if (val != null) {
            delegateProps.setProperty("org.omg.CORBA.ORBInitialHost", val);
            this.useInitialHost = true;
         }

         val = props.getProperty("org.omg.CORBA.ORBInitialPort");
         if (val != null) {
            delegateProps.setProperty("org.omg.CORBA.ORBInitialPort", val);
            this.useInitialHost = true;
         }
      }

      this.delegate = org.omg.CORBA_2_3.ORB.init((String[])emptyArgs, delegateProps);
      if (traceEnabled) {
         ntrace.doTrace("]/ORB/set_parameters/50");
      }

   }

   protected void set_parameters(Applet app, Properties props) {
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORB/set_parameters/" + app + "/" + props);
      }

      Properties delegateProps = new Properties();
      delegateProps.setProperty("org.omg.CORBA.ORBClass", "weblogic.wtc.corba.internal.DelegatedSunORB");
      this.delegate = org.omg.CORBA_2_3.ORB.init(app, delegateProps);
      if (traceEnabled) {
         ntrace.doTrace("]/ORB/set_parameters/50");
      }

   }

   public Object resolve_initial_references(String object_name) throws InvalidName {
      Object returnObj = null;
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORB/resolve_initial_references/" + object_name);
      }

      String initialRef = (String)this.initialRefs.get(object_name);
      if (initialRef == null && this.defaultInitialRef != null) {
         initialRef = this.defaultInitialRef + "/" + object_name;
      } else if (initialRef == null) {
         initialRef = object_name;
      }

      if (traceEnabled) {
         ntrace.doTrace("/ORB/resolve_initial_references/20/" + initialRef);
      }

      if (initialRef != null && (initialRef.startsWith("corbaloc:tgiop:") || initialRef.startsWith("corbaname:tgiop:"))) {
         returnObj = this.orbInitialRef.convertTGIOPURLToObject(initialRef);
      } else if (initialRef != null && initialRef.equals("NameService") && !this.useInitialHost) {
         try {
            IOR namingIOR = RootNamingContextImpl.getInitialReference().getIOR();
            returnObj = this.delegate.string_to_object(namingIOR.stringify());
         } catch (IOException var6) {
            throw new INTERNAL();
         }
      } else {
         returnObj = this.delegate.resolve_initial_references(object_name);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/ORB/resolve_initial_references/50/" + returnObj);
      }

      return returnObj;
   }

   private void ProcessInitialReferences(String[] args) {
      if (args != null) {
         boolean traceEnabled = ntrace.isTraceEnabled(8);
         if (traceEnabled) {
            ntrace.doTrace("[/ORB/ProcessInitialReferences/" + Arrays.deepToString(args));
         }

         int len = args.length;

         for(int i = 0; i < len; ++i) {
            if (traceEnabled) {
               ntrace.doTrace("]/ORB/ProcessInitialReferences/10/" + args[i]);
            }

            if (args[i] != null && args[i].startsWith("-ORBDefaultInitRef") && i + 1 < len && args[i + 1] != null) {
               this.defaultInitialRef = args[i + 1];
               ++i;
               if (traceEnabled) {
                  ntrace.doTrace("]/ORB/ProcessInitialReferences/20/" + this.defaultInitialRef);
               }
            }

            if (args[i] != null && args[i].startsWith("-ORBInitRef") && i + 1 < len && args[i + 1] != null) {
               String ref = args[i + 1];
               ++i;
               if (ref.indexOf(61) != -1) {
                  String val = ref.substring(ref.indexOf(61) + 1).trim();
                  String name = ref.substring(0, ref.indexOf(61)).trim();
                  this.initialRefs.put(name, val);
                  if (traceEnabled) {
                     ntrace.doTrace("]/ORB/ProcessInitialReferences/30/" + name + "/" + val);
                  }
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORB/ProcessInitialReferences/100");
         }

      }
   }

   public void connect(Object obj) {
      this.delegate.connect(obj);
   }

   public void destroy() {
      this.delegate.destroy();
   }

   public void disconnect(Object obj) {
      this.delegate.disconnect(obj);
   }

   public String[] list_initial_services() {
      return this.delegate.list_initial_services();
   }

   public String object_to_string(Object obj) {
      return this.delegate.object_to_string(obj);
   }

   public Object string_to_object(String str) {
      Object returnObj = null;
      boolean traceEnabled = ntrace.isTraceEnabled(8);
      if (traceEnabled) {
         ntrace.doTrace("[/ORB/string_to_object/" + str);
      }

      String resolvePath;
      if (str != null && str.startsWith("corbaloc:rir:")) {
         resolvePath = str.substring("corbaloc:rir:".length());
         if (resolvePath.length() == 0) {
            resolvePath = "NameService";
         } else {
            if (!resolvePath.startsWith("/")) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ORB/string_to_object/20");
               }

               throw new BAD_PARAM();
            }

            resolvePath = resolvePath.substring(1);
         }

         try {
            returnObj = this.resolve_initial_references(resolvePath);
         } catch (InvalidName var8) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORB/string_to_object/25/" + var8);
            }

            throw new BAD_PARAM();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORB/string_to_object/30/" + returnObj);
         }

         return returnObj;
      } else if (str != null && str.startsWith("corbaloc:tgiop:")) {
         try {
            returnObj = this.orbInitialRef.convertTGIOPURLToObject(str);
         } catch (InvalidName var9) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORB/string_to_object/35/" + var9);
            }

            throw new BAD_PARAM();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORB/string_to_object/40/" + returnObj);
         }

         return returnObj;
      } else if (str != null && str.startsWith("corbaname:rir:")) {
         resolvePath = str.substring("corbaname:rir:".length());
         if (resolvePath.startsWith("#")) {
            resolvePath = resolvePath.substring(1);
            Object nsObj = null;

            try {
               nsObj = this.resolve_initial_references("NameService");
            } catch (InvalidName var10) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ORB/string_to_object/50/" + var10);
               }

               throw new BAD_PARAM();
            }

            try {
               returnObj = this.orbInitialRef.resolvePath(nsObj, resolvePath);
            } catch (InvalidName var7) {
               throw new BAD_PARAM();
            }

            if (traceEnabled) {
               ntrace.doTrace("]/ORB/string_to_object/55/" + returnObj);
            }

            return returnObj;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORB/string_to_object/45");
            }

            throw new BAD_PARAM();
         }
      } else if (str != null && str.startsWith("corbaname:tgiop:")) {
         try {
            returnObj = this.orbInitialRef.convertTGIOPURLToObject(str);
         } catch (InvalidName var11) {
            if (traceEnabled) {
               ntrace.doTrace("*]/ORB/string_to_object/60/" + var11);
            }

            throw new BAD_PARAM();
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ORB/string_to_object/65/" + returnObj);
         }

         return returnObj;
      } else if (str != null && (str.startsWith("corbaloc") || str.startsWith("corbaname"))) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ORB/string_to_object/70");
         }

         throw new NO_IMPLEMENT();
      } else {
         returnObj = this.delegate.string_to_object(str);
         if (traceEnabled) {
            ntrace.doTrace("]/ORB/string_to_object/75/" + returnObj);
         }

         return returnObj;
      }
   }

   public NVList create_list(int count) {
      return this.delegate.create_list(count);
   }

   public NVList create_operation_list(Object oper) {
      return this.delegate.create_operation_list(oper);
   }

   public NamedValue create_named_value(String s, Any any, int flags) {
      return this.delegate.create_named_value(s, any, flags);
   }

   public ExceptionList create_exception_list() {
      return this.delegate.create_exception_list();
   }

   public ContextList create_context_list() {
      return this.delegate.create_context_list();
   }

   public Context get_default_context() {
      return this.delegate.get_default_context();
   }

   public Environment create_environment() {
      return this.delegate.create_environment();
   }

   public OutputStream create_output_stream() {
      return this.delegate.create_output_stream();
   }

   public void send_multiple_requests_oneway(Request[] req) {
      this.delegate.send_multiple_requests_oneway(req);
   }

   public void send_multiple_requests_deferred(Request[] req) {
      this.delegate.send_multiple_requests_deferred(req);
   }

   public boolean poll_next_response() {
      return this.delegate.poll_next_response();
   }

   public Request get_next_response() throws WrongTransaction {
      return this.delegate.get_next_response();
   }

   public TypeCode get_primitive_tc(TCKind tcKind) {
      return this.delegate.get_primitive_tc(tcKind);
   }

   public TypeCode create_struct_tc(String id, String name, StructMember[] members) {
      return this.delegate.create_struct_tc(id, name, members);
   }

   public TypeCode create_union_tc(String id, String name, TypeCode discriminator_type, UnionMember[] members) {
      return this.delegate.create_union_tc(id, name, discriminator_type, members);
   }

   public TypeCode create_enum_tc(String id, String name, String[] members) {
      return this.delegate.create_enum_tc(id, name, members);
   }

   public TypeCode create_alias_tc(String id, String name, TypeCode original_type) {
      return this.delegate.create_alias_tc(id, name, original_type);
   }

   public TypeCode create_exception_tc(String id, String name, StructMember[] members) {
      return this.delegate.create_exception_tc(id, name, members);
   }

   public TypeCode create_interface_tc(String id, String name) {
      return this.delegate.create_interface_tc(id, name);
   }

   public TypeCode create_string_tc(int bound) {
      return this.delegate.create_string_tc(bound);
   }

   public TypeCode create_wstring_tc(int bound) {
      return this.delegate.create_wstring_tc(bound);
   }

   public TypeCode create_sequence_tc(int bound, TypeCode element_type) {
      return this.delegate.create_sequence_tc(bound, element_type);
   }

   public TypeCode create_recursive_sequence_tc(int bound, int offset) {
      return this.delegate.create_recursive_sequence_tc(bound, offset);
   }

   public TypeCode create_array_tc(int length, TypeCode element_type) {
      return this.delegate.create_array_tc(length, element_type);
   }

   public TypeCode create_native_tc(String id, String name) {
      return this.delegate.create_native_tc(id, name);
   }

   public TypeCode create_abstract_interface_tc(String id, String name) {
      return this.delegate.create_abstract_interface_tc(id, name);
   }

   public TypeCode create_fixed_tc(short digits, short scale) {
      return this.delegate.create_fixed_tc(digits, scale);
   }

   public TypeCode create_value_tc(String id, String name, short type_modifier, TypeCode concrete_base, ValueMember[] members) {
      return this.delegate.create_value_tc(id, name, type_modifier, concrete_base, members);
   }

   public TypeCode create_recursive_tc(String id) {
      return this.delegate.create_recursive_tc(id);
   }

   public TypeCode create_value_box_tc(String id, String name, TypeCode boxed_type) {
      return this.delegate.create_value_box_tc(id, name, boxed_type);
   }

   public Any create_any() {
      return this.delegate.create_any();
   }

   public Current get_current() {
      return this.delegate.get_current();
   }

   public void run() {
      this.delegate.run();
   }

   public void shutdown(boolean wait_for_completion) {
      this.delegate.shutdown(wait_for_completion);
   }

   public boolean work_pending() {
      return this.delegate.work_pending();
   }

   public void perform_work() {
      this.delegate.perform_work();
   }

   public boolean get_service_information(short service_type, ServiceInformationHolder service_info) {
      return this.delegate.get_service_information(service_type, service_info);
   }

   public DynAny create_dyn_any(Any value) {
      return this.delegate.create_dyn_any(value);
   }

   public DynAny create_basic_dyn_any(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_basic_dyn_any(type);
   }

   public DynStruct create_dyn_struct(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_dyn_struct(type);
   }

   public DynSequence create_dyn_sequence(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_dyn_sequence(type);
   }

   public DynArray create_dyn_array(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_dyn_array(type);
   }

   public DynUnion create_dyn_union(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_dyn_union(type);
   }

   public DynEnum create_dyn_enum(TypeCode type) throws InconsistentTypeCode {
      return this.delegate.create_dyn_enum(type);
   }

   public Policy create_policy(int type, Any val) throws PolicyError {
      return this.delegate.create_policy(type, val);
   }
}
