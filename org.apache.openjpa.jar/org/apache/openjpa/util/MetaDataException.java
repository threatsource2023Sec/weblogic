package org.apache.openjpa.util;

import org.apache.openjpa.lib.util.Localizer;

public class MetaDataException extends UserException {
   public MetaDataException() {
      this.setFatal(true);
   }

   public MetaDataException(String msg) {
      super(msg);
      this.setFatal(true);
   }

   public MetaDataException(Localizer.Message msg) {
      super(msg);
      this.setFatal(true);
   }

   public MetaDataException(Localizer.Message msg, Throwable nested) {
      super(msg, nested);
      this.setFatal(true);
   }

   public int getSubtype() {
      return 1;
   }
}
