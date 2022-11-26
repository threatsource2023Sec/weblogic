package com.bea.wls.redef.filter;

import serp.bytecode.lowlevel.ConstantPoolTable;

public interface MetaDataFilterFactory {
   MetaDataFilter newFilter(String var1, Class var2, MetaDataFilter var3, ConstantPoolTable var4, byte[] var5);
}
