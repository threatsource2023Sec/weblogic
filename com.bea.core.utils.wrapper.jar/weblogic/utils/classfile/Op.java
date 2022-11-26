package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import weblogic.utils.classfile.ops.BipushOp;
import weblogic.utils.classfile.ops.BranchOp;
import weblogic.utils.classfile.ops.ConstPoolOp;
import weblogic.utils.classfile.ops.IndexAndConstOp;
import weblogic.utils.classfile.ops.InvokeInterfaceOp;
import weblogic.utils.classfile.ops.LdcOp;
import weblogic.utils.classfile.ops.LocalVarOp;
import weblogic.utils.classfile.ops.LookupswitchOp;
import weblogic.utils.classfile.ops.MultinewarrayOp;
import weblogic.utils.classfile.ops.NewarrayOp;
import weblogic.utils.classfile.ops.SipushOp;
import weblogic.utils.classfile.ops.TableswitchOp;
import weblogic.utils.classfile.ops.WideOp;

public class Op {
   static final boolean debug = false;
   public static final int[] operand_bytes = new int[256];
   public static final int VARIABLE = -1;
   protected int op_code;
   private int pc;
   public static final int AALOAD = 50;
   public static final int AASTORE = 83;
   public static final int ACONST_NULL = 1;
   public static final int ALOAD = 25;
   public static final int ALOAD_0 = 42;
   public static final int ALOAD_1 = 43;
   public static final int ALOAD_2 = 44;
   public static final int ALOAD_3 = 45;
   public static final int ANEWARRAY = 189;
   public static final int ARETURN = 176;
   public static final int ARRAYLENGTH = 190;
   public static final int ASTORE = 58;
   public static final int ASTORE_0 = 75;
   public static final int ASTORE_1 = 76;
   public static final int ASTORE_2 = 77;
   public static final int ASTORE_3 = 78;
   public static final int ATHROW = 191;
   public static final int BALOAD = 51;
   public static final int BASTORE = 84;
   public static final int BIPUSH = 16;
   public static final int CALOAD = 52;
   public static final int CASTORE = 85;
   public static final int CHECKCAST = 192;
   public static final int D2F = 144;
   public static final int D2I = 142;
   public static final int D2L = 143;
   public static final int DADD = 99;
   public static final int DALOAD = 49;
   public static final int DASTORE = 82;
   public static final int DCMPG = 152;
   public static final int DCMPL = 151;
   public static final int DCONST_0 = 14;
   public static final int DCONST_1 = 15;
   public static final int DDIV = 111;
   public static final int DLOAD = 24;
   public static final int DLOAD_0 = 38;
   public static final int DLOAD_1 = 39;
   public static final int DLOAD_2 = 40;
   public static final int DLOAD_3 = 41;
   public static final int DMUL = 107;
   public static final int DNEG = 119;
   public static final int DREM = 115;
   public static final int DRETURN = 175;
   public static final int DSTORE = 57;
   public static final int DSTORE_0 = 71;
   public static final int DSTORE_1 = 72;
   public static final int DSTORE_2 = 73;
   public static final int DSTORE_3 = 74;
   public static final int DSUB = 103;
   public static final int DUP = 89;
   public static final int DUP2 = 92;
   public static final int DUP2_X1 = 93;
   public static final int DUP2_X2 = 94;
   public static final int DUP_X1 = 90;
   public static final int DUP_X2 = 91;
   public static final int F2D = 141;
   public static final int F2I = 139;
   public static final int F2L = 140;
   public static final int FADD = 98;
   public static final int FALOAD = 48;
   public static final int FASTORE = 81;
   public static final int FCMPG = 150;
   public static final int FCMPL = 149;
   public static final int FCONST_0 = 11;
   public static final int FCONST_1 = 12;
   public static final int FCONST_2 = 13;
   public static final int FDIV = 110;
   public static final int FLOAD = 23;
   public static final int FLOAD_0 = 34;
   public static final int FLOAD_1 = 35;
   public static final int FLOAD_2 = 36;
   public static final int FLOAD_3 = 37;
   public static final int FMUL = 106;
   public static final int FNEG = 118;
   public static final int FREM = 114;
   public static final int FRETURN = 174;
   public static final int FSTORE = 56;
   public static final int FSTORE_0 = 67;
   public static final int FSTORE_1 = 68;
   public static final int FSTORE_2 = 69;
   public static final int FSTORE_3 = 70;
   public static final int FSUB = 102;
   public static final int GETFIELD = 180;
   public static final int GETSTATIC = 178;
   public static final int GOTO = 167;
   public static final int GOTO_W = 200;
   public static final int I2B = 145;
   public static final int I2C = 146;
   public static final int I2D = 135;
   public static final int I2F = 134;
   public static final int I2L = 133;
   public static final int I2S = 147;
   public static final int IADD = 96;
   public static final int IALOAD = 46;
   public static final int IAND = 126;
   public static final int IASTORE = 79;
   public static final int ICONST_0 = 3;
   public static final int ICONST_1 = 4;
   public static final int ICONST_2 = 5;
   public static final int ICONST_3 = 6;
   public static final int ICONST_4 = 7;
   public static final int ICONST_5 = 8;
   public static final int ICONST_M1 = 2;
   public static final int IDIV = 108;
   public static final int IF_ACMPEQ = 165;
   public static final int IF_ACMPNE = 166;
   public static final int IF_ICMPEQ = 159;
   public static final int IF_ICMPGE = 162;
   public static final int IF_ICMPGT = 163;
   public static final int IF_ICMPLE = 164;
   public static final int IF_ICMPLT = 161;
   public static final int IF_ICMPNE = 160;
   public static final int IFEQ = 153;
   public static final int IFGE = 156;
   public static final int IFGT = 157;
   public static final int IFLE = 158;
   public static final int IFLT = 155;
   public static final int IFNE = 154;
   public static final int IFNONULL = 199;
   public static final int IFNULL = 198;
   public static final int IINC = 132;
   public static final int ILOAD = 21;
   public static final int ILOAD_0 = 26;
   public static final int ILOAD_1 = 27;
   public static final int ILOAD_2 = 28;
   public static final int ILOAD_3 = 29;
   public static final int IMUL = 104;
   public static final int INEG = 116;
   public static final int INSTANCEOF = 193;
   public static final int INVOKEINTERFACE = 185;
   public static final int INVOKESPECIAL = 183;
   public static final int INVOKESTATIC = 184;
   public static final int INVOKEVIRTUAL = 182;
   public static final int IOR = 128;
   public static final int IREM = 112;
   public static final int IRETURN = 172;
   public static final int ISHL = 120;
   public static final int ISHR = 122;
   public static final int ISTORE = 54;
   public static final int ISTORE_0 = 59;
   public static final int ISTORE_1 = 60;
   public static final int ISTORE_2 = 61;
   public static final int ISTORE_3 = 62;
   public static final int ISUB = 100;
   public static final int IUSHR = 124;
   public static final int IXOR = 130;
   public static final int JSR = 168;
   public static final int JSR_W = 201;
   public static final int L2D = 138;
   public static final int L2F = 137;
   public static final int L2I = 136;
   public static final int LADD = 97;
   public static final int LALOAD = 47;
   public static final int LAND = 127;
   public static final int LASTORE = 80;
   public static final int LCMP = 148;
   public static final int LCONST_0 = 9;
   public static final int LCONST_1 = 10;
   public static final int LDC = 18;
   public static final int LDC2_W = 20;
   public static final int LDC_W = 19;
   public static final int LDIV = 109;
   public static final int LLOAD = 22;
   public static final int LLOAD_0 = 30;
   public static final int LLOAD_1 = 31;
   public static final int LLOAD_2 = 32;
   public static final int LLOAD_3 = 33;
   public static final int LMUL = 105;
   public static final int LNEG = 117;
   public static final int LOOKUPSWITCH = 171;
   public static final int LOR = 129;
   public static final int LREM = 113;
   public static final int LRETURN = 173;
   public static final int LSHL = 121;
   public static final int LSHR = 123;
   public static final int LSTORE = 55;
   public static final int LSTORE_0 = 63;
   public static final int LSTORE_1 = 64;
   public static final int LSTORE_2 = 65;
   public static final int LSTORE_3 = 66;
   public static final int LSUB = 101;
   public static final int LUSHR = 125;
   public static final int LXOR = 131;
   public static final int MONITORENTER = 194;
   public static final int MONITOREXIT = 195;
   public static final int MULTIANEWARRAY = 197;
   public static final int NEW = 187;
   public static final int NEWARRAY = 188;
   public static final int NOP = 0;
   public static final int POP = 87;
   public static final int POP2 = 88;
   public static final int PUTFIELD = 181;
   public static final int PUTSTATIC = 179;
   public static final int RET = 169;
   public static final int RETURN = 177;
   public static final int SALOAD = 53;
   public static final int SASTORE = 86;
   public static final int SIPUSH = 17;
   public static final int SWAP = 95;
   public static final int TABLESWITCH = 170;
   public static final int WIDE = 196;
   public static final int XXXUNUSEDXXX = 186;

