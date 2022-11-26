package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JProperty;
import com.bea.xbean.xb.xsdschema.ExtensionType;
import com.bea.xbean.xb.xsdschema.TopLevelComplexType;

public class ExceptionTypegenFacade extends DefaultTypegenFacade implements TypegenFacade {
   private JClass mExceptionClass;

   public ExceptionTypegenFacade(JClass exceptionClass, Java2SchemaContext ctx, TopLevelComplexType schemaType, ExtensionType extTypeOrNull, String targetNamespace, ByNameBean bindingType) {
      super(ctx, schemaType, extTypeOrNull, targetNamespace, bindingType);
      this.mExceptionClass = exceptionClass;
   }

   public PropgenFacade createNextElement(JElement srcContext) {
      this.finishCurrentProp();
      if (!(srcContext instanceof JProperty)) {
         throw new IllegalArgumentException(" ExceptionTypegenFacade.createNextElement expected an argument that implements JProperty");
      } else {
         JMethod getter = ((JProperty)srcContext).getGetter();
         JClass getterClass = (JClass)getter.getParent();
         if (getterClass.equals(this.mExceptionClass)) {
            if (this.mXsSequence == null) {
               this.mXsSequence = this.mExtensionType != null ? this.mExtensionType.addNewSequence() : this.mXsType.addNewSequence();
            }

            return this.mCurrentProp = this.createPropgenFacade(this.mContext, srcContext, new QNameProperty(), this.mTargetNamespace, this.mXsSequence.addNewElement());
         } else {
            return this.mCurrentProp = this.createBtsPropgenFacade(this.mContext, srcContext, new QNameProperty(), this.mTargetNamespace);
         }
      }
   }

   protected PropgenFacade createBtsPropgenFacade(Java2SchemaContext ctx, JElement srcContext, QNameProperty btsProp, String targetNamespace) {
      return new BtsPropgenFacade(ctx, srcContext, btsProp, targetNamespace);
   }
}
