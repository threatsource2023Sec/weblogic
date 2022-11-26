package weblogic.entitlement.expression;

import weblogic.security.shared.LoggerWrapper;

public abstract class EExprRep implements EExpression {
   protected static final int DEPENDS_ON_UNKNOWN = 0;
   private static LoggerWrapper LOG = LoggerWrapper.getInstance("SecurityEEngine");
   private int dependsOn = 0;
   protected boolean Enclosed = false;

   public void SetEnclosed() {
      this.Enclosed = true;
   }

   public final int getDependsOn() {
      if (this.dependsOn == 0) {
         this.dependsOn = this.getDependsOnInternal();
      }

      return this.dependsOn;
   }

   protected abstract int getDependsOnInternal();

   public final String externalize() {
      StringBuffer buf = new StringBuffer();
      this.writeExternalForm(buf);
      return buf.toString();
   }

   protected abstract void writeExternalForm(StringBuffer var1);

   public String serialize() {
      StringBuffer buf = new StringBuffer();
      this.outForPersist(buf);
      return buf.toString();
   }

   abstract void outForPersist(StringBuffer var1);

   abstract char getTypeId();

   protected void writeTypeId(StringBuffer buf) {
      buf.append((char)(this.getTypeId() | (this.Enclosed ? 128 : 0)));
   }

   public static EExprRep deserialize(String serialized) {
      if (serialized != null && serialized.length() != 0) {
         char[] eexpr = serialized.toCharArray();
         int[] i = new int[]{0};
         return deserialize(eexpr, i);
      } else {
         return null;
      }
   }

   private static EExprRep deserialize(char[] eexpr, int[] i) {
      int var10004 = i[0];
      int var10001 = i[0];
      i[0] = var10004 + 1;
      char id = eexpr[var10001];
      char typeId = (char)(id & 127);
      EExprRep expr = null;
      switch (typeId) {
         case '&':
            expr = new Intersection(getFuncArgs(eexpr, i));
            break;
         case '-':
            expr = new Difference(getFuncArgs(eexpr, i));
            break;
         case 'G':
            expr = new GroupList(getFuncArgs(eexpr, i));
            break;
         case 'R':
            expr = new RoleList(getFuncArgs(eexpr, i));
            break;
         case 'U':
            expr = new UserList(getFuncArgs(eexpr, i));
            break;
         case 'e':
            return Empty.EMPTY;
         case 'g':
            expr = new GroupIdentifier(getStr(eexpr, i));
            break;
         case 'p':
            String name = getStr(eexpr, i);
            var10004 = i[0];
            var10001 = i[0];
            i[0] = var10004 + 1;
            int count = eexpr[var10001];
            String[] params = null;
            if (count > 0) {
               params = new String[count];

               for(int j = 0; j < count; ++j) {
                  params[j] = getStr(eexpr, i);
               }
            }

            try {
               expr = new PredicateOp(name, params);
            } catch (Exception var9) {
               if (LOG.isDebugEnabled()) {
                  LOG.debug("Failed to deserialize a predicate in expression", var9);
               }
            }
            break;
         case 'r':
            expr = new RoleIdentifier(getStr(eexpr, i));
            break;
         case 'u':
            expr = new UserIdentifier(getStr(eexpr, i));
            break;
         case '|':
            expr = new Union(getFuncArgs(eexpr, i));
            break;
         case '~':
            expr = new Inverse(getFuncArgs(eexpr, i)[0]);
            break;
         default:
            throw new RuntimeException("Invalid expression type id: " + typeId);
      }

      ((EExprRep)expr).Enclosed = id > 128;
      return (EExprRep)expr;
   }

   private static String getStr(char[] e, int[] i) {
      int start;
      int var10002;
      for(start = i[0]; i[0] < e.length && e[i[0]] != '\n'; var10002 = i[0]++) {
      }

      int var10007 = i[0];
      int var10004 = i[0];
      i[0] = var10007 + 1;
      return new String(e, start, var10004 - start);
   }

   protected static void writeStr(String str, StringBuffer buf) {
      buf.append(str).append('\n');
   }

   private static EExprRep[] getFuncArgs(char[] e, int[] i) {
      int var10004 = i[0];
      int var10001 = i[0];
      i[0] = var10004 + 1;
      int count = e[var10001];
      EExprRep[] args = new EExprRep[count];

      for(int j = 0; j < count; ++j) {
         args[j] = deserialize(e, i);
      }

      return args;
   }
}
