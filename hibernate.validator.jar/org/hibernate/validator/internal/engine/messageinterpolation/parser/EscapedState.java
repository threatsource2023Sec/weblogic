package org.hibernate.validator.internal.engine.messageinterpolation.parser;

public class EscapedState implements ParserState {
   ParserState previousState;

   public EscapedState(ParserState previousState) {
      this.previousState = previousState;
   }

   public void terminate(TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.terminateToken();
   }

   public void handleNonMetaCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleEscapedCharacter(character, tokenCollector);
   }

   public void handleBeginTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleEscapedCharacter(character, tokenCollector);
   }

   public void handleEndTerm(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleEscapedCharacter(character, tokenCollector);
   }

   public void handleEscapeCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleEscapedCharacter(character, tokenCollector);
   }

   public void handleELDesignator(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      this.handleEscapedCharacter(character, tokenCollector);
   }

   private void handleEscapedCharacter(char character, TokenCollector tokenCollector) throws MessageDescriptorFormatException {
      tokenCollector.appendToToken(character);
      tokenCollector.transitionState(this.previousState);
   }
}
