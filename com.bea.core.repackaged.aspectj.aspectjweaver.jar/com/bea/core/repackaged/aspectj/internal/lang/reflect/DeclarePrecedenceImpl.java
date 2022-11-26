package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclarePrecedence;
import com.bea.core.repackaged.aspectj.lang.reflect.TypePattern;
import java.util.StringTokenizer;

public class DeclarePrecedenceImpl implements DeclarePrecedence {
   private AjType declaringType;
   private TypePattern[] precedenceList;
   private String precedenceString;

   public DeclarePrecedenceImpl(String precedenceList, AjType declaring) {
      this.declaringType = declaring;
      this.precedenceString = precedenceList;
      String toTokenize = precedenceList;
      if (precedenceList.startsWith("(")) {
         toTokenize = precedenceList.substring(1, precedenceList.length() - 1);
      }

      StringTokenizer strTok = new StringTokenizer(toTokenize, ",");
      this.precedenceList = new TypePattern[strTok.countTokens()];

      for(int i = 0; i < this.precedenceList.length; ++i) {
         this.precedenceList[i] = new TypePatternImpl(strTok.nextToken().trim());
      }

   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public TypePattern[] getPrecedenceOrder() {
      return this.precedenceList;
   }

   public String toString() {
      return "declare precedence : " + this.precedenceString;
   }
}
