package org.python.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Untraversable
public class SyspathArchive extends PyString {
   private ZipFile zipFile;

   public SyspathArchive(String archiveName) throws IOException {
      super(Py.fileSystemEncode(archiveName).getString());
      archiveName = getArchiveName(archiveName);
      if (archiveName == null) {
         throw new IOException("path '" + archiveName + "' not an archive");
      } else {
         this.zipFile = new ZipFile(new File(archiveName));
         if (PySystemState.isPackageCacheEnabled()) {
            PySystemState.packageManager.addJar(archiveName, true);
         }

      }
   }

   SyspathArchive(ZipFile zipFile, String archiveName) {
      super(Py.fileSystemEncode(archiveName).getString());
      this.zipFile = zipFile;
   }

   static String getArchiveName(String dir) {
      String lowerName = dir.toLowerCase();
      int idx = lowerName.indexOf(".zip");
      if (idx < 0) {
         idx = lowerName.indexOf(".jar");
      }

      if (idx < 0) {
         return null;
      } else if (idx == dir.length() - 4) {
         return dir;
      } else {
         char ch = dir.charAt(idx + 4);
         return ch != File.separatorChar && ch != '/' ? null : dir.substring(0, idx + 4);
      }
   }

   public SyspathArchive makeSubfolder(String folder) {
      return new SyspathArchive(this.zipFile, super.toString() + "/" + folder);
   }

   private String makeEntry(String entry) {
      String archive = super.toString();
      String folder = getArchiveName(super.toString());
      return archive.length() == folder.length() ? entry : archive.substring(folder.length() + 1) + "/" + entry;
   }

   ZipEntry getEntry(String entryName) {
      return this.zipFile.getEntry(this.makeEntry(entryName));
   }

   public String asUriCompatibleString() {
      String result = this.__str__().toString();
      return File.separatorChar == '\\' ? result.replace(File.separatorChar, '/') : result;
   }

   InputStream getInputStream(ZipEntry entry) throws IOException {
      InputStream istream = this.zipFile.getInputStream(entry);
      int len = (int)entry.getSize();
      byte[] buffer = new byte[len];

      int l;
      for(int off = 0; len > 0; len -= l) {
         l = istream.read(buffer, off, buffer.length - off);
         if (l < 0) {
            return null;
         }

         off += l;
      }

      istream.close();
      return new ByteArrayInputStream(buffer);
   }
}
