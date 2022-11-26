package weblogic.application.descriptor;

import javax.xml.stream.XMLStreamException;

public class DeploymentPlanException extends XMLStreamException {
   public DeploymentPlanException(String message, Throwable t) {
      super(message, t);
   }

   public DeploymentPlanException(Throwable t) {
      super(t);
   }

   public DeploymentPlanException(String message) {
      super(message);
   }
}
