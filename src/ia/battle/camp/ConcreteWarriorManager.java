package ia.battle.camp;
import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;


public class ConcreteWarriorManager extends WarriorManager{
	
    @Override
    public String getName() {
            return "Warrior Manager";
    }

    @Override
    public Warrior getNextWarrior() throws RuleException {

            Warrior m = new AStartWarrior("Warrior 1", 10, 10, 10, 40, 30);

            return m;
    }

}
