package org.apache.xmlbeans.impl.jam.mutable;

import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JParameter;

public interface MParameter extends JParameter, MMember {
   void setType(String var1);

   void setType(JClass var1);

   void setUnqualifiedType(String var1);
}
