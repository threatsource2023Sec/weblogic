package org.antlr.runtime.debug;

import java.io.IOException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public class DebugParser extends Parser {
   protected DebugEventListener dbg;
   public boolean isCyclicDecision;

   public DebugParser(TokenStream input, DebugEventListener dbg, RecognizerSharedState state) {
      super((TokenStream)(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, dbg)), state);
      this.dbg = null;
      this.isCyclicDecision = false;
      this.setDebugListener(dbg);
   }

   public DebugParser(TokenStream input, RecognizerSharedState state) {
      super((TokenStream)(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, (DebugEventListener)null)), state);
      this.dbg = null;
      this.isCyclicDecision = false;
   }

   public DebugParser(TokenStream input, DebugEventListener dbg) {
      this((TokenStream)(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, dbg)), dbg, (RecognizerSharedState)null);
   }

   public void setDebugListener(DebugEventListener dbg) {
      if (this.input instanceof DebugTokenStream) {
         ((DebugTokenStream)this.input).setDebugListener(dbg);
      }

      this.dbg = dbg;
   }

   public DebugEventListener getDebugListener() {
      return this.dbg;
   }

   public void reportError(IOException e) {
      System.err.println(e);
      e.printStackTrace(System.err);
   }

   public void beginResync() {
      this.dbg.beginResync();
   }

   public void endResync() {
      this.dbg.endResync();
   }

   public void beginBacktrack(int level) {
      this.dbg.beginBacktrack(level);
   }

   public void endBacktrack(int level, boolean successful) {
      this.dbg.endBacktrack(level, successful);
   }

   public void reportError(RecognitionException e) {
      super.reportError(e);
      this.dbg.recognitionException(e);
   }
}
