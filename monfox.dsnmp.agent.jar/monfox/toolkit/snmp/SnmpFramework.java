package monfox.toolkit.snmp;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import monfox.log.Logger;
import monfox.toolkit.snmp.metadata.Result;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpMetadataException;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataRepository;
import monfox.toolkit.snmp.util.FileLocator;
import monfox.toolkit.snmp.util.FormatUtil;

public class SnmpFramework {
   /** @deprecated */
   public static boolean dumpPackets = false;
   /** @deprecated */
   public static boolean debugOn = false;
   public static final int COMPAT_NO_DATAGRAM_OFFSET = 1;
   public static PrintStream dbg;
   private static int a;
   private static boolean b;
   private static SnmpExceptionHandler c;
   private static SnmpMetadata d;
   private static String e;
   public static final int OID_FORMAT_NUMERIC = 1;
   public static final int OID_FORMAT_LABEL = 2;
   public static final int OID_FORMAT_INDEXES = 3;
   private static boolean f;
   private static FileLocator g;
   private static String h;
   private static boolean i;
   private static boolean j;
   private static boolean k;
   private static boolean l;
   private static boolean m;
   private static boolean n;
   private static int o;
   private static CharacterSetSupport p;
   private static Logger q;
   private static final String r = "$Id: SnmpFramework.java,v 1.39 2011/08/30 21:54:22 sking Exp $";

   /** @deprecated */
   public static void dumpPackets(boolean var0) {
      dumpPackets = var0;
   }

   /** @deprecated */
   public static boolean dumpPackets() {
      return dumpPackets;
   }

   /** @deprecated */
   public static void debugOn(boolean var0) {
      debugOn = var0;
   }

   /** @deprecated */
   public static void DebugOn(boolean var0) {
      debugOn = var0;
   }

   public static boolean debugOn() {
      return debugOn;
   }

   /** @deprecated */
   public static boolean DebugOn() {
      return debugOn();
   }

   public static SnmpMetadata loadMetadata(String var0) throws IOException, SnmpException {
      setMetadata(SnmpMetadata.load(var0));
      return getMetadata();
   }

   public static synchronized void addMibSearchPath(String var0) {
      SnmpMetadata.Repository var1 = SnmpMetadata.getRepository();
      String var2 = var1.getSearchPath();
      var1.setSearchPath(var0 + ";" + var2);
   }

   public static synchronized SnmpMetadata loadMibs(String var0) throws IOException, SnmpMetadataException {
      SnmpMetadata var1 = getMetadata();
      if (var1 == null) {
         var1 = new SnmpMetadata();
         setMetadata(var1);
      }

      Result var2 = var1.loadModules(var0);
      if (var2 != null && var2.getErrorCount() > 0) {
         throw new SnmpMetadataException(var2);
      } else {
         return var1;
      }
   }

   public static synchronized SnmpMetadata loadAllMibs(String var0) throws IOException, SnmpMetadataException {
      SnmpMetadata var1 = getMetadata();
      if (var1 == null) {
         var1 = new SnmpMetadata();
         setMetadata(var1);
      }

      Result var2 = var1.loadAllMibs(var0);
      if (var2 != null && var2.getErrorCount() > 0) {
         throw new SnmpMetadataException(var2);
      } else {
         return var1;
      }
   }

   public static synchronized SnmpMetadata loadMibs(String var0, String var1) throws IOException, SnmpMetadataException {
      SnmpMetadata var2 = getMetadata();
      SnmpMetadataRepository var3 = new SnmpMetadataRepository();
      String var4 = var3.getSearchPath();
      var3.setSearchPath(var1 + ";" + var4);
      if (var2 == null) {
         var2 = new SnmpMetadata();
         setMetadata(var2);
      }

      Result var5 = var2.loadModules(var3, (String)var0);
      if (var5 != null && var5.getErrorCount() > 0) {
         throw new SnmpMetadataException(var5);
      } else {
         return var2;
      }
   }

   /** @deprecated */
   public static void LoadMetadata(String var0) throws IOException, SnmpException {
      loadMetadata(var0);
   }

   public static SnmpMetadata loadMetadata() throws IOException, SnmpException {
      setMetadata(SnmpMetadata.load((String)null));
      return getMetadata();
   }

   /** @deprecated */
   public static void LoadMetadata() throws IOException, SnmpException {
      loadMetadata();
   }

   public static void setMetadata(SnmpMetadata var0) {
      d = var0;
   }

   /** @deprecated */
   public static void SetMetadata(SnmpMetadata var0) {
      setMetadata(var0);
   }

   public static SnmpMetadata getMetadata() {
      return d;
   }

   /** @deprecated */
   public static SnmpMetadata GetMetadata() {
      return getMetadata();
   }

   public static void resolveOidNames(boolean var0) {
      b = var0;
   }

   /** @deprecated */
   public static void ResolveOidNames(boolean var0) {
      resolveOidNames(var0);
   }

   public static boolean resolveOidNames() {
      return b;
   }

