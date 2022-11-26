package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.ObjectProvider;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionCustomizer;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.context.ApplicationContextInitializer;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.env.ConfigurableEnvironment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 11},
   bv = {1, 0, 2},
   k = 1,
   d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000256B4\u0012\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\b\u0006\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\u0002\u0010\nJ\u0086\u0001\u0010\u001b\u001a\u00020\u0005\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)H\u0086\b¢\u0006\u0002\u0010*J\u0096\u0001\u0010\u001b\u001a\u00020\u0005\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u001f2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\u000e\b\u0004\u0010+\u001a\b\u0012\u0004\u0012\u0002H\u001c0,H\u0086\b¢\u0006\u0002\u0010-J8\u0010.\u001a\u00020\u00052\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\u0002\b\u00062\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\b\u0006J\u0010\u0010/\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0002H\u0016J'\u00100\u001a\u00020\u00052\u0006\u00100\u001a\u00020\u001f2\u0017\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\b\u0006J\u001b\u00101\u001a\b\u0012\u0004\u0012\u0002H\u001c02\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u001dH\u0086\bJ&\u00103\u001a\u0002H\u001c\"\n\b\u0000\u0010\u001c\u0018\u0001*\u00020\u001d2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0086\b¢\u0006\u0002\u00104R,\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\u00000\fj\b\u0012\u0004\u0012\u00020\u0000`\r8\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0012\u001a\u00020\u00028\u0000@\u0000X\u0081.¢\u0006\u0014\n\u0000\u0012\u0004\b\u0013\u0010\u000f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001f\u0010\u0003\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\b\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"},
   d2 = {"Lorg/springframework/context/support/BeanDefinitionDsl;", "Lorg/springframework/context/ApplicationContextInitializer;", "Lorg/springframework/context/support/GenericApplicationContext;", "init", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "condition", "Lorg/springframework/core/env/ConfigurableEnvironment;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "children", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "children$annotations", "()V", "getChildren", "()Ljava/util/ArrayList;", "context", "context$annotations", "getContext", "()Lorg/springframework/context/support/GenericApplicationContext;", "setContext", "(Lorg/springframework/context/support/GenericApplicationContext;)V", "env", "getEnv", "()Lorg/springframework/core/env/ConfigurableEnvironment;", "bean", "T", "", "name", "", "scope", "Lorg/springframework/context/support/BeanDefinitionDsl$Scope;", "isLazyInit", "isPrimary", "isAutowireCandidate", "initMethodName", "destroyMethodName", "description", "role", "Lorg/springframework/context/support/BeanDefinitionDsl$Role;", "(Ljava/lang/String;Lorg/springframework/context/support/BeanDefinitionDsl$Scope;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lorg/springframework/context/support/BeanDefinitionDsl$Role;)V", "function", "Lkotlin/Function0;", "(Ljava/lang/String;Lorg/springframework/context/support/BeanDefinitionDsl$Scope;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lorg/springframework/context/support/BeanDefinitionDsl$Role;Lkotlin/jvm/functions/Function0;)V", "environment", "initialize", "profile", "provider", "Lorg/springframework/beans/factory/ObjectProvider;", "ref", "(Ljava/lang/String;)Ljava/lang/Object;", "Role", "Scope", "spring-context"}
)
public class BeanDefinitionDsl implements ApplicationContextInitializer {
   @NotNull
   private final ArrayList children;
   @NotNull
   public GenericApplicationContext context;
   private final Function1 init;
   private final Function1 condition;

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void children$annotations() {
   }

   @NotNull
   public final ArrayList getChildren() {
      return this.children;
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void context$annotations() {
   }

   @NotNull
   public final GenericApplicationContext getContext() {
      GenericApplicationContext var10000 = this.context;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
      }

      return var10000;
   }

   public final void setContext(@NotNull GenericApplicationContext var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.context = var1;
   }

   @NotNull
   public final ConfigurableEnvironment getEnv() {
      GenericApplicationContext var10000 = this.context;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("context");
      }