   public Op(int op_code) {
      this.op_code = op_code;
   }

   void setPC(int pc) {
      this.pc = pc;
   }

   int getPC() {
      return this.pc;
   }

   public void read(DataInput in) throws IOException {
   }

   public void write(DataOutput out) throws IOException {
      out.writeByte(this.op_code);
   }

   public void dump(PrintStream out) {
      out.println(this.toString());
   }

   public String toString() {
      return Op.ByteCodeNames.names[this.op_code];
   }

   public int length() {
      return 1 + operand_bytes[this.op_code];
   }

   public static Op getOp(DataInput in, Bytecodes code, int pc) throws IOException {
      int op_code = in.readUnsignedByte();
      Object op;
      switch (op_code) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 70:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         case 115:
         case 116:
         case 117:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 124:
         case 125:
         case 126:
         case 127:
         case 128:
         case 129:
         case 130:
         case 131:
         case 133:
         case 134:
         case 135:
         case 136:
         case 137:
         case 138:
         case 139:
         case 140:
         case 141:
         case 142:
         case 143:
         case 144:
         case 145:
         case 146:
         case 147:
         case 148:
         case 149:
         case 150:
         case 151:
         case 152:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 186:
         case 190:
         case 191:
         case 194:
         case 195:
            op = new Op(op_code);
            break;
         case 16:
            op = new BipushOp(op_code);
            break;
         case 17:
            op = new SipushOp(op_code);
            break;
         case 18:
         case 19:
            op = new LdcOp(op_code, code.constant_pool);
            break;
         case 20:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 187:
         case 189:
         case 192:
         case 193:
            op = new ConstPoolOp(op_code, code.constant_pool);
            break;
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 169:
            op = new LocalVarOp(op_code);
            break;
         case 132:
            op = new IndexAndConstOp(op_code);
            break;
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 198:
         case 199:
         case 200:
         case 201:
            op = new BranchOp(op_code);
            break;
         case 170:
            op = new TableswitchOp(op_code, pc);
            break;
         case 171:
            op = new LookupswitchOp(op_code, pc);
            break;
         case 185:
            op = new InvokeInterfaceOp(op_code, code.constant_pool);
            break;
         case 188:
            op = new NewarrayOp(op_code);
            break;
         case 196:
            op = new WideOp(op_code);
            break;
         case 197:
            op = new MultinewarrayOp(op_code, code.constant_pool);
            break;
         default:
            throw new IOException("Unrecognized op_code: " + Integer.toHexString(op_code));
      }

