package weblogic.jms.extensions;

import javax.jms.Destination;
import weblogic.jms.common.JMSException;

public final class SequenceGapException extends JMSException {
   static final long serialVersionUID = -2031233154467385324L;
   private Destination destination;
   private int missingCount;

   public SequenceGapException(String reason, String errorCode, Destination destination, int missingCount) {
      super(reason, errorCode);
      this.destination = destination;
      this.missingCount = missingCount;
   }

   public SequenceGapException(String reason, Destination destination, int missingCount) {
      super(reason);
      this.destination = destination;
      this.missingCount = missingCount;
   }

   public Destination getJMSDestination() {
      return this.destination;
   }

   public int getMissingCount() {
      return this.missingCount;
   }

   public boolean isInformational() {
      return true;
   }
}
