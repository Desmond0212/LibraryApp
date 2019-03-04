package com.example.desmond.libraryapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 04-Feb-18.
 */

public class BooksUpdate {

    String A_Book_Id;
    String B_Book_Title;
    String C_Name_By;
    String D_Book_Contributor;
    String E_Book_MaterialType;
    String F_Book_Publisher;
    String G_Book_Edition;
    String H_Book_Description;
    String I_Book_Subject;
    String J_Book_CallNumber;
    String K_Book_CopyNumber;

    public BooksUpdate()
    {

    }

    public BooksUpdate(String a_Book_Id, String b_Book_Title, String c_Name_By, String d_Book_Contributor, String e_Book_MaterialType, String f_Book_Publisher, String g_Book_Edition, String h_Book_Description, String i_Book_Subject, String j_Book_CallNumber, String k_Book_CopyNumber) {


        A_Book_Id = a_Book_Id;
        B_Book_Title = b_Book_Title;
        C_Name_By = c_Name_By;
        D_Book_Contributor = d_Book_Contributor;
        E_Book_MaterialType = e_Book_MaterialType;
        F_Book_Publisher = f_Book_Publisher;
        G_Book_Edition = g_Book_Edition;
        H_Book_Description = h_Book_Description;
        I_Book_Subject = i_Book_Subject;
        J_Book_CallNumber = j_Book_CallNumber;
        K_Book_CopyNumber = k_Book_CopyNumber;
    }



    public String getA_Book_Id() {
        return A_Book_Id;
    }

    public String getB_Book_Title() {
        return B_Book_Title;
    }

    public String getC_Name_By() {
        return C_Name_By;
    }

    public String getD_Book_Contributor() {
        return D_Book_Contributor;
    }

    public String getE_Book_MaterialType() {
        return E_Book_MaterialType;
    }

    public String getF_Book_Publisher() {
        return F_Book_Publisher;
    }

    public String getG_Book_Edition() {
        return G_Book_Edition;
    }

    public String getH_Book_Description() {
        return H_Book_Description;
    }

    public String getI_Book_Subject() {
        return I_Book_Subject;
    }

    public String getJ_Book_CallNumber() {
        return J_Book_CallNumber;
    }

    public String getK_Book_CopyNumber() {
        return K_Book_CopyNumber;
    }


    public void setA_Book_Id(String a_Book_Id) {
        A_Book_Id = a_Book_Id;
    }

    public void setB_Book_Title(String b_Book_Title) {
        B_Book_Title = b_Book_Title;
    }

    public void setC_Name_By(String c_Name_By) {
        C_Name_By = c_Name_By;
    }

    public void setD_Book_Contributor(String d_Book_Contributor) {
        D_Book_Contributor = d_Book_Contributor;
    }

    public void setE_Book_MaterialType(String e_Book_MaterialType) {
        E_Book_MaterialType = e_Book_MaterialType;
    }

    public void setF_Book_Publisher(String f_Book_Publisher) {
        F_Book_Publisher = f_Book_Publisher;
    }

    public void setG_Book_Edition(String g_Book_Edition) {
        G_Book_Edition = g_Book_Edition;
    }

    public void setH_Book_Description(String h_Book_Description) {
        H_Book_Description = h_Book_Description;
    }

    public void setI_Book_Subject(String i_Book_Subject) {
        I_Book_Subject = i_Book_Subject;
    }

    public void setJ_Book_CallNumber(String j_Book_CallNumber) {
        J_Book_CallNumber = j_Book_CallNumber;
    }

    public void setK_Book_CopyNumber(String k_Book_CopyNumber) {
        K_Book_CopyNumber = k_Book_CopyNumber;
    }
}
