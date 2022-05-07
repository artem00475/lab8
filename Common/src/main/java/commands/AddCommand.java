package commands;

import Messages.Request;

/**
 * Команда, добавляющая элемент в коллекцию
 */
public class AddCommand implements Command {

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new AddCommand()),argument);
    }
}
