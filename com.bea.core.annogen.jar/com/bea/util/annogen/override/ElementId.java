package com.bea.util.annogen.override;

public interface ElementId {
   int PACKAGE_TYPE = 0;
   int CLASS_TYPE = 1;
   int FIELD_TYPE = 2;
   int METHOD_TYPE = 3;
   int CONSTRUCTOR_TYPE = 4;
   int PARAMETER_TYPE = 5;
   int ANNOTATION_TYPE = 6;
   int NO_PARAMETER = -1;

   int getType();

   String getName();

   String getContainingClass();

   String[] getSignature();

   int getParameterNumber();
}
