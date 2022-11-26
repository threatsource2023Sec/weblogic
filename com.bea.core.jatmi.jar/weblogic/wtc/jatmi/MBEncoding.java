package weblogic.wtc.jatmi;

import com.bea.core.jatmi.internal.ConfigHelper;
import java.io.FileReader;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.nio.charset.Charset;
import java.util.Hashtable;
import weblogic.wtc.WTCLogger;

public final class MBEncoding implements Serializable {
   private static final long serialVersionUID = -1509142621752324483L;
   private static final String MBENCODINGPROPERTY = "weblogic.wtc.mbencoding";
   private static final String MBENCODINGMAPPROPERTY = "weblogic.wtc.mbencodingmap";
   private static MapEntry[] defaultEntries = new MapEntry[]{new MapEntry("CP932", "MS932")};
   private static Hashtable default_t2jmap = new Hashtable();
   private static Hashtable default_j2tmap = new Hashtable();
   private static Hashtable t2jmap;
   private static Hashtable j2tmap;
   private static String default_mbencoding;

   public static void setMBEncodingMapFile(String mapfile) {
      t2jmap = new Hashtable();
      j2tmap = new Hashtable();
      if (mapfile == null) {
         mapfile = ConfigHelper.getGlobalMBEncodingMapFile();
      }

      if (mapfile == null) {
         try {
            mapfile = System.getProperty("weblogic.wtc.mbencodingmap");
         } catch (Exception var7) {
         }
      }

      if (mapfile == null) {
         mapfile = ConfigHelper.getHomePath() + "/lib/mbencmap";

         try {
            new FileReader(mapfile);
         } catch (Exception var6) {
            mapfile = null;
         }
      }

      if (mapfile != null) {
         try {
            StreamTokenizer st = new StreamTokenizer(new FileReader(mapfile));
            st.wordChars(95, 95);
            st.eolIsSignificant(true);
            int nentry = 0;
            String javaEncoding = null;
            String tuxedoEncoding = null;

            int ret;
            while((ret = st.nextToken()) != -1) {
               switch (ret) {
                  case -3:
                     if (nentry == 0) {
                        tuxedoEncoding = st.sval;
                     } else {
                        javaEncoding = st.sval;
                        if (nentry == 1 && !t2jmap.containsKey(tuxedoEncoding)) {
                           t2jmap.put(tuxedoEncoding, javaEncoding);
                        }

                        if (!j2tmap.containsKey(javaEncoding)) {
                           j2tmap.put(javaEncoding, tuxedoEncoding);
                        }
                     }

                     ++nentry;
                  case -2:
                  default:
                     break;
                  case 10:
                     nentry = 0;
               }
            }
         } catch (Exception var8) {
            mapfile = null;
         }

      }
   }

   public static String mapTuxedoToJava(String tuxedoEncoding) {
      if (tuxedoEncoding == null) {
         return null;
      } else {
         String javaEncoding = (String)t2jmap.get(tuxedoEncoding);
         if (javaEncoding == null) {
            javaEncoding = (String)default_t2jmap.get(tuxedoEncoding);
         }

         return javaEncoding != null ? javaEncoding : tuxedoEncoding;
      }
   }

   public static String mapJavaToTuxedo(String javaEncoding) {
      if (javaEncoding == null) {
         return null;
      } else {
         String tuxedoEncoding = (String)j2tmap.get(javaEncoding);
         if (tuxedoEncoding == null) {
            tuxedoEncoding = (String)default_j2tmap.get(javaEncoding);
         }

         return tuxedoEncoding != null ? tuxedoEncoding : javaEncoding;
      }
   }

   public static void setDefaultMBEncoding(String tuxedoEncoding) {
      default_mbencoding = checkMBEncoding(tuxedoEncoding);
   }

   public static String getDefaultMBEncoding() {
      if (default_mbencoding != null) {
         return default_mbencoding;
      } else {
         String tuxedoEncoding = ConfigHelper.getGlobalRemoteMBEncoding();
         if (tuxedoEncoding == null) {
            try {
               tuxedoEncoding = System.getProperty("weblogic.wtc.mbencoding");
            } catch (Exception var4) {
            }
         }

         tuxedoEncoding = checkMBEncoding(tuxedoEncoding);
         if (tuxedoEncoding == null) {
            String javaEncoding;
            try {
               javaEncoding = Charset.defaultCharset().name();
            } catch (Throwable var3) {
               javaEncoding = "ISO-8859-1";
            }

            tuxedoEncoding = mapJavaToTuxedo(javaEncoding);
         }

         default_mbencoding = tuxedoEncoding;
         return tuxedoEncoding;
      }
   }

   public static String checkMBEncoding(String tuxedoEncoding) {
      if (tuxedoEncoding == null) {
         return null;
      } else {
         String javaEncoding = mapTuxedoToJava(tuxedoEncoding);

         try {
            String teststr = new String("test");
            teststr.getBytes(javaEncoding);
            return tuxedoEncoding;
         } catch (Exception var3) {
            WTCLogger.logErrorUnsupportedEncoding(tuxedoEncoding);
            return null;
         }
      }
   }

   static {
      for(int i = 0; i < defaultEntries.length; ++i) {
         MapEntry entry = defaultEntries[i];
         if (!default_t2jmap.containsKey(entry.tenc)) {
            default_t2jmap.put(entry.tenc, entry.jenc);
         }

         if (!default_j2tmap.containsKey(entry.jenc)) {
            default_j2tmap.put(entry.jenc, entry.tenc);
         }
      }

      setMBEncodingMapFile((String)null);
      default_mbencoding = null;
   }

   private static class MapEntry {
      public String tenc;
      public String jenc;

      public MapEntry(String tenc, String jenc) {
         this.tenc = tenc;
         this.jenc = jenc;
      }
   }
}
