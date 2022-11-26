package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import java.lang.invoke.MethodHandles;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTermType;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class MessageState implements ParserState {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public void terminate(TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.terminateToken();
   }

   public void handleNonMetaCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
   }

   public void handleBeginTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.terminateToken();
      tokenCollector.appendToToken(character);
      if (tokenCollector.getInterpolationType().equals(InterpolationTermType.PARAMETER)) {
         tokenCollector.makeParameterToken();
      }

      tokenCollector.transitionState(new InterpolationTermState());
   }

   public void handleEndTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      throw LOG.getNonTerminatedParameterException(tokenCollector.getOriginalMessageDescriptor(), character);
   }

   public void handleEscapeCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
      tokenCollector.transitionState(new EscapedState(this));
   }

   public void handleELDesignator(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      if (tokenCollector.getInterpolationType().equals(InterpolationTermType.PARAMETER)) {
         this.handleNonMetaCharacter(character, tokenCollector);
      } else {
         tokenCollector.transitionState(new ELState());
      }

   }
}
