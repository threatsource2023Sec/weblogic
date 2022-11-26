package weblogic.ejb.container.swap;

import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import javax.ejb.EJBContext;
import javax.ejb.NoSuchEJBException;
import weblogic.common.internal.ReplacerObjectInputStream;
import weblogic.common.internal.ReplacerObjectOutputStream;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.interfaces.WLSessionEJBContext;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.protocol.ServerChannelManager;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.io.ClassLoaderResolver;
import weblogic.utils.io.Resolver;

final class PassivationUtils {
   private Resolver resolver;

   PassivationUtils(ClassLoader cl) {
      this.resolver = new ClassLoaderResolver(cl);
   }

   Object read(StatefulSessionManager beanManager, InputStream is, Object key, Class iface) throws InternalException {
      try {
         return this.readBeanState(beanManager, is, key);
      } catch (IOException var7) {
         String emsg = EJBLogger.logBeanActivationExceptionLoggable(iface + "[key=" + key + "]", StackTraceUtilsClient.throwable2StackTrace(var7)).getMessageText();
         EJBRuntimeUtils.throwInternalException(emsg, new NoSuchEJBException(emsg));
         throw new AssertionError("Should not reach.");
      }
   }

   Object readBeanState(StatefulSessionManager beanManager, InputStream is, Object key) throws IOException {
      ObjectInputStream ois = null;

      Object var8;
      try {
         EJBReplacer replacer = new EJBReplacer();
         EJBContext ctx = beanManager.allocateContext((Object)null, key);
         replacer.setContext(ctx);
         ois = new ReplacerObjectInputStream(is, replacer, this.resolver);
         Object o = ois.readObject();
         o = beanManager.assembleEJB3Proxy(o);
         ((WLEJBContext)ctx).setBean(o);
         if (beanManager.getBeanInfo().isEJB30()) {
            ((WLSessionEJBContext)ctx).setPrimaryKey(key);
         }

         var8 = o;
      } catch (IOException var18) {
         throw var18;
      } catch (ClassNotFoundException var19) {
         throw new IOException(var19);
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (IOException var17) {
            }
         }

      }

      return var8;
   }

   void write(StatefulSessionManager beanManager, OutputStream os, Object bean) throws InternalException {
      ReplacerObjectOutputStream oos = null;

      try {
         if (beanManager.getBeanInfo().isEJB30() && bean instanceof __ProxyControl) {
            bean = ((__ProxyControl)bean).__getTarget();
         }

         oos = new ReplacerObjectOutputStream(os, new EJBReplacer());
         oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
         oos.writeObject(bean);
      } catch (IOException var14) {
         EJBRuntimeUtils.throwInternalException("Error during write.", var14);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (IOException var13) {
            }
         }

      }

   }

   public void updateClassLoader(ClassLoader cl) {
      this.resolver = new ClassLoaderResolver(cl);
   }
}
