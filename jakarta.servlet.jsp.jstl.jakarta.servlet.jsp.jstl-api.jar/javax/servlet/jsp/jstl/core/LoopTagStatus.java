package javax.servlet.jsp.jstl.core;

public interface LoopTagStatus {
   Object getCurrent();

   int getIndex();

   int getCount();

   boolean isFirst();

   boolean isLast();

   Integer getBegin();

   Integer getEnd();

   Integer getStep();
}
