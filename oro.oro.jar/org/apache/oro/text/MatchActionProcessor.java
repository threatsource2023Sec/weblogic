package org.apache.oro.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Util;

public final class MatchActionProcessor {
   private Pattern __fieldSeparator;
   private PatternCompiler __compiler;
   private PatternMatcher __matcher;
   private Vector __patterns;
   private Vector __actions;
   private MatchAction __defaultAction;

   public MatchActionProcessor(PatternCompiler var1, PatternMatcher var2) {
      this.__fieldSeparator = null;
      this.__patterns = new Vector();
      this.__actions = new Vector();
      this.__defaultAction = new DefaultMatchAction();
      this.__compiler = var1;
      this.__matcher = var2;
   }

   public MatchActionProcessor() {
      this(new Perl5Compiler(), new Perl5Matcher());
   }

   public void addAction(String var1, int var2, MatchAction var3) throws MalformedPatternException {
      if (var1 != null) {
         this.__patterns.addElement(this.__compiler.compile(var1, var2));
      } else {
         this.__patterns.addElement((Object)null);
      }

      this.__actions.addElement(var3);
   }

   public void addAction(String var1, int var2) throws MalformedPatternException {
      this.addAction(var1, var2, this.__defaultAction);
   }

   public void addAction(String var1) throws MalformedPatternException {
      this.addAction(var1, 0);
   }

   public void addAction(String var1, MatchAction var2) throws MalformedPatternException {
      this.addAction(var1, 0, var2);
   }

   public void setFieldSeparator(String var1, int var2) throws MalformedPatternException {
      if (var1 == null) {
         this.__fieldSeparator = null;
      } else {
         this.__fieldSeparator = this.__compiler.compile(var1, var2);
      }
   }

   public void setFieldSeparator(String var1) throws MalformedPatternException {
      this.setFieldSeparator(var1, 0);
   }

   public void processMatches(InputStream var1, OutputStream var2, String var3) throws IOException {
      this.processMatches((Reader)(new InputStreamReader(var1, var3)), (Writer)(new OutputStreamWriter(var2)));
   }

   public void processMatches(InputStream var1, OutputStream var2) throws IOException {
      this.processMatches((Reader)(new InputStreamReader(var1)), (Writer)(new OutputStreamWriter(var2)));
   }

   public void processMatches(Reader var1, Writer var2) throws IOException {
      LineNumberReader var5 = new LineNumberReader(var1);
      PrintWriter var6 = new PrintWriter(var2);
      MatchActionInfo var7 = new MatchActionInfo();
      ArrayList var11 = new ArrayList();
      var7.matcher = this.__matcher;
      var7.fieldSeparator = this.__fieldSeparator;
      var7.input = var5;
      var7.output = var6;
      var7.fields = null;
      int var3 = this.__patterns.size();
      var7.lineNumber = 0;

      while((var7.line = var5.readLine()) != null) {
         var7.charLine = var7.line.toCharArray();

         for(int var4 = 0; var4 < var3; ++var4) {
            Object var8 = this.__patterns.elementAt(var4);
            MatchAction var10;
            if (var8 != null) {
               Pattern var9 = (Pattern)this.__patterns.elementAt(var4);
               if (this.__matcher.contains(var7.charLine, var9)) {
                  var7.match = this.__matcher.getMatch();
                  var7.lineNumber = var5.getLineNumber();
                  var7.pattern = var9;
                  if (this.__fieldSeparator != null) {
                     var11.clear();
                     Util.split(var11, this.__matcher, this.__fieldSeparator, var7.line);
                     var7.fields = var11;
                  } else {
                     var7.fields = null;
                  }

                  var10 = (MatchAction)this.__actions.elementAt(var4);
                  var10.processMatch(var7);
               }
            } else {
               var7.match = null;
               var7.lineNumber = var5.getLineNumber();
               if (this.__fieldSeparator != null) {
                  var11.clear();
                  Util.split(var11, this.__matcher, this.__fieldSeparator, var7.line);
                  var7.fields = var11;
               } else {
                  var7.fields = null;
               }

               var10 = (MatchAction)this.__actions.elementAt(var4);
               var10.processMatch(var7);
            }
         }
      }

      var6.flush();
      var5.close();
   }
}
