package org.opensaml.core.xml.persist;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class FilesystemLoadSaveManager implements XMLObjectLoadSaveManager {
   private Logger log;
   private File baseDirectory;
   private ParserPool parserPool;
   private FileFilter fileFilter;

   public FilesystemLoadSaveManager(@Nonnull String baseDir) {
      this((File)(new File((String)Constraint.isNotNull(StringSupport.trimOrNull(baseDir), "Base directory string instance was null or empty"))), (ParserPool)null);
   }

   public FilesystemLoadSaveManager(@Nonnull File baseDir) {
      this((File)baseDir, (ParserPool)null);
   }

   public FilesystemLoadSaveManager(@Nonnull String baseDir, @Nullable ParserPool pp) {
      this(new File((String)Constraint.isNotNull(StringSupport.trimOrNull(baseDir), "Base directory string instance was null or empty")), pp);
   }

   public FilesystemLoadSaveManager(@Nonnull File baseDir, @Nullable ParserPool pp) {
      this.log = LoggerFactory.getLogger(FilesystemLoadSaveManager.class);
      this.baseDirectory = (File)Constraint.isNotNull(baseDir, "Base directory File instance was null");
      Constraint.isTrue(this.baseDirectory.isAbsolute(), "Base directory specified was not an absolute path");
      if (this.baseDirectory.exists()) {
         Constraint.isTrue(this.baseDirectory.isDirectory(), "Existing base directory path was not a directory");
      } else {
         Constraint.isTrue(this.baseDirectory.mkdirs(), "Base directory did not exist and could not be created");
      }

      this.parserPool = pp;
      if (this.parserPool == null) {
         this.parserPool = (ParserPool)Constraint.isNotNull(XMLObjectProviderRegistrySupport.getParserPool(), "Specified ParserPool was null and global ParserPool was not available");
      }

      this.fileFilter = new DefaultFileFilter();
   }

   public Set listKeys() throws IOException {
      File[] files = this.baseDirectory.listFiles(this.fileFilter);
      HashSet keys = new HashSet();
      File[] var3 = files;
      int var4 = files.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         File file = var3[var5];
         keys.add(file.getName());
      }

      return Collections.unmodifiableSet(keys);
   }

   public Iterable listAll() throws IOException {
      return new FileIterable(this.listKeys());
   }

   public boolean exists(String key) throws IOException {
      return this.buildFile(key).exists();
   }

   public XMLObject load(String key) throws IOException {
      File file = this.buildFile(key);
      if (!file.exists()) {
         this.log.debug("Target file with key '{}' does not exist, path: {}", key, file.getAbsolutePath());
         return null;
      } else {
         FileInputStream fis = new FileInputStream(file);
         Throwable var4 = null;

         XMLObject var9;
         try {
            byte[] source = ByteStreams.toByteArray(fis);

            try {
               ByteArrayInputStream bais = new ByteArrayInputStream(source);
               Throwable var7 = null;

               try {
                  XMLObject xmlObject = XMLObjectSupport.unmarshallFromInputStream(this.parserPool, bais);
                  xmlObject.getObjectMetadata().put(new XMLObjectSource(source));
                  var9 = xmlObject;
               } catch (Throwable var34) {
                  var7 = var34;
                  throw var34;
               } finally {
                  if (bais != null) {
                     if (var7 != null) {
                        try {
                           bais.close();
                        } catch (Throwable var33) {
                           var7.addSuppressed(var33);
                        }
                     } else {
                        bais.close();
                     }
                  }

               }
            } catch (UnmarshallingException | XMLParserException var36) {
               throw new IOException(String.format("Error loading file from path: %s", file.getAbsolutePath()), var36);
            }
         } catch (Throwable var37) {
            var4 = var37;
            throw var37;
         } finally {
            if (fis != null) {
               if (var4 != null) {
                  try {
                     fis.close();
                  } catch (Throwable var32) {
                     var4.addSuppressed(var32);
                  }
               } else {
                  fis.close();
               }
            }

         }

         return var9;
      }
   }

   public void save(String key, XMLObject xmlObject) throws IOException {
      this.save(key, xmlObject, false);
   }

   public void save(String key, XMLObject xmlObject, boolean overwrite) throws IOException {
      if (!overwrite && this.exists(key)) {
         throw new IOException(String.format("Target file already exists for key '%s' and overwrite not indicated", key));
      } else {
         File file = this.buildFile(key);
         FileOutputStream fos = new FileOutputStream(file);
         Throwable var6 = null;

         try {
            List sources = xmlObject.getObjectMetadata().get(XMLObjectSource.class);
            if (sources.size() == 1) {
               this.log.debug("XMLObject contained 1 XMLObjectSource instance, persisting existing byte[]");
               XMLObjectSource source = (XMLObjectSource)sources.get(0);
               fos.write(source.getObjectSource());
            } else {
               this.log.debug("XMLObject contained {} XMLObjectSource instances, persisting marshalled object", sources.size());

               try {
                  XMLObjectSupport.marshallToOutputStream(xmlObject, fos);
               } catch (MarshallingException var17) {
                  throw new IOException(String.format("Error saving target file: %s", file.getAbsolutePath()), var17);
               }
            }

            fos.flush();
         } catch (Throwable var18) {
            var6 = var18;
            throw var18;
         } finally {
            if (fos != null) {
               if (var6 != null) {
                  try {
                     fos.close();
                  } catch (Throwable var16) {
                     var6.addSuppressed(var16);
                  }
               } else {
                  fos.close();
               }
            }

         }

      }
   }

   public boolean remove(String key) throws IOException {
      File file = this.buildFile(key);
      if (file.exists()) {
         boolean success = file.delete();
         if (success) {
            return true;
         } else {
            throw new IOException(String.format("Error removing target file: %s", file.getAbsolutePath()));
         }
      } else {
         return false;
      }
   }

   public boolean updateKey(String currentKey, String newKey) throws IOException {
      File currentFile = this.buildFile(currentKey);
      if (!currentFile.exists()) {
         return false;
      } else {
         File newFile = this.buildFile(newKey);
         if (newFile.exists()) {
            throw new IOException(String.format("Specified new key already exists: %s", newKey));
         } else {
            Files.move(currentFile, newFile);
            return true;
         }
      }
   }

   protected File buildFile(String key) throws IOException {
      File path = new File(this.baseDirectory, (String)Constraint.isNotNull(StringSupport.trimOrNull(key), "Input key was null or empty"));
      if (path.exists() && !path.isFile()) {
         throw new IOException(String.format("Path exists based on specified key, but is not a file: %s", path.getAbsolutePath()));
      } else {
         return path;
      }
   }

   private class FileIterator implements Iterator {
      private Iterator keysIter;
      private Pair current;

      public FileIterator(@Nonnull Collection filenames) {
         Set keys = new HashSet();
         keys.addAll(Collections2.filter(filenames, Predicates.notNull()));
         this.keysIter = keys.iterator();
      }

      public boolean hasNext() {
         if (this.current != null) {
            return true;
         } else {
            this.current = this.getNext();
            return this.current != null;
         }
      }

      public Pair next() {
         Pair temp;
         if (this.current != null) {
            temp = this.current;
            this.current = null;
            return temp;
         } else {
            temp = this.getNext();
            if (temp != null) {
               return temp;
            } else {
               throw new NoSuchElementException();
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private Pair getNext() {
         while(this.keysIter.hasNext()) {
            String key = (String)this.keysIter.next();

            try {
               XMLObject xmlObject = FilesystemLoadSaveManager.this.load(key);
               if (xmlObject != null) {
                  return new Pair(key, xmlObject);
               }

               FilesystemLoadSaveManager.this.log.warn("Target file with key '{}' was removed since iterator creation, skipping", key);
            } catch (IOException var3) {
               FilesystemLoadSaveManager.this.log.warn("Error loading target file with key '{}'", key, var3);
            }
         }

         return null;
      }
   }

   private class FileIterable implements Iterable {
      private Set keys = new HashSet();

      public FileIterable(@Nonnull Collection filenames) {
         this.keys.addAll(Collections2.filter(filenames, Predicates.notNull()));
      }

      public Iterator iterator() {
         return FilesystemLoadSaveManager.this.new FileIterator(this.keys);
      }
   }

   public static class DefaultFileFilter implements FileFilter {
      public boolean accept(File pathname) {
         return pathname == null ? false : pathname.isFile();
      }
   }
}
