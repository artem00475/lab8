import Messages.Answer;
import Messages.Request;
import collection.CollectionManager;
import commands.Command;
import commands.CommandManager;
import person.ColorE;
import person.Location;
import person.Person;

public class ServerCommandManager implements CommandManager {
    private boolean wasErrors=false;
    private final CollectionManager collectionManager;

    public ServerCommandManager(CollectionManager collectionManager){
        this.collectionManager=collectionManager;
    }

    public Answer execute(Request request, boolean argument) {
        Command command = request.getCommand();
        if (!command.hasArgement()) {
            if (command.getName().equals("help")) {
                return new Answer(helpCommand(), wasErrors);
            } else if (command.getName().equals("info")) {
                return new Answer(infoCommand(),wasErrors);
            } else if (command.getName().equals("show")) {
                return new Answer(showCommand(),wasErrors);
            } else if (command.getName().equals("clear")) {
                return new Answer(clearCommand(),wasErrors);
            }else if (command.getName().equals("remove_head")){
                return new Answer(removeHeadCommand(),wasErrors);
            }else {
                return new Answer(printFieldAscendingLocationCommand(),wasErrors);
            }
        }else{
            if (command.getName().equals("add")){
                return new Answer(addCommand((Person) request.getObject()), wasErrors);
            }else if (command.getName().equals("add_if_max")){
                return new Answer(addIfMaxCommand((Person) request.getObject()), wasErrors);
            }else if (command.getName().equals("remove_greater")){
                return new Answer(removeGreaterCommand((Person) request.getObject()), wasErrors);
            }else if (command.getName().equals("remove_by_id")){
                return new Answer(removeByIdCommand(request.getId()), wasErrors);
            }else if (command.getName().equals("update id")){
                return new Answer(updateCommand(request.getId(), (Person) request.getObject()), wasErrors);
            }else if (command.getName().equals("count_greater_than_location")){
                return new Answer(countGreaterThanLocationCommand((Location) request.getObject()), wasErrors);
            }else {
                return new Answer(filterLessThanEyeColorCommand((ColorE) request.getObject()), wasErrors);
            }
        }
    }

    public String helpCommand(){
               return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить работу \n" +
                "remove_head : вывести первый элемент коллекции и удалить его\n" +
                "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "count_greater_than_location location : вывести количество элементов, значение поля location которых больше заданного\n" +
                "filter_less_than_eye_color eyeColor : вывести элементы, значение поля eyeColor которых меньше заданного\n" +
                "print_field_ascending_location : вывести значения поля location всех элементов в порядке возрастания\n";
    }

    public String infoCommand(){
        return  "Информация о коллекции:\n" +
                "Коллекция типа PriorityQueue, в которой хранятся объекты класса Person\n" +
                "Дата инициализации: " + collectionManager.getInitDate() + "\n" +
                "Количестов элементов: " + collectionManager.getCollection().size() + "\n";
    }

    public String showCommand(){
        StringBuilder stringBuilder = new StringBuilder();
        if (collectionManager.getCollection().isEmpty()) {
            stringBuilder.append("Нельзя выполнить команду show: коллекция пустая\n");
        } else {
            stringBuilder.append("Все элементы коллекции: \n");
            collectionManager.getCollection().forEach(person -> stringBuilder.append(person).append("\n"));
        }
        return stringBuilder.toString();
    }

    public String clearCommand(){
        collectionManager.removeAll();
        return "Коллекция очищена";
    }

    public String removeHeadCommand() {
        if (collectionManager.getCollection().isEmpty()) {
            return "Коллекция пуста";
        } else {
            return collectionManager.removeFirstElement().toString();
        }
    }

    public String printFieldAscendingLocationCommand(){
        StringBuilder stringBuilder = new StringBuilder();
        if (collectionManager.getCollection().isEmpty()) {
            return "Коллекция пуста";
        } else {collectionManager.sortByLocation().forEach(person -> stringBuilder.append(person.getLocation()).append("\n"));
        } return stringBuilder.toString();
    }

    public String addCommand(Person person){
        collectionManager.addElement(createPerson(person));
        return "Элемент успешно добавлен";
   }

   public String addIfMaxCommand(Person person){
       if (collectionManager.ifMore(person)) {
           collectionManager.addElement(createPerson(person));
           return "Элемент успешно добавлен";
       } else {
           return "Значение элемента не превышает наибольшего элемента коллекции";
       }
   }

   public String removeGreaterCommand(Person person){
       if (collectionManager.removeGreater(person)){
           return "Элементы успешно удалены";
       } else {
           return "В коллекции нет элементов, удовлетворяющих условию";
       }
   }

   public String removeByIdCommand(int id){
       if (collectionManager.removeElementByID(id)) {
           return "Элемент успешно удалён.";
       }else {return "Элемента с таким id нет в коллекции";}
   }

   public String updateCommand(int id, Person person){
        if (collectionManager.updateElement(id,person)){
            return "Элемент успешно обговлен";
        }else {
            return "Элемента с таким id нет в коллекции";}
        }

   public String countGreaterThanLocationCommand(Location location){
        return "Количество элементов, значение поля location которых больше заданного - " + collectionManager.countGreaterLocation(location);

   }

   public String filterLessThanEyeColorCommand(ColorE colorE){
        StringBuilder stringBuilder = new StringBuilder();
        collectionManager.filterLessThanEyeColor(colorE).forEach(person -> stringBuilder.append(person.toString()).append("\n"));
       if (stringBuilder.length()==0){
           return "Таких элементов нет";
       }else {return stringBuilder.toString();}
   }

   public Person createPerson(Person person){
        return new Person(person.getName(),person.getCoordinates().getX(),person.getCoordinates().getY(),person.getHeight(),person.getEyeColor(),person.getHairColor(),person.getNationality(),person.getLocation().getX(),person.getLocation().getY(),person.getLocation().getZ(),person.getLocation().getName());
   }
}
