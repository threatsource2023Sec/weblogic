package weblogic.jms.extensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.jms.JMSException;
import weblogic.jms.safclient.ClientSAFImpl;
import weblogic.kernel.KernelStatus;

public class ClientSAFFactory {
   private static final String DEFAULT_FILENAME = "ClientSAF.xml";

   public static ClientSAF getClientSAF() throws ClientSAFDuplicateException, JMSException {
      try {
         return getClientSAF(new FileInputStream(new File("ClientSAF.xml")));
      } catch (FileNotFoundException var1) {
         throw new weblogic.jms.common.JMSException(var1);
      }
   }

   public static ClientSAF getClientSAF(InputStream configuration) throws ClientSAFDuplicateException, JMSException {
      String userDirectory = System.getProperty("user.dir");
      if (userDirectory == null) {
         throw new JMSException("Cannot get the users current working directory");
      } else {
         return getClientSAF(new File(userDirectory), configuration);
      }
   }

   public static ClientSAF getClientSAF(File rootDirectory, InputStream configuration) throws ClientSAFDuplicateException, JMSException {
      if (KernelStatus.isServer()) {
         throw new JMSException("An attempt was made to create a SAF client on a WLS server");
      } else if (configuration == null) {
         throw new JMSException("Must have an input stream");
      } else {
         ClientSAFImpl retVal = new ClientSAFImpl(rootDirectory, configuration);
         return retVal;
      }
   }
}
