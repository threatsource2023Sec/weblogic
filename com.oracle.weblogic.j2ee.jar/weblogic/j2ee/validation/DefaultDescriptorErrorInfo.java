package weblogic.j2ee.validation;

public abstract class DefaultDescriptorErrorInfo implements IDescriptorErrorInfo {
   private Object m_key;
   private Object[] m_elementErrorKeys = new Object[1];
   private String[] m_elements = new String[1];

   public DefaultDescriptorErrorInfo(String element, Object key, Object elementErrorKey) {
      this.m_elements[0] = element;
      this.m_key = key;
      this.m_elementErrorKeys[0] = elementErrorKey;
   }

   public DefaultDescriptorErrorInfo(String[] elements, Object key, Object[] elementErrorKeys) {
      this.m_elements = elements;
      this.m_key = key;
      this.m_elementErrorKeys = elementErrorKeys;
   }

   public DefaultDescriptorErrorInfo(String[] elements, Object key, Object elementErrorKey) {
      this.m_elements = elements;
      this.m_key = key;
      this.m_elementErrorKeys[0] = elementErrorKey;
   }

   public Object getTopLevelSearchKey() {
      return this.m_key;
   }

   public Object[] getElementErrorKeys() {
      return this.m_elementErrorKeys;
   }

   public String[] getElementTypes() {
      return this.m_elements;
   }
}