      ConfigurableEnvironment var1 = var10000.getEnvironment();
      Intrinsics.checkExpressionValueIsNotNull(var1, "context.environment");
      return var1;
   }

   private final void bean(String name, Scope scope, Boolean isLazyInit, Boolean isPrimary, Boolean isAutowireCandidate, String initMethodName, String destroyMethodName, String description, Role role) {
      BeanDefinitionCustomizer customizer = (BeanDefinitionCustomizer)(new BeanDefinitionCustomizer() {
         public final void customize(@NotNull BeanDefinition bd) {
            Intrinsics.checkParameterIsNotNull(bd, "bd");
            if (scope != null) {
               String var4 = scope.name();
               if (var4 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               String var10000 = var4.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               String var6 = var10000;
               bd.setScope(var6);
            }

            Boolean var8 = isLazyInit;
            Boolean var2;
            boolean it;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setLazyInit(isLazyInit);
            }

            var8 = isPrimary;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setPrimary(isPrimary);
            }

            var8 = isAutowireCandidate;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setAutowireCandidate(isAutowireCandidate);
            }

            if (initMethodName != null) {
               bd.setInitMethodName(initMethodName);
            }

            if (destroyMethodName != null) {
               bd.setDestroyMethodName(destroyMethodName);
            }

            if (description != null) {
               bd.setDescription(description);
            }

            if (role != null) {
               bd.setRole(role.ordinal());
            }

         }
      });
      String var10000 = name;
      if (name == null) {
         Intrinsics.reifiedOperationMarker(4, "T");
         var10000 = BeanDefinitionReaderUtils.uniqueBeanName(Object.class.getName(), (BeanDefinitionRegistry)this.getContext());
         Intrinsics.checkExpressionValueIsNotNull(var10000, "BeanDefinitionReaderUtil…class.java.name, context)");
      }

      String beanName = var10000;
      GenericApplicationContext var13 = this.getContext();
      Intrinsics.reifiedOperationMarker(4, "T");
      var13.registerBean(beanName, Object.class, customizer);
   }

   private final void bean(String name, Scope scope, Boolean isLazyInit, Boolean isPrimary, Boolean isAutowireCandidate, String initMethodName, String destroyMethodName, String description, Role role, Function0 function) {
      BeanDefinitionCustomizer customizer = (BeanDefinitionCustomizer)(new BeanDefinitionCustomizer() {
         public final void customize(@NotNull BeanDefinition bd) {
            Intrinsics.checkParameterIsNotNull(bd, "bd");
            if (scope != null) {
               String var4 = scope.name();
               if (var4 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               String var10000 = var4.toLowerCase();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
               String var6 = var10000;
               bd.setScope(var6);
            }

            Boolean var8 = isLazyInit;
            Boolean var2;
            boolean it;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setLazyInit(isLazyInit);
            }

            var8 = isPrimary;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setPrimary(isPrimary);
            }

            var8 = isAutowireCandidate;
            if (var8 != null) {
               var2 = var8;
               it = var2;
               bd.setAutowireCandidate(isAutowireCandidate);
            }

            if (initMethodName != null) {
               bd.setInitMethodName(initMethodName);
            }

            if (destroyMethodName != null) {
               bd.setDestroyMethodName(destroyMethodName);
            }

            if (description != null) {
               bd.setDescription(description);
            }

            if (role != null) {
               bd.setRole(role.ordinal());
            }

         }
      });
      String var10000 = name;
      if (name == null) {
         Intrinsics.reifiedOperationMarker(4, "T");
         var10000 = BeanDefinitionReaderUtils.uniqueBeanName(Object.class.getName(), (BeanDefinitionRegistry)this.getContext());
         Intrinsics.checkExpressionValueIsNotNull(var10000, "BeanDefinitionReaderUtil…class.java.name, context)");
      }

      String beanName = var10000;
      GenericApplicationContext var14 = this.getContext();
      Intrinsics.reifiedOperationMarker(4, "T");
      var14.registerBean(beanName, Object.class, (Supplier)(new Supplier() {
         @NotNull
         public final Object get() {
            return function.invoke();
         }
      }), customizer);
   }

   private final Object ref(String name) {
      GenericApplicationContext var10000;
      Object var4;
      if (name == null) {
         var10000 = this.getContext();
         Intrinsics.reifiedOperationMarker(4, "T");
         var4 = var10000.getBean(Object.class);
         Intrinsics.checkExpressionValueIsNotNull(var4, "context.getBean(T::class.java)");
      } else {
         var10000 = this.getContext();
         Intrinsics.reifiedOperationMarker(4, "T");
         var4 = var10000.getBean(name, Object.class);
         Intrinsics.checkExpressionValueIsNotNull(var4, "context.getBean(name, T::class.java)");
      }

      return var4;
   }

   private final ObjectProvider provider() {
      BeanFactory $receiver$iv = (BeanFactory)this.getContext();
      Intrinsics.needClassReification();
      ObjectProvider var10000 = $receiver$iv.getBeanProvider(ResolvableType.forType((new BeanDefinitionDsl$provider$$inlined$getBeanProvider$1()).getType()));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "getBeanProvider(Resolvab…Reference<T>() {}).type))");
      return var10000;
   }

   public final void profile(@NotNull final String profile, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull(profile, "profile");
      Intrinsics.checkParameterIsNotNull(init, "init");
      BeanDefinitionDsl beans = new BeanDefinitionDsl(init, (Function1)(new Function1() {
         public final boolean invoke(@NotNull ConfigurableEnvironment it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return ArraysKt.contains(it.getActiveProfiles(), profile);
         }
      }));
      this.children.add(beans);
   }

   public final void environment(@NotNull Function1 condition, @NotNull Function1 init) {
      Intrinsics.checkParameterIsNotNull(condition, "condition");
      Intrinsics.checkParameterIsNotNull(init, "init");
      BeanDefinitionDsl beans = new BeanDefinitionDsl(init, (Function1)(new Function1(condition) {
         public final boolean invoke(@NotNull ConfigurableEnvironment p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return (Boolean)((Function1)this.receiver).invoke(p1);
         }

         public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Function1.class);
         }

         public final String getName() {
            return "invoke";
         }

         public final String getSignature() {
            return "invoke(Ljava/lang/Object;)Ljava/lang/Object;";
         }
      }));
      this.children.add(beans);
   }

   public void initialize(@NotNull GenericApplicationContext context) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      this.context = context;
      this.init.invoke(this);
      Iterator var3 = this.children.iterator();

      while(var3.hasNext()) {
         BeanDefinitionDsl child = (BeanDefinitionDsl)var3.next();
         Function1 var10000 = child.condition;
         ConfigurableEnvironment var10001 = context.getEnvironment();
         Intrinsics.checkExpressionValueIsNotNull(var10001, "context.environment");
         if ((Boolean)var10000.invoke(var10001)) {
            child.initialize(context);
         }
      }

   }

   public BeanDefinitionDsl(@NotNull Function1 init, @NotNull Function1 condition) {
      Intrinsics.checkParameterIsNotNull(init, "init");
      Intrinsics.checkParameterIsNotNull(condition, "condition");
      super();
      this.init = init;
      this.condition = condition;
      ArrayList var4 = new ArrayList();
      this.children = var4;
   }

   // $FF: synthetic method
   public BeanDefinitionDsl(Function1 var1, Function1 var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = (Function1)null.INSTANCE;
      }

      this(var1, var2);
   }

   @Metadata(
      mv = {1, 1, 11},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"},
      d2 = {"Lorg/springframework/context/support/BeanDefinitionDsl$Scope;", "", "(Ljava/lang/String;I)V", "SINGLETON", "PROTOTYPE", "spring-context"}
   )
   public static enum Scope {
      SINGLETON,
      PROTOTYPE;
   }

   @Metadata(
      mv = {1, 1, 11},
      bv = {1, 0, 2},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"},
      d2 = {"Lorg/springframework/context/support/BeanDefinitionDsl$Role;", "", "(Ljava/lang/String;I)V", "APPLICATION", "SUPPORT", "INFRASTRUCTURE", "spring-context"}
   )
   public static enum Role {
      APPLICATION,
      SUPPORT,
      INFRASTRUCTURE;
   }
}
