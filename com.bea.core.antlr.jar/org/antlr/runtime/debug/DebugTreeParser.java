package org.antlr.runtime.debug;

import java.io.IOException;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

public class DebugTreeParser extends TreeParser {
   protected DebugEventListener dbg;
   public boolean isCyclicDecision;

   public DebugTreeParser(TreeNodeStream input, DebugEventListener dbg, RecognizerSharedState state) {
      super((TreeNodeStream)(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, dbg)), state);
      this.dbg = null;
      this.isCyclicDecision = false;
      this.setDebugListener(dbg);
   }

   public DebugTreeParser(TreeNodeStream input, RecognizerSharedState state) {
      super((TreeNodeStream)(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, (DebugEventListener)null)), state);
      this.dbg = null;
      this.isCyclicDecision = false;
   }

   public DebugTreeParser(TreeNodeStream input, DebugEventListener dbg) {
      this((TreeNodeStream)(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, dbg)), dbg, (RecognizerSharedState)null);
   }

   public void setDebugListener(DebugEventListener dbg) {
      if (this.input instanceof DebugTreeNodeStream) {
         ((DebugTreeNodeStream)this.input).setDebugListener(dbg);
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

   public void reportError(RecognitionException e) {
      this.dbg.recognitionException(e);
   }

   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
      Object o = super.getMissingSymbol(input, e, expectedTokenType, follow);
      this.dbg.consumeNode(o);
      return o;
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
}
