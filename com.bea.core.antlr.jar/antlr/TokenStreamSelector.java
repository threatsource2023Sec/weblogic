package antlr;

import antlr.ASdebug.ASDebugStream;
import antlr.ASdebug.IASDebugStream;
import antlr.ASdebug.TokenOffsetInfo;
import antlr.collections.Stack;
import antlr.collections.impl.LList;
import java.util.Hashtable;

public class TokenStreamSelector implements TokenStream, IASDebugStream {
   protected Hashtable inputStreamNames = new Hashtable();
   protected TokenStream input;
   protected Stack streamStack = new LList();

   public void addInputStream(TokenStream var1, String var2) {
      this.inputStreamNames.put(var2, var1);
   }

   public TokenStream getCurrentStream() {
      return this.input;
   }

   public TokenStream getStream(String var1) {
      TokenStream var2 = (TokenStream)this.inputStreamNames.get(var1);
      if (var2 == null) {
         throw new IllegalArgumentException("TokenStream " + var1 + " not found");
      } else {
         return var2;
      }
   }

   public Token nextToken() throws TokenStreamException {
      while(true) {
         try {
            return this.input.nextToken();
         } catch (TokenStreamRetryException var2) {
         }
      }
   }

   public TokenStream pop() {
      TokenStream var1 = (TokenStream)this.streamStack.pop();
      this.select(var1);
      return var1;
   }

   public void push(TokenStream var1) {
      this.streamStack.push(this.input);
      this.select(var1);
   }

   public void push(String var1) {
      this.streamStack.push(this.input);
      this.select(var1);
   }

   public void retry() throws TokenStreamRetryException {
      throw new TokenStreamRetryException();
   }

   public void select(TokenStream var1) {
      this.input = var1;
   }

   public void select(String var1) throws IllegalArgumentException {
      this.input = this.getStream(var1);
   }

   public String getEntireText() {
      return ASDebugStream.getEntireText(this.input);
   }

   public TokenOffsetInfo getOffsetInfo(Token var1) {
      return ASDebugStream.getOffsetInfo(this.input, var1);
   }
}
