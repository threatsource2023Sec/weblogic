package com.oracle.weblogic.lifecycle.provisioning.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Transformer {
   Object transform(Object var1) throws TransformationException;
}
