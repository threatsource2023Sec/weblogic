package weblogic.servlet.internal.dd.compliance;

import weblogic.j2ee.validation.IDescriptorErrorInfo;

public class DescriptorErrorInfo implements IDescriptorErrorInfo {
   private Object key;
   private Object[] elementErrorKeys = new Object[1];
   private String[] elements = new String[1];

   public DescriptorErrorInfo(String element, Object key, Object elementErrorKey) {
      this.elements[0] = element;
      this.key = key;
      this.elementErrorKeys[0] = elementErrorKey;
   }

   public DescriptorErrorInfo(String[] elements, Object key, Object[] elementErrorKey) {
      this.elements = elements;
      this.key = key;
      this.elementErrorKeys = elementErrorKey;
   }

   public Object getTopLevelSearchKey() {
      return this.key;
   }

   public Object[] getElementErrorKeys() {
      return this.elementErrorKeys;
   }

   public String[] getElementTypes() {
      return this.elements;
   }
}
