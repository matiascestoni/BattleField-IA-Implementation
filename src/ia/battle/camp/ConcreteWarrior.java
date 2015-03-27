package ia.battle.camp;
import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.battle.camp.FieldCellType;
import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorData;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

import java.util.ArrayList;


public class ConcreteWarrior extends Warrior{

	 private enum Direccion {
         ARRIBA, ABAJO, IZQUIERDA, DERECHA,
	 }

	 private Direccion direction = Direccion.DERECHA;
	
	 public ConcreteWarrior(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
	         super(name, health, defense, strength, speed, range);
	
	 }
	
	 @Override
	 public Action playTurn(long tick, int actionNumber) {
	
	         if (actionNumber == 1) {
	                 // Se genera el objeto que indica los movimientos a realizar
	        	 WarriorMove move = new WarriorMove();
	
	                 try {
	                         // Se obtiene la posicion actual de mi warrior
	                         int x = this.getPosition().getX(), y = this.getPosition().getY();
	
	                         // Se obtiene la celda (x+1, y)
	                         FieldCell next = this.getPosition();
	                        
	                         while (next.equals(this.getPosition())) {
	                                
	                                 switch(this.direction){
	                                 case DERECHA:
	                                         if (BattleField.getInstance().getFieldCell(x + 1, y).getFieldCellType()==FieldCellType.BLOCKED)
	                                                 this.direction = Direccion.ABAJO;
	                                         else
	                                                 next = BattleField.getInstance().getFieldCell(x + 1, y);
	                                         break;
	                                        
	                                 case IZQUIERDA:
	                                         if (BattleField.getInstance().getFieldCell(x - 1, y).getFieldCellType()==FieldCellType.BLOCKED)
	                                                 this.direction = Direccion.ARRIBA;
	                                         else
	                                                 next = BattleField.getInstance().getFieldCell(x - 1, y);
	                                         break;
	                                        
	                                 case ARRIBA:
	                                         if (BattleField.getInstance().getFieldCell(x, y + 1).getFieldCellType()==FieldCellType.BLOCKED)
	                                                 this.direction = Direccion.DERECHA;
	                                         else
	                                                 next = BattleField.getInstance().getFieldCell(x, y + 1);
	                                         break;
	                                        
	                                 case ABAJO:
	                                         if (BattleField.getInstance().getFieldCell(x, y - 1).getFieldCellType()==FieldCellType.BLOCKED)
	                                                 this.direction = Direccion.IZQUIERDA;
	                                         else
	                                                 next = BattleField.getInstance().getFieldCell(x, y - 1);
	                                         break;
	                                        
	                                 }
	                                
	                         }
	
	                         // Se genera un arrayList con las celdas por donde se va a mover
	                         ArrayList<FieldCell> movs = new ArrayList<FieldCell>();
	
	                         movs.add(next);
	
	                         // Se pasa la lista de celdas al objeto Move
	                         move.setMovimientos(movs);
	
	                 } catch (OutOfMapException e) {
	                         e.printStackTrace();
	                 }
	
	                 // Se devuelve la accion de mover al battlefield
	                 return move;
	         }
	         else
	         {
	                 // Los datos del enemigo
	                 WarriorData enemy = BattleField.getInstance().getEnemyData();
	
	                 return new Attack(enemy.getFieldCell());
	         }
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
