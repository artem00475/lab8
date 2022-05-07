package commands;

import Messages.Request;

/**
 * Команда, удаляющая элементы, превышающие заданный
 */
public class RemoveGreaterCommand implements Command{

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new RemoveGreaterCommand()),argument);
    }
}
