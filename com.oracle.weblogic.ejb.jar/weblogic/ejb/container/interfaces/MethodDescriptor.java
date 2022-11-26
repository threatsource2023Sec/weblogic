package weblogic.ejb.container.interfaces;

public interface MethodDescriptor {
   short DEFAULT_METHOD = 1;
   short GENERIC_METHOD = 2;
   short SPECIFIC_METHOD = 3;
   String DEFAULT_MD_METHOD_NAME = "*";

   short getMethodType();

   String getMethodSignature();

   String getEjbName();

   String getMethodIntf();
}
