package commands;

import Messages.Request;

/**
 * Команда, удаляющая первый элемент в очереди
 */
public class RemoveHeadCommand implements Command{

    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "remove_head";
    }

    @Override
    public String getDescription() {
        return "вывести первый элемент коллекции и удалить его";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new RemoveHeadCommand()),argument);
    }
}
