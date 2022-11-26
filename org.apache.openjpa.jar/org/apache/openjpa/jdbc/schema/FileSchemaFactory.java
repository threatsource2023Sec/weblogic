package org.apache.openjpa.jdbc.schema;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.util.GeneralException;

public class FileSchemaFactory implements SchemaFactory, Configurable {
   private JDBCConfiguration _conf = null;
   private String _fileName = "package.schema";
   private ClassLoader _loader = null;

   public String getFile() {
      return this._fileName;
   }

   public void setFile(String fileName) {
      this._fileName = fileName;
   }

   /** @deprecated */
   public void setFileName(String name) {
      this.setFile(name);
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (JDBCConfiguration)conf;
      this._loader = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), (ClassLoader)null);
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public SchemaGroup readSchema() {
      URL url = (URL)AccessController.doPrivileged(J2DoPrivHelper.getResourceAction(this._loader, this._fileName));
      if (url == null) {
         return new SchemaGroup();
      } else {
         XMLSchemaParser parser = new XMLSchemaParser(this._conf);

         try {
            parser.parse(url);
         } catch (IOException var4) {
            throw new GeneralException(var4);
         }

         return parser.getSchemaGroup();
      }
   }

   public void storeSchema(SchemaGroup schema) {
      File file = Files.getFile(this._fileName, this._loader);
      XMLSchemaSerializer ser = new XMLSchemaSerializer(this._conf);
      ser.addAll(schema);

      try {
         ser.serialize(file, 1);
      } catch (IOException var5) {
         throw new GeneralException(var5);
      }
   }
}
