import org.antlr.v4.runtime.*;
import java.util.*;

public class SyntaxErrorListener extends BaseErrorListener {
    private final List<String> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {
        errors.add("Error sintáctico en línea " + line + ":" + charPositionInLine + " - " + msg);
    }

    public List<String> getErrors() {
        return errors;
    }
}