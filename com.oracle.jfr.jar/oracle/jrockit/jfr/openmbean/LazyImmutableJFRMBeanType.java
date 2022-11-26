package oracle.jrockit.jfr.openmbean;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import sun.management.LazyCompositeData;

abstract class LazyImmutableJFRMBeanType extends JFRMBeanType {
   private final Class type;

   LazyImmutableJFRMBeanType(Class type, String name, String desc, Member... members) throws OpenDataException {
      super(name, desc, members);
      this.type = type;
   }

   LazyImmutableJFRMBeanType(Class type, String name, String desc, String[] names, String[] descriptions, OpenType[] types) throws OpenDataException {
      super(name, desc, names, descriptions, types);
      this.type = type;
   }

   public final CompositeData toCompositeTypeData(Object t) throws OpenDataException {
      return new ImmutableCompositeData(t);
   }

   public final Object toJavaTypeData(CompositeData d) throws OpenDataException {
      if (ImmutableCompositeData.class.isInstance(d)) {
         try {
            return this.type.cast(((ImmutableCompositeData)d).data);
         } catch (ClassCastException var3) {
         }
      }

      return this.toJavaTypeDataLazy(d);
   }

   protected abstract CompositeData toCompositeTypeDataLazy(Object var1) throws OpenDataException;

   protected abstract Object toJavaTypeDataLazy(CompositeData var1) throws OpenDataException;

   private final class ImmutableCompositeData extends LazyCompositeData {
      private final Object data;

      public ImmutableCompositeData(Object data) {
         this.data = data;
      }

      protected CompositeData getCompositeData() {
         try {
            return LazyImmutableJFRMBeanType.this.toCompositeTypeDataLazy(this.data);
         } catch (OpenDataException var2) {
            throw (InternalError)(new InternalError()).initCause(var2);
         }
      }

      public CompositeType getCompositeType() {
         return LazyImmutableJFRMBeanType.this.getType();
      }
   }
}
