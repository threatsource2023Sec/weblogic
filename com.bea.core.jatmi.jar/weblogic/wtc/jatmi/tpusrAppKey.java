package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Utilities;
import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCSecurityManager;
import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import weblogic.wtc.WTCLogger;

public final class tpusrAppKey implements TCAppKey {
   private File myfile;
   private LruCache userMap;
   private String anon_user = null;
   private String tpusrfile = null;
   private long l_time;
   private int dfltAppkey = -1;
   private int cache_size;
   private DefaultUserRec anonUserRec = null;
   private boolean allinmemory;
   private boolean allowAnon;
   private boolean fileExists = true;
   private boolean fix_size = false;
   private boolean _cached = false;
   private static final int USRIDX = 0;
   private static final int PWDIDX = 1;
   private static final int UIDIDX = 2;
   private static final int GIDIDX = 3;
   private static final int CLTIDX = 4;
   private static final int DFLT_CA_SIZE = 10000;
   private static final byte[] tpsysadm_string = new byte[]{116, 112, 115, 121, 115, 97, 100, 109};
   private static final byte[] tpsysop_string = new byte[]{116, 112, 115, 121, 115, 111, 112};

   public void init(String param, boolean anonAllowed, int dfltAppKey) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/init(param " + param + ", anonAllowed " + anonAllowed + ",dfltAppKey " + dfltAppKey + ")");
      }

      this.parseParam(param);
      this.myfile = new File(this.tpusrfile);
      if (!this.myfile.exists()) {
         WTCLogger.logErrorFileDoesNotExist(this.tpusrfile);
         this.fileExists = false;
      } else {
         if (!this.myfile.isFile()) {
            WTCLogger.logErrorNotAFile(this.tpusrfile);
            if (traceEnabled) {
               ntrace.doTrace("*]/tpusrAppKey/init(20, " + this.tpusrfile + " is not a file) return");
            }

            throw new TPException(12, "Invalid TPUSR file");
         }

         if (!this.myfile.canRead()) {
            WTCLogger.logErrorFileNotReadable(this.tpusrfile);
            if (traceEnabled) {
               ntrace.doTrace("*]/tpusrAppKey/init(30, " + this.tpusrfile + " is not readable) return");
            }

            throw new TPException(12, "Bad TPUSR file permission");
         }
      }

      if (this.cache_size == -1) {
         this.cache_size = this.calCacheSize();
      }

      this.userMap = new LruCache(this.cache_size);
      if (this.fileExists && this.initCache() == -1) {
         if (traceEnabled) {
            ntrace.doTrace("*]/tpusrAppKey/init(40, init cache failed) return");
         }

         throw new TPException(12, "fail to create user cache");
      } else {
         if (this.fileExists) {
            this.l_time = this.myfile.lastModified();
         } else {
            this.l_time = 0L;
         }

         this.anon_user = TCSecurityManager.getAnonymousUserName();
         this.allowAnon = anonAllowed;
         this.dfltAppkey = dfltAppKey;
         this.anonUserRec = new DefaultUserRec(this.anon_user, this.dfltAppkey);
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/init(50) return");
         }

      }
   }

   public void uninit() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/uninit()");
      }

      if (this.userMap != null) {
         this.userMap.clear();
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tpusrAppKey/uninit(10) return");
      }

   }

   public UserRec getTuxedoUserRecord(TCAuthenticatedUser subj) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/getTuxedoUserRecord(subj " + subj + ")");
      }

      if (!this.myfile.exists()) {
         if (this.fileExists) {
            this.l_time = 0L;
            this.fileExists = false;
            this.userMap.clear();
         }

         if (this.allowAnon) {
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(10) return anonymous user: " + this.anonUserRec);
            }

            return this.anonUserRec;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(20) return null");
            }

            return null;
         }
      } else {
         Object[] obj = subj.getPrincipals();
         if (obj != null && obj.length != 0) {
            long n_time = this.myfile.lastModified();
            if (!this.fileExists) {
               this.fileExists = true;
            } else if (n_time != this.l_time) {
               this.userMap.clear();
            }

            if (n_time != this.l_time) {
               if (!this.fix_size) {
                  int new_size = this.calCacheSize();
                  if (new_size != this.cache_size) {
                     this.cache_size = new_size;
                     this.userMap.setCacheSize(this.cache_size);
                  }
               }

               this.initCache();
               this.l_time = n_time;
            }

            for(int i = 0; i < obj.length; ++i) {
               Principal user = (Principal)obj[i];
               String username = user.getName();
               if (username.equals(this.anon_user)) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(50) return anonymous user: " + this.anonUserRec);
                  }

                  return this.anonUserRec;
               }

               UserRec rec;
               if ((rec = this.getFromCache(username)) != null) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(60) return user: " + rec);
                  }

                  return rec;
               }
            }

            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(70) return null user");
            }

            return null;
         } else if (this.allowAnon) {
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(30) return anonymous user: " + this.anonUserRec);
            }

            return this.anonUserRec;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/getTuxedoUserRecord(40) return null");
            }

            return null;
         }
      }
   }

   private UserRec getFromCache(String name) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/getFromCache(name " + name + ")");
      }

      UserRec ur = (UserRec)this.userMap.get(name);
      if (ur != null || ur == null && this.allinmemory) {
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/getFromCache(10) return user :" + ur);
         }

         return ur;
      } else {
         try {
            FileInputStream fin = new FileInputStream(this.tpusrfile);

            byte[] line;
            while((line = this.readOneLine(fin)) != null) {
               DefaultUserRec ur;
               if ((ur = this.parseOneLine(line)) != null && name.equals(ur.getLocalUserName())) {
                  this.userMap.put(name, ur);
                  fin.close();
                  if (traceEnabled) {
                     ntrace.doTrace("]/tpusrAppKey/getFromCache(20) return user:" + ur);
                  }

                  return ur;
               }
            }

            fin.close();
         } catch (FileNotFoundException var7) {
            WTCLogger.logErrorFileNotFound(this.tpusrfile);
         } catch (SecurityException var8) {
            WTCLogger.logErrorFileSecurity(this.tpusrfile);
         } catch (IOException var9) {
            WTCLogger.logErrorFileIOError(this.tpusrfile, var9.toString());
         }

         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/getFromCache(30) return null");
         }

         return null;
      }
   }

   private int initCache() {
      int i = 0;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/initCache()");
      }

      this.allinmemory = true;

      try {
         FileInputStream fin = new FileInputStream(this.tpusrfile);

         byte[] line;
         while((line = this.readOneLine(fin)) != null && i < this.cache_size) {
            DefaultUserRec ur;
            if ((ur = this.parseOneLine(line)) != null) {
               ++i;
               this.userMap.put(ur.getLocalUserName(), ur);
            }
         }

         fin.close();
         if (i >= this.cache_size) {
            if (traceEnabled) {
               ntrace.doTrace("/tpusrAppKey/initCache/number of user > cache size");
            }

            this.allinmemory = false;
         }
      } catch (FileNotFoundException var7) {
         WTCLogger.logErrorFileNotFound(this.tpusrfile);
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/initCache(10) return -1");
         }

         return -1;
      } catch (SecurityException var8) {
         WTCLogger.logErrorFileSecurity(this.tpusrfile);
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/initCache(20) return -1");
         }

         return -1;
      } catch (IOException var9) {
         WTCLogger.logErrorFileIOError(this.tpusrfile, var9.toString());
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/initCache(30) return -1");
         }

         return -1;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/tpusrAppKey/initialCache(50) return 0");
      }

      return 0;
   }

   private void dumpCache(String label) {
      boolean traceEnabled = ntrace.getTraceLevel() >= 1200000;
      if (traceEnabled) {
         Set caSet = this.userMap.entrySet();
         Iterator i = caSet.iterator();
         ntrace.doTrace("========== " + label + " Cache Contents ==========");

         while(i.hasNext()) {
            ntrace.doTrace(i.next().toString());
         }

         ntrace.doTrace("==================================================");
      }

   }

   private byte[] readOneLine(FileInputStream fh) {
      int len = 80;
      byte[] line = new byte[len];
      int inp = true;
      int idx = 0;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/readOneLine(file handle " + fh);
      }

      byte[] tmp;
      int inp;
      try {
         while((inp = fh.read()) != -1) {
            if (idx != 0 || inp != 10 && inp != 0) {
               if (inp == 10) {
                  break;
               }

               if (idx == len - 1) {
                  tmp = new byte[len + 80];
                  System.arraycopy(line, 0, tmp, 0, len);
                  line = tmp;
                  len += 80;
               }

               line[idx] = (byte)inp;
               ++idx;
            }
         }
      } catch (IOException var8) {
         WTCLogger.logErrorFileIOError(this.tpusrfile, var8.toString());
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/readOneLine(10) return null");
         }

         return null;
      }

      if (inp == -1 && idx == 0) {
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/readOneLine(20) return null");
         }

         return null;
      } else {
         tmp = new byte[idx];
         System.arraycopy(line, 0, tmp, 0, idx);
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/readOneLine(30) return a line " + Utilities.prettyByteArray(tmp));
         }

         return tmp;
      }
   }

   private DefaultUserRec parseOneLine(byte[] line) {
      int key = -1;
      byte[] buid = null;
      byte[] bgid = null;
      byte[] clt = null;
      byte[] uname = null;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/parseOneLine(line " + Utilities.prettyByteArray(line) + ")");
      }

      int firstCharacter = line[0];
      if (firstCharacter != 35 && firstCharacter != 10 && firstCharacter != 33 && firstCharacter != 0 && firstCharacter != 13) {
         int fldlen = 0;
         int sidx = 0;
         int i = 0;

         int fn;
         for(fn = 0; i < line.length && fn <= 4; ++i) {
            if (line[i] == 58) {
               switch (fn) {
                  case 0:
                     uname = new byte[fldlen];
                     System.arraycopy(line, sidx, uname, 0, fldlen);
                  case 1:
                  default:
                     break;
                  case 2:
                     buid = new byte[fldlen];
                     System.arraycopy(line, sidx, buid, 0, fldlen);
                     break;
                  case 3:
                     bgid = new byte[fldlen];
                     System.arraycopy(line, sidx, bgid, 0, fldlen);
                     break;
                  case 4:
                     if (line[sidx] == 84 && line[sidx + 1] == 80 && line[sidx + 2] == 67 && line[sidx + 3] == 76 && line[sidx + 4] == 84 && line[sidx + 5] == 78 && line[sidx + 6] == 77 && line[sidx + 7] == 44) {
                        sidx += 8;
                        fldlen -= 8;
                     }

                     if (fldlen > 0) {
                        clt = new byte[fldlen];
                        System.arraycopy(line, sidx, clt, 0, fldlen);
                     }
               }

               ++fn;
               fldlen = 0;
               sidx = i + 1;
            } else {
               ++fldlen;
            }
         }

         if (fn <= 4 && fldlen > 0) {
            switch (fn) {
               case 0:
                  uname = new byte[fldlen];
                  System.arraycopy(line, sidx, uname, 0, fldlen);
               case 1:
               default:
                  break;
               case 2:
                  buid = new byte[fldlen];
                  System.arraycopy(line, sidx, buid, 0, fldlen);
                  break;
               case 3:
                  bgid = new byte[fldlen];
                  System.arraycopy(line, sidx, bgid, 0, fldlen);
                  break;
               case 4:
                  if (line[sidx] == 84 && line[sidx + 1] == 80 && line[sidx + 2] == 67 && line[sidx + 3] == 76 && line[sidx + 4] == 84 && line[sidx + 5] == 78 && line[sidx + 6] == 77 && line[sidx + 7] == 44) {
                     sidx += 8;
                     fldlen -= 8;
                  }

                  clt = new byte[fldlen];
                  System.arraycopy(line, sidx, clt, 0, fldlen);
            }
         }

         if (uname != null && buid != null && bgid != null) {
            String name = new String(uname);
            if (clt != null) {
               if (Arrays.equals(tpsysadm_string, clt)) {
                  key = Integer.MIN_VALUE;
               } else if (Arrays.equals(tpsysop_string, clt)) {
                  key = -1073741824;
               }
            }

            if (key == -1) {
               int uid = false;
               int gid = false;

               try {
                  Integer u_val = new Integer(new String(buid));
                  Integer g_val = new Integer(new String(bgid));
                  int uid = u_val;
                  int gid = g_val;
                  uid &= 131071;
                  gid &= 16383;
                  key = uid | gid << 17;
               } catch (NumberFormatException var20) {
                  if (traceEnabled) {
                     ntrace.doTrace("]/tpusrAppKey/parseOneLine(30) return null");
                  }

                  return null;
               }
            }

            DefaultUserRec usr = new DefaultUserRec(name, key);
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/parseOneLine(40) return user: " + usr);
            }

            return usr;
         } else {
            if (traceEnabled) {
               ntrace.doTrace("]/tpusrAppKey/parseOneLine(20) return null");
            }

            return null;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/parseOneLine(10) return null");
         }

         return null;
      }
   }

   private void parseParam(String param) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/parseParam(param " + param + ")");
      }

      this.cache_size = -1;
      if (param == null) {
         this.tpusrfile = new String("tpusr");
         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/parseParam(10) return");
         }

      } else {
         this.tpusrfile = param.trim();
         if (traceEnabled) {
            ntrace.doTrace("tpusrfile = " + this.tpusrfile);
         }

         String caStr = System.getProperty("weblogic.wtc.cacheSize");
         if (caStr != null) {
            String kw = caStr.trim();

            try {
               Integer val = new Integer(kw);
               this.cache_size = val;
               this.fix_size = true;
               if (traceEnabled) {
                  ntrace.doTrace("custom cache_size = " + this.cache_size);
               }
            } catch (NumberFormatException var9) {
               WTCLogger.logErrorBadNumberFormat(kw);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/tpusrAppKey/parseParam(20) return");
         }

      }
   }

   private int calCacheSize() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/calCacheSize()");
      }

      int size = 0;
      int fac = 1;
      long i = this.myfile.length() / 32L;
      if (traceEnabled) {
         ntrace.doTrace("[/tpusrAppKey/calCacheSize()");
      }

      if (i == 0L) {
         i = 1L;
      }

      do {
         int lad;
         if (i > 10000L) {
            lad = 10000;
            i -= 10000L;
         } else {
            lad = (int)i;
         }

         size += (lad / fac + 99) / 100 * 100;
         if (fac == 1) {
            fac = 4;
         } else if (fac < 32) {
            fac *= 2;
         }

         fac *= 4;
      } while(i > 10000L && size < 50000);

      if (traceEnabled) {
         ntrace.doTrace("]/tpusrAppKey/calCacheSize(10) returns " + size);
      }

      return size;
   }

   public void doCache(boolean b) {
      this._cached = b;
   }

   public boolean isCached() {
      return this._cached;
   }
}
