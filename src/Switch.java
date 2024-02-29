import java.util.ArrayList;

// Base plate for gates can either be on or off and can have multiple outputs


public class Switch {
    private Level.GateType type;
    private boolean light;
    private ArrayList<Switch> outputs;

    // Creating Fresh game
    public Switch( boolean light){
        this.type = Level.GateType.Switch;
        this.light = light;
        this.outputs = new ArrayList<Switch>();
    }

    //Creating gate from save
    public Switch(Level.GateType type, boolean light,  ArrayList<Switch> outputs){
        this.type = type;
        this.light = light;
        this.outputs = outputs;
    }

    //getters and setters
    public void addOutput(Switch output){
        this.outputs.add(output);
    }

    public void removeOutput(Gate output){
        this.outputs.remove(output);
    }

    public ArrayList<Switch> getOutputs(){
        return this.outputs;
    }

    public void setOutputs(ArrayList<Switch> outputs){
        this.outputs = outputs;
    }
    
    public Level.GateType getType(){
        return this.type;
    }

    public boolean getLight(){
        return this.light;
    }

    public void setLight(boolean light){
        this.light = light;
    }

    



    
}
