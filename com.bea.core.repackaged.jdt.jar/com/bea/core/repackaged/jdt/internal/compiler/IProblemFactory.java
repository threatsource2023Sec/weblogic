package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import java.util.Locale;

public interface IProblemFactory {
   CategorizedProblem createProblem(char[] var1, int var2, String[] var3, String[] var4, int var5, int var6, int var7, int var8, int var9);

   CategorizedProblem createProblem(char[] var1, int var2, String[] var3, int var4, String[] var5, int var6, int var7, int var8, int var9, int var10);

   Locale getLocale();

   String getLocalizedMessage(int var1, String[] var2);

   String getLocalizedMessage(int var1, int var2, String[] var3);
}
