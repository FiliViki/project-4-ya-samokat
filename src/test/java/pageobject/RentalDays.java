package pageobject;

public enum RentalDays {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5),
    SEVEN(6);

    private final int number;

    RentalDays(int index) {
        this.number = index;
    }

    public int getIndex() {
        return number;
    }
}
