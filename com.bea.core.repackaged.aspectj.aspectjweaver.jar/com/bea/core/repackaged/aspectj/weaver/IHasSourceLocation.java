package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;

public interface IHasSourceLocation extends IHasPosition {
   ISourceContext getSourceContext();

   ISourceLocation getSourceLocation();
}
