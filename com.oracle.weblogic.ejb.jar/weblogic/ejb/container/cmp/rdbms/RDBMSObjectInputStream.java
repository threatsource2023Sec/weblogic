package weblogic.ejb.container.cmp.rdbms;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public final class RDBMSObjectInputStream extends ObjectInputStream {
   private ClassLoader classLoader = null;

   public RDBMSObjectInputStream(InputStream is, ClassLoader cl) throws IOException {
      super(is);
      this.classLoader = cl;
   }

   protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
      return Class.forName(v.getName(), false, this.classLoader);
   }
}
