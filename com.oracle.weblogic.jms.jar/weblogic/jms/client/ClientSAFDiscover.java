package weblogic.jms.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import weblogic.jms.extensions.ClientSAFFactory;
import weblogic.jms.safclient.ClientSAFImpl;

public class ClientSAFDiscover {
   private static final String HELP_OPT = "-help";
   private static final String ROOT_DIRECTORY_OPT = "-clientSAFRootDir";
   private static final String CUTOFF_TIME_FORMAT_OPT = "-cutoffFormat";
   private static final String CUTOFF_TIME_OPT = "-cutoffTime";
   private static final String DISCOVERY_FILE_OPT = "-discoveryFile";
   private static final String CONFIGURATION_FILE_OPT = "-configurationFile";
   private static Set supportedOptions = new HashSet();

   private static Map parseOptions(String[] args) {
      Map options = new HashMap();

      for(int i = 0; i < args.length; ++i) {
         String anOption = args[i];
         if (supportedOptions.contains(anOption)) {
            if ("-help".equals(anOption)) {
               options.put(anOption, "");
               break;
            }

            ++i;
            if (i >= args.length) {
               errorOut("missing option value for option " + anOption);
            }

            options.put(anOption, args[i]);
         } else {
            errorOut("unsupported option " + anOption);
         }
      }

      if (options.get("-clientSAFRootDir") != null && options.get("-configurationFile") == null) {
         errorOut("The configurationFile option must be specified when rootDir option is specified");
      }

      return options;
   }

   private static void discover(Map options) throws Throwable {
      long cutoffTime = -1L;
      String cutoffTimeOpt = (String)options.get("-cutoffTime");
      String cutoffFormatOpt;
      if (cutoffTimeOpt != null) {
         cutoffFormatOpt = (String)options.get("-cutoffFormat");
         if (cutoffFormatOpt == null) {
            cutoffFormatOpt = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
         }

         SimpleDateFormat cutoffFormat = new SimpleDateFormat(cutoffFormatOpt);

         try {
            cutoffTime = cutoffFormat.parse(cutoffTimeOpt).getTime();
         } catch (ParseException var13) {
            throwJMSException(var13, "The cutoff time option " + cutoffTimeOpt + " is not of " + cutoffFormatOpt + " format");
         }
      }

      cutoffFormatOpt = (String)options.get("-clientSAFRootDir");
      File rootDirectory = cutoffFormatOpt == null ? null : new File(cutoffFormatOpt);
      String configurationFileOpt = (String)options.get("-configurationFile");
      InputStream configIS = null;

      try {
         File configurationFile;
         if (configurationFileOpt != null) {
            configurationFile = new File(configurationFileOpt);
            if (!configurationFile.exists()) {
               throwJMSException((Throwable)null, "The configuration file " + configurationFileOpt + " does not exist");
            }

            configIS = new FileInputStream(configurationFile);
         }

         configurationFile = null;
         ClientSAFImpl clientSAF;
         if (rootDirectory == null) {
            if (configIS == null) {
               clientSAF = (ClientSAFImpl)ClientSAFFactory.getClientSAF();
            } else {
               clientSAF = (ClientSAFImpl)ClientSAFFactory.getClientSAF(configIS);
            }
         } else {
            clientSAF = (ClientSAFImpl)ClientSAFFactory.getClientSAF(rootDirectory, configIS);
         }

         String discoveryFileOpt = (String)options.get("-discoveryFile");
         clientSAF.discover(discoveryFileOpt, cutoffTime);
      } finally {
         if (configIS != null) {
            configIS.close();
         }

      }

   }

   public static void main(String[] args) {
      Map options = parseOptions(args);
      if (options.containsKey("-help")) {
         printUsage();
      } else {
         try {
            discover(options);
         } catch (Throwable var3) {
            var3.printStackTrace();
         }

      }
   }

   private static void throwJMSException(Throwable t, String errorMessage) throws JMSException {
      JMSException jmse = new JMSException(errorMessage);
      if (t != null) {
         jmse.initCause(t);
      }

      throw jmse;
   }

   private static void errorOut(String errorMsg) {
      System.out.println(errorMsg + "\n");
      printUsage();
      System.exit(1);
   }

