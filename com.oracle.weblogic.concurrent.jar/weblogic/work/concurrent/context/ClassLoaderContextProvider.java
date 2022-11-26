package weblogic.work.concurrent.context;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class ClassLoaderContextProvider implements ContextProvider {
   private static final long serialVersionUID = 1630351329864758942L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   private static final ClassLoaderContextProvider instance = new ClassLoaderContextProvider();

   protected ClassLoaderContextProvider() {
   }

   public static ClassLoaderContextProvider getInstance() {
      return instance;
   }

   public ContextHandle save(Map executionProperties) {
      ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
      return new ClassLoaderHandle(contextClassLoader);
   }

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof ClassLoaderHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip setupClassloader: " + contextHandle);
         }

         return null;
      } else {
         ClassLoaderHandle handle = (ClassLoaderHandle)contextHandle;
         ClassLoader savedClassLoader = null;
         Thread thread = Thread.currentThread();
         savedClassLoader = thread.getContextClassLoader();
         thread.setContextClassLoader(handle.getContextClassLoader());
         return new ClassLoaderHandle(savedClassLoader);
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (!(contextHandle instanceof ClassLoaderHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip resetClassloader: " + contextHandle);
         }

      } else {
         ClassLoaderHandle handle = (ClassLoaderHandle)contextHandle;
         Thread.currentThread().setContextClassLoader(handle.getContextClassLoader());
      }
   }

   public String getContextType() {
      return "classloader";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public static class ClassLoaderHandle implements ContextHandle {
      private static final long serialVersionUID = -3839081372823718651L;
      private static final int CURRENT_VERSION = 1;
      private transient ClassLoader contextClassLoader;
      private final int version;

      public ClassLoaderHandle(ClassLoader contextClassLoader) {
         this.contextClassLoader = contextClassLoader;
         this.version = 1;
      }

      public ClassLoader getContextClassLoader() {
         return this.contextClassLoader;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.defaultWriteObject();
         if (this.contextClassLoader == null) {
            out.writeBoolean(false);
         } else {
            out.writeBoolean(true);
            if (!(this.contextClassLoader instanceof Serializable) && !(this.contextClassLoader instanceof Externalizable)) {
               out.writeBoolean(false);
               String key = ContextCache.putContext(this.contextClassLoader);
               out.writeUTF(key);
            } else {
               out.writeBoolean(true);
               out.writeObject(this.contextClassLoader);
            }

         }
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         if (this.version != 1 && ClassLoaderContextProvider.debugLogger.isDebugEnabled()) {
            ClassLoaderContextProvider.debugLogger.debug("ClassLoaderHandle version mismatch: current=1, object=" + this.version);
         }

         boolean hasClassLoader = in.readBoolean();
         if (hasClassLoader) {
            boolean isSerializable = in.readBoolean();
            if (isSerializable) {
               Object obj = in.readObject();
               if (!(obj instanceof ClassLoader)) {
                  String msg = ConcurrencyLogger.logDeserializeErrorObjectLoggable(ClassLoader.class.getName(), obj == null ? null : obj.getClass().getName()).getMessage();
                  if (ClassLoaderContextProvider.debugLogger.isDebugEnabled()) {
                     ClassLoaderContextProvider.debugLogger.debug(msg);
                  }

                  throw new IOException(msg);
               }

               this.contextClassLoader = (ClassLoader)obj;
            } else {
               String key = in.readUTF();
               Object context = ContextCache.getContext(key);
               if (!(context instanceof ClassLoader)) {
                  String msg = ConcurrencyLogger.logDeserializeErrorCacheLoggable(ClassLoader.class.getName(), key).getMessage();
                  if (ClassLoaderContextProvider.debugLogger.isDebugEnabled()) {
                     ClassLoaderContextProvider.debugLogger.debug(msg);
                  }

                  throw new IOException(msg);
               }

               this.contextClassLoader = (ClassLoader)context;
            }

         }
      }
   }
}
