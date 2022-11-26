package weblogic.application.utils.annotation;

public interface MethodInfo extends Info {
   String getSignature();

   String[] getExceptions();
}
