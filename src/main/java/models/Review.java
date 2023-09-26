package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Review implements Comparable<Review>{
    private String content;
    private String author;
    private int rating;
    private int id;
    private int tourId;
    private long createdAt;
    private String formattedCreatedAt;

    public Review(String content, String author, int rating, int id, int tourId, long createdAt, String formattedCreatedAt) {
        this.content = content;
        this.author = author;
        this.rating = rating;
        this.id = id;
        this.tourId = tourId;
        this.createdAt = System.currentTimeMillis();
        this.formattedCreatedAt = setFormattedCreatedAt();
    }


    @Override
    public int compareTo(Review reviewObject) {
        if (this.createdAt < reviewObject.createdAt)
        {
            return -1; //this object was made earlier than the second object.
        }
        else if (this.createdAt > reviewObject.createdAt){ //this object was made later than the second object
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
        Date date = new Date(createdAt);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public String setFormattedCreatedAt(){
        Date date = new Date(this.createdAt);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedCreatedAt = sdf.format(date);
        return datePatternToUse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
                Objects.equals(author, review.author) &&
                Objects.equals(formattedCreatedAt, review.formattedCreatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, author, rating, id, tourId, createdAt, formattedCreatedAt);
    }
}
