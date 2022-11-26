package weblogic.application.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.Iterators;
import weblogic.utils.jars.VirtualJarFile;

public final class ClassLoaderVJF implements VirtualJarFile {
   private final String uri;
   private final String prefix;
   private final GenericClassLoader cl;
   private final File[] rootFiles;

   public ClassLoaderVJF(String uri, GenericClassLoader cl, File[] rootFiles) {
      this.uri = uri;
      this.prefix = uri + "#";
      this.cl = cl;
      this.rootFiles = rootFiles;
   }

   public String getName() {
      return this.uri;
   }

   public void close() throws IOException {
   }

   public URL getResource(String name) {
      return this.cl.getResource(this.prefix + name);
   }

   public ZipEntry getEntry(String name) {
      URL u = this.getResource(name);
      return u == null ? null : new URLZipEntry(u);
   }

   public Iterator getEntries(String uri) throws IOException {
      List l = new ArrayList();
      Enumeration e = this.cl.getResources(this.prefix + uri);

      while(e.hasMoreElements()) {
         l.add(new URLZipEntry((URL)e.nextElement()));
      }

      return l.iterator();
   }

   public InputStream getInputStream(ZipEntry ze) throws IOException {
      URL u = ((URLZipEntry)ze).getURL();
      return u.openStream();
   }

   public Manifest getManifest() throws IOException {
      throw new AssertionError("getManifest not supported");
   }

   public File[] getRootFiles() {
      return this.rootFiles;
   }

   public boolean isDirectory() {
      throw new AssertionError("isDirectory not supported");
   }

   public JarFile getJarFile() {
      throw new AssertionError("getJarFile not supported");
   }

   public File getDirectory() {
      throw new AssertionError("getDirectory not supported");
   }

   public Iterator entries() {
      try {
         return this.getEntries("/");
      } catch (IOException var2) {
         return Iterators.EMPTY_ITERATOR;
      }
   }

   private static class URLZipEntry extends ZipEntry {
      private final URL url;

      URLZipEntry(URL url) {
         super(url.toString());
         this.url = url;
      }

      URL getURL() {
         return this.url;
      }
   }
}
