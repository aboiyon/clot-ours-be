package models;

import java.util.Objects;

public class Review implements Comparable<Review>{
    private String content;
    private String writtenBy;
    private int rating;
    private int id;
    private int tourId;
    private long createdAt;
    private String formattedCreatedAt;

    public Review(String content, String writtenBy, int rating, int id, int tourId, long createdAt, String formattedCreatedAt) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.id = id;
        this.tourId = tourId;
        this.createdAt = System.currentTimeMillis();
        setFormattedCreatedAt();
    }

    @Override
    public int compareTo(Review reviewObject) {
        if (this.createdat < reviewObject.createdat)
        {
            return -1; //this object was made earlier than the second object.
        }
        else if (this.createdat > reviewObject.createdat){ //this object was made later than the second object
            return 1;
        }
        else {
            return 0; //they were made at the same time, which is very unlikely, but mathematically not impossible.
        }
    }

    public long getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = System.currentTimeMillis();
    }

    public String getFormattedCreatedAt(){
        Date date = new Date(createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public void setFormattedCreatedAt(){
        Date date = new Date(this.createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedCreatedAt = sdf.format(date);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setFormattedCreatedAt(String formattedCreatedAt) {
        this.formattedCreatedAt = formattedCreatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return rating == review.rating &&
                id == review.id &&
                tourId == review.tourId &&
                createdAt == review.createdAt &&
                Objects.equals(content, review.content) &&
                Objects.equals(writtenBy, review.writtenBy) &&
                Objects.equals(formattedCreatedAt, review.formattedCreatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, writtenBy, rating, id, tourId, createdAt, formattedCreatedAt);
    }
}
