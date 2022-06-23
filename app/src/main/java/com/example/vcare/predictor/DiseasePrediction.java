package com.example.vcare.predictor;

public class DiseasePrediction {

    private String symptoms;
    private String predictedDisease;
    private int approvalstatus;
    private String precautions;
    private String patientName;
    private String patientEmail;
    private String discategory;

    public DiseasePrediction() {
    }

    public DiseasePrediction(String symptoms, String predictedDisease, int approvalstatus, String precautions, String patientName, String patientEmail, String discategory) {
        this.symptoms = symptoms;
        this.predictedDisease = predictedDisease;
        this.approvalstatus = approvalstatus;
        this.precautions = precautions;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.discategory = discategory;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPredictedDisease() {
        return predictedDisease;
    }

    public void setPredictedDisease(String predictedDisease) {
        this.predictedDisease = predictedDisease;
    }

    public int getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(int approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getPrecautions() {
        return precautions;
    }

    public void setPrecautions(String precautions) {
        this.precautions = precautions;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDiscategory() {
        return discategory;
    }

    public void setDiscategory(String discategory) {
        this.discategory = discategory;
    }
}

