package commands;

import Messages.Request;

/**
 * Команда, очищающая коллекцию
 */
public class ClearCommand implements Command {

    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new ClearCommand()),argument);
    }
}
