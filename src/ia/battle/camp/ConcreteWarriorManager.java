package ia.battle.camp;
import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;


public class ConcreteWarriorManager extends WarriorManager{


    @Override
    public String getName() {
            return "El couch";
    }

    @Override
    public Warrior getNextWarrior() throws RuleException {

            ConcreteWarrior m = new ConcreteWarrior("Warrior 1", 60, 10, 10, 10, 10);

            return m;
    }

}
