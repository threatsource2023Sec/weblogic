package com.bea.staxb.runtime;

import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.io.IOException;
import java.net.URI;
import java.util.jar.JarInputStream;

public abstract class BindingContextFactory {
   protected static final String DEFAULT_IMPL = "com.bea.staxb.runtime.internal.BindingContextFactoryImpl";

   public abstract BindingContext createBindingContext();

   public abstract BindingContext createBindingContext(ClassLoader var1) throws IOException, XmlException;

   public static BindingContextFactory newInstance() {
      try {
         Class default_impl = Class.forName("com.bea.staxb.runtime.internal.BindingContextFactoryImpl");
         BindingContextFactory factory = (BindingContextFactory)default_impl.newInstance();
         return factory;
      } catch (ClassNotFoundException var2) {
         throw new XmlRuntimeException(var2);
      } catch (InstantiationException var3) {
         throw new XmlRuntimeException(var3);
      } catch (IllegalAccessException var4) {
         throw new XmlRuntimeException(var4);
      }
   }

   /** @deprecated */
   @Deprecated
   public abstract BindingContext createBindingContext(URI var1) throws IOException, XmlException;

   /** @deprecated */
   @Deprecated
   public abstract BindingContext createBindingContext(URI[] var1) throws IOException, XmlException;

   /** @deprecated */
   @Deprecated
   public abstract BindingContext createBindingContext(JarInputStream var1) throws IOException, XmlException;
}
