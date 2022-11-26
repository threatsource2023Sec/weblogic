package weblogic.descriptor.internal;

class DescriptorBeanKey {
   private final AbstractDescriptorBean bean;

   public DescriptorBeanKey(AbstractDescriptorBean bean) {
      assert bean != null;

      this.bean = bean;
   }

   public int hashCode() {
      Object key = this.bean == null ? null : this.bean._getKey();
      return key == null ? 0 : key.hashCode();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof DescriptorBeanKey) ? false : this.bean._isKeyEqual(((DescriptorBeanKey)other).bean);
      }
   }
}
