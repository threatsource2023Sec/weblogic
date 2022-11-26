package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.ContentType;
import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.InvalidValueException;
import com.oracle.jrockit.jfr.Transition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ValueDescriptor {
   private final String id;
   private final String name;
   private final String description;
   private final String relationKey;
   private final Field field;
   private final Class valueType;
   private final Transition transition;
   private final ContentTypeImpl contentType;
   private final DataType dataType;
   private final int innerType;
   private final String constantPool;
   private static final HashMap types = new HashMap();

   public String getRelationKey() {
      return this.relationKey;
   }

   public ContentTypeImpl getContentTypeImpl() {
      return this.contentType;
   }

   public ContentType getContentType() {
      return this.contentType.getMapped();
   }

   public DataType getDataType() {
      return this.dataType;
   }

   public int getContentTypeOrdinal() {
      return this.contentType.getOrdinal();
   }

   public int getDataTypeOrdinal() {
      return this.dataType.ordinal();
   }

   public String getConstantPool() {
      return this.constantPool;
   }

   public ValueDescriptor(String id, String name, String description, String relationKey, ContentType type, Transition transition, String constantPool, Field field, Class valueType) throws InvalidValueException {
      if (id == null || id.length() == 0) {
         if (field == null) {
            throw new NullPointerException("id");
         }

         id = field.getName();
      }

      if (name == null || name.length() == 0) {
         name = id;
      }

      if (relationKey != null && relationKey.length() == 0) {
         relationKey = null;
      }

      this.id = id;
      this.name = name;
      this.description = description;
      this.field = field;
      this.transition = transition;
      this.valueType = valueType;
      this.relationKey = relationKey;
      this.constantPool = constantPool;
      if (relationKey != null) {
         try {
            new URI(relationKey);
         } catch (URISyntaxException var14) {
            throw new InvalidValueException("Illegal relation key : " + relationKey, var14);
         }
      }

      if (!valueType.isPrimitive() && valueType != Class.class && valueType != Thread.class && valueType != String.class) {
         throw new InvalidValueException("Unsupported value type " + valueType);
      } else {
         DataType dt = (DataType)types.get(valueType);

         assert dt != null || !valueType.isPrimitive();

         ContentTypeImpl ct = null;
         if (!valueType.isPrimitive()) {
            ct = ContentTypeImpl.forClass(valueType);
            if (type != ContentType.None && type != ct.getMapped()) {
               throw new InvalidValueException("Illegal content type " + type);
            }

            type = ct.getMapped();
            DataType cdt = ct.getType();
            if (cdt != null) {
               dt = cdt;
            }
         } else if (type != ContentType.None) {
            ct = ContentTypeImpl.forContentType(type);
         } else {
            ct = ContentTypeImpl.forClass(valueType);
         }

         assert ct != null;

         assert dt != null;

         if (!ct.isCompatible(dt)) {
            throw new InvalidValueException("Field " + name + ", incompatible content type: " + ct);
         } else {
            this.contentType = ct;
            this.dataType = dt;
            this.innerType = 0;
         }
      }
   }

   public ValueDescriptor(String id, String name, String descriptor, String relationKey, Transition transitionType, DataType dataType, ContentTypeImpl contentType, int innerType, String constantPool, Class valueType) throws InvalidValueException {
      this.id = id;
      this.name = name;
      this.description = descriptor;
      this.transition = transitionType;
      this.dataType = dataType;
      this.contentType = contentType;
      this.field = null;
      this.valueType = valueType;
      this.innerType = innerType;
      this.relationKey = relationKey;
      this.constantPool = constantPool;
   }

   public ValueDescriptor(ValueDescriptor other) {
      this.id = other.id;
      this.innerType = other.innerType;
      this.contentType = other.contentType;
      this.dataType = other.dataType;
      this.description = other.description;
      this.name = other.name;
      this.field = other.field;
      this.valueType = other.valueType;
      this.transition = other.transition;
      this.relationKey = other.relationKey;
      this.constantPool = other.constantPool;
   }

   public ValueDescriptor(ValueDefinition d, UseConstantPool p, Field f) throws InvalidValueException {
      this(d.id(), d.name(), d.description(), d.relationKey(), d.contentType(), d.transition(), p != null ? p.name() : null, f, f.getType());
      if (!d.contentType().isAllowedForUserValue()) {
         throw new InvalidValueException("Illegal content type for user event : " + d);
      } else {
         this.field.setAccessible(true);
      }
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public Transition getTransition() {
      return this.transition;
   }

   public Field getField() {
      return this.field;
   }

   public Class getValueType() {
      return this.valueType;
   }

   public int getInnerType() {
      return this.innerType;
   }

   public Object loadValue(Object reciever) throws IllegalArgumentException {
      try {
         return this.field.get(reciever);
      } catch (IllegalAccessException var3) {
         throw new InternalError(var3.getMessage());
      }
   }

   public void setValue(Object reciever, Object value) throws IllegalArgumentException {
      try {
         this.field.set(reciever, value);
      } catch (IllegalAccessException var4) {
         throw new InternalError(var4.getMessage());
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(this.id).append(" '").append(this.name).append("' ").append(this.dataType).append(' ').append(this.contentType);
      return buf.toString();
   }

   static {
      DataType[] arr$ = DataType.values();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DataType t = arr$[i$];
         if (t.isPrimary()) {
            types.put(t.getJavaType(), t);
         }
      }

      types.put(Character.class, DataType.U2);
   }
}
