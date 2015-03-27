package ia.battle.enemy;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class ConcreteEnemyManager  extends WarriorManager{
	
    @Override
    public String getName() {
            return "Enemy Manager";
    }

    @Override
    public Warrior getNextWarrior() throws RuleException {

            Warrior m = new ConcreteStaticWarrior("Static Warrior", 60, 10, 10, 10, 10);

            return m;
    }


}
