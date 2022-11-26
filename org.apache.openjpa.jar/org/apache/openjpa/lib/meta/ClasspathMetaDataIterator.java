package org.apache.openjpa.lib.meta;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Properties;
import java.util.zip.ZipFile;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import serp.util.Strings;

public class ClasspathMetaDataIterator extends MetaDataIteratorChain {
   public ClasspathMetaDataIterator() throws IOException {
      this((String[])null, (MetaDataFilter)null);
   }

   public ClasspathMetaDataIterator(String[] dirs, MetaDataFilter filter) throws IOException {
      Properties props = (Properties)AccessController.doPrivileged(J2DoPrivHelper.getPropertiesAction());
      String path = props.getProperty("java.class.path");
      String[] tokens = Strings.split(path, props.getProperty("path.separator"), 0);

      for(int i = 0; i < tokens.length; ++i) {
         if (dirs == null || dirs.length == 0 || endsWith(tokens[i], dirs)) {
            File file = new File(tokens[i]);
            if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.existsAction(file))) {
               if ((Boolean)AccessController.doPrivileged(J2DoPrivHelper.isDirectoryAction(file))) {
                  this.addIterator(new FileMetaDataIterator(file, filter));
               } else if (tokens[i].endsWith(".jar")) {
                  try {
                     ZipFile zFile = (ZipFile)AccessController.doPrivileged(J2DoPrivHelper.newZipFileAction(file));
                     this.addIterator(new ZipFileMetaDataIterator(zFile, filter));
                  } catch (PrivilegedActionException var9) {
                     throw (IOException)var9.getException();
                  }
               }
            }
         }
      }

   }

   private static boolean endsWith(String token, String[] suffs) {
      for(int i = 0; i < suffs.length; ++i) {
         if (token.endsWith(suffs[i])) {
            return true;
         }
      }

      return false;
   }
}
