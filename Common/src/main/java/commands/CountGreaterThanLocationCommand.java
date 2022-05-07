package commands;

import Messages.Request;

/**
 * Команда, выводящая количество элементов, значение которых больше заданного
 */
public class CountGreaterThanLocationCommand implements Command {

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "count_greater_than_location";
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов, значение поля location которых больше заданного";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new CountGreaterThanLocationCommand()),argument);
    }
}
