package weblogic.iiop.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.Principal;
import org.omg.CORBA.SystemException;
import weblogic.iiop.contexts.SFVContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.ior.CompoundSecMechList;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.security.subject.AbstractSubject;

public final class RequestMessage extends SequencedRequestMessage implements ServiceContextMessage {
   private boolean response_expected;
   private IOR ior;
   private TargetAddress target;
   private byte[] object_key;
   private String operation;
   private Principal requesting_principal;
   private CompoundSecMechList mechList;
   private boolean isForeign;
   private AbstractSubject subject = null;
   private Object cachedTxContext;
   private ServiceContextList serviceContexts = new ServiceContextList();
   private List outboundServiceContexts = new ArrayList();

   public RequestMessage(MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      if (!this.isFragmented()) {
         this.unmarshal();
      } else {
         this.request_id = is.peek_long();
      }

   }

   public RequestMessage(IOR ior, int minorVersion, int request_id, String operation, boolean oneway) {
      super(new MessageHeader(0, minorVersion));
      this.ior = ior;
      this.request_id = request_id;
      this.response_expected = !oneway;
      this.operation = operation;
      this.object_key = ior.getProfile().getKey();
      this.target = new TargetAddress(this.object_key);
      this.requesting_principal = null;
      this.mechList = (CompoundSecMechList)ior.getProfile().getComponent(33);
      this.isForeign = ior.isRemote();
      this.setMaxStreamFormatVersion(ior.getProfile().getMaxStreamFormatVersion());
   }

   public final ServiceContextList getOutboundServiceContexts() {
      ServiceContextList serviceContextList = new ServiceContextList();
      Iterator var2 = this.outboundServiceContexts.iterator();

      while(var2.hasNext()) {
         ServiceContext serviceContext = (ServiceContext)var2.next();
         serviceContextList.addServiceContext(serviceContext);
      }

      return serviceContextList;
   }

   public final void addOutboundServiceContext(ServiceContext serviceContext) {
      this.outboundServiceContexts.add(serviceContext);
   }

   public final ServiceContext getServiceContext(int ctxid) {
      return this.serviceContexts.getServiceContext(ctxid);
   }

   public ServiceContextList getServiceContexts() {
      return this.serviceContexts;
   }

   public final void removeServiceContext(int ctxid) {
      this.serviceContexts.removeServiceContext(ctxid);
   }

   public final void addServiceContext(ServiceContext sc) {
      this.serviceContexts.addServiceContext(sc);
   }

   public final byte[] getObjectKey() {
      return this.object_key;
   }

   public final IOR getIOR() {
      return this.ior;
   }

   public final String getOperationName() {
      return this.operation;
   }

   public final Principal getPrincipal() {
      return this.requesting_principal;
   }

   public boolean isOneWay() {
      return !this.response_expected;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   public void setSubject(AbstractSubject subject) {
      this.subject = subject;
   }

   public void write(CorbaOutputStream os) throws SystemException {
      super.write(os);
      switch (this.getMinorVersion()) {
         case 0:
         case 1:
            this.writeServiceContexts(os);
            os.write_ulong(this.request_id);
            os.write_boolean(this.response_expected);
            if (this.getMinorVersion() == 1) {
               this.produceMinorVersion1Padding(os);
            }

            os.write_octet_sequence(this.getObjectKey());
            os.write_string(this.operation);
            this.writeRequestingPrincipal(os);
            break;
         case 2:
            os.write_ulong(this.request_id);
            if (this.response_expected) {
               os.write_octet((byte)3);
            } else {
               os.write_octet((byte)0);
            }

            this.produceMinorVersion1Padding(os);
            this.target.write(os);
            os.write_string(this.operation);
            this.writeServiceContexts(os);
      }

      this.alignOnEightByteBoundry(os);
   }

   private void writeServiceContexts(CorbaOutputStream os) {
      this.serviceContexts.write(os);
   }

   private void produceMinorVersion1Padding(CorbaOutputStream os) {
      os.write_octet((byte)0);
      os.write_octet((byte)0);
      os.write_octet((byte)0);
   }

   private void writeRequestingPrincipal(CorbaOutputStream os) {
      if (this.requesting_principal != null) {
         os.write_Principal(this.requesting_principal);
      } else {
         os.write_long(0);
      }

   }

   public void read(CorbaInputStream is) throws SystemException {
      switch (this.getMinorVersion()) {
         case 0:
            this.readServiceContexts(is);
            this.request_id = is.read_ulong();
            this.response_expected = is.read_boolean();
            this.object_key = is.read_octet_sequence(1048576);
            this.operation = is.read_string();
            this.requesting_principal = is.read_Principal();
            break;
         case 1:
            this.readServiceContexts(is);
            this.request_id = is.read_ulong();
            this.response_expected = is.read_boolean();
            is.read_octet();
            is.read_octet();
            is.read_octet();
            this.object_key = is.read_octet_sequence(1048576);
            this.operation = is.read_string();
            this.requesting_principal = is.read_Principal();
            break;
         case 2:
            this.request_id = is.read_ulong();
            byte response_flags = is.read_octet();
            switch (response_flags) {
               case 0:
                  this.response_expected = false;
                  break;
               case 3:
                  this.response_expected = true;
                  break;
               default:
                  throw new MARSHAL("Unknown response_expected flags: " + response_flags);
            }

            is.read_octet();
            is.read_octet();
            is.read_octet();
            this.target = new TargetAddress(is);
            this.object_key = this.target.getObjectKey();
            this.operation = is.read_string();
            this.readServiceContexts(is);
      }

      this.alignOnEightByteBoundry(is);
   }

   private void readServiceContexts(CorbaInputStream is) {
      this.serviceContexts.read(is);
      SFVContext sfv = (SFVContext)this.serviceContexts.getServiceContext(17);
      if (sfv != null) {
         this.setMaxStreamFormatVersion(sfv.getMaxFormatVersion());
      }

   }

   public CompoundSecMechList getMechanismListForRequest() {
      return this.mechList;
   }

   public boolean isForeign() {
      return this.isForeign;
   }

   public final Object getCachedTxContext() {
      return this.cachedTxContext;
   }

   public final void setCachedTxContext(Object txContext) {
      this.cachedTxContext = txContext;
   }

   protected static void p(String s) {
      System.err.println("<RequestMessage> " + s);
   }

   public String toString() {
      return "RequestMessage{operation='" + this.operation + '\'' + " serviceContexts=" + this.getServiceContexts().toListString() + '}';
   }
}
