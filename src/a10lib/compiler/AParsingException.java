package a10lib.compiler;

/**
 * A class representing exception of a10lib's compiler framework in
 * {@code a10lib.compiler.syntax} package
 * or {@code a10lib.compiler.ACompilingException} that occurs in
 * {@code a10lib.compiler.syntax.Parser} class
 * 
 * 
 * @author Athensclub
 *
 */
public class AParsingException extends ACompilingException {

    public AParsingException() {
	super();
    }

    public AParsingException(String str) {
	super(str);
    }

}
