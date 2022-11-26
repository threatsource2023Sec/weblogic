package weblogic.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.Resolver;

public final class ReplacerObjectInputStream extends FilteringObjectInputStream {
   private final Replacer replacer;
   private final Resolver resolver;

   public ReplacerObjectInputStream(InputStream is, Replacer rep, Resolver res) throws IOException {
      super(is);
      this.enableResolveObject(rep != null);
      this.replacer = rep;
      this.resolver = res;
   }

   protected Object resolveObject(Object obj) throws IOException {
      Object newobj = this.replacer.resolveObject(obj);
      return newobj;
   }

   protected Class resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      this.checkLegacyBlacklistIfNeeded(osc.getName());
      if (this.resolver != null) {
         Class c = this.resolver.resolveClass(osc);
         if (c != null) {
            return c;
         }
      }

      return super.resolveClass(osc);
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      return RJVMEnvironment.getEnvironment().resolveProxyClass(interfaces);
   }
}
