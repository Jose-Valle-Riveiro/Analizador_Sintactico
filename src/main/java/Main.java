import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        StringBuilder inputBuilder = new StringBuilder();
        String line;
        System.out.println("Ingrese el código fuente (presione Enter dos veces para finalizar):");
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.isEmpty()) { // Si la línea está vacía, terminar
                break;
            }
            inputBuilder.append(line).append("\n");
        }

        String input = inputBuilder.toString();

        AntlrLexer lexer = new AntlrLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AntlrParser parser = new AntlrParser(tokens);

        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        ParseTree tree = parser.prog();

        if (!errorListener.getErrors().isEmpty()) {
            errorListener.getErrors().forEach(System.err::println);
        } else {
            EvalVisitor evalVisitor = new EvalVisitor();
            evalVisitor.visit(tree);

            if (!evalVisitor.getErrors().isEmpty()) {
                evalVisitor.getErrors().forEach(System.err::println);
            } else {
                evalVisitor.getVariables().forEach((name, value) ->
                        System.out.println(name + " = " + value));
            }
        }
    }
}