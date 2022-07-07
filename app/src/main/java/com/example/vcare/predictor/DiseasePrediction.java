package com.example.vcare.predictor;

public class DiseasePrediction {

    private String symptoms;
    private String predictedDisease;
    private int approvalstatus;
    private String precautions;
    private String patientName,docname,docemail;
    private String patientEmail;
    private String discategory;
    private String remarks;
    private String age;
    private String gender;
    private String predictedDate;
    String pid;

    public DiseasePrediction() {
    }

    public String getDocemail() {
        return docemail;
    }

    public void setDocemail(String docemail) {
        this.docemail = docemail;
    }

    public DiseasePrediction(String age, String remarks, String pid, String symptoms, String predictedDisease, int approvalstatus, String precautions, String patientName, String patientEmail, String discategory, String predictedDate, String docname, String docemail) {
        this.symptoms = symptoms;
        this.pid =pid;
        this.predictedDisease = predictedDisease;
        this.approvalstatus = approvalstatus;
        this.precautions = precautions;
        this.patientName = patientName;
        this.patientEmail = patientEmail;
        this.discategory = discategory;
        this.remarks=remarks;
        this.age= age;
        this.predictedDate = predictedDate;
        this.docemail= docemail;
        this.docname = docname;

    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }
}

