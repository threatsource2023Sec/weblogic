package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import javax.validation.ValidationException;

public class MessageDescriptorFormatException extends ValidationException {
   public MessageDescriptorFormatException(String s) {
      super(s);
   }
}
