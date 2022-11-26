package org.stringtemplate.v4;

import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public interface ModelAdaptor {
   Object getProperty(Interpreter var1, ST var2, Object var3, Object var4, String var5) throws STNoSuchPropertyException;
}
