package weblogic.management.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import javax.management.Attribute;

/** @deprecated */
@Deprecated
public class WebLogicAttribute extends Attribute {
   public static final long serialVersionUID = 216628355171837704L;
   public static final Object NULL_VALUE;

   public WebLogicAttribute(String key, Object value) {
      super(key, value == null ? NULL_VALUE : value);
   }

   public Object getValue() {
      Object result = super.getValue();
      return result == NULL_VALUE ? null : result;
   }

   static {
      NULL_VALUE = WebLogicAttribute.NullObject.it;
   }

   public static final class NullObject implements Serializable {
      static NullObject it = new NullObject();
      private static final long serialVersionUID = -4024808658342520589L;

      public boolean equals(Object o) {
         return o instanceof NullObject;
      }

      public int hashCode() {
         return 0;
      }

      public Object clone() {
         return it;
      }

      private Object readResolve() throws ObjectStreamException {
         return it;
      }
   }
}
