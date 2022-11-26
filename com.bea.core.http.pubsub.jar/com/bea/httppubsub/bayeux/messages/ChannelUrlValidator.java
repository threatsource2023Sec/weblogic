package com.bea.httppubsub.bayeux.messages;

public interface ChannelUrlValidator {
   void validate(String var1) throws InvalidChannelUrlFoundException;
}
