package weblogic.security.psm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.Permission;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.security.auth.Subject;

public class PrintingSecurityManager extends SecurityManager {
   boolean logAccess = false;
   boolean logFailure = false;
   static final String ACCESS = "ACCESS";
   static final String FAILURE = " failure";
   private static String newLine = "\n";
   private static ThreadLocal callDepth = new CallDepth();
   public static boolean firstInvocationHappened = false;
   static boolean needToConfigureOutput = true;
   static PrintStream out;
   Map uniquePDPs;
   static PrintWriter missingPermissionsWriter;
   static PrintWriter generatedGrantsWriter;
   static PrintWriter defaultMissingPermissionsWriter;
   static PrintWriter defaultGeneratedGrantsWriter;
   static final String MSG_TYPE_GRANT = "GRANTS";
   static final String MSG_TYPE_PERMISSION = "PERMISSIONS";
   public static final String PRINTING_SECURITY_MANAGER_OUTPUT_FILE = "oracle.weblogic.security.manager.printing.file";
   public static final String PRINTING_SECURITY_MANAGER_GENERATED_GRANTS_FILE = "oracle.weblogic.security.manager.printing.generated.grants.file";

   public static void setNeedToConfigureOutput(boolean needToConfigureOutput) {
      PrintingSecurityManager.needToConfigureOutput = needToConfigureOutput;
      log("", "PERMISSIONS");
   }

   public static void setMissingPermissionsWriter(PrintWriter missingPermissionsWriter) {
      PrintingSecurityManager.missingPermissionsWriter = missingPermissionsWriter;
   }

   public static void setGeneratedGrantsWriter(PrintWriter generatedGrantsWriter) {
      PrintingSecurityManager.generatedGrantsWriter = generatedGrantsWriter;
   }

   public static void setOut(PrintStream newOut) {
      out = newOut;
   }

   public int getLoggableMsgCount() {
      return this.uniquePDPs.size();
   }

   public void clearPDPs() {
      this.uniquePDPs.clear();
   }

   public PrintingSecurityManager() {
      if (!firstInvocationHappened) {
         firstInvocationHappened = true;
         out.println("*** PrintingSecurityManager is not to be used in Production Environment. It is intended solely for development to identify missing permissions. ***");
         this.configurePrintingSecurityManager();
      }

      this.uniquePDPs = new WeakHashMap();
   }

