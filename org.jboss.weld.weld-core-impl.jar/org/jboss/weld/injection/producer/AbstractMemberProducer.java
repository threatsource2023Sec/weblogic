package org.jboss.weld.injection.producer;

import java.io.Serializable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMember;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMember;
import org.jboss.weld.bean.ContextualInstance;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.contexts.WeldCreationalContext;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractMemberProducer extends AbstractProducer {
   private final DisposalMethod disposalMethod;

   public AbstractMemberProducer(EnhancedAnnotatedMember enhancedMember, DisposalMethod disposalMethod) {
      this.disposalMethod = disposalMethod;
      this.checkDeclaringBean();
      this.checkProducerReturnType(enhancedMember);
   }

   protected void checkDeclaringBean() {
      if (this.getDeclaringBean() == null && !this.getAnnotated().isStatic()) {
         throw BeanLogger.LOG.declaringBeanMissing(this.getAnnotated());
      }
   }

   protected void checkProducerReturnType(EnhancedAnnotatedMember enhancedMember) {
      this.checkReturnTypeForWildcardsAndTypeVariables(enhancedMember, enhancedMember.getBaseType(), false);
   }

   private void checkReturnTypeForWildcardsAndTypeVariables(EnhancedAnnotatedMember enhancedMember, Type type, boolean isParameterizedType) {
      if (type instanceof TypeVariable) {
         if (!isParameterizedType) {
            throw this.producerWithInvalidTypeVariable(enhancedMember);
         }

         if (!this.isDependent()) {
            throw this.producerWithParameterizedTypeWithTypeVariableBeanTypeMustBeDependent(enhancedMember);
         }
      } else {
         if (type instanceof WildcardType) {
            throw this.producerWithInvalidWildcard(enhancedMember);
         }

         if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type[] var5 = parameterizedType.getActualTypeArguments();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Type parameterType = var5[var7];
               this.checkReturnTypeForWildcardsAndTypeVariables(enhancedMember, parameterType, true);
            }
         } else if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            this.checkReturnTypeForWildcardsAndTypeVariables(enhancedMember, arrayType.getGenericComponentType(), false);
         }
      }

   }

   protected abstract DefinitionException producerWithInvalidTypeVariable(AnnotatedMember var1);

   protected abstract DefinitionException producerWithInvalidWildcard(AnnotatedMember var1);

   protected abstract DefinitionException producerWithParameterizedTypeWithTypeVariableBeanTypeMustBeDependent(AnnotatedMember var1);

   private boolean isDependent() {
      return this.getBean() != null && Dependent.class.equals(this.getBean().getScope());
   }

   protected Object getReceiver(CreationalContext productCreationalContext, CreationalContext receiverCreationalContext) {
      if (this.getAnnotated().isStatic()) {
         return null;
      } else {
         if (productCreationalContext instanceof WeldCreationalContext) {
            WeldCreationalContext creationalContextImpl = (WeldCreationalContext)productCreationalContext;
            Object incompleteInstance = creationalContextImpl.getIncompleteInstance(this.getDeclaringBean());
            if (incompleteInstance != null) {
               BeanLogger.LOG.circularCall(this.getAnnotated(), this.getDeclaringBean());
               return incompleteInstance;
            }
         }

         return this.getBeanManager().getReference(this.getDeclaringBean(), (Type)null, receiverCreationalContext, true);
      }
   }

   public void dispose(Object instance) {
      if (this.disposalMethod != null) {
         if (this.disposalMethod.getAnnotated().isStatic()) {
            this.disposalMethod.invokeDisposeMethod((Object)null, instance, (CreationalContext)null);
         } else {
            WeldCreationalContext ctx = null;

            try {
               Object receiver = ContextualInstance.getIfExists(this.getDeclaringBean(), this.getBeanManager());
               if (receiver == null) {
                  ctx = this.getBeanManager().createCreationalContext((Contextual)null);
                  receiver = ContextualInstance.get((Bean)this.getDeclaringBean(), this.getBeanManager(), ctx.getCreationalContext(this.getDeclaringBean()));
               }

               if (receiver != null) {
                  this.disposalMethod.invokeDisposeMethod(receiver, instance, ctx);
               }
            } finally {
               if (ctx != null) {
                  ctx.release();
               }

            }
         }
      }

   }

   public Object produce(CreationalContext ctx) {
      CreationalContext receiverCreationalContext = this.getReceiverCreationalContext(ctx);
      Object receiver = this.getReceiver(ctx, receiverCreationalContext);

      Object var4;
      try {
         var4 = this.produce(receiver, ctx);
      } finally {
         receiverCreationalContext.release();
      }

      return var4;
   }

   private CreationalContext getReceiverCreationalContext(CreationalContext ctx) {
      return ctx instanceof WeldCreationalContext ? ((WeldCreationalContext)ctx).getProducerReceiverCreationalContext(this.getDeclaringBean()) : this.getBeanManager().createCreationalContext(this.getDeclaringBean());
   }

   public DisposalMethod getDisposalMethod() {
      return this.disposalMethod;
   }

   protected boolean isTypeSerializable(Object object) {
      return object instanceof Serializable;
   }

   public abstract BeanManagerImpl getBeanManager();

   public abstract Bean getDeclaringBean();

   public abstract Bean getBean();

   public abstract AnnotatedMember getAnnotated();

   protected abstract Object produce(Object var1, CreationalContext var2);

   public String toString() {
      StringBuilder result = new StringBuilder("Producer for ");
      if (this.getDeclaringBean() == null) {
         result.append(this.getAnnotated());
      } else {
         if (this.getBean() == null) {
            result.append(this.getAnnotated());
         } else {
            result.append(this.getBean());
         }

         result.append(" declared on ").append(this.getDeclaringBean());
      }

      return result.toString();
   }
}
