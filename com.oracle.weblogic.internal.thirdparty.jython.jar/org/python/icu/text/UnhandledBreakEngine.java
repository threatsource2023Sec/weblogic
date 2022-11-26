package org.python.icu.text;

import java.text.CharacterIterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.python.icu.impl.CharacterIteration;
import org.python.icu.lang.UCharacter;

final class UnhandledBreakEngine implements LanguageBreakEngine {
   final AtomicReferenceArray fHandled = new AtomicReferenceArray(5);

   public UnhandledBreakEngine() {
      for(int i = 0; i < this.fHandled.length(); ++i) {
         this.fHandled.set(i, new UnicodeSet());
      }

   }

   public boolean handles(int c, int breakType) {
      return breakType >= 0 && breakType < this.fHandled.length() && ((UnicodeSet)this.fHandled.get(breakType)).contains(c);
   }

   public int findBreaks(CharacterIterator text, int startPos, int endPos, boolean reverse, int breakType, DictionaryBreakEngine.DequeI foundBreaks) {
      if (breakType >= 0 && breakType < this.fHandled.length()) {
         UnicodeSet uniset = (UnicodeSet)this.fHandled.get(breakType);
         int c = CharacterIteration.current32(text);
         if (reverse) {
            while(text.getIndex() > startPos && uniset.contains(c)) {
               CharacterIteration.previous32(text);
               c = CharacterIteration.current32(text);
            }
         } else {
            while(text.getIndex() < endPos && uniset.contains(c)) {
               CharacterIteration.next32(text);
               c = CharacterIteration.current32(text);
            }
         }
      }

      return 0;
   }

   public void handleChar(int c, int breakType) {
      if (breakType >= 0 && breakType < this.fHandled.length() && c != Integer.MAX_VALUE) {
         UnicodeSet originalSet = (UnicodeSet)this.fHandled.get(breakType);
         if (!originalSet.contains(c)) {
            int script = UCharacter.getIntPropertyValue(c, 4106);
            UnicodeSet newSet = new UnicodeSet();
            newSet.applyIntPropertyValue(4106, script);
            newSet.addAll(originalSet);
            this.fHandled.set(breakType, newSet);
         }
      }

   }
}
