package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.utils.classloaders.FileSource;
import weblogic.utils.classloaders.NullSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.SourceWithMetadata;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.classloaders.ZipSource;

public class WarSource implements Source {
   final Source delegate;
   private final long length;
   private final boolean isDirectory;
   private final long lastChecked = System.currentTimeMillis();
   private final long lastModified;
   private final String lastModifiedString;
   private final boolean fromArchive;
   private final boolean fromLibrary;
   private String contentType;
   private String fileName = null;

   public WarSource(Source delegate) {
      boolean archive = false;
      boolean fromLib = false;
      if (delegate instanceof SourceWithMetadata) {
         Object metadata = ((SourceWithMetadata)delegate).getMetadata();
         if (metadata instanceof War.LibrarySourceMetadata) {
            SourceWithMetadata libSource = (SourceWithMetadata)delegate;
            archive = ((War.LibrarySourceMetadata)metadata).isFromArchive();
            delegate = libSource.getSource();
            fromLib = true;
         }
      }

      this.delegate = delegate;
      this.fromArchive = archive;
      this.fromLibrary = fromLib;
      this.length = delegate.length();
      this.lastModified = delegate.lastModified();
      this.lastModifiedString = ResponseHeaders.getDateString(this.lastModified);
      this.isDirectory = isDirectory(delegate);
   }

   public Source getDelegateSource() {
      return this.delegate;
   }

   public long getLastChecked() {
      return this.lastChecked;
   }

   public byte[] getBytes() throws IOException {
      return this.delegate.getBytes();
   }

   public long length() {
      return this.length;
   }

   public long lastModified() {
      return this.lastModified;
   }

   public InputStream getInputStream() throws IOException {
      return this.delegate.getInputStream();
   }

   public URL getURL() {
      return this.delegate.getURL();
   }

   public URL getCodeSourceURL() {
      return this.delegate.getCodeSourceURL();
   }

   public String getLastModifiedAsString() {
      return this.lastModifiedString;
   }

   public boolean isDirectory() {
      return this.isDirectory;
   }

   public boolean isFile() {
      if (this.delegate instanceof FileSource) {
         return true;
      } else if (this.delegate instanceof ZipSource) {
         return false;
      } else if (this.delegate instanceof WarSource) {
         return ((WarSource)this.delegate).isFile();
      } else if (this.delegate instanceof URLSource) {
         return true;
      } else if (this.delegate instanceof MDSSource) {
         return false;
      } else {
         throw new UnsupportedOperationException("Can't isFile on: '" + this.delegate + "'");
      }
   }

   public String getFileName() {
      if (this.fileName != null) {
         return this.fileName;
      } else {
         if (this.delegate instanceof FileSource) {
            this.fileName = ((FileSource)this.delegate).getFile().getAbsolutePath();
         } else if (!(this.delegate instanceof ZipSource) && !(this.delegate instanceof URLSource)) {
            if (this.delegate instanceof MDSSource) {
               this.fileName = ((MDSSource)this.delegate).getProviderURI();
            } else {
               if (!(this.delegate instanceof WarSource)) {
                  throw new UnsupportedOperationException("Can't getFileName on: '" + this.delegate + "'");
               }

               this.fileName = ((WarSource)this.delegate).getFileName();
            }
         } else {
            this.fileName = this.delegate.getURL().getFile();
         }

         return this.fileName;
      }
   }

   public boolean isFromArchive() {
      return this.fromArchive;
   }

   public boolean isFromLibrary() {
      return this.fromLibrary;
   }

   public String getContentType(WebAppServletContext context) {
      if (this.contentType != null) {
         return this.contentType;
      } else {
         this.contentType = context.getMimeType(this.getName());
         return this.contentType;
      }
   }

   public String getName() {
      if (this.delegate instanceof FileSource) {
         return ((FileSource)this.delegate).getFile().getName();
      } else {
         String path;
         if (this.delegate instanceof ZipSource) {
            path = ((ZipSource)this.delegate).getEntry().getName();
            return this.getNameFromPath(path);
         } else if (this.delegate instanceof MDSSource) {
            path = ((MDSSource)this.delegate).getProviderURI();
            return this.getNameFromPath(path);
         } else if (this.delegate instanceof WarSource) {
            return ((WarSource)this.delegate).getName();
         } else if (this.delegate instanceof URLSource) {
            return ((URLSource)this.delegate).getURL().getFile();
         } else {
            throw new UnsupportedOperationException("Can't getName on: '" + this.delegate + "'");
         }
      }
   }

   private String getNameFromPath(String path) {
      if (path.charAt(path.length() - 1) == '/') {
         path = path.substring(0, path.length() - 1);
      }

      int index = path.lastIndexOf("/");
      return index != -1 ? path.substring(index + 1) : path;
   }

   public WarSource[] listSources() {
      if (this.delegate instanceof FileSource) {
         return getFileSourceListing((FileSource)this.delegate);
      } else if (this.delegate instanceof ZipSource) {
         return getZipSourceListing((ZipSource)this.delegate);
      } else if (this.delegate instanceof WarSource) {
         return ((WarSource)this.delegate).listSources();
      } else if (this.delegate instanceof MDSSource) {
         return null;
      } else if (this.delegate instanceof URLSource) {
         return null;
      } else {
         throw new UnsupportedOperationException("Can't getDirectoryListing on: " + this.delegate + "'");
      }
   }

   private static WarSource[] getFileSourceListing(FileSource s) {
      if (!s.getFile().isDirectory()) {
         return null;
      } else {
         File[] files = s.getFile().listFiles();
         WarSource[] sources = new WarSource[files.length];

         for(int i = 0; i < files.length; ++i) {
            sources[i] = new WarSource(new FileSource(s.getCodeBase(), files[i]));
         }

         return sources;
      }
   }

   private static WarSource[] getZipSourceListing(ZipSource s) {
      String rootname = s.getEntry().getName();
      if ("/".equals(rootname)) {
         rootname = "";
      }

      ArrayList zips = new ArrayList();
      Enumeration e = s.getFile().entries();

      while(true) {
         ZipEntry ze;
         String name;
         int indexOf;
         do {
            do {
               do {
                  if (!e.hasMoreElements()) {
                     return (WarSource[])((WarSource[])zips.toArray(new WarSource[zips.size()]));
                  }

                  ze = (ZipEntry)e.nextElement();
                  name = ze.getName();
               } while(name.equals(rootname));
            } while(!name.startsWith(rootname));

            indexOf = name.indexOf("/", rootname.length());
         } while(indexOf > 0 && indexOf < name.length() - 1);

         zips.add(new WarSource(new ZipSource(s.getFile(), ze)));
      }
   }

   private static boolean isDirectory(Source s) {
      if (s == null) {
         return false;
      } else if (s instanceof FileSource) {
         return ((FileSource)s).getFile().isDirectory();
      } else if (s instanceof ZipSource) {
         ZipSource zs = (ZipSource)s;
         if (zs.getEntry().isDirectory()) {
            return true;
         } else {
            ZipFile zf = zs.getFile();
            ZipEntry directory = zf.getEntry(zs.getEntry().toString() + '/');
            return directory != null;
         }
      } else if (s instanceof WarSource) {
         return ((WarSource)s).isDirectory();
      } else if (!(s instanceof NullSource) && !(s instanceof URLSource) && !(s instanceof MDSSource)) {
         throw new UnsupportedOperationException("Can't isDirectory on: '" + s + "'");
      } else {
         return false;
      }
   }
}
