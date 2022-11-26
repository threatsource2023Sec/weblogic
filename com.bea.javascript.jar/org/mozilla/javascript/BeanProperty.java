package org.mozilla.javascript;

import java.lang.reflect.Method;

class BeanProperty {
   Method getter;
   Method setter;

   BeanProperty(Method var1, Method var2) {
      this.getter = var1;
      this.setter = var2;
   }
}
