package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class DecodingException extends CodecException {
   public DecodingException(String msg) {
      super(msg);
   }

   public DecodingException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
