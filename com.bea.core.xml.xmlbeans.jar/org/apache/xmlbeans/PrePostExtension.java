package org.apache.xmlbeans;

public interface PrePostExtension {
   int OPERATION_SET = 1;
   int OPERATION_INSERT = 2;
   int OPERATION_REMOVE = 3;

   String getStaticHandler();

   boolean hasPreCall();

   boolean hasPostCall();
}
