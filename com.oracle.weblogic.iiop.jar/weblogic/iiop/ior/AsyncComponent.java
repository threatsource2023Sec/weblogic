package weblogic.iiop.ior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class AsyncComponent extends TaggedComponent {
   private List signatures;

   public AsyncComponent() {
      super(1111834884);
      this.signatures = new ArrayList();
   }

   public AsyncComponent(CorbaInputStream in) {
      super(1111834884, in);
   }

   public void addAsyncSignature(String signature) {
      this.signatures.add(signature);
   }

   public boolean hasSignatures() {
      return !this.signatures.isEmpty();
   }

   public String[] getSignatures() {
      return (String[])this.signatures.toArray(new String[this.signatures.size()]);
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      int length = in.read_long();
      this.signatures = new ArrayList();

      for(int i = 0; i < length; ++i) {
         this.signatures.add(in.read_string());
      }

      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_long(this.signatures.size());
      Iterator var4 = this.signatures.iterator();

      while(var4.hasNext()) {
         String signature = (String)var4.next();
         out.write_string(signature);
      }

      out.endEncapsulation(handle);
   }

   public String toString() {
      return "AsyncComponent{signatures=" + this.signatures + '}';
   }
}
