package com.bea.common.logger.spi;

public interface LoggableMessageSpi {
   String getPrefix();

   String getSubsystem();

   String getMessageId();

   String getFormattedMessageBody();
}
