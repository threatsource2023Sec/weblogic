package weblogic.opatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;

public class OPatchUtil {
   static final String OPATCH_PATTERNS_FILE_PROP = "weblogic.opatch.patternsfile";
   private static final String APPLIED_DATE_PROP = "APPLIED_DATE";
   private static final String PATCH_ID_PROP = "PATCH_ID";
   private static final String PATCH_DESC_PROP = "PATCH_DESC";
   private static final String DEFAULT_APPLIED_DATE_PATTERN = "^Patch\\s*([^\\s]*)\\s*: applied on (.*)";
   private static final String DEFAULT_PATCH_ID_PATTERN = "^Unique Patch ID:\\s*([^\\s]*)";
   private static final String DEFAULT_PATCH_DESC_PATTERN = "^Patch description:\\s*\"?([^\"]*)\"?$";
   private static final String SEP = ";";
   public static final String MSG_NO_PATCHES = "No patches installed";
   public static final String ERR_NOT_FOUND = "ERROR: OPatch executable not found";
   public static final String ERR_NOT_EXECUTABLE = "ERROR: OPatch not executable";
   public static final String ERR_EXECUTION_ERROR = "ERROR: Failed to collect patch information.";
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugOPatchUtils");
   private static OPatchUtil SINGLETON;
   private Pattern pat_appliedDate;
   private Pattern pat_patchId;
   private Pattern pat_patchDesc;
   private String[] patchInfos;

   private OPatchUtil() {
      Properties props = this.getOPatchPatterns();
      this.pat_appliedDate = Pattern.compile(props.getProperty("APPLIED_DATE"));
      this.pat_patchId = Pattern.compile(props.getProperty("PATCH_ID"));
      this.pat_patchDesc = Pattern.compile(props.getProperty("PATCH_DESC"));
   }

   private Properties getOPatchPatterns() {
      Properties props = new Properties();
      String patternsFilePath = System.getProperty("weblogic.opatch.patternsfile");
      InputStream in = null;

      try {
         if (patternsFilePath != null) {
            File patternsFile = new File(patternsFilePath);
            if (patternsFile.exists()) {
               in = new FileInputStream(patternsFile);
               props.load(in);
            } else if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Specified patterns file " + patternsFilePath + " does not exist.");
            }
         }
      } catch (IOException var13) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Specified patterns file " + patternsFilePath + " cannot be opened.");
         }
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (Exception var12) {
            }
         }

      }

      if (props.getProperty("APPLIED_DATE") == null) {
         props.setProperty("APPLIED_DATE", "^Patch\\s*([^\\s]*)\\s*: applied on (.*)");
      }

      if (props.getProperty("PATCH_ID") == null) {
         props.setProperty("PATCH_ID", "^Unique Patch ID:\\s*([^\\s]*)");
      }

      if (props.getProperty("PATCH_DESC") == null) {
         props.setProperty("PATCH_DESC", "^Patch description:\\s*\"?([^\"]*)\"?$");
      }

      return props;
   }

   public static synchronized OPatchUtil getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new OPatchUtil();
      }

      return SINGLETON;
   }

   private List gatherPatchInfo(InputStream in) throws IOException {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      List list = new ArrayList();
      OPatchInfo info = null;

      String line;
      while((line = reader.readLine()) != null) {
         line = line.trim();
         Matcher matcher = this.pat_appliedDate.matcher(line);
         if (matcher.matches()) {
            info = new OPatchInfo();
            info.bugNumber = matcher.group(1);
            info.dateApplied = matcher.group(2);
         } else if (info != null) {
            matcher = this.pat_patchId.matcher(line);
            if (matcher.matches()) {
               info.patchId = matcher.group(1);
            } else {
               matcher = this.pat_patchDesc.matcher(line);
               if (matcher.matches()) {
                  info.patchDescription = matcher.group(1);
                  list.add(info.toString());
                  info = null;
               }
            }
         }
      }

      if (list.isEmpty()) {
         list.add("No patches installed");
      }

      return list;
   }

   private String[] createMessageList(String msg) {
      this.patchInfos = new String[]{msg};
      return this.patchInfos;
   }

   static File getOpatchPath(String mwHome) {
      File opatchDir = new File(mwHome, "OPatch");
      boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
      File opatch = new File(opatchDir, isWindows ? "opatch.bat" : "opatch");
      return opatch;
   }

   public synchronized String[] getPatchInfos(String mwHome) {
      if (this.patchInfos == null) {
         File opatch = getOpatchPath(mwHome);
         if (!opatch.exists()) {
            return this.createMessageList("ERROR: OPatch executable not found");
         }

         if (!opatch.canExecute()) {
            return this.createMessageList("ERROR: OPatch not executable");
         }

         String[] cmdarr = new String[]{opatch.getAbsolutePath(), "lsinventory"};
         InputStream in = null;

         try {
            Map env = new HashMap(System.getenv());
            env.put("LC_ALL", "en_US");
            env.put("LANG", "en_US.ISO-8859-1");
            boolean isHpUx = System.getProperty("os.name").toLowerCase().indexOf("hp-ux") != 1;
            if (isHpUx) {
               env.put("LC_ALL", "C");
               env.put("LANG", "C");
            }

            List envList = new ArrayList();
            Iterator var8 = env.entrySet().iterator();

            while(var8.hasNext()) {
               Map.Entry entry = (Map.Entry)var8.next();
               envList.add((String)entry.getKey() + "=" + (String)entry.getValue());
            }

            String[] envarr = (String[])envList.toArray(new String[0]);
            Process proc = Runtime.getRuntime().exec(cmdarr, envarr);
            in = proc.getInputStream();
            this.patchInfos = (String[])this.gatherPatchInfo(in).toArray(new String[0]);
         } catch (SecurityException var20) {
            this.patchInfos = this.createMessageList("ERROR: Failed to collect patch information.");
         } catch (IOException var21) {
            this.patchInfos = this.createMessageList("ERROR: Failed to collect patch information.");
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var19) {
               }
            }

         }
      }

      String[] arr = new String[this.patchInfos.length];
      System.arraycopy(this.patchInfos, 0, arr, 0, arr.length);
      return arr;
   }

   public static class OPatchInfo {
      private String bugNumber;
      private String dateApplied;
      private String patchId;
      private String patchDescription;

      public String getBugNumber() {
         return this.bugNumber;
      }

      public String getDateApplied() {
         return this.dateApplied;
      }

      public String getPatchId() {
         return this.patchId;
      }

      public String getPatchDescription() {
         return this.patchDescription;
      }

      public String toString() {
         return "" + this.bugNumber + ";" + this.patchId + ";" + this.dateApplied + ";" + this.patchDescription;
      }
   }
}
