package weblogic.application;

public class CannotRedeployException extends NonFatalDeploymentException {
   static final long serialVersionUID = 2960878996294897858L;

   public CannotRedeployException(String s) {
      super(s);
   }
}
