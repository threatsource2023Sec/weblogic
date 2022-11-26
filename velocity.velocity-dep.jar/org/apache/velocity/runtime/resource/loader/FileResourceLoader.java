package org.apache.velocity.runtime.resource.loader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.util.StringUtils;

public class FileResourceLoader extends ResourceLoader {
   private Vector paths = null;
   private Hashtable templatePaths = new Hashtable();

   public void init(ExtendedProperties configuration) {
      super.rsvc.info("FileResourceLoader : initialization starting.");
      this.paths = configuration.getVector("path");
      int sz = this.paths.size();

      for(int i = 0; i < sz; ++i) {
         super.rsvc.info("FileResourceLoader : adding path '" + (String)this.paths.get(i) + "'");
      }

      super.rsvc.info("FileResourceLoader : initialization complete.");
   }

   public synchronized InputStream getResourceStream(String templateName) throws ResourceNotFoundException {
      if (templateName != null && templateName.length() != 0) {
         String template = StringUtils.normalizePath(templateName);
         if (template != null && template.length() != 0) {
            if (template.startsWith("/")) {
               template = template.substring(1);
            }

            int size = this.paths.size();

            String path;
            for(int i = 0; i < size; ++i) {
               path = (String)this.paths.get(i);
               InputStream inputStream = this.findTemplate(path, template);
               if (inputStream != null) {
                  this.templatePaths.put(templateName, path);
                  return inputStream;
               }
            }

            path = "FileResourceLoader Error: cannot find resource " + template;
            throw new ResourceNotFoundException(path);
         } else {
            String msg = "File resource error : argument " + template + " contains .. and may be trying to access " + "content outside of template root.  Rejected.";
            super.rsvc.error("FileResourceLoader : " + msg);
            throw new ResourceNotFoundException(msg);
         }
      } else {
         throw new ResourceNotFoundException("Need to specify a file name or file path!");
      }
   }

   private InputStream findTemplate(String path, String template) {
      try {
         File file = new File(path, template);
         return file.canRead() ? new BufferedInputStream(new FileInputStream(file.getAbsolutePath())) : null;
      } catch (FileNotFoundException var4) {
         return null;
      }
   }

   public boolean isSourceModified(Resource resource) {
      boolean modified = true;
      String fileName = resource.getName();
      String path = (String)this.templatePaths.get(fileName);
      File currentFile = null;

      for(int i = 0; currentFile == null && i < this.paths.size(); ++i) {
         String testPath = (String)this.paths.get(i);
         File testFile = new File(testPath, fileName);
         if (testFile.canRead()) {
            currentFile = testFile;
         }
      }

      File file = new File(path, fileName);
      if (currentFile != null && file.exists() && currentFile.equals(file) && file.canRead()) {
         modified = file.lastModified() != resource.getLastModified();
      }

      return modified;
   }

   public long getLastModified(Resource resource) {
      String path = (String)this.templatePaths.get(resource.getName());
      File file = new File(path, resource.getName());
      return file.canRead() ? file.lastModified() : 0L;
   }
}
