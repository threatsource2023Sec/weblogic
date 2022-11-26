package org.python.netty.handler.codec;

import org.python.netty.util.HashingStrategy;

public final class DefaultHeadersImpl extends DefaultHeaders {
   public DefaultHeadersImpl(HashingStrategy nameHashingStrategy, ValueConverter valueConverter, DefaultHeaders.NameValidator nameValidator) {
      super(nameHashingStrategy, valueConverter, nameValidator);
   }
}
