package weblogic.rmi.utils.enumerations;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public final class EmptyEnumerator implements Enumeration, Externalizable {
   private static final long serialVersionUID = -2031518801286395088L;

   public boolean hasMoreElements() {
      return false;
   }

   public Object nextElement() {
      throw new NoSuchElementException();
   }

   public void readExternal(ObjectInput wloi) throws IOException, ClassNotFoundException {
   }

   public void writeExternal(ObjectOutput wloo) throws IOException {
   }
}
