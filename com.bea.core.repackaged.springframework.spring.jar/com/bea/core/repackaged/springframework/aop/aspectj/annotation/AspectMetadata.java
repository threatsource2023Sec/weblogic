package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.aspectj.lang.annotation.Aspect;
import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.PerClauseKind;
import com.bea.core.repackaged.springframework.aop.Pointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.bea.core.repackaged.springframework.aop.aspectj.TypePatternClassFilter;
import com.bea.core.repackaged.springframework.aop.framework.AopConfigException;
import com.bea.core.repackaged.springframework.aop.support.ComposablePointcut;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class AspectMetadata implements Serializable {
   private final String aspectName;
   private final Class aspectClass;
   private transient AjType ajType;
   private final Pointcut perClausePointcut;

   public AspectMetadata(Class aspectClass, String aspectName) {
      this.aspectName = aspectName;
      Class currClass = aspectClass;

      AjType ajType;
      for(ajType = null; currClass != Object.class; currClass = currClass.getSuperclass()) {
         AjType ajTypeToCheck = AjTypeSystem.getAjType(currClass);
         if (ajTypeToCheck.isAspect()) {
            ajType = ajTypeToCheck;
            break;
         }
      }

      if (ajType == null) {
         throw new IllegalArgumentException("Class '" + aspectClass.getName() + "' is not an @AspectJ aspect");
      } else if (ajType.getDeclarePrecedence().length > 0) {
         throw new IllegalArgumentException("DeclarePrecendence not presently supported in Spring AOP");
      } else {
         this.aspectClass = ajType.getJavaClass();
         this.ajType = ajType;
         switch (this.ajType.getPerClause().getKind()) {
            case SINGLETON:
               this.perClausePointcut = Pointcut.TRUE;
               return;
            case PERTARGET:
            case PERTHIS:
               AspectJExpressionPointcut ajexp = new AspectJExpressionPointcut();
               ajexp.setLocation(aspectClass.getName());
               ajexp.setExpression(this.findPerClause(aspectClass));
               ajexp.setPointcutDeclarationScope(aspectClass);
               this.perClausePointcut = ajexp;
               return;
            case PERTYPEWITHIN:
               this.perClausePointcut = new ComposablePointcut(new TypePatternClassFilter(this.findPerClause(aspectClass)));
               return;
            default:
               throw new AopConfigException("PerClause " + ajType.getPerClause().getKind() + " not supported by Spring AOP for " + aspectClass);
         }
      }
   }

   private String findPerClause(Class aspectClass) {
      String str = ((Aspect)aspectClass.getAnnotation(Aspect.class)).value();
      str = str.substring(str.indexOf(40) + 1);
      str = str.substring(0, str.length() - 1);
      return str;
   }

   public AjType getAjType() {
      return this.ajType;
   }

   public Class getAspectClass() {
      return this.aspectClass;
   }

   public String getAspectName() {
      return this.aspectName;
   }

   public Pointcut getPerClausePointcut() {
      return this.perClausePointcut;
   }

   public boolean isPerThisOrPerTarget() {
      PerClauseKind kind = this.getAjType().getPerClause().getKind();
      return kind == PerClauseKind.PERTARGET || kind == PerClauseKind.PERTHIS;
   }

   public boolean isPerTypeWithin() {
      PerClauseKind kind = this.getAjType().getPerClause().getKind();
      return kind == PerClauseKind.PERTYPEWITHIN;
   }

   public boolean isLazilyInstantiated() {
      return this.isPerThisOrPerTarget() || this.isPerTypeWithin();
   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      inputStream.defaultReadObject();
      this.ajType = AjTypeSystem.getAjType(this.aspectClass);
   }
}
