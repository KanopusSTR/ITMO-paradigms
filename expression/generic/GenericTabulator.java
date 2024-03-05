package expression.generic;

import expression.exceptions.ExpressionParser;

public class GenericTabulator implements Tabulator {

    public GenericTabulator () {

    }

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        int x = x2 - x1 + 1;
        int y = y2 - y1 + 1;
        int z = z2 - z1 + 1;
        Object [][][] table = new Object[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    if (mode.equals("i")) {
                        ExpressionParser parser = new ExpressionParser();
                        try {
                            table[i][j][k] = parser.parse(expression).evaluate(i + x1, j + y1, k + z1);
                        } catch (ArithmeticException e) {
                            table[i][j][k] = null;
                        }
                    } else if (mode.equals("d")) {
                        ExpressionParser parser = new ExpressionParser();
                        try {
                            table[i][j][k] = (double) parser.parse(expression).evaluate(i + x1, j + y1, k + z1);
                        } catch (ArithmeticException e) {
                            table[i][j][k] = null;
                        }
                    }
                }
            }
        }
        return table;
    }
}
