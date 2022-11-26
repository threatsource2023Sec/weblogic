package weblogic.rjvm;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ClusterInfo90 extends ClusterInfo implements Externalizable {
   private static final long serialVersionUID = -1841117794380050697L;
   private String url;

   public ClusterInfo90() {
   }

   public ClusterInfo90(String theUrl) {
      this.setUrl(theUrl);
   }

   public final void setUrl(String theUrl) {
      this.url = theUrl;
   }

   public final String getUrl() {
      return this.url;
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.url = oi.readUTF();
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeUTF(this.url);
   }

   public String toString() {
      return "ClusterInfo90(" + this.url + ")";
   }
}
