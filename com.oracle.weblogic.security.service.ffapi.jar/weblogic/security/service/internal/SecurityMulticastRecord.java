package weblogic.security.service.internal;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class SecurityMulticastRecord implements Serializable {
   String origin = null;
   String realmName = null;
   int sequence_number = 0;
   long timestamp = 0L;

   SecurityMulticastRecord(String eventOrigin, String eventRealmName, int eventSequenceNumber, long eventTime) {
      this.origin = eventOrigin;
      this.realmName = eventRealmName;
      this.sequence_number = eventSequenceNumber;
      this.timestamp = eventTime;
   }

   String eventOrigin() {
      return this.origin;
   }

   String eventRealmName() {
      return this.realmName;
   }

   int eventSequenceNumber() {
      return this.sequence_number;
   }

   long eventTime() {
      return this.timestamp;
   }

   public String toString() {
      DateFormat med = DateFormat.getDateTimeInstance(2, 2);
      return "SecurityMulticastRecord: origin: " + this.origin + " realmName: " + this.realmName + " seqnum: " + this.sequence_number + " timestamp: " + med.format(new Date(this.timestamp));
   }
}
