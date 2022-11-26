package weblogic.coherence.service.internal.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;
import weblogic.coherence.service.internal.DebugLogger;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.Resolver;

public class WLSObjectInputStream extends FilteringObjectInputStream {
   private final Replacer replacer;
   private final Resolver resolver;
   private final ClassLoader ldr;

   public WLSObjectInputStream(InputStream is, Replacer rep, Resolver res, ClassLoader ldr) throws IOException {
      super(is);
      this.enableResolveObject(rep != null);
      this.replacer = rep;
      this.resolver = res;
      this.ldr = ldr;
   }

   protected Object resolveObject(Object obj) throws IOException {
      return this.replacer.resolveObject(obj);
   }

   protected Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      try {
         this.checkLegacyBlacklistIfNeeded(osc.getName());
      } catch (InvalidClassException var3) {
         if (DebugLogger.isDebugEnabled()) {
            DebugLogger.debug("Unauthorized deserialization attempt");
         }

         throw var3;
      }

      Class c = this.resolver.resolveClass(osc);
      return c != null ? c : super.resolveClass(osc);
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      if (this.ldr == null) {
         return super.resolveProxyClass(interfaces);
      } else {
         Class[] classes = new Class[interfaces.length];
         int i = 0;
         String[] var4 = interfaces;
         int var5 = interfaces.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String s = var4[var6];
            classes[i++] = this.loadClass(s, this.ldr);
         }

         return Proxy.getProxyClass(this.ldr, classes);
      }
   }

   private Class loadClass(String className, ClassLoader ldr) throws ClassNotFoundException {
      return Class.forName(className, false, ldr);
   }
}
