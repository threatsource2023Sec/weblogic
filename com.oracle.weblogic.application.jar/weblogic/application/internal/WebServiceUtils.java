package weblogic.application.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import weblogic.application.ApplicationFileManager;
import weblogic.application.utils.HaltListener;
import weblogic.application.utils.HaltListenerManager;
import weblogic.application.utils.PathUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.utils.jars.VirtualJarFile;

public final class WebServiceUtils implements HaltListener {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static boolean DEBUG = false;
   private static final String WEB_SERVICES_URI = "WEB-INF/web-services.xml";
   private static final String WEB_URI = "WEB-INF/web-services.xml";
   private static String FILE_NAME = "WebServiceUtils.ser";
   private LinkedList cacheList;
   private static final int MAX_LIST_SIZE = getMaxListSize();
   private File serFile;

   private static int getMaxListSize() {
      int maxListSizeValue = 0;
      String maxListSizeString = null;

      try {
         maxListSizeString = System.getProperty("weblogic.application.webserviceutils.maxlistsize");
         if (maxListSizeString != null) {
            maxListSizeValue = Integer.parseInt(maxListSizeString);
         }
      } catch (NumberFormatException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("maxlistsize is not Integer number:" + maxListSizeString);
         }
      } catch (SecurityException var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("SecurityExcpetion is raised on maxlistsize processing:" + var4);
         }
      }

      return maxListSizeValue > 20 ? maxListSizeValue : 20;
   }

   private WebServiceUtils() {
      this.serFile = new File(PathUtils.getTempDir(), FILE_NAME);
      this.cacheList = this.readSavedCache();
      HaltListenerManager.registerListener(this);
   }

   private LinkedList readSavedCache() {
      if (!this.serFile.exists()) {
         return new LinkedList();
      } else {
         ObjectInputStream is = null;

         LinkedList var3;
         try {
            FileInputStream fis = new FileInputStream(this.serFile);
            is = new ObjectInputStream(fis);
            var3 = (LinkedList)is.readObject();
            return var3;
         } catch (Exception var13) {
            if (DEBUG) {
               var13.printStackTrace();
            }

            var3 = new LinkedList();
         } finally {
            if (is != null) {
               try {
                  is.close();
               } catch (Exception var12) {
               }
            }

         }

         return var3;
      }
   }

   public void halt() {
      ObjectOutputStream os = null;

      try {
         FileOutputStream fos = new FileOutputStream(this.serFile);
         os = new ObjectOutputStream(fos);
         os.writeObject(this.cacheList);
      } catch (IOException var11) {
         if (DEBUG) {
            var11.printStackTrace();
         }
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (IOException var10) {
            }
         }

      }

   }

   public static WebServiceUtils getWebServiceUtils() {
      return WebServiceUtils.WebServiceUtilsSingleton.SINGLETON;
   }

   private boolean isCacheable(VirtualJarFile ear) {
      return !ear.isDirectory();
   }

   private Set readCache(ApplicationMBean appMBean, VirtualJarFile ear) {
      if (!this.isCacheable(ear)) {
         return null;
      } else {
         String appName = appMBean.getName();
         synchronized(this.cacheList) {
            Iterator it = this.cacheList.iterator();

            CacheEntry e;
            do {
               if (!it.hasNext()) {
                  return null;
               }

               e = (CacheEntry)it.next();
            } while(!e.appName.equals(appName));

            File earFile = ear.getRootFiles()[0];
            it.remove();
            if (earFile.lastModified() == e.lastModified) {
               this.cacheList.addFirst(e);
               return e.wsSet;
            } else {
               return null;
            }
         }
      }
   }

   private void updateCache(ApplicationMBean appMBean, VirtualJarFile ear, Set s) {
      if (this.isCacheable(ear)) {
         File earFile = ear.getRootFiles()[0];
         CacheEntry e = new CacheEntry();
         e.appName = appMBean.getName();
         e.lastModified = earFile.lastModified();
         e.wsSet = s;
         synchronized(this.cacheList) {
            this.cacheList.addFirst(e);
            if (this.cacheList.size() > MAX_LIST_SIZE) {
               this.cacheList.removeLast();
            }

         }
      }
   }

   Set findWebServices(ApplicationMBean appMBean, ApplicationFileManager appFileManager, VirtualJarFile ear, ModuleBean[] m) throws IOException {
      Set s = this.readCache(appMBean, ear);
      if (s != null) {
         return s;
      } else {
         Set s = Collections.EMPTY_SET;

         for(int i = 0; i < m.length; ++i) {
            if (m[i].getWeb() != null) {
               String uri = m[i].getWeb().getWebUri();
               if (this.isWebService(appFileManager, ear, uri)) {
                  if (((Set)s).size() == 0) {
                     s = new HashSet();
                  }

                  ((Set)s).add(uri);
               }
            }
         }

         this.updateCache(appMBean, ear, (Set)s);
         return (Set)s;
      }
   }

   private boolean isWebService(ApplicationFileManager appFileManager, VirtualJarFile ear, String uri) throws IOException {
      if (ear.isDirectory()) {
         VirtualJarFile war = null;

         boolean var30;
         try {
            war = appFileManager.getVirtualJarFile(uri);
            var30 = war.getEntry("WEB-INF/web-services.xml") != null;
         } finally {
            if (war != null) {
               try {
                  war.close();
               } catch (IOException var26) {
               }
            }

         }

         return var30;
      } else if (ear.getEntry(uri + "/" + "WEB-INF/web-services.xml") != null) {
         return ear.getEntry(uri + "/" + "WEB-INF/web-services.xml") != null;
      } else {
         ZipInputStream in = null;

         boolean var7;
         try {
            ZipEntry ze = ear.getEntry(uri);
            if (ze == null) {
               boolean var31 = false;
               return var31;
            }

            InputStream vis = ear.getInputStream(ze);
            if (vis == null) {
               var7 = false;
               return var7;
            }

            in = new ZipInputStream(vis);

            do {
               if ((ze = in.getNextEntry()) == null) {
                  var7 = false;
                  return var7;
               }
            } while(!ze.getName().equals("WEB-INF/web-services.xml"));

            var7 = true;
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var25) {
               }
            }

         }

         return var7;
      }
   }

   // $FF: synthetic method
   WebServiceUtils(Object x0) {
      this();
   }

   private static class CacheEntry implements Serializable {
      String appName;
      long lastModified;
      Set wsSet;

      private CacheEntry() {
      }

      // $FF: synthetic method
      CacheEntry(Object x0) {
         this();
      }
   }

   private static final class WebServiceUtilsSingleton {
      private static final WebServiceUtils SINGLETON = new WebServiceUtils();
   }
}
