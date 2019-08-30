package app.model;

public enum Subject {
    ECONOMY("Экономика"), PROGRAMMING("Программирование"), HISTORY("История");

    private String title;

    Subject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
