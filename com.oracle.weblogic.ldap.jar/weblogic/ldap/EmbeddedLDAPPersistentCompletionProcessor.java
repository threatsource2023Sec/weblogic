package weblogic.ldap;

import javax.management.InvalidAttributeValueException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;
import weblogic.security.Salt;
import weblogic.utils.Hex;

public class EmbeddedLDAPPersistentCompletionProcessor implements ConfigurationProcessor {
   private static final int CRED_LEN = 13;
   private static String credential = null;

   public void updateConfiguration(DomainMBean domain) throws UpdateException {
      EmbeddedLDAPMBean config = domain.getEmbeddedLDAP();
      if (config == null) {
         throw new AssertionError("null embedded ldap mbean");
      } else if (config.getCredential() == null) {
         if (credential == null) {
            byte[] credRandom = Salt.getRandomBytes(13);
            credential = Hex.asHex(credRandom);
         }

         try {
            config.setCredential(credential);
         } catch (InvalidAttributeValueException var4) {
            throw new UpdateException(var4);
         }
      }
   }
}
