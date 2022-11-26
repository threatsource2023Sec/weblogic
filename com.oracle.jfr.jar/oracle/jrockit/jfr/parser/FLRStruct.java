package oracle.jrockit.jfr.parser;

import java.util.List;

public interface FLRStruct {
   Object getValue(String var1);

   Object getResolvedValue(String var1);

   Object getValue(int var1);

   Object getResolvedValue(int var1);

   List getValues();

   List getResolvedValues();

   List getValueInfos();

   FLRValueInfo getValueInfo(int var1);

   FLRValueInfo getValueInfo(String var1);
}
