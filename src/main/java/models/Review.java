package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Review implements Comparable<Review>{
    private String content;
    private String author;
    private int rating;
    private int id;
    private int kidId;
    private int manId;
    private int womanId;
    private long createdat;
    private String formattedCreatedAt;

    public Review(String content, String author, int rating, int id, int kidId) {
        this.content = content;
        this.author = author;
        this.rating = rating;
        this.id = id;
        this.kidId = kidId;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt();
    }

    // Constructor for Man
    public Review(String content, String author, int rating, int id, long createdat, int manId) {
        this.content = content;
        this.author = author;
        this.rating = rating;
        this.id = id;
        this.manId = manId;
        this.createdat = createdat;
        setFormattedCreatedAt();
    }

    // Constructor for Woman
    public Review(String content, String author, int rating, int id, int womanId, String formattedCreatedAt) {
        this.content = content;
        this.author = author;
        this.rating = rating;
        this.id = id;
        this.womanId = womanId;
        this.createdat = createdat;
        setFormattedCreatedAt();
    }

//    public Review(String content, String author, int rating, int id, int kidId, String type) {
//        this.content = content;
//        this.author = author;
//        this.rating = rating;
//        this.id = id;
//        if ("kid".equals(type)) {
//            this.kidId = kidId;
//        } else if ("man".equals(type)) {
//            this.manId = kidId;
//        } else if ("woman".equals(type)) {
//            this.womanId = kidId;
//        }
//        this.createdat = System.currentTimeMillis();
//        setFormattedCreatedAt();
//    }


//    public Review(String content, String author, int rating, int id, int manId) {
//        this.content = content;
//        this.author = author;
//        this.rating = rating;
//        this.id = id;
//        this.manId = manId;
//        this.createdat = System.currentTimeMillis();
//        setFormattedCreatedAt();
//    }
//
//    public Review(String content, String author, int rating, int id, int womanId) {
//        this.content = content;
//        this.author = author;
//        this.rating = rating;
//        this.id = id;
//        this.womanId = womanId;
//        this.createdat = System.currentTimeMillis();
//        setFormattedCreatedAt();
//    }

    @Override
    public int compareTo(Review reviewObject) {
        return Long.compare(reviewObject.createdat, this.createdat);
//        if (this.createdat < reviewObject.createdat)
//        {
//            return -1; //this object was made earlier than the second object.
//        }
//        else if (this.createdat > reviewObject.createdat){ //this object was made later than the second object
//            return 1;
//        }
//        else {
//            return 0; //they were made at the same time, which is very unlikely, but mathematically not impossible.
//        }
    }

    public long getCreatedat(){
        return createdat;
    }

    public void setCreatedAt() {
        this.createdat = System.currentTimeMillis();
    }

    public String getFormattedCreatedAt(){
        Date date = new Date(createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public String setFormattedCreatedAt(){
        Date date = new Date(this.createdat);
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

    public int getKidId() {
        return kidId;
    }

    public void setKidId(int kidId) {
        this.kidId = kidId;
    }

    public int getManId() {
        return manId;
    }

    public void setManId(int manId) {
        this.manId = manId;
    }

    public int getWomanId() {
        return womanId;
    }

    public void setWomanId(int womanId) {
        this.womanId = womanId;
    }

    //    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getKidId() {
//        return kidId;
//    }
//
//    public void setKidId(int kidId) {
//        this.kidId = kidId;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Review)) return false;
//        Review review = (Review) o;
//        return rating == review.rating &&
//                id == review.id &&
//                kidId == review.kidId &&
//                createdat == review.createdat &&
//                Objects.equals(content, review.content) &&
//                Objects.equals(author, review.author) &&
//                Objects.equals(formattedCreatedAt, review.formattedCreatedAt);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(content, author, rating, id, kidId, createdat, formattedCreatedAt);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return rating == review.rating &&
                id == review.id &&
                kidId == review.kidId &&
                manId == review.manId &&
                womanId == review.womanId &&
                createdat == review.createdat &&
                content.equals(review.content) &&
                author.equals(review.author) &&
                formattedCreatedAt.equals(review.formattedCreatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, author, rating, id, kidId, manId, womanId, createdat, formattedCreatedAt);
    }
}
