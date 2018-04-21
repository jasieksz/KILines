package model;

public enum Award {
    COLLISION(10),
    APPLE_AWARD(5);

    private int award;

    Award(int award) {
        this.award = award;
    }

    public int getAward() {
        return award;
    }
}
