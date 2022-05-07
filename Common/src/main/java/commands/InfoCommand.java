package commands;

import Messages.Request;

/**
 * Команда, выводящая информацию о коллекции
 */
public class InfoCommand implements Command{

    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов)";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new InfoCommand()),argument);
    }
}
