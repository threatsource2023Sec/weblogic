package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.LinkedList;

public final class ParseState {
   private static final char TAB = '\t';
   private final LinkedList state;

   public ParseState() {
      this.state = new LinkedList();
   }

   private ParseState(ParseState other) {
      this.state = (LinkedList)other.state.clone();
   }

   public void push(Entry entry) {
      this.state.push(entry);
   }

   public void pop() {
      this.state.pop();
   }

   @Nullable
   public Entry peek() {
      return (Entry)this.state.peek();
   }

   public ParseState snapshot() {
      return new ParseState(this);
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();

      for(int x = 0; x < this.state.size(); ++x) {
         if (x > 0) {
            sb.append('\n');

            for(int y = 0; y < x; ++y) {
               sb.append('\t');
            }

            sb.append("-> ");
         }

         sb.append(this.state.get(x));
      }

      return sb.toString();
   }

   public interface Entry {
   }
}
