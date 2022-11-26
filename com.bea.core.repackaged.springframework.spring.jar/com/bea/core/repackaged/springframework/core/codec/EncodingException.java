package com.bea.core.repackaged.springframework.core.codec;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class EncodingException extends CodecException {
   public EncodingException(String msg) {
      super(msg);
   }

   public EncodingException(String msg, @Nullable Throwable cause) {
      super(msg, cause);
   }
}