   public void checkPermission(final Permission perm, Object context) {
      try {
         if (0 == ((CallDepth)callDepth.get()).increment()) {
            super.checkPermission(perm, context);
         }
      } catch (final AccessControlException var9) {
         final Subject subject = Subject.getSubject(AccessController.getContext());
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PrintingSecurityManager.this.handleException(var9, PrintingSecurityManager.this.getClassContext(), subject, perm);
               return null;
            }
         });
      } catch (final StackOverflowError var10) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PrintingSecurityManager.this.processStackOverflow(var10);
               return null;
            }
         });
      } finally {
         ((CallDepth)callDepth.get()).decrement();
      }

   }

   public void checkPermission(final Permission perm) {
      try {
         if (0 == ((CallDepth)callDepth.get()).increment()) {
            super.checkPermission(perm);
         }
      } catch (final AccessControlException var8) {
         final Subject subject = Subject.getSubject(AccessController.getContext());
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PrintingSecurityManager.this.handleException(var8, PrintingSecurityManager.this.getClassContext(), subject, perm);
               return null;
            }
         });
      } catch (final StackOverflowError var9) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               PrintingSecurityManager.this.processStackOverflow(var9);
               return null;
            }
         });
      } finally {
         ((CallDepth)callDepth.get()).decrement();
      }

   }

   private void processStackOverflow(StackOverflowError soe) {
      String policyFile = System.getProperty("java.security.policy");
      String msg = "";
      if (policyFile == null) {
         msg = "please set java.security.policy system property";
      } else if (!(new File(policyFile)).exists()) {
         msg = "please check java.security.policy system property, the specifed file does not exist.";
      }

      String oracleHome = System.getProperty("oracle.home");
      if (oracleHome == null) {
         msg = msg + "\nPlease check the value of oracle.home  system property";
      }

      System.out.println("SEVERE ERROR: The stackoverflow error happened, " + msg);
      soe.printStackTrace();
      System.exit(-1);
   }

   public StringBuffer getBatchFormattedPermissionGrants() {
      Iterator itr = this.uniquePDPs.keySet().iterator();
      StringBuffer grantBuffer = new StringBuffer();

      while(itr.hasNext()) {
         PDP pdp = (PDP)itr.next();
         grantBuffer.append(newLine);
         grantBuffer.append(this.getFormattedPermissionGrant(pdp.pd, pdp.p));
      }

      return grantBuffer;
   }

   public StringBuffer getFormattedPermissionGrant(ProtectionDomain pd, Permission p) {
      StringBuffer gb = new StringBuffer();
      gb.append("grant codeBase \"");
      gb.append(pd.getCodeSource().getLocation());
      gb.append("\" ");
      gb.append("{\n    ");
      gb.append(this.formatPermission(p));
      gb.append("};");
      return gb;
   }

   private String escapeQuotes(String str) {
      return str.indexOf("\"") > -1 ? str.replaceAll("\"", "\\\"") : str;
   }

   private String extractClassName(Permission p) {
      String original = p.getClass().toString();
      return original.substring(original.indexOf(" ")).trim();
   }

   private void handleException(AccessControlException ace, Class[] frames, Subject subject, Permission p) {
      if (needToConfigureOutput) {
         this.configurePrintingSecurityManager();
      }

      Principal[] principals = null;

      for(int j = 0; j < frames.length; ++j) {
         Class c = frames[j];
         ProtectionDomain pd = c.getProtectionDomain();
         if (!pd.implies(p)) {
            if (null != this.createUniquePDP(pd, p)) {
               StringBuffer msg = this.createMissingPermissionsMessage(pd, (Principal[])null, subject, p);
               log(msg.toString(), "PERMISSIONS");
               log(this.getFormattedPermissionGrant(pd, p).toString(), "GRANTS");
            }

            return;
         }

         principals = this.mergePrincipals(principals, pd.getPrincipals());
      }

      StringBuffer msg = this.createMissingPermissionsMessage((ProtectionDomain)null, principals, subject, ace.getPermission());
      log(msg.toString(), "PERMISSIONS");
   }

   Principal[] mergePrincipals(Principal[] principals, Principal[] pdPrincipals) {
      if (null != pdPrincipals && 0 != pdPrincipals.length) {
         if (null != principals && 0 != principals.length) {
            int covered = 0;

            for(int i = 0; i < pdPrincipals.length; ++i) {
               for(int j = 0; j < principals.length; ++j) {
                  if (principals[j] == pdPrincipals[i]) {
                     ++covered;
                     break;
                  }
               }
            }

            if (pdPrincipals.length == covered) {
               return principals;
            } else {
               Principal[] merged = new Principal[principals.length + pdPrincipals.length];
               System.arraycopy(principals, 0, merged, 0, principals.length);
               System.arraycopy(pdPrincipals, 0, merged, principals.length, pdPrincipals.length);
               return merged;
            }
         } else {
            return pdPrincipals;
         }
      } else {
         return principals;
      }
   }

   private StringBuffer createMissingPermissionsMessage(ProtectionDomain pd, Principal[] principals, Subject subject, Permission p) {
      StringBuffer msg = new StringBuffer();
      if (null != pd) {
         msg.append("CodeSource: " + pd.getCodeSource());
         msg.append((new StringBuffer()).append("\n running as ").append(formatPrincipal(pd.getPrincipals())).toString());
         msg.append(" \n needs ");
         msg.append(this.formatPermission(p));
         msg.append(" to avoid AccessControlException");
      } else {
         msg.append("Subject ");
         msg.append(formatPrincipal(subject));
         msg.append(" running as ");
         msg.append(formatPrincipal(principals));
         msg.append(" \n needs ");
         msg.append(this.formatPermission(p));
         msg.append(" to avoid AccessControlException");
      }

      return msg;
   }

   public PDP createUniquePDP(ProtectionDomain pd, Permission p) {
      PDP pdp = new PDP(pd, p);
      boolean contains = this.uniquePDPs.containsValue(pdp);
      if (!contains) {
         this.uniquePDPs.put(pdp, pdp);
         return pdp;
      } else {
         return null;
      }
   }

   public static StringBuffer formatPrincipal(Principal[] principals) {
      StringBuffer retVal = new StringBuffer();
      if (null != principals && principals.length != 0) {
         boolean first = true;
         retVal.append(" [ ");

         for(int i = 0; i < principals.length; ++i) {
            Principal p = principals[i];
            if (!first) {
               retVal.append(", ");
            } else {
               first = false;
            }

            retVal.append(p.getClass().getName());
            retVal.append(" \"");
            retVal.append(p.getName());
            retVal.append("\"");
         }

         retVal.append(" ]");
      } else {
         retVal.append(" [ null ]");
      }

      return retVal;
   }

   public static StringBuffer formatPrincipal(Subject subject) {
      StringBuffer retVal = new StringBuffer();
      if (null != subject && subject.getPrincipals().size() != 0) {
         boolean first = true;
         retVal.append(" [ ");
         Iterator iter = subject.getPrincipals().iterator();

         while(iter.hasNext()) {
            Principal p = (Principal)iter.next();
            if (!first) {
               retVal.append(", ");
            } else {
               first = false;
            }

            retVal.append(p.getClass().getName());
            retVal.append(" \"");
            retVal.append(p.getName());
            retVal.append("\"");
         }

         retVal.append(" ]");
      } else {
         retVal.append(" [ null ]");
      }

      return retVal;
   }

   StringBuffer formatPermission(Permission p) {
      StringBuffer gb = new StringBuffer();
      gb.append("permission ");
      gb.append(this.extractClassName(p));
      gb.append(" \"");
      gb.append(this.escapeQuotes(p.getName()));
      gb.append("\"");
      if (p.getActions() != null) {
         gb.append(", \"");
         gb.append(p.getActions());
         gb.append("\"");
      }

      gb.append(";\n");
      return gb;
   }

   static void log(String msg, String type) {
      if (type.equalsIgnoreCase("PERMISSIONS")) {
         if (missingPermissionsWriter == null) {
            defaultMissingPermissionsWriter.println(msg);
         } else {
            missingPermissionsWriter.println(msg);
            missingPermissionsWriter.flush();
         }
      } else if (type.equalsIgnoreCase("GRANTS")) {
         if (generatedGrantsWriter == null) {
            defaultGeneratedGrantsWriter.println(msg);
         } else {
            generatedGrantsWriter.println(msg);
            generatedGrantsWriter.flush();
         }
      }

   }

   protected void finalize() throws Throwable {
      this.flush();
   }

   void flush() {
      try {
         missingPermissionsWriter.flush();
         generatedGrantsWriter.flush();
         defaultGeneratedGrantsWriter.flush();
         defaultMissingPermissionsWriter.flush();
      } catch (Exception var5) {
      } finally {
         missingPermissionsWriter.close();
         generatedGrantsWriter.close();
      }

   }

   private void configurePrintingSecurityManager() {
      needToConfigureOutput = false;

      try {
         String generatedGrantsFile;
         if (missingPermissionsWriter == null) {
            generatedGrantsFile = System.getProperty("oracle.weblogic.security.manager.printing.file");
            if (generatedGrantsFile != null) {
               try {
                  setMissingPermissionsWriter(new PrintWriter(new BufferedWriter(new FileWriter(generatedGrantsFile))));
               } catch (FileNotFoundException var4) {
                  System.out.print("Could not open file specified via oracle.weblogic.security.manager.printing.file system property,  PrintingSecurityManager will log to System.out");
               }
            }
         }

         if (generatedGrantsWriter == null) {
            generatedGrantsFile = System.getProperty("oracle.weblogic.security.manager.printing.generated.grants.file");
            if (generatedGrantsFile != null) {
               try {
                  setGeneratedGrantsWriter(new PrintWriter(new BufferedWriter(new FileWriter(generatedGrantsFile))));
               } catch (FileNotFoundException var3) {
                  System.out.print("Could not open file specified via oracle.weblogic.security.manager.printing.generated.grants.file system property,  PrintingSecurityManager will not generate permission grants");
               }
            }
         }
      } catch (IOException var5) {
         System.out.println("Exception opening oracle.weblogic.security.manager.printing.file\n or oracle.weblogic.security.manager.printing.generated.grants.file");
         var5.printStackTrace();
      }

   }

   void logThis(String concern, String message) {
      String securityDebug = System.getProperty("java.security.debug");
      if (securityDebug != null) {
         if (concern.equalsIgnoreCase("ACCESS")) {
            if (!this.logAccess && securityDebug.indexOf("ACCESS") != -1) {
               this.logAccess = true;
            }

            if (this.logAccess) {
               System.out.println("PSM->" + message);
            }
         }

         if (concern.equalsIgnoreCase(" failure")) {
            if (!this.logFailure && securityDebug.indexOf(" failure") != -1) {
               this.logFailure = true;
            }

            if (this.logFailure) {
               System.err.println("PSM->" + message);
            }
         }

      }
   }

   static {
      out = System.out;
      missingPermissionsWriter = null;
      generatedGrantsWriter = null;
      defaultMissingPermissionsWriter = new PrintWriter(System.out, true);
      defaultGeneratedGrantsWriter = new PrintWriter(System.out, true);
   }

   private static class CallDepth extends InheritableThreadLocal {
      private int callDepth;

      private CallDepth() {
         this.callDepth = 0;
      }

      public int increment() {
         return this.callDepth++;
      }

      public int decrement() {
         return --this.callDepth;
      }

      protected synchronized Object initialValue() {
         return new CallDepth();
      }

      // $FF: synthetic method
      CallDepth(Object x0) {
         this();
      }
   }
}
