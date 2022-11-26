package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.List;

public class NewMemberClassTypeMunger extends ResolvedTypeMunger {
   private UnresolvedType targetType;
   private String memberTypeName;
   private int version = 1;

   public NewMemberClassTypeMunger(UnresolvedType targetType, String memberTypeName) {
      super(ResolvedTypeMunger.InnerClass, (ResolvedMember)null);
      this.targetType = targetType;
      this.memberTypeName = memberTypeName;
   }

   public void write(CompressingDataOutputStream stream) throws IOException {
      this.kind.write(stream);
      stream.writeInt(this.version);
      this.targetType.write(stream);
      stream.writeUTF(this.memberTypeName);
      this.writeSourceLocation(stream);
      this.writeOutTypeAliases(stream);
   }

   public static ResolvedTypeMunger readInnerClass(VersionedDataInputStream stream, ISourceContext context) throws IOException {
      stream.readInt();
      UnresolvedType targetType = UnresolvedType.read(stream);
      String memberTypeName = stream.readUTF();
      ISourceLocation sourceLocation = readSourceLocation(stream);
      List typeVarAliases = readInTypeAliases(stream);
      NewMemberClassTypeMunger newInstance = new NewMemberClassTypeMunger(targetType, memberTypeName);
      newInstance.setTypeVariableAliases(typeVarAliases);
      newInstance.setSourceLocation(sourceLocation);
      return newInstance;
   }

   public UnresolvedType getTargetType() {
      return this.targetType;
   }

   public UnresolvedType getDeclaringType() {
      return this.targetType;
   }

   public String getMemberTypeName() {
      return this.memberTypeName;
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.kind.hashCode();
      result = 37 * result + this.memberTypeName.hashCode();
      result = 37 * result + this.targetType.hashCode();
      result = 37 * result + (this.typeVariableAliases == null ? 0 : this.typeVariableAliases.hashCode());
      return result;
   }

   public boolean equals(Object other) {
      if (!(other instanceof NewMemberClassTypeMunger)) {
         return false;
      } else {
         boolean var10000;
         label42: {
            label31: {
               NewMemberClassTypeMunger o = (NewMemberClassTypeMunger)other;
               if (this.kind == null) {
                  if (o.kind != null) {
                     break label31;
                  }
               } else if (!this.kind.equals(o.kind)) {
                  break label31;
               }

               if (this.memberTypeName.equals(o.memberTypeName) && this.targetType.equals(o.targetType)) {
                  if (this.typeVariableAliases == null) {
                     if (o.typeVariableAliases == null) {
                        break label42;
                     }
                  } else if (this.typeVariableAliases.equals(o.typeVariableAliases)) {
                     break label42;
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }
}
