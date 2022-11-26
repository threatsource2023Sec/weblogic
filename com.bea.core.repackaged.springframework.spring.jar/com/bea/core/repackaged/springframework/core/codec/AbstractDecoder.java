package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.MimeType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public abstract class AbstractDecoder implements Decoder {
   private final List decodableMimeTypes;
   protected Log logger = LogFactory.getLog(this.getClass());

   protected AbstractDecoder(MimeType... supportedMimeTypes) {
      this.decodableMimeTypes = Arrays.asList(supportedMimeTypes);
   }

   public void setLogger(Log logger) {
      this.logger = logger;
   }

   public Log getLogger() {
      return this.logger;
   }

   public List getDecodableMimeTypes() {
      return this.decodableMimeTypes;
   }

   public boolean canDecode(ResolvableType elementType, @Nullable MimeType mimeType) {
      if (mimeType == null) {
         return true;
      } else {
         Iterator var3 = this.decodableMimeTypes.iterator();

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

   public Mono decodeToMono(Publisher inputStream, ResolvableType elementType, @Nullable MimeType mimeType, @Nullable Map hints) {
      throw new UnsupportedOperationException();
   }
}