   private static void printUsage() {
      String usageString = "Usage: java weblogic.jms.extensions.ClientSAFDiscover [options]\n\nThis command helps users to survey their existing local SAF messages\nbefore applying the fix for bug 8174629 or upgrading from WL 10.3.2 or\nprior releases to 11gR1PS2/10.3.3 or later. The survey can help determine\nwhether the upgrade needs to be tuned for non-default behavior.\nPlease check the description of bug 8174629 in the release note.\n\nThe options include:\n    -help             Print this usage information.\n    -clientSAFRootDir <client-saf-root-directory>  Optional, defaults to\n                      current directory. The client saf root directory of\n                      the target Client SAF that users want to discover.\n                      Any relative paths in the Client SAF configuration\n                      file are relative to this directory (for example,\n                      the store directory).\n    -configurationFile <configuration-file> Optional, defaults to\n                      \"ClientSAF.xml\". The location of the configuration\n                      file used for the target Client SAF. The configuration\n                      file is a well-formed xml file with respect to\n                      the weblogic-jmsmd.xsd schema and which has a \n                      root element of weblogic-client-jms. This option\n                      is required if the clientSAFRootDir option is specified.\n                      If neither clientSAFRootDir option nor this option is\n                      specified, SAFClientDiscover will use the\n                      \"ClientSAF.xml\" under the current working\n                      directory as the configuration file. In any case,\n                      Exception will be thrown if the specified\n                      configuration file does not exist.\n    -cutoffFormat <pattern> Optional, defaults to\n                      \"yyyy-MM-dd'T'HH:mm:ss.SSSZ\". The date and time\n                      pattern for the optional cutoff time of this\n                      ClientSAFDiscover. Check the javadoc for the\n                      java.text.SimpleDateFormat class for more\n                      information.\n    -cutoffTime <cutoff-time> Optional, defaults to \"not set\".\n                      Print data on messages that would be discarded\n                      during upgrade if the optional upgrade cut-off-time\n                      Java system property is set. Users can optionally\n                      specify a cutoff timestamp as a Java system property\n                      for the message upgrade to only upgrade messages\n                      sent after this timestamp. Messages before\n                      this timestamp will be discarded. No messages are\n                      discarded in the client SAF discovery process.\n                      The cutoff time format depends on the cutoffFormat\n                      option. For example, if the cutoffFormat is the default\n                      \"yyyy-MM-dd'T'HH:mm:ss.SSSZ\", an example cutoff\n                      time can be \"2009-12-16T10:34:17.887-0800\".\n                      An exception will be thrown if the specified cutoff\n                      time does not match the cutoffFormat pattern.\n                      If a cutoff time is not specified, the message\n                      upgrade will migrate all the messages and the\n                      SAFClientDiscover command line tool will not\n                      report any message to be discarded.\n    -discoveryFile <discovery-file> Optional, defaults to SAF_DISCOVERY.\n                      The discovery file contains the output for\n                      ClientSAFDiscover.  It is placed relative to the\n                      client SAF root directory unless an absolute path\n                      is specified. If the specified file already exists\n                      ClientSafDiscover deletes the file and creates a\n                      new one.\n\nExample:\nIf a program calls\n\nClientSAFFactory.getClientSAF(new File(\"c:\\\\foo\"),\n    new FileInputStream(\"c:\\\\ClientSAF-jms.xml\"));\n\nto create their client SAF, you can run the following\nClientSAFDiscover command to print out the automatic upgrade that\nwould occur(without actually causing an upgrade or changing any\nstored data):\n\njava weblogic.jms.client.ClientSAFDiscover -rootDir c:\\foo\n    -configurationFile c:\\ClientSAF-jms.xml\n\nThe discovery information will be written to the default location\nc:\\foo\\SAF_DISCOVERY.\n";
      System.out.println(usageString);
   }

   static {
      supportedOptions.add("-help");
      supportedOptions.add("-clientSAFRootDir");
      supportedOptions.add("-cutoffFormat");
      supportedOptions.add("-cutoffTime");
      supportedOptions.add("-discoveryFile");
      supportedOptions.add("-configurationFile");
   }
}
