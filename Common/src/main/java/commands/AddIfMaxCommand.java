package commands;


import Messages.Request;

/**
 * Команда, добавляющая элемент, если он больше максимального
 */
public class AddIfMaxCommand implements Command{

    @Override
    public boolean hasArgement() {
        return true;
    }

    @Override
    public String getName() {
        return "add_if_max";
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции";
    }

    @Override
    public void execute(Boolean argument, CommandManager commandManager) {
        commandManager.execute(new Request(new AddIfMaxCommand()),argument);
    }
}
