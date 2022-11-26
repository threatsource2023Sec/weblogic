package weblogic.xml.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.utils.AssertionError;

public class Debug {
   private static Debug anInstance = new Debug();
   static int minDebugLevel = 0;
   static Vector DebugFacilityExtent = new Vector();

   public static Location location(int level) {
      return (anInstance.new StackTrace()).location(level);
   }

   public static void setDebugLevelAll(int level) {
      Enumeration e = DebugFacilityExtent.elements();

      while(e.hasMoreElements()) {
         DebugFacility dl = (DebugFacility)e.nextElement();
         if (dl.getLevel() < level) {
            dl.setLevel(level);
         }
      }

      minDebugLevel = level;
   }

   public static DebugFacility makeDebugFacility(DebugSpec spec) {
      return anInstance.new DebugFacility(spec);
   }

   public static DebugSpec getDebugSpec() {
      return anInstance.new DebugSpec();
   }

   public static String getPackage() {
      String pkg = location(1).getPackage();
      return pkg;
   }

   public static String getClassName() {
      return getClassName(2);
   }

   private static String getClassName(int stackLevel) {
      String cn = location(stackLevel).getClassName();
      return cn;
   }

   public static String getFullClassName() {
      return getFullClassName(2);
   }

   private static String getFullClassName(int stackLevel) {
      String cn = location(stackLevel).getFullClassName();
      return cn;
   }

   public static Formatter getFormatter(OutputStream str) {
      return new Debug().new StreamFormatterImpl(str);
   }

   public class StreamFormatterImpl implements Formatter {
      PrintWriter pw = null;

      public StreamFormatterImpl(OutputStream str) {
         this.pw = new PrintWriter(str);
      }

      public void println(String text) {
         this.pw.println(text);
      }

      public void print(String text) {
         this.pw.print(text);
      }

      public void flush() {
         this.pw.flush();
      }
   }

   public interface DebugFormatter extends Formatter {
      void println(int var1, String var2);

      void print(int var1, String var2);
   }

   public interface Formatter {
      void println(String var1);

      void print(String var1);

      void flush();
   }

   final class StackTrace {
      Vector stack = new Vector();

      StackTrace() {
         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            (new Exception()).printStackTrace(ps);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            DataInputStream in = new DataInputStream(bais);
            ps.close();
            in.readLine();
            in.readLine();
            in.readLine();

            String line;
            while((line = in.readLine()) != null) {
               this.stack.addElement(Debug.this.new Location(line));
            }
         } catch (IOException var8) {
         }

      }

