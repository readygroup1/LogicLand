import java.util.ArrayList;

public class Level {
    static public enum GateType {AND, OR, NOT, NAND, NOR, XOR, XNOR, Switch, Wire, Light};

    public static void main(String[] args) {
        Switch s = new Switch( true);
        Gate g = new Gate(GateType.AND, true);
        ArrayList<Switch> inputs = new ArrayList<Switch>();
        ArrayList<Switch> ouputs = new ArrayList<Switch>();

        inputs.add(s);
        inputs.add(g);
        Gate w = new Gate(GateType.AND, true, inputs, ouputs);

        ((Gate)(inputs.get(1))).addInput(w);
    }
}
