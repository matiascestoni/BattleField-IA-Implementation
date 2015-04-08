package ia.battle.camp;

import java.util.ArrayList;

import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.move.AStar;
import ia.battle.move.Node;
import ia.exceptions.RuleException;

public class AStartWarrior extends Warrior{

	public AStartWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
	      /* 
	       * Implementar diferentes estrategias segun numero de accion    
	        switch(actionNumber){
	    		case 1:
					break;
				case 2:
					break;
				case 3:
					break;
			}		
			*/

		
        // enemy data
        WarriorData enemyData = BattleField.getInstance().getEnemyData();
        
        // Si esta en rango ataco
        if(enemyData.getInRange()){
        	return new Attack(enemyData.getFieldCell());
        }
        // Sino me acerco
        FieldCell enemyCell = enemyData.getFieldCell();
        
        // Obtengo mejor camino para objetivo
        // Armar una clase con el algoritmo y llamar al metodo que retorne lista de movimientos
        AStar astar = new AStar();
        astar.discoverMap();
        ArrayList<Node> nodes = astar.findPath(getPosition(), enemyCell);
        
        // Ver maximo de movimientos ahora le mando 3 casilleros por jugada
        // Se genera un arrayList con las celdas por donde se va a mover
        ArrayList<FieldCell> movs = (ArrayList<FieldCell>) astar.getMaxMoves(nodes, 3);
        
        // Se pasa la lista de celdas al objeto Move
        WarriorMove move = new WarriorMove();
        move.setMovimientos(movs);
        
        return move;

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
