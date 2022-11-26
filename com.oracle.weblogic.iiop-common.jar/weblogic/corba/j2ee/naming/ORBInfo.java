package weblogic.corba.j2ee.naming;

import javax.security.auth.Subject;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.omg.CORBA.ORB;

public class ORBInfo {
   private ORB orb;
   private String key;

   public ORBInfo(ORB orb, String key) {
      this.orb = orb;
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }

   public void setTransaction(UserTransaction ut) throws SystemException {
   }

   public void setSubject(Subject subject) {
   }

   public ORB getORB() {
      return this.orb;
   }

   public void removeClientSecurityContext() {
   }
}
