package org.apache.oro.text;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;

public interface PatternCache {
   Pattern addPattern(String var1) throws MalformedPatternException;

   Pattern addPattern(String var1, int var2) throws MalformedPatternException;

   Pattern getPattern(String var1) throws MalformedCachePatternException;

   Pattern getPattern(String var1, int var2) throws MalformedCachePatternException;

   int size();

   int capacity();
}
