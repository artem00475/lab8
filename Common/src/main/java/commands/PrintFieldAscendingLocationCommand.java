package commands;

import Messages.Request;

/**
 * Команда, выводящая значения поля location в порядке возрастания
 */
public class PrintFieldAscendingLocationCommand implements Command{

    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "print_field_ascending_location";
    }

    @Override
    public String getDescription() {
        return "вывести значения поля location всех элементов в порядке возрастания";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new PrintFieldAscendingLocationCommand()),argument);
    }
}
