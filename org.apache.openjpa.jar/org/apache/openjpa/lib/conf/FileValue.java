package org.apache.openjpa.lib.conf;

import java.io.File;
import java.security.AccessController;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class FileValue extends Value {
   private File value;

   public FileValue(String prop) {
      super(prop);
   }

   public Class getValueType() {
      return File.class;
   }

   public void set(File value) {
      this.assertChangeable();
      File oldValue = this.value;
      this.value = value;
      if (!ObjectUtils.equals(oldValue, value)) {
         this.valueChanged();
      }

   }

   public File get() {
      return this.value;
   }

   protected String getInternalString() {
      return this.value == null ? null : (String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(this.value));
   }

   protected void setInternalString(String val) {
      this.set(new File(val));
   }

   protected void setInternalObject(Object obj) {
      this.set((File)obj);
   }
}
