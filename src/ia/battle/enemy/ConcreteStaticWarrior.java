package ia.battle.enemy;

import ia.battle.camp.FieldCell;
import ia.battle.camp.Warrior;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Skip;
import ia.exceptions.RuleException;

public class ConcreteStaticWarrior  extends Warrior{

	

	 public ConcreteStaticWarrior(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
	         super(name, health, defense, strength, speed, range);
	
	 }
	
	 @Override
	 public Action playTurn(long tick, int actionNumber) {
		 // do nothing
		 return (new Skip());
	 }
	
	 @Override
	 public void wasAttacked(int damage, FieldCell source) {
	         // TODO Auto-generated method stub
	
	 }
	
	 @Override
	 public void enemyKilled() {
	         // TODO Auto-generated method stub
	
	 }

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		// TODO Auto-generated method stub
		
	}
}