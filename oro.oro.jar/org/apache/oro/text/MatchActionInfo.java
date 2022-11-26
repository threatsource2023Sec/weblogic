package org.apache.oro.text;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;

public final class MatchActionInfo {
   public int lineNumber;
   public String line;
   public char[] charLine;
   public Pattern fieldSeparator;
   public List fields;
   public PatternMatcher matcher;
   public Pattern pattern;
   public MatchResult match;
   public PrintWriter output;
   public BufferedReader input;
}
