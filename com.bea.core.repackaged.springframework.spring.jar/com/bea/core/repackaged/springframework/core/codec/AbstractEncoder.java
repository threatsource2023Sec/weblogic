package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractEncoder implements Encoder {
   private final List encodableMimeTypes;
   protected Log logger = LogFactory.getLog(this.getClass());

   protected AbstractEncoder(MimeType... supportedMimeTypes) {
      this.encodableMimeTypes = Arrays.asList(supportedMimeTypes);
   }

   public void setLogger(Log logger) {
      this.logger = logger;
   }

   public Log getLogger() {
      return this.logger;
   }

   public List getEncodableMimeTypes() {
      return this.encodableMimeTypes;
   }

   public boolean canEncode(ResolvableType elementType, @Nullable MimeType mimeType) {
      if (mimeType == null) {
         return true;
      } else {
         Iterator var3 = this.encodableMimeTypes.iterator();

         MimeType candidate;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            candidate = (MimeType)var3.next();
         } while(!candidate.isCompatibleWith(mimeType));

         return true;
      }
   }
}
