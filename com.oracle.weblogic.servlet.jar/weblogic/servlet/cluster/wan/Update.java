package weblogic.servlet.cluster.wan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public class Update implements Externalizable {
   static final long serialVersionUID = 6694065036191470528L;
   private String sessionID;
   private String contextPath;
   private long creationTime;
   private int maxInactiveTime;
   private long lastAccessTime;
   private SessionDiff change;
   private boolean serialized = false;
   private int hashCode;

   public Update(String sessionID, String contextPath, long creationTime, int maxInactiveTime, long lastAccessTime, SessionDiff change) {
      this.sessionID = sessionID;
      this.contextPath = "".equals(contextPath) ? "/" : contextPath;
      this.hashCode = sessionID.hashCode() ^ contextPath.hashCode();
      this.creationTime = creationTime;
      this.maxInactiveTime = maxInactiveTime;
      this.lastAccessTime = lastAccessTime;
      this.change = change;
   }

   public Update() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeImmutable(this.sessionID);
         wlOut.writeImmutable(this.contextPath);
      } else {
         out.writeUTF(this.sessionID);
         out.writeUTF(this.contextPath);
      }

      out.writeLong(this.creationTime);
      out.writeInt(this.maxInactiveTime);
      out.writeLong(this.lastAccessTime);
      out.writeObject(this.change.cloneAndClear());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.sessionID = (String)wlIn.readImmutable();
         this.contextPath = (String)wlIn.readImmutable();
      } else {
         this.sessionID = in.readUTF();
         this.contextPath = in.readUTF();
      }

      this.creationTime = in.readLong();
      this.maxInactiveTime = in.readInt();
      this.lastAccessTime = in.readLong();
      this.change = (SessionDiff)in.readObject();
      this.hashCode = this.sessionID.hashCode() ^ this.contextPath.hashCode();
      this.serialized = true;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         try {
            Update other = (Update)object;
            return this.hashCode == other.hashCode && this.contextPath.equals(other.contextPath) && this.sessionID.equals(other.sessionID);
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   public String getSessionID() {
      return this.sessionID;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public int getMaxInactiveTime() {
      return this.maxInactiveTime;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public SessionDiff getChange() {
      return !this.serialized ? this.change.cloneAndClear() : this.change;
   }
}
