package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.utils.http.HttpParsing;

public class CodebaseComponent extends TaggedComponent {
   private String codebase;

   protected CodebaseComponent() {
      super(25);
      this.codebase = "";
   }

   CodebaseComponent(CorbaInputStream in) {
      this();
      this.read(in);
   }

   CodebaseComponent(String codebase) {
      this();
      this.codebase = codebase;
   }

   public String getCodebase() {
      return this.codebase;
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.codebase = HttpParsing.unescape(in.read_string());
      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      this.writeCodebase(out);
      out.endEncapsulation(handle);
   }

   protected void writeCodebase(CorbaOutputStream out) {
      out.write_string(HttpParsing.escape(this.codebase));
   }
}
