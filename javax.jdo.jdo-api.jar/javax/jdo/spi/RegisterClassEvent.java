package javax.jdo.spi;

import java.util.EventObject;

public class RegisterClassEvent extends EventObject {
   protected Class pcClass;
   protected String[] fieldNames;
   protected Class[] fieldTypes;
   protected byte[] fieldFlags;
   protected Class persistenceCapableSuperclass;

   public RegisterClassEvent(JDOImplHelper helper, Class registeredClass, String[] fieldNames, Class[] fieldTypes, byte[] fieldFlags, Class persistenceCapableSuperclass) {
      super(helper);
      this.pcClass = registeredClass;
      this.fieldNames = fieldNames;
      this.fieldTypes = fieldTypes;
      this.fieldFlags = fieldFlags;
      this.persistenceCapableSuperclass = persistenceCapableSuperclass;
   }

   public Class getRegisteredClass() {
      return this.pcClass;
   }

   public String[] getFieldNames() {
      return this.fieldNames;
   }

   public Class[] getFieldTypes() {
      return this.fieldTypes;
   }

   public byte[] getFieldFlags() {
      return this.fieldFlags;
   }

   public Class getPersistenceCapableSuperclass() {
      return this.persistenceCapableSuperclass;
   }
}
