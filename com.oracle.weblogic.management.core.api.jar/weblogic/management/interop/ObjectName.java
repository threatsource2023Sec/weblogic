package weblogic.management.interop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.management.MalformedObjectNameException;

public class ObjectName implements Serializable {
   private static final long serialVersionUID = -5467795090068647408L;
   private String canonicalName = null;
   private boolean pattern = false;
   private boolean propertyPattern = false;
   private Map propertyList = new HashMap(5);
   private String domain = null;
   private String propertyListString = null;

   public ObjectName() {
   }

   public ObjectName(String name) {
      this.canonicalName = name;
   }

   protected String getCanonicalName() {
      return this.canonicalName;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
   }

   private void writeObject(ObjectOutputStream out) throws IOException, ClassNotFoundException {
      out.defaultWriteObject();
      out.writeObject(this.canonicalName);
   }

   private Object readResolve() throws ObjectStreamException {
      try {
         return new javax.management.ObjectName(this.canonicalName);
      } catch (MalformedObjectNameException var2) {
         return new Error(var2);
      }
   }
}
