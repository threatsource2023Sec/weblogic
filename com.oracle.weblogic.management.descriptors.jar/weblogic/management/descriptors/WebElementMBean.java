package weblogic.management.descriptors;

public interface WebElementMBean extends XMLElementMBean {
   boolean isValid();

   String[] getDescriptorErrors();

   void setDescriptorErrors(String[] var1);

   void addDescriptorError(String var1);

   void removeDescriptorError(String var1);
}
