package weblogic.jms.backend;

import java.util.HashSet;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.TimedSecurityParticipant;
import weblogic.security.acl.internal.AuthenticatedSubject;

class BEProducerSecurityParticipantImpl implements TimedSecurityParticipant {
   private final int hashcode;
   private JMSID producerId;
   private BEDestinationImpl destination;
   private int lifeCount;
   private AuthenticatedSubject authenticatedSubject;

   BEProducerSecurityParticipantImpl(JMSID producerId, BEDestinationImpl destination, int lifeCount, AuthenticatedSubject subject) {
      this.producerId = producerId;
      this.destination = destination;
      this.lifeCount = lifeCount;
      this.authenticatedSubject = subject;
      int result = 19;
      result = 31 * result + (producerId != null ? producerId.hashCode() : 0);
      result = 31 * result + (destination != null ? destination.hashCode() : 0);
      this.hashcode = result;
   }

   JMSID getProducerId() {
      return this.producerId;
   }

   public synchronized AuthenticatedSubject getSubject() {
      return this.authenticatedSubject;
   }

   synchronized void setSubject(AuthenticatedSubject subject) {
      if (subject != null) {
         this.authenticatedSubject = subject;
      }

   }

   public void securityLapsed() {
      this.destination.removeProducer(this.producerId);
   }

   public boolean isClosed() {
      --this.lifeCount;
      if (this.lifeCount <= 0) {
         this.destination.removeProducer(this.producerId);
         return true;
      } else {
         return false;
      }
   }

   public HashSet getAcceptedDestinations() {
      return null;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other == null) {
         return false;
      } else if (!(other instanceof BEProducerSecurityParticipantImpl)) {
         return false;
      } else {
         boolean var10000;
         label48: {
            label32: {
               BEProducerSecurityParticipantImpl participant = (BEProducerSecurityParticipantImpl)other;
               if (participant.producerId != null) {
                  if (!participant.producerId.equals(this.producerId)) {
                     break label32;
                  }
               } else if (participant.producerId != this.producerId) {
                  break label32;
               }

               if (participant.destination != null) {
                  if (participant.destination.equals(this.destination)) {
                     break label48;
                  }
               } else if (participant.destination == this.destination) {
                  break label48;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      return this.hashcode;
   }
}
