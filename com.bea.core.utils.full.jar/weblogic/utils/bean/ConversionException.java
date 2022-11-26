package weblogic.utils.bean;

import weblogic.utils.NestedException;

public class ConversionException extends NestedException {
   private Object input;
   private Object output;
   private Object object;

   public Object getInput() {
      return this.input;
   }

   public void setInput(Object c) {
      this.input = c;
   }

   public Object getOutput() {
      return this.output;
   }

   public void setOutput(Object c) {
      this.output = c;
   }

   public Object getObject() {
      return this.object;
   }

   public void setObject(Object o) {
      this.object = o;
   }

   public ConversionException(Converter converter) {
      this(converter.getInput(), converter.getOutput());
   }

   public ConversionException(Converter converter, String msg) {
      super(msg);
      this.setInput(converter.getInput());
      this.setOutput(converter.getOutput());
   }

   public ConversionException(Converter converter, Throwable th) {
      super(th);
      this.setInput(converter.getInput());
      this.setOutput(converter.getOutput());
   }

   public ConversionException(Object input, Object output) {
      this(input, output, (Object)null, (Throwable)null);
   }

   public ConversionException(Object input, Object output, Object object) {
      this(input, output, object, (Throwable)null);
   }

   public ConversionException(Object input, Object output, Throwable th) {
      this(input, output, (Object)null, th);
   }

   public ConversionException(Object input, Object output, Object object, Throwable th) {
      super(th);
      this.input = input;
      this.output = output;
      this.object = object;
   }

   public ConversionException(String msg) {
      super(msg);
   }

   public ConversionException(String msg, Throwable th) {
      super(msg, th);
   }

   public String getMessage() {
      String msg = super.getMessage();
      if (msg != null) {
         return msg;
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("Couldn't convert ").append(this.input).append(" to ").append(this.output);
         Throwable th = this.getNestedException();
         if (th != null) {
            sb.append(": ").append(th.toString()).append(".");
         } else {
            sb.append(".");
         }

         return sb.toString();
      }
   }
}
