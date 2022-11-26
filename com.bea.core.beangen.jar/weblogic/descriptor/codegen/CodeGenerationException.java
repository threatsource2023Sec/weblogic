package weblogic.descriptor.codegen;

public class CodeGenerationException extends Exception {
   public CodeGenerationException() {
   }

   public CodeGenerationException(String msg) {
      super(msg);
   }

   public CodeGenerationException(Throwable th) {
      super(th);
   }

   public CodeGenerationException(String msg, Throwable th) {
      super(msg, th);
   }
}
