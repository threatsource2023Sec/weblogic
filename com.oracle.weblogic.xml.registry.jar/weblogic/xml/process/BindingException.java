package weblogic.xml.process;

public class BindingException extends SAXProcessorException {
   public BindingException(String msg) {
      super(msg);
   }

   public BindingException(Exception e) {
      super(e);
   }

   public BindingException(String msg, Exception e) {
      super(msg, e);
   }
}
