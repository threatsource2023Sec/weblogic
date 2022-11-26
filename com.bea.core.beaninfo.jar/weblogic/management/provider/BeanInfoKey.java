package weblogic.management.provider;

public interface BeanInfoKey {
   String getBeanInfoClassName();

   Class getBeanInfoClass();

   ClassLoader getBeanInfoClassLoader();

   boolean isReadOnly();

   String getVersion();
}
