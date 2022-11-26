package org.hibernate.validator.internal.engine.messageinterpolation.parser;

public interface ParserState {
   void terminate(TokenCollector var1) throws MessageDescriptorFormatException;

   void handleNonMetaCharacter(char var1, TokenCollector var2) throws MessageDescriptorFormatException;

   void handleBeginTerm(char var1, TokenCollector var2) throws MessageDescriptorFormatException;

   void handleEndTerm(char var1, TokenCollector var2) throws MessageDescriptorFormatException;

   void handleEscapeCharacter(char var1, TokenCollector var2) throws MessageDescriptorFormatException;

   void handleELDesignator(char var1, TokenCollector var2) throws MessageDescriptorFormatException;
}
