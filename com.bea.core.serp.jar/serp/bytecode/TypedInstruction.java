package serp.bytecode;

import java.util.HashSet;
import java.util.Set;
import serp.util.Strings;

public abstract class TypedInstruction extends Instruction {
   private static final Set _opcodeTypes = new HashSet();

   TypedInstruction(Code owner) {
      super(owner);
   }

   TypedInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   String mapType(String type, Class[][] mappings, boolean demote) {
      if (type == null) {
         return null;
      } else {
         type = this.getProject().getNameCache().getExternalForm(type, false);
         if (!_opcodeTypes.contains(type) && demote) {
            type = Object.class.getName();
         }

         if (mappings != null) {
            for(int i = 0; i < mappings.length; ++i) {
               if (mappings[i][0].getName().equals(type)) {
                  type = mappings[i][1].getName();
               }
            }
         }

         return type;
      }
   }

   public abstract String getTypeName();

   public Class getType() {
      String type = this.getTypeName();
      return type == null ? null : Strings.toClass(type, this.getClassLoader());
   }

   public BCClass getTypeBC() {
      String type = this.getTypeName();
      return type == null ? null : this.getProject().loadClass(type, this.getClassLoader());
   }

   public abstract TypedInstruction setType(String var1);

   public TypedInstruction setType(Class type) {
      return type == null ? this.setType((String)null) : this.setType(type.getName());
   }

   public TypedInstruction setType(BCClass type) {
      return type == null ? this.setType((String)null) : this.setType(type.getName());
   }

   static {
      _opcodeTypes.add(Integer.TYPE.getName());
      _opcodeTypes.add(Long.TYPE.getName());
      _opcodeTypes.add(Float.TYPE.getName());
      _opcodeTypes.add(Double.TYPE.getName());
      _opcodeTypes.add(Object.class.getName());
      _opcodeTypes.add(Byte.TYPE.getName());
      _opcodeTypes.add(Character.TYPE.getName());
      _opcodeTypes.add(Short.TYPE.getName());
      _opcodeTypes.add(Boolean.TYPE.getName());
      _opcodeTypes.add(Void.TYPE.getName());
   }
}