      ((Op)op).read(in);
      return (Op)op;
   }

   static {
      operand_bytes[50] = 0;
      operand_bytes[83] = 0;
      operand_bytes[1] = 0;
      operand_bytes[25] = 1;
      operand_bytes[42] = 0;
      operand_bytes[43] = 0;
      operand_bytes[44] = 0;
      operand_bytes[45] = 0;
      operand_bytes[189] = 2;
      operand_bytes[176] = 0;
      operand_bytes[190] = 0;
      operand_bytes[58] = 1;
      operand_bytes[75] = 0;
      operand_bytes[76] = 0;
      operand_bytes[77] = 0;
      operand_bytes[78] = 0;
      operand_bytes[191] = 0;
      operand_bytes[51] = 0;
      operand_bytes[84] = 0;
      operand_bytes[16] = 1;
      operand_bytes[52] = 0;
      operand_bytes[85] = 0;
      operand_bytes[192] = 2;
      operand_bytes[144] = 0;
      operand_bytes[142] = 0;
      operand_bytes[143] = 0;
      operand_bytes[99] = 0;
      operand_bytes[49] = 0;
      operand_bytes[82] = 0;
      operand_bytes[152] = 0;
      operand_bytes[151] = 0;
      operand_bytes[14] = 0;
      operand_bytes[15] = 0;
      operand_bytes[111] = 0;
      operand_bytes[24] = 1;
      operand_bytes[38] = 0;
      operand_bytes[39] = 0;
      operand_bytes[40] = 0;
      operand_bytes[41] = 0;
      operand_bytes[107] = 0;
      operand_bytes[119] = 0;
      operand_bytes[115] = 0;
      operand_bytes[175] = 0;
      operand_bytes[57] = 1;
      operand_bytes[71] = 0;
      operand_bytes[72] = 0;
      operand_bytes[73] = 0;
      operand_bytes[74] = 0;
      operand_bytes[103] = 0;
      operand_bytes[92] = 0;
      operand_bytes[93] = 0;
      operand_bytes[94] = 0;
      operand_bytes[89] = 0;
      operand_bytes[90] = 0;
      operand_bytes[91] = 0;
      operand_bytes[141] = 0;
      operand_bytes[139] = 0;
      operand_bytes[140] = 0;
      operand_bytes[98] = 0;
      operand_bytes[48] = 0;
      operand_bytes[81] = 0;
      operand_bytes[150] = 0;
      operand_bytes[149] = 0;
      operand_bytes[11] = 0;
      operand_bytes[12] = 0;
      operand_bytes[13] = 0;
      operand_bytes[110] = 0;
      operand_bytes[23] = 1;
      operand_bytes[34] = 0;
      operand_bytes[35] = 0;
      operand_bytes[36] = 0;
      operand_bytes[37] = 0;
      operand_bytes[106] = 0;
      operand_bytes[118] = 0;
      operand_bytes[114] = 0;
      operand_bytes[174] = 0;
      operand_bytes[56] = 1;
      operand_bytes[67] = 0;
      operand_bytes[68] = 0;
      operand_bytes[69] = 0;
      operand_bytes[70] = 0;
      operand_bytes[102] = 0;
      operand_bytes[180] = 2;
      operand_bytes[178] = 2;
      operand_bytes[167] = 2;
      operand_bytes[200] = 4;
      operand_bytes[145] = 0;
      operand_bytes[146] = 0;
      operand_bytes[135] = 0;
      operand_bytes[134] = 0;
      operand_bytes[133] = 0;
      operand_bytes[147] = 0;
      operand_bytes[96] = 0;
      operand_bytes[46] = 0;
      operand_bytes[126] = 0;
      operand_bytes[79] = 0;
      operand_bytes[3] = 0;
      operand_bytes[4] = 0;
      operand_bytes[5] = 0;
      operand_bytes[6] = 0;
      operand_bytes[7] = 0;
      operand_bytes[8] = 0;
      operand_bytes[2] = 0;
      operand_bytes[108] = 0;
      operand_bytes[153] = 2;
      operand_bytes[156] = 2;
      operand_bytes[157] = 2;
      operand_bytes[158] = 2;
      operand_bytes[155] = 2;
      operand_bytes[154] = 2;
      operand_bytes[199] = 2;
      operand_bytes[198] = 2;
      operand_bytes[165] = 2;
      operand_bytes[166] = 2;
      operand_bytes[159] = 2;
      operand_bytes[162] = 2;
      operand_bytes[163] = 2;
      operand_bytes[164] = 2;
      operand_bytes[161] = 2;
      operand_bytes[160] = 2;
      operand_bytes[132] = 2;
      operand_bytes[21] = 1;
      operand_bytes[26] = 0;
      operand_bytes[27] = 0;
      operand_bytes[28] = 0;
      operand_bytes[29] = 0;
      operand_bytes[104] = 0;
      operand_bytes[116] = 0;
      operand_bytes[193] = 2;
      operand_bytes[185] = 4;
      operand_bytes[183] = 2;
      operand_bytes[184] = 2;
      operand_bytes[182] = 2;
      operand_bytes[128] = 0;
      operand_bytes[112] = 0;
      operand_bytes[172] = 0;
      operand_bytes[120] = 0;
      operand_bytes[122] = 0;
      operand_bytes[54] = 1;
      operand_bytes[59] = 0;
      operand_bytes[60] = 0;
      operand_bytes[61] = 0;
      operand_bytes[62] = 0;
      operand_bytes[100] = 0;
      operand_bytes[124] = 0;
      operand_bytes[130] = 0;
      operand_bytes[168] = 2;
      operand_bytes[201] = 4;
      operand_bytes[138] = 0;
      operand_bytes[137] = 0;
      operand_bytes[136] = 0;
      operand_bytes[97] = 0;
      operand_bytes[47] = 0;
      operand_bytes[127] = 0;
      operand_bytes[80] = 0;
      operand_bytes[148] = 0;
      operand_bytes[9] = 0;
      operand_bytes[10] = 0;
      operand_bytes[20] = 2;
      operand_bytes[18] = 1;
      operand_bytes[19] = 2;
      operand_bytes[109] = 0;
      operand_bytes[22] = 1;
      operand_bytes[30] = 0;
      operand_bytes[31] = 0;
      operand_bytes[32] = 0;
      operand_bytes[33] = 0;
      operand_bytes[105] = 0;
      operand_bytes[117] = 0;
      operand_bytes[171] = -1;
      operand_bytes[129] = 0;
      operand_bytes[113] = 0;
      operand_bytes[173] = 0;
      operand_bytes[121] = 0;
      operand_bytes[123] = 0;
      operand_bytes[55] = 1;
      operand_bytes[63] = 0;
      operand_bytes[64] = 0;
      operand_bytes[65] = 0;
      operand_bytes[66] = 0;
      operand_bytes[101] = 0;
      operand_bytes[125] = 0;
      operand_bytes[131] = 0;
      operand_bytes[194] = 0;
      operand_bytes[195] = 0;
      operand_bytes[197] = 3;
      operand_bytes[188] = 1;
      operand_bytes[187] = 2;
      operand_bytes[0] = 0;
      operand_bytes[88] = 0;
      operand_bytes[87] = 0;
      operand_bytes[181] = 2;
      operand_bytes[179] = 2;
      operand_bytes[177] = 0;
      operand_bytes[169] = 1;
      operand_bytes[53] = 0;
      operand_bytes[86] = 0;
      operand_bytes[17] = 2;
      operand_bytes[95] = 0;
      operand_bytes[170] = -1;
      operand_bytes[196] = -1;
      operand_bytes[186] = 0;
   }

   private static final class ByteCodeNames {
      static final String[] names = new String[256];

      static {
         names[50] = "AALOAD";
         names[83] = "AASTORE";
         names[1] = "ACONST_NULL";
         names[25] = "ALOAD";
         names[42] = "ALOAD_0";
         names[43] = "ALOAD_1";
         names[44] = "ALOAD_2";
         names[45] = "ALOAD_3";
         names[189] = "ANEWARRAY";
         names[176] = "ARETURN";
         names[190] = "ARRAYLENGTH";
         names[58] = "ASTORE";
         names[75] = "ASTORE_0";
         names[76] = "ASTORE_1";
         names[77] = "ASTORE_2";
         names[78] = "ASTORE_3";
         names[191] = "ATHROW";
         names[51] = "BALOAD";
         names[84] = "BASTORE";
         names[16] = "BIPUSH";
         names[52] = "CALOAD";
         names[85] = "CASTORE";
         names[192] = "CHECKCAST";
         names[144] = "D2F";
         names[142] = "D2I";
         names[143] = "D2L";
         names[99] = "DADD";
         names[49] = "DALOAD";
         names[82] = "DASTORE";
         names[152] = "DCMPG";
         names[151] = "DCMPL";
         names[14] = "DCONST_0";
         names[15] = "DCONST_1";
         names[111] = "DDIV";
         names[24] = "DLOAD";
         names[38] = "DLOAD_0";
         names[39] = "DLOAD_1";
         names[40] = "DLOAD_2";
         names[41] = "DLOAD_3";
         names[107] = "DMUL";
         names[119] = "DNEG";
         names[115] = "DREM";
         names[175] = "DRETURN";
         names[57] = "DSTORE";
         names[71] = "DSTORE_0";
         names[72] = "DSTORE_1";
         names[73] = "DSTORE_2";
         names[74] = "DSTORE_3";
         names[103] = "DSUB";
         names[89] = "DUP";
         names[92] = "DUP2";
         names[93] = "DUP2_X1";
         names[94] = "DUP2_X2";
         names[90] = "DUP_X1";
         names[91] = "DUP_X2";
         names[141] = "F2D";
         names[139] = "F2I";
         names[140] = "F2L";
         names[98] = "FADD";
         names[48] = "FALOAD";
         names[81] = "FASTORE";
         names[150] = "FCMPG";
         names[149] = "FCMPL";
         names[11] = "FCONST_0";
         names[12] = "FCONST_1";
         names[13] = "FCONST_2";
         names[110] = "FDIV";
         names[23] = "FLOAD";
         names[34] = "FLOAD_0";
         names[35] = "FLOAD_1";
         names[36] = "FLOAD_2";
         names[37] = "FLOAD_3";
         names[106] = "FMUL";
         names[118] = "FNEG";
         names[114] = "FREM";
         names[174] = "FRETURN";
         names[56] = "FSTORE";
         names[67] = "FSTORE_0";
         names[68] = "FSTORE_1";
         names[69] = "FSTORE_2";
         names[70] = "FSTORE_3";
         names[102] = "FSUB";
         names[180] = "GETFIELD";
         names[178] = "GETSTATIC";
         names[167] = "GOTO";
         names[200] = "GOTO_W";
         names[145] = "I2B";
         names[146] = "I2C";
         names[135] = "I2D";
         names[134] = "I2F";
         names[133] = "I2L";
         names[147] = "I2S";
         names[96] = "IADD";
         names[46] = "IALOAD";
         names[126] = "IAND";
         names[79] = "IASTORE";
         names[3] = "ICONST_0";
         names[4] = "ICONST_1";
         names[5] = "ICONST_2";
         names[6] = "ICONST_3";
         names[7] = "ICONST_4";
         names[8] = "ICONST_5";
         names[2] = "ICONST_M1";
         names[108] = "IDIV";
         names[165] = "IF_ACMPEQ";
         names[166] = "IF_ACMPNE";
         names[159] = "IF_ICMPEQ";
         names[162] = "IF_ICMPGE";
         names[163] = "IF_ICMPGT";
         names[164] = "IF_ICMPLE";
         names[161] = "IF_ICMPLT";
         names[160] = "IF_ICMPNE";
         names[153] = "IFEQ";
         names[156] = "IFGE";
         names[157] = "IFGT";
         names[158] = "IFLE";
         names[155] = "IFLT";
         names[154] = "IFNE";
         names[199] = "IFNONULL";
         names[198] = "IFNULL";
         names[132] = "IINC";
         names[21] = "ILOAD";
         names[26] = "ILOAD_0";
         names[27] = "ILOAD_1";
         names[28] = "ILOAD_2";
         names[29] = "ILOAD_3";
         names[104] = "IMUL";
         names[116] = "INEG";
         names[193] = "INSTANCEOF";
         names[185] = "INVOKEINTERFACE";
         names[183] = "INVOKESPECIAL";
         names[184] = "INVOKESTATIC";
         names[182] = "INVOKEVIRTUAL";
         names[128] = "IOR";
         names[112] = "IREM";
         names[172] = "IRETURN";
         names[120] = "ISHL";
         names[122] = "ISHR";
         names[54] = "ISTORE";
         names[59] = "ISTORE_0";
         names[60] = "ISTORE_1";
         names[61] = "ISTORE_2";
         names[62] = "ISTORE_3";
         names[100] = "ISUB";
         names[124] = "IUSHR";
         names[130] = "IXOR";
         names[168] = "JSR";
         names[201] = "JSR_W";
         names[138] = "L2D";
         names[137] = "L2F";
         names[136] = "L2I";
         names[97] = "LADD";
         names[47] = "LALOAD";
         names[127] = "LAND";
         names[80] = "LASTORE";
         names[148] = "LCMP";
         names[9] = "LCONST_0";
         names[10] = "LCONST_1";
         names[18] = "LDC";
         names[20] = "LDC2_W";
         names[19] = "LDC_W";
         names[109] = "LDIV";
         names[22] = "LLOAD";
         names[30] = "LLOAD_0";
         names[31] = "LLOAD_1";
         names[32] = "LLOAD_2";
         names[33] = "LLOAD_3";
         names[105] = "LMUL";
         names[117] = "LNEG";
         names[171] = "LOOKUPSWITCH";
         names[129] = "LOR";
         names[113] = "LREM";
         names[173] = "LRETURN";
         names[121] = "LSHL";
         names[123] = "LSHR";
         names[55] = "LSTORE";
         names[63] = "LSTORE_0";
         names[64] = "LSTORE_1";
         names[65] = "LSTORE_2";
         names[66] = "LSTORE_3";
         names[101] = "LSUB";
         names[125] = "LUSHR";
         names[131] = "LXOR";
         names[194] = "MONITORENTER";
         names[195] = "MONITOREXIT";
         names[197] = "MULTIANEWARRAY";
         names[187] = "NEW";
         names[188] = "NEWARRAY";
         names[0] = "NOP";
         names[87] = "POP";
         names[88] = "POP2";
         names[181] = "PUTFIELD";
         names[179] = "PUTSTATIC";
         names[169] = "RET";
         names[177] = "RETURN";
         names[53] = "SALOAD";
         names[86] = "SASTORE";
         names[17] = "SIPUSH";
         names[95] = "SWAP";
         names[170] = "TABLESWITCH";
         names[196] = "WIDE";
         names[186] = "XXXUNUSEDXXX";
      }
   }
}
