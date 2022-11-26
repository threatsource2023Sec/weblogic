package weblogic.diagnostics.instrumentation.engine.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;

public interface PointcutExpression extends Serializable {
   MatchInfo isEligibleCallsite(ClassInstrumentor var1, String var2, String var3, String var4) throws InvalidPointcutException;

   MatchInfo isEligibleCallsite(ClassInstrumentor var1, String var2, String var3, String var4, MethodInfo var5) throws InvalidPointcutException;

   MatchInfo isEligibleMethod(ClassInstrumentor var1, String var2, MethodInfo var3) throws InvalidPointcutException;

   MatchInfo isEligibleCatchBlock(ClassInstrumentor var1, String var2, MethodInfo var3) throws InvalidPointcutException;

   void accept(PointcutExpressionVisitor var1);

   void markAsKeep();

   boolean getKeepHint();

   public static final class Factory {
      public static PointcutExpression parse(String pointcutSpec) throws InvalidPointcutException {
         if (pointcutSpec != null && pointcutSpec.trim().length() != 0) {
            ByteArrayInputStream in = null;

            PointcutExpression var5;
            try {
               in = new ByteArrayInputStream(pointcutSpec.getBytes());
               PointcutLexer lexer = new PointcutLexer(in);
               PointcutParser parser = new PointcutParser(lexer);
               PointcutExpression pExpr = parser.pointcutExpr();
               var5 = pExpr;
            } catch (Exception var14) {
               throw new InvalidPointcutException(var14);
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var13) {
                     throw new InvalidPointcutException(var13);
                  }
               }

            }

            return var5;
         } else {
            throw new InvalidPointcutException("Empty pointcut specification");
         }
      }
   }
}
