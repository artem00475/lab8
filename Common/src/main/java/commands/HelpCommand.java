package commands;

import Messages.Request;

/**
 * Команда, выводящая список команд
 */
public class HelpCommand implements Command{
    @Override
    public boolean hasArgement() {
        return false;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new HelpCommand()),argument);
    }
}
