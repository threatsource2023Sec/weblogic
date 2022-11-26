package com.bea.staxb.buildtime.internal.joust;

public interface ExpressionFactory {
   Expression createBoolean(boolean var1);

   Expression createString(String var1);

   Expression createInt(int var1);

   Expression createNull();

   Expression createVerbatim(String var1);
}
