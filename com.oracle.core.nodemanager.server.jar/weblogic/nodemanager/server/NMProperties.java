package weblogic.nodemanager.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.nodemanager.NodeManagerTextTextFormatter;

public class NMProperties extends Properties implements Comparator {
   TreeMap lineInfoMap;
   HashMap nameToLineMap;
   Matcher varMatcher;
   Pattern varPattern;
   Matcher varOnlyMatcher;
   Pattern varOnlyPattern;
   Matcher contMatcher;
   Pattern contPattern;
   private static final long serialVersionUID = -9039140023562390804L;
   String varPatternString;
   String varOnlyPatternString;
   String contPatternString;
   private static final int FAB_LINE_BASE = 1073741824;
   int nextFabLine;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   private File configFile;

   private void resetMaps() {
      this.lineInfoMap.clear();
      this.nameToLineMap.clear();
      this.nextFabLine = 1073741824;
   }

   public NMProperties() {
      this((Properties)null);
   }

   public NMProperties(File file) {
      this((Properties)null);
      this.setConfigFile(file);
   }

   public NMProperties(Properties defaults) {
      super(defaults);
      this.varPatternString = "^                      # Match beginning of line\n\\s*                   # Match leading whitespaces (not captured)\n(\\w+)                 # The property name (made of word chars)\n\\s*                   # Followed by optional whitespaces\n(?:[=:]|\\s)           # =, : or whitespace as delimiter\n\\s*                 # More optional whitespace\n(.*?)                  # Match till end of line\n(\\\\?)                  # With an optional continuation\n$                      # End of line\n";
      this.varOnlyPatternString = "^                      # Match beginning of line\n\\s*                   # Match leading whitespaces (not captured)\n(\\w+)                 # The property name (made of word chars)\n\\s*                   # Followed by optional whitespaces\n$                      # End of line\n";
      this.contPatternString = "^                      # Match beginning of line\n\\s*                   # Match leading whitespaces (not captured)\n(.*?)                  # Match any character, relucatant\n(\\\\?)                  # Optional continuation\n$                      # End of line\n";
      this.nextFabLine = 1073741824;
      this.lineInfoMap = new TreeMap(this);
      this.nameToLineMap = new HashMap();
      this.nextFabLine = 1073741824;
      this.varPattern = Pattern.compile(this.varPatternString, 4);
      this.varOnlyPattern = Pattern.compile(this.varOnlyPatternString, 4);
      this.contPattern = Pattern.compile(this.contPatternString, 4);
   }

   public int getIntProperty(String name, int defaultValue) throws NumberFormatException {
      String s = this.getProperty(name);

      try {
         return s != null ? Integer.parseInt(s) : defaultValue;
      } catch (NumberFormatException var5) {
         throw new NumberFormatException(nmText.getInvalidIntProperty(name));
      }
   }

   public boolean isTrue(String name, boolean defaultValue) {
      String s = this.getProperty(name);
      return s != null ? "true".equalsIgnoreCase(s) : defaultValue;
   }

   public boolean isTrue(String name) {
      return "true".equalsIgnoreCase(this.getProperty(name));
   }

   public synchronized void setProperty(String n, String v, Integer l) {
      this.setProperty(n, v);
      if (l == null) {
         l = new Integer(this.nextFabLine);
         ++this.nextFabLine;
      }

      this.nameToLineMap.put(n, l);
      LineInfo li = new LineInfo();
      li.setName(n);
      this.lineInfoMap.put(l, li);
   }

   public synchronized void saveWithComments(OutputStream o, String hdr) throws IOException {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(o));
      Set s = this.keySet();
      Iterator i = s.iterator();

      while(i.hasNext()) {
         String name = (String)i.next();
         if (this.nameToLineMap.get(name) == null) {
            Integer l = new Integer(this.nextFabLine);
            ++this.nextFabLine;
            this.nameToLineMap.put(name, l);
            LineInfo li = new LineInfo();
            li.setName(name);
            this.lineInfoMap.put(l, li);
         }
      }

      if (hdr != null) {
         bw.write("#" + hdr);
         bw.newLine();
      }

      bw.write("#" + (new Date()).toString());
      bw.newLine();
      s = this.lineInfoMap.keySet();

      for(i = s.iterator(); i.hasNext(); bw.newLine()) {
         Integer l = (Integer)i.next();
         LineInfo li = (LineInfo)this.lineInfoMap.get(l);
         String n = li.getName();
         if (n == null) {
            bw.write(li.getRawLine());
         } else {
            bw.write(n + "=");
            String val = (String)this.get(n);
            if (val != null) {
               bw.write(val);
            }
         }
      }

