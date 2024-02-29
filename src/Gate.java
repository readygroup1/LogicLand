import java.util.ArrayList;

public class Gate extends Switch {

    //Genaric gate class that changes how it checks inputs based on type
    private ArrayList<Switch> inputs;

    // creating fresh gate
    Gate(Level.GateType type, boolean light){
        super(light);
        this.inputs = new ArrayList<Switch>();
    }

    // creating gate from save
    Gate(Level.GateType type, boolean light, ArrayList<Switch> inputs, ArrayList<Switch> outputs){
        super(type, light, outputs);
        this.inputs = inputs;
    }

    //getters and setters for inputs (switches only have outputs)
    public void addInput(Switch input){
        this.inputs.add(input);
    }

    public void removeInput(Switch input){
        this.inputs.remove(input);
    }

    public ArrayList<Switch> getInputs(){
        return this.inputs;
    }

    public void setInputs(ArrayList<Switch> inputs){
        this.inputs = inputs;
    }



    //checks inputs based on type
    /*
     * When ever checkInputs gets called it will check the inputs of the gate and change its light value based on the type of gate
     * It will then tell every output of the gate to check its inputs, this will happen recursivly till we reach the light gate
     * where it will check if the game is won (check if itself is lit)
     * 
     */

    public void CheckInputs(){

        boolean light;
        //switch statement to check inputs based on type
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
            case Light:             //special case if its the last gate the "light"
                CheckGame();    // if lit that means the game is won
                break;
            default:
                break;
        }


        for (int i = 0; i < this.getOutputs().size(); i++){
            ((Gate)this.getOutputs().get(i)).CheckInputs();
        }


        
    }

    //checks if the game is won
    public boolean CheckGame(){
        boolean light = false;
        for (int i = 0; i < this.getInputs().size(); i++){
            if (this.getInputs().get(i).getLight() == true){
                light = true;
                break;
            }
        }
        this.setLight(light);
        return light;
    }

}
