package org.jboss.weld.annotated.slim;

import java.lang.reflect.Type;
import java.util.Objects;
import org.jboss.weld.annotated.Identifier;
import org.jboss.weld.util.Types;

public class AnnotatedTypeIdentifier implements Identifier {
   public static final String NULL_BDA_ID = AnnotatedTypeIdentifier.class.getName() + ".null";
   public static final String SYNTHETIC_ANNOTATION_SUFFIX = "syntheticAnnotation";
   private static final long serialVersionUID = -264184070652700144L;
   private final String contextId;
   private final String bdaId;
   private final String className;
   private final String suffix;
   private final boolean modified;
   private final int hashCode;

   public static AnnotatedTypeIdentifier forBackedAnnotatedType(String contextId, Class javaClass, Type type, String bdaId) {
      return forBackedAnnotatedType(contextId, javaClass, type, bdaId, (String)null);
   }

   public static AnnotatedTypeIdentifier forBackedAnnotatedType(String contextId, Class javaClass, Type type, String bdaId, String suffix) {
      return new AnnotatedTypeIdentifier(contextId, bdaId, javaClass.getName(), suffix != null ? suffix : getTypeId(type), false);
   }

   public static AnnotatedTypeIdentifier forModifiedAnnotatedType(AnnotatedTypeIdentifier originalIdentifier) {
      if (originalIdentifier.modified) {
         throw new IllegalArgumentException("Cannot create a modified identifier for an already modified identifier.");
      } else {
         return new AnnotatedTypeIdentifier(originalIdentifier.contextId, originalIdentifier.bdaId, originalIdentifier.className, originalIdentifier.suffix, true);
      }
   }

   public static AnnotatedTypeIdentifier of(String contextId, String bdaId, String className, String suffix, boolean modified) {
      return new AnnotatedTypeIdentifier(contextId, bdaId, className, suffix, modified);
   }

   private AnnotatedTypeIdentifier(String contextId, String bdaId, String className, String suffix, boolean modified) {
      this.contextId = contextId;
      this.bdaId = bdaId;
      this.className = className;
      this.suffix = suffix;
      this.modified = modified;
      this.hashCode = Objects.hash(new Object[]{contextId, bdaId, className, suffix, modified});
   }

   private static String getTypeId(Type type) {
      return type != null && !(type instanceof Class) ? Types.getTypeId(type) : null;
   }

   public String getContextId() {
      return this.contextId;
   }

   public String getBdaId() {
      return this.bdaId;
   }

   public String getClassName() {
      return this.className;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public boolean isModified() {
      return this.modified;
   }

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof AnnotatedTypeIdentifier)) {
         return false;
      } else {
         AnnotatedTypeIdentifier they = (AnnotatedTypeIdentifier)obj;
         return Objects.equals(this.bdaId, they.bdaId) && Objects.equals(this.className, they.className) && Objects.equals(this.suffix, they.suffix) && Objects.equals(this.modified, they.modified) && Objects.equals(this.contextId, they.contextId);
      }
   }

   public String asString() {
      StringBuilder builder = new StringBuilder();
      builder.append(this.contextId);
      builder.append("|");
      builder.append(this.bdaId != null ? (this.bdaId.startsWith(this.contextId) ? this.bdaId.substring(this.contextId.length()) : this.bdaId) : this.bdaId);
      builder.append("|");
      builder.append(this.className);
      builder.append("|");
      builder.append(this.suffix != null ? (this.suffix.startsWith(this.className) ? this.suffix.substring(this.className.length()) : this.suffix) : this.suffix);
      builder.append("|");
      builder.append(this.modified ? 1 : 0);
      return builder.toString();
   }

   public String toString() {
      return "AnnotatedTypeIdentifier [contextId=" + this.contextId + ", bdaId=" + this.bdaId + ", className=" + this.className + ", suffix=" + this.suffix + ", modified=" + this.modified + "]";
   }
}
