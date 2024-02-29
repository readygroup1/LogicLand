import java.util.ArrayList;

public class Gate extends Switch {
    private ArrayList<Switch> inputs;

    

    Gate(Level.GateType type, boolean light){
        super(type, light);
        this.inputs = new ArrayList<Switch>();
    }

    Gate(Level.GateType type, boolean light, ArrayList<Switch> inputs, ArrayList<Switch> outputs){
        super(type, light);
        this.inputs = inputs;
    }

    public void addInput(Switch input){
        this.inputs.add(input);
    }

    public ArrayList<Switch> getInputs(){
        return this.inputs;
    }

    public void setInputs(ArrayList<Switch> inputs){
        this.inputs = inputs;
    }


    public void CheckInputs(){

        boolean light;
        switch(this.getType()){
            case AND:
                light = true;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == false){
                        light = false;
                        break;
                    }
                }
                this.setLight(light);
                break;
            case OR:
                light = false;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == true){
                        light = true;
                        break;
                    }
                }
                this.setLight(light);
                break;
            case NOT:
                this.setLight(!this.getLight());
                break;

            case NAND:
                light = true;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == true){
                        light = false;
                        break;
                    }
                }
                this.setLight(light);
               
                break;
            case NOR:
                light = true;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == true){
                        light = false;
                        break;
                    }
                }
                this.setLight(light);
                
                break;
            case XOR:
                light = false;
                int count = 0;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == true){
                        count++;
                    }
                }
                if (count % 2 == 1){
                    light = true;
                }
                this.setLight(light);
                
                break;
            case XNOR:
                light = true;
                count = 0;
                for (int i = 0; i < this.getInputs().size(); i++){
                    if (this.getInputs().get(i).getLight() == true){
                        count++;
                    }
                }
                if (count % 2 == 1){
                    light = false;
                }
                this.setLight(light);
                break;
            case Light:
                CheckGame();
                break;
            default:
                break;
        }


        for (int i = 0; i < this.getOutputs().size(); i++){
            ((Gate)this.getOutputs().get(i)).CheckInputs();
        }


        
    }

    public void CheckGame(){
        boolean light = false;
        for (int i = 0; i < this.getInputs().size(); i++){
            if (this.getInputs().get(i).getLight() == true){
                light = true;
                break;
            }
        }
        this.setLight(light);
    }

}
