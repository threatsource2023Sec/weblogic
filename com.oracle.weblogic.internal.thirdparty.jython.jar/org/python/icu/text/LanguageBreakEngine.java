package org.python.icu.text;

import java.text.CharacterIterator;

interface LanguageBreakEngine {
   boolean handles(int var1, int var2);

   int findBreaks(CharacterIterator var1, int var2, int var3, boolean var4, int var5, DictionaryBreakEngine.DequeI var6);
}