   /** @deprecated */
   public static boolean ResolveOidNames() {
      return resolveOidNames();
   }

   /** @deprecated */
   public static void HandleException(Object var0, Throwable var1) {
      handleException(var0, var1);
   }

   public static void handleException(Object var0, Throwable var1) {
      if (c != null) {
         c.handleException(var0, var1);
         if (!SnmpValue.b) {
            return;
         }
      }

      Logger var2 = Logger.getInstance(a("i\u0001\u00025MH\u000e\u0002 |U\u001d\u0004"));
      if (var2.isEnabled()) {
         StringBuffer var3 = new StringBuffer();
         var3.append(a("0LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019e"));
         var3.append(a("\u0019O*\u001dH\u007f?;\fDtUe"));
         var3.append(a("\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(0"));
         var3.append(a("y\u0003\u000e6x\u0000O") + var0.getClass() + "\n");
         var3.append(a("\u007f\u0017\f {N\u0006\u0000+1\u001a") + var1 + "\n");
         var3.append(a("\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh\u0001"));
         var3.append(a("\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(\u0019LLf(0"));
         var2.debug(var3.toString());
      }

   }

   public static int getCompatFlags() {
      return a;
   }

   public static void setCompatFlags(int var0) {
      a = var0;
   }

   public static void setCompatFlag(int var0) {
      a |= var0;
   }

   public static void setExceptionHandler(SnmpExceptionHandler var0) {
      c = var0;
   }

   /** @deprecated */
   public static void SetExceptionHandler(SnmpExceptionHandler var0) {
      setExceptionHandler(var0);
   }

   public static void setSecurityProviderName(String var0) {
      e = var0;
   }

   public static String getSecurityProviderName() {
      return e;
   }

   public static void setDefaultOidFormat(int var0) {
      o = var0;
   }

   public static int getDefaultOidFormat() {
      return o;
   }

   public static CharacterSetSupport getCharacterSetSupport() {
      if (p == null) {
         p = new CharacterSetSupport();
      }

      return p;
   }

   public static String byteArrayToString(byte[] var0, int var1, int var2, SnmpOid var3) {
      if (var0 == null) {
         return null;
      } else if (p == null) {
         if (a().isDebugEnabled()) {
            a().debug(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var3);
         }

         return new String(var0, var1, var2);
      } else {
         String var4 = p.getCharSetForVariable(var3);
         if (var4 != null) {
            try {
               if (a().isDetailedEnabled()) {
                  a().detailed(a("O\u001c\u0006+l\u001aH") + var4 + a("\u001dO\f-jHO\n+hU\u000b\u0006+l\u001a\t\u000071\u001a") + var3);
               }

               return new String(var0, var1, var2, var4);
            } catch (Exception var6) {
               a().warn(a("Y\u0007\u000e7jY\u001b\n7+_\u0001\f*oS\u0001\benH\u001d\u00007+\\\u0000\u001de,") + var0 + a("\u001dO\u001a6bT\bO&c[\u001d\u001c \u007f\u001aH") + var4 + a("\u001dAO\u0017nL\n\u001d1bT\bO1d\u001a\u001f\u0003$\u007f\\\u0000\u001d(+^\n\t$~V\u001bA"));
            }
         }

         if (a().isDetailedEnabled()) {
            a().detailed(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var3);
         }

         return new String(var0, var1, var2);
      }
   }

   public static String byteArrayToString(byte[] var0, SnmpOid var1) {
      if (var0 == null) {
         return null;
      } else if (p == null) {
         if (a().isDetailedEnabled()) {
            a().detailed(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var1);
         }

         return new String(var0);
      } else {
         String var2 = p.getCharSetForVariable(var1);
         if (var2 != null) {
            try {
               if (a().isDetailedEnabled()) {
                  a().detailed(a("O\u001c\u0006+l\u001aH") + var2 + a("\u001dO\f-jHO\n+hU\u000b\u0006+l\u001a\t\u000071\u001a") + var1);
               }

               return new String(var0, var2);
            } catch (Exception var4) {
               a().warn(a("Y\u0007\u000e7jY\u001b\n7+_\u0001\f*oS\u0001\benH\u001d\u00007+\\\u0000\u001de,") + var0 + a("\u001dO\u001a6bT\bO&c[\u001d\u001c \u007f\u001aH") + var2 + a("\u001dAO\u0017nL\n\u001d1bT\bO1d\u001a\u001f\u0003$\u007f\\\u0000\u001d(+^\n\t$~V\u001bA"));
            }
         }

         if (a().isDetailedEnabled()) {
            a().detailed(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var1);
         }

         return new String(var0);
      }
   }

