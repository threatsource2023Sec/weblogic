package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.jdo.JDOUserException;
import javax.jdo.spi.JDOImplHelper;

public class ObjectIdentity extends SingleFieldIdentity {
   private static JDOImplHelper helper = (JDOImplHelper)AccessController.doPrivileged(new PrivilegedAction() {
      public JDOImplHelper run() {
         return JDOImplHelper.getInstance();
      }
   });
   private static final String STRING_DELIMITER = ":";

   public ObjectIdentity(Class pcClass, Object param) {
      super(pcClass);
      this.assertKeyNotNull(param);
      String paramString = null;
      String keyString = null;
      String className = null;
      if (param instanceof String) {
         paramString = (String)param;
         if (paramString.length() < 3) {
            throw new JDOUserException(msg.msg("EXC_ObjectIdentityStringConstructionTooShort") + msg.msg("EXC_ObjectIdentityStringConstructionUsage", (Object)paramString));
         }

         int indexOfDelimiter = paramString.indexOf(":");
         if (indexOfDelimiter < 0) {
            throw new JDOUserException(msg.msg("EXC_ObjectIdentityStringConstructionNoDelimiter") + msg.msg("EXC_ObjectIdentityStringConstructionUsage", (Object)paramString));
         }

         keyString = paramString.substring(indexOfDelimiter + 1);
         className = paramString.substring(0, indexOfDelimiter);
         JDOImplHelper var10001 = helper;
         this.keyAsObject = JDOImplHelper.construct(className, keyString);
      } else {
         this.keyAsObject = param;
      }

      this.hashCode = this.hashClassName() ^ this.keyAsObject.hashCode();
   }

   public ObjectIdentity() {
   }

   public Object getKey() {
      return this.keyAsObject;
   }

   public String toString() {
      return this.keyAsObject.getClass().getName() + ":" + this.keyAsObject.toString();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         ObjectIdentity other = (ObjectIdentity)obj;
         return this.keyAsObject.equals(other.keyAsObject);
      }
   }

   public int hashCode() {
      return this.keyAsObject.hashCode();
   }

   public int compareTo(Object o) {
      if (o instanceof ObjectIdentity) {
         ObjectIdentity other = (ObjectIdentity)o;
         int result = super.compare(other);
         if (result == 0) {
            if (other.keyAsObject instanceof Comparable && this.keyAsObject instanceof Comparable) {
               return ((Comparable)this.keyAsObject).compareTo((Comparable)other.keyAsObject);
            } else {
               throw new ClassCastException("The key class (" + this.keyAsObject.getClass().getName() + ") does not implement Comparable");
            }
         } else {
            return result;
         }
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.keyAsObject);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.keyAsObject = in.readObject();
   }
}
