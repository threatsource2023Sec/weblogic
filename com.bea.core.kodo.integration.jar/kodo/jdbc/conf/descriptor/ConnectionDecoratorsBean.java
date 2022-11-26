package kodo.jdbc.conf.descriptor;

public interface ConnectionDecoratorsBean {
   CustomConnectionDecoratorBean[] getCustomConnectionDecorators();

   CustomConnectionDecoratorBean createCustomConnectionDecorator();

   void destroyCustomConnectionDecorator(CustomConnectionDecoratorBean var1);
}
