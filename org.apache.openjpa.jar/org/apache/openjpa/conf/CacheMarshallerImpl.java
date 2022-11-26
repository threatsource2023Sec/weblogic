package org.apache.openjpa.conf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;

public class CacheMarshallerImpl implements CacheMarshaller, Configurable {
   private static final Localizer _loc = Localizer.forPackage(CacheMarshallerImpl.class);
   private String _id;
   private CacheMarshaller.ValidationPolicy _validationPolicy;
   private OpenJPAConfiguration _conf;
   private Log _log;
   private File _outputFile;
   private URL _inputURL;
   private String _inputResourceLocation;
   private boolean _consumeErrors = true;

   public Object load() {
      if (this._inputURL == null) {
         this._log.trace(_loc.get("cache-marshaller-no-inputs", (Object)this.getId()));
         return null;
      } else {
         Object o = null;
         ObjectInputStream in = null;

         try {
            in = new ObjectInputStream(new BufferedInputStream(this._inputURL.openStream()));
            o = in.readObject();
            o = this._validationPolicy.getValidData(o);
            if (o != null && o.getClass().isArray()) {
               Object[] array = (Object[])((Object[])o);

               for(int i = 0; i < array.length; ++i) {
                  this.configure(array[i]);
               }
            } else {
               this.configure(o);
            }

            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("cache-marshaller-loaded", o == null ? null : o.getClass().getName(), this._inputURL));
            }
         } catch (Exception var14) {
            if (!this._consumeErrors) {
               throw new InternalException(_loc.get("cache-marshaller-load-exception-fatal", (Object)this._inputURL), var14);
            }

            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("cache-marshaller-load-exception-ignore", (Object)this._inputURL), var14);
            }
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var13) {
               }
            }

         }

         return o;
      }
   }

   private void configure(Object o) {
      if (o instanceof Configurable) {
         ((Configurable)o).setConfiguration(this._conf);
         ((Configurable)o).startConfiguration();
         ((Configurable)o).endConfiguration();
      }

   }

   public void store(Object o) {
      if (this._outputFile == null) {
         this._log.trace(_loc.get("cache-marshaller-no-output-file", (Object)this.getId()));
      } else {
         OutputStream out = null;

         try {
            out = new FileOutputStream(this._outputFile);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(out));
            Object toStore = this._validationPolicy.getCacheableData(o);
            oos.writeObject(toStore);
            oos.flush();
            out.flush();
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("cache-marshaller-stored", o.getClass().getName(), this._outputFile));
            }
         } catch (Exception var14) {
            if (!this._consumeErrors) {
               throw new InternalException(_loc.get("cache-marshaller-store-exception", o.getClass().getName(), this._outputFile), var14);
            }

            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("cache-marshaller-store-exception", o.getClass().getName(), this._outputFile), var14);
            }
         } finally {
            if (out != null) {
               try {
                  out.close();
               } catch (IOException var13) {
               }
            }

         }

      }
   }

   public void setOutputFile(File file) {
      this._outputFile = file;
   }

   public File getOutputFile() {
      return this._outputFile;
   }

   public void setInputURL(URL url) {
      this._inputURL = url;
   }

   public void setInputResource(String resource) {
      this._inputResourceLocation = resource;
   }

   public void setConsumeSerializationErrors(boolean consume) {
      this._consumeErrors = consume;
   }

   public String getId() {
      return this._id;
   }

   public void setId(String id) {
      this._id = id;
   }

   public void setValidationPolicy(String policy) throws InstantiationException, IllegalAccessException {
      String name = Configurations.getClassName(policy);
      String props = Configurations.getProperties(policy);
      this._validationPolicy = (CacheMarshaller.ValidationPolicy)Configurations.newInstance(name, this._conf, props, (ClassLoader)null);
   }

   public CacheMarshaller.ValidationPolicy getValidationPolicy() {
      return this._validationPolicy;
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (OpenJPAConfiguration)conf;
      this._log = conf.getConfigurationLog();
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
      if (this._inputResourceLocation != null && this._inputURL != null) {
         throw new IllegalStateException(_loc.get("cache-marshaller-input-url-and-resource-specified").getMessage());
      } else {
         if (this._inputResourceLocation != null) {
            this.setInputUrlFromResourceLocation();
         }

         if (this._validationPolicy == null) {
            throw new IllegalStateException(_loc.get("cache-marshaller-null-validation-policy", (Object)this.getClass().getName()).getMessage());
         } else if (this._id == null) {
            throw new IllegalStateException(_loc.get("cache-marshaller-null-id", (Object)this.getClass().getName()).getMessage());
         }
      }
   }

   private void setInputUrlFromResourceLocation() {
      try {
         ClassLoader cl = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), (ClassLoader)null);
         List list = new ArrayList();
         Enumeration e = cl.getResources(this._inputResourceLocation);

         while(e.hasMoreElements()) {
            list.add(e.nextElement());
         }

         if (list.size() > 1) {
            if (!this._consumeErrors) {
               throw new IllegalStateException(_loc.get("cache-marshaller-multiple-resources", this.getId(), this._inputResourceLocation, list).getMessage());
            }

            if (this._log.isWarnEnabled()) {
               this._log.warn(_loc.get("cache-marshaller-multiple-resources-warn", this.getId(), this._inputResourceLocation, list).getMessage());
            }
         }

         if (!list.isEmpty()) {
            this._inputURL = (URL)list.get(0);
         }

      } catch (IOException var4) {
         IllegalStateException ise = new IllegalStateException(_loc.get("cache-marshaller-bad-url", this.getId(), this._inputResourceLocation).getMessage());
         ise.initCause(var4);
         throw ise;
      }
   }
}
