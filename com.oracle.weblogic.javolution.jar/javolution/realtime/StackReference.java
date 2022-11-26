package javolution.realtime;

import javolution.lang.Reference;
import javolution.lang.Text;

public final class StackReference extends RealtimeObject implements Reference {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      protected Object create() {
         return new StackReference();
      }

      protected void cleanup(Object var1) {
         StackReference.access$102((StackReference)var1, (Object)null);
      }
   };
   private Object _value;

   private StackReference() {
   }

   public static StackReference newInstance() {
      return (StackReference)FACTORY.object();
   }

   public Text toText() {
      return Text.valueOf(this.get());
   }

   public Object get() {
      return this._value;
   }

   public void set(Object var1) {
      this._value = var1;
   }

   StackReference(Object var1) {
      this();
   }

   static Object access$102(StackReference var0, Object var1) {
      return var0._value = var1;
   }
}
