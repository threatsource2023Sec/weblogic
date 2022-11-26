package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class VendorInfoSecurity extends ServiceContext {
   protected AuthenticatedUser user;
   public static final VendorInfoSecurity ANONYMOUS = new VendorInfoSecurity();

   public VendorInfoSecurity() {
      super(1111834882);
   }

   public VendorInfoSecurity(AuthenticatedUser user) {
      super(1111834882);
      this.user = user;
   }

   public AuthenticatedUser getUser() {
      return this.user;
   }

   protected VendorInfoSecurity(CorbaInputStream in) {
      super(1111834882);
      this.readEncapsulatedContext(in);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.user = (AuthenticatedUser)in.read_value();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_value(this.user);
   }

   public String toString() {
      return "VendorInfoSecurity Context: " + this.user;
   }
}
