package weblogic.utils.classloaders.debug;

import java.text.SimpleDateFormat;
import java.util.Date;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

abstract class Record {
   private final Thread thread = Thread.currentThread();
   private final long timestamp = System.currentTimeMillis();
   protected final Annotation annotation;
   protected final int hashCode;
   protected final SupportedClassLoader instanceClassName;
   protected final SupportedClassLoader recordClassName;
   protected final String methodName;
   protected final String arg;
   private static final SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy KK:mm:ss.SSS a z");

   Record(GenericClassLoader instance, SupportedClassLoader clz, String methodName, String arg) {
      this.annotation = instance.getAnnotation();
      this.hashCode = instance.hashCode();
      this.instanceClassName = SupportedClassLoader.fromClass(instance.getClass());
      this.recordClassName = clz;
      this.methodName = methodName;
      this.arg = arg;
   }

   Thread getThread() {
      return this.thread;
   }

   void toString(StringBuilder builder) {
      fillBasicRecord(builder, this.instanceClassName.toString(), this.hashCode, this.timestamp);
      builder.append('[');
      if (this.annotation != null) {
         builder.append(this.annotation.getDescription());
      }

      builder.append(']');
      builder.append(' ').append(this.recordClassName).append('.').append(this.methodName).append('(');
      builder.append(this.arg);
      builder.append(")");
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      this.toString(builder);
      return builder.toString();
   }

   public static void fillBasicRecord(StringBuilder builder, String instanceClassName, int hashCode, long timestamp) {
      builder.append('<');
      builder.append(format.format(new Date(timestamp)));
      builder.append('>').append(' ');
      builder.append(instanceClassName);
      builder.append('[').append(Integer.toHexString(hashCode)).append(']');
   }
}
