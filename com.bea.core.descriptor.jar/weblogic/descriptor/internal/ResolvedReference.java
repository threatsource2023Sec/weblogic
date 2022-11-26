package weblogic.descriptor.internal;

import java.lang.reflect.Array;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.Reference;
import weblogic.diagnostics.debug.DebugLogger;

public abstract class ResolvedReference implements Reference {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private final AbstractDescriptorBean parent;
   private final int propIndex;
   private final AbstractDescriptorBean reference;

   public ResolvedReference(AbstractDescriptorBean parent, int propIndex, AbstractDescriptorBean ref) {
      this.parent = parent;
      this.propIndex = propIndex;
      this.reference = ref;
   }

   public DescriptorBean getBean() {
      return this.parent;
   }

   public String getPropertyName() {
      return this.parent._getQualifiedName(this.propIndex);
   }

   protected abstract Object getPropertyValue();

   boolean isValid() {
      boolean debugEnabled = debug.isDebugEnabled();
      Object value = this.getPropertyValue();
      if (value == null) {
         if (debugEnabled) {
            debug.debug("ref " + this.reference + " not valid");
         }

         return false;
      } else {
         Class returnType = value.getClass();
         if (returnType.isArray()) {
            int len = Array.getLength(value);
            if (debugEnabled) {
               debug.debug("[ResolvedReference] Length of returned array " + len);
            }

            for(int i = 0; i < len; ++i) {
               Object val = Array.get(value, i);
               if (this.reference.equals(val)) {
                  if (debugEnabled) {
                     debug.debug("[ResolvedReference] ref " + this.reference + " is valid ");
                  }

                  return true;
               }
            }

            if (debugEnabled) {
               debug.debug("[ResolvedReference] ref " + this.reference + " is INVALID ");
            }

            return false;
         } else {
            boolean ret = this.reference.equals(value);
            if (debugEnabled) {
               debug.debug("[ResolvedReference] ref " + this.reference + " is " + (ret ? "valid " : " INVALID"));
            }

            return ret;
         }
      }
   }

   public int hashCode() {
      return this.parent.hashCode() ^ this.propIndex ^ this.reference.hashCode();
   }

   public boolean equals(Object o) {
      boolean equals;
      if (!(o instanceof ResolvedReference)) {
         equals = false;
      } else {
         ResolvedReference other = (ResolvedReference)o;
         if (this.parent != other.parent) {
            equals = false;
         } else if (this.propIndex != other.propIndex) {
            equals = false;
         } else if (this.reference != other.reference) {
            equals = false;
         } else {
            equals = true;
         }
      }

      return equals;
   }

   public String toString() {
      return this.reference._getKey() + " by " + this.parent._getQualifiedName(this.propIndex);
   }

   int getPropIndex() {
      return this.propIndex;
   }
}
