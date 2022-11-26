package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.rmi.internal.FutureResultID;

public class FutureObjectIdServiceContext extends ServiceContext {
   private long objectID;

   public FutureObjectIdServiceContext(FutureResultID resultId) {
      this();
      this.objectID = resultId.getHash();
   }

   FutureObjectIdServiceContext(CorbaInputStream in) {
      this();
      this.readEncapsulatedContext(in);
   }

   private FutureObjectIdServiceContext() {
      super(1111834892);
   }

   public FutureResultID getResultID() {
      return new FutureResultID(this.objectID);
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      this.objectID = in.read_longlong();
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_longlong(this.objectID);
   }

   public String toString() {
      return "FutureObjectIdServiceContext: " + this.objectID;
   }

   public boolean equals(Object obj) {
      return obj instanceof FutureObjectIdServiceContext && this.objectID == ((FutureObjectIdServiceContext)obj).objectID;
   }

   public int hashCode() {
      return (new Long(this.objectID)).hashCode();
   }
}
