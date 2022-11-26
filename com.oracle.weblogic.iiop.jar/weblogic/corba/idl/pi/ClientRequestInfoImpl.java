package weblogic.corba.idl.pi;

import org.omg.CORBA.Any;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.ObjectImpl;
import org.omg.Dynamic.Parameter;
import org.omg.IOP.ServiceContext;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedProfile;
import org.omg.PortableInterceptor.ClientRequestInfo;
import org.omg.PortableInterceptor.InvalidSlot;
import weblogic.corba.idl.AnyImpl;
import weblogic.corba.idl.TypeCodeImpl;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;

public class ClientRequestInfoImpl extends ObjectImpl implements ClientRequestInfo {
   private RequestMessage request;
   private ReplyMessage reply;

   public ClientRequestInfoImpl(RequestMessage request) {
      this.request = request;
   }

   public ClientRequestInfoImpl(ReplyMessage reply) {
      this.reply = reply;
   }

   public String received_exception_id() {
      return this.reply.getExceptionId().toString();
   }

   public Any received_exception() {
      AnyImpl aimpl = new AnyImpl();
      TypeCode tc = TypeCodeImpl.create_struct_tc(22, this.received_exception_id(), "Exception", new StructMember[0]);
      aimpl.type(tc);
      return aimpl;
   }

   public Object effective_target() {
      return null;
   }

   public Object target() {
      return null;
   }

   public Policy get_request_policy(int type) {
      return null;
   }

   public void add_request_service_context(ServiceContext service_context, boolean replace) {
      this.request.getServiceContexts().addServiceContext(new weblogic.iiop.contexts.ServiceContext(service_context.context_id, service_context.context_data));
   }

   public TaggedComponent get_effective_component(int id) {
      return null;
   }

   public TaggedComponent[] get_effective_components(int id) {
      return new TaggedComponent[0];
   }

   public TaggedProfile effective_profile() {
      return null;
   }

   public int request_id() {
      return this.request == null ? this.reply.getRequestID() : this.request.getRequestID();
   }

   public short reply_status() {
      return (short)this.reply.getReplyStatus();
   }

   public short sync_scope() {
      return 0;
   }

   public boolean response_expected() {
      return !this.request.isOneWay();
   }

   public String operation() {
      return this.request.getOperationName();
   }

   public String[] contexts() {
      throw new NO_IMPLEMENT("contexts()");
   }

   public String[] operation_context() {
      throw new NO_IMPLEMENT("operation_contexts()");
   }

   public Any result() {
      return null;
   }

   public Any get_slot(int id) throws InvalidSlot {
      return null;
   }

   public Object forward_reference() {
      return null;
   }

   public TypeCode[] exceptions() {
      return new TypeCode[0];
   }

   public Parameter[] arguments() {
      return new Parameter[0];
   }

   public ServiceContext get_reply_service_context(int id) {
      weblogic.iiop.contexts.ServiceContext sc = this.reply.getServiceContext(id);
      return new ServiceContext(sc.getContextId(), sc.getContextData());
   }

   public ServiceContext get_request_service_context(int id) {
      weblogic.iiop.contexts.ServiceContext sc = this.request.getServiceContext(id);
      return new ServiceContext(sc.getContextId(), sc.getContextData());
   }

   public String[] _ids() {
      return new String[]{"IDL:omg.org/PortableInterceptor/ClientRequestInfo:1.0"};
   }
}
