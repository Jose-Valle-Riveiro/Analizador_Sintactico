import java.util.*;

public class EvalVisitor extends AntlrBaseVisitor<Double> {
    private final Map<String, Double> variables = new HashMap<>();
    private final List<String> errors = new ArrayList<>();

    public Map<String, Double> getVariables() {
        return variables;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public Double visitAssignStmt(AntlrParser.AssignStmtContext ctx) {
        // Obtiene el contexto de la regla "assignment" dentro de "assignStmt"
        AntlrParser.AssignmentContext assignmentCtx = ctx.assignment();

        // Accede al ID y a la expresión desde el contexto de "assignment"
        String id = assignmentCtx.ID().getText();
        Double value = visit(assignmentCtx.expr());

        variables.put(id, value);
        return value;
    }

    @Override
    public Double visitPowExpr(AntlrParser.PowExprContext ctx) {
        return Math.pow(visit(ctx.expr(0)), visit(ctx.expr(1)));
    }

    @Override
    public Double visitMulExpr(AntlrParser.MulExprContext ctx) {
        return visit(ctx.expr(0)) * visit(ctx.expr(1));
    }

    @Override
    public Double visitDivExpr(AntlrParser.DivExprContext ctx) {
        Double right = visit(ctx.expr(1));
        if (right == 0) {
            errors.add("División por cero en línea " + ctx.getStart().getLine());
            return Double.NaN;
        }
        return visit(ctx.expr(0)) / right;
    }

    @Override
    public Double visitAddExpr(AntlrParser.AddExprContext ctx) {
        return visit(ctx.expr(0)) + visit(ctx.expr(1));
    }

    @Override
    public Double visitSubExpr(AntlrParser.SubExprContext ctx) {
        return visit(ctx.expr(0)) - visit(ctx.expr(1));
    }

    @Override
    public Double visitId(AntlrParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (!variables.containsKey(id)) {
            errors.add("Variable no definida '" + id + "' en línea " + ctx.getStart().getLine());
            return Double.NaN;
        }
        return variables.get(id);
    }

    @Override
    public Double visitNumber(AntlrParser.NumberContext ctx) {
        return Double.parseDouble(ctx.NUMBER().getText());
    }

    @Override
    public Double visitParen(AntlrParser.ParenContext ctx) {
        return visit(ctx.expr());
    }
}