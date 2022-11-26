package weblogic.management.context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import javax.security.auth.Subject;

public class JMXContextImpl implements JMXContext {
   private static final long serialVersionUID = -6085646368327683332L;
   private static final int VERSION = 2;
   private Locale locale;
   private Subject subject;
   private String partitionName = "DOMAIN";

   public Locale getLocale() {
      return this.locale;
   }

   public void setLocale(Locale locale) {
      this.locale = locale;
   }

   public Subject getSubject() {
      return this.subject;
   }

   public void setSubject(Subject subject) {
      this.subject = subject;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeInt(2);
      out.writeObject(this.locale);
      if (this.subject != null) {
         out.writeBoolean(true);
         out.writeObject(this.subject);
      } else {
         out.writeBoolean(false);
      }

      if (this.partitionName != null) {
         out.writeObject(this.partitionName);
      } else {
         out.writeObject("DOMAIN");
      }

   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      int version = in.readInt();
      this.locale = (Locale)in.readObject();
      if (in.readBoolean()) {
         this.subject = (Subject)in.readObject();
      }

      if (version >= 2) {
         this.partitionName = (String)in.readObject();
      } else {
         this.partitionName = "DOMAIN";
      }

   }
}
