package weblogic.jms.adapter;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.SecurityException;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;

public class Util {
   public static PasswordCredential getPasswordCredential(ManagedConnectionFactory mcf, final Subject subject, ConnectionRequestInfo info) throws ResourceException {
      JMSConnectionRequestInfo myinfo = (JMSConnectionRequestInfo)info;
      if (info != null && myinfo.getUser() != null && myinfo.getPassword() != null) {
         PasswordCredential pc = new PasswordCredential(myinfo.getUser(), myinfo.getPassword().toCharArray());
         pc.setManagedConnectionFactory(mcf);
         return pc;
      } else if (subject == null) {
         return null;
      } else {
         Set creds = (Set)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return subject.getPrivateCredentials(PasswordCredential.class);
            }
         });
         PasswordCredential pc = null;
         Iterator iter = creds.iterator();

         while(iter.hasNext()) {
            PasswordCredential temp = (PasswordCredential)iter.next();
            if (temp.getManagedConnectionFactory().equals(mcf)) {
               pc = temp;
               break;
            }
         }

         if (pc == null) {
            throw new SecurityException("No PasswordCredential found");
         } else {
            return pc;
         }
      }
   }

   public static boolean isEqual(String a, String b) {
      if (a == null) {
         return b == null;
      } else {
         return a.equals(b);
      }
   }
}
