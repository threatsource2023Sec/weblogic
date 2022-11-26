package weblogic.servlet.cluster.wan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Invalidate implements Externalizable {
   private String sessionID;
   private String contextPath;
   private int hashCode;

   public Invalidate() {
   }

   public Invalidate(String sessionID, String contextPath) {
      this.sessionID = sessionID;
      this.contextPath = contextPath;
      this.hashCode = sessionID.hashCode() ^ contextPath.hashCode();
   }

   final String getSessionID() {
      return this.sessionID;
   }

   final String getContextPath() {
      return this.contextPath;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object object) {
      try {
         Invalidate other = (Invalidate)object;
         return this.sessionID.equals(other.sessionID) && this.contextPath.equals(other.contextPath);
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.sessionID);
      out.writeUTF(this.contextPath);
      out.writeInt(this.hashCode);
   }

   public void readExternal(ObjectInput in) throws IOException {
      this.sessionID = in.readUTF();
      this.contextPath = in.readUTF();
      this.hashCode = in.readInt();
   }
}
