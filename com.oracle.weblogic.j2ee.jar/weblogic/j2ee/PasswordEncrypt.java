package weblogic.j2ee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.utils.DescriptorUtils;
import weblogic.j2ee.descriptor.wl.ConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ConnectionParamsBean;
import weblogic.j2ee.descriptor.wl.ConnectionPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean;
import weblogic.j2ee.descriptor.wl.ParameterBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;

/** @deprecated */
@Deprecated
public final class PasswordEncrypt {
   public static boolean debug = false;

   public static void main(String[] args) {
      System.out.println("");
      System.out.println("** This utility is deprecated and has been replaced by weblogic.security.Encrypt **");
      System.out.println("");
      if (args != null && args.length != 2) {
         System.err.println("Correct usage: java weblogic.j2ee.PasswordEncrypt <descriptor file> <domain_config_dir>");
         System.err.println("<descriptor file) --  the application's weblogic-application.xml");
         System.err.println("<domain_config_dir) -- server domain directory");
         System.exit(-1);
      }

      if (debug) {
         System.out.println("Args: " + args[0] + " " + args[1]);
      }

      PasswordEncrypt noClearTxt = new PasswordEncrypt();
      File wlsApp = null;

      try {
         wlsApp = (new File(args[0])).getCanonicalFile();
      } catch (IOException var7) {
         System.err.println("Couldn't open " + args[0] + " " + var7.getMessage());
         System.exit(-1);
      } catch (SecurityException var8) {
         System.err.println("Couldn't open " + args[0] + " " + var8.getMessage());
         System.exit(-1);
      }

      EncryptionService es = null;
      ClearOrEncryptedService encrypter = null;

      try {
         es = SerializedSystemIni.getEncryptionService(args[1]);
         encrypter = new ClearOrEncryptedService(es);
      } catch (Exception var6) {
         System.err.println("Error obtaining EncryptionService using :" + args[1] + " " + var6.getMessage());
         var6.printStackTrace();
         System.exit(-1);
      }

      noClearTxt.findAndUpdateWLSpwd(wlsApp, encrypter);
      System.out.println("Password Encrypting Complete.  Please use file " + args[0] + " when creating your .ear file");
      System.exit(0);
   }

   private void findAndUpdateWLSpwd(File descriptor, ClearOrEncryptedService encrypter) {
      WeblogicApplicationBean wamb = null;
      ApplicationDescriptor appDesc = null;

      try {
         appDesc = new ApplicationDescriptor((InputStream)null, new FileInputStream(descriptor));
         wamb = appDesc.getWeblogicApplicationDescriptor();
      } catch (IOException var17) {
         System.err.println("IOException parsing descriptor file " + descriptor.getName());
         var17.printStackTrace();
         System.exit(-1);
      } catch (XMLStreamException var18) {
         System.err.println("Error parsing descriptor file " + descriptor.getName());
         var18.printStackTrace();
         System.exit(-1);
      }

      JDBCConnectionPoolBean[] poolOfPools = wamb.getJDBCConnectionPools();
      if (poolOfPools != null && poolOfPools.length > 0) {
         System.out.println("We have " + poolOfPools.length + " connectionPools defined");
         ConnectionFactoryBean cFactory = null;
         ConnectionPropertiesBean cprops = null;
         ConnectionParamsBean[] cparams = null;
         ParameterBean[] poolStuff = null;
         String topLevelPwd = null;
         String propsPwd = null;

         for(int i = 0; i < poolOfPools.length; ++i) {
            cFactory = poolOfPools[i].getConnectionFactory();
            cprops = cFactory.getConnectionProperties();
            topLevelPwd = cprops.getPassword();
            if (topLevelPwd != null) {
               if (debug) {
                  System.out.println("BEFORE(topLevelPwd): " + topLevelPwd);
               }

               topLevelPwd = encrypter.encrypt(topLevelPwd);
               cprops.setPassword(topLevelPwd);
               if (debug) {
                  System.out.println("AFTER(topLevelPwd): " + topLevelPwd);
               }
            }

            cparams = cprops.getConnectionParams();

            for(int k = 0; k < cparams.length; ++k) {
               poolStuff = cparams[k].getParameters();

               for(int j = 0; j < poolStuff.length; ++j) {
                  if (poolStuff[j].getParamName().equals("password")) {
                     propsPwd = poolStuff[j].getParamValue();
                     if (debug) {
                        System.out.println("BEFORE(propsPwd): " + propsPwd);
                     }

                     propsPwd = encrypter.encrypt(propsPwd);
                     poolStuff[j].setParamValue(propsPwd);
                     if (debug) {
                        System.out.println("AFTER(propsPwd): " + propsPwd);
                     }
                  }
               }
            }
         }

         if (topLevelPwd == null && propsPwd == null) {
            System.out.println("No ConnectionPool passwords defined");
         } else {
            try {
               DescriptorManager descMgr = new DescriptorManager();
               DescriptorBean bean = (DescriptorBean)appDesc.getWeblogicApplicationDescriptor();
               if (bean != null) {
                  DescriptorUtils.writeDescriptor(descMgr, bean, descriptor);
               }
            } catch (IOException var15) {
               System.err.println("IOException obtaining writing out new descriptor file: " + var15.getMessage());
               System.exit(-1);
            } catch (XMLStreamException var16) {
               System.err.println("XMLStreamException writing out new descriptor file: " + var16.getMessage());
               System.exit(-1);
            }

         }
      } else {
         System.out.println("There are no connectionPools to process.");
      }
   }
}
