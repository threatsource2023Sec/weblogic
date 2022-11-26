package weblogic.management.runtime;

public interface SpringBeanDependencyValue {
   int INJECTION_TYPE_CONSTRUCTOR_ARG = 0;
   int INJECTION_TYPE_PROPERTY = 1;

   int getInjectionType();

   String getKey();

   String getStringValue();
}
