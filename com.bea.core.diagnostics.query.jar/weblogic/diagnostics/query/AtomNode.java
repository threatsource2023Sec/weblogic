package weblogic.diagnostics.query;

import antlr.CommonAST;
import antlr.Token;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;

public abstract class AtomNode extends CommonAST {
   private static final long serialVersionUID = 8775291253300674959L;
   protected static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();
   protected static final DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private Pattern pattern = null;
   protected int javaType = -1;

   public AtomNode() {
   }

   public AtomNode(Token tok) {
      this.initialize(tok);
   }

   protected int getJavaType() {
      return this.javaType;
   }

   public Pattern getPattern(boolean sqlLike) {
      if (this.pattern == null) {
         String s = this.getText();
         if (sqlLike) {
            s = this.getLikePatternString();
         }

         if (queryDebugLogger.isDebugEnabled()) {
            queryDebugLogger.debug("Compiling pattern for regex " + s);
         }

         this.pattern = Pattern.compile(s, 32);
      }

      return this.pattern;
   }

   public String getLikePatternString() {
      String s = this.getText();
      s = s.replaceAll("%", "(.)*");
      s = s.replaceAll("_", ".");
      return s;
   }

   public abstract Object getValue() throws UnknownVariableException;

   public abstract String getString() throws UnknownVariableException;

   public abstract int getIntValue() throws UnknownVariableException;

   public abstract long getLongValue() throws UnknownVariableException;

   public abstract float getFloatValue() throws UnknownVariableException;

   public abstract double getDoubleValue() throws UnknownVariableException;

   public String toString() {
      return this.getText();
   }
}
