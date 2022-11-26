package weblogic.apache.org.apache.oro.text;

import weblogic.apache.org.apache.oro.text.regex.MalformedPatternException;
import weblogic.apache.org.apache.oro.text.regex.Pattern;

public interface PatternCache {
   Pattern addPattern(String var1) throws MalformedPatternException;

   Pattern addPattern(String var1, int var2) throws MalformedPatternException;

   int capacity();

   Pattern getPattern(String var1) throws MalformedCachePatternException;

   Pattern getPattern(String var1, int var2) throws MalformedCachePatternException;

   int size();
}
