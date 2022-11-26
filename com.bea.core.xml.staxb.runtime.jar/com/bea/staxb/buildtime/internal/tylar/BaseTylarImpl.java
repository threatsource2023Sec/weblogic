package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.CompositeBindingLoader;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamServiceFactory;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.IOException;
import java.net.URL;

/** @deprecated */
@Deprecated
public abstract class BaseTylarImpl implements Tylar {
   public String getDescription() {
      return "[" + this.getClass().getName() + "]";
   }

   public URL[] getLocations() {
      return null;
   }

   public URL getLocation() {
      return null;
   }

   public BindingLoader getBindingLoader() {
      try {
         BindingFile[] bfs = this.getBindingFiles();
         BindingLoader[] loaders = new BindingLoader[bfs.length + 1];
         System.arraycopy(bfs, 0, loaders, 0, bfs.length);
         loaders[loaders.length - 1] = this.getBuiltinBindingLoader();
         return CompositeBindingLoader.forPath(loaders);
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   protected BindingLoader getBuiltinBindingLoader() {
      return BuiltinBindingLoader.getBuiltinBindingLoader(false);
   }

   public JamClassLoader getJamClassLoader() {
      return JamServiceFactory.getInstance().createSystemJamClassLoader();
   }

   public void setExcludeLocations(URL[] excludes) {
   }

   protected SchemaTypeSystem getDefaultSchemaTypeSystem() throws IOException, XmlException {
      SchemaDocument[] xsds = this.getSchemas();
      XmlObject[] xxds = new XmlObject[xsds.length];

      for(int i = 0; i < xsds.length; ++i) {
         xxds[i] = xsds[i].getSchema();
      }

      try {
         XmlOptions opts = new XmlOptions();
         opts.setCompileDownloadUrls();
         opts.setLoadUseDefaultResolver();
         opts.setCompileNoAnnotations();
         return XmlBeans.compileXsd(xxds, XmlBeans.getBuiltinTypeSystem(), opts);
      } catch (XmlException var4) {
         throw (IOException)(new IOException()).initCause(var4);
      }
   }
}
