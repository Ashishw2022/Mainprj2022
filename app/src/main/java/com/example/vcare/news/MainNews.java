package com.example.vcare.news;

import java.util.ArrayList;

public class MainNews {

    private String status;
    private String totalResults;
    private ArrayList<com.example.vcare.news.ModelClass> articles;

    public MainNews(String status, String totalResults, ArrayList<com.example.vcare.news.ModelClass> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<com.example.vcare.news.ModelClass> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<com.example.vcare.news.ModelClass> articles) {
        this.articles = articles;
    }
}
