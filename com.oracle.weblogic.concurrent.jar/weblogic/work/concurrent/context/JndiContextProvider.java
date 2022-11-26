package weblogic.work.concurrent.context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class JndiContextProvider implements ContextProvider {
   private static final long serialVersionUID = 8781340024252904466L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   private static final JndiContextProvider instance = new JndiContextProvider();

   protected JndiContextProvider() {
   }

   public static JndiContextProvider getInstance() {
      return instance;
   }

   public ContextHandle save(Map executionProperties) {
      return new JndiContextHandle(javaURLContextFactory.peekContext());
   }

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof JndiContextHandle)) {
         return null;
      } else {
         JndiContextHandle handle = (JndiContextHandle)contextHandle;
         javaURLContextFactory.pushContext(handle.getContext());
         return new JndiContextHandle((Context)null);
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (contextHandle instanceof JndiContextHandle) {
         javaURLContextFactory.popContext();
      }
   }

   public String getContextType() {
      return "jndi";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public static class JndiContextHandle implements ContextHandle {
      private static final long serialVersionUID = -7977297799736380305L;
      private static final int CURRENT_VERSION = 1;
      private transient Context context;
      private final int version;

      public JndiContextHandle(Context context) {
         this.context = context;
         this.version = 1;
      }

      public Context getContext() {
         return this.context;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.defaultWriteObject();
         boolean writeJndi = this.context != null;
         out.writeBoolean(writeJndi);
         if (writeJndi) {
            String key = ContextCache.putContext(this.context);
            out.writeUTF(key);
         }

      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         in.defaultReadObject();
         if (this.version != 1 && JndiContextProvider.debugLogger.isDebugEnabled()) {
            JndiContextProvider.debugLogger.debug("AbstractJndiHandle version mismatch: current=1, object=" + this.version);
         }

         boolean hasContext = in.readBoolean();
         if (hasContext) {
            String key = in.readUTF();
            Object obj = ContextCache.getContext(key);
            if (!(obj instanceof Context)) {
               throw new IOException("app not started.");
            }

            this.context = (Context)obj;
         }

      }
   }
}
