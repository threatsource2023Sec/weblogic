package org.apache.velocity.runtime.parser.node;

import java.io.IOException;
import java.io.Writer;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.Token;

public interface Node {
   void jjtOpen();

   void jjtClose();

   void jjtSetParent(Node var1);

   Node jjtGetParent();

   void jjtAddChild(Node var1, int var2);

   Node jjtGetChild(int var1);

   int jjtGetNumChildren();

   Object jjtAccept(ParserVisitor var1, Object var2);

   Object childrenAccept(ParserVisitor var1, Object var2);

   Token getFirstToken();

   Token getLastToken();

   int getType();

   Object init(InternalContextAdapter var1, Object var2) throws Exception;

   boolean evaluate(InternalContextAdapter var1) throws MethodInvocationException;

   Object value(InternalContextAdapter var1) throws MethodInvocationException;

   boolean render(InternalContextAdapter var1, Writer var2) throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException;

   Object execute(Object var1, InternalContextAdapter var2) throws MethodInvocationException;

   void setInfo(int var1);

   int getInfo();

   String literal();

   void setInvalid();

   boolean isInvalid();

   int getLine();

   int getColumn();
}
