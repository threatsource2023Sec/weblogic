package weblogic.apache.org.apache.oro.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import weblogic.apache.org.apache.oro.text.regex.MalformedPatternException;
import weblogic.apache.org.apache.oro.text.regex.Pattern;
import weblogic.apache.org.apache.oro.text.regex.PatternCompiler;
import weblogic.apache.org.apache.oro.text.regex.PatternMatcher;
import weblogic.apache.org.apache.oro.text.regex.Perl5Compiler;
import weblogic.apache.org.apache.oro.text.regex.Perl5Matcher;
import weblogic.apache.org.apache.oro.text.regex.Util;

public final class MatchActionProcessor {
   private Pattern __fieldSeparator;
   private PatternCompiler __compiler;
   private PatternMatcher __matcher;
   private Vector __patterns;
   private Vector __actions;
   private MatchAction __defaultAction;

   public MatchActionProcessor() {
      this(new Perl5Compiler(), new Perl5Matcher());
   }

   public MatchActionProcessor(PatternCompiler var1, PatternMatcher var2) {
      this.__fieldSeparator = null;
      this.__patterns = new Vector();
      this.__actions = new Vector();
      this.__defaultAction = new DefaultMatchAction();
      this.__compiler = var1;
      this.__matcher = var2;
   }

   public void addAction(String var1) throws MalformedPatternException {
      this.addAction(var1, 0);
   }

   public void addAction(String var1, int var2) throws MalformedPatternException {
      this.addAction(var1, var2, this.__defaultAction);
   }

   public void addAction(String var1, int var2, MatchAction var3) throws MalformedPatternException {
      if (var1 != null) {
         this.__patterns.addElement(this.__compiler.compile(var1, var2));
      } else {
         this.__patterns.addElement((Object)null);
      }

      this.__actions.addElement(var3);
   }

   public void addAction(String var1, MatchAction var2) throws MalformedPatternException {
      this.addAction(var1, 0, var2);
   }

   public void processMatches(InputStream var1, OutputStream var2) throws IOException {
      LineNumberReader var5 = new LineNumberReader(new InputStreamReader(var1));
      PrintWriter var6 = new PrintWriter(var2);
      MatchActionInfo var7 = new MatchActionInfo();
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
                  if (this.__fieldSeparator != null) {
                     var7.fields = Util.split(this.__matcher, this.__fieldSeparator, var7.line);
                  }

                  var10 = (MatchAction)this.__actions.elementAt(var4);
                  var10.processMatch(var7);
               }
            } else {
               var7.match = null;
               var7.lineNumber = var5.getLineNumber();
               if (this.__fieldSeparator != null) {
                  var7.fields = Util.split(this.__matcher, this.__fieldSeparator, var7.line);
               }

               var10 = (MatchAction)this.__actions.elementAt(var4);
               var10.processMatch(var7);
            }
         }
      }

      var6.flush();
      var5.close();
   }

   public void setFieldSeparator(String var1) throws MalformedPatternException {
      this.setFieldSeparator(var1, 0);
   }

   public void setFieldSeparator(String var1, int var2) throws MalformedPatternException {
      if (var1 == null) {
         this.__fieldSeparator = null;
      } else {
         this.__fieldSeparator = this.__compiler.compile(var1, var2);
      }
   }
}
