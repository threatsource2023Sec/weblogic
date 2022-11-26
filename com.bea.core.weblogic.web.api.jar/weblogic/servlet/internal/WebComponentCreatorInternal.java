package weblogic.servlet.internal;

public interface WebComponentCreatorInternal {
   void inject(Object var1);

   void notifyPreDestroy(Object var1);

   void notifyPostConstruct(Object var1);

   boolean needDependencyInjection(Object var1);
}