      public Location location(int level) throws ArrayIndexOutOfBoundsException {
         return (Location)this.stack.elementAt(level);
      }
   }

   public class Location {
      private String pkg;
      private String clazz;
      private String method;
      private String linenum;
      private String fullClass;
      private String sourcefile;

      public String getPackage() {
         return this.pkg;
      }

      public String getClassName() {
         return this.clazz;
      }

      public String getMethod() {
         return this.method;
      }

      public String getLineNumber() {
         return this.linenum;
      }

      public String getFullClassName() {
         return this.fullClass;
      }

      public String getSourceFile() {
         return this.sourcefile;
      }

      public String here() {
         return this.fullClass + "." + this.method + "(" + this.sourcefile + ":" + this.linenum + ")";
      }

      public String caller() {
         return this.fullname() + "(), line " + this.linenum;
      }

      private String fullname() {
         return this.fullClass.length() > 0 ? this.fullClass + "." + this.method : this.method;
      }

      private Location(String line) {
         this.pkg = "<unknown>";
         this.clazz = "<unknown>";
         this.method = "<unknown>";
         this.linenum = "<unknown>";
         this.fullClass = "<unknown>";
         this.sourcefile = "<unknown>";
         int begin = line.indexOf("at ") + 3;
         int end = line.indexOf("(");
         if (begin != -1 && end != -1) {
            String fullName = line.substring(begin, end);
            end = fullName.lastIndexOf(".");
            if (end != -1) {
               this.fullClass = fullName.substring(0, end);
               this.method = fullName.substring(end + 1);
               end = this.fullClass.lastIndexOf(".");
               if (end == -1) {
                  this.pkg = "<default>";
                  this.clazz = this.fullClass;
               } else {
                  this.pkg = this.fullClass.substring(0, end);
                  this.clazz = this.fullClass.substring(end + 1);
               }

               begin = line.indexOf("(") + 1;
               end = line.indexOf(":");
               if (begin != -1 && end != -1) {
                  this.sourcefile = line.substring(begin, end);
                  begin = end + 1;
                  end = line.lastIndexOf(")");
                  if (end != -1) {
                     this.linenum = line.substring(begin, end);
                  } else {
                     this.linenum = line.substring(begin);
                  }

               }
            }
         }
      }

      // $FF: synthetic method
      Location(String x1, Object x2) {
         this(x1);
      }
   }

   public class DebugFacility implements DebugFormatter {
      PrintWriter wtr;
      DebugSpec spec;
      boolean initialized;

      private DebugFacility(DebugSpec specIn) {
         this.wtr = null;
         this.spec = null;
         this.initialized = false;
         this.spec = specIn;
         this.wtr = new PrintWriter(this.spec.outStream);
         if (this.spec.name != null && this.spec.shortName) {
            int index = this.spec.name.lastIndexOf(".");
            this.spec.name = this.spec.name.substring(index + 1);
         }

         Debug.DebugFacilityExtent.add(this);
      }

      public boolean areDebugging() {
         return this.spec.level > 0;
      }

      public boolean areDebuggingAt(int level) {
         return level <= this.spec.level;
      }

      public void pl(String text) {
         this.println(text);
      }

      public void pl(int level, String text) {
         this.println(text);
      }

      public void println(String text) {
         this.print(2, text + "\n", true);
      }

      public void println(int level, String text) {
         this.print(level, text + "\n", true);
      }

      public void pw(String text) {
         this.printWarning(text);
      }

      public void pe(String text) {
         this.printError(text);
      }

      public void printWarning(String text) {
         this.println(1, "WARNING: " + text);
      }

      public void printError(String text) {
         this.println(1, "ERROR: " + text);
      }

      public void print(String text) {
         this.print(2, text, true);
      }

      public void print(int level, String text) {
         this.print(level, text, true);
      }

      public int getLevel() {
         return this.spec.level;
      }

      public void setLevel(int level) {
         this.spec.level = level;
      }

      public void setName(String name) {
         this.spec.name = name;
      }

      public void setPrefix(String prefix) {
         this.spec.prefix = prefix;
      }

      public void setOutStream(OutputStream outStream) {
         this.spec.outStream = outStream;
      }

      public void setIncludeTime(boolean includeTime) {
         this.spec.includeTime = includeTime;
      }

      public void setIncludeName(boolean includeName) {
         this.spec.includeName = includeName;
      }

      public void setIncludeClass(boolean includeClass) {
         this.spec.includeClass = includeClass;
      }

      public void setIncludeLocation(boolean includeLocation) {
         this.spec.includeLocation = includeLocation;
      }

      public void setShortName(boolean shortName) {
         this.spec.shortName = shortName;
      }

      public void setShortClass(boolean shortClass) {
         this.spec.shortClass = shortClass;
      }

      public void setMBean(ServerDebugMBean mbean) {
         if (this.spec.prefix != null) {
            int level = 0;
            String name = null;
            OutputStream str = null;
            boolean includeTime = false;
            boolean includeName = false;
            boolean includeClass = false;
            boolean includeLocation = false;
            boolean useShortClass = false;
            if (this.spec.prefix.equals("JAXP")) {
               try {
                  level = mbean.getDebugJAXPDebugLevel();
                  name = mbean.getDebugJAXPDebugName();
                  str = mbean.getDebugJAXPOutputStream();
                  includeTime = mbean.getDebugJAXPIncludeTime();
                  includeName = mbean.getDebugJAXPIncludeName();
                  includeClass = mbean.getDebugJAXPIncludeClass();
                  includeLocation = mbean.getDebugJAXPIncludeLocation();
                  useShortClass = mbean.getDebugJAXPUseShortClass();
               } catch (Exception var13) {
               }
            } else if (this.spec.prefix.equals("XMLRegistry")) {
               try {
                  level = mbean.getDebugXMLRegistryDebugLevel();
                  name = mbean.getDebugXMLRegistryDebugName();
                  str = mbean.getDebugXMLRegistryOutputStream();
                  includeTime = mbean.getDebugXMLRegistryIncludeTime();
                  includeName = mbean.getDebugXMLRegistryIncludeName();
                  includeClass = mbean.getDebugXMLRegistryIncludeClass();
                  includeLocation = mbean.getDebugXMLRegistryIncludeLocation();
                  useShortClass = mbean.getDebugXMLRegistryUseShortClass();
               } catch (Exception var12) {
               }
            } else {
               if (!this.spec.prefix.equals("XMLEntityCache")) {
                  throw new IllegalArgumentException(this.spec.prefix + " prefix not supported");
               }

               try {
                  level = mbean.getDebugXMLEntityCacheDebugLevel();
                  name = mbean.getDebugXMLEntityCacheDebugName();
                  str = mbean.getDebugXMLEntityCacheOutputStream();
                  includeTime = mbean.getDebugXMLEntityCacheIncludeTime();
                  includeName = mbean.getDebugXMLEntityCacheIncludeName();
                  includeClass = mbean.getDebugXMLEntityCacheIncludeClass();
                  includeLocation = mbean.getDebugXMLEntityCacheIncludeLocation();
                  useShortClass = mbean.getDebugXMLEntityCacheUseShortClass();
               } catch (Exception var11) {
               }
            }

            if (level > this.getLevel()) {
               this.setLevel(level);
            }

            if (name != null) {
               this.setName(name);
            }

            if (str != null) {
               this.setOutStream(str);
            }

            this.setIncludeTime(includeTime);
            this.setIncludeName(includeName);
            this.setIncludeClass(includeClass);
            this.setIncludeLocation(includeLocation);
            this.setShortClass(useShortClass);
         }
      }

      private synchronized void print(int level, String text, boolean flush) {
         if (!this.initialized) {
            this.initialize();
         }

         if (this.areDebuggingAt(level)) {
            this.wtr.print("DBG:");
            if (this.spec.includeTime) {
               this.wtr.print(" " + new Date());
            }

            if (this.spec.includeName || this.spec.includeLocation) {
               this.wtr.print(" (");
               if (this.spec.includeName) {
                  this.wtr.print(this.spec.name);
               }

               if (this.spec.includeClass) {
                  if (this.spec.includeName) {
                     this.wtr.print(", ");
                  }

                  String className = Debug.getFullClassName(4);
                  if (className != null && this.spec.shortClass) {
                     int index = className.lastIndexOf(".");
                     className = className.substring(index + 1);
                  }

                  this.wtr.print(className);
               }

               if (this.spec.includeLocation) {
                  if (this.spec.includeName || this.spec.includeClass) {
                     this.wtr.print(", ");
                  }

                  this.wtr.print(Debug.location(2).here());
               }

               this.wtr.print(")");
            }

            this.wtr.print(" " + text);
            if (flush) {
               this.wtr.flush();
            }

         }
      }

      public PrintWriter getWriter() {
         return this.wtr;
      }

      public void flush() {
         this.wtr.flush();
      }

      public void setDebugLevel(int level) {
         this.spec.level = level;
      }

      private void initialize() {
         String originalName = this.spec.name;

         try {
            String propertySpecFile = System.getProperty("DebugSpec");
            if (propertySpecFile != null) {
               this.initializeFromPropertries(propertySpecFile);
            }

            propertySpecFile = System.getProperty("DebugSpec." + originalName);
            if (propertySpecFile != null) {
               this.initializeFromPropertries(propertySpecFile);
            }

            Integer level = Integer.getInteger("DebugLevel." + originalName);
            if (level != null) {
               this.setLevel(level);
            } else {
               level = Integer.getInteger("DebugLevel");
               this.setLevel(level);
            }
         } catch (SecurityException var4) {
         }

         this.initialized = true;
      }

      private void setLevel(Integer level) {
         if (level != null && level > Debug.minDebugLevel) {
            this.spec.level = level;
         }

      }

      private void initializeFromPropertries(String debugSpecFile) {
         try {
            FileInputStream inStrm = new FileInputStream(debugSpecFile);
            Properties props = new Properties();
            props.load(inStrm);
            String levelS = props.getProperty("level");
            String includeTime = props.getProperty("includeTime");
            String includeLocation = props.getProperty("includeLocation");
            String outputFile = props.getProperty("outputFile");
            String name = props.getProperty("name");
            String includeName = props.getProperty("includeName");
            String shortName = props.getProperty("shortName");
            String includeClass = props.getProperty("includeClass");
            String shortClass = props.getProperty("shortClass");
            if (levelS != null) {
               try {
                  this.setLevel(Integer.parseInt(levelS));
               } catch (Exception var21) {
               }
            }

            if (includeTime != null) {
               try {
                  this.spec.includeTime = Boolean.valueOf(includeTime);
               } catch (Exception var20) {
               }
            }

            if (includeLocation != null) {
               try {
                  this.spec.includeLocation = Boolean.valueOf(includeLocation);
               } catch (Exception var19) {
               }
            }

            if (outputFile != null) {
               try {
                  this.spec.outStream = new FileOutputStream(outputFile);
               } catch (Exception var18) {
               }
            }

            if (name != null) {
               this.spec.name = name;
            }

            if (includeName != null) {
               try {
                  this.spec.includeName = Boolean.valueOf(includeName);
               } catch (Exception var17) {
               }
            }

            if (shortName != null) {
               try {
                  this.spec.shortName = Boolean.valueOf(shortName);
               } catch (Exception var16) {
               }
            }

            if (includeClass != null) {
               try {
                  this.spec.includeClass = Boolean.valueOf(includeClass);
               } catch (Exception var15) {
               }
            }

            if (shortClass != null) {
               try {
                  this.spec.shortClass = Boolean.valueOf(shortClass);
               } catch (Exception var14) {
               }
            }
         } catch (IOException var22) {
         }

      }

      public void assertion(boolean condition) {
         this.assertion(1, condition, "");
      }

      public void assertion(boolean condition, String message) {
         this.assertion(1, condition, message);
      }

      public void assertion(int level, boolean condition) {
         this.assertion(level, condition, "");
      }

      public void assertion(int level, boolean condition, String message) {
         if (this.areDebuggingAt(level) && !condition) {
            throw new AssertionError("Assertion violated");
         }
      }

      public void px(Throwable e) {
         this.pxI(e, "", 2, 2);
      }

      public void px(Throwable e, String s) {
         this.pxI(e, s, 2, 2);
      }

      public void px(Throwable e, int dumpLevel) {
         this.pxI(e, "", dumpLevel, dumpLevel);
      }

      public void px(Throwable e, String s, int dumpLevel) {
         this.pxI(e, s, dumpLevel, dumpLevel);
      }

      public void px(Throwable e, int loglevel, int dumpLevel) {
         this.pxI(e, "", loglevel, dumpLevel);
      }

      public void px(Throwable e, String s, int loglevel, int dumpLevel) {
         this.pxI(e, s, loglevel, dumpLevel);
      }

      private synchronized void pxI(Throwable e, String s, int logLevel, int dumpLevel) {
         if (e != null) {
            int level = logLevel;
            if (dumpLevel < logLevel) {
               level = dumpLevel;
            }

            if (level <= this.spec.level) {
               this.println(level, "---------------------------------------------------------------------");
               if (this.areDebuggingAt(logLevel)) {
                  this.println(level, "*** Exception: " + e);
                  this.println(level, "*** Caught from: " + Debug.location(2).here());
                  this.println(level, "*** Associated message: " + s);
               }

               if (this.areDebuggingAt(dumpLevel)) {
                  this.println(level, "*** Stack trace:");
                  e.printStackTrace(this.wtr);
               }

               this.println(level, "---------------------------------------------------------------------");
               this.wtr.flush();
            }
         }
      }

      // $FF: synthetic method
      DebugFacility(DebugSpec x1, Object x2) {
         this(x1);
      }

      public class DebugListener implements PropertyChangeListener {
         public void propertyChange(PropertyChangeEvent pce) {
            String attributeName = pce.getPropertyName();
            if (("Debug" + DebugFacility.this.spec.prefix + "DebugLevel").equals(attributeName)) {
               DebugFacility.this.setLevel((Integer)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "DebugName").equals(attributeName)) {
               DebugFacility.this.setName((String)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "OutputStream").equals(attributeName)) {
               DebugFacility.this.setOutStream((OutputStream)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "IncludeTime").equals(attributeName)) {
               DebugFacility.this.setIncludeTime((Boolean)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "IncludeName").equals(attributeName)) {
               DebugFacility.this.setIncludeName((Boolean)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "IncludeClass").equals(attributeName)) {
               DebugFacility.this.setIncludeClass((Boolean)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "IncludeLocation").equals(attributeName)) {
               DebugFacility.this.setIncludeLocation((Boolean)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "UseShortName").equals(attributeName)) {
               DebugFacility.this.setShortName((Boolean)pce.getNewValue());
            } else if (("Debug" + DebugFacility.this.spec.prefix + "UseShortClass").equals(attributeName)) {
               DebugFacility.this.setShortClass((Boolean)pce.getNewValue());
            }

         }
      }
   }

   public class DebugSpec {
      public int level;
      public boolean includeTime;
      public boolean includeLocation;
      public OutputStream outStream;
      public String name;
      public String prefix;
      public boolean includeName;
      public boolean shortName;
      public boolean includeClass;
      public boolean shortClass;

      public DebugSpec() {
         this.level = Debug.minDebugLevel;
         this.includeTime = false;
         this.includeLocation = false;
         this.outStream = System.out;
         this.name = null;
         this.prefix = null;
         this.includeName = true;
         this.shortName = false;
         this.includeClass = false;
         this.shortClass = true;
      }

      public void dump(PrintStream str) {
         str.println("DUMP of debug spec:");
         str.println("  level: " + this.level);
         str.println("  includeTime: " + this.includeTime);
         str.println("  includeLocation: " + this.includeLocation);
         str.println("  outStream: " + this.outStream);
         str.println("  name: " + this.name);
         str.println("  prefix: " + this.prefix);
         str.println("  includeName: " + this.includeName);
         str.println("  shortName: " + this.shortName);
         str.println("  includeClass: " + this.includeClass);
         str.println("  shortClass: " + this.shortClass);
      }
   }
}
