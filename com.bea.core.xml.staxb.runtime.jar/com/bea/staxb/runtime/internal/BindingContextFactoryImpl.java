package com.bea.staxb.runtime.internal;

import com.bea.ns.staxb.bindingConfig.x90.BindingConfigDocument;
import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingFileUtils;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.CompositeBindingLoader;
import com.bea.staxb.buildtime.internal.tylar.DefaultTylarLoader;
import com.bea.staxb.buildtime.internal.tylar.Tylar;
import com.bea.staxb.buildtime.internal.tylar.TylarLoader;
import com.bea.staxb.runtime.BindingContext;
import com.bea.staxb.runtime.BindingContextFactory;
import com.bea.xml.XmlException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.jar.JarInputStream;

public final class BindingContextFactoryImpl extends BindingContextFactory {
   public BindingContext createBindingContext(ClassLoader cl) throws IOException, XmlException {
      if (cl == null) {
         throw new IllegalArgumentException("null classloader");
      } else {
         TylarLoader loader = DefaultTylarLoader.getInstance();
         if (loader == null) {
            throw new IllegalStateException("null loader");
         } else {
            Tylar tylar = loader.load(cl);
            if (tylar == null) {
               throw new XmlException("unable to load type library from classloader " + cl);
            } else {
               return this.createBindingContext(tylar);
            }
         }
      }
   }

   public BindingContext createBindingContext(Tylar tylar) throws IOException, XmlException {
      assert tylar != null;

      BindingLoader loader = tylar.getBindingLoader();
      TylarSchemaTypeLoaderProvider provider = new TylarSchemaTypeLoaderProvider(tylar);
      return new BindingContextImpl(loader, provider);
   }

   public BindingContext createBindingContext() {
      BindingFile empty = new BindingFile();
      SchemaTypeLoaderProvider provider = BuiltinSchemaTypeLoaderProvider.getInstance();
      return createBindingContext(empty, provider);
   }

   private static BindingContextImpl createBindingContext(BindingFile bf, SchemaTypeLoaderProvider provider) {
      BindingLoader bindingLoader = buildBindingLoader(bf);
      return new BindingContextImpl(bindingLoader, provider);
   }

   private static BindingLoader buildBindingLoader(BindingFile bf) {
      BindingLoader builtins = BuiltinBindingLoader.getBuiltinBindingLoader(false);
      return CompositeBindingLoader.forPath(new BindingLoader[]{builtins, bf});
   }

   /** @deprecated */
   @Deprecated
   public BindingContext createBindingContextFromConfig(File bindingConfig) throws IOException, XmlException {
      BindingConfigDocument doc = BindingConfigDocument.Factory.parse(bindingConfig);
      BindingFile bf = BindingFileUtils.forDoc(doc);
      SchemaTypeLoaderProvider provider = UnusedSchemaTypeLoaderProvider.getInstance();
      return createBindingContext(bf, provider);
   }

   /** @deprecated */
   @Deprecated
   public BindingContext createBindingContext(URI tylarUri) throws IOException, XmlException {
      return this.createBindingContext(new URI[]{tylarUri});
   }

   /** @deprecated */
   @Deprecated
   public BindingContext createBindingContext(URI[] tylarUris) throws IOException, XmlException {
      if (tylarUris == null) {
         throw new IllegalArgumentException("null uris");
      } else {
         TylarLoader loader = DefaultTylarLoader.getInstance();
         if (loader == null) {
            throw new IllegalStateException("null loader");
         } else {
            return this.createBindingContext(((DefaultTylarLoader)loader).load(tylarUris));
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public BindingContext createBindingContext(JarInputStream jar) throws IOException, XmlException {
      if (jar == null) {
         throw new IllegalArgumentException("null InputStream");
      } else {
         TylarLoader loader = DefaultTylarLoader.getInstance();
         if (loader == null) {
            throw new IllegalStateException("null TylarLoader");
         } else {
            return this.createBindingContext(((DefaultTylarLoader)loader).load(jar));
         }
      }
   }
}
