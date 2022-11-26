package weblogic.j2ee;

import com.bea.xbean.common.SystemCache;
import com.bea.xml.SchemaTypeLoader;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;

public class ApplicationCache extends SystemCache {
   private static ApplicationCache singleton = new ApplicationCache();
   private WeakHashMap cl2SchemaTypeLoader;

   private ApplicationCache() {
      SystemCache.set(this);
      this.cl2SchemaTypeLoader = new WeakHashMap();
   }

   public static ApplicationCache getApplicationCache() {
      return singleton;
   }

   public SchemaTypeLoader getFromTypeLoaderCache(ClassLoader cl) {
      ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
      WeakReference wr;
      if (appCtx != null) {
         wr = (WeakReference)appCtx.getSchemaTypeLoader(cl);
         return wr != null && wr.get() != null ? (SchemaTypeLoader)wr.get() : null;
      } else {
         wr = (WeakReference)this.cl2SchemaTypeLoader.get(cl);
         return wr != null && wr.get() != null ? (SchemaTypeLoader)wr.get() : null;
      }
   }

   public void addToTypeLoaderCache(SchemaTypeLoader stl, ClassLoader cl) {
      ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
      if (appCtx != null) {
         appCtx.setSchemaTypeLoader(cl, new WeakReference(stl));
      } else {
         this.cl2SchemaTypeLoader.put(cl, new WeakReference(stl));
      }

   }
}
