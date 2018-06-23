public abstract class Lab {

    public Lab() {}

    public Lab(String path) {
        fillFromFile(path);
        run();
        print();
    }

    public abstract void fillFromFile(String path);

    public abstract void run();

    public abstract void print();
}