   public static byte[] stringToByteArray(String var0, SnmpOid var1) {
      if (var0 == null) {
         return null;
      } else if (p == null) {
         if (a().isDetailedEnabled()) {
            a().detailed(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var1);
         }

         return var0.getBytes();
      } else {
         String var2 = p.getCharSetForVariable(var1);
         if (var2 != null) {
            try {
               if (a().isDetailedEnabled()) {
                  a().detailed(a("O\u001c\u0006+l\u001aH") + var2 + a("\u001dO\f-jHO\n+hU\u000b\u0006+l\u001a\t\u000071\u001a") + var1);
               }

               return var0.getBytes(var2);
            } catch (Exception var4) {
               a().warn(a("Y\u0007\u000e7jY\u001b\n7+^\n\f*oS\u0001\benH\u001d\u00007+\\\u0000\u001de,") + var0 + a("\u001dO\u001a6bT\bO&c[\u001d\u001c \u007f\u001aH") + var2 + a("\u001dAO\u0017nL\n\u001d1bT\bO1d\u001a\u001f\u0003$\u007f\\\u0000\u001d(+^\n\t$~V\u001bA"));
            }
         }

         if (a().isDetailedEnabled()) {
            a().detailed(a("O\u001c\u0006+l\u001a\u001f\u0003$\u007f\\\u0000\u001d(+Y\u0007\u000e7+_\u0001\f*oS\u0001\bemU\u001dUe") + var1);
         }

         return var0.getBytes();
      }
   }

   public static boolean isFixedStringEncodingEnabled() {
      return j;
   }

   public static void isFixedStringEncodingEnabled(boolean var0) {
      j = var0;
   }

   public static void isUTF8ToStringSupported(boolean var0) {
      i = var0;
   }

   public static boolean isUTF8ToStringSupported() {
      return i;
   }

   public static String getUTF8Encoding() {
      return h;
   }

   public static void setUTF8Encoding(String var0) {
      h = var0;
   }

   public static void isCachingOidInfo(boolean var0) {
      k = var0;
   }

   public static boolean isCachingOidInfo() {
      return k;
   }

   public static void isCompatibilityMode(boolean var0) {
      l = var0;
   }

   public static boolean isCompatibilityMode() {
      return l;
   }

   public static void isBooleanToIntSupported(boolean var0) {
      m = var0;
   }

   public static boolean isBooleanToIntSupported() {
      return m;
   }

   public static FileLocator getFileLocator() {
      return g;
   }

   public static boolean isStackTraceDebugEnabled() {
      return n;
   }

   public static void isStackTraceDebugEnabled(boolean var0) {
      n = var0;
   }

   public static boolean isDisplayAllStringsAsCharacters() {
      return f;
   }

   public static void isDisplayAllStringsAsCharacters(boolean var0) {
      f = var0;
   }

   private static Logger a() {
      boolean var5 = SnmpValue.b;
      if (q == null) {
         q = Logger.getInstance(a("~<!\b["), "", a("i\u0001\u00025MH\u000e\u0002 |U\u001d\u0004"));
         Logger var10000 = q;
         StringBuffer var10001 = (new StringBuffer()).append(a("0eO\u0001Xt\"?e]\u007f=<\fDtOUe"));
         new Version();
         var10001 = var10001.append(Version.getBuildRelease()).append(a("0O-\u0010Bv+O\u0001Jn*Oe+\u001aUO"));
         new Version();
         var10000.config(var10001.append(Version.getBuildDate()).append("\n").append(a("0O,\u0010Yh*!\u0011+~.;\u0000+\u001aUO")).append(new Date()).append(a("0e")).toString());
         StringBuffer var0 = new StringBuffer();
         var0.append(a("0eO\u0016Ri;*\b+j= \u0015Nh;&\u0000X\u0000ee"));
         var0.append(a("0BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBO"));
         Properties var1 = System.getProperties();
         Iterator var2 = var1.keySet().iterator();

         label20: {
            while(var2.hasNext()) {
               String var3 = (String)var2.next();
               String var4 = var1.getProperty(var3);
               var0.append(a("0OOe+\u001aO"));
               var0.append(FormatUtil.pad(var3, 15, 'l'));
               var0.append(a("\u001aUO"));
               var0.append(var4);
               if (var5) {
                  break label20;
               }

               if (var5) {
                  break;
               }
            }

            var0.append(a("0BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBh&\u0017BBO"));
         }

         q.config(var0.toString());
      }

      return q;
   }

   static {
      dbg = System.out;
      a = 0;
      b = true;
      c = null;
      d = null;
      e = null;
      f = false;
      g = new FileLocator();
      h = a("o;)h3");
      i = false;
      j = true;
      k = true;
      l = false;
      m = true;
      n = false;
      o = 2;
      p = new CharacterSetSupport();
      q = null;
      monfox.toolkit.snmp.f.getInstance();

      try {
         String var0 = System.getProperty(a("P\u000e\u0019$%L\u0002A+jW\n"), "").toLowerCase();
         if (var0.indexOf(a("P\u001d\u0000&`S\u001b")) >= 0) {
            a = 1;
         }
      } catch (Exception var1) {
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 58;
               break;
            case 1:
               var10003 = 111;
               break;
            case 2:
               var10003 = 111;
               break;
            case 3:
               var10003 = 69;
               break;
            default:
               var10003 = 11;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
