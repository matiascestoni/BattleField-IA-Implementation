package ia.battle.camp;

import java.util.ArrayList;

import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.camp.actions.Skip;
import ia.battle.move.AStar;
import ia.battle.move.Node;
import ia.exceptions.RuleException;

public class AStartWarrior extends Warrior{

	private int enemyRange;
	
	public AStartWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		
		// Rango enemigo máximo detectado
		enemyRange = 0;
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
        // enemy data
        WarriorData enemyData = BattleField.getInstance().getEnemyData();		
        
        // Obtengo mejor camino para objetivo
        // Armar una clase con el algoritmo y llamar al metodo que retorne lista de movimientos
        AStar astar = new AStar();
        astar.discoverMap();
        
		/* Implementar diferentes estrategias segun numero de accion */    
        /*if (actionNumber <= 1) {*/
        	// Primeros 2 movimientos
        	
	        // Si esta en rango ataco
	        if(enemyData.getInRange()){
	        	FieldCell enemyCell = enemyData.getFieldCell();
	        	/*
				// Calculo distancia Manhattan
	        	int h = Math.abs(this.getPosition().getX() - enemyCell.getX());
				h += Math.abs(this.getPosition().getY() - enemyCell.getY());	        	
	        	
				if (h > enemyRange)
				{
					System.out.append("ENEMY RANGE PREVIO: " + enemyRange + "\n");					
					enemyRange = h;
					System.out.append("* * * * RANGO CAMBIO A:" + enemyRange + "* * * \n");
				}*/
	        	System.out.append("****************************************** ATACO *********************************************\n");
	        	return new Attack(enemyCell);
	        }
	        
	        // Sino me acerco
	        FieldCell enemyCell = enemyData.getFieldCell();
	        

	        ArrayList<Node> nodes = astar.findPath(getPosition(), enemyCell);
	        
	        // Si está encerrado, tiene que romper pared.
	        if (nodes == null) {
				FieldCell wall = astar.getWallToBreak(getPosition(), enemyCell);
				
				if (wall != null) {
					System.out.append("********************** ROMPIENDO PARED " + wall.getX() + ":" + wall.getY() + " *********************************************\n");
					return new Attack(wall);
				} else {
	        		System.out.append("****************************************** ME SALTEO TURNO PORQUE NO PUEDO MOVERME *********************************************\n");
	        		return new Skip();					
				}
				
	        } else {
		        // Mueve la mitad de lo que se puede mover antes de atacar.
		        // Se genera un arrayList con las celdas por donde se va a mover
		        ArrayList<FieldCell> movs = (ArrayList<FieldCell>) astar.getMaxMoves(nodes, this.getSpeed() / 10);
		        
		        // Se pasa la lista de celdas al objeto Move
		        WarriorMove move = new WarriorMove();
		        move.setMovimientos(movs);
		        System.out.append("****************************************** ME MUEVO *********************************************\n");
		        return move;
	        }
	        /*} else {
			
	        // Me alejo (considerando no acercarme al Hunter)
	        FieldCell enemyCell = enemyData.getFieldCell();
	        
	        // Obtengo camino
	        AStar astar = new AStar();
	        astar.discoverMap();
	        ArrayList<Node> nodes = astar.findPath(getPosition(), enemyCell);
	        
	        // Ver maximo de movimientos ahora le mando 3 casilleros por jugada
	        // Se genera un arrayList con las celdas por donde se va a mover
	        ArrayList<FieldCell> movs = (ArrayList<FieldCell>) astar.getMaxMoves(nodes, this.getSpeed() / 10);
	        
	        // Se pasa la lista de celdas al objeto Move
	        WarriorMove move = new WarriorMove();
	        move.setMovimientos(movs);
	        
	        return move;		
			
		}	*/
			

		

        


        

	}

	@Override
	public void wasAttacked(int damage, FieldCell source) {
		System.out.append("*** ME ATACARON - ME QUEDA: " + this.getHealth() + " ***\n");
	}

	@Override
	public void enemyKilled() {
		System.out.append("*** MATÉ AL ENEMIGO ***\n");
		
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		// TODO Auto-generated method stub
		
	}

}
