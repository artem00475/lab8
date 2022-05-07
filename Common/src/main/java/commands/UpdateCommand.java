package commands;

import Messages.Request;

/**
 * Команда, обновляющая элемент по id
 */
public class UpdateCommand implements Command{

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "update id";
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new UpdateCommand()),argument);
    }
}
