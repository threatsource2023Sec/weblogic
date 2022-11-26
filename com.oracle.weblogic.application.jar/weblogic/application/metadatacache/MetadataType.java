package weblogic.application.metadatacache;

import java.io.Serializable;

public final class MetadataType implements Serializable {
   private static final long serialVersionUID = -7070404227431048765L;
   public static final MetadataType TLD = new MetadataType(".tlds");
   public static final MetadataType FACE_BEANS = new MetadataType(".faces");
   public static final MetadataType TAG_HANDLERS = new MetadataType(".taghandlers");
   public static final MetadataType TAG_LISTENERS = new MetadataType(".taglisteners");
   public static final MetadataType CLASSLEVEL_INFOS = new MetadataType(".classinfos", true);
   private final String name;
   private final boolean weakRef;

   private MetadataType(String name) {
      this(name, false);
   }

   private MetadataType(String name, boolean weakRef) {
      this.name = name;
      this.weakRef = weakRef;
   }

   public String getName() {
      return this.name;
   }

   public boolean isWeakRef() {
      return this.weakRef;
   }

   public String toString() {
      return this.name;
   }
}
