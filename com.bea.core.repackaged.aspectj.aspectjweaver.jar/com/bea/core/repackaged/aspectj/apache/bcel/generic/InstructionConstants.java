package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public interface InstructionConstants {
   Instruction NOP = new Instruction((short)0);
   Instruction ACONST_NULL = new Instruction((short)1);
   Instruction ICONST_M1 = new Instruction((short)2);
   Instruction ICONST_0 = new Instruction((short)3);
   Instruction ICONST_1 = new Instruction((short)4);
   Instruction ICONST_2 = new Instruction((short)5);
   Instruction ICONST_3 = new Instruction((short)6);
   Instruction ICONST_4 = new Instruction((short)7);
   Instruction ICONST_5 = new Instruction((short)8);
   Instruction LCONST_0 = new Instruction((short)9);
   Instruction LCONST_1 = new Instruction((short)10);
   Instruction FCONST_0 = new Instruction((short)11);
   Instruction FCONST_1 = new Instruction((short)12);
   Instruction FCONST_2 = new Instruction((short)13);
   Instruction DCONST_0 = new Instruction((short)14);
   Instruction DCONST_1 = new Instruction((short)15);
   Instruction IALOAD = new Instruction((short)46);
   Instruction LALOAD = new Instruction((short)47);
   Instruction FALOAD = new Instruction((short)48);
   Instruction DALOAD = new Instruction((short)49);
   Instruction AALOAD = new Instruction((short)50);
   Instruction BALOAD = new Instruction((short)51);
   Instruction CALOAD = new Instruction((short)52);
   Instruction SALOAD = new Instruction((short)53);
   Instruction IASTORE = new Instruction((short)79);
   Instruction LASTORE = new Instruction((short)80);
   Instruction FASTORE = new Instruction((short)81);
   Instruction DASTORE = new Instruction((short)82);
   Instruction AASTORE = new Instruction((short)83);
   Instruction BASTORE = new Instruction((short)84);
   Instruction CASTORE = new Instruction((short)85);
   Instruction SASTORE = new Instruction((short)86);
   Instruction POP = new Instruction((short)87);
   Instruction POP2 = new Instruction((short)88);
   Instruction DUP = new Instruction((short)89);
   Instruction DUP_X1 = new Instruction((short)90);
   Instruction DUP_X2 = new Instruction((short)91);
   Instruction DUP2 = new Instruction((short)92);
   Instruction DUP2_X1 = new Instruction((short)93);
   Instruction DUP2_X2 = new Instruction((short)94);
   Instruction SWAP = new Instruction((short)95);
   Instruction IADD = new Instruction((short)96);
   Instruction LADD = new Instruction((short)97);
   Instruction FADD = new Instruction((short)98);
   Instruction DADD = new Instruction((short)99);
   Instruction ISUB = new Instruction((short)100);
   Instruction LSUB = new Instruction((short)101);
   Instruction FSUB = new Instruction((short)102);
   Instruction DSUB = new Instruction((short)103);
   Instruction IMUL = new Instruction((short)104);
   Instruction LMUL = new Instruction((short)105);
   Instruction FMUL = new Instruction((short)106);
   Instruction DMUL = new Instruction((short)107);
   Instruction IDIV = new Instruction((short)108);
   Instruction LDIV = new Instruction((short)109);
   Instruction FDIV = new Instruction((short)110);
   Instruction DDIV = new Instruction((short)111);
   Instruction IREM = new Instruction((short)112);
   Instruction LREM = new Instruction((short)113);
   Instruction FREM = new Instruction((short)114);
   Instruction DREM = new Instruction((short)115);
   Instruction INEG = new Instruction((short)116);
   Instruction LNEG = new Instruction((short)117);
   Instruction FNEG = new Instruction((short)118);
   Instruction DNEG = new Instruction((short)119);
   Instruction ISHL = new Instruction((short)120);
   Instruction LSHL = new Instruction((short)121);
   Instruction ISHR = new Instruction((short)122);
   Instruction LSHR = new Instruction((short)123);
   Instruction IUSHR = new Instruction((short)124);
   Instruction LUSHR = new Instruction((short)125);
   Instruction IAND = new Instruction((short)126);
   Instruction LAND = new Instruction((short)127);
   Instruction IOR = new Instruction((short)128);
   Instruction LOR = new Instruction((short)129);
   Instruction IXOR = new Instruction((short)130);
   Instruction LXOR = new Instruction((short)131);
   Instruction I2L = new Instruction((short)133);
   Instruction I2F = new Instruction((short)134);
   Instruction I2D = new Instruction((short)135);
   Instruction L2I = new Instruction((short)136);
   Instruction L2F = new Instruction((short)137);
   Instruction L2D = new Instruction((short)138);
   Instruction F2I = new Instruction((short)139);
   Instruction F2L = new Instruction((short)140);
   Instruction F2D = new Instruction((short)141);
   Instruction D2I = new Instruction((short)142);
   Instruction D2L = new Instruction((short)143);
   Instruction D2F = new Instruction((short)144);
   Instruction I2B = new Instruction((short)145);
   Instruction I2C = new Instruction((short)146);
   Instruction I2S = new Instruction((short)147);
   Instruction LCMP = new Instruction((short)148);
   Instruction FCMPL = new Instruction((short)149);
   Instruction FCMPG = new Instruction((short)150);
   Instruction DCMPL = new Instruction((short)151);
   Instruction DCMPG = new Instruction((short)152);
   Instruction IRETURN = new Instruction((short)172);
   Instruction LRETURN = new Instruction((short)173);
   Instruction FRETURN = new Instruction((short)174);
   Instruction DRETURN = new Instruction((short)175);
   Instruction ARETURN = new Instruction((short)176);
   Instruction RETURN = new Instruction((short)177);
   Instruction ARRAYLENGTH = new Instruction((short)190);
   Instruction ATHROW = new Instruction((short)191);
   Instruction MONITORENTER = new Instruction((short)194);
   Instruction MONITOREXIT = new Instruction((short)195);
   Instruction IMPDEP1 = new Instruction((short)254);
   Instruction IMPDEP2 = new Instruction((short)255);
   InstructionLV THIS = new InstructionCLV((short)25, 0);
   InstructionLV ALOAD_0 = new InstructionCLV((short)42);
   InstructionLV ALOAD_1 = new InstructionCLV((short)43);
   InstructionLV ALOAD_2 = new InstructionCLV((short)44);
   InstructionLV ALOAD_3 = new InstructionCLV((short)45);
   InstructionLV ILOAD_0 = new InstructionCLV((short)26);
   InstructionLV ILOAD_1 = new InstructionCLV((short)27);
   InstructionLV ILOAD_2 = new InstructionCLV((short)28);
   InstructionLV ILOAD_3 = new InstructionCLV((short)29);
   InstructionLV DLOAD_0 = new InstructionCLV((short)38);
   InstructionLV DLOAD_1 = new InstructionCLV((short)39);
   InstructionLV DLOAD_2 = new InstructionCLV((short)40);
   InstructionLV DLOAD_3 = new InstructionCLV((short)41);
   InstructionLV FLOAD_0 = new InstructionCLV((short)34);
   InstructionLV FLOAD_1 = new InstructionCLV((short)35);
   InstructionLV FLOAD_2 = new InstructionCLV((short)36);
   InstructionLV FLOAD_3 = new InstructionCLV((short)37);
   InstructionLV LLOAD_0 = new InstructionCLV((short)30);
   InstructionLV LLOAD_1 = new InstructionCLV((short)31);
   InstructionLV LLOAD_2 = new InstructionCLV((short)32);
   InstructionLV LLOAD_3 = new InstructionCLV((short)33);
   InstructionLV ASTORE_0 = new InstructionCLV((short)75);
   InstructionLV ASTORE_1 = new InstructionCLV((short)76);
   InstructionLV ASTORE_2 = new InstructionCLV((short)77);
   InstructionLV ASTORE_3 = new InstructionCLV((short)78);
   InstructionLV ISTORE_0 = new InstructionCLV((short)59);
   InstructionLV ISTORE_1 = new InstructionCLV((short)60);
   InstructionLV ISTORE_2 = new InstructionCLV((short)61);
   InstructionLV ISTORE_3 = new InstructionCLV((short)62);
   InstructionLV LSTORE_0 = new InstructionCLV((short)63);
   InstructionLV LSTORE_1 = new InstructionCLV((short)64);
   InstructionLV LSTORE_2 = new InstructionCLV((short)65);
   InstructionLV LSTORE_3 = new InstructionCLV((short)66);
   InstructionLV FSTORE_0 = new InstructionCLV((short)67);
   InstructionLV FSTORE_1 = new InstructionCLV((short)68);
   InstructionLV FSTORE_2 = new InstructionCLV((short)69);
   InstructionLV FSTORE_3 = new InstructionCLV((short)70);
   InstructionLV DSTORE_0 = new InstructionCLV((short)71);
   InstructionLV DSTORE_1 = new InstructionCLV((short)72);
   InstructionLV DSTORE_2 = new InstructionCLV((short)73);
   InstructionLV DSTORE_3 = new InstructionCLV((short)74);
   Instruction[] INSTRUCTIONS = new Instruction[256];
   Clinit bla = new Clinit();

   public static class Clinit {
      Clinit() {
         InstructionConstants.INSTRUCTIONS[0] = InstructionConstants.NOP;
         InstructionConstants.INSTRUCTIONS[1] = InstructionConstants.ACONST_NULL;
         InstructionConstants.INSTRUCTIONS[2] = InstructionConstants.ICONST_M1;
         InstructionConstants.INSTRUCTIONS[3] = InstructionConstants.ICONST_0;
         InstructionConstants.INSTRUCTIONS[4] = InstructionConstants.ICONST_1;
         InstructionConstants.INSTRUCTIONS[5] = InstructionConstants.ICONST_2;
         InstructionConstants.INSTRUCTIONS[6] = InstructionConstants.ICONST_3;
         InstructionConstants.INSTRUCTIONS[7] = InstructionConstants.ICONST_4;
         InstructionConstants.INSTRUCTIONS[8] = InstructionConstants.ICONST_5;
         InstructionConstants.INSTRUCTIONS[9] = InstructionConstants.LCONST_0;
         InstructionConstants.INSTRUCTIONS[10] = InstructionConstants.LCONST_1;
         InstructionConstants.INSTRUCTIONS[11] = InstructionConstants.FCONST_0;
         InstructionConstants.INSTRUCTIONS[12] = InstructionConstants.FCONST_1;
         InstructionConstants.INSTRUCTIONS[13] = InstructionConstants.FCONST_2;
         InstructionConstants.INSTRUCTIONS[14] = InstructionConstants.DCONST_0;
         InstructionConstants.INSTRUCTIONS[15] = InstructionConstants.DCONST_1;
         InstructionConstants.INSTRUCTIONS[46] = InstructionConstants.IALOAD;
         InstructionConstants.INSTRUCTIONS[47] = InstructionConstants.LALOAD;
         InstructionConstants.INSTRUCTIONS[48] = InstructionConstants.FALOAD;
         InstructionConstants.INSTRUCTIONS[49] = InstructionConstants.DALOAD;
         InstructionConstants.INSTRUCTIONS[50] = InstructionConstants.AALOAD;
         InstructionConstants.INSTRUCTIONS[51] = InstructionConstants.BALOAD;
         InstructionConstants.INSTRUCTIONS[52] = InstructionConstants.CALOAD;
         InstructionConstants.INSTRUCTIONS[53] = InstructionConstants.SALOAD;
         InstructionConstants.INSTRUCTIONS[79] = InstructionConstants.IASTORE;
         InstructionConstants.INSTRUCTIONS[80] = InstructionConstants.LASTORE;
         InstructionConstants.INSTRUCTIONS[81] = InstructionConstants.FASTORE;
         InstructionConstants.INSTRUCTIONS[82] = InstructionConstants.DASTORE;
         InstructionConstants.INSTRUCTIONS[83] = InstructionConstants.AASTORE;
         InstructionConstants.INSTRUCTIONS[84] = InstructionConstants.BASTORE;
         InstructionConstants.INSTRUCTIONS[85] = InstructionConstants.CASTORE;
         InstructionConstants.INSTRUCTIONS[86] = InstructionConstants.SASTORE;
         InstructionConstants.INSTRUCTIONS[87] = InstructionConstants.POP;
         InstructionConstants.INSTRUCTIONS[88] = InstructionConstants.POP2;
         InstructionConstants.INSTRUCTIONS[89] = InstructionConstants.DUP;
         InstructionConstants.INSTRUCTIONS[90] = InstructionConstants.DUP_X1;
         InstructionConstants.INSTRUCTIONS[91] = InstructionConstants.DUP_X2;
         InstructionConstants.INSTRUCTIONS[92] = InstructionConstants.DUP2;
         InstructionConstants.INSTRUCTIONS[93] = InstructionConstants.DUP2_X1;
         InstructionConstants.INSTRUCTIONS[94] = InstructionConstants.DUP2_X2;
         InstructionConstants.INSTRUCTIONS[95] = InstructionConstants.SWAP;
         InstructionConstants.INSTRUCTIONS[96] = InstructionConstants.IADD;
         InstructionConstants.INSTRUCTIONS[97] = InstructionConstants.LADD;
         InstructionConstants.INSTRUCTIONS[98] = InstructionConstants.FADD;
         InstructionConstants.INSTRUCTIONS[99] = InstructionConstants.DADD;
         InstructionConstants.INSTRUCTIONS[100] = InstructionConstants.ISUB;
         InstructionConstants.INSTRUCTIONS[101] = InstructionConstants.LSUB;
         InstructionConstants.INSTRUCTIONS[102] = InstructionConstants.FSUB;
         InstructionConstants.INSTRUCTIONS[103] = InstructionConstants.DSUB;
         InstructionConstants.INSTRUCTIONS[104] = InstructionConstants.IMUL;
         InstructionConstants.INSTRUCTIONS[105] = InstructionConstants.LMUL;
         InstructionConstants.INSTRUCTIONS[106] = InstructionConstants.FMUL;
         InstructionConstants.INSTRUCTIONS[107] = InstructionConstants.DMUL;
         InstructionConstants.INSTRUCTIONS[108] = InstructionConstants.IDIV;
         InstructionConstants.INSTRUCTIONS[109] = InstructionConstants.LDIV;
         InstructionConstants.INSTRUCTIONS[110] = InstructionConstants.FDIV;
         InstructionConstants.INSTRUCTIONS[111] = InstructionConstants.DDIV;
         InstructionConstants.INSTRUCTIONS[112] = InstructionConstants.IREM;
         InstructionConstants.INSTRUCTIONS[113] = InstructionConstants.LREM;
         InstructionConstants.INSTRUCTIONS[114] = InstructionConstants.FREM;
         InstructionConstants.INSTRUCTIONS[115] = InstructionConstants.DREM;
         InstructionConstants.INSTRUCTIONS[116] = InstructionConstants.INEG;
         InstructionConstants.INSTRUCTIONS[117] = InstructionConstants.LNEG;
         InstructionConstants.INSTRUCTIONS[118] = InstructionConstants.FNEG;
         InstructionConstants.INSTRUCTIONS[119] = InstructionConstants.DNEG;
         InstructionConstants.INSTRUCTIONS[120] = InstructionConstants.ISHL;
         InstructionConstants.INSTRUCTIONS[121] = InstructionConstants.LSHL;
         InstructionConstants.INSTRUCTIONS[122] = InstructionConstants.ISHR;
         InstructionConstants.INSTRUCTIONS[123] = InstructionConstants.LSHR;
         InstructionConstants.INSTRUCTIONS[124] = InstructionConstants.IUSHR;
         InstructionConstants.INSTRUCTIONS[125] = InstructionConstants.LUSHR;
         InstructionConstants.INSTRUCTIONS[126] = InstructionConstants.IAND;
         InstructionConstants.INSTRUCTIONS[127] = InstructionConstants.LAND;
         InstructionConstants.INSTRUCTIONS[128] = InstructionConstants.IOR;
         InstructionConstants.INSTRUCTIONS[129] = InstructionConstants.LOR;
         InstructionConstants.INSTRUCTIONS[130] = InstructionConstants.IXOR;
         InstructionConstants.INSTRUCTIONS[131] = InstructionConstants.LXOR;
         InstructionConstants.INSTRUCTIONS[133] = InstructionConstants.I2L;
         InstructionConstants.INSTRUCTIONS[134] = InstructionConstants.I2F;
         InstructionConstants.INSTRUCTIONS[135] = InstructionConstants.I2D;
         InstructionConstants.INSTRUCTIONS[136] = InstructionConstants.L2I;
         InstructionConstants.INSTRUCTIONS[137] = InstructionConstants.L2F;
         InstructionConstants.INSTRUCTIONS[138] = InstructionConstants.L2D;
         InstructionConstants.INSTRUCTIONS[139] = InstructionConstants.F2I;
         InstructionConstants.INSTRUCTIONS[140] = InstructionConstants.F2L;
         InstructionConstants.INSTRUCTIONS[141] = InstructionConstants.F2D;
         InstructionConstants.INSTRUCTIONS[142] = InstructionConstants.D2I;
         InstructionConstants.INSTRUCTIONS[143] = InstructionConstants.D2L;
         InstructionConstants.INSTRUCTIONS[144] = InstructionConstants.D2F;
         InstructionConstants.INSTRUCTIONS[145] = InstructionConstants.I2B;
         InstructionConstants.INSTRUCTIONS[146] = InstructionConstants.I2C;
         InstructionConstants.INSTRUCTIONS[147] = InstructionConstants.I2S;
         InstructionConstants.INSTRUCTIONS[148] = InstructionConstants.LCMP;
         InstructionConstants.INSTRUCTIONS[149] = InstructionConstants.FCMPL;
         InstructionConstants.INSTRUCTIONS[150] = InstructionConstants.FCMPG;
         InstructionConstants.INSTRUCTIONS[151] = InstructionConstants.DCMPL;
         InstructionConstants.INSTRUCTIONS[152] = InstructionConstants.DCMPG;
         InstructionConstants.INSTRUCTIONS[172] = InstructionConstants.IRETURN;
         InstructionConstants.INSTRUCTIONS[173] = InstructionConstants.LRETURN;
         InstructionConstants.INSTRUCTIONS[174] = InstructionConstants.FRETURN;
         InstructionConstants.INSTRUCTIONS[175] = InstructionConstants.DRETURN;
         InstructionConstants.INSTRUCTIONS[176] = InstructionConstants.ARETURN;
         InstructionConstants.INSTRUCTIONS[177] = InstructionConstants.RETURN;
         InstructionConstants.INSTRUCTIONS[190] = InstructionConstants.ARRAYLENGTH;
         InstructionConstants.INSTRUCTIONS[191] = InstructionConstants.ATHROW;
         InstructionConstants.INSTRUCTIONS[194] = InstructionConstants.MONITORENTER;
         InstructionConstants.INSTRUCTIONS[195] = InstructionConstants.MONITOREXIT;
         InstructionConstants.INSTRUCTIONS[254] = InstructionConstants.IMPDEP1;
         InstructionConstants.INSTRUCTIONS[255] = InstructionConstants.IMPDEP2;
         InstructionConstants.INSTRUCTIONS[42] = InstructionConstants.ALOAD_0;
         InstructionConstants.INSTRUCTIONS[43] = InstructionConstants.ALOAD_1;
         InstructionConstants.INSTRUCTIONS[44] = InstructionConstants.ALOAD_2;
         InstructionConstants.INSTRUCTIONS[45] = InstructionConstants.ALOAD_3;
         InstructionConstants.INSTRUCTIONS[30] = InstructionConstants.LLOAD_0;
         InstructionConstants.INSTRUCTIONS[31] = InstructionConstants.LLOAD_1;
         InstructionConstants.INSTRUCTIONS[32] = InstructionConstants.LLOAD_2;
         InstructionConstants.INSTRUCTIONS[33] = InstructionConstants.LLOAD_3;
         InstructionConstants.INSTRUCTIONS[38] = InstructionConstants.DLOAD_0;
         InstructionConstants.INSTRUCTIONS[39] = InstructionConstants.DLOAD_1;
         InstructionConstants.INSTRUCTIONS[40] = InstructionConstants.DLOAD_2;
         InstructionConstants.INSTRUCTIONS[41] = InstructionConstants.DLOAD_3;
         InstructionConstants.INSTRUCTIONS[34] = InstructionConstants.FLOAD_0;
         InstructionConstants.INSTRUCTIONS[35] = InstructionConstants.FLOAD_1;
         InstructionConstants.INSTRUCTIONS[36] = InstructionConstants.FLOAD_2;
         InstructionConstants.INSTRUCTIONS[37] = InstructionConstants.FLOAD_3;
         InstructionConstants.INSTRUCTIONS[26] = InstructionConstants.ILOAD_0;
         InstructionConstants.INSTRUCTIONS[27] = InstructionConstants.ILOAD_1;
         InstructionConstants.INSTRUCTIONS[28] = InstructionConstants.ILOAD_2;
         InstructionConstants.INSTRUCTIONS[29] = InstructionConstants.ILOAD_3;
         InstructionConstants.INSTRUCTIONS[75] = InstructionConstants.ASTORE_0;
         InstructionConstants.INSTRUCTIONS[76] = InstructionConstants.ASTORE_1;
         InstructionConstants.INSTRUCTIONS[77] = InstructionConstants.ASTORE_2;
         InstructionConstants.INSTRUCTIONS[78] = InstructionConstants.ASTORE_3;
         InstructionConstants.INSTRUCTIONS[63] = InstructionConstants.LSTORE_0;
         InstructionConstants.INSTRUCTIONS[64] = InstructionConstants.LSTORE_1;
         InstructionConstants.INSTRUCTIONS[65] = InstructionConstants.LSTORE_2;
         InstructionConstants.INSTRUCTIONS[66] = InstructionConstants.LSTORE_3;
         InstructionConstants.INSTRUCTIONS[71] = InstructionConstants.DSTORE_0;
         InstructionConstants.INSTRUCTIONS[72] = InstructionConstants.DSTORE_1;
         InstructionConstants.INSTRUCTIONS[73] = InstructionConstants.DSTORE_2;
         InstructionConstants.INSTRUCTIONS[74] = InstructionConstants.DSTORE_3;
         InstructionConstants.INSTRUCTIONS[67] = InstructionConstants.FSTORE_0;
         InstructionConstants.INSTRUCTIONS[68] = InstructionConstants.FSTORE_1;
         InstructionConstants.INSTRUCTIONS[69] = InstructionConstants.FSTORE_2;
         InstructionConstants.INSTRUCTIONS[70] = InstructionConstants.FSTORE_3;
         InstructionConstants.INSTRUCTIONS[59] = InstructionConstants.ISTORE_0;
         InstructionConstants.INSTRUCTIONS[60] = InstructionConstants.ISTORE_1;
         InstructionConstants.INSTRUCTIONS[61] = InstructionConstants.ISTORE_2;
         InstructionConstants.INSTRUCTIONS[62] = InstructionConstants.ISTORE_3;
      }
   }
}