package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public interface InstVisitor {
   void visitStackInstruction(Instruction var1);

   void visitLocalVariableInstruction(InstructionLV var1);

   void visitBranchInstruction(InstructionBranch var1);

   void visitLoadClass(Instruction var1);

   void visitFieldInstruction(Instruction var1);

   void visitIfInstruction(Instruction var1);

   void visitConversionInstruction(Instruction var1);

   void visitPopInstruction(Instruction var1);

   void visitStoreInstruction(Instruction var1);

   void visitTypedInstruction(Instruction var1);

   void visitSelect(InstructionSelect var1);

   void visitJsrInstruction(InstructionBranch var1);

   void visitGotoInstruction(Instruction var1);

   void visitUnconditionalBranch(Instruction var1);

   void visitPushInstruction(Instruction var1);

   void visitArithmeticInstruction(Instruction var1);

   void visitCPInstruction(Instruction var1);

   void visitInvokeInstruction(InvokeInstruction var1);

   void visitArrayInstruction(Instruction var1);

   void visitAllocationInstruction(Instruction var1);

   void visitReturnInstruction(Instruction var1);

   void visitFieldOrMethod(Instruction var1);

   void visitConstantPushInstruction(Instruction var1);

   void visitExceptionThrower(Instruction var1);

   void visitLoadInstruction(Instruction var1);

   void visitVariableLengthInstruction(Instruction var1);

   void visitStackProducer(Instruction var1);

   void visitStackConsumer(Instruction var1);

   void visitACONST_NULL(Instruction var1);

   void visitGETSTATIC(FieldInstruction var1);

   void visitIF_ICMPLT(Instruction var1);

   void visitMONITOREXIT(Instruction var1);

   void visitIFLT(Instruction var1);

   void visitLSTORE(Instruction var1);

   void visitPOP2(Instruction var1);

   void visitBASTORE(Instruction var1);

   void visitISTORE(Instruction var1);

   void visitCHECKCAST(Instruction var1);

   void visitFCMPG(Instruction var1);

   void visitI2F(Instruction var1);

   void visitATHROW(Instruction var1);

   void visitDCMPL(Instruction var1);

   void visitARRAYLENGTH(Instruction var1);

   void visitDUP(Instruction var1);

   void visitINVOKESTATIC(InvokeInstruction var1);

   void visitLCONST(Instruction var1);

   void visitDREM(Instruction var1);

   void visitIFGE(Instruction var1);

   void visitCALOAD(Instruction var1);

   void visitLASTORE(Instruction var1);

   void visitI2D(Instruction var1);

   void visitDADD(Instruction var1);

   void visitINVOKESPECIAL(InvokeInstruction var1);

   void visitIAND(Instruction var1);

   void visitPUTFIELD(FieldInstruction var1);

   void visitILOAD(Instruction var1);

   void visitDLOAD(Instruction var1);

   void visitDCONST(Instruction var1);

   void visitNEW(Instruction var1);

   void visitIFNULL(Instruction var1);

   void visitLSUB(Instruction var1);

   void visitL2I(Instruction var1);

   void visitISHR(Instruction var1);

   void visitTABLESWITCH(TABLESWITCH var1);

   void visitIINC(IINC var1);

   void visitDRETURN(Instruction var1);

   void visitFSTORE(Instruction var1);

   void visitDASTORE(Instruction var1);

   void visitIALOAD(Instruction var1);

   void visitDDIV(Instruction var1);

   void visitIF_ICMPGE(Instruction var1);

   void visitLAND(Instruction var1);

   void visitIDIV(Instruction var1);

   void visitLOR(Instruction var1);

   void visitCASTORE(Instruction var1);

   void visitFREM(Instruction var1);

   void visitLDC(Instruction var1);

   void visitBIPUSH(Instruction var1);

   void visitDSTORE(Instruction var1);

   void visitF2L(Instruction var1);

   void visitFMUL(Instruction var1);

   void visitLLOAD(Instruction var1);

   void visitJSR(InstructionBranch var1);

   void visitFSUB(Instruction var1);

   void visitSASTORE(Instruction var1);

   void visitALOAD(Instruction var1);

   void visitDUP2_X2(Instruction var1);

   void visitRETURN(Instruction var1);

   void visitDALOAD(Instruction var1);

   void visitSIPUSH(Instruction var1);

   void visitDSUB(Instruction var1);

   void visitL2F(Instruction var1);

   void visitIF_ICMPGT(Instruction var1);

   void visitF2D(Instruction var1);

   void visitI2L(Instruction var1);

   void visitIF_ACMPNE(Instruction var1);

   void visitPOP(Instruction var1);

   void visitI2S(Instruction var1);

   void visitIFEQ(Instruction var1);

   void visitSWAP(Instruction var1);

   void visitIOR(Instruction var1);

   void visitIREM(Instruction var1);

   void visitIASTORE(Instruction var1);

   void visitNEWARRAY(Instruction var1);

   void visitINVOKEINTERFACE(INVOKEINTERFACE var1);

   void visitINEG(Instruction var1);

   void visitLCMP(Instruction var1);

   void visitJSR_W(InstructionBranch var1);

   void visitMULTIANEWARRAY(MULTIANEWARRAY var1);

   void visitDUP_X2(Instruction var1);

   void visitSALOAD(Instruction var1);

   void visitIFNONNULL(Instruction var1);

   void visitDMUL(Instruction var1);

   void visitIFNE(Instruction var1);

   void visitIF_ICMPLE(Instruction var1);

   void visitLDC2_W(Instruction var1);

   void visitGETFIELD(FieldInstruction var1);

   void visitLADD(Instruction var1);

   void visitNOP(Instruction var1);

   void visitFALOAD(Instruction var1);

   void visitINSTANCEOF(Instruction var1);

   void visitIFLE(Instruction var1);

   void visitLXOR(Instruction var1);

   void visitLRETURN(Instruction var1);

   void visitFCONST(Instruction var1);

   void visitIUSHR(Instruction var1);

   void visitBALOAD(Instruction var1);

   void visitDUP2(Instruction var1);

   void visitIF_ACMPEQ(Instruction var1);

   void visitIMPDEP1(Instruction var1);

   void visitMONITORENTER(Instruction var1);

   void visitLSHL(Instruction var1);

   void visitDCMPG(Instruction var1);

   void visitD2L(Instruction var1);

   void visitIMPDEP2(Instruction var1);

   void visitL2D(Instruction var1);

   void visitRET(RET var1);

   void visitIFGT(Instruction var1);

   void visitIXOR(Instruction var1);

   void visitINVOKEVIRTUAL(InvokeInstruction var1);

   void visitFASTORE(Instruction var1);

   void visitIRETURN(Instruction var1);

   void visitIF_ICMPNE(Instruction var1);

   void visitFLOAD(Instruction var1);

   void visitLDIV(Instruction var1);

   void visitPUTSTATIC(FieldInstruction var1);

   void visitAALOAD(Instruction var1);

   void visitD2I(Instruction var1);

   void visitIF_ICMPEQ(Instruction var1);

   void visitAASTORE(Instruction var1);

   void visitARETURN(Instruction var1);

   void visitDUP2_X1(Instruction var1);

   void visitFNEG(Instruction var1);

   void visitGOTO_W(Instruction var1);

   void visitD2F(Instruction var1);

   void visitGOTO(Instruction var1);

   void visitISUB(Instruction var1);

   void visitF2I(Instruction var1);

   void visitDNEG(Instruction var1);

   void visitICONST(Instruction var1);

   void visitFDIV(Instruction var1);

   void visitI2B(Instruction var1);

   void visitLNEG(Instruction var1);

   void visitLREM(Instruction var1);

   void visitIMUL(Instruction var1);

   void visitIADD(Instruction var1);

   void visitLSHR(Instruction var1);

   void visitLOOKUPSWITCH(LOOKUPSWITCH var1);

   void visitDUP_X1(Instruction var1);

   void visitFCMPL(Instruction var1);

   void visitI2C(Instruction var1);

   void visitLMUL(Instruction var1);

   void visitLUSHR(Instruction var1);

   void visitISHL(Instruction var1);

   void visitLALOAD(Instruction var1);

   void visitASTORE(Instruction var1);

   void visitANEWARRAY(Instruction var1);

   void visitFRETURN(Instruction var1);

   void visitFADD(Instruction var1);

   void visitBREAKPOINT(Instruction var1);
}
