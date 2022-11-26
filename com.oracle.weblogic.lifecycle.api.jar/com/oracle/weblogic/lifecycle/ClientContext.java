package com.oracle.weblogic.lifecycle;

import java.util.Locale;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ClientContext {
   Locale getLocale();

   void setLocale(Locale var1);
}
