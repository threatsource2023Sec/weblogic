package org.apache.oro.text.regex;

public interface PatternCompiler {
   Pattern compile(String var1) throws MalformedPatternException;

   Pattern compile(String var1, int var2) throws MalformedPatternException;

   Pattern compile(char[] var1) throws MalformedPatternException;

   Pattern compile(char[] var1, int var2) throws MalformedPatternException;
}
