package com.example.clientApi.model.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**Class defining table/Entity structure for the
 * database
 * */
@Entity(tableName = "authors")
public class Authors {
    @PrimaryKey(autoGenerate = true)
    private int id;
    //@ColumnInfo(name="articleTitle")
    private String articleTitle;

    //@ColumnInfo(name="articleAuthor")
    private String articleAuthor;

    @Override
    public String toString() {
        return "Authors{" +
                "id=" + id +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleAuthor='" + articleAuthor + '\'' +
                '}';
    }

    public Authors(int id, String articleTitle, String articleAuthor) {
        this.id = id;
        this.articleTitle = articleTitle;
        this.articleAuthor = articleAuthor;
    }

    @Ignore
    public Authors(String articleTitle, String articleAuthor) {
        this.articleTitle = articleTitle;
        this.articleAuthor = articleAuthor;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }
}
