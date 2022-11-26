package weblogic.utils.enumerations;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LineEnumerator extends PeekingEnumerator {
   private DataInputStream dis;

   public LineEnumerator(InputStream in) {
      this.dis = new DataInputStream(in);
   }

   public String nextLine() throws IOException {
      try {
         return (String)this.nextElement();
      } catch (EnumerationException var2) {
         throw (IOException)var2.getRealException();
      }
   }

   public Object nextObject() {
      try {
         Object o = this.dis.readLine();
         return o != null ? o : END;
      } catch (IOException var3) {
         throw new EnumerationException(var3);
      }
   }
}
