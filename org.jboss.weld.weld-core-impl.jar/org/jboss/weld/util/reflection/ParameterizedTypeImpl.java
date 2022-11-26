package org.jboss.weld.util.reflection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType, Serializable {
   private static final long serialVersionUID = -3005183010706452884L;
   private final Type[] actualTypeArguments;
   private final Type rawType;
   private final Type ownerType;

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public ParameterizedTypeImpl(Type rawType, Type... actualTypeArguments) {
      this(rawType, actualTypeArguments, (Type)null);
   }

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public ParameterizedTypeImpl(Type rawType, Type[] actualTypeArguments, Type ownerType) {
      this.actualTypeArguments = actualTypeArguments;
      this.rawType = rawType;
      this.ownerType = ownerType;
   }

   public Type[] getActualTypeArguments() {
      return (Type[])Arrays.copyOf(this.actualTypeArguments, this.actualTypeArguments.length);
   }

   public Type getOwnerType() {
      return this.ownerType;
   }

   public Type getRawType() {
      return this.rawType;
   }

   public int hashCode() {
      return Arrays.hashCode(this.actualTypeArguments) ^ (this.ownerType == null ? 0 : this.ownerType.hashCode()) ^ (this.rawType == null ? 0 : this.rawType.hashCode());
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof ParameterizedType)) {
         return false;
      } else {
         boolean var10000;
         label39: {
            ParameterizedType that = (ParameterizedType)obj;
            Type thatOwnerType = that.getOwnerType();
            Type thatRawType = that.getRawType();
            if (this.ownerType == null) {
               if (thatOwnerType != null) {
                  break label39;
               }
            } else if (!this.ownerType.equals(thatOwnerType)) {
               break label39;
            }

            if (this.rawType == null) {
               if (thatRawType != null) {
                  break label39;
               }
            } else if (!this.rawType.equals(thatRawType)) {
               break label39;
            }

            if (Arrays.equals(this.actualTypeArguments, that.getActualTypeArguments())) {
               var10000 = true;
               return var10000;
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.rawType);
      if (this.actualTypeArguments.length > 0) {
         sb.append("<");
         Type[] var2 = this.actualTypeArguments;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Type actualType = var2[var4];
            sb.append(actualType);
            sb.append(",");
         }

         sb.delete(sb.length() - 1, sb.length());
         sb.append(">");
      }

      return sb.toString();
   }
}
