package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import java.lang.invoke.MethodHandles;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class InterpolationTermState implements ParserState {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public void terminate(TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      throw LOG.getNonTerminatedParameterException(tokenCollector.getOriginalMessageDescriptor(), '{');
   }

   public void handleNonMetaCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
   }

   public void handleBeginTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      throw LOG.getNestedParameterException(tokenCollector.getOriginalMessageDescriptor());
   }

   public void handleEndTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
      tokenCollector.terminateToken();
      tokenCollector.transitionState(new MessageState());
   }

   public void handleEscapeCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
      ParserState state = new EscapedState(this);
      tokenCollector.transitionState(state);
   }

   public void handleELDesignator(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
   }
}
