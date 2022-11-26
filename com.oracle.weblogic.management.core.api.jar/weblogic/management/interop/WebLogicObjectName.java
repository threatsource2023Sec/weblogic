package weblogic.management.interop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.management.MalformedObjectNameException;

public final class WebLogicObjectName extends ObjectName implements Serializable {
   private static final long serialVersionUID = 7351961731978894257L;
   private boolean isAdmin;
   private boolean isConfig;
   private boolean isRuntime;
   private weblogic.management.WebLogicObjectName parent;
   private int hashCode;

   public WebLogicObjectName() {
   }

   public WebLogicObjectName(String name) {
      super(name);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
   }

   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
      out.defaultWriteObject();
   }

   private Object readResolve() throws ObjectStreamException {
      try {
         return new weblogic.management.WebLogicObjectName(super.getCanonicalName());
      } catch (MalformedObjectNameException var2) {
         return new Error(var2);
      }
   }
}
