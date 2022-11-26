package weblogic.utils.classfile.expr;

public final class Const {
   public static final Expression NULL = new ConstNullExpression();
   public static final Expression THIS = new ConstThisExpression();

   public static ConstIntExpression get(int i) {
      return new ConstIntExpression(i);
   }

   public static ConstLongExpression get(long l) {
      return new ConstLongExpression(l);
   }

   public static ConstDoubleExpression get(double d) {
      return new ConstDoubleExpression(d);
   }

   public static ConstStringExpression get(String s) {
      return new ConstStringExpression(s);
   }

   public static ConstClassExpression get(Class c) {
      return new ConstClassExpression(c);
   }

   public static ConstClassExpression getClass(String s) {
      return new ConstClassExpression(s);
   }

   public static ConstBooleanExpression get(boolean b) {
      return new ConstBooleanExpression(b);
   }
}
