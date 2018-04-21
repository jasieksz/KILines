package model;

public enum Award {
    COLLISION(10);

    private int award;

    Award(int award) {
        this.award = award;
    }

    public int getAward() {
        return award;
    }
}
