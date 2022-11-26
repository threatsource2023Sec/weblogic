package net.shibboleth.utilities.java.support.xml;

import java.io.InputStream;
import java.io.Reader;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;

public interface ParserPool {
   @Nonnull
   DocumentBuilder getBuilder() throws XMLParserException;

   void returnBuilder(@Nullable DocumentBuilder var1);

   @Nonnull
   Document newDocument() throws XMLParserException;

   @Nonnull
   Document parse(@Nonnull InputStream var1) throws XMLParserException;

   @Nonnull
   Document parse(@Nonnull Reader var1) throws XMLParserException;
}
