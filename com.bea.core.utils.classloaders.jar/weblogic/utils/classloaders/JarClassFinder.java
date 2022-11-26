package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;
import weblogic.utils.enumerations.SequencingEnumerator;

public final class JarClassFinder extends MultiClassFinder {
   private final ClassFinder delegate;
   private final ClassFinder manifestFinder;
   private boolean isUnmodifiable;

   public JarClassFinder(File f) throws IOException {
      this(f, new HashSet());
   }

   public JarClassFinder(File f, Set exclude) throws IOException {
      this(f, exclude, false);
   }

   public JarClassFinder(File f, Set exclude, boolean enforceCase) throws IOException {
      this.isUnmodifiable = false;
      if (exclude == null) {
         throw new IllegalArgumentException("Null exclude set");
      } else {
         Manifest manifest;
         ClassFinder mf;
         if (f.isDirectory()) {
            this.delegate = new IndexedDirectoryClassFinder(f, enforceCase);
            manifest = ClassFinderUtils.getManifest(f);
            mf = ClassFinderUtils.getManifestFinder(manifest, f.getPath(), exclude);
         } else {
            try {
               this.delegate = new ZipClassFinder((ZipFile)(f.getName().endsWith(".jar") ? new JarFile(f) : new ZipFile(f)));
               manifest = ClassFinderUtils.getManifest(((ZipClassFinder)this.delegate).getZipFile());
               mf = ClassFinderUtils.getManifestFinder(manifest, ((ZipClassFinder)this.delegate).getZipFile().getName(), exclude);
               if (ClassFinderUtils.isShareable(manifest)) {
                  ((ZipClassFinder)this.delegate).markShareable();
               }
            } catch (IOException var8) {
               ClassFinderUtils.checkArchive(f.getCanonicalPath(), var8);
               IOException ex = new IOException(var8.getMessage() + " for file : " + f.getCanonicalPath(), var8);
               throw ex;
            }
         }

         if (mf == null) {
            this.manifestFinder = NullClassFinder.NULL_FINDER;
         } else {
            this.manifestFinder = mf;
         }

         this.addFinder(this.delegate);
         this.addFinder(this.manifestFinder);
         this.isUnmodifiable = true;
      }
   }

   protected boolean isUnmodifiable() {
      return this.isUnmodifiable;
   }

   public ClassFinder getDelegate() {
      return this.delegate;
   }

   public ClassFinder getManifestFinder() {
      return this.manifestFinder;
   }

   public Enumeration entries() {
      return new SequencingEnumerator(new Enumeration[]{this.delegate.entries(), this.manifestFinder.entries()});
   }

   public void close() {
      this.delegate.close();
      this.manifestFinder.close();
   }

   public String toString() {
      return super.toString() + " - delegate: '" + this.delegate + "'";
   }
}
