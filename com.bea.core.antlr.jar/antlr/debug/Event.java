package antlr.debug;

import java.util.EventObject;

public abstract class Event extends EventObject {
   private int type;

   public Event(Object var1) {
      super(var1);
   }

   public Event(Object var1, int var2) {
      super(var1);
      this.setType(var2);
   }

   public int getType() {
      return this.type;
   }

   void setType(int var1) {
      this.type = var1;
   }

   void setValues(int var1) {
      this.setType(var1);
   }
}
