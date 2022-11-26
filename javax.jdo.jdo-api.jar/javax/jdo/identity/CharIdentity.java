package javax.jdo.identity;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jdo.spi.I18NHelper;

public class CharIdentity extends SingleFieldIdentity {
   private static I18NHelper msg = I18NHelper.getInstance("javax.jdo.Bundle");
   private char key;

   private void construct(char key) {
      this.key = key;
      this.hashCode = this.hashClassName() ^ key;
   }

   public CharIdentity(Class pcClass, char key) {
      super(pcClass);
      this.construct(key);
   }

   public CharIdentity(Class pcClass, Character key) {
      super(pcClass);
      this.setKeyAsObject(key);
      this.construct(key);
   }

   public CharIdentity(Class pcClass, String str) {
      super(pcClass);
      this.assertKeyNotNull(str);
      if (str.length() != 1) {
         throw new IllegalArgumentException(msg.msg("EXC_StringWrongLength"));
      } else {
         this.construct(str.charAt(0));
      }
   }

   public CharIdentity() {
   }

   public char getKey() {
      return this.key;
   }

   public String toString() {
      return String.valueOf(this.key);
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         CharIdentity other = (CharIdentity)obj;
         return this.key == other.key;
      }
   }

   public int compareTo(Object o) {
      if (o instanceof CharIdentity) {
         CharIdentity other = (CharIdentity)o;
         int result = super.compare(other);
         return result == 0 ? this.key - other.key : result;
      } else if (o == null) {
         throw new ClassCastException("object is null");
      } else {
         throw new ClassCastException(this.getClass().getName() + " != " + o.getClass().getName());
      }
   }

   protected Object createKeyAsObject() {
      return new Character(this.key);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeChar(this.key);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.key = in.readChar();
   }
}
