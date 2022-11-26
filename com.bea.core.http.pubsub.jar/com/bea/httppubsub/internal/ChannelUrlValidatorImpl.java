package com.bea.httppubsub.internal;

import com.bea.httppubsub.bayeux.messages.ChannelUrlValidator;
import com.bea.httppubsub.bayeux.messages.InvalidChannelUrlFoundException;

public class ChannelUrlValidatorImpl implements ChannelUrlValidator {
   public void validate(String channelURL) throws InvalidChannelUrlFoundException {
      try {
         ChannelId.newInstance(channelURL);
      } catch (InvalidChannelException var3) {
         throw new InvalidChannelUrlFoundException(var3.getMessage(), var3);
      }
   }
}
