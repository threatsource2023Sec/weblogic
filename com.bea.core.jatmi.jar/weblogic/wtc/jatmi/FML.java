package weblogic.wtc.jatmi;

import java.util.Iterator;

public interface FML {
   int FLD_SHORT = 0;
   int FLD_LONG = 1;
   int FLD_CHAR = 2;
   int FLD_FLOAT = 3;
   int FLD_DOUBLE = 4;
   int FLD_STRING = 5;
   int FLD_CARRAY = 6;
   int FLD_INT = 7;
   int FLD_DECIMAL = 8;
   int FLD_PTR = 9;
   int FLD_FML32 = 10;
   int FLD_VIEW32 = 11;
   int FLD_MBSTRING = 12;
   int FLD_BOOL = 14;
   int FLD_UCHAR = 15;
   int FLD_SCHAR = 16;
   int FLD_WCHAR = 17;
   int FLD_UINT = 18;
   int FLD_LLONG = 19;
   int FLD_ULLONG = 21;
   int FLD_LDOUBLE = 22;
   int FLD_USHORT = 23;

   void setFieldTables(FldTbl[] var1);

   FldTbl[] getFieldTables();

   int Fldno(int var1);

   int Fldtype(int var1);

   void Fchg(FmlKey var1, Object var2) throws Ferror;

   void Fchg(int var1, int var2, Object var3) throws Ferror;

   Object Fget(FmlKey var1) throws Ferror;

   Object Fget(int var1, int var2) throws Ferror;

   Iterator Fiterator() throws Ferror;

   int Foccur(int var1);

   void Fdel(FmlKey var1) throws Ferror;

   void Fdel(int var1, int var2) throws Ferror;

   String Fname(int var1) throws Ferror;

   int Fldid(String var1) throws Ferror;

   int Fused() throws Ferror;
}
