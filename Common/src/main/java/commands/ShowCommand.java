package commands;

import Messages.Request;

/**
 * Команда, выводящая все элементы коллекции
 */
public class ShowCommand implements Command{

    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new ShowCommand()),argument);
    }




}
