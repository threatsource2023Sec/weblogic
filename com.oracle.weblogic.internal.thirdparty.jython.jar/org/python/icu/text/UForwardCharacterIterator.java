package org.python.icu.text;

public interface UForwardCharacterIterator {
   int DONE = -1;

   int next();

   int nextCodePoint();
}