      bw.close();
   }

   public synchronized void loadWithComments(InputStream in) throws IOException {
      LineNumberReader lr = new LineNumberReader(new InputStreamReader(in));
      boolean continuation = false;
      String name = null;
      String value = null;
      String cont = null;
      this.resetMaps();

      while(true) {
         while(true) {
            while(true) {
               String curLine;
               while((curLine = lr.readLine()) != null) {
                  int lineNum = lr.getLineNumber();
                  if (!continuation) {
                     this.varMatcher = this.varPattern.matcher(curLine);
                     if (this.varMatcher.matches()) {
                        name = this.varMatcher.group(1);
                        value = this.varMatcher.group(2);
                        cont = this.varMatcher.group(3);
                        if (cont != null && cont.length() != 0) {
                           continuation = true;
                        } else {
                           this.setProperty(name, value, new Integer(lineNum));
                           name = null;
                           value = null;
                           continuation = false;
                        }
                     } else {
                        this.varOnlyMatcher = this.varOnlyPattern.matcher(curLine);
                        if (this.varOnlyMatcher.matches()) {
                           name = this.varOnlyMatcher.group(1);
                           value = "";
                           this.setProperty(name, value, new Integer(lineNum));
                        } else {
                           LineInfo li = new LineInfo();
                           li.setRawLine(curLine);
                           this.lineInfoMap.put(new Integer(lineNum), li);
                        }
                     }
                  } else {
                     this.contMatcher = this.contPattern.matcher(curLine);
                     if (this.contMatcher.matches()) {
                        String moreVal = this.contMatcher.group(1);
                        cont = this.contMatcher.group(2);
                        value = value + moreVal;
                        if (cont != null && cont.length() != 0) {
                           continuation = true;
                        } else {
                           this.setProperty(name, value, new Integer(lineNum));
                           continuation = false;
                        }
                     } else {
                        assert false : "Pattern matching error.  Line should have been matched!!";
                     }
                  }
               }

               return;
            }
         }
      }
   }

   public void loadWithComments(File file) throws IOException {
      InputStream is = new FileInputStream(file);
      this.loadWithComments((InputStream)is);
      is.close();
   }

   public void load(File file) throws IOException {
      InputStream is = new FileInputStream(file);
      this.load(is);
      is.close();
   }

   public void load() throws IOException {
      if (this.configFile == null) {
         throw new IOException(nmText.getInvalidNMPropFile("null"));
      } else {
         if (this.configFile.exists()) {
            try {
               this.load(this.configFile);
            } catch (IllegalArgumentException var2) {
               throw (IOException)(new IOException(nmText.getInvalidNMPropFile(this.configFile.toString()))).initCause(var2);
            } catch (IOException var3) {
               throw (IOException)(new IOException(nmText.getErrorReadingNMPropFile(this.configFile.toString()))).initCause(var3);
            }
         }

      }
   }

   public void saveWithComments(File file, String hdr) throws IOException {
      OutputStream os = new FileOutputStream(file);

      try {
         this.saveWithComments((OutputStream)os, hdr);
      } finally {
         os.close();
      }

   }

   public void saveWithComments(File file) throws IOException {
      this.saveWithComments((File)file, (String)null);
   }

   public void save(File file, String hdr) throws IOException {
      OutputStream os = new FileOutputStream(file);

      try {
         this.store(os, hdr);
      } finally {
         os.close();
      }

   }

   public void save(File file) throws IOException {
      this.save(file, (String)null);
   }

   public void save() throws IOException {
      this.save(this.configFile, (String)null);
   }

   public void save(NMServerConfig config) throws IOException {
      if (this.configFile == null) {
         throw new IOException(nmText.getInvalidNMPropFile("null"));
      } else {
         String configFileName = this.configFile.toString();
         if (this.configFile.exists()) {
            NMServer.nmLog.info(nmText.getLoadedNMProps(configFileName));
         } else {
            NMServer.nmLog.warning(nmText.getNMPropsNotFound(configFileName));
            NMServer.nmLog.info(nmText.getSavingNMProps(configFileName));
            NMProperties nmProps = config.getConfigProperties();
            nmProps.putAll(this);
            nmProps.save();
         }

      }
   }

   public void setConfigFile(File file) {
      this.configFile = file;
   }

   public File getConfigFile() {
      return this.configFile;
   }

   public void putAll(NMProperties prop) {
      super.putAll(prop);
      File f = prop.getConfigFile();
      if (f != null) {
         this.setConfigFile(f);
      }

   }

   public int compare(Object o1, Object o2) {
      Integer l1 = (Integer)o1;
      Integer l2 = (Integer)o2;
      return l1.compareTo(l2);
   }

   private static class LineInfo {
      String name;
      String rawLine;

      public LineInfo(String n, String r) {
         this.name = n;
         this.rawLine = r;
      }

      public LineInfo() {
         this((String)null, (String)null);
      }

      public void setName(String n) {
         this.name = n;
      }

      public void setRawLine(String r) {
         this.rawLine = r;
      }

      public String getName() {
         return this.name;
      }

      public String getRawLine() {
         return this.rawLine;
      }
   }
}
