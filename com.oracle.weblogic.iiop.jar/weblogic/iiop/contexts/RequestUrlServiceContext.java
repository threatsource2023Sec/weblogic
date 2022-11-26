package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public class RequestUrlServiceContext extends ServiceContext {
   private String requestUrl;

   public RequestUrlServiceContext(String requestUrl) {
      super(1111834894);
      this.requestUrl = requestUrl;
   }

   protected RequestUrlServiceContext(CorbaInputStream in) {
      super(1111834894);
      this.readEncapsulatedContext(in);
   }

   public String getRequestUrl() {
      return this.requestUrl;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.requestUrl = in.read_string();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_string(this.requestUrl);
   }

   public String toString() {
      return "RequestUrlServiceContext{ requestUrl='" + this.requestUrl + "'}";
   }
}
