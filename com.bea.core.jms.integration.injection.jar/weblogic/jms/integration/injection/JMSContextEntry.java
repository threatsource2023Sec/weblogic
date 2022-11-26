package weblogic.jms.integration.injection;

import java.io.Serializable;
import javax.jms.JMSContext;

public class JMSContextEntry implements Serializable {
   private static final long serialVersionUID = -3641068312684374062L;
   private final JMSContext context;

   public JMSContextEntry(JMSContext contextArg) {
      this.context = contextArg;
   }

   public JMSContext getContext() {
      return this.context;
   }
}
