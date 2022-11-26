package weblogic.i18n.tools;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import weblogic.utils.compiler.Tool;

public final class FixDates extends Tool {
   public static final String VERBOSE = "verbose";
   public static final String SERVER = "server";

   public static void main(String[] args) throws Exception {
      try {
         (new FixDates(args)).run();
      } catch (Exception var2) {
         System.err.println(var2.toString());
         System.exit(1);
      }

   }

   private FixDates(String[] args) {
      super(args);
   }

   public void prepare() {
      this.opts.addFlag("server", "Allow message ids in server range.");
      this.opts.addFlag("verbose", "Verbose output.");
      this.opts.addAlias("s", "server");
      this.opts.setUsageArgs("[filelist]");
   }

   public void runBody() throws Exception {
      boolean verbose = this.opts.hasOption("verbose");
      boolean server = this.opts.hasOption("server");
      String[] sources = this.opts.args();

      for(int i = 0; i < sources.length; ++i) {
         File srcFile = new File(sources[i]);
         if (verbose) {
            System.out.print("Reading: ");
         }

         System.out.println(srcFile.getCanonicalPath());
         if (srcFile.canRead() && sources[i].endsWith(".xml")) {
            Config cfg = new Config();
            cfg.setVerbose(verbose);
            cfg.setServer(server);
            MessageCatalogParser parser = new MessageCatalogParser(cfg, verbose);

            try {
               MessageCatalog msgcat = parser.parse(sources[i]);
               if (msgcat != null && fixDates(msgcat, srcFile)) {
                  try {
                     MessageCatalogWriter.writeCatalog(srcFile.getCanonicalPath(), msgcat);
                     System.out.println("Changed and wrote: " + srcFile.getCanonicalPath());
                  } catch (IOException var10) {
                     System.out.println("Unable to open " + srcFile.getCanonicalPath() + " for output.");
                  }
               }
            } catch (SAXException var11) {
               System.err.print(sources[i] + ": " + var11.getMessage());
               throw var11;
            } catch (ParserConfigurationException var12) {
               System.err.print(sources[i] + ": " + var12.getMessage());
               throw var12;
            } catch (IOException var13) {
               System.err.print(sources[i] + ": " + var13.getMessage());
               throw var13;
            }
         } else {
            System.out.println(" Failed to read file, or file name did not end with \".xml\"");
         }
      }

   }

   public static boolean fixDates(MessageCatalog cat, File srcFil) {
      boolean changedAtLeastOne = false;
      Vector masterMessages;
      if (cat.getCatType() == 2) {
         masterMessages = cat.getLogMessages();
      } else {
         masterMessages = cat.getMessages();
      }

      String masterFileDate = String.valueOf(srcFil.lastModified());
      Enumeration e = masterMessages.elements();

      while(true) {
         BasicMessage msg;
         String mdlc;
         do {
            if (!e.hasMoreElements()) {
               return changedAtLeastOne;
            }

            msg = (BasicMessage)e.nextElement();
            mdlc = msg.getDateLastChanged();
         } while(mdlc != null && msg.hashesOK());

         msg.setDateLastChanged(masterFileDate);

         try {
            cat.changeBasicMessage(msg, msg.getMessageId());
            changedAtLeastOne = true;
         } catch (WrongTypeException var9) {
            System.err.println("Message Id = " + msg.getMessageId());
            System.err.println(var9.toString());
         } catch (MessageNotFoundException var10) {
            System.err.println("Message Id = " + msg.getMessageId());
            System.err.println(var10.toString());
         }
      }
   }
}
