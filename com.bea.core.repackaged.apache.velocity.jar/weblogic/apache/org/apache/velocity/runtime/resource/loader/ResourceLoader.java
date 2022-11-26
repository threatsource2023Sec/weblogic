package weblogic.apache.org.apache.velocity.runtime.resource.loader;

import java.io.InputStream;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;

public abstract class ResourceLoader {
   protected boolean isCachingOn = false;
   protected long modificationCheckInterval = 2L;
   protected String className = null;
   protected RuntimeServices rsvc = null;

   public void commonInit(RuntimeServices rs, ExtendedProperties configuration) {
      this.rsvc = rs;
      this.isCachingOn = configuration.getBoolean("cache", false);
      this.modificationCheckInterval = configuration.getLong("modificationCheckInterval", 0L);
      this.className = configuration.getString("class");
   }

   public abstract void init(ExtendedProperties var1);

   public abstract InputStream getResourceStream(String var1) throws ResourceNotFoundException;

   public abstract boolean isSourceModified(Resource var1);

   public abstract long getLastModified(Resource var1);

   public String getClassName() {
      return this.className;
   }

   public void setCachingOn(boolean value) {
      this.isCachingOn = value;
   }

   public boolean isCachingOn() {
      return this.isCachingOn;
   }

   public void setModificationCheckInterval(long modificationCheckInterval) {
      this.modificationCheckInterval = modificationCheckInterval;
   }

   public long getModificationCheckInterval() {
      return this.modificationCheckInterval;
   }
}
