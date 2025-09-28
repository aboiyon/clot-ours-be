package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Order {
    int mId;
    String mName;
    int mAge;
    Timestamp mBirthday;

    public Order(int mId, String mName, int mAge, Timestamp mBirthday) {
        this.mId = mId;
        this.mName = mName;
        this.mAge = mAge;
        this.mBirthday = mBirthday;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public Timestamp getmBirthday() {
        return mBirthday;
    }

    public void setmBirthday(Timestamp mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getmId() == order.getmId() && getmAge() == order.getmAge() && Objects.equals(getmName(), order.getmName()) && Objects.equals(getmBirthday(), order.getmBirthday());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getmId(), getmName(), getmAge(), getmBirthday());
    }
}
