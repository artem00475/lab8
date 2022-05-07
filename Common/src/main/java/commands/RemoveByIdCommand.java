package commands;

import Messages.Request;

/**
 * Команда, удаляющая элемент по id
 */
public class RemoveByIdCommand implements Command {

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new RemoveByIdCommand()),argument);
    }
}
