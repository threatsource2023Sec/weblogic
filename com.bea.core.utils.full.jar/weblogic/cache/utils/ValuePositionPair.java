package weblogic.cache.utils;

import java.io.Serializable;

public class ValuePositionPair implements Serializable {
   private static final long serialVersionUID = -5481419839863890159L;
   private Object value;
   private int position;

   public void setValue(Object value) {
      this.value = value;
   }

   public Object getValue() {
      return this.value;
   }

   public void setPosition(int position) {
      this.position = position;
   }

   public int getPosition() {
      return this.position;
   }

   public String toString() {
      return "[ " + this.position + ": " + this.value + " ]";
   }
}
