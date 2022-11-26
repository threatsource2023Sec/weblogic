package com.bea.core.repackaged.aspectj.weaver;

import java.util.Iterator;

public interface IClassFileProvider {
   Iterator getClassFileIterator();

   IWeaveRequestor getRequestor();

   boolean isApplyAtAspectJMungersOnly();
}
