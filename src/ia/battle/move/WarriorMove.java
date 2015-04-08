package ia.battle.move;

import java.util.ArrayList;

import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Move;

public class WarriorMove extends Move{
	private ArrayList<FieldCell> movements;
     
    public WarriorMove() {
    	movements = new ArrayList<FieldCell>();
    }
    
     public void setMovements(ArrayList<FieldCell> movements) {
    	 this.movements = movements;
     }
    
     @Override
     public ArrayList<FieldCell> move() {
    	 return movements;
     }

}
