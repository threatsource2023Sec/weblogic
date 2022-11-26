package weblogic.security.service.internal;

import java.util.Vector;
import weblogic.security.auth.callback.IdentityDomainNames;

public final class InvalidLogin {
   private String user_name;
   private long locked_timestamp;
   private Vector failure_records;
   private String identity_domain;

   public InvalidLogin(String userName, String identityDomain) {
      this.user_name = userName;
      this.identity_domain = identityDomain;
      this.locked_timestamp = 0L;
      this.failure_records = new Vector();
   }

   public InvalidLogin() {
      this.user_name = null;
      this.identity_domain = null;
      this.locked_timestamp = 0L;
      this.failure_records = new Vector();
   }

   String getName() {
      return this.user_name;
   }

   void setUser(IdentityDomainNames user) {
      this.erase();
      this.user_name = user.getName();
      this.identity_domain = user.getIdentityDomain();
   }

   String getIdentityDomain() {
      return this.identity_domain;
   }

   int getFailureCount() {
      return this.failure_records.size();
   }

   long getLockedTimestamp() {
      return this.locked_timestamp;
   }

   void setLockedTimestamp(long lockedTime) {
      this.locked_timestamp = lockedTime;
   }

   Vector getFailures() {
      return this.failure_records;
   }

   void erase() {
      this.user_name = null;
      this.identity_domain = null;
      this.locked_timestamp = 0L;
      this.failure_records.removeAllElements();
   }

   void addFailure(Object failureRecord) {
      this.failure_records.addElement(failureRecord);
   }

   public String toString() {
      String formattedName = this.identity_domain != null && !this.identity_domain.isEmpty() ? this.user_name + " [" + this.identity_domain + "]" : this.user_name;
      return formattedName + " " + this.locked_timestamp + " " + this.failure_records.size();
   }

   public Object getLatestFailure() {
      return this.failure_records.size() == 0 ? null : this.failure_records.lastElement();
   }
}
