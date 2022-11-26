package weblogic.security.pki.revocation.common;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;

public final class CertRevocStatus {
   private static final String DATE_FORMAT = "EEE d MMM yyyy HH:mm:ss.SSS Z";
   private final CertRevocCheckMethodList.SelectableMethod sourceMethod;
   private final X500Principal subjectDn;
   private final X500Principal issuerDn;
   private final BigInteger serialNumber;
   private final Date thisUpdate;
   private final Date nextUpdate;
   private final boolean revoked;
   private final Boolean nonceIgnored;
   private final Object key;
   private final Map additionalProperties;

   CertRevocStatus(CertRevocCheckMethodList.SelectableMethod sourceMethod, X500Principal subjectDn, X500Principal issuerDn, BigInteger serialNumber, Date thisUpdate, Date nextUpdate, boolean revoked, Boolean nonceIgnored, Map additionalProperties) {
      Util.checkNotNull("sourceMethod", sourceMethod);
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("serialNumber", serialNumber);
      Util.checkNotNull("thisUpdate", thisUpdate);
      this.sourceMethod = sourceMethod;
      this.subjectDn = subjectDn;
      this.issuerDn = issuerDn;
      this.serialNumber = serialNumber;
      this.thisUpdate = thisUpdate;
      this.nextUpdate = nextUpdate;
      this.revoked = revoked;
      this.nonceIgnored = nonceIgnored;
      this.key = createKey(issuerDn, serialNumber);
      if (null == additionalProperties) {
         this.additionalProperties = null;
      } else {
         HashMap defensiveCopy = new HashMap(additionalProperties);
         this.additionalProperties = Collections.unmodifiableMap(defensiveCopy);
      }

   }

   public Object getKey() {
      return this.key;
   }

   public static Object createKey(X509Certificate x509Cert) {
      Util.checkNotNull("X509Certificate", x509Cert);
      X500Principal issuerDn = x509Cert.getIssuerX500Principal();
      BigInteger serialNumber = x509Cert.getSerialNumber();
      return null != issuerDn && null != serialNumber ? createKey(issuerDn, serialNumber) : null;
   }

   public static Object createKey(X500Principal issuerDn, BigInteger serialNumber) {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("serialNumber", serialNumber);
      StringBuilder sb = new StringBuilder(128);
      sb.append(issuerDn.getName());
      sb.append("|");
      sb.append(serialNumber.toString(16));
      return sb.toString();
   }

   public CertRevocCheckMethodList.SelectableMethod getSourceMethod() {
      return this.sourceMethod;
   }

   public X500Principal getSubjectDn() {
      return this.subjectDn;
   }

   public X500Principal getIssuerDn() {
      return this.issuerDn;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public Date getThisUpdate() {
      return this.thisUpdate;
   }

   public Date getNextUpdate() {
      return this.nextUpdate;
   }

   public boolean isRevoked() {
      return this.revoked;
   }

   public Boolean isNonceIgnored() {
      return this.nonceIgnored;
   }

   boolean isValid(int timeTolerance, int refreshPeriodPercent, LogListener log) {
      Util.checkTimeTolerance(timeTolerance);
      Util.checkRefreshPeriodPercent(refreshPeriodPercent);
      if (null == this.nextUpdate) {
         if (null != log && log.isLoggable(Level.FINEST)) {
            log.log(Level.FINEST, "Status not cachable for {0}: No next update indicated, continually updated.", this.getSubjectDn());
         }

         return false;
      } else if (this.thisUpdate.getTime() >= this.nextUpdate.getTime()) {
         if (null != log && log.isLoggable(Level.FINEST)) {
            log.log(Level.FINEST, "Invalid status for {0}: ThisUpdate {1} is after NextUpdate {2}.", this.getSubjectDn(), this.thisUpdate, this.nextUpdate);
         }

         return false;
      } else {
         long refreshPeriod = this.nextUpdate.getTime() - this.thisUpdate.getTime();
         long adjustedRefreshPeriod = refreshPeriod * (long)refreshPeriodPercent / 100L;
         Date adjustedNextUpdate = new Date(this.thisUpdate.getTime() + adjustedRefreshPeriod);
         Date tolerantThisUpdate = this.thisUpdate;
         if (timeTolerance > 0) {
            tolerantThisUpdate = new Date(this.thisUpdate.getTime() - (long)(timeTolerance * 1000));
         }

         Date tolerantNextUpdate = adjustedNextUpdate;
         if (timeTolerance > 0) {
            tolerantNextUpdate = new Date(adjustedNextUpdate.getTime() + (long)(timeTolerance * 1000));
         }

         Date now = new Date();
         if (now.before(tolerantThisUpdate)) {
            if (null != log && log.isLoggable(Level.FINEST)) {
               log.log(Level.FINEST, "Premature status for {0}: Now {1} is before tolerance-applied ThisUpdate {2}.", this.getSubjectDn(), now, tolerantThisUpdate);
            }

            return false;
         } else if (!now.before(tolerantNextUpdate)) {
            if (null != log && log.isLoggable(Level.FINEST)) {
               log.log(Level.FINEST, "Expired status for {0}: Now {1} is not before tolerance-applied NextUpdate {2}.", this.getSubjectDn(), now, tolerantNextUpdate);
            }

            return false;
         } else {
            return true;
         }
      }
   }

   public String toString() {
      return this.generateString();
   }

   private String generateString() {
      String revokedMsg = this.isRevoked() ? "REVOKED" : "NOT REVOKED";
      String subjectDnStr = this.getSubjectDn() != null ? this.getSubjectDn().getName() : null;
      String output1 = MessageFormat.format("\nStatus={0}\nSource={1}\nSubject=\"{2}\"\nIssuer=\"{3}\"\nSerialNumber={4}\nStatusValid={5,date,EEE d MMM yyyy HH:mm:ss.SSS Z}\nStatusExpires={6,date,EEE d MMM yyyy HH:mm:ss.SSS Z}", revokedMsg, this.getSourceMethod(), subjectDnStr, this.getIssuerDn().getName(), this.getSerialNumber().toString(16), this.getThisUpdate(), this.getNextUpdate());
      StringBuilder sb = new StringBuilder(256);
      sb.append(output1);
      if (null != this.nonceIgnored) {
         sb.append("\n");
         sb.append("NonceIgnored");
         sb.append("=");
         sb.append(this.nonceIgnored);
      }

      if (null != this.additionalProperties) {
         Set entrySet = this.additionalProperties.entrySet();
         if (null != entrySet) {
            Iterator var6 = entrySet.iterator();

            while(var6.hasNext()) {
               Map.Entry entry = (Map.Entry)var6.next();
               if (null != entry) {
                  sb.append("\n");
                  sb.append((String)entry.getKey());
                  sb.append("=");
                  sb.append((String)entry.getValue());
               }
            }
         }
      }

      sb.append("\n");
      return sb.toString();
   }

   public Map getAdditionalProperties() {
      return this.additionalProperties;
   }

   static String format(Date date) {
      if (null == date) {
         return null;
      } else {
         SimpleDateFormat format = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss.SSS Z");
         return format.format(date);
      }
   }
}
