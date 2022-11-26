package org.antlr.codegen;

import java.util.ArrayList;
import java.util.List;
import org.antlr.runtime.Token;
import org.antlr.tool.Grammar;

public class Python3Target extends Target {
   public boolean useBaseTemplatesForSynPredFragments() {
      return false;
   }

   public String getTokenTypeAsTargetLabel(CodeGenerator generator, int ttype) {
      if (ttype >= 0 && ttype <= 3) {
         return String.valueOf(ttype);
      } else {
         String name = generator.grammar.getTokenDisplayName(ttype);
         return name.charAt(0) == '\'' ? String.valueOf(ttype) : name;
      }
   }

   public String getTargetCharLiteralFromANTLRCharLiteral(CodeGenerator generator, String literal) {
      int c = Grammar.getCharValueFromGrammarCharLiteral(literal);
      return String.valueOf(c);
   }

   private List splitLines(String text) {
      ArrayList l = new ArrayList();
      int idx = 0;

      while(true) {
         int eol = text.indexOf("\n", idx);
         if (eol == -1) {
            l.add(text.substring(idx));
            return l;
         }

         l.add(text.substring(idx, eol + 1));
         idx = eol + 1;
      }
   }

   public List postProcessAction(List chunks, Token actionToken) {
      List nChunks = new ArrayList();

      int lineNo;
      int firstChunk;
      int indent;
      for(lineNo = 0; lineNo < chunks.size(); ++lineNo) {
         Object chunk = chunks.get(lineNo);
         String ws;
         if (chunk instanceof String) {
            ws = (String)chunks.get(lineNo);
            if (nChunks.isEmpty() && actionToken.getCharPositionInLine() >= 0) {
               String ws = "";

               for(indent = 0; indent < actionToken.getCharPositionInLine(); ++indent) {
                  ws = ws + " ";
               }

               ws = ws + ws;
            }

            nChunks.addAll(this.splitLines(ws));
         } else {
            if (nChunks.isEmpty() && actionToken.getCharPositionInLine() >= 0) {
               ws = "";

               for(firstChunk = 0; firstChunk <= actionToken.getCharPositionInLine(); ++firstChunk) {
                  ws = ws + " ";
               }

               nChunks.add(ws);
            }

            nChunks.add(chunk);
         }
      }

      lineNo = actionToken.getLine();
      int col = 0;

      int lastChunk;
      for(lastChunk = nChunks.size() - 1; lastChunk > 0 && nChunks.get(lastChunk) instanceof String && ((String)nChunks.get(lastChunk)).trim().length() == 0; --lastChunk) {
      }

      for(firstChunk = 0; firstChunk <= lastChunk && nChunks.get(firstChunk) instanceof String && ((String)nChunks.get(firstChunk)).trim().length() == 0 && ((String)nChunks.get(firstChunk)).endsWith("\n"); ++firstChunk) {
         ++lineNo;
      }

      indent = -1;

      for(int i = firstChunk; i <= lastChunk; ++i) {
         Object chunk = nChunks.get(i);
         if (!(chunk instanceof String)) {
            ++col;
         } else {
            String text = (String)chunk;
            if (col == 0) {
               int j;
               if (indent == -1) {
                  indent = 0;

                  for(j = 0; j < text.length() && Character.isWhitespace(text.charAt(j)); ++j) {
                     ++indent;
                  }
               }

               if (text.length() >= indent) {
                  for(j = 0; j < indent; ++j) {
                     if (!Character.isWhitespace(text.charAt(j))) {
                        System.err.println("Warning: badly indented line " + lineNo + " in action:");
                        System.err.println(text);
                        break;
                     }
                  }

                  nChunks.set(i, text.substring(j));
               } else if (text.trim().length() > 0) {
                  System.err.println("Warning: badly indented line " + lineNo + " in action:");
                  System.err.println(text);
               }
            }

            if (text.endsWith("\n")) {
               ++lineNo;
               col = 0;
            } else {
               col += text.length();
            }
         }
      }

      return nChunks;
   }
}
