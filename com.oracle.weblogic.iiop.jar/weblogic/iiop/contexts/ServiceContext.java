package weblogic.iiop.contexts;

import java.util.Arrays;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.rmi.spi.ServiceContextID;

public class ServiceContext implements ServiceContextID {
   private int context_id;
   protected byte[] context_data;
   protected static final boolean DEBUG = false;

   public String toString() {
      return "ServiceContext: ContextId = " + VMCIDToString(this.context_id);
   }

   public static String VMCIDToString(int context_id) {
      StringBuilder cid = new StringBuilder();
      int i = context_id >> 24 & 255;
      cid.append(i >= 32 && i <= 126 ? Character.toString((char)i) : "\\" + Integer.toString(i));
      i = context_id >> 16 & 255;
      cid.append(i >= 32 && i <= 126 ? Character.toString((char)i) : "\\" + Integer.toString(i));
      i = context_id >> 8 & 255;
      cid.append(i >= 32 && i <= 126 ? Character.toString((char)i) : "\\" + Integer.toString(i));
      i = context_id & 255;
      cid.append(i >= 32 && i <= 126 ? Character.toString((char)i) : "\\" + Integer.toString(i));
      return cid.toString();
   }

   protected ServiceContext(int ctxid, CorbaInputStream in) {
      this.context_id = ctxid;
      this.read(in);
   }

   public ServiceContext(int ctxid, byte[] data) {
      this.context_id = ctxid;
      this.context_data = data;
   }

   public ServiceContext(int ctxid) {
      this(ctxid, (byte[])null);
   }

   public ServiceContext() {
   }

   public int getContextId() {
      return this.context_id;
   }

   public byte[] getContextData() {
      return this.context_data;
   }

   public static ServiceContext readServiceContext(CorbaInputStream in) {
      int context_id = in.read_long();
      switch (context_id) {
         case 0:
            return new PropagationContextImpl(in);
         case 1:
            return new CodeSetServiceContext(in);
         case 2:
         case 3:
         case 4:
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 16:
         default:
            if ((context_id & -256) == 1111834880) {
               return VendorInfo.readServiceContext(context_id, in);
            }

            return new ServiceContext(context_id, in);
         case 5:
            return new BiDirIIOPContextImpl(in);
         case 6:
            return new SendingContextRunTime(in);
         case 7:
            return new MessagingPolicyContext(in);
         case 9:
            return new UnknownExceptionInfo(in);
         case 15:
            return new SASServiceContext(in);
         case 17:
            return new SFVContext(in);
      }
   }

   protected void readEncapsulation(CorbaInputStream in, long handle) {
      this.readEncapsulation(in);
   }

   protected void readEncapsulation(CorbaInputStream in) {
   }

   protected void writeEncapsulation(CorbaOutputStream os) {
   }

   public void write(CorbaOutputStream os) {
      os.write_long(this.context_id);
      if (this.context_data == null) {
         os.write_long(0);
      } else {
         os.write_octet_sequence(this.context_data);
      }

   }

   public final void writeEncapsulatedContext(CorbaOutputStream os) {
      os.write_long(this.context_id);
      if (this.context_data != null) {
         os.write_octet_sequence(this.context_data);
      } else {
         long handle = os.startEncapsulation();
         this.writeEncapsulation(os);
         os.endEncapsulation(handle);
      }

   }

   public final void premarshal() {
      CorbaOutputStream os = IiopProtocolFacade.createOutputStream();
      os.putEndian();
      this.writeEncapsulation(os);
      this.context_data = os.getBuffer();
      os.close();
   }

   protected final void read(CorbaInputStream is) {
      this.context_data = is.read_octet_sequence();
   }

   protected final void readEncapsulatedContext(CorbaInputStream is) {
      long handle = is.startEncapsulation();
      if (handle != 0L) {
         this.readEncapsulation(is, handle);
         is.endEncapsulation(handle);
      }

   }

   protected static void p(String s) {
      System.err.println("<ServiceContext> " + s);
   }

   public boolean equals(Object o) {
      return this == o || o != null && this.getClass().equals(o.getClass()) && this.equals((ServiceContext)o);
   }

   private boolean equals(ServiceContext other) {
      return Arrays.equals(this.context_data, other.context_data);
   }

   public int hashCode() {
      int result = this.context_id;
      result = 31 * result + (this.context_data != null ? Arrays.hashCode(this.context_data) : 0);
      return result;
   }
}
