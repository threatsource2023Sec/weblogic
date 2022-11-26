package serp.bytecode;

import serp.bytecode.visitor.BCVisitor;

public class IfInstruction extends JumpInstruction {
   IfInstruction(Code owner, int opcode) {
      super(owner, opcode);
   }

   public int getStackChange() {
      switch (this.getOpcode()) {
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 198:
         case 199:
            return -1;
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
            return -2;
         case 167:
         case 168:
         case 169:
         case 170:
         case 171:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         case 191:
         case 192:
         case 193:
         case 194:
         case 195:
         case 196:
         case 197:
         default:
            return super.getStackChange();
      }
   }

   int getLength() {
      return super.getLength() + 2;
   }

   public String getTypeName() {
      switch (this.getOpcode()) {
         case 165:
         case 166:
         case 198:
         case 199:
            return "java.lang.Object";
         default:
            return "I";
      }
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterIfInstruction(this);
      visit.exitIfInstruction(this);
   }
}
