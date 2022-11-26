package weblogic.servlet.internal.session;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import weblogic.cache.utils.BubblingCache;
import weblogic.i18n.logging.Loggable;
import weblogic.management.RuntimeDir.Current;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.WebServerRegistry;

public final class FileSessionContext extends SessionContext {
   private static final String ROOT;
   private File sessionDir = null;
   private int sessionDirLen = 0;
   private BubblingCache cachedSessions = null;
   private int fileSessionsCacheSize = 256;
   protected static final String[][] RESERVED;
   protected static final boolean WRITE_VERSIONS;
   private static final int PATH_LIM = 4;

   public FileSessionContext(WebAppServletContext sci, SessionConfigManager scm) {
      super(sci, scm);

      String fullpath;
      try {
         String tmpPath = sci.getTempPath();
         fullpath = this.configMgr.getPersistentStoreDir();
         fullpath = fullpath.replace('/', File.separatorChar);
         if (!isAbsolute(fullpath)) {
            File dataDir = new File(Current.get().getDataDirForServer(sci.getServer().getServerName()), ROOT + tmpPath);
            if (!dataDir.exists() && !dataDir.mkdirs()) {
               HTTPLogger.logUnableToMakeDirectory(sci.getLogContext(), dataDir.getAbsolutePath());
            }

            this.sessionDir = new File(dataDir, fullpath);
         } else {
            String contextName = sci.getName();
            if (contextName != null && contextName.length() > 0) {
               fullpath = fullpath + File.separatorChar + contextName;
            }

            this.sessionDir = new File(fullpath);
         }
      } catch (Exception var9) {
         throw new Error(var9.toString());
      }

      if (this.configMgr.isCleanupSessionFilesEnabled()) {
         this.deleteSessionTree();
      }

      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logPersistenceLoggable(this.sessionDir.getAbsolutePath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      boolean ok = false;

      for(int i = 0; i < 10; ++i) {
         synchronized(this.getDirTreeLockObject()) {
            if (this.sessionDir.mkdirs() || this.sessionDir.isDirectory()) {
               ok = true;
               break;
            }

            try {
               Thread.sleep(500L);
            } catch (Exception var8) {
            }
         }
      }

      if (!ok) {
         throw new RuntimeException("Cannot make directory: " + this.sessionDir.getAbsolutePath());
      } else {
         fullpath = this.sessionDir.getAbsolutePath();
         if (fullpath.endsWith(File.separator)) {
            this.sessionDirLen = fullpath.length();
         } else {
            this.sessionDirLen = fullpath.length() + 1;
         }

         this.fileSessionsCacheSize = this.configMgr.getCacheSize();
         if (this.fileSessionsCacheSize > 0) {
            this.cachedSessions = new BubblingCache(this.fileSessionsCacheSize);
         }

      }
   }

   private Object getDirTreeLockObject() {
      return this.servletContext.getHttpServer().getFileSessionPersistenceLockObject();
   }

   static boolean containsReservedKeywords(String id) {
      String[] dir = getStorageDirs(id.toUpperCase());
      int j;
      if (4 <= RESERVED.length) {
         for(int i = 0; i < dir.length - 1; ++i) {
            String name = dir[i];

            for(j = 0; j < RESERVED[4].length; ++j) {
               if (RESERVED[4][j].equals(name)) {
                  return true;
               }
            }
         }
      }

      String filename = dir[dir.length - 1];
      int len = filename.length();
      if (len <= RESERVED.length) {
         for(j = 0; j < RESERVED[len].length; ++j) {
            if (RESERVED[len][j].equals(filename)) {
               return true;
            }
         }
      }

      return false;
   }

   protected void invalidateOrphanedSessions() {
      Set ids = this.getServletContext().getServer().getSessionLogin().getAllIds();
      if (!ids.isEmpty()) {
         String[] internalIds = this.getIdsInternal();

         int i;
         for(i = 0; i < internalIds.length; ++i) {
            ids.remove(internalIds[i]);
         }

         if (i != 0) {
            String id = null;
            Iterator iter = ids.iterator();

            while(iter.hasNext()) {
               id = (String)iter.next();
               this.getServletContext().getServer().getSessionLogin().unregister(id, this.getServletContext().getContextPath());
            }

         }
      }
   }

   private static boolean isAbsolute(String s) {
      String osname = System.getProperty("os.name");
      if (osname != null) {
         osname = osname.toLowerCase(Locale.ENGLISH);
         if (osname.indexOf("windows") >= 0 && s.length() > 2 && Character.isLetter(s.charAt(0)) && s.charAt(1) == ':') {
            return true;
         }
      }

      return s.length() > 0 && s.charAt(0) == File.separatorChar;
   }

   public String getPersistentStoreType() {
      return "file";
   }

   private void deleteSessionTree() {
      if (this.isDebugEnabled()) {
         Loggable l = HTTPSessionLogger.logDeleteDirectoryLoggable(this.sessionDir.getAbsolutePath());
         DEBUG_SESSIONS.debug(l.getMessage());
      }

      FileEnumerator x = new FileEnumerator(this, true, false);
      x.recurse(this.sessionDir);
      File[] sessionFiles = x.getSessionFiles();

      for(int i = 0; i < sessionFiles.length; ++i) {
         if (!sessionFiles[i].delete()) {
            HTTPSessionLogger.logUnableToDelete(sessionFiles[i].getAbsolutePath());
         }
      }

   }

   private void makeStorageDir(String id) {
      boolean ok = false;
      File dir = this.getSessionPath(SessionData.getID(id));
      dir = new File(dir.getParent());
      if (!dir.isDirectory()) {
         for(int j = 0; j < 10; ++j) {
            synchronized(this.getDirTreeLockObject()) {
               if (dir.mkdirs() || dir.isDirectory()) {
                  ok = true;
                  break;
               }
            }

            try {
               Thread.sleep(500L);
            } catch (Exception var7) {
            }
         }

         if (!ok) {
            throw new RuntimeException("Cannot make directory: " + dir.getAbsolutePath());
         }
      }
   }

   public HttpSession getNewSession(String id, ServletRequestImpl req, ServletResponseImpl res) {
      FileSessionData session = new FileSessionData(id, this, true);
      session.incrementActiveRequestCount();
      this.addSession(session.id, session);
      this.makeStorageDir(session.id);
      this.incrementOpenSessionsCount();
      return session;
   }

   private String path2session(File f) {
      String path = f.getAbsolutePath().substring(this.sessionDirLen);
      StringBuilder sb = new StringBuilder();
      int len = path.length();

      for(int i = 0; i < len; ++i) {
         char c = path.charAt(i);
         if (c != File.separatorChar) {
            sb.append(c);
         }
      }

      return sb.toString();
   }

   private String session2path(String s) {
      StringBuilder sb = new StringBuilder();
      String[] dir = getStorageDirs(s);
      if (dir != null) {
         for(int i = 0; i < dir.length - 1; ++i) {
            sb.append(dir[i]);
            sb.append(File.separatorChar);
         }

         sb.append(dir[dir.length - 1]);
      }

      return sb.toString();
   }

   static String[] getStorageDirs(String s) {
      int len = s.length();
      int size = len / 4;
      if (len % 4 > 0) {
         ++size;
      }

      String[] dir = new String[size];
      int i = 0;

      for(int count = 0; i < len - 4; ++count) {
         dir[count] = s.substring(i, i + 4);
         i += 4;
      }

      dir[size - 1] = s.substring(i);
      return dir;
   }

   public File getSessionPath(String id) {
      return new File(this.sessionDir, this.session2path(id));
   }

   void registerIdChangedSession(SessionData session) {
      super.registerIdChangedSession(session);
      if (this.cachedSessions != null && this.cachedSessions.remove(session.oldId) != null) {
         this.cachedSessions.put(session.id, session);
      }

      if (this.transientData != null && this.transientData.remove(session.oldId) != null) {
         this.transientData.put(session.id, session.transientAttributes);
      }

   }

   protected void afterSync(SessionData session) {
      FileSessionData data = (FileSessionData)session;
      if (data.needToSyncNewSessionId()) {
         File f = this.getSessionPath(data.oldId);
         this.cleanSessionFile(false, data.oldId, f);
      }

      String id = data.id;
      if (this.cachedSessions != null) {
         this.cachedSessions.put(data.id, session);
      }

      DataOutputStream os = null;
      File saveTo = null;

      try {
         saveTo = this.getSessionPath(id);
         synchronized(this.getDirTreeLockObject()) {
            this.makeStorageDir(id);
            os = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(saveTo)));
            ObjectOutputStream oos = WebServerRegistry.getInstance().getContainerSupportProvider().getObjectOutputStream(os);
            if (WRITE_VERSIONS) {
               this.writeVersionInfo((ServletWorkContext)data.getServletContext(), oos);
            }

            oos.writeObject(data);
            oos.flush();
            os.writeLong(data.getLAT());
            oos.close();
            os.close();
         }

         if (data.transientAttributes != null) {
            this.transientData.put(id, data.transientAttributes);
         }

         os = null;
         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logPickledSessionLoggable(id, saveTo.getAbsolutePath());
            DEBUG_SESSIONS.debug(l.getMessage());
         }
      } catch (ThreadDeath var20) {
         throw var20;
      } catch (Throwable var21) {
         HTTPSessionLogger.logErrorSavingSessionData(var21);
         if (saveTo != null) {
            saveTo.delete();
         }
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (Exception var18) {
            }
         }

      }

      this.removeSession(id);
   }

   protected boolean needToPassivate() {
      return true;
   }

   public void destroy(boolean redeploy, boolean serverShutdown) {
      super.destroy(serverShutdown);
      if (this.configMgr.isCleanupSessionFilesEnabled()) {
         this.deleteSessionTree();
      }

   }

   private void writeVersionInfo(ServletWorkContext ctx, ObjectOutputStream oos) throws IOException {
      if (ctx.getVersionId() == null) {
         oos.writeObject((Object)null);
      } else {
         oos.writeObject(new AppVersionInfo(ctx.getVersionId()));
      }

   }

   private File getParent(File f) {
      String s = f.getParent();
      if (s == null) {
         return null;
      } else {
         File parent = new File(s);
         s = parent.getAbsolutePath();
         return s.length() <= this.sessionDirLen ? null : parent;
      }
   }

   private void delTree(File dir) {
      synchronized(this.getDirTreeLockObject()) {
         if (dir != null && dir.exists()) {
            do {
               String[] files = dir.list();
               if (files != null && files.length > 0) {
                  return;
               }

               if (!dir.delete()) {
                  return;
               }
            } while((dir = this.getParent(dir)) != null);

         }
      }
   }

   boolean invalidateSession(SessionData data, boolean trigger) {
      if (data == null) {
         return false;
      } else {
         String id = data.id;
         File f = this.getSessionPath(id);
         this.removeSession(id);
         if (this.cachedSessions != null) {
            this.cachedSessions.remove(data.id);
         }

         this.transientData.remove(id);
         data.remove();
         data.setValid(false);
         this.decrementOpenSessionsCount();
         return this.cleanSessionFile(trigger, id, f);
      }
   }

   private boolean cleanSessionFile(boolean trigger, String id, File f) {
      try {
         boolean deleted = false;

         for(int retry = trigger ? 1 : 3; retry > 0; --retry) {
            if (!f.exists()) {
               if (this.isDebugEnabled()) {
                  Loggable l = HTTPSessionLogger.logNotInvalidatedLoggable(f.getAbsolutePath());
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               return false;
            }

            if (f.delete()) {
               deleted = true;
               break;
            }

            if (retry > 1) {
               if (this.isDebugEnabled()) {
                  DEBUG_SESSIONS.debug("Retry deleting session persistent file : " + f.getAbsolutePath());
               }

               try {
                  Thread.sleep((long)(50 + (int)(Math.random() * 50.0)));
               } catch (Exception var7) {
               }
            }
         }

         if (!deleted) {
            HTTPSessionLogger.logUnableToDelete(f.getAbsolutePath());
         } else if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logDeletedFileLoggable(f.getAbsolutePath());
            DEBUG_SESSIONS.debug(l.getMessage());
         }

         this.delTree(this.getParent(f));
      } catch (Throwable var8) {
         HTTPSessionLogger.logTestFailure(id, var8);
         if (f != null) {
            f.delete();
            this.delTree(this.getParent(f));
         }
      }

      return true;
   }

   private FileSessionData loadSession(FileInputStream i, File f, String id, boolean forceCheck) {
      if (!f.canRead()) {
         this.removeSession(id);
         return null;
      } else {
         synchronized(this.getDirTreeLockObject()) {
            if (!f.canRead()) {
               this.removeSession(id);
               return null;
            } else {
               FileSessionData var9;
               try {
                  if (i == null) {
                     i = new FileInputStream(f);
                  }

                  ObjectInputStream ois = WebServerRegistry.getInstance().getContainerSupportProvider().getObjectInputStream(i);
                  Object o = ois.readObject();
                  FileSessionData ret;
                  if (o != null && !(o instanceof AppVersionInfo)) {
                     ret = (FileSessionData)o;
                  } else {
                     ret = (FileSessionData)ois.readObject();
                  }

                  ret.id = id;
                  ret.setSessionContext(this);
                  ret.setModified(false);
                  ois.close();
                  i.close();
                  ret.transientAttributes = (Hashtable)this.transientData.get(id);
                  i = null;
                  if (!forceCheck || ret.isValidForceCheck()) {
                     var9 = ret;
                     return var9;
                  }

                  var9 = null;
               } catch (FileNotFoundException var30) {
                  this.logLoadingErrorForDebug(var30);
                  return null;
               } catch (EOFException var31) {
                  this.logLoadingErrorForDebug(var31);
                  return null;
               } catch (IOException var32) {
                  this.logLoadingErrorForDebug(var32);
                  return null;
               } catch (ClassNotFoundException var33) {
                  this.logLoadingErrorForDebug("Probably the classfiles have changed for a new version", var33);
                  return null;
               } catch (Throwable var34) {
                  HTTPSessionLogger.logErrorLoadingSessionData(id, var34);
                  return null;
               } finally {
                  if (i != null) {
                     try {
                        i.close();
                     } catch (Exception var29) {
                     }
                  }

               }

               return var9;
            }
         }
      }
   }

   private final void logLoadingErrorForDebug(Exception e) {
      this.logLoadingErrorForDebug((String)null, e);
   }

   private final void logLoadingErrorForDebug(String msg, Exception e) {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(msg != null ? msg : "Failed to load session from file. It could be interfered by other server which is pointing to the same persistent file system.", e);
      }

   }

   public String[] getIdsInternal() {
      FileEnumerator fileEnum = new FileEnumerator(this, false, false);
      synchronized(this.getDirTreeLockObject()) {
         fileEnum.recurse(this.sessionDir);
      }

      return fileEnum.getSessionIds();
   }

   void unregisterExpiredSessions(ArrayList expired) {
   }

   public int getCurrOpenSessionsCount() {
      FileEnumerator fileEnum = new FileEnumerator(this, false, true);
      fileEnum.recurse(this.sessionDir);
      return fileEnum.getCount();
   }

   private boolean isCacheStale(SessionData session) {
      File f = this.getSessionPath(session.id);
      if (!f.exists()) {
         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logNotInvalidatedLoggable(f.getAbsolutePath());
            DEBUG_SESSIONS.debug(l.getMessage());
         }

         return true;
      } else {
         RandomAccessFile raf = null;
         synchronized(this.getDirTreeLockObject()) {
            try {
               raf = new RandomAccessFile(f, "r");
               raf.seek(f.length() - 8L);
               long modtime = raf.readLong();
               raf.seek(0L);
               raf.close();
               raf = null;
               if (modtime <= session.getLAT()) {
                  boolean var7 = false;
                  return var7;
               }
            } catch (Throwable var20) {
               if (this.isDebugEnabled()) {
                  Loggable l = HTTPSessionLogger.logTestFailureLoggable(session.id, var20);
                  DEBUG_SESSIONS.debug(l.getMessage());
               }

               f.delete();
               this.delTree(this.getParent(f));
            } finally {
               if (raf != null) {
                  try {
                     raf.close();
                  } catch (Exception var19) {
                  }
               }

               raf = null;
            }

            return true;
         }
      }
   }

   public SessionData getSessionInternal(String id, ServletRequestImpl req, ServletResponseImpl res) {
      id = SessionData.getID(id);
      FileSessionData sess = (FileSessionData)this.getOpenSession(id);
      if (sess == null && this.cachedSessions != null) {
         sess = (FileSessionData)this.cachedSessions.get(id);
         if (sess != null) {
            if (!sess.sessionInUse() && this.isCacheStale(sess)) {
               this.cachedSessions.remove(sess.id);
               sess = null;
            } else {
               this.addSession(sess.id, sess);
            }
         }
      }

      if (sess != null) {
         if (req != null && res != null) {
            if (!sess.isValidForceCheck()) {
               this.removeFromCache(sess);
               return null;
            }

            sess.incrementActiveRequestCount();
            if (!sess.isValidForceCheck()) {
               this.removeFromCache(sess);
               return null;
            }
         }

         return sess;
      } else {
         File file = this.getSessionPath(id);
         if (this.isDebugEnabled()) {
            Loggable l = HTTPSessionLogger.logSessionPathLoggable(id, file.getAbsolutePath(), file.exists());
            DEBUG_SESSIONS.debug(l.getMessage());
         }

         sess = this.loadSession((FileInputStream)null, file, id, req != null && res != null);
         if (sess != null && req != null && res != null) {
            sess.reinitRuntimeMBean();
            sess.updateVersionIfNeeded(this);
            sess.getContext().addSession(id, sess);
            sess.incrementActiveRequestCount();
            sess.notifyActivated(new HttpSessionEvent(sess));
         }

         return sess;
      }
   }

   private void removeFromCache(FileSessionData sess) {
      if (this.cachedSessions != null) {
         this.cachedSessions.remove(sess.id);
      }

      this.removeSession(sess.id);
   }

   public int getNonPersistedSessionCount() {
      return 0;
   }

   public String lookupAppVersionIdForSession(String sid, ServletRequestImpl req, ServletResponseImpl res) {
      if (sid == null) {
         return null;
      } else {
         sid = SessionData.getID(sid);
         if (this.cachedSessions != null && !this.cachedSessions.isEmpty()) {
            SessionData sess = (SessionData)this.cachedSessions.get(sid);
            if (sess != null && sess.getServletContext() != null) {
               return ((ServletWorkContext)sess.getServletContext()).getVersionId();
            }
         }

         File sessFile = this.getSessionPath(sid);
         if (sessFile != null && sessFile.exists() && !sessFile.isDirectory()) {
            FileInputStream fis = null;
            String vid = null;

            try {
               fis = new FileInputStream(sessFile);
               ObjectInputStream ois = WebServerRegistry.getInstance().getContainerSupportProvider().getObjectInputStream(fis);
               Object o = ois.readObject();
               if (o != null && o instanceof AppVersionInfo) {
                  vid = ((AppVersionInfo)o).getVersionInfo();
               }

               if (HTTPDebugLogger.isEnabled()) {
                  StringBuilder sb = new StringBuilder("got version id \"");
                  sb.append(vid).append("\" for session id \"").append(sid).append("\" from file \"").append(sessFile).append("\"");
                  HTTPDebugLogger.debug(sb.toString());
               }
            } catch (FileNotFoundException var26) {
               this.logLoadingErrorForDebug(var26);
            } catch (EOFException var27) {
               this.logLoadingErrorForDebug(var27);
            } catch (IOException var28) {
               this.logLoadingErrorForDebug(var28);
            } catch (ClassNotFoundException var29) {
               this.logLoadingErrorForDebug(var29);
            } catch (Exception var30) {
               this.logLoadingErrorForDebug(var30);
            } finally {
               if (fis != null) {
                  try {
                     fis.close();
                  } catch (Throwable var25) {
                  }
               }

            }

            return vid;
         } else {
            return null;
         }
      }
   }

   static {
      ROOT = "webapps" + File.separator;
      RESERVED = new String[][]{new String[0], new String[0], new String[0], {"PRN"}, {"LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"}, new String[0], new String[0]};
      WRITE_VERSIONS = !Boolean.getBoolean("weblogic.servlet.session.PersistentBackCompatibility");
   }

   private class FileEnumerator {
      private final ArrayList list;
      private final ArrayList sessionIds;
      private FileSessionContext ctxt;
      private boolean listFiles = false;
      private boolean justCount = false;
      private int count = 0;

      FileEnumerator(FileSessionContext ctxt, boolean files, boolean count) {
         this.ctxt = ctxt;
         this.list = new ArrayList();
         this.listFiles = files;
         this.justCount = count;
         this.sessionIds = new ArrayList();
      }

      public void recurse(File root) {
         String[] s = root.list();
         if (s != null && s.length != 0) {
            for(int i = 0; i < s.length; ++i) {
               File f = new File(root, s[i]);
               if (f.isDirectory()) {
                  this.recurse(f);
                  if (!this.justCount && this.listFiles) {
                     this.list.add(f);
                  }
               } else if (this.justCount) {
                  ++this.count;
               } else if (this.listFiles) {
                  this.list.add(f);
               } else {
                  String id = this.ctxt.path2session(f);
                  this.list.add(id);
                  this.sessionIds.add(id);
               }
            }

         }
      }

      public int getCount() {
         return this.count;
      }

      public File[] getSessionFiles() {
         File[] files = new File[this.list.size()];
         return (File[])((File[])this.list.toArray(files));
      }

      public String[] getSessionIds() {
         String[] ids = new String[this.sessionIds.size()];
         return (String[])((String[])this.sessionIds.toArray(ids));
      }
   }

   private static final class AppVersionInfo implements Serializable {
      private final String versionId;

      AppVersionInfo(String vid) {
         this.versionId = vid;
      }

      public String getVersionInfo() {
         return this.versionId;
      }
   }
}
