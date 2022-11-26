package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import java.lang.invoke.MethodHandles;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ELState implements ParserState {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public void terminate(TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken('$');
      tokenCollector.terminateToken();
   }

   public void handleNonMetaCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken('$');
      tokenCollector.appendToToken(character);
      tokenCollector.terminateToken();
      tokenCollector.transitionState(new MessageState());
   }

   public void handleBeginTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.terminateToken();
      tokenCollector.appendToToken('$');
      tokenCollector.appendToToken(character);
      tokenCollector.makeELToken();
      tokenCollector.transitionState(new InterpolationTermState());
   }

   public void handleEndTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      throw LOG.getNonTerminatedParameterException(tokenCollector.getOriginalMessageDescriptor(), character);
   }

   public void handleEscapeCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.transitionState(new EscapedState(this));
   }

   public void handleELDesignator(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleNonMetaCharacter(character, tokenCollector);
   }
}
