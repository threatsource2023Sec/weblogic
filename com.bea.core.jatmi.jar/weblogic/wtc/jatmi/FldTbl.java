package weblogic.wtc.jatmi;

public interface FldTbl {
   String Fldid_to_name(int var1);

   int name_to_Fldid(String var1);

   String[] getFldNames();
}
