package weblogic.i18ntools.gui;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Vector;

final class InputArgs {
   static final String SERVER_ARG_NAME = "-server";
   private boolean myServer = false;
   private boolean myVerbose = false;
   private String[] myUnresolved = null;
   private String myFile = null;
   private boolean myHelp = false;
   private String myEncoding = null;

   boolean isVerbose() {
      return this.myVerbose;
   }

   boolean isAllowingServerIds() {
      return this.myServer;
   }

   String[] getUnresolvedArgs() {
      return this.myUnresolved;
   }

   String getFileName() {
      return this.myFile;
   }

   boolean getHelp() {
      return this.myHelp;
   }

   String getEncoding() {
      return this.myEncoding;
   }

   public InputArgs() {
   }

   public static InputArgs parse(String[] args, String[] defaults) throws IllegalArgumentException {
      InputArgs inpArgs = new InputArgs();
      Vector unresolved = new Vector();
      parseOneSet(inpArgs, unresolved, defaults);
      parseOneSet(inpArgs, unresolved, args);
      if (unresolved.size() == 0) {
         inpArgs.myUnresolved = null;
      } else {
         inpArgs.myUnresolved = new String[unresolved.size()];

         for(int i = 0; i < unresolved.size(); ++i) {
            inpArgs.myUnresolved[i] = (String)unresolved.elementAt(i);
         }
      }

      return inpArgs;
   }

   public static void parseOneSet(InputArgs inpArgs, Vector unresolved, String[] args) throws IllegalArgumentException {
      boolean needEncoding = false;

      for(int i = 0; i < args.length; ++i) {
         String thisArg = args[i];
         if (needEncoding) {
            try {
               setEncoding(inpArgs, thisArg);
            } catch (IllegalArgumentException var7) {
               System.err.println(var7.getMessage());
               unresolved.addElement(thisArg);
            }

            needEncoding = false;
         } else if (thisArg.equals("-server")) {
            inpArgs.myServer = true;
         } else if (thisArg.equals("-help")) {
            inpArgs.myHelp = true;
         } else if (thisArg.equals("-verbose")) {
            inpArgs.myVerbose = true;
         } else if (thisArg.equals("-encoding")) {
            needEncoding = true;
         } else if (!thisArg.startsWith("-")) {
            try {
               setFile(inpArgs, thisArg);
            } catch (IllegalArgumentException var8) {
               System.err.println(var8.getMessage());
               unresolved.addElement(thisArg);
            }
         } else {
            unresolved.addElement(thisArg);
         }
      }

   }

   private static void setEncoding(InputArgs inpArgs, String encoding) throws IllegalArgumentException {
      try {
         Charset.forName(encoding);
         inpArgs.myEncoding = encoding;
      } catch (Exception var4) {
         System.out.println("Supported character sets:");
         Iterator sets = Charset.availableCharsets().keySet().iterator();

         while(sets.hasNext()) {
            System.out.println((String)sets.next());
         }

         throw new IllegalArgumentException(var4.toString());
      }
   }

   private static void setFile(InputArgs inpArgs, String name) throws IllegalArgumentException {
      if (inpArgs.myFile == null) {
         inpArgs.myFile = name;
      } else {
         throw new IllegalArgumentException("Multiple files not allowed on command line");
      }
   }

   public void dump() {
      System.err.println("server: " + this.myServer);
      System.err.println("verbose: " + this.myVerbose);
      System.err.println("file: " + this.myFile);
   }
}
