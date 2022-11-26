package weblogic.utils.classloaders.debug;

import weblogic.utils.classloaders.GenericClassLoader;

class MessageRecord extends Record {
   private final String message;
   private final Object[] params;

   MessageRecord(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg, String message, Object... params) {
      super(instance, clz, methodName, arg);
      this.message = message;
      this.params = params;
   }

   void toString(StringBuilder builder) {
      super.toString(builder);
      builder.append(": ");
      builder.append(this.message);
      if (this.params != null && this.params.length > 0) {
         builder.append(": ");
         builder.append(this.params[0] == null ? "null" : this.params[0].toString());

         for(int i = 1; i < this.params.length; ++i) {
            builder.append(", ");
            builder.append(this.params[i] == null ? "null" : this.params[i].toString());
         }
      }

   }
}
